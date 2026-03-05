package com.eightsidedsquare.zine.mixin.advancement;

import com.eightsidedsquare.zine.common.advancement.AdvancementEvents;
import com.eightsidedsquare.zine.common.advancement.AdvancementEventsImpl;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.server.ServerAdvancementManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ServerAdvancementManager.class)
public abstract class ServerAdvancementManagerMixin {

    @Shadow @Final private HolderLookup.Provider registries;

    @ModifyVariable(method = "lambda$apply$0", at = @At("HEAD"), argsOnly = true)
    private Advancement zine$modifyAdvancements(Advancement advancement, @Local(argsOnly = true) Identifier id) {
        Event<AdvancementEvents.ModifyAdvancement> event = AdvancementEventsImpl.getModifyAdvancementEvent(id);
        if(event != null) {
            return event.invoker().modifyAdvancement(advancement, this.registries);
        }
        return advancement;
    }

}
