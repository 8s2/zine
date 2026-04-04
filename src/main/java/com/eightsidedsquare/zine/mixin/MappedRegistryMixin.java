package com.eightsidedsquare.zine.mixin;

import com.eightsidedsquare.zine.common.registry.FreezeRegistriesEventsImpl;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MappedRegistry.class)
public abstract class MappedRegistryMixin implements WritableRegistry<Object> {

    @Inject(method = "freeze", at = @At(value = "FIELD", target = "Lnet/minecraft/core/MappedRegistry;frozen:Z", ordinal = 1))
    private void zine$beforeFreeze(CallbackInfoReturnable<Registry<?>> cir) {
        FreezeRegistriesEventsImpl.apply(true, this);
    }

    @Inject(method = "freeze", at = @At(value = "RETURN", ordinal = 1))
    private void zine$afterFreeze(CallbackInfoReturnable<Registry<?>> cir) {
        FreezeRegistriesEventsImpl.apply(false, this);
    }

}
