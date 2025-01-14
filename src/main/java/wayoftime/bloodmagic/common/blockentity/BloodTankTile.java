package wayoftime.bloodmagic.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;

public class BloodTankTile extends BlockEntity {
    private final FluidTank tank = new FluidTank(16 * FluidType.BUCKET_VOLUME) {
        @Override
        protected void onContentsChanged() {
            setChanged();
        }
    };
    public BloodTankTile(BlockPos pos, BlockState blockState) {
        super(BloodMagicTiles.BLOOD_TANK_TYPE.get(), pos, blockState);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        CompoundTag tankTag = tag.getCompound("tank");
        tank.readFromNBT(registries, tankTag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        CompoundTag tankTag = new CompoundTag();
        tank.writeToNBT(registries, tankTag);
        tag.put("tank", tankTag);
    }

    public static @Nullable IFluidHandler getFluidHandler(BloodTankTile tile, @Nullable Direction direction) {
        return tile.tank;
    }
}
