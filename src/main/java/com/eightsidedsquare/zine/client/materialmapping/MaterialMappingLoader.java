package com.eightsidedsquare.zine.client.materialmapping;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.StrictJsonParser;
import org.slf4j.Logger;

import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public final class MaterialMappingLoader {
    private static final FileToIdConverter MAPPING_LISTER = FileToIdConverter.json("material_mapping");
    private static final Logger LOGGER = LogUtils.getLogger();

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

    private MaterialMappingLoader() {
    }
}
