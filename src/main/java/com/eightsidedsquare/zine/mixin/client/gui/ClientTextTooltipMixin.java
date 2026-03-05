package com.eightsidedsquare.zine.mixin.client.gui;

import net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(ClientTextTooltip.class)
public abstract class ClientTextTooltipMixin implements ClientTooltipComponentMixin {

    @Shadow @Final private FormattedCharSequence text;

    @Override
    public void zine$appendSearchableText(List<Component> texts) {
        texts.add(this.text.zine$toText());
    }
}
