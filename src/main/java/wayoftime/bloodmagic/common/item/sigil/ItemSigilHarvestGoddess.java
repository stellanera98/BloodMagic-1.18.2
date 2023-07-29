package wayoftime.bloodmagic.common.item.sigil;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import wayoftime.bloodmagic.core.data.SoulTicket;
import wayoftime.bloodmagic.util.helper.NetworkHelper;
import wayoftime.bloodmagic.util.helper.PlayerHelper;
import wayoftime.bloodmagic.ritual.harvest.HarvestRegistry;
import wayoftime.bloodmagic.ritual.harvest.IHarvestHandler;

public class ItemSigilHarvestGoddess extends ItemSigilToggleableBase
{
	public ItemSigilHarvestGoddess()
	{
		super("harvest_goddess", 750);
	}

	@Override
	public void onSigilUpdate(ItemStack stack, Level worldIn, Player player, int itemSlot, boolean isSelected)
	{
		if (PlayerHelper.isFakePlayer(player))
			return;

		int range = 3;
		int verticalRange = 2;
		int posX = (int) Math.round(player.getX() - 0.5f);
		int posY = (int) player.getY();
		int posZ = (int) Math.round(player.getZ() - 0.5f);
		if (worldIn instanceof ServerLevel)
		{
			ServerLevel serverWorld = (ServerLevel) worldIn;
			for (int ix = posX - range; ix <= posX + range; ix++)
			{
				for (int iz = posZ - range; iz <= posZ + range; iz++)
				{
					for (int iy = posY - verticalRange; iy <= posY + verticalRange; iy++)
					{
						BlockPos blockPos = new BlockPos(ix, iy, iz);
						BlockState state = worldIn.getBlockState(blockPos);
                        // harvesting here
                        for (IHarvestHandler handler : HarvestRegistry.getHarvestHandlers())
		                {
		                	if (handler.test(world, cropPos, harvestState))
	                		{
				                List<ItemStack> drops = Lists.newArrayList();
			                	if (handler.harvest(world, cropPos, harvestState, drops))
		                		{
			                		for (ItemStack stack : drops)
			                		{
				                		if (stack.isEmpty())
					                		continue;
                        
				                		Containers.dropItemStack(world, cropPos.getX(), cropPos.getY(), cropPos.getZ(), stack);
                                    }
                                }
                            }
                        }
					}
				}
			}
		}
	}
}
