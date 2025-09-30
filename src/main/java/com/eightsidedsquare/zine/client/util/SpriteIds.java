package com.eightsidedsquare.zine.client.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Atlases;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public final class SpriteIds {
    
    public static SpriteIdentifier of(Identifier atlasId, Identifier id) {
        return new SpriteIdentifier(atlasId, id);
    }

    public static SpriteIdentifier armorTrim(Identifier id) {
        return of(Atlases.ARMOR_TRIMS, id);
    }

    public static SpriteIdentifier bannerPattern(Identifier id) {
        return of(Atlases.BANNER_PATTERNS, id);
    }

    public static SpriteIdentifier bed(Identifier id) {
        return of(Atlases.BEDS, id);
    }

    public static SpriteIdentifier block(Identifier id) {
        return of(Atlases.BLOCKS, id);
    }

    public static SpriteIdentifier chest(Identifier id) {
        return of(Atlases.CHESTS, id);
    }

    public static SpriteIdentifier decoratedPot(Identifier id) {
        return of(Atlases.DECORATED_POT, id);
    }

    public static SpriteIdentifier gui(Identifier id) {
        return of(Atlases.GUI, id);
    }

    public static SpriteIdentifier mapDecoration(Identifier id) {
        return of(Atlases.MAP_DECORATIONS, id);
    }

    public static SpriteIdentifier painting(Identifier id) {
        return of(Atlases.PAINTINGS, id);
    }

    public static SpriteIdentifier particle(Identifier id) {
        return of(Atlases.PARTICLES, id);
    }

    public static SpriteIdentifier shieldPattern(Identifier id) {
        return of(Atlases.SHIELD_PATTERNS, id);
    }

    public static SpriteIdentifier shulkerBox(Identifier id) {
        return of(Atlases.SHULKER_BOXES, id);
    }

    public static SpriteIdentifier sign(Identifier id) {
        return of(Atlases.SIGNS, id);
    }

    private SpriteIds() {
    }
}
