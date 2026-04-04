package com.eightsidedsquare.zine.data.model;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class ZineEquipmentAssetProvider extends FabricCodecDataProvider<EquipmentClientInfo> {

    public ZineEquipmentAssetProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture, PackOutput.Target.RESOURCE_PACK, "equipment", EquipmentClientInfo.CODEC);
    }

    protected abstract void bootstrap(Output output);

    @Override
    protected final void configure(BiConsumer<Identifier, EquipmentClientInfo> provider, HolderLookup.Provider registryLookup) {
        this.bootstrap((key, info) -> provider.accept(key.identifier(), info));
    }

    @FunctionalInterface
    public interface Output {
        void accept(ResourceKey<EquipmentAsset> key, EquipmentClientInfo info);

        default void accept(ResourceKey<EquipmentAsset> key, EquipmentClientInfo.Builder infoBuilder) {
            this.accept(key, infoBuilder.build());
        }
    }
}
