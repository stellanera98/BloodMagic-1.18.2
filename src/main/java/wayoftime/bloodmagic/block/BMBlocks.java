package wayoftime.bloodmagic.block;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.block.item.DecorationBlockItem;
import wayoftime.bloodmagic.util.helper.BlockEntityHelper;
import wayoftime.bloodmagic.util.helper.BlockWithItemHolder;
import wayoftime.bloodmagic.util.helper.BlockWithItemRegister;

import java.util.List;
import java.util.function.Function;

public class BMBlocks {
    public static final DeferredRegister<Block> BASIC_BLOCKS = DeferredRegister.createBlocks(BloodMagic.MODID);
    public static final DeferredRegister<Item> BASIC_BLOCK_ITEMS = DeferredRegister.createItems(BloodMagic.MODID);
    public static final BlockWithItemRegister BASIC_REG = new BlockWithItemRegister(BASIC_BLOCKS, BASIC_BLOCK_ITEMS);

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(BloodMagic.MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.createItems(BloodMagic.MODID);
    public static final BlockWithItemRegister BLOCK_REG = new BlockWithItemRegister(BLOCKS, BLOCK_ITEMS);

    public static final BlockWithItemHolder<BloodTankBlock, BlockItem> BLOOD_TANK = BLOCK_REG.register("blood_tank", BloodTankBlock::new, block -> new BlockItem(block, new Item.Properties().stacksTo(1).component(BMDataComponents.CONTAINER_TIER, 1)));
    public static final BlockWithItemHolder<BloodAltarBlock, BlockItem> BLOOD_ALTAR = BLOCK_REG.register("blood_altar", BloodAltarBlock::new);

    private static final BlockBehaviour.Properties rune_properties = BlockBehaviour.Properties.of().strength(2.0F, 5.0F).requiresCorrectToolForDrops();
    private static final Component save_decoration = BlockEntityHelper.translatableHover("tooltip.bloodmagic.save_for_decoration").withStyle(ChatFormatting.ITALIC);
    private static final Function<Block, DecorationBlockItem> save_decoration_getter = block -> new DecorationBlockItem(block, new Item.Properties(), List.of(save_decoration));

    public static final BlockWithItemHolder<Block, DecorationBlockItem> RUNE_BLANK = BASIC_REG.register("rune_blank", rune_properties, save_decoration_getter);
    public static final BlockWithItemHolder<Block, DecorationBlockItem> RUNE_SELF_SACRIFICE = BASIC_REG.register("rune_sacrifice_self", rune_properties, save_decoration_getter);
    public static final BlockWithItemHolder<Block, DecorationBlockItem> RUNE_SACRIFICE = BASIC_REG.register("rune_sacrifice", rune_properties, save_decoration_getter);
    public static final BlockWithItemHolder<Block, DecorationBlockItem> RUNE_CAPACITY = BASIC_REG.register("rune_capacity", rune_properties, save_decoration_getter);
    public static final BlockWithItemHolder<Block, DecorationBlockItem> RUNE_CAPACITY_AUGMENTED = BASIC_REG.register("rune_capacity_augmented", rune_properties, save_decoration_getter);
    public static final BlockWithItemHolder<Block, DecorationBlockItem> RUNE_SPEED = BASIC_REG.register("rune_speed", rune_properties, save_decoration_getter);
    public static final BlockWithItemHolder<Block, DecorationBlockItem> RUNE_ACCELERATION = BASIC_REG.register("rune_acceleration", rune_properties, save_decoration_getter);
    public static final BlockWithItemHolder<Block, DecorationBlockItem> RUNE_ORB = BASIC_REG.register("rune_orb", rune_properties, save_decoration_getter);
    public static final BlockWithItemHolder<Block, DecorationBlockItem> RUNE_DISLOCATION = BASIC_REG.register("rune_dislocation", rune_properties, save_decoration_getter);
    public static final BlockWithItemHolder<Block, DecorationBlockItem> RUNE_CHARGING = BASIC_REG.register("rune_charging", rune_properties, save_decoration_getter);
    public static final BlockWithItemHolder<Block, DecorationBlockItem> RUNE_EFFICIENCY = BASIC_REG.register("rune_efficiency", rune_properties, save_decoration_getter);

    public static final BlockWithItemHolder<Block, DecorationBlockItem> RUNE_2_SELF_SACRIFICE = BASIC_REG.register("rune_2_sacrifice_self", rune_properties, save_decoration_getter);
    public static final BlockWithItemHolder<Block, DecorationBlockItem> RUNE_2_SACRIFICE = BASIC_REG.register("rune_2_sacrifice", rune_properties, save_decoration_getter);
    public static final BlockWithItemHolder<Block, DecorationBlockItem> RUNE_2_CAPACITY = BASIC_REG.register("rune_2_capacity", rune_properties, save_decoration_getter);
    public static final BlockWithItemHolder<Block, DecorationBlockItem> RUNE_2_CAPACITY_AUGMENTED = BASIC_REG.register("rune_2_capacity_augmented", rune_properties, save_decoration_getter);
    public static final BlockWithItemHolder<Block, DecorationBlockItem> RUNE_2_SPEED = BASIC_REG.register("rune_2_speed", rune_properties, save_decoration_getter);
    public static final BlockWithItemHolder<Block, DecorationBlockItem> RUNE_2_ACCELERATION = BASIC_REG.register("rune_2_acceleration", rune_properties, save_decoration_getter);
    public static final BlockWithItemHolder<Block, DecorationBlockItem> RUNE_2_ORB = BASIC_REG.register("rune_2_orb", rune_properties, save_decoration_getter);
    public static final BlockWithItemHolder<Block, DecorationBlockItem> RUNE_2_DISLOCATION = BASIC_REG.register("rune_2_dislocation", rune_properties, save_decoration_getter);
    public static final BlockWithItemHolder<Block, DecorationBlockItem> RUNE_2_CHARGING = BASIC_REG.register("rune_2_charging", rune_properties, save_decoration_getter);
    public static final BlockWithItemHolder<Block, DecorationBlockItem> RUNE_2_EFFICIENCY = BASIC_REG.register("rune_2_efficiency", rune_properties, save_decoration_getter);

    public static void register(IEventBus modBus) {
        BASIC_BLOCKS.register(modBus);
        BASIC_BLOCK_ITEMS.register(modBus);
        BLOCKS.register(modBus);
        BLOCK_ITEMS.register(modBus);
    }
}
