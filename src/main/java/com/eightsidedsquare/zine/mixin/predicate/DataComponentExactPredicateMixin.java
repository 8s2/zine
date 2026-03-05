package com.eightsidedsquare.zine.mixin.predicate;

import com.eightsidedsquare.zine.common.predicate.ZineDataComponentExactPredicate;
import com.eightsidedsquare.zine.common.util.ZineUtil;
import net.minecraft.core.component.DataComponentExactPredicate;
import net.minecraft.core.component.TypedDataComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(DataComponentExactPredicate.class)
public abstract class DataComponentExactPredicateMixin implements ZineDataComponentExactPredicate {

    @Shadow @Final @Mutable
    private List<TypedDataComponent<?>> expectedComponents;

    @Override
    public void zine$setComponents(List<TypedDataComponent<?>> components) {
        this.expectedComponents = components;
    }

    @Override
    public void zine$addComponent(TypedDataComponent<?> component) {
        this.expectedComponents = ZineUtil.addOrUnfreeze(this.expectedComponents, component);
    }

    @Override
    public void zine$addComponents(List<TypedDataComponent<?>> components) {
        this.expectedComponents = ZineUtil.addAllOrUnfreeze(this.expectedComponents, components);
    }
}
