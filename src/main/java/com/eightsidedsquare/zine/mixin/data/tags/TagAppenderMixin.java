package com.eightsidedsquare.zine.mixin.data.tags;

import com.eightsidedsquare.zine.data.tags.ZineTagAppender;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagAppender;
import net.minecraft.data.tags.TagAppender;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TagAppender.class)
public interface TagAppenderMixin<T> extends ZineTagAppender<T>, FabricTagAppender<T> {
}
