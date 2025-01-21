package wayoftime.bloodmagic.datagen.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import wayoftime.bloodmagic.block.BloodMagicBlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BlockSubProvider extends BlockLootSubProvider {
    public BlockSubProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    private final List<Block> specialDropList = List.of(BloodMagicBlocks.BLOOD_TANK.block().get());
    private final List<Block> dropSelfList = List.of();

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> list = new ArrayList<>();
        list.addAll(specialDropList);
        list.addAll(dropSelfList);
        return list;
    }

    @Override
    protected void generate() {
        dropSelfList.forEach(this::dropSelf);
        add(
                BloodMagicBlocks.BLOOD_TANK.block().get(),
                LootTable.lootTable().withPool(
                        this.applyExplosionCondition(BloodMagicBlocks.BLOOD_TANK.block().get(), LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(BloodMagicBlocks.BLOOD_TANK)
                                        .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY))
                                )
                        )
                )
        );
    }
}
