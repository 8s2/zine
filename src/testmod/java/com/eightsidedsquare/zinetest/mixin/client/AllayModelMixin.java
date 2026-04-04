package com.eightsidedsquare.zinetest.mixin.client;

import net.minecraft.client.model.animal.allay.AllayModel;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AllayModel.class)
public abstract class AllayModelMixin extends ModelMixin {

//    @Inject(method = "<init>", at = @At("TAIL"))
//    private void zinetest$init(ModelPart modelPart, CallbackInfo ci) {
//        this.layerFactory = TestmodClient.CUSTOM_ENTITY;
//    }

}
