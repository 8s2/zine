package com.eightsidedsquare.zine.common.advancement;

import net.minecraft.advancements.AdvancementType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import org.jetbrains.annotations.Nullable;

public interface ZineDisplayInfo {

    default void zine$setTitle(Component title) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setDescription(Component description) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setIcon(ItemStackTemplate icon) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setBackground(@Nullable Identifier background) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setFrame(AdvancementType frame) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setShowToast(boolean showToast) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setAnnounceToChat(boolean announceToChat) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setHidden(boolean hidden) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
