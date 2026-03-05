package com.eightsidedsquare.zine.client.materialmapping;

import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadEmitter;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;

public final class MappableItemModel implements MappableModel {

    public static final MappableItemModel INSTANCE = new MappableItemModel();
    public static final MappableModel.Unbaked UNBAKED = (_, _) -> INSTANCE;
    private static final ModelBaker.PartCache PART_CACHE = new ModelBakery.PartCacheImpl();

    @Override
    public void outputTo(QuadEmitter emitter, MaterialMapping.@Nullable Baked mapping) {
        if(mapping == null) {
            return;
        }
        emit(emitter, mapping.mappings(), BlockModelRotation.IDENTITY, ItemModelGenerator.LAYERS);
    }

    public static void emit(QuadEmitter emitter, Map<String, TextureAtlasSprite> sprites, ModelState modelState, List<String> layers) {
        for (int layerIndex = 0; layerIndex < layers.size(); layerIndex++) {
            String textureReference = layers.get(layerIndex);
            TextureAtlasSprite sprite = sprites.get(textureReference);
            if (sprite == null) {
                break;
            }

            SpriteContents spriteContents = sprite.contents();
            for (BlockElement element : ItemModelGenerator.processFrames(layerIndex, textureReference, spriteContents)) {
                emitFace(emitter, element, sprite, modelState);
            }
        }
    }

    private static void emitFace(QuadEmitter emitter, BlockElement element, TextureAtlasSprite sprite, ModelState modelState) {
        boolean drawXFaces = true;
        boolean drawYFaces = true;
        boolean drawZFaces = true;
        Vector3fc from = element.from();
        Vector3fc to = element.to();
        if (from.x() == to.x()) {
            drawYFaces = false;
            drawZFaces = false;
        }
        if (from.y() == to.y()) {
            drawXFaces = false;
            drawZFaces = false;
        }
        if (from.z() == to.z()) {
            drawXFaces = false;
            drawYFaces = false;
        }

        if (drawXFaces || drawYFaces || drawZFaces) {
            for (Map.Entry<Direction, BlockElementFace> entry : element.faces().entrySet()) {
                Direction facing = entry.getKey();
                BlockElementFace face = entry.getValue();
                if (facing.getAxis().choose(drawXFaces, drawYFaces, drawZFaces)) {
                    BakedQuad quad = FaceBakery.bakeQuad(
                            PART_CACHE, from, to, face, sprite, facing, modelState, element.rotation(), element.shade(), element.lightEmission()
                    );
                    emitter.fromBakedQuad(quad);
                    if(face.cullForDirection() == null) {
                        emitter.cullFace(null);
                    }else {
                        emitter.cullFace(Direction.rotate(modelState.transformation().getMatrix(), face.cullForDirection()));
                    }
                    emitter.emit();
                }
            }
        }
    }

    private MappableItemModel() {
    }
}
