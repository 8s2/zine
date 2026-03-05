package com.eightsidedsquare.zine.client.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resources.model.SpriteId;
import net.minecraft.data.AtlasIds;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public final class SpriteIds {
    
    public static SpriteId of(Identifier atlasId, Identifier id) {
        return new SpriteId(atlasId, id);
    }

    public static SpriteId armorTrim(Identifier id) {
        return of(AtlasIds.ARMOR_TRIMS, id);
    }

    public static SpriteId bannerPattern(Identifier id) {
        return of(AtlasIds.BANNER_PATTERNS, id);
    }

    public static SpriteId bed(Identifier id) {
        return of(AtlasIds.BEDS, id);
    }

    public static SpriteId block(Identifier id) {
        return of(AtlasIds.BLOCKS, id);
    }

    public static SpriteId chest(Identifier id) {
        return of(AtlasIds.CHESTS, id);
    }

    public static SpriteId decoratedPot(Identifier id) {
        return of(AtlasIds.DECORATED_POT, id);
    }

    public static SpriteId gui(Identifier id) {
        return of(AtlasIds.GUI, id);
    }

    public static SpriteId item(Identifier id) {
        return of(AtlasIds.ITEMS, id);
    }

    public static SpriteId mapDecoration(Identifier id) {
        return of(AtlasIds.MAP_DECORATIONS, id);
    }

    public static SpriteId painting(Identifier id) {
        return of(AtlasIds.PAINTINGS, id);
    }

    public static SpriteId particle(Identifier id) {
        return of(AtlasIds.PARTICLES, id);
    }

    public static SpriteId shieldPattern(Identifier id) {
        return of(AtlasIds.SHIELD_PATTERNS, id);
    }

    public static SpriteId shulkerBox(Identifier id) {
        return of(AtlasIds.SHULKER_BOXES, id);
    }

    public static SpriteId sign(Identifier id) {
        return of(AtlasIds.SIGNS, id);
    }

    public static SpriteId celestial(Identifier id) {
        return of(AtlasIds.CELESTIALS, id);
    }

    private SpriteIds() {
    }
}
