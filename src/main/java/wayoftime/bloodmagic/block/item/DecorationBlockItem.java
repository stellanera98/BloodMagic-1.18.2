package wayoftime.bloodmagic.block.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class DecorationBlockItem extends BlockItem {
    private final List<Component> components;
    public DecorationBlockItem(Block block, Properties properties, List<Component> components) {
        super(block, properties);
        this.components = components;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.addAll(this.components);
    }
}
