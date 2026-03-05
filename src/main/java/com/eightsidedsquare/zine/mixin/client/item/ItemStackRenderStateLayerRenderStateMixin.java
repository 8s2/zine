package com.eightsidedsquare.zine.mixin.client.item;

import com.eightsidedsquare.zine.client.item.ZineItemStackRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStackRenderState.LayerRenderState.class)
public abstract class ItemStackRenderStateLayerRenderStateMixin implements ZineItemStackRenderState.ZineLayerRenderState {

    @Unique
    @Nullable
    private Consumer<PoseStack> matrixTransformation;
    @Unique
    private boolean transformBeforeDisplayTransforms;

    @Override
    public void zine$setMatrixTransformation(boolean beforeDisplayTransforms, Consumer<PoseStack> matrixTransformation) {
        this.transformBeforeDisplayTransforms = beforeDisplayTransforms;
        this.matrixTransformation = matrixTransformation;
    }

    @Inject(method = "submit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/model/ItemTransform;apply(ZLcom/mojang/blaze3d/vertex/PoseStack$Pose;)V"))
    private void zine$applyMatrixTransformationBefore(PoseStack matrices, SubmitNodeCollector queue, int light, int overlay, int i, CallbackInfo ci) {
        if(this.transformBeforeDisplayTransforms && this.matrixTransformation != null) {
            this.matrixTransformation.accept(matrices);
        }
    }

    @Inject(method = "submit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/model/ItemTransform;apply(ZLcom/mojang/blaze3d/vertex/PoseStack$Pose;)V", shift = At.Shift.AFTER))
    private void zine$applyMatrixTransformationAfter(PoseStack matrices, SubmitNodeCollector queue, int light, int overlay, int i, CallbackInfo ci) {
        if(!this.transformBeforeDisplayTransforms && this.matrixTransformation != null) {
            this.matrixTransformation.accept(matrices);
        }
    }

    @Inject(method = "clear", at = @At("TAIL"))
    private void zine$clear(CallbackInfo ci) {
        this.matrixTransformation = null;
        this.transformBeforeDisplayTransforms = false;
    }
}
