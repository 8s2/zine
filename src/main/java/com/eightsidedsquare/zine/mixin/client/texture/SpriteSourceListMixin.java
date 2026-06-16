package com.eightsidedsquare.zine.mixin.client.texture;

import com.eightsidedsquare.zine.client.atlas.AtlasEvents;
import com.eightsidedsquare.zine.client.atlas.AtlasEventsImpl;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceList;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(SpriteSourceList.class)
public abstract class SpriteSourceListMixin {
    @WrapOperation(method = "load", at = @At(value = "NEW", target = "(Ljava/util/List;)Lnet/minecraft/client/renderer/texture/atlas/SpriteSourceList;"))
    private static SpriteSourceList zine$invokeModifySourcesEvent(List<SpriteSource> sources, Operation<SpriteSourceList> original, @Local(argsOnly = true) Identifier id) {
        Event<AtlasEvents.ModifySources> modifySourcesEvent = AtlasEventsImpl.getModifySourcesEvent(id);
        if (modifySourcesEvent != null) {
            modifySourcesEvent.invoker().modifySources(sources);
        }

        return original.call(sources);
    }
}
