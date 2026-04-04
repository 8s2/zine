package com.eightsidedsquare.zine.mixin.advancement;

import com.eightsidedsquare.zine.common.advancement.ZineDisplayInfo;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(DisplayInfo.class)
public abstract class DisplayInfoMixin implements ZineDisplayInfo {

    @Shadow @Final @Mutable
    private Component title;

    @Shadow @Final @Mutable
    private Component description;

    @Shadow @Final @Mutable
    private ItemStackTemplate icon;

    @Shadow @Final @Mutable
    private Optional<Identifier> background;

    @Shadow @Final @Mutable
    private AdvancementType type;

    @Shadow @Final @Mutable
    private boolean showToast;

    @Shadow @Final @Mutable
    private boolean announceChat;

    @Shadow @Final @Mutable
    private boolean hidden;

    @Override
    public void zine$setTitle(Component title) {
        this.title = title;
    }

    @Override
    public void zine$setDescription(Component description) {
        this.description = description;
    }

    @Override
    public void zine$setIcon(ItemStackTemplate icon) {
        this.icon = icon;
    }

    @Override
    public void zine$setBackground(@Nullable Identifier background) {
        this.background = Optional.ofNullable(background);
    }

    @Override
    public void zine$setFrame(AdvancementType frame) {
        this.type = frame;
    }

    @Override
    public void zine$setShowToast(boolean showToast) {
        this.showToast = showToast;
    }

    @Override
    public void zine$setAnnounceToChat(boolean announceToChat) {
        this.announceChat = announceToChat;
    }

    @Override
    public void zine$setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
