package com.eightsidedsquare.zine.common.item.tooltip;

import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.eightsidedsquare.zine.common.util.network.StreamCodecUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public record ConditionTooltipImage(List<ConditionCase> cases, Optional<TooltipImage> fallback) implements TooltipImage {
    public static final SyncedCodec<ConditionTooltipImage> TYPE = new SyncedCodec<>(
            RecordCodecBuilder.mapCodec(i -> i.group(
                    ExtraCodecs.nonEmptyList(ConditionCase.CODEC.listOf()).fieldOf("cases").forGetter(ConditionTooltipImage::cases),
                    TooltipImage.CODEC.optionalFieldOf("fallback").forGetter(ConditionTooltipImage::fallback)
            ).apply(i, ConditionTooltipImage::new)),
            StreamCodec.composite(
                    ConditionCase.STREAM_CODEC.apply(ByteBufCodecs.list()),
                    ConditionTooltipImage::cases,
                    TooltipImage.STREAM_CODEC.apply(ByteBufCodecs::optional),
                    ConditionTooltipImage::fallback,
                    ConditionTooltipImage::new
            )
    );

    public ConditionTooltipImage(List<ConditionCase> cases, TooltipImage fallback) {
        this(cases, Optional.of(fallback));
    }

    public ConditionTooltipImage(List<ConditionCase> cases) {
        this(cases, Optional.empty());
    }

    @Override
    public @Nullable TooltipComponent getTooltipImage(ItemStack itemStack, TooltipDisplay display) {
        for (ConditionCase conditionCase : this.cases) {
            if (conditionCase.condition.test(itemStack)) {
                return conditionCase.image.getTooltipImage(itemStack, display);
            }
        }
        return this.fallback.map(image -> image.getTooltipImage(itemStack, display)).orElse(null);
    }

    @Override
    public boolean canShow(TooltipDisplay display) {
        for (ConditionCase conditionCase : this.cases) {
            if (conditionCase.image.canShow(display)) {
                return true;
            }
        }
        return this.fallback.map(image -> image.canShow(display)).orElse(false);
    }

    @Override
    public SyncedCodec<? extends TooltipImage> type() {
        return TYPE;
    }

    public record ConditionCase(ItemPredicate condition, TooltipImage image) {
        public static final Codec<ConditionCase> CODEC = RecordCodecBuilder.create(i -> i.group(
                ItemPredicate.CODEC.fieldOf("condition").forGetter(ConditionCase::condition),
                TooltipImage.CODEC.fieldOf("image").forGetter(ConditionCase::image)
        ).apply(i, ConditionCase::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, ConditionCase> STREAM_CODEC = StreamCodec.composite(
                StreamCodecUtil.ITEM_PREDICATE,
                ConditionCase::condition,
                TooltipImage.STREAM_CODEC,
                ConditionCase::image,
                ConditionCase::new
        );
    }
}
