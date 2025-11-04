package com.eightsidedsquare.zine.mixin.text;

import com.eightsidedsquare.zine.common.text.ZineText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Text.class)
public interface TextMixin extends ZineText {
}
