package com.eightsidedsquare.zine.mixin.client.item;

import com.eightsidedsquare.zine.client.item.ItemModelEvents;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.resources.model.ClientItemInfoLoader;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(ClientItemInfoLoader.class)
public abstract class ClientItemInfoLoaderMixin {

    @Inject(method = "lambda$scheduleLoad$6", at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private static void zine$modifyUnbakedItemModels(List<ClientItemInfoLoader.PendingLoad> definitions, CallbackInfoReturnable<ClientItemInfoLoader.LoadedClientInfos> cir, @Local(name = "load") ClientItemInfoLoader.PendingLoad load) {
        ClientItem itemAsset = load.clientItemInfo();
        if(itemAsset == null) {
            return;
        }
        itemAsset.model = ItemModelEvents.BEFORE_BAKE.invoker().modifyBeforeBake(load.id(), itemAsset.model);
    }

    @Inject(method = "lambda$scheduleLoad$6", at = @At(value = "NEW", target = "(Ljava/util/Map;)Lnet/minecraft/client/resources/model/ClientItemInfoLoader$LoadedClientInfos;"))
    private static void zine$addUnbakedItemModels(List<ClientItemInfoLoader.PendingLoad> definitions, CallbackInfoReturnable<ClientItemInfoLoader.LoadedClientInfos> cir, @Local(name = "resultMap") Map<Identifier, ClientItem> resultMap) {
        ItemModelEvents.ADD_UNBAKED.invoker().addUnbakedModels(resultMap::put);
    }

}
