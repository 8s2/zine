package com.eightsidedsquare.zine.mixin.structure;

import com.eightsidedsquare.zine.common.util.ZineUtil;
import com.eightsidedsquare.zine.common.world.structure.ZineStructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(StructureProcessorList.class)
public abstract class StructureProcessorListMixin implements ZineStructureProcessorList {

    @Shadow @Final @Mutable
    private List<StructureProcessor> list;

    @Override
    public List<StructureProcessor> zine$getProcessors() {
        return this.list;
    }

    @Override
    public void zine$setProcessors(List<StructureProcessor> processors) {
        this.list = processors;
    }

    @Override
    public void zine$addProcessor(StructureProcessor processor) {
        this.list = ZineUtil.addOrUnfreeze(this.list, processor);
    }

    @Override
    public void zine$addProcessors(List<StructureProcessor> processors) {
        this.list = ZineUtil.addAllOrUnfreeze(this.list, processors);
    }
}
