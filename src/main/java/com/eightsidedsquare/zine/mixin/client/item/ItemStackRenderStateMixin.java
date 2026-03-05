package com.eightsidedsquare.zine.mixin.client.item;

import com.eightsidedsquare.zine.client.item.ZineItemStackRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemStackRenderState.class)
public abstract class ItemStackRenderStateMixin implements ZineItemStackRenderState {

    @Shadow private ItemStackRenderState.LayerRenderState[] layers;

    @Shadow private int activeLayerCount;

    @Override
    public ItemStackRenderState.LayerRenderState[] zine$getLayers() {
        return this.layers;
    }

    @Override
    public ItemStackRenderState.LayerRenderState zine$getLastLayer() {
        return this.layers[this.activeLayerCount - 1];
    }
}
