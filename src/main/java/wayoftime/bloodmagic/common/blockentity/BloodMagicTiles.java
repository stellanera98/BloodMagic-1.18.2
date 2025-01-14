package wayoftime.bloodmagic.common.blockentity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.block.BloodMagicBlocks;

import java.util.Set;

public class BloodMagicTiles {
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, BloodMagic.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BloodTankTile>> BLOOD_TANK_TYPE = TILES.register("blood_tank",
            () -> new BlockEntityType<>(BloodTankTile::new, Set.of(BloodMagicBlocks.BLOOD_TANK.block().get()), null));

    private static void registerTileCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                BLOOD_TANK_TYPE.get(),
                BloodTankTile::getFluidHandler
        );
    }

    public static void register(IEventBus modBus) {
        TILES.register(modBus);
        modBus.addListener(BloodMagicTiles::registerTileCapabilities);
    }
}