package wayoftime.bloodmagic.common.item;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import wayoftime.bloodmagic.common.block.BlockRitualStone;
import wayoftime.bloodmagic.core.data.Binding;
import wayoftime.bloodmagic.core.data.SoulTicket;
import wayoftime.bloodmagic.ritual.EnumRuneType;
import wayoftime.bloodmagic.util.helper.NetworkHelper;
import wayoftime.bloodmagic.util.helper.TextHelper;

public class ItemBindableInscriptionTool extends ItemBindableBase
{

	private final EnumRuneType type;
	private final int syphon = 100;

	public ItemBindableInscriptionTool(EnumRuneType type)
	{
		super();

		this.type = type;
	}

	@Override
	public InteractionResult useOn(UseOnContext context)
	{
		ItemStack stack = context.getItemInHand();
		BlockPos pos = context.getClickedPos();
		Level world = context.getLevel();
		Player player = context.getPlayer();
		BlockState state = world.getBlockState(pos);
		Binding binding = getBinding(stack);

		if (binding == null)
		{
			return super.useOn(context);
		}

		if (state.getBlock() instanceof BlockRitualStone && !((BlockRitualStone) state.getBlock()).isRuneType(world, pos, type))
		{
			if (player.isCreative() || NetworkHelper.getSoulNetwork(getBinding(stack)).syphonAndDamage(player, SoulTicket.item(stack, world, player, syphon)).isSuccess())
			{
				((BlockRitualStone) state.getBlock()).setRuneType(world, pos, type);
				return InteractionResult.SUCCESS;
			}
		}

		return InteractionResult.FAIL;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag)
	{
		tooltip.add(new TranslatableComponent(TextHelper.localizeEffect("tooltip.bloodmagic.inscriber.desc")).withStyle(ChatFormatting.GRAY));
		super.appendHoverText(stack, world, tooltip, flag);
	}
}
