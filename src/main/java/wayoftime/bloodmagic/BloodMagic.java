package wayoftime.bloodmagic;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import wayoftime.bloodmagic.common.block.BloodMagicBlocks;
import wayoftime.bloodmagic.common.blockentity.BloodMagicTiles;
import wayoftime.bloodmagic.common.fluid.BloodMagicFluids;

@Mod(BloodMagic.MODID)
public class BloodMagic {
    public static final String MODID = "bloodmagic";

    public BloodMagic(IEventBus modBus) {
        BloodMagicFluids.register(modBus);
        BloodMagicBlocks.register(modBus);
        BloodMagicTiles.register(modBus);
    }
}
