package com.eightsidedsquare.zine.client.materialmapping;

import com.eightsidedsquare.zine.client.util.ZineClientUtil;
import com.eightsidedsquare.zine.common.util.ZineUtil;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.fabricmc.fabric.api.client.renderer.v1.Renderer;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.MutableMesh;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadEmitter;
import net.minecraft.client.renderer.block.dispatch.ModelState;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.cuboid.CuboidFace;
import net.minecraft.client.resources.model.cuboid.CuboidModelElement;
import net.minecraft.client.resources.model.cuboid.FaceBakery;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;

public interface MappableModel {
    MappableModel EMPTY = (_, _) -> {};

    void outputTo(QuadEmitter emitter, MaterialMapping.@Nullable Baked mapping);

    default Material.@Nullable Baked particleMaterial(MaterialMapping.@Nullable Baked mapping) {
        return mapping == null ? null : mapping.mappings().values().iterator().next();
    }

    static MappableModel bake(List<CuboidModelElement> elements, ModelBaker modelBaker, ModelState modelState) {
        return bake(elements, modelBaker.parts(), modelBaker.zine$getMissingSprite(), modelState);
    }

    static MappableModel bake(List<CuboidModelElement> elements, ModelBaker.Interner interner, TextureAtlasSprite sprite, ModelState modelState) {
        Map<String, Pair<MutableMesh, QuadEmitter>> builders = new Object2ObjectArrayMap<>();
        for (CuboidModelElement element : elements) {
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
                float u0 = sprite.getU0();
                float u1 = sprite.getU1();
                float v0 = sprite.getV0();
                float v1 = sprite.getV1();
                for (Map.Entry<Direction, CuboidFace> entry : element.faces().entrySet()) {
                    Direction facing = entry.getKey();
                    CuboidFace face = entry.getValue();
                    if (facing.getAxis().choose(drawXFaces, drawYFaces, drawZFaces)) {
                        BakedQuad quad = FaceBakery.bakeQuad(
                                interner,
                                from,
                                to,
                                face.uvs(),
                                face.rotation(),
                                face.tintIndex(),
                                null,
                                facing,
                                modelState,
                                element.rotation(),
                                element.shade(),
                                element.lightEmission()
                        );
                        QuadEmitter emitter = builders.computeIfAbsent(face.texture(), name -> {
                            MutableMesh builder = Renderer.get().mutableMesh();
                            return Pair.of(builder, builder.emitter());
                        }).getSecond();
                        emitter.fromBakedQuad(quad);
                        if(face.cullForDirection() == null) {
                            emitter.cullFace(null);
                        }else {
                            emitter.cullFace(Direction.rotate(modelState.transformation().getMatrix(), face.cullForDirection()));
                        }
                        for (int i = 0; i < 4; i++) {
                            emitter.uv(i, Mth.inverseLerp(emitter.u(i), u0, u1), Mth.inverseLerp(emitter.v(i), v0, v1));
                        }
                        emitter.emit();
                    }
                }
            }
        }
        return new MappableModelImpl(ZineUtil.mapValues(builders, pair -> ZineClientUtil.bake(pair.getFirst())));
    }

    interface Unbaked {
        Unbaked EMPTY = (_, _) -> MappableModel.EMPTY;

        MappableModel bake(ModelBaker modelBaker, ModelState modelState);
    }
}
