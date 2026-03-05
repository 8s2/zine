package com.eightsidedsquare.zine.mixin.client;

import com.eightsidedsquare.zine.client.util.ZineMinecraft;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin implements ZineMinecraft {

    @Shadow @Final private DeltaTracker.Timer deltaTracker;

    @Override
    public float zine$getTickProgress(boolean ignoreFreeze) {
        return this.deltaTracker.getGameTimeDeltaPartialTick(ignoreFreeze);
    }

    @Override
    public float zine$getFixedDeltaTicks() {
        return this.deltaTracker.getRealtimeDeltaTicks();
    }

    @Override
    public float zine$getDynamicDeltaTicks() {
        return this.deltaTracker.getGameTimeDeltaTicks();
    }
}
