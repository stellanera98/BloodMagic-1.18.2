package wayoftime.bloodmagic.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.datacomponent.BMDataComponents;

public class BloodTankTile extends BlockEntity {
    private int tier;
    public static final int[] CAPACITIES = {16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288};
    private final FluidTank tank = new FluidTank(FluidType.BUCKET_VOLUME) {
        @Override
        protected void onContentsChanged() {
            setChanged();
        }
    };

    public BloodTankTile(BlockPos pos, BlockState state) {
        super(BMTiles.BLOOD_TANK_TYPE.get(), pos, state);
    }

    private void updateCapacity() {
        this.tank.setCapacity(FluidType.BUCKET_VOLUME * CAPACITIES[tier -1]);
        setChanged();
    }

    @Override
    public void setChanged() {
        super.setChanged();
        BloodMagic.LOGGER.debug("tier: {}, amount: {}/{}", tier, tank.getFluid().getAmount(), tank.getCapacity());
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        CompoundTag tankTag = tag.getCompound("tank");
        tank.readFromNBT(registries, tankTag);
        tier = tag.getInt("tier");
        updateCapacity();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        CompoundTag tankTag = new CompoundTag();
        tank.writeToNBT(registries, tankTag);
        tag.put("tank", tankTag);
        tag.putInt("tier", tier);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    public static @Nullable IFluidHandler getFluidHandler(BloodTankTile tile, @Nullable Direction direction) {
        return tile.tank;
    }

    public FluidStack getFluidContained() {
        return this.tank.getFluid();
    }

    public int getCapacity() {
        return this.tank.getCapacity();
    }

    @Override
    protected void applyImplicitComponents(DataComponentInput componentInput) {
        super.applyImplicitComponents(componentInput);
        this.tier = componentInput.getOrDefault(BMDataComponents.CONTAINER_TIER, 0);
        FluidStack stack = componentInput.getOrDefault(BMDataComponents.FLUID_CONTENT, SimpleFluidContent.EMPTY).copy();
        this.tank.setFluid(stack);
        updateCapacity();
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        components.set(BMDataComponents.CONTAINER_TIER, this.tier);
        components.set(BMDataComponents.FLUID_CONTENT, SimpleFluidContent.copyOf(this.tank.getFluid()));
    }
}
