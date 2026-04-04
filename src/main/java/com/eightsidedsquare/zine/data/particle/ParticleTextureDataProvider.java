package com.eightsidedsquare.zine.data.particle;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Extend this class and implement {@link ParticleTextureDataProvider#generate}.
 *
 * <p>Register an instance of the class with {@link FabricDataGenerator.Pack#addProvider} in a {@link net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint}.
 */
public abstract class ParticleTextureDataProvider implements DataProvider {

    private final PackOutput.PathProvider pathResolver;

    public ParticleTextureDataProvider(FabricPackOutput output) {
        this.pathResolver = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "particles");
    }

    protected abstract void generate(Output output);

    protected final Identifier[] rangeAscending(Identifier texture, int minInclusive, int maxInclusive) {
        Identifier[] textures = this.createRangeArray(minInclusive, maxInclusive);
        for(int i = minInclusive; i <= maxInclusive; i++) {
            textures[i] = texture.withSuffix("_" + i);
        }
        return textures;
    }

    protected final Identifier[] rangeDescending(Identifier texture, int minInclusive, int maxInclusive) {
        Identifier[] textures = this.createRangeArray(minInclusive, maxInclusive);
        for(int i = minInclusive; i <= maxInclusive; i++) {
            textures[maxInclusive + minInclusive - i] = texture.withSuffix("_" + i);
        }
        return textures;
    }

    private Identifier[] createRangeArray(int minInclusive, int maxInclusive) {
        if(minInclusive < 0 || maxInclusive < 0) {
            throw new IllegalArgumentException("Range cannot be less than 0");
        }else if(minInclusive > maxInclusive) {
            throw new IllegalArgumentException("Min cannot be greater than max");
        }
        return new Identifier[maxInclusive - minInclusive + 1];
    }

    @Override
    public CompletableFuture<?> run(CachedOutput writer) {
        List<CompletableFuture<?>> futures = new ArrayList<>();
        Map<Identifier, List<Identifier>> map = new Object2ObjectOpenHashMap<>();
        this.generate((particleType, textures) ->
                map.put(BuiltInRegistries.PARTICLE_TYPE.getKey(particleType), textures)
        );
        map.forEach((id, textures) -> {
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            textures.forEach(texture -> jsonArray.add(texture.toString()));
            jsonObject.add("textures", jsonArray);
            futures.add(DataProvider.saveStable(writer, jsonObject, this.pathResolver.json(id)));
        });
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Particle Texture Data";
    }

    @FunctionalInterface
    public interface Output {

        void accept(ParticleType<?> particleType, List<Identifier> textures);

        default void accept(ParticleType<?> particleType, Identifier... textures) {
            this.accept(particleType, List.of(textures));
        }

        /**
         * Accepts the particle type with its registered ID as its single texture
         */
        default void accept(ParticleType<?> particleType) {
            this.accept(particleType, BuiltInRegistries.PARTICLE_TYPE.getKey(particleType));
        }

    }
}
