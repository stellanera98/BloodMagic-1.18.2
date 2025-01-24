package wayoftime.bloodmagic.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.util.RuneType;
import wayoftime.bloodmagic.util.helper.AltarUtil;

import java.util.Map;

public class BloodAltarTile extends BlockEntity {
    public int tier = 0;
    public int ticks;
    public int inputTank = 0;
    public int outputTank = 0;
    public int mainTank = 0;
    public int chargingTank = 0;
    public ItemStackHandler inv = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
        }
    };

    private float capacityMod;
    private int tickRate;
    private float consumptionMod;
    private float sacrificeMod;
    private float selfSacMod;
    private float dislocationMod;
    private float orbCapMod;
    private float chargeAmountMod;
    private float chargeCapMod;
    private float efficiencyMod;
    private Map<RuneType, Integer> currentUpgrades;

    public BloodAltarTile(BlockPos pos, BlockState blockState) {
        super(BMTiles.BLOOD_ALTAR_TYPE.get(), pos, blockState);
    }

    public void calculateStats(Map<RuneType, Integer> upgrades) {
        capacityMod = (float) ((1D + 0.2D * upgrades.get(RuneType.CAPACITY) * Math.pow(1.075, upgrades.get(RuneType.AUGMENTED_CAPACITY))));
        tickRate = Math.max(1, 20 - upgrades.get(RuneType.ACCELERATION));
        consumptionMod = 1F + 0.2F * upgrades.get(RuneType.SPEED);
        sacrificeMod = 1F + 0.1F * upgrades.get(RuneType.SACRIFICE);
        selfSacMod = 1F + 0.1F * upgrades.get(RuneType.SELF_SACRIFICE);
        dislocationMod = (float) Math.pow(1.2, upgrades.get(RuneType.DISPLACEMENT));
        orbCapMod = 1F + 0.2F * upgrades.get(RuneType.ORB);
        chargeAmountMod = (10 * upgrades.get(RuneType.CHARGING) * (1 + consumptionMod/2));
        chargeCapMod = (float) Math.max(0.5 * capacityMod, 1) * upgrades.get(RuneType.CHARGING);
        efficiencyMod = (float) Math.pow(0.85, upgrades.get(RuneType.EFFICIENCY));
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, BloodAltarTile tile) {
        tile.ticks++;
        if (tile.ticks % (20 * 5) == 0 && !level.isClientSide) {
            int newTier = AltarUtil.getTier(level, blockPos);
            Map<RuneType, Integer> newUpgrades = AltarUtil.getUpgrades(newTier);
        }
    }

    public @Nullable IFluidHandler getFluidHandler(@Nullable Direction direction) {
        return new FluidTank(FluidType.BUCKET_VOLUME); // TODO make functional
    }

    public int getMainCapacity() {
        return (int) ((float) FluidType.BUCKET_VOLUME * 10F * capacityMod);
    }

    public int getIOCapacity() {
        return (int) ((float) FluidType.BUCKET_VOLUME * 1F * capacityMod);
    }

    public int getChargingCapacity() {
        return (int) ((float) FluidType.BUCKET_VOLUME * chargeCapMod);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        CompoundTag stats = tag.getCompound("upgrades");
        tickRate = stats.getInt("tickrate");
        capacityMod = stats.getFloat("capacity");
        consumptionMod = stats.getFloat("consumption");
        efficiencyMod = stats.getFloat("efficiency");
        sacrificeMod = stats.getFloat("sacrifice");
        selfSacMod = stats.getFloat("selfsacrifice");
        dislocationMod = stats.getFloat("dislocation");
        orbCapMod = stats.getFloat("orb");
        chargeAmountMod = stats.getFloat("chargeamount");
        chargeCapMod = stats.getFloat("chargecap");

        CompoundTag tanks = tag.getCompound("tanks");

        inputTank = tanks.getInt("input");
        outputTank = tanks.getInt("output");
        mainTank = tanks.getInt("main");
        chargingTank = tanks.getInt("charging");

        inv.deserializeNBT(registries, tag.getCompound("inventory"));
        BloodMagic.LOGGER.debug("loaded {}", inv.getStackInSlot(0).getHoverName());

        this.tier = tag.getInt("tier");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        CompoundTag stats = new CompoundTag();
        stats.putInt("tickrate", tickRate);
        stats.putFloat("capacity", capacityMod);
        stats.putFloat("consumption", consumptionMod);
        stats.putFloat("efficiency", efficiencyMod);
        stats.putFloat("sacrifice", sacrificeMod);
        stats.putFloat("selfsacrifice", selfSacMod);
        stats.putFloat("dislocation", dislocationMod);
        stats.putFloat("orb", orbCapMod);
        stats.putFloat("chargeamount", chargeAmountMod);
        stats.putFloat("chargecap", chargeCapMod);

        CompoundTag tanks = new CompoundTag();
        tanks.putInt("input", inputTank);
        tanks.putInt("output", outputTank);
        tanks.putInt("main", mainTank);
        tanks.putInt("charging", chargingTank);

        CompoundTag inventory = inv.serializeNBT(registries);

        tag.put("tanks", tanks);

        tag.put("inventory", inventory);

        tag.put("stats", stats);
        tag.putInt("tier", this.tier);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }
}
