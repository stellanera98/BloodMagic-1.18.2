package wayoftime.bloodmagic;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import wayoftime.bloodmagic.block.BloodMagicBlocks;
import wayoftime.bloodmagic.blockentity.BloodMagicTiles;
import wayoftime.bloodmagic.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.fluid.BloodMagicFluids;
import wayoftime.bloodmagic.recipe.BMRecipes;


@Mod(BloodMagic.MODID)
public class BloodMagic {
    public static final String MODID = "bloodmagic";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BloodMagic(IEventBus modBus) {
        BMDataComponents.register(modBus);
        BloodMagicFluids.register(modBus);
        BloodMagicBlocks.register(modBus);
        BloodMagicTiles.register(modBus);
        BMRecipes.register(modBus);
    }
}
