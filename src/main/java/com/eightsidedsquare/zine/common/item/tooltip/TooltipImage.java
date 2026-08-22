package com.eightsidedsquare.zine.common.item.tooltip;

import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.eightsidedsquare.zine.core.ZineBuiltinRegistries;
import com.eightsidedsquare.zine.core.ZineRegistries;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface TooltipImage {
    Codec<TooltipImage> CODEC = Codec.lazyInitialized(TooltipImage::createCodec);
    StreamCodec<RegistryFriendlyByteBuf, TooltipImage> STREAM_CODEC = ByteBufCodecs.registry(ZineRegistries.TOOLTIP_IMAGE).dispatch(TooltipImage::type, SyncedCodec::streamCodec);

    @Nullable
    TooltipComponent getTooltip(ItemStack itemStack, TooltipDisplay display);

    default boolean canShow(TooltipDisplay display) {
        return true;
    }

    SyncedCodec<? extends TooltipImage> type();

    static Optional<TooltipComponent> getTooltip(Optional<TooltipImage> optional, ItemStack itemStack, TooltipDisplay display) {
        return optional.flatMap(image -> Optional.ofNullable(image.getTooltip(itemStack, display)));
    }

    private static Codec<TooltipImage> createCodec() {
        Codec<TooltipImage> dispatched = ZineBuiltinRegistries.TOOLTIP_IMAGE.byNameCodec()
                .dispatch(TooltipImage::type, SyncedCodec::codec);
        return Codec.recursive("tooltip_image", codec ->
                Codec.either(
                        Codec.either(ExtraCodecs.nonEmptyList(codec.listOf()), dispatched).xmap(
                                either -> either.map(CompositeTooltip.Image::new, Function.identity()),
                                image -> image instanceof CompositeTooltip.Image(List<TooltipImage> contents) ?
                                        Either.left(contents) : Either.right(image)
                        ),
                        ComponentSerialization.CODEC
                ).xmap(
                        either -> either.map(Function.identity(), TextTooltip.Image::new),
                        image -> image instanceof TextTooltip.Image(Component text) ? Either.right(text) : Either.left(image)
                )
        );
    }

    @FunctionalInterface
    interface Builder {
        TooltipImage build();
    }
}
