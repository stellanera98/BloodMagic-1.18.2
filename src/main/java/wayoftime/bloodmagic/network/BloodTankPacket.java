package wayoftime.bloodmagic.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent.Context;
import wayoftime.bloodmagic.common.tile.TileAlchemicalReactionChamber;
import wayoftime.bloodmagic.common.tile.TileBloodTank;

public class BloodTankPacket
{
	private BlockPos pos;
	private CompoundTag fluidStack;

	public BloodTankPacket()
	{
		pos = BlockPos.ZERO;
		fluidStack = new CompoundTag();
	}

	public BloodTankPacket(TileBloodTank tile)
	{
		this(tile.getBlockPos(), tile.internalFluid.writeToNBT(new CompoundTag()));
	}

	public BloodTankPacket(BlockPos pos, CompoundTag fluidNBT)
	{
		this.pos = pos;
		this.fluidStack = fluidNBT;
	}

	public static void encode(BloodTankPacket pkt, FriendlyByteBuf buf)
	{
		buf.writeBlockPos(pkt.pos);
		buf.writeNbt(pkt.fluidStack);
	}

	public static BloodTankPacket decode(FriendlyByteBuf buf)
	{
		BloodTankPacket pkt = new BloodTankPacket(buf.readBlockPos(), buf.readNbt()); 

		return pkt;
	}

	public static void handle(BloodTankPacket message, Supplier<Context> context)
	{
		context.get().enqueueWork(() -> updateTanks(message.pos, message.fluidStack));
		context.get().setPacketHandled(true);
	}

	@OnlyIn(Dist.CLIENT)
	public static void updateTanks(BlockPos pos, CompoundTag fluidStack)
	{
		Level world = Minecraft.getInstance().level;
		BlockEntity tile = world.getBlockEntity(pos);
		if (tile instanceof TileBloodTank)
		{
			((TileBloodTank) tile).internalFluid.readFromNBT(fluidStack);
		}
	}
}
