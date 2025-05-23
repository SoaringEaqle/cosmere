/*
 * File updated ~ 4 - 1 - 2025 ~ Leaf
 */

package leaf.cosmere.tools;

import leaf.cosmere.BaseRecipeProvider;
import leaf.cosmere.api.CosmereTags;
import leaf.cosmere.api.EnumUtils;
import leaf.cosmere.api.Metals;
import leaf.cosmere.tools.common.CosmereTools;
import leaf.cosmere.tools.common.registries.ToolsItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

public class ToolsRecipeGen extends BaseRecipeProvider implements IConditionBuilder
{
	public ToolsRecipeGen(PackOutput output, ExistingFileHelper existingFileHelper)
	{
		super(output, existingFileHelper, CosmereTools.MODID);
	}

	@Override
	protected ResourceLocation makeRL(String path)
	{
		return CosmereTools.rl(path);
	}

	@Override
	protected void addRecipes(Consumer<FinishedRecipe> consumer)
	{

		for (Metals.MetalType metalType : EnumUtils.METAL_TYPES)
		{
			if (!metalType.hasMaterialItem())
			{
				continue;
			}

			addPickaxeRecipe(consumer, ToolsItems.METAL_PICKAXES.get(metalType), CosmereTags.Items.METAL_INGOT_TAGS.get(metalType));
			addShovelRecipe(consumer, ToolsItems.METAL_SHOVEL.get(metalType), CosmereTags.Items.METAL_INGOT_TAGS.get(metalType));
			addAxeRecipe(consumer, ToolsItems.METAL_AXES.get(metalType), CosmereTags.Items.METAL_INGOT_TAGS.get(metalType));
			addSwordRecipe(consumer, ToolsItems.METAL_SWORDS.get(metalType), CosmereTags.Items.METAL_INGOT_TAGS.get(metalType));
			addHoeRecipe(consumer, ToolsItems.METAL_HOE.get(metalType), CosmereTags.Items.METAL_INGOT_TAGS.get(metalType));

			addArmorRecipes(
					consumer,
					CosmereTags.Items.METAL_INGOT_TAGS.get(metalType),
					ToolsItems.METAL_HELMETS.get(metalType),
					ToolsItems.METAL_CHESTPLATES.get(metalType),
					ToolsItems.METAL_LEGGINGS.get(metalType),
					ToolsItems.METAL_BOOTS.get(metalType)
			);

		}
	}
}
