package leaf.cosmere.recipe;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.PartialNBTIngredient;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class NbtShapelessRecipeBuilder implements RecipeBuilder {
	private final ShapelessRecipeBuilder base;
	private CompoundTag resultNbt;

	private NbtShapelessRecipeBuilder(RecipeCategory cat, ItemLike result, int count) {
		this.base = ShapelessRecipeBuilder.shapeless(cat, result, count);
	}

	public static NbtShapelessRecipeBuilder shapeless(RecipeCategory cat, ItemLike result) {
		return new NbtShapelessRecipeBuilder(cat, result, 1);
	}

	public static NbtShapelessRecipeBuilder shapeless(RecipeCategory cat, ItemLike result, int count) {
		return new NbtShapelessRecipeBuilder(cat, result, count);
	}

	public NbtShapelessRecipeBuilder resultNbt(CompoundTag nbt) {
		this.resultNbt = (nbt == null || nbt.isEmpty()) ? null : nbt.copy();
		return this;
	}

	public NbtShapelessRecipeBuilder requires(ItemLike item) {
		base.requires(item);
		return this;
	}

	public NbtShapelessRecipeBuilder requires(Ingredient ingredient) {
		base.requires(ingredient);
		return this;
	}

	public NbtShapelessRecipeBuilder requires(Ingredient ingredient, int count) {
		base.requires(ingredient, count);
		return this;
	}

	public NbtShapelessRecipeBuilder requiresPartial(ItemLike item, CompoundTag subsetNbt, int count) {
		return requires(PartialNBTIngredient.of(item, subsetNbt), count);
	}

	public NbtShapelessRecipeBuilder group(@Nullable String group) { base.group(group); return this; }
	public NbtShapelessRecipeBuilder unlockedBy(String name, CriterionTriggerInstance trigger) { base.unlockedBy(name, trigger); return this; }

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
