package com.eightsidedsquare.zine.common.block.entity;

import com.eightsidedsquare.zine.common.util.codec.DataHelper;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jspecify.annotations.Nullable;

public interface ZineBlockEntity {
    @Nullable
    default DataHelper<? extends BlockEntity> zine$dataHelper() {
        return null;
    }
}
