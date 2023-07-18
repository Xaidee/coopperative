package galena.copperative.content.block;

import galena.copperative.index.CConversions;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class WeatheringPillarBlock extends RotatedPillarBlock implements CWeatheringCopper {

    private final WeatherState weatherState;

    public WeatheringPillarBlock(WeatherState weatherState, Properties properties) {
        super(properties);
        this.weatherState = weatherState;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        this.onRandomTick(state, world, pos, random);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return CConversions.getWeatheredVersion(state.getBlock()).isPresent();
    }

    @Override
    public WeatherState getAge() {
        return this.weatherState;
    }
}
