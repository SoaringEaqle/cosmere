/*
 * File updated ~ 4 - 1 - 2025 ~ Leaf
 */

package leaf.cosmere.example;

import leaf.cosmere.BaseRecipeProvider;
import leaf.cosmere.example.common.Example;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

public class ExampleRecipeGen extends BaseRecipeProvider implements IConditionBuilder
{
	public ExampleRecipeGen(PackOutput output, ExistingFileHelper existingFileHelper)
	{
		super(output, existingFileHelper, Example.MODID);
	}

	@Override
	protected ResourceLocation makeRL(String path)
	{
		return Example.rl(path);
	}

	@Override
	protected void addRecipes(Consumer<FinishedRecipe> consumer)
	{
	}

}
