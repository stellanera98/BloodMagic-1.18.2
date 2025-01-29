package wayoftime.bloodmagic.datagen.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.fluids.FluidType;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.block.BMBlocks;
import wayoftime.bloodmagic.fluid.BMFluids;
import wayoftime.bloodmagic.util.helper.BlockWithItemHolder;

public class BMLanguageProvider extends LanguageProvider {
    public BMLanguageProvider(PackOutput output, String locale) {
        super(output, BloodMagic.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add(BMFluids.LIFE_ESSENCE_TYPE.get(), "Life Essence");
        add(BMFluids.LIFE_ESSENCE_BUCKET.get(), "Life Essence Bucket");
        add(BMFluids.LIFE_ESSENCE_BLOCK.get(), "Life Essence");

        add(BMFluids.DOUBT_TYPE.get(), "Liquid Doubt");
        add(BMFluids.DOUBT_BUCKET.get(), "Doubt Bucket");
        add(BMFluids.DOUBT_BLOCK.get(), "Liquid Doubt");

        add(BMBlocks.BLOOD_TANK, "Blood Tank");
        addTooltip("container_tier", "Tier: %d");
        addTooltip("container_tier_missing", "Tier missing!");
        addTooltip("fluid_content", "%s: %d");
        addTooltip("fluid_content_empty", "Empty");

        addTooltip("save_for_decoration", "Save for decoration");
        add(BMBlocks.BLOODSTONE, "Bloodstone");
        add(BMBlocks.BLOODSTONE_BRICK, "Bloodstone Brick");

        add(BMBlocks.RUNE_BLANK, "Blank Rune");

        add(BMBlocks.RUNE_SACRIFICE, "Rune of Sacrifice");
        add(BMBlocks.RUNE_SELF_SACRIFICE, "Rune of Self Sacrifice");
        add(BMBlocks.RUNE_CAPACITY, "Rune of Capacity");
        add(BMBlocks.RUNE_CAPACITY_AUGMENTED, "Rune of Augmented Capacity");
        add(BMBlocks.RUNE_DISLOCATION, "Rune of Dislocation");
        add(BMBlocks.RUNE_ORB, "Rune of the Orb");
        add(BMBlocks.RUNE_CHARGING, "Charging Rune");
        add(BMBlocks.RUNE_SPEED, "Speed Rune");
        add(BMBlocks.RUNE_ACCELERATION, "Acceleration Rune");
        add(BMBlocks.RUNE_EFFICIENCY, "Efficiency Rune");

        add(BMBlocks.RUNE_2_SACRIFICE, "Reinforced Rune of Sacrifice");
        add(BMBlocks.RUNE_2_SELF_SACRIFICE, "Reinforced Rune of Self Sacrifice");
        add(BMBlocks.RUNE_2_CAPACITY, "Reinforced Rune of Capacity");
        add(BMBlocks.RUNE_2_CAPACITY_AUGMENTED, "Reinforced Rune of Augmented Capacity");
        add(BMBlocks.RUNE_2_DISLOCATION, "Reinforced Rune of Dislocation");
        add(BMBlocks.RUNE_2_ORB, "Reinforced Rune of the Orb");
        add(BMBlocks.RUNE_2_CHARGING, "Reinforced Charging Rune");
        add(BMBlocks.RUNE_2_SPEED, "Reinforced Speed Rune");
        add(BMBlocks.RUNE_2_ACCELERATION, "Reinforced Acceleration Rune");
        add(BMBlocks.RUNE_2_EFFICIENCY, "Reinforced Efficiency Rune");
    }

    public void add(FluidType type, String name) {
        add(type.getDescriptionId(), name);
    }

    public void add(BlockWithItemHolder<? extends Block, ? extends BlockItem> block, String name) {
        add(block.block().get(), name);
    }

    public void addTooltip(String name, String value) {
        add("tooltip.bloodmagic."+name, value);
    }
}
