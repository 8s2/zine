package com.eightsidedsquare.zine.mixin.predicate;

import com.eightsidedsquare.zine.common.predicate.ZineEntityPredicate;
import com.eightsidedsquare.zine.common.util.ZineUtil;
import com.mojang.serialization.Codec;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.predicates.entity.EntitySubPredicate;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(EntityPredicate.class)
public abstract class EntityPredicateMixin implements ZineEntityPredicate {

    @Shadow @Final @Mutable
    private Map<Codec<? extends EntitySubPredicate>, EntitySubPredicate> parts;
    @Shadow @Final @Mutable
    private EntitySubPredicate combinedPart;

    @Shadow
    private static EntitySubPredicate combine(Map<Codec<? extends EntitySubPredicate>, EntitySubPredicate> predicateMap) {
        throw new AssertionError();
    }

    @Override
    public Map<Codec<? extends EntitySubPredicate>, EntitySubPredicate> zine$getParts() {
        return this.parts;
    }

    @Override
    public void zine$setParts(Map<Codec<? extends EntitySubPredicate>, EntitySubPredicate> parts) {
        this.parts = parts;
        this.combinedPart = combine(this.parts);
    }

    @Override
    public <T extends EntitySubPredicate> void zine$addPart(Codec<T> codec, T predicate) {
        this.zine$setParts(ZineUtil.putOrUnfreeze(this.parts, codec, predicate));
    }

    @Override
    public void zine$addParts(Map<Codec<? extends EntitySubPredicate>, EntitySubPredicate> parts) {
        this.zine$setParts(ZineUtil.putAllOrUnfreeze(this.parts, parts));
    }
}
