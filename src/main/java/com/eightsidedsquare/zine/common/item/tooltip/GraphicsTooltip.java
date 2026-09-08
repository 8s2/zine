package com.eightsidedsquare.zine.common.item.tooltip;

import com.eightsidedsquare.zine.common.util.codec.CodecUtil;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.eightsidedsquare.zine.common.util.network.StreamCodecUtil;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.TooltipDisplay;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.util.List;
import java.util.function.Function;

public record GraphicsTooltip(
        List<Text> texts,
        List<Sprite> sprites,
        List<Rectangle> rectangles,
        List<Item> items
) implements TooltipComponent {
    public record Image(
            List<Text> texts,
            List<Sprite> sprites,
            List<Rectangle> rectangles,
            List<Item> items
    ) implements TooltipImage {
        public static final SyncedCodec<Image> TYPE = new SyncedCodec<>(
                RecordCodecBuilder.mapCodec(i -> i.group(
                        Text.CODEC.listOf().optionalFieldOf("texts", List.of()).forGetter(Image::texts),
                        Sprite.CODEC.listOf().optionalFieldOf("sprites", List.of()).forGetter(Image::sprites),
                        Rectangle.CODEC.listOf().optionalFieldOf("rectangles", List.of()).forGetter(Image::rectangles),
                        Item.CODEC.listOf().optionalFieldOf("items", List.of()).forGetter(Image::items)
                ).apply(i, Image::new)),
                StreamCodec.composite(
                        Text.STREAM_CODEC.apply(ByteBufCodecs.list()),
                        Image::texts,
                        Sprite.STREAM_CODEC.apply(ByteBufCodecs.list()),
                        Image::sprites,
                        Rectangle.STREAM_CODEC.apply(ByteBufCodecs.list()),
                        Image::rectangles,
                        Item.STREAM_CODEC.apply(ByteBufCodecs.list()),
                        Image::items,
                        Image::new
                )
        );
        @Override
        public TooltipComponent getTooltip(ItemStack itemStack, TooltipDisplay display) {
            return new GraphicsTooltip(this.texts, this.sprites, this.rectangles, this.items);
        }

        @Override
        public SyncedCodec<? extends TooltipImage> type() {
            return TYPE;
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder implements TooltipImage.Builder {
            private final ImmutableList.Builder<Text> texts = ImmutableList.builder();
            private final ImmutableList.Builder<Sprite> sprites = ImmutableList.builder();
            private final ImmutableList.Builder<Rectangle> rectangles = ImmutableList.builder();
            private final ImmutableList.Builder<Item> items = ImmutableList.builder();

            public Builder text(Component text, int x, int y, int maxWidth) {
                this.texts.add(new Text(text, x, y, maxWidth));
                return this;
            }

            public Builder text(Component text, int x, int y) {
                return this.text(text, x, y, 0);
            }

            public Builder text(Component text, int maxWidth) {
                return this.text(text, 0, 0, maxWidth);
            }

            public Builder text(Component text) {
                return this.text(text, 0, 0, 0);
            }

            public Builder sprite(Identifier sprite, int x, int y, int width, int height, int color) {
                this.sprites.add(new Sprite(sprite, x, y, width, height, color));
                return this;
            }

            public Builder sprite(Identifier sprite, int x, int y, int width, int height) {
                return this.sprite(sprite, x, y, width, height, -1);
            }

            public Builder rectangle(int x, int y, int width, int height, int fromColor, int toColor) {
                this.rectangles.add(new Rectangle(x, y, width, height, fromColor, toColor));
                return this;
            }

            public Builder rectangle(int x, int y, int width, int height, int color) {
                return this.rectangle(x, y, width, height, color, color);
            }

            public Builder rectangle(int x, int y, int width, int height) {
                return this.rectangle(x, y, width, height, -1);
            }

            public Builder item(int x, int y, ItemStackTemplate item) {
                this.items.add(new Item(x, y, item));
                return this;
            }

            @Override
            public TooltipImage build() {
                return new GraphicsTooltip.Image(this.texts.build(), this.sprites.build(), this.rectangles.build(), this.items.build());
            }
        }
    }

    public record Text(Component text, int x, int y, int maxWidth) {
        public static final Codec<Text> CODEC = RecordCodecBuilder.create(i -> i.group(
                ComponentSerialization.CODEC.fieldOf("text").forGetter(Text::text),
                CodecUtil.VECTOR2I.optionalFieldOf("pos", new Vector2i()).forGetter(Text::pos),
                ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("max_width", 0).forGetter(Text::maxWidth)
        ).apply(i, Text::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, Text> STREAM_CODEC = StreamCodec.composite(
                ComponentSerialization.STREAM_CODEC,
                Text::text,
                ByteBufCodecs.VAR_INT,
                Text::x,
                ByteBufCodecs.VAR_INT,
                Text::y,
                ByteBufCodecs.VAR_INT,
                Text::maxWidth,
                Text::new
        );

        public Text(Component text, Vector2ic pos, int maxWidth) {
            this(text, pos.x(), pos.y(), maxWidth);
        }

        public Vector2ic pos() {
            return new Vector2i(this.x, this.y);
        }
    }

    public record Sprite(Identifier sprite, int x, int y, int width, int height, int color) {
        public static final Codec<Sprite> CODEC = RecordCodecBuilder.create(i -> i.group(
                Identifier.CODEC.fieldOf("sprite").forGetter(Sprite::sprite),
                Codec.INT.optionalFieldOf("x", 0).forGetter(Sprite::x),
                Codec.INT.optionalFieldOf("y", 0).forGetter(Sprite::y),
                Codec.INT.optionalFieldOf("width", 1).forGetter(Sprite::width),
                Codec.INT.optionalFieldOf("height", 1).forGetter(Sprite::height),
                ExtraCodecs.STRING_ARGB_COLOR.optionalFieldOf("color", -1).forGetter(Sprite::color)
        ).apply(i, Sprite::new));
        public static final StreamCodec<ByteBuf, Sprite> STREAM_CODEC = StreamCodec.composite(
                StreamCodecUtil.SHORT_IDENTIFIER,
                Sprite::sprite,
                ByteBufCodecs.VAR_INT,
                Sprite::x,
                ByteBufCodecs.VAR_INT,
                Sprite::y,
                ByteBufCodecs.VAR_INT,
                Sprite::width,
                ByteBufCodecs.VAR_INT,
                Sprite::height,
                ByteBufCodecs.INT,
                Sprite::color,
                Sprite::new
        );
    }

    public record Rectangle(int x, int y, int width, int height, int fromColor, int toColor) {
        private static final Codec<Vector2ic> COLOR_CODEC = Codec.either(
                ExtraCodecs.STRING_ARGB_COLOR,
                RecordCodecBuilder.<Vector2ic>create(i -> i.group(
                        ExtraCodecs.STRING_ARGB_COLOR.fieldOf("from").forGetter(Vector2ic::x),
                        ExtraCodecs.STRING_ARGB_COLOR.fieldOf("to").forGetter(Vector2ic::y)
                ).apply(i, Vector2i::new))
        ).xmap(
                either -> either.map(Vector2i::new, Function.identity()),
                color -> color.x() == color.y() ? Either.left(color.x()) : Either.right(color)
        );
        public static final Codec<Rectangle> CODEC = RecordCodecBuilder.create(i -> i.group(
                Codec.INT.optionalFieldOf("x", 0).forGetter(Rectangle::x),
                Codec.INT.optionalFieldOf("y", 0).forGetter(Rectangle::y),
                Codec.INT.optionalFieldOf("width", 1).forGetter(Rectangle::width),
                Codec.INT.optionalFieldOf("height", 1).forGetter(Rectangle::height),
                COLOR_CODEC.optionalFieldOf("color", new Vector2i(-1)).forGetter(Rectangle::color)
        ).apply(i, Rectangle::new));
        public static final StreamCodec<ByteBuf, Rectangle> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.VAR_INT,
                Rectangle::x,
                ByteBufCodecs.VAR_INT,
                Rectangle::y,
                ByteBufCodecs.VAR_INT,
                Rectangle::width,
                ByteBufCodecs.VAR_INT,
                Rectangle::height,
                ByteBufCodecs.INT,
                Rectangle::fromColor,
                ByteBufCodecs.INT,
                Rectangle::toColor,
                Rectangle::new
        );

        public Rectangle(int x, int y, int width, int height, Vector2ic color) {
            this(x, y, width, height, color.x(), color.y());
        }

        public Vector2ic color() {
            return new Vector2i(this.fromColor, this.toColor);
        }
    }

    public record Item(int x, int y, ItemStackTemplate item) {
        public static final Codec<Item> CODEC = RecordCodecBuilder.create(i -> i.group(
                Codec.INT.optionalFieldOf("x", 0).forGetter(Item::x),
                Codec.INT.optionalFieldOf("y", 0).forGetter(Item::y),
                ItemStackTemplate.CODEC.fieldOf("item").forGetter(Item::item)
        ).apply(i, Item::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, Item> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.VAR_INT,
                Item::x,
                ByteBufCodecs.VAR_INT,
                Item::y,
                ItemStackTemplate.STREAM_CODEC,
                Item::item,
                Item::new
        );
    }
}
