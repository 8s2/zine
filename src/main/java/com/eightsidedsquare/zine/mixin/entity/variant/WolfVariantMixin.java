package com.eightsidedsquare.zine.mixin.entity.variant;

import com.eightsidedsquare.zine.common.entity.variant.ZineWolfVariant;
import com.eightsidedsquare.zine.common.entity.variant.ZineWolfVariantWolfAssetInfo;
import net.minecraft.entity.passive.WolfVariant;
import net.minecraft.entity.spawn.SpawnConditionSelectors;
import net.minecraft.util.AssetInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WolfVariant.class)
public abstract class WolfVariantMixin implements ZineWolfVariant, ZineWolfVariantWolfAssetInfo {

    @Shadow @Final @Mutable
    private WolfVariant.WolfAssetInfo assetInfo;

    @Shadow @Final @Mutable
    private SpawnConditionSelectors spawnConditions;

    @Override
    public void zine$setAssetInfo(WolfVariant.WolfAssetInfo assetInfo) {
        this.assetInfo = assetInfo;
    }

    @Override
    public void zine$setSpawnConditions(SpawnConditionSelectors spawnConditions) {
        this.spawnConditions = spawnConditions;
    }

    @Override
    public void zine$setWild(AssetInfo.TextureAssetInfo wild) {
        this.assetInfo.zine$setWild(wild);
    }

    @Override
    public void zine$setTame(AssetInfo.TextureAssetInfo tame) {
        this.assetInfo.zine$setTame(tame);
    }

    @Override
    public void zine$setAngry(AssetInfo.TextureAssetInfo angry) {
        this.assetInfo.zine$setAngry(angry);
    }
}
