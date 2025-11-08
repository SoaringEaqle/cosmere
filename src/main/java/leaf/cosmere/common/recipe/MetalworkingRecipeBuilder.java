package leaf.cosmere.common.recipe;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import leaf.cosmere.common.registry.MenuRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.CraftingRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class MetalworkingRecipeBuilder extends CraftingRecipeBuilder implements RecipeBuilder {
	private final RecipeCategory category;
	private final Item result;
	private final int count;
	private final List<JsonElement> ingredients = Lists.newArrayList();
	private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
	@Nullable
	private String group;
	@Nullable
	private CompoundTag resultNbt;

	public MetalworkingRecipeBuilder(RecipeCategory category, ItemLike result, int count) {
		this.category = category;
		this.result = result.asItem();
		this.count = count;
	}

	public static MetalworkingRecipeBuilder metalworking(RecipeCategory category, ItemLike result) {
		return new MetalworkingRecipeBuilder(category, result, 1);
	}

	public static MetalworkingRecipeBuilder metalworking(RecipeCategory category, ItemLike result, int count) {
		return new MetalworkingRecipeBuilder(category, result, count);
	}

	public MetalworkingRecipeBuilder requires(TagKey<Item> tag) {
		return this.requires(Ingredient.of(tag));
	}

	public MetalworkingRecipeBuilder requires(ItemLike item) {
		return this.requires(item, 1);
	}

	public MetalworkingRecipeBuilder requires(ItemLike item, int quantity) {
		for (int i = 0; i < quantity; ++i) {
			this.requires(Ingredient.of(item));
		}
		return this;
	}

	public MetalworkingRecipeBuilder requires(Ingredient ingredient) {
		return this.requires(ingredient, 1);
	}

	public MetalworkingRecipeBuilder requires(Ingredient ingredient, int quantity) {
		for (int i = 0; i < quantity; ++i) {
			this.ingredients.add(ingredient.toJson());
		}
		return this;
	}

	public MetalworkingRecipeBuilder requiresRaw(JsonElement rawIngredientJson) {
		this.ingredients.add(rawIngredientJson);
		return this;
	}

	public MetalworkingRecipeBuilder resultNbt(@Nullable CompoundTag nbt) {
		this.resultNbt = nbt == null ? null : nbt.copy();
		return this;
	}

	public MetalworkingRecipeBuilder unlockedBy(String name, CriterionTriggerInstance trigger) {
		this.advancement.addCriterion(name, trigger);
		return this;
	}

	public MetalworkingRecipeBuilder group(@Nullable String group) {
		this.group = group;
		return this;
	}

	public Item getResult() {
		return this.result;
	}

	public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
		this.ensureValid(id);
		this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(net.minecraft.advancements.AdvancementRewards.Builder.recipe(id)).requirements(RequirementsStrategy.OR);
		consumer.accept(new Result(id, this.result, this.count, this.group == null ? "" : this.group, determineBookCategory(this.category), this.ingredients, this.advancement, id.withPrefix("recipes/" + this.category.getFolderName() + "/"), this.resultNbt));
	}

	private void ensureValid(ResourceLocation id) {
		if (this.ingredients.isEmpty() || this.ingredients.size() > 16) {
			throw new IllegalStateException("Metalworking recipe " + id + " must have 1..16 ingredients");
		}
		if (this.advancement.getCriteria().isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + id);
		}
	}

	public static class Result extends CraftingRecipeBuilder.CraftingResult {
		private final ResourceLocation id;
		private final Item result;
		private final int count;
		private final String group;
		private final List<JsonElement> ingredients;
		private final Advancement.Builder advancement;
		private final ResourceLocation advancementId;
		@Nullable
		private final CompoundTag resultNbt;

		public Result(ResourceLocation id, Item result, int count, String group, CraftingBookCategory category, List<JsonElement> ingredients, Advancement.Builder advancement, ResourceLocation advancementId, @Nullable CompoundTag resultNbt) {
			super(category);
			this.id = id;
			this.result = result;
			this.count = count;
			this.group = group;
			this.ingredients = ingredients;
			this.advancement = advancement;
			this.advancementId = advancementId;
			this.resultNbt = resultNbt;
		}

		public void serializeRecipeData(JsonObject json) {
			super.serializeRecipeData(json);
			if (!this.group.isEmpty()) {
				json.addProperty("group", this.group);
			}
			JsonArray arr = new JsonArray();
			Iterator<JsonElement> it = this.ingredients.iterator();
			while (it.hasNext()) {
				arr.add(it.next());
			}
			json.add("ingredients", arr);
			JsonObject res = new JsonObject();
			res.addProperty("item", BuiltInRegistries.ITEM.getKey(this.result).toString());
			if (this.count > 1) {
				res.addProperty("count", this.count);
			}
			if (this.resultNbt != null && !this.resultNbt.isEmpty()) {
				res.addProperty("nbt", this.resultNbt.toString());
			}
			json.add("result", res);
		}

		public RecipeSerializer<?> getType() {
			return MenuRegistry.METALWORKING_SERIALIZER.get();
		}

		public ResourceLocation getId() {
			return this.id;
		}

		@Nullable
		public JsonObject serializeAdvancement() {
			return this.advancement.serializeToJson();
		}

		@Nullable
		public ResourceLocation getAdvancementId() {
			return this.advancementId;
		}
	}
}
