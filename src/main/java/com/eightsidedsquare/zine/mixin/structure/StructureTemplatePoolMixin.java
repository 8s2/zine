package com.eightsidedsquare.zine.mixin.structure;

import com.eightsidedsquare.zine.common.util.ZineUtil;
import com.eightsidedsquare.zine.common.world.structure.ZineStructureTemplatePool;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(StructureTemplatePool.class)
public abstract class StructureTemplatePoolMixin implements ZineStructureTemplatePool {

    @Shadow @Final
    private ObjectArrayList<StructurePoolElement> templates;

    @Mutable @Shadow @Final
    private List<Pair<StructurePoolElement, Integer>> rawTemplates;

    @Shadow
    private int maxSize;

    @Shadow @Final @Mutable
    private Holder<StructureTemplatePool> fallback;

    @Override
    public ObjectArrayList<StructurePoolElement> zine$getElements() {
        return this.templates;
    }

    @Override
    public List<Pair<StructurePoolElement, Integer>> zine$getElementWeights() {
        return this.rawTemplates;
    }

    @Override
    public void zine$addElement(StructurePoolElement element, int weight) {
        if(weight < 1 || weight > 150) {
            return;
        }
        for(int i = 0; i < weight; i++) {
            this.templates.add(element);
        }
        this.rawTemplates = ZineUtil.addOrUnfreeze(this.rawTemplates, new Pair<>(element, weight));
        // Reset highest y so it can be recalculated
        this.maxSize = Integer.MIN_VALUE;
    }

    @Override
    public void zine$setFallback(Holder<StructureTemplatePool> fallback) {
        this.fallback = fallback;
    }
}
