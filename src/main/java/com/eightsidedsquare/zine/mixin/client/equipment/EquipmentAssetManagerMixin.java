package com.eightsidedsquare.zine.mixin.client.equipment;

import com.eightsidedsquare.zine.client.equipment.EquipmentAssetEvents;
import net.minecraft.client.resources.model.EquipmentAssetManager;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.stream.Collectors;

@Mixin(EquipmentAssetManager.class)
public abstract class EquipmentAssetManagerMixin {
    @Shadow
    private Map<ResourceKey<EquipmentAsset>, EquipmentClientInfo> equipmentAssets;

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At("TAIL"))
    private void zine$apply(Map<Identifier, EquipmentClientInfo> preparations, ResourceManager manager, ProfilerFiller profiler, CallbackInfo ci) {
        this.equipmentAssets = this.equipmentAssets.entrySet()
                .stream()
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, EquipmentAssetManagerMixin::zine$modify));
    }

    @Unique
    private static EquipmentClientInfo zine$modify(Map.Entry<ResourceKey<EquipmentAsset>, EquipmentClientInfo> entry) {
        EquipmentClientInfo equipmentInfo = entry.getValue();
        EquipmentClientInfo.Builder builder = EquipmentClientInfo.builder();
        builder.layersByType.putAll(equipmentInfo.layers());
        builder.trimOverrides.addAll(equipmentInfo.trimOverrides());
        EquipmentAssetEvents.MODIFY.invoker().modify(entry.getKey(), builder);
        return builder.build();
    }
}
