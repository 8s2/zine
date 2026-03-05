package com.eightsidedsquare.zine.client.materialmapping;

import com.eightsidedsquare.zine.client.util.EmptyMesh;
import com.eightsidedsquare.zine.client.util.ZineClientUtil;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.client.renderer.v1.Renderer;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.MutableMesh;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadEmitter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.CacheSlot;
import net.minecraft.client.multiplayer.ClientLevel;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.function.BiConsumer;

public class MeshCache<K> {
    private final CacheSlot<ClientLevel, Map<K, Mesh>> cache = new CacheSlot<>(_ -> new Object2ObjectOpenHashMap<>());
    private final BiConsumer<K, Callback> callback;

    public MeshCache(BiConsumer<K, Callback> callback) {
        this.callback = callback;
    }

    public Mesh get(K key) {
        ClientLevel level = Minecraft.getInstance().level;
        return level == null || !MaterialMappingLoader.hasMappings() ? EmptyMesh.INSTANCE : this.cache.compute(level).computeIfAbsent(key, this::createMesh);
    }

    private Mesh createMesh(K key) {
        MutableMesh builder = Renderer.get().mutableMesh();
        QuadEmitter emitter = builder.emitter();
        this.callback.accept(key, (mappableModel, spriteMapping) ->
                mappableModel.outputTo(emitter, spriteMapping)
        );
        return ZineClientUtil.bake(builder);
    }

    public interface Callback {
        void accept(MappableModel mappableModel, MaterialMapping.@Nullable Baked spriteMapping);
    }
}
