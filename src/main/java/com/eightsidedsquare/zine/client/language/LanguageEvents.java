package com.eightsidedsquare.zine.client.language;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import java.util.Map;

public final class LanguageEvents {
    /**
     * Event to modify the translations map when languages are loaded.
     */
    public static final Event<ModifyTranslations> MODIFY_TRANSLATIONS = EventFactory.createArrayBacked(
            ModifyTranslations.class,
            callbacks -> (translations, languageCode, bidirectional) -> {
                for(ModifyTranslations callback : callbacks) {
                    callback.modify(translations, languageCode, bidirectional);
                }
            }
    );

    @FunctionalInterface
    public interface ModifyTranslations {
        /**
         * Modifies the loaded translations by manipulating the translations map.
         * @param translations the map of loaded translation keys and values
         * @param languageCode the top language code being loaded. English (US) would be "en_us"
         * @param bidirectional {@code true} if the language is bidirectional
         * @apiNote When adding new translations,
         * assume the possibility that a resource pack adds a translation under the same key.
         * Use {@link Map#putIfAbsent(Object, Object)} to add translations without risking an override.
         */
        void modify(Map<String, String> translations, String languageCode, boolean bidirectional);
    }

    private LanguageEvents() {
    }
}
