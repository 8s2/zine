package com.eightsidedsquare.zine.mixin.predicate;

import com.eightsidedsquare.zine.common.predicate.ZineEntityTypePredicate;
import com.eightsidedsquare.zine.common.util.ZineUtil;
import net.minecraft.advancements.criterion.EntityTypePredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.function.Function;

@Mixin(EntityTypePredicate.class)
public abstract class EntityTypePredicateMixin implements ZineEntityTypePredicate {

    @Shadow @Final @Mutable
    private HolderSet<EntityType<?>> types;

    @Override
    public void zine$setTypes(HolderSet<EntityType<?>> types) {
        this.types = types;
    }

    @Override
    public void zine$addType(EntityType<?> type) {
        this.types = ZineUtil.mergeValue(this.types, BuiltInRegistries.ENTITY_TYPE::wrapAsHolder, type);
    }

    @Override
    public void zine$addType(Holder<EntityType<?>> type) {
        this.types = ZineUtil.mergeValue(this.types, Function.identity(), type);
    }

    @Override
    public void zine$addTypes(HolderSet<EntityType<?>> types) {
        this.types = ZineUtil.mergeValues(this.types, types);
    }

    @Override
    public void zine$addTypes(Collection<EntityType<?>> types) {
        this.types= ZineUtil.mergeValues(this.types, BuiltInRegistries.ENTITY_TYPE::wrapAsHolder, types);
    }
}
