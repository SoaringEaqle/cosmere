package leaf.cosmere.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import leaf.cosmere.common.registry.MenuRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

// Shapeless4x4Recipe.java
public class MetalworkingRecipe implements Recipe<CraftingContainer>
{
	public static final RecipeType<MetalworkingRecipe> TYPE = RecipeTypes.METALWORKING;

	private final ResourceLocation id;
	private final String group;
	private final ItemStack result;
	private final NonNullList<Ingredient> ingredients; // 1..16

	public MetalworkingRecipe(ResourceLocation id, String group, ItemStack result, NonNullList<Ingredient> ingredients)
	{
		this.id = id;
		this.group = group;
		this.result = result;
		this.ingredients = ingredients;
	}

	@Override
	public boolean matches(CraftingContainer input, Level level)
	{
		if (input.getWidth() != 4 || input.getHeight() != 4)
		{
			return false;
		}

		List<ItemStack> inputs = new ArrayList<>();
		for (int i = 0; i < 16; i++)
		{
			ItemStack s = input.getItem(i);
			if (!s.isEmpty())
			{
				inputs.add(s);
			}
		}
		if (inputs.size() != ingredients.size())
		{
			return false;
		}

		List<Ingredient> pool = new ArrayList<>(ingredients);
		outer:
		for (ItemStack s : inputs)
		{
			for (int i = 0; i < pool.size(); i++)
			{
				if (pool.get(i).test(s))
				{
					pool.remove(i);
					continue outer;
				}
			}
			return false;
		}
		return pool.isEmpty();
	}

	@Override
	public ItemStack assemble(CraftingContainer input, RegistryAccess access)
	{
		return result.copy();
	}

	@Override
	public boolean canCraftInDimensions(int w, int h)
	{
		return w * h >= ingredients.size();
	}

	@Override
	public ItemStack getResultItem(RegistryAccess access)
	{
		return result;
	}

	@Override
	public ResourceLocation getId()
	{
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer()
	{
		return MenuRegistry.METALWORKING_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType()
	{
		return TYPE;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingContainer input) {
		NonNullList<ItemStack> list = NonNullList.withSize(input.getContainerSize(), ItemStack.EMPTY);
		for (int i = 0; i < list.size(); ++i) {
			ItemStack stack = input.getItem(i);
			list.set(i, stack.getCraftingRemainingItem());
		}
		return list;
	}

	// ---- Serializer ----
	public static class Serializer implements RecipeSerializer<MetalworkingRecipe>
	{
		@Override
		public MetalworkingRecipe fromJson(ResourceLocation id, JsonObject json)
		{
			String group = GsonHelper.getAsString(json, "group", "");

			JsonArray arr = GsonHelper.getAsJsonArray(json, "ingredients");
			NonNullList<Ingredient> ings = NonNullList.create();
			for (JsonElement el : arr) {
				Ingredient ingredient = Ingredient.fromJson(el);
				if (!ingredient.isEmpty()) {
					ings.add(ingredient);
				}
			}

			if (ings.isEmpty() || ings.size() > 16)
				throw new JsonSyntaxException("Shapeless 4x4 requires 1–16 ingredients");

			ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
			return new MetalworkingRecipe(id, group, result, ings);
		}

		@Override
		public MetalworkingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf)
		{
			String group = buf.readUtf();
			int count = buf.readVarInt();
			NonNullList<Ingredient> ings = NonNullList.withSize(count, Ingredient.EMPTY);
			for (int i = 0; i < count; i++) ings.set(i, Ingredient.fromNetwork(buf));
			ItemStack result = buf.readItem();
			return new MetalworkingRecipe(id, group, result, ings);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buf, MetalworkingRecipe r)
		{
			buf.writeUtf(r.group);
			buf.writeVarInt(r.ingredients.size());
			for (Ingredient i : r.ingredients) i.toNetwork(buf);
			buf.writeItem(r.result);
		}
	}
}
