package com.eightsidedsquare.zine.mixin.entity.spawn;

import com.eightsidedsquare.zine.common.entity.spawn.ZineMoonBrightnessCheck;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.world.entity.variant.MoonBrightnessCheck;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MoonBrightnessCheck.class)
public abstract class MoonBrightnessCheckMixin implements ZineMoonBrightnessCheck {

    @Shadow @Final @Mutable
    private MinMaxBounds.Doubles range;

    @Override
    public void zine$setRange(MinMaxBounds.Doubles range) {
        this.range = range;
    }
}
