package com.eightsidedsquare.zine.common.item.tooltip;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.text.Text;

import java.util.List;

public record CompositeTooltipData(List<Either<Text, TooltipData>> data) implements TooltipData {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final ImmutableList.Builder<Either<Text, TooltipData>> data = ImmutableList.builder();

        public Builder with(Text text) {
            this.data.add(Either.left(text));
            return this;
        }

        public Builder with(TooltipData tooltipData) {
            this.data.add(Either.right(tooltipData));
            return this;
        }

        public CompositeTooltipData build() {
            return new CompositeTooltipData(this.data.build());
        }
    }

}
