package com.eightsidedsquare.zine.mixin.block.entity;

import com.eightsidedsquare.zine.common.block.entity.ZineBlockEntity;
import com.eightsidedsquare.zine.common.util.codec.DataHelper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin implements ZineBlockEntity {
    @Inject(method = "saveAdditional", at = @At("HEAD"))
    private void zine$saveAdditional(ValueOutput output, CallbackInfo ci) {
        DataHelper<? extends BlockEntity> dataHelper = this.zine$dataHelper();
        if (dataHelper != null) {
            dataHelper.writeUnchecked(output, this);
        }
    }

    @Inject(method = "loadAdditional", at = @At("HEAD"))
    private void zine$loadAdditional(ValueInput input, CallbackInfo ci) {
        DataHelper<? extends BlockEntity> dataHelper = this.zine$dataHelper();
        if (dataHelper != null) {
            dataHelper.readUnchecked(input, this);
        }
    }
}
