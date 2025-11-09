package leaf.cosmere.recipe;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.PartialNBTIngredient;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class NbtShapedRecipeBuilder implements RecipeBuilder {
	private final ShapedRecipeBuilder base;
	private CompoundTag resultNbt;

	private NbtShapedRecipeBuilder(RecipeCategory cat, ItemLike result, int count) {
		this.base = ShapedRecipeBuilder.shaped(cat, result, count);
	}

	public static NbtShapedRecipeBuilder shaped(RecipeCategory cat, ItemLike result) {
		return new NbtShapedRecipeBuilder(cat, result, 1);
	}
	public static NbtShapedRecipeBuilder shaped(RecipeCategory cat, ItemLike result, int count) {
		return new NbtShapedRecipeBuilder(cat, result, count);
	}

	public NbtShapedRecipeBuilder resultNbt(CompoundTag nbt) {
		this.resultNbt = (nbt == null || nbt.isEmpty()) ? null : nbt.copy();
		return this;
	}

	public NbtShapedRecipeBuilder define(char symbol, Ingredient ingredient) {
		base.define(symbol, ingredient);
		return this;
	}
	public NbtShapedRecipeBuilder define(char symbol, ItemLike item) {
		base.define(symbol, item);
		return this;
	}
	public NbtShapedRecipeBuilder define(char symbol, TagKey<Item> tag) {
		base.define(symbol, tag);
		return this;
	}

	public NbtShapedRecipeBuilder definePartial(char symbol, ItemLike item, CompoundTag subsetNbt) {
		return define(symbol, PartialNBTIngredient.of(item, subsetNbt));
	}

	public NbtShapedRecipeBuilder pattern(String row) { base.pattern(row); return this; }
	public NbtShapedRecipeBuilder group(@Nullable String group) { base.group(group); return this; }
	public NbtShapedRecipeBuilder unlockedBy(String name, CriterionTriggerInstance trigger) { base.unlockedBy(name, trigger); return this; }

	@Override public Item getResult() { return base.getResult(); }

	@Override
	public void save(Consumer<FinishedRecipe> out, ResourceLocation id) {
		base.save(fr -> out.accept(wrap(fr, resultNbt)), id);
	}

	private static FinishedRecipe wrap(FinishedRecipe inner, @Nullable CompoundTag resultNbt) {
		if (resultNbt == null || resultNbt.isEmpty()) return inner;
		return new FinishedWithResultNbt(inner, resultNbt.copy());
	}

	private static final class FinishedWithResultNbt implements FinishedRecipe {
		private final FinishedRecipe inner;
		private final CompoundTag nbt;

		FinishedWithResultNbt(FinishedRecipe inner, CompoundTag nbt) {
			this.inner = inner;
			this.nbt = nbt;
		}

		@Override public void serializeRecipeData(JsonObject json) {
			inner.serializeRecipeData(json);
			JsonObject result = json.getAsJsonObject("result");
			if (result == null) {
				result = new JsonObject();
				json.add("result", result);
			}
			result.addProperty("nbt", nbt.toString());
		}

		@Override public ResourceLocation getId() { return inner.getId(); }
		@Override public net.minecraft.world.item.crafting.RecipeSerializer<?> getType() { return inner.getType(); }
		@Override public @Nullable JsonObject serializeAdvancement() { return inner.serializeAdvancement(); }
		@Override public @Nullable ResourceLocation getAdvancementId() { return inner.getAdvancementId(); }
	}
}
