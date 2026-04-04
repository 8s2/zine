package com.eightsidedsquare.zine.data.atlas;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.AtlasProvider;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.data.CachedOutput;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class ZineAtlasDefinitionProvider extends AtlasProvider {

    public ZineAtlasDefinitionProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public final CompletableFuture<?> run(CachedOutput writer) {
        List<CompletableFuture<?>> futures = new ArrayList<>();
        this.generate((atlasId, sources) -> futures.add(this.storeAtlas(writer, atlasId, sources)));
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    protected abstract void generate(Output output);

    public interface Output {
        void accept(Identifier atlasId, List<SpriteSource> sources);
    }
}
