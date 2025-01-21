package wayoftime.bloodmagic.datagen.providers;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.fluids.FluidType;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.block.BloodMagicBlocks;
import wayoftime.bloodmagic.fluid.BloodMagicFluids;

public class BMLanguageProvider extends LanguageProvider {
    public BMLanguageProvider(PackOutput output, String locale) {
        super(output, BloodMagic.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add(BloodMagicFluids.LIFE_ESSENCE_TYPE.get(), "Life Essence");
        add(BloodMagicFluids.LIFE_ESSENCE_BUCKET.get(), "Life Essence Bucket");
        add(BloodMagicFluids.LIFE_ESSENCE_BLOCK.get(), "Life Essence");

        add(BloodMagicFluids.DOUBT_TYPE.get(), "Liquid Doubt");
        add(BloodMagicFluids.DOUBT_BUCKET.get(), "Doubt Bucket");
        add(BloodMagicFluids.DOUBT_BLOCK.get(), "Liquid Doubt");

        add(BloodMagicBlocks.BLOOD_TANK.block().get(), "Blood Tank");
        addTooltip("container_tier", "Tier: %d");
        addTooltip("container_tier_missing", "Tier missing!");
        addTooltip("fluid_content", "%s: %d");
        addTooltip("fluid_content_empty", "Empty");
    }

    public void add(FluidType type, String name) {
        add(type.getDescriptionId(), name);
    }

    public void addTooltip(String name, String value) {
        add("tooltip.bloodmagic."+name, value);
    }
}
