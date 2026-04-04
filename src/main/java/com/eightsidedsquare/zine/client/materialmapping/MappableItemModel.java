package com.eightsidedsquare.zine.client.materialmapping;

import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadEmitter;
import net.minecraft.client.renderer.block.dispatch.ModelState;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.cuboid.ItemModelGenerator;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import org.jspecify.annotations.Nullable;

public final class MappableItemModel implements MappableModel {
    public static final MappableModel.Unbaked UNBAKED = (_, modelState) -> new MappableItemModel(modelState);
    private final ModelBaker.Interner interner = new ModelBakery.InternerImpl();
    private final ModelState modelState;

    @Override
    public void outputTo(QuadEmitter emitter, MaterialMapping.@Nullable Baked mapping) {
        if(mapping != null) {
            this.emit(emitter, mapping);
        }
    }

    private void emit(QuadEmitter emitter, MaterialMapping.Baked mapping) {
        QuadCollection.Builder builder = new QuadCollection.Builder() {
            @Override
            public QuadCollection.Builder addCulledFace(Direction direction, BakedQuad quad) {
                emitter.fromBakedQuad(quad).cullFace(direction).emit();
                return this;
            }

            @Override
            public QuadCollection.Builder addUnculledFace(BakedQuad quad) {
                emitter.fromBakedQuad(quad).emit();
                return this;
            }

            @Override
            public QuadCollection.Builder addAll(QuadCollection quadCollection) {
                for (Direction direction : Direction.values()) {
                    for (BakedQuad quad : quadCollection.getQuads(direction)) {
                        this.addCulledFace(direction, quad);
                    }
                }
                for (BakedQuad quad : quadCollection.getQuads(null)) {
                    this.addUnculledFace(quad);
                }
                return this;
            }
        };

        for (int layerIndex = 0; layerIndex < ItemModelGenerator.LAYERS.size(); layerIndex++) {
            Material.Baked material = mapping.get(ItemModelGenerator.LAYERS.get(layerIndex));
            if(material == null) {
                continue;
            }
            BakedQuad.MaterialInfo materialInfo = this.interner.materialInfo(
                    BakedQuad.MaterialInfo.of(material, material.sprite().transparency(), layerIndex, true, 0)
            );
            ItemModelGenerator.bakeExtrudedSprite(builder, this.interner, this.modelState, materialInfo);
        }
    }

    private MappableItemModel(ModelState modelState) {
        this.modelState = modelState;
    }
}
