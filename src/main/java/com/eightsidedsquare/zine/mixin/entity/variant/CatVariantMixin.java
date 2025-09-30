package com.eightsidedsquare.zine.mixin.entity.variant;

import com.eightsidedsquare.zine.common.entity.variant.ZineMobVariant;
import net.minecraft.entity.passive.CatVariant;
import net.minecraft.entity.spawn.SpawnConditionSelectors;
import net.minecraft.util.AssetInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CatVariant.class)
public abstract class CatVariantMixin implements ZineMobVariant {

    @Shadow @Final @Mutable
    private AssetInfo.TextureAssetInfo assetInfo;

    @Shadow @Final @Mutable
    private SpawnConditionSelectors spawnConditions;

    @Override
    public void zine$setAssetInfo(AssetInfo.TextureAssetInfo assetInfo) {
        this.assetInfo = assetInfo;
    }

    @Override
    public void zine$setSpawnConditions(SpawnConditionSelectors spawnConditions) {
        this.spawnConditions = spawnConditions;
    }
}
