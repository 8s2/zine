package com.eightsidedsquare.zinetest.client;

import com.mojang.math.Axis;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

public class TransformedItemModel implements ItemModel {

    @Override
    public void update(ItemStackRenderState state, ItemStack stack, ItemModelResolver resolver, ItemDisplayContext displayContext, @Nullable ClientLevel world, @Nullable ItemOwner heldItemContext, int seed) {
        state.appendModelIdentityElement(this);
        ItemStack itemStack = Items.MACE.getDefaultInstance();
        resolver.appendItemLayers(state, itemStack, displayContext, world, heldItemContext, seed);
        state.setAnimated();
        if(heldItemContext == null) {
            return;
        }
        LivingEntity user = heldItemContext.asLivingEntity();
        if(user == null) {
            return;
        }
        state.zine$getLastLayer().zine$setMatrixTransformation(matrices -> {
            float t = 0.25f * (user.tickCount + Minecraft.getInstance().zine$getTickProgress());
            matrices.translate(0.5f, 0.5f, 0);
            matrices.mulPose(Axis.ZN.rotation(t));
            matrices.translate(-0.5f, -0.5f, 0);
        });
    }

    public record Unbaked() implements net.minecraft.client.renderer.item.ItemModel.Unbaked {

        public static final MapCodec<Unbaked> CODEC = MapCodec.unit(new Unbaked());

        @Override
        public MapCodec<? extends net.minecraft.client.renderer.item.ItemModel.Unbaked> type() {
            return CODEC;
        }

        @Override
        public ItemModel bake(BakingContext context) {
            return new TransformedItemModel();
        }

        @Override
        public void resolveDependencies(Resolver resolver) {

        }
    }
}
