package wayoftime.bloodmagic.common.block.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TankBlockItem extends BlockItem {
    public TankBlockItem(Block block) {
        this(block, new Item.Properties()
                .stacksTo(1)
                .component()
        );
    }

    public TankBlockItem(Block block, Item.Properties properties) {
        super(block, properties);
    }
}
