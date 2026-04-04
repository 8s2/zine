package com.eightsidedsquare.zinetest.common.block.entity;

import com.eightsidedsquare.zine.common.block_entity.SyncingBlockEntity;
import com.eightsidedsquare.zinetest.core.TestmodBlockEntities;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.animal.chicken.ChickenVariant;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

public class NestBlockEntity extends SyncingBlockEntity {

    private static final Codec<Holder<ChickenVariant>> CODEC = DataComponents.CHICKEN_VARIANT.codec();
    @Nullable
    private Holder<ChickenVariant> variant;

    public NestBlockEntity(BlockPos pos, BlockState state) {
        super(TestmodBlockEntities.NEST, pos, state);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.storeNullable("variant", CODEC, this.variant);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.variant = input.read("variant", CODEC).orElse(null);
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter components) {
        super.applyImplicitComponents(components);
        this.variant = components.get(DataComponents.CHICKEN_VARIANT);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        if(this.variant != null) {
            components.set(DataComponents.CHICKEN_VARIANT, this.variant);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void removeComponentsFromTag(ValueOutput output) {
        output.discard("variant");
    }

    @Override
    public @Nullable Object getRenderData() {
        return this.variant;
    }
}
