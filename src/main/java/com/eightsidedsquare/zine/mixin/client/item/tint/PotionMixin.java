package com.eightsidedsquare.zine.mixin.client.item.tint;

import com.eightsidedsquare.zine.client.item.tint.ZinePotion;
import net.minecraft.client.color.item.Potion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Potion.class)
public abstract class PotionMixin implements ZinePotion {

    @Shadow @Final @Mutable
    private int defaultColor;

    @Override
    public void zine$setDefaultColor(int defaultColor) {
        this.defaultColor = defaultColor;
    }
}
