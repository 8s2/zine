package com.eightsidedsquare.zine.mixin.advancement;

import com.eightsidedsquare.zine.common.advancement.ZineAdvancementRequirements;
import com.eightsidedsquare.zine.common.util.ZineUtil;
import net.minecraft.advancements.AdvancementRequirements;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(AdvancementRequirements.class)
public abstract class AdvancementRequirementsMixin implements ZineAdvancementRequirements {

    @Shadow @Final @Mutable
    private List<List<String>> requirements;

    @Override
    public void zine$setRequirements(List<List<String>> requirements) {
        this.requirements = requirements;
    }

    @Override
    public void zine$addRequirement(List<String> requirement) {
        this.requirements = ZineUtil.addOrUnfreeze(this.requirements, requirement);
    }

    @Override
    public void zine$addRequirements(List<List<String>> requirements) {
        this.requirements = ZineUtil.addAllOrUnfreeze(this.requirements, requirements);
    }

    @Override
    public void zine$addRequirement(int index, String requirement) {
        this.zine$setRequirement(index, ZineUtil.addOrUnfreeze(this.requirements.get(index), requirement));
    }

    @Override
    public void zine$addRequirements(int index, List<String> requirements) {
        this.zine$setRequirement(index, ZineUtil.addAllOrUnfreeze(this.requirements.get(index), requirements));
    }

    @Override
    public void zine$setRequirement(int index, List<String> requirements) {
        if(index < 0 || index >= this.requirements.size()) {
            return;
        }
        this.requirements = ZineUtil.setOrUnfreeze(this.requirements, index, requirements);
    }
}
