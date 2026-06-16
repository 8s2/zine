package com.eightsidedsquare.zinetest.common.block.entity;

import com.eightsidedsquare.zine.common.block.entity.SyncingBlockEntity;
import com.eightsidedsquare.zine.common.util.codec.DataHelper;
import com.eightsidedsquare.zinetest.core.TestmodBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.animal.chicken.ChickenVariant;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

public class NestBlockEntity extends SyncingBlockEntity {
    private static final DataHelper<NestBlockEntity> DATA_HELPER = DataHelper.<NestBlockEntity>builder()
            .nullableField(DataComponents.CHICKEN_VARIANT.codec(), DataComponents.CHICKEN_VARIANT.streamCodec(), "variant")
            .apply(NestBlockEntity::getVariant, NestBlockEntity::setVariant)
            .build();
    @Nullable
    private Holder<ChickenVariant> variant;

    public NestBlockEntity(BlockPos pos, BlockState state) {
        super(TestmodBlockEntities.NEST, pos, state);
    }

    @Override
    public @Nullable DataHelper<? extends BlockEntity> zine$dataHelper() {
        return DATA_HELPER;
    }

    public @Nullable Holder<ChickenVariant> getVariant() {
        return this.variant;
    }

    public void setVariant(@Nullable Holder<ChickenVariant> variant) {
        this.variant = variant;
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
