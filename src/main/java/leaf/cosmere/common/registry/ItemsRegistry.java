/*
 * File updated ~ 20 - 11 - 2024 ~ Leaf
 */

package leaf.cosmere.common.registry;

import leaf.cosmere.api.Constants.RegNameStubs;
import leaf.cosmere.api.EnumUtils;
import leaf.cosmere.api.Metals;
import leaf.cosmere.common.Cosmere;
import leaf.cosmere.common.items.GuideItem;
import leaf.cosmere.common.items.MetalIngotItem;
import leaf.cosmere.common.items.MetalNuggetItem;
import leaf.cosmere.common.items.MetalRawOreItem;
import leaf.cosmere.common.registration.impl.ItemDeferredRegister;
import leaf.cosmere.common.registration.impl.ItemRegistryObject;
import net.minecraft.world.item.Item;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ItemsRegistry
{
	public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(Cosmere.MODID);

	//other items

	public static final ItemRegistryObject<GuideItem> GUIDE = ITEMS.register("guide", GuideItem::new);


	//Mass items gen


	public static final Map<Metals.MetalType, ItemRegistryObject<Item>> METAL_RAW_ORE =
			Arrays.stream(EnumUtils.METAL_TYPES)
					.filter(Metals.MetalType::hasOre)
					.collect(Collectors.toMap(
							Function.identity(),
							type -> ITEMS.register(
									RegNameStubs.RAW + type.getName() + RegNameStubs.ORE,
									() -> new MetalRawOreItem(type)
							)));

	public static final Map<Metals.MetalType, ItemRegistryObject<Item>> METAL_RAW_BLEND =
			Arrays.stream(EnumUtils.METAL_TYPES)
					.filter(Metals.MetalType::isAlloy)
					.collect(Collectors.toMap(
							Function.identity(),
							type -> ITEMS.register(
									type.getName() + RegNameStubs.BLEND,
									() -> new MetalRawOreItem(type)
							)));

	public static final Map<Metals.MetalType, ItemRegistryObject<Item>> METAL_NUGGETS =
			Arrays.stream(EnumUtils.METAL_TYPES)
					.filter(type -> type.hasMaterialItem() || type == Metals.MetalType.COPPER)      // I'm sorry for this Leaf :( >> Gerbagel
					.collect(Collectors.toMap(
							Function.identity(),
							type -> ITEMS.register(
									type.getName() + RegNameStubs.NUGGET,
									() -> new MetalNuggetItem(type)
							)));

	public static final Map<Metals.MetalType, ItemRegistryObject<MetalIngotItem>> METAL_INGOTS =
			Arrays.stream(EnumUtils.METAL_TYPES)
					.filter(type -> type.hasMaterialItem() && type != Metals.MetalType.COPPER)
					.collect(Collectors.toMap(
							Function.identity(),
							type -> ITEMS.register(
									type.getName() + RegNameStubs.INGOT,
									() -> new MetalIngotItem(type)
							)));


}
