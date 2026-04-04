package com.eightsidedsquare.zine.mixin.client;

import com.eightsidedsquare.zine.client.language.LanguageEvents;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.resources.language.ClientLanguage;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(ClientLanguage.class)
public abstract class ClientLanguageMixin {

    @Inject(method = "loadFrom(Lnet/minecraft/server/packs/resources/ResourceManager;Ljava/util/List;Z)Lnet/minecraft/client/resources/language/ClientLanguage;", at = @At(value = "NEW", target = "(Ljava/util/Map;Z)Lnet/minecraft/client/resources/language/ClientLanguage;"))
    private static void zine$invokeModifyTranslationsEvent(ResourceManager resourceManager,
                                                           List<String> definitions,
                                                           boolean rightToLeft,
                                                           CallbackInfoReturnable<ClientLanguage> cir,
                                                           @Local(ordinal = 0) Map<String, String> translations) {
        LanguageEvents.MODIFY_TRANSLATIONS.invoker().modify(translations, definitions.getLast(), rightToLeft);
    }

}
