/*
 * File updated ~ 8 - 10 - 2024 ~ Leaf
 */

package leaf.cosmere.surgebinding;

import leaf.cosmere.api.helpers.RegistryHelper;
import leaf.cosmere.api.providers.IItemProvider;
import leaf.cosmere.surgebinding.common.Surgebinding;
import leaf.cosmere.surgebinding.common.items.*;
import leaf.cosmere.surgebinding.common.registries.SurgebindingItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class SurgebindingItemModelsGen extends ItemModelProvider
{

	public SurgebindingItemModelsGen(PackOutput generator, ExistingFileHelper existingFileHelper)
	{
		super(generator, Surgebinding.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels()
	{
		for (IItemProvider itemRegistryObject : SurgebindingItems.ITEMS.getAllItems())
		{
			String path = itemRegistryObject.getRegistryName().getPath();
			Item item = itemRegistryObject.asItem();

			//blocks have their own model rules
			if (item instanceof BlockItem)
			{
				continue;
			}
			if (item instanceof ForgeSpawnEggItem)
			{
				getBuilder(item.toString()).parent(new ModelFile.UncheckedModelFile("item/template_spawn_egg"));
				continue;
			}
			//otherwise set specific textures based on these item types
			else if (item instanceof ShardbladeItem)
			{
				/*if (item instanceof HonorbladeItem honorbladeItem)
				{
					this.getBuilder(path)
							.parent(new ModelFile.UncheckedModelFile("cosmere:item/test_blade"));
				}*/

				continue;
			}
			else if (item instanceof GemstoneItem gemstoneItem)
			{
				switch (gemstoneItem.getSize())
				{
					case BROAM:
						simpleItem(path, "gemstone_broam");
						break;
					case MARK:
						simpleItem(path, "gemstone_mark");
						break;
					case CHIP:
						simpleItem(path, "gemstone_chip");
						break;
				}
				continue;
			}
			else if (item instanceof ShardplateItem)
			{
				if (item instanceof DeadplateItem)
				{
					if (((DeadplateItem) item).getEquipmentSlot() == EquipmentSlot.HEAD)
					{
						complexItem(path,"shard" + path.substring(4), "shardplate_helmet_visor");
					}
					else
					{
					simpleItem(path, "shard" + path.substring(4));
					}
				}
				else if (item instanceof LivingplateItem)
				{
					if (((LivingplateItem) item).getEquipmentSlot() == EquipmentSlot.HEAD)
					{
						complexItem(path,path.substring(path.indexOf("_") + 1), "shardplate_helmet_visor");
					}
					else
					{
						simpleItem(path, path.substring(path.indexOf("_") + 1));
					}
				}
				continue;
			}
			else if(item instanceof ShardplateCurioItem
			|| item instanceof LivingplateCurioItem)
			{
				complexItem(path, "shardplate_helmet", "shardplate_helmet_visor");
				continue;
			}


			//else normal item texture rules apply
			simpleItem(path, path);
		}

	}

	public String getPath(Supplier<? extends Item> itemSupplier)
	{
		ResourceLocation location = RegistryHelper.get(itemSupplier.get());
		return location.getPath();
	}

	public ItemModelBuilder simpleItem(String path, String texturePath)
	{
		return this.getBuilder(path)
				.parent(new ModelFile.UncheckedModelFile("item/generated"))
				.texture("layer0", modLoc("item/" + texturePath));
	}
	public ItemModelBuilder complexItem(String path, String texturePath1, String texturePath2)
	{
		return this.getBuilder(path)
				.parent(new ModelFile.UncheckedModelFile("item/generated"))
				.texture("layer0", modLoc("item/" + texturePath1))
				.texture("layer1",modLoc("item/" + texturePath2));
	}
}