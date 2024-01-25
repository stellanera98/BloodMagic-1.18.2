package wayoftime.bloodmagic.common.tile;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.tile.base.TileBase;
import wayoftime.bloodmagic.network.BloodTankPacket;

public class TileBloodTank extends TileBase implements IFluidHandler
{

    public FluidTank internalFluid;
    private LazyOptional<IFluidHandler> lazyFluidHandler;
    private int capacity;

    public TileBloodTank(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        this(type, pos, state, 0);
    }

    public TileBloodTank(BlockEntityType<?> type, BlockPos pos, BlockState state, int tier)
    {
        super(type, pos, state);
        capacity = FluidAttributes.BUCKET_VOLUME * 16 * (int) Math.pow(2, tier);
        internalFluid = new FluidTank(capacity);
        this.initializeFluidCapabilities();
    }

    protected void initializeFluidCapabilities()
    {
        this.lazyFluidHandler = LazyOptional.of(() -> this);
    }

    @Override
    public void deserialize(CompoundTag tag)
    {
        super.deserialize(tag);
        CompoundTag tank = tag.getCompound("tank");
        internalFluid.readFromNBT(tank);
    }

    @Override
    public CompoundTag serialize(CompoundTag tag)
    {
        super.serialize(tag);
        CompoundTag tank = new CompoundTag();
        internalFluid.writeToNBT(tank);
        tag.put("tank", tank);

        return tag;
    }

    public int getCapacity()
    {
        return capacity;
    }

    public FluidStack getFluidStack()
    {
        return this.internalFluid.getFluid();
    }

    public TileBloodTank(BlockPos pos, BlockState state)
    {
        this(BloodMagicTileEntities.BLOOD_TANK_TYPE.get(), pos, state);
    }

    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return lazyFluidHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps()
    {
        super.invalidateCaps();
        if (lazyFluidHandler != null)
        {
            lazyFluidHandler.invalidate();
            lazyFluidHandler = null;
        }
    }

    @Override
    public int getTanks()
    {
        return 1;
    }

    @Override
    public FluidStack getFluidInTank(int tank)
    {
        return internalFluid.getFluid();
    }

    @Override
    public int getTankCapacity(int tank)
    {
        return internalFluid.getCapacity();
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack)
    {
        return internalFluid.isFluidValid(stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action)
    {
        int fillAmount = internalFluid.fill(resource, action);
        if (fillAmount > 0 && !level.isClientSide)
            BloodMagic.packetHandler.sendToAllTracking(new BloodTankPacket(this), this);

        return fillAmount;
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action)
    {
        FluidStack drainedStack = internalFluid.drain(resource, action);
        if (!drainedStack.isEmpty() && !level.isClientSide)
            BloodMagic.packetHandler.sendToAllTracking(new BloodTankPacket(this), this);

        return drainedStack;
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action)
    {
        FluidStack drainedStack = internalFluid.drain(maxDrain, action);
        if (!drainedStack.isEmpty() && !level.isClientSide)
            BloodMagic.packetHandler.sendToAllTracking(new BloodTankPacket(this), this);

        return drainedStack;
    }
}
