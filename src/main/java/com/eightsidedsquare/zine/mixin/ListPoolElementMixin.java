package com.eightsidedsquare.zine.mixin;

import com.eightsidedsquare.zine.common.util.ZineUtil;
import com.eightsidedsquare.zine.common.world.structure.ListPoolElementExtensions;
import net.minecraft.structure.pool.ListPoolElement;
import net.minecraft.structure.pool.StructurePoolElement;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(ListPoolElement.class)
public abstract class ListPoolElementMixin implements ListPoolElementExtensions {

    @Shadow @Final @Mutable
    private List<StructurePoolElement> elements;

    @Override
    public List<StructurePoolElement> zine$getElements() {
        return this.elements;
    }

    @Override
    public void zine$setElements(List<StructurePoolElement> elements) {
        this.elements = elements;
    }

    @Override
    public void zine$addElement(StructurePoolElement element) {
        this.elements = ZineUtil.addOrUnfreeze(this.elements, element);
    }

    @Override
    public void zine$addElements(List<StructurePoolElement> elements) {
        this.elements = ZineUtil.addAllOrUnfreeze(this.elements, elements);
    }
}
