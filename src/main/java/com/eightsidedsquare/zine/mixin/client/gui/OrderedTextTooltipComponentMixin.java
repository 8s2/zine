package com.eightsidedsquare.zine.mixin.client.gui;

import net.minecraft.client.gui.tooltip.OrderedTextTooltipComponent;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(OrderedTextTooltipComponent.class)
public abstract class OrderedTextTooltipComponentMixin implements TooltipComponentMixin {

    @Shadow @Final private OrderedText text;

    @Override
    public void zine$appendSearchableText(List<Text> texts) {
        texts.add(this.text.zine$toText());
    }
}
