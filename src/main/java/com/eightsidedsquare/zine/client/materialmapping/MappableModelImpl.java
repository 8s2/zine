package com.eightsidedsquare.zine.client.materialmapping;

import net.fabricmc.fabric.api.client.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadEmitter;
import net.minecraft.client.renderer.block.dispatch.ModelState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.cuboid.CuboidModelElement;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;

public record MappableModelImpl(Map<String, Mesh> meshes) implements MappableModel {

    @Override
    public void outputTo(QuadEmitter emitter, MaterialMapping.@Nullable Baked mapping) {
        if (mapping == null) {
            return;
        }
        for (Map.Entry<String, Mesh> entry : this.meshes.entrySet()) {
            TextureAtlasSprite sprite = mapping.get(entry.getKey());
            if (sprite == null) {
                continue;
            }
            entry.getValue().forEach(quad -> {
                emitter.copyFrom(quad);
                for (int i = 0; i < 4; i++) {
                    emitter.uv(i, sprite.getU(emitter.u(i)), sprite.getV(emitter.v(i)));
                }
                emitter.emit();
            });
        }
    }

    public record UnbakedImpl(List<CuboidModelElement> elements) implements MappableModel.Unbaked {
        @Override
        public MappableModel bake(ModelBaker modelBaker, ModelState modelState) {
            return this.elements.isEmpty() ? MappableModel.EMPTY : MappableModel.bake(this.elements, modelBaker, modelState);
        }
    }
}
