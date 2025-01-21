package wayoftime.bloodmagic.datagen.builders.recipe;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;
import wayoftime.bloodmagic.recipe.tiered.EnergyTieredRecipe;
import wayoftime.bloodmagic.recipe.tiered.FluidTieredRecipe;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TieredRecipeBuilder implements RecipeBuilder {
    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    protected String group;
    protected final ItemStack result;
    protected final RecipeCategory category;

    protected final List<String> rows = Lists.newArrayList();
    protected final Map<Character, Ingredient> key = Maps.newLinkedHashMap();

    protected final boolean isFluid;
    protected int primary;
    protected int secondary;

    public TieredRecipeBuilder(RecipeCategory category, ItemStack result, boolean isFluid) {
        this.category = category;
        this.result = result;
        this.isFluid = isFluid;
    }

    public static TieredRecipeBuilder fluid(RecipeCategory category, ItemLike result) {
        return new TieredRecipeBuilder(category, new ItemStack(result, 1), true);
    }

    public static TieredRecipeBuilder energy(RecipeCategory category, ItemLike result) {
        return new TieredRecipeBuilder(category, new ItemStack(result, 1), false);
    }

    public TieredRecipeBuilder primary(int primary) {
        this.primary = primary;
        return this;
    }

    public TieredRecipeBuilder secondary(int secondary) {
        this.secondary = secondary;
        return this;
    }

    public TieredRecipeBuilder define(Character symbol, ItemLike item) {
        return this.define(symbol, Ingredient.of(item));
    }

    public TieredRecipeBuilder define(Character symbol, TagKey<Item> tag) {
        return this.define(symbol, Ingredient.of(tag));
    }

    public TieredRecipeBuilder define(Character symbol, Ingredient ingredient) {
        if (this.key.containsKey(symbol)) {
            throw new IllegalArgumentException("Symbol '" + symbol + "' is already defined!");
        } else if (symbol == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.key.put(symbol, ingredient);
            return this;
        }
    }

    public TieredRecipeBuilder pattern(String pattern) {
        if (!this.rows.isEmpty() && pattern.length() != this.rows.get(0).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.rows.add(pattern);
            return this;
        }
    }

    @Override
    public TieredRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public TieredRecipeBuilder group(@Nullable String groupName) {
        this.group = groupName;
        return this;
    }

    @Override
    public Item getResult() {
        return result.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        ShapedRecipePattern shapedrecipepattern = ShapedRecipePattern.of(key, rows);
        Advancement.Builder advancement$builder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);
        Recipe recipe;
        if (isFluid) {
            saveFluid(recipeOutput, id, shapedrecipepattern, advancement$builder);
        } else {
            saveEnergy(recipeOutput, id, shapedrecipepattern, advancement$builder);
        }
    }

    private void saveFluid(RecipeOutput output, ResourceLocation id, ShapedRecipePattern shapedPattern, Advancement.Builder advancementBuilder) {
        FluidTieredRecipe recipe = new FluidTieredRecipe(CraftingBookCategory.valueOf(this.category.name()), shapedPattern, primary, secondary, result);
        output.accept(id, recipe, advancementBuilder.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void saveEnergy(RecipeOutput output, ResourceLocation id, ShapedRecipePattern shapedPattern, Advancement.Builder advancementBuilder) {
        EnergyTieredRecipe recipe = new EnergyTieredRecipe(CraftingBookCategory.valueOf(this.category.name()), shapedPattern, primary, secondary, result);
        output.accept(id, recipe, advancementBuilder.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }
}
