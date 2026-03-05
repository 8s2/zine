package com.eightsidedsquare.zine.mixin.predicate;

import com.eightsidedsquare.zine.common.predicate.ZineDamagePredicate;
import net.minecraft.advancements.criterion.DamagePredicate;
import net.minecraft.advancements.criterion.DamageSourcePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(DamagePredicate.class)
public abstract class DamagePredicateMixin implements ZineDamagePredicate {

    @Shadow @Final @Mutable
    private MinMaxBounds.Doubles dealtDamage;

    @Shadow @Final @Mutable
    private MinMaxBounds.Doubles takenDamage;

    @Shadow @Final @Mutable
    private Optional<EntityPredicate> sourceEntity;

    @Shadow @Final @Mutable
    private Optional<Boolean> blocked;

    @Shadow @Final @Mutable
    private Optional<DamageSourcePredicate> type;

    @Override
    public void zine$setDealt(MinMaxBounds.Doubles dealt) {
        this.dealtDamage = dealt;
    }

    @Override
    public void zine$setTaken(MinMaxBounds.Doubles taken) {
        this.takenDamage = taken;
    }

    @Override
    public void zine$setSourceEntity(@Nullable EntityPredicate sourceEntity) {
        this.sourceEntity = Optional.ofNullable(sourceEntity);
    }

    @Override
    public void zine$setBlocked(@Nullable Boolean blocked) {
        this.blocked = Optional.ofNullable(blocked);
    }

    @Override
    public void zine$setType(@Nullable DamageSourcePredicate type) {
        this.type = Optional.ofNullable(type);
    }
}
