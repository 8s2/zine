package com.eightsidedsquare.zine.data.atlas;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.AtlasDefinitionProvider;
import net.minecraft.client.texture.atlas.AtlasSource;
import net.minecraft.data.DataWriter;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class ZineAtlasDefinitionProvider extends AtlasDefinitionProvider {

    public ZineAtlasDefinitionProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public final CompletableFuture<?> run(DataWriter writer) {
        List<CompletableFuture<?>> futures = new ArrayList<>();
        this.generate((atlasId, sources) -> futures.add(this.runForAtlas(writer, atlasId, sources)));
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    protected abstract void generate(Output output);

    public interface Output {
        void accept(Identifier atlasId, List<AtlasSource> sources);
    }
}
