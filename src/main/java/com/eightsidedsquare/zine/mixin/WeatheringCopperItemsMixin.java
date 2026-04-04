package com.eightsidedsquare.zine.mixin;

import com.eightsidedsquare.zine.common.util.ZineWeatheringCopperSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.WeatheringCopperItems;
import net.minecraft.world.level.block.WeatheringCopper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WeatheringCopperItems.class)
public abstract class WeatheringCopperItemsMixin implements ZineWeatheringCopperSet<Item> {

    @Shadow @Final private Item waxed;

    @Shadow @Final private Item waxedExposed;

    @Shadow @Final private Item waxedWeathered;

    @Shadow @Final private Item waxedOxidized;

    @Shadow @Final private Item unaffected;

    @Shadow @Final private Item exposed;

    @Shadow @Final private Item weathered;

    @Shadow @Final private Item oxidized;

    @Override
    public Item zine$get(WeatheringCopper.WeatherState oxidation, boolean waxed) {
        if(waxed) {
            return switch (oxidation) {
                case UNAFFECTED -> this.waxed;
                case EXPOSED -> this.waxedExposed;
                case WEATHERED -> this.waxedWeathered;
                case OXIDIZED -> this.waxedOxidized;
            };
        }
        return switch (oxidation) {
            case UNAFFECTED -> this.unaffected;
            case EXPOSED -> this.exposed;
            case WEATHERED -> this.weathered;
            case OXIDIZED -> this.oxidized;
        };
    }
}
