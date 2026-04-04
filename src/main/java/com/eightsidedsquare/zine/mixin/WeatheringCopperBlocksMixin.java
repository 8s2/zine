package com.eightsidedsquare.zine.mixin;

import com.eightsidedsquare.zine.common.util.ZineWeatheringCopperSet;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.WeatheringCopperBlocks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WeatheringCopperBlocks.class)
public abstract class WeatheringCopperBlocksMixin implements ZineWeatheringCopperSet<Block> {

    @Shadow @Final private Block waxed;

    @Shadow @Final private Block waxedExposed;

    @Shadow @Final private Block waxedWeathered;

    @Shadow @Final private Block waxedOxidized;

    @Shadow @Final private Block unaffected;

    @Shadow @Final private Block exposed;

    @Shadow @Final private Block weathered;

    @Shadow @Final private Block oxidized;

    @Override
    public Block zine$get(WeatheringCopper.WeatherState oxidation, boolean waxed) {
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
