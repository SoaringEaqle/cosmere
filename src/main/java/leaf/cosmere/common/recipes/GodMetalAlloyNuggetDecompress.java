package leaf.cosmere.common.recipes;

import leaf.cosmere.api.Metals.MetalType;
import leaf.cosmere.api.providers.IItemProvider;
import leaf.cosmere.common.Cosmere;
import leaf.cosmere.common.items.GodMetalAlloyNuggetItem;
import leaf.cosmere.common.registry.CosmereRecipesRegistry;
import leaf.cosmere.common.registry.ItemsRegistry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class GodMetalAlloyNuggetDecompress extends CustomRecipe {

	public GodMetalAlloyNuggetDecompress(ResourceLocation loc, CraftingBookCategory pCategory)
	{
		super(loc, pCategory);
	}

	@Override
	public boolean matches(CraftingContainer inv, @Nonnull Level world)
	{
		Ingredient INGREDIENT_GOD_METAL_ALLOY_NUG = Ingredient.of(ItemsRegistry.GOD_METAL_ALLOY_NUGGETS.values().stream()
				.flatMap(inner -> inner.values().stream())
				.map(IItemProvider::getItemStack));


		int itemCount = 0;
		GodMetalAlloyNuggetItem godMetalAlloyNuggetItem = null;
		for(int i = 0; i < inv.getContainerSize(); i++)
		{
			ItemStack itemStack = inv.getItem(i);
			if(itemStack.isEmpty()) continue;
			if(!INGREDIENT_GOD_METAL_ALLOY_NUG.test(itemStack)) return false;
			if(!itemStack.isEmpty())
			{
				itemCount++;
				if(itemCount > 1) return false;

				godMetalAlloyNuggetItem = (GodMetalAlloyNuggetItem) itemStack.getItem();
				int currentSize = godMetalAlloyNuggetItem.readMetalAlloySizeNbtData(itemStack);
				if(currentSize == godMetalAlloyNuggetItem.getMinSize()) return false; // No splitting smallest size
				if(currentSize % 2 == 1) return false; // No odd splitting
			}
		}
		if(itemCount == 0) return false;
		
		return true;
	}

	@Override
	public ItemStack assemble(CraftingContainer inv, RegistryAccess pRegistryAccess)
	{
		GodMetalAlloyNuggetItem godMetalAlloyNuggetItem = null;
		int index = 0;
		for(int i = 0; i < inv.getContainerSize(); i++)
		{
			if(!inv.getItem(i).isEmpty())
			{
				godMetalAlloyNuggetItem = (GodMetalAlloyNuggetItem) inv.getItem(i).getItem();
				index = i;
				break;
			}
		}

		MetalType godMetalType = godMetalAlloyNuggetItem.getMetalType();
		MetalType alloyedMetalType = godMetalAlloyNuggetItem.getAlloyedMetalType();
		int currentSize = godMetalAlloyNuggetItem.readMetalAlloySizeNbtData(inv.getItem(index));

		ItemStack itemStack = new ItemStack(ItemsRegistry.GOD_METAL_ALLOY_NUGGETS.get(godMetalType).get(alloyedMetalType));
		GodMetalAlloyNuggetItem item = (GodMetalAlloyNuggetItem) itemStack.getItem();

		item.writeMetalAlloySizeNbtData(itemStack, currentSize / 2);
		itemStack.setCount(2);

		return itemStack;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height)
	{
		// We need a 3x3 grid
		return width * height == 9;
	}

	@Override
	public @Nonnull ResourceLocation getId()
	{
		return new ResourceLocation(Cosmere.MODID, "god_metal_alloy_nugget_compress");
	}

	@Override
	public @Nonnull RecipeSerializer<?> getSerializer()
	{
		return CosmereRecipesRegistry.GOD_METAL_ALLOY_NUGGET_DECOMPRESS.get();
	}


}
