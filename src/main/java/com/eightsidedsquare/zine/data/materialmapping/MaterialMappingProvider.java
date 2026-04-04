package com.eightsidedsquare.zine.data.materialmapping;

import com.eightsidedsquare.zine.client.materialmapping.MaterialMapping;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class MaterialMappingProvider extends FabricCodecDataProvider<MaterialMapping.UnbakedSet> {
    protected Output output;
    protected HolderLookup.Provider lookup;

    public MaterialMappingProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(
                output,
                registriesFuture,
                PackOutput.Target.RESOURCE_PACK,
                "material_mapping",
                MaterialMapping.UnbakedSet.CODEC
        );
    }

    protected abstract void generate();

    @Override
    protected final void configure(BiConsumer<Identifier, MaterialMapping.UnbakedSet> provider, HolderLookup.Provider lookup) {
        Map<Identifier, MaterialMapping.UnbakedSet.Builder> map = new Object2ObjectOpenHashMap<>();
        this.output = (id, textureSetBuilder) -> map.computeIfAbsent(id, _ -> MaterialMapping.UnbakedSet.builder()).addAll(textureSetBuilder);
        this.lookup = lookup;

        this.generate();

        map.forEach((id, textureSetBuilder) -> provider.accept(id, textureSetBuilder.build()));
    }

    @Override
    public String getName() {
        return "Material Mappings";
    }

    public interface Output {
        void accept(Identifier id, MaterialMapping.UnbakedSet.Builder textureSetBuilder);

        default void accept(ResourceKey<?> key, MaterialMapping.UnbakedSet.Builder textureSetBuilder) {
            this.accept(key.identifier().withPrefix(key.registry().getPath() + "/"), textureSetBuilder);
        }
    }
}
