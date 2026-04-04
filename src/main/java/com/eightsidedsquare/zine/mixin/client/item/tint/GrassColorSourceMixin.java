package com.eightsidedsquare.zine.mixin.client.item.tint;

import com.eightsidedsquare.zine.client.item.tint.ZineGrassColorSource;
import net.minecraft.client.color.item.GrassColorSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GrassColorSource.class)
public abstract class GrassColorSourceMixin implements ZineGrassColorSource {

    @Shadow @Final @Mutable
    private float downfall;

    @Shadow @Final @Mutable
    private float temperature;

    @Override
    public void zine$setTemperature(float temperature) {
        this.temperature = temperature;
    }

    @Override
    public void zine$setDownfall(float downfall) {
        this.downfall = downfall;
    }
}
