package com.eightsidedsquare.zine.mixin.client.texture;

import com.eightsidedsquare.zine.client.atlas.AtlasEvents;
import com.eightsidedsquare.zine.client.atlas.AtlasEventsImpl;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceList;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(SpriteSourceList.class)
public abstract class SpriteSourceListMixin {

    @Inject(method = "load", at = @At(value = "NEW", target = "(Ljava/util/List;)Lnet/minecraft/client/renderer/texture/atlas/SpriteSourceList;"))
    private static void zine$invokeModifySourcesEvent(ResourceManager resourceManager, Identifier id, CallbackInfoReturnable<SpriteSourceList> cir, @Local List<SpriteSource> atlasSources) {
        Event<AtlasEvents.ModifySources> modifySourcesEvent = AtlasEventsImpl.getModifySourcesEvent(id);
        if(modifySourcesEvent != null) {
            modifySourcesEvent.invoker().modifySources(atlasSources);
        }
    }

}
