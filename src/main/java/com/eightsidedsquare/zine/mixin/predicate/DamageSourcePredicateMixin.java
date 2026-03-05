package com.eightsidedsquare.zine.mixin.predicate;

import com.eightsidedsquare.zine.common.predicate.ZineDamageSourcePredicate;
import com.eightsidedsquare.zine.common.util.ZineUtil;
import net.minecraft.advancements.criterion.DamageSourcePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.TagPredicate;
import net.minecraft.world.damagesource.DamageType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Optional;

@Mixin(DamageSourcePredicate.class)
public abstract class DamageSourcePredicateMixin implements ZineDamageSourcePredicate {

    @Shadow @Final @Mutable
    private List<TagPredicate<DamageType>> tags;

    @Shadow @Final @Mutable
    private Optional<EntityPredicate> sourceEntity;

    @Shadow @Final @Mutable
    private Optional<EntityPredicate> directEntity;

    @Shadow @Final @Mutable
    private Optional<Boolean> isDirect;

    @Override
    public void zine$setTags(List<TagPredicate<DamageType>> tags) {
        this.tags = tags;
    }

    @Override
    public void zine$addTag(TagPredicate<DamageType> tag) {
        this.tags = ZineUtil.addOrUnfreeze(this.tags, tag);
    }

    @Override
    public void zine$addTags(List<TagPredicate<DamageType>> tags) {
        this.tags = ZineUtil.addAllOrUnfreeze(this.tags, tags);
    }

    @Override
    public void zine$setDirectEntity(@Nullable EntityPredicate directEntity) {
        this.directEntity = Optional.ofNullable(directEntity);
    }

    @Override
    public void zine$setSourceEntity(@Nullable EntityPredicate sourceEntity) {
        this.sourceEntity = Optional.ofNullable(sourceEntity);
    }

    @Override
    public void zine$setDirect(@Nullable Boolean direct) {
        this.isDirect = Optional.ofNullable(direct);
    }
}
