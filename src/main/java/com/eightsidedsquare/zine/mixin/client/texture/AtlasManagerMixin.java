package com.eightsidedsquare.zine.mixin.client.texture;

import com.eightsidedsquare.zine.client.materialmapping.MaterialMappingLoader;
import net.minecraft.client.resources.model.AtlasManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AtlasManager.class)
public abstract class AtlasManagerMixin {

    @Inject(method = "updateSpriteMaps", at = @At("HEAD"))
    private void zine$clearMaterialMappings(AtlasManager.PendingStitchResults pendingStitches, CallbackInfo ci) {
        MaterialMappingLoader.clear();
    }

}
