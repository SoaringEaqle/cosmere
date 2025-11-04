/*
 * File updated ~ 10 - 10 - 2024 ~ Leaf
 */

package leaf.cosmere.common.registry;

import leaf.cosmere.api.EnumUtils;
import leaf.cosmere.api.Metals.MetalType;
import leaf.cosmere.api.providers.IBlockProvider;
import leaf.cosmere.api.providers.IItemProvider;
import leaf.cosmere.common.Cosmere;
import leaf.cosmere.common.items.InvestedMetalNuggetItem;
import leaf.cosmere.common.registration.impl.CreativeTabDeferredRegister;
import leaf.cosmere.common.registration.impl.CreativeTabRegistryObject;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

import java.util.Arrays;
import java.util.HashSet;

public class CreativeTabsRegistry
{
	public static final CreativeTabDeferredRegister CREATIVE_TABS = new CreativeTabDeferredRegister(Cosmere.MODID, CreativeTabsRegistry::addToExistingTabs);


	public static final CreativeTabRegistryObject ITEMS =
			CREATIVE_TABS.registerMain(
					Component.translatable("tabs." + Cosmere.MODID + ".items"),
					ItemsRegistry.GUIDE,
					builder ->
							builder.withSearchBar()//Allow our tabs to be searchable for convenience purposes
									.displayItems((displayParameters, output) ->
									{
										CreativeTabDeferredRegister.addToDisplay(ItemsRegistry.ITEMS, output);
										CreativeTabDeferredRegister.addToDisplay(BlocksRegistry.BLOCKS, output);
										addLerasiumAlloyNuggets(output);
										addLerasatiumAlloyNuggets(output);
									})
			);


	private static void addLerasiumAlloyNuggets(CreativeModeTab.Output output)
	{
		for (MetalType metalType : Arrays.asList(EnumUtils.METAL_TYPES).subList(0, 16))
		{
			if (metalType.hasFeruchemicalEffect())
			{
				final InvestedMetalNuggetItem item = ItemsRegistry.INVESTED_METAL_NUGGET.get();

				HashSet<MetalType> metalTypes = new HashSet<>();
				metalTypes.add(MetalType.LERASIUM);
				metalTypes.add(metalType);

				item.addFilled(output,metalTypes , 16);
			}
		}
	}

	private static void addLerasatiumAlloyNuggets(CreativeModeTab.Output output)
	{
		for (MetalType metalType : Arrays.asList(EnumUtils.METAL_TYPES).subList(0, 16))
		{
			if (metalType.hasFeruchemicalEffect())
			{
				final InvestedMetalNuggetItem item = (InvestedMetalNuggetItem) ItemsRegistry.INVESTED_METAL_NUGGET.get();
				HashSet<MetalType> metalTypes = new HashSet<>();
				metalTypes.add(MetalType.LERASATIUM);
				metalTypes.add(metalType);

				item.addFilled(output,metalTypes , 16);
			}
		}
	}


	private static void addToExistingTabs(BuildCreativeModeTabContentsEvent event)
	{
		ResourceKey<CreativeModeTab> tabKey = event.getTabKey();
		if (tabKey == CreativeModeTabs.BUILDING_BLOCKS)
		{
			CreativeTabDeferredRegister.addToDisplay(event, BlocksRegistry.METALWORKING_TABLE);

			for (var ore : BlocksRegistry.METAL_BLOCKS.values())
			{
				CreativeTabDeferredRegister.addToDisplay(event, ore);
			}
		}
		else if (tabKey == CreativeModeTabs.NATURAL_BLOCKS)
		{
			for (var ore : BlocksRegistry.METAL_ORE.values())
			{
				CreativeTabDeferredRegister.addToDisplay(event, ore.stone());
				CreativeTabDeferredRegister.addToDisplay(event, ore.deepslate());
			}
		}
		else if (tabKey == CreativeModeTabs.FUNCTIONAL_BLOCKS)
		{
			for (IBlockProvider blockProvider : BlocksRegistry.BLOCKS.getAllBlocks())
			{
				Block block = blockProvider.getBlock();
				//if (block == valid)
				//{
				//	CreativeTabDeferredRegister.addToDisplay(event, block);
				//}
			}
		}
		else if (tabKey == CreativeModeTabs.REDSTONE_BLOCKS)
		{

		}
		else if (tabKey == CreativeModeTabs.TOOLS_AND_UTILITIES)
		{

		}
		else if (tabKey == CreativeModeTabs.COMBAT)
		{

		}
		else if (tabKey == CreativeModeTabs.FOOD_AND_DRINKS)
		{
			//ItemsRegistry.SOME_FOOD.get().addItems(event);
		}
		else if (tabKey == CreativeModeTabs.SPAWN_EGGS)
		{

		}
		else if (tabKey == CreativeModeTabs.INGREDIENTS)
		{
			for (IItemProvider item : ItemsRegistry.METAL_INGOTS.values())
			{
				CreativeTabDeferredRegister.addToDisplay(event, item);
			}
		}
	}

}
