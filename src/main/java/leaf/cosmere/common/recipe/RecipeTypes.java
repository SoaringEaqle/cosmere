package leaf.cosmere.common.recipe;

import leaf.cosmere.common.Cosmere;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeTypes
{
	public static final RecipeType<MetalworkingRecipe> METALWORKING = RecipeType.simple(
			new ResourceLocation(Cosmere.MODID, "metalworking"));
}
