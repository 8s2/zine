package com.eightsidedsquare.zine.mixin.client.item;

import com.eightsidedsquare.zine.client.item.ZineItemRenderStateLayerRenderState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemRenderState.LayerRenderState.class)
public abstract class ItemRenderStateLayerRenderStateMixin implements ZineItemRenderStateLayerRenderState {

    @Unique
    @Nullable
    private Consumer<MatrixStack> matrixTransformation;
    @Unique
    private boolean transformBeforeDisplayTransforms;

    @Override
    public void zine$setMatrixTransformation(boolean beforeDisplayTransforms, Consumer<MatrixStack> matrixTransformation) {
        this.transformBeforeDisplayTransforms = beforeDisplayTransforms;
        this.matrixTransformation = matrixTransformation;
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/json/Transformation;apply(ZLnet/minecraft/client/util/math/MatrixStack$Entry;)V"))
    private void zine$applyMatrixTransformationBefore(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, int overlay, int i, CallbackInfo ci) {
        if(this.transformBeforeDisplayTransforms && this.matrixTransformation != null) {
            this.matrixTransformation.accept(matrices);
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/json/Transformation;apply(ZLnet/minecraft/client/util/math/MatrixStack$Entry;)V", shift = At.Shift.AFTER))
    private void zine$applyMatrixTransformationAfter(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, int overlay, int i, CallbackInfo ci) {
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
