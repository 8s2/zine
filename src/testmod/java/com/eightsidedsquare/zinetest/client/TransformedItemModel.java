package com.eightsidedsquare.zinetest.client;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.HeldItemContext;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.Nullable;

public class TransformedItemModel implements ItemModel {

    @Override
    public void update(ItemRenderState state, ItemStack stack, ItemModelManager resolver, ItemDisplayContext displayContext, @Nullable ClientWorld world, @Nullable HeldItemContext heldItemContext, int seed) {
        state.addModelKey(this);
        ItemStack itemStack = Items.MACE.getDefaultStack();
        resolver.update(state, itemStack, displayContext, world, heldItemContext, seed);
        state.markAnimated();
        if(heldItemContext == null) {
            return;
        }
        LivingEntity user = heldItemContext.getEntity();
        if(user == null) {
            return;
        }
        state.zine$getLastLayer().zine$setMatrixTransformation(matrices -> {
            float t = 0.25f * (user.age + MinecraftClient.getInstance().zine$getTickProgress());
            matrices.translate(0.5f, 0.5f, 0);
            matrices.multiply(RotationAxis.NEGATIVE_Z.rotation(t));
            matrices.translate(-0.5f, -0.5f, 0);
        });
    }

    public record Unbaked() implements ItemModel.Unbaked {

        public static final MapCodec<Unbaked> CODEC = MapCodec.unit(new Unbaked());

        @Override
        public MapCodec<? extends ItemModel.Unbaked> getCodec() {
            return CODEC;
        }

        @Override
        public ItemModel bake(BakeContext context) {
            return new TransformedItemModel();
        }

        @Override
        public void resolve(Resolver resolver) {

        }
    }
}
