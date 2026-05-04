package com.eightsidedsquare.zine.mixin.level;

import com.eightsidedsquare.zine.common.level.ZineLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LevelAccessor.class)
public interface LevelAccessorMixin extends ZineLevelAccessor {
}
