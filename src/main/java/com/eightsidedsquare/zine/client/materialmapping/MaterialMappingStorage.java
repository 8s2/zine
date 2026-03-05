package com.eightsidedsquare.zine.client.materialmapping;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.CacheSlot;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.model.sprite.MaterialBaker;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;

import java.util.Map;

public final class MaterialMappingStorage {

    public static final MaterialMappingStorage EMPTY = new MaterialMappingStorage(Map.of());
    private final Map<Identifier, MaterialMapping.BakedSet> mappings;
    private final CacheSlot<ClientLevel, Map<Holder<?>, MaterialMapping.BakedSet>> mappingCache = new CacheSlot<>(_ -> new Reference2ObjectOpenHashMap<>());

    public MaterialMappingStorage(Map<Identifier, MaterialMapping.BakedSet> mappings) {
        this.mappings = mappings;
    }

    public MaterialMapping.BakedSet get(Holder<?> holder) {
        ClientLevel level = Minecraft.getInstance().level;
        return level == null ? MaterialMapping.BakedSet.EMPTY : this.mappingCache.compute(level).computeIfAbsent(holder, this::computeMapping);
    }

    public MaterialMapping.BakedSet get(Identifier id) {
        return this.mappings.getOrDefault(id, MaterialMapping.BakedSet.EMPTY);
    }

    private MaterialMapping.BakedSet computeMapping(Holder<?> holder) {
        return holder.unwrapKey()
                .map(key -> this.get(key.identifier().withPrefix(key.registry().getPath() + "/")))
                .orElse(MaterialMapping.BakedSet.EMPTY);
    }

    public static MaterialMappingStorage bake(Map<Identifier, MaterialMapping.UnbakedSet> mappings, MaterialBaker materials) {
        Map<Identifier, MaterialMapping.BakedSet> bakedMappings = new Object2ObjectOpenHashMap<>();
        mappings.forEach((id, mapping) -> bakedMappings.put(id, mapping.bake(materials::zine$get)));
        return new MaterialMappingStorage(bakedMappings);
    }

}
