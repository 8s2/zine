package com.eightsidedsquare.zine.mixin.structure;

import com.eightsidedsquare.zine.common.world.structure.ZineFeaturePoolElement;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.pools.FeaturePoolElement;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FeaturePoolElement.class)
public abstract class FeaturePoolElementMixin implements ZineFeaturePoolElement {

    @Shadow @Final @Mutable
    private Holder<PlacedFeature> feature;

    @Shadow @Final @Mutable
    private CompoundTag defaultJigsawNBT;

    @Override
    public Holder<PlacedFeature> zine$getFeature() {
        return this.feature;
    }

    @Override
    public void zine$setFeature(Holder<PlacedFeature> feature) {
        this.feature = feature;
    }

    @Override
    public CompoundTag zine$getNbt() {
        return this.defaultJigsawNBT;
    }

    @Override
    public void zine$setNbt(CompoundTag nbt) {
        this.defaultJigsawNBT = nbt;
    }
}
