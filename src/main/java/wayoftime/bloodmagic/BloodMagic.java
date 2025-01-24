package wayoftime.bloodmagic;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import org.slf4j.Logger;
import wayoftime.bloodmagic.block.BMBlocks;
import wayoftime.bloodmagic.blockentity.BMTiles;
import wayoftime.bloodmagic.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.datamap.BMDataMaps;
import wayoftime.bloodmagic.fluid.BMFluids;
import wayoftime.bloodmagic.recipe.BMRecipes;
import wayoftime.bloodmagic.registry.BMRegistries;


@Mod(BloodMagic.MODID)
public class BloodMagic {
    public static final String MODID = "bloodmagic";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BloodMagic(IEventBus modBus) {
        BMDataComponents.register(modBus);
        BMFluids.register(modBus);
        BMBlocks.register(modBus);
        BMTiles.register(modBus);
        BMRecipes.register(modBus);
        BMDataMaps.register(modBus);
        BMRegistries.register(modBus);
    }
}
