package com.eightsidedsquare.zine.client.materialmapping;

import com.eightsidedsquare.zine.core.ZineMod;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.CacheSlot;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.Material;
import net.minecraft.client.renderer.texture.SpriteLoader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.AtlasManager;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.AtlasIds;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.StrictJsonParser;
import net.minecraft.util.thread.ParallelMapTransform;
import org.slf4j.Logger;

import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

public final class MaterialMappingLoader implements PreparableReloadListener {

    public static final Identifier ID = ZineMod.id("material_mapping");
    public static final MaterialMappingLoader INSTANCE = new MaterialMappingLoader();
    private static final FileToIdConverter MAPPING_LISTER = FileToIdConverter.json("material_mapping");
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final MaterialMapping.BakedSet EMPTY_MAPPING = new MaterialMapping.BakedSet(Map.of());
    private static final ResourceKey<? extends Registry<MaterialMapping.BakedSet>> ROOT_ID = ResourceKey.createRegistryKey(ID);
    private Map<ResourceKey<MaterialMapping.BakedSet>, MaterialMapping.BakedSet> mappings;
    private final CacheSlot<ClientLevel, Map<Holder<?>, MaterialMapping.BakedSet>> mappingCache = new CacheSlot<>(_ -> new Reference2ObjectOpenHashMap<>());

    public MaterialMapping.BakedSet getMapping(Holder<?> holder) {
        ClientLevel level = Minecraft.getInstance().level;
        return level == null ? EMPTY_MAPPING : this.mappingCache.compute(level).computeIfAbsent(holder, this::computeMapping);
    }

    public MaterialMapping.BakedSet getMapping(ResourceKey<MaterialMapping.BakedSet> key) {
        return Objects.requireNonNull(this.mappings, "Mappings are not loaded").getOrDefault(key, EMPTY_MAPPING);
    }

    public MaterialMapping.BakedSet getMapping(Identifier id) {
        return this.getMapping(ResourceKey.create(ROOT_ID, id));
    }

    private MaterialMapping.BakedSet computeMapping(Holder<?> holder) {
        return holder.unwrapKey()
                .map(MaterialMappingLoader::createKey)
                .map(this::getMapping)
                .orElse(EMPTY_MAPPING);
    }

    public static ResourceKey<MaterialMapping.BakedSet> createKey(ResourceKey<?> key) {
        return ResourceKey.create(ROOT_ID, key.identifier().withPrefix(key.registry().getPath() + "/"));
    }

    public static boolean hasMappings() {
        return INSTANCE.mappings != null;
    }

    @Override
    public CompletableFuture<Void> reload(SharedState currentReload, Executor taskExecutor, PreparationBarrier preparationBarrier, Executor reloadExecutor) {
        ResourceManager resourceManager = currentReload.resourceManager();
        CompletableFuture<SpriteLoader.Preparations> blockAtlasFuture = currentReload.get(AtlasManager.PENDING_STITCH).get(AtlasIds.BLOCKS);
        CompletableFuture<SpriteLoader.Preparations> itemAtlasFuture = currentReload.get(AtlasManager.PENDING_STITCH).get(AtlasIds.ITEMS);
        return CompletableFuture.allOf(blockAtlasFuture, itemAtlasFuture)
                .thenComposeAsync(_ -> this.prepare(blockAtlasFuture.join(), itemAtlasFuture.join(), resourceManager, taskExecutor), taskExecutor)
                .thenCompose(preparationBarrier::wait)
                .thenAcceptAsync(mappings -> {
                    this.mappings = mappings;
                    this.mappingCache.clear();
                }, reloadExecutor);

    }

    public static CompletableFuture<Map<Identifier, MaterialMapping.UnbakedSet>> loadMappings(ResourceManager resourceManager, Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
                    Map<Identifier, MaterialMapping.UnbakedSet> map = new Object2ObjectOpenHashMap<>();
                    for (Map.Entry<Identifier, List<Resource>> entry : MAPPING_LISTER.listMatchingResourceStacks(resourceManager).entrySet()) {
                        Identifier id = entry.getKey();
                        MaterialMapping.UnbakedSet.Builder builder = MaterialMapping.UnbakedSet.builder();
                        for (Resource resource : entry.getValue()) {
                            parseMapping(id, resource).ifPresent(builder::addAll);
                        }
                        map.put(MAPPING_LISTER.fileToId(id), builder.build());
                    }
                    return map;
                }, executor);
    }

    private static Optional<MaterialMapping.UnbakedSet> parseMapping(Identifier id, Resource resource) {
        MaterialMapping.UnbakedSet mapping = null;
        try {
            Reader reader = resource.openAsReader();
            try {
                mapping = MaterialMapping.UnbakedSet.CODEC.parse(
                        JsonOps.INSTANCE,
                        StrictJsonParser.parse(reader)
                ).getOrThrow();
            }catch (Throwable readThrowable) {
                try {
                    reader.close();
                } catch (Throwable closeThrowable) {
                    readThrowable.addSuppressed(closeThrowable);
                }
                throw readThrowable;
            }
            reader.close();
        } catch (Exception exception) {
            LOGGER.error("Failed to load material mapping {}", id, exception);
        }
        return Optional.ofNullable(mapping);
    }

    public static void clear() {
        INSTANCE.mappings = null;
        INSTANCE.mappingCache.clear();
    }

    private CompletionStage<Map<ResourceKey<MaterialMapping.BakedSet>, MaterialMapping.BakedSet>> prepare(
            SpriteLoader.Preparations blockAtlas,
            SpriteLoader.Preparations itemAtlas,
            ResourceManager resourceManager,
            Executor taskExecutor
    ) {
        Map<Identifier, MaterialMapping.UnbakedSet> textureMappings = new Object2ObjectOpenHashMap<>();
        SimpleJsonResourceReloadListener.scanDirectory(resourceManager, MAPPING_LISTER, JsonOps.INSTANCE, MaterialMapping.UnbakedSet.CODEC, textureMappings);
        return ParallelMapTransform.schedule(
                textureMappings,
                (_, texture) -> texture.bake(id -> new Material.Baked(getSprite(blockAtlas, itemAtlas, id.sprite()), id.forceTranslucent())),
                taskExecutor
        ).thenApply(map -> {
            Map<ResourceKey<MaterialMapping.BakedSet>, MaterialMapping.BakedSet> mappings = new Reference2ObjectOpenHashMap<>();
            map.forEach((id, sprite) -> mappings.put(ResourceKey.create(ROOT_ID, id), sprite));
            return mappings;
        });
    }

    private static TextureAtlasSprite getSprite(SpriteLoader.Preparations blockAtlas, SpriteLoader.Preparations itemAtlas, Identifier id) {
        TextureAtlasSprite sprite = itemAtlas.getSprite(id);
        return sprite == null ? blockAtlas.zine$getOrMissing(id) : sprite;
    }

    private MaterialMappingLoader() {
    }
}
