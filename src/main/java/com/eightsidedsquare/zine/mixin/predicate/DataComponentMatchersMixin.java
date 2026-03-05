package com.eightsidedsquare.zine.mixin.predicate;

import com.eightsidedsquare.zine.common.predicate.ZineDataComponentMatchers;
import com.eightsidedsquare.zine.common.util.ZineUtil;
import net.minecraft.advancements.criterion.DataComponentMatchers;
import net.minecraft.core.component.DataComponentExactPredicate;
import net.minecraft.core.component.predicates.DataComponentPredicate;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(DataComponentMatchers.class)
public abstract class DataComponentMatchersMixin implements ZineDataComponentMatchers {

    @Shadow @Final @Mutable
    private DataComponentExactPredicate exact;

    @Shadow @Final @Mutable
    private Map<DataComponentPredicate.Type<?>, DataComponentPredicate> partial;

    @Override
    public void zine$setExact(DataComponentExactPredicate exact) {
        this.exact = exact;
    }

    @Override
    public void zine$setPartial(Map<DataComponentPredicate.Type<?>, DataComponentPredicate> partial) {
        this.partial = partial;
    }

    @Override
    public void zine$addPartial(DataComponentPredicate.Type<?> type, DataComponentPredicate predicate) {
        this.partial = ZineUtil.putOrUnfreeze(this.partial, type, predicate);
    }
}
