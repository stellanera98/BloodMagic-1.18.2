package wayoftime.bloodmagic.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import wayoftime.bloodmagic.datagen.loot.BlockSubProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class BMLootTableProvider extends LootTableProvider {
    public BMLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Set.of(), List.of(
                new SubProviderEntry(BlockSubProvider::new, LootContextParamSets.BLOCK)
        ), registries);
    }
}
