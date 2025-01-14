package wayoftime.bloodmagic.common.util.helper;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class BlockWithItemRegister {
    private final DeferredRegister<Block> BLOCK;
    private final DeferredRegister<Item> ITEM;

    public BlockWithItemRegister(DeferredRegister<Block> block, DeferredRegister<Item> item) {
        BLOCK = block;
        ITEM = item;
    }

    public <B extends Block, I extends BlockItem> BlockWithItemHolder<B, I> register(String name, Supplier<B> block, Function<B, I> item) {
        DeferredHolder<Block, B> regBlock = BLOCK.register(name, block);
        DeferredHolder<Item, I> regItem = ITEM.register(name, () -> item.apply(regBlock.get()));
        return new BlockWithItemHolder<B, I>(regBlock, regItem);
    }

    public <B extends Block> BlockWithItemHolder<B, BlockItem> register(String name, Supplier<B> block, Item.Properties properties) {
        return register(name, block, BlockItem::new, properties);
    }

    public <B extends Block, I extends BlockItem> BlockWithItemHolder<B, I> register(String name, Supplier<B> block, BiFunction<B, Item.Properties, I> item, Item.Properties properties) {
        return register(name, block, reg -> item.apply(reg, properties));
    }
}
