package com.eightsidedsquare.zine.client.materialmapping;

import com.eightsidedsquare.zine.client.util.ZineClientUtil;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.client.renderer.v1.Renderer;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.MutableMesh;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadEmitter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.CacheSlot;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.sprite.Material;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class MeshCache<K> {
    private final MaterialMappingStorage mappings;
    private final Material.Baked missingMaterial;
    private final Callback<K> callback;
    private final CacheSlot<ClientLevel, Map<K, MappedMesh>> cache = new CacheSlot<>(_ -> new Object2ObjectOpenHashMap<>());

    public MeshCache(MaterialMappingStorage mappings, Material.Baked missingMaterial, Callback<K> callback) {
        this.mappings = mappings;
        this.missingMaterial = missingMaterial;
        this.callback = callback;
    }

    @Nullable
    public MappedMesh get(K key) {
        ClientLevel level = Minecraft.getInstance().level;
        return level == null ? null : this.cache.compute(level).computeIfAbsent(key, this::compute);
    }

    public Material.Baked particleMaterial(K key) {
        MappedMesh mesh = this.get(key);
        return mesh == null ? this.missingMaterial : mesh.particleMaterial();
    }

    public void output(K key, QuadEmitter emitter) {
        MappedMesh mesh = this.get(key);
        if(mesh != null) {
            mesh.outputTo(emitter);
        }
    }

    public void output(K key, ItemStackRenderState.LayerRenderState layerState) {
        MappedMesh mesh = this.get(key);
        if(mesh != null) {
            mesh.outputTo(layerState);
        }
    }

    private MappedMesh compute(K key) {
        MutableMesh builder = Renderer.get().mutableMesh();
        QuadEmitter emitter = builder.emitter();
        AtomicReference<Material.Baked> particleMaterial = new AtomicReference<>(this.missingMaterial);
        this.callback.accept(key, this.mappings, (mappableModel, spriteMapping) -> {
            mappableModel.outputTo(emitter, spriteMapping);
            if(spriteMapping != null) {
                particleMaterial.set(spriteMapping.getParticleMaterial(this.missingMaterial));
            }
        });
        Mesh mesh = ZineClientUtil.bake(builder);
        AtomicBoolean translucent = new AtomicBoolean(false);
        AtomicBoolean animated = new AtomicBoolean(false);
        mesh.forEach(quad -> {
            if(quad.chunkLayer().translucent()) {
                translucent.set(true);
            }
            if(quad.animated()) {
                animated.set(true);
            }
        });
        return new MappedMesh(
                mesh,
                particleMaterial.get(),
                (translucent.get() ? 1 : 0) | (animated.get() ? 2 : 0)
        );
    }

    @FunctionalInterface
    public interface Output {
        void accept(MappableModel mappableModel, MaterialMapping.@Nullable Baked spriteMapping);
    }

    @FunctionalInterface
    public interface Callback<K> {
        void accept(K key, MaterialMappingStorage storage, Output output);
    }
}
