package com.eightsidedsquare.zine.client.materialmapping;

import com.google.gson.*;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.client.resources.model.cuboid.*;
import net.minecraft.client.resources.model.geometry.UnbakedGeometry;
import net.minecraft.client.resources.model.sprite.TextureSlots;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Util;
import org.slf4j.Logger;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

public final class MappableModelLoader {

    private static final FileToIdConverter MODEL_LISTER = FileToIdConverter.json("mappable_model");
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(CuboidModel.class, new ModelDeserializer())
            .registerTypeAdapter(CuboidModelElement.class, new CuboidModelElement.Deserializer())
            .registerTypeAdapter(CuboidFace.class, new CuboidFace.Deserializer())
            .create();

    @SuppressWarnings("ConstantValue")
    public static CompletableFuture<Map<Identifier, MappableModel.Unbaked>> loadModels(ResourceManager resourceManager, Executor executor) {
        return CompletableFuture.supplyAsync(() -> MODEL_LISTER.listMatchingResources(resourceManager), executor)
                .thenCompose(resources -> {
                    List<CompletableFuture<Pair<Identifier, MappableModel.Unbaked>>> result = new ObjectArrayList<>(resources.size() + 1);
                    result.add(CompletableFuture.supplyAsync(
                            () -> Pair.of(ItemModelGenerator.GENERATED_ITEM_MODEL_ID, MappableItemModel.UNBAKED)
                    ));
                    for (Map.Entry<Identifier, Resource> entry : resources.entrySet()) {
                        result.add(CompletableFuture.supplyAsync(() -> {
                            Identifier id = MODEL_LISTER.fileToId(entry.getKey());
                            try {
                                Reader reader = entry.getValue().openAsReader();

                                Pair<Identifier, MappableModel.Unbaked> pair;
                                try {
                                    CuboidModel model = GsonHelper.fromJson(GSON, reader, CuboidModel.class);
                                    pair = model.geometry() instanceof UnbakedCuboidGeometry(
                                            List<CuboidModelElement> elements
                                    ) ? Pair.of(id, new MappableModelImpl.UnbakedImpl(elements)) : null;
                                } catch (Throwable readThrowable) {
                                    if (reader != null) {
                                        try {
                                            reader.close();
                                        } catch (Throwable closeThrowable) {
                                            readThrowable.addSuppressed(closeThrowable);
                                        }
                                    }

                                    throw readThrowable;
                                }

                                if (reader != null) {
                                    reader.close();
                                }

                                return pair;
                            } catch (Exception exception) {
                                LOGGER.error("Failed to load model {}", entry.getKey(), exception);
                                return null;
                            }
                        }, executor));
                    }
                    return Util.sequence(result)
                            .thenApply(pairs -> pairs
                                    .stream()
                                    .filter(Objects::nonNull)
                                    .collect(Collectors.toUnmodifiableMap(Pair::getFirst, Pair::getSecond)));
                });
    }

    private MappableModelLoader() {
    }

    private static class ModelDeserializer extends CuboidModel.Deserializer {
        @Override
        public CuboidModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            UnbakedGeometry elements = this.getElements(context, object);
            return new CuboidModel(elements, null, null, null, TextureSlots.Data.EMPTY, null);
        }
    }
}
