/*
 * File updated ~ 20 - 11 - 2024 ~ Leaf
 */

package leaf.cosmere.common.registry;

import leaf.cosmere.api.Constants;
import leaf.cosmere.api.EnumUtils;
import leaf.cosmere.api.Metals;
import leaf.cosmere.common.Cosmere;
import leaf.cosmere.common.blocks.MetalBlock;
import leaf.cosmere.common.blocks.MetalOreBlock;
import leaf.cosmere.common.blocks.MetalworkingTableBlock;
import leaf.cosmere.common.registration.impl.BlockDeferredRegister;
import leaf.cosmere.common.registration.impl.BlockRegistryObject;
import leaf.cosmere.common.resource.ore.OreBlockType;
import leaf.cosmere.common.resource.ore.OreType;
import leaf.cosmere.common.util.CosmereEnumUtils;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/* Special thank you to SizableShrimp from the Forge Project discord!
 * Java isn't my first programming language, so I didn't know you could collect and set up items like this!
 * Makes setting up items for metals a breeze~
 */
public class BlocksRegistry
{
	public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(Cosmere.MODID);

	public static final BlockRegistryObject<Block, BlockItem> METALWORKING_TABLE = BLOCKS.register("metalworking_table", MetalworkingTableBlock::new);

	public static final Map<Metals.MetalType, BlockRegistryObject<MetalBlock, BlockItem>> METAL_BLOCKS =
			Arrays.stream(EnumUtils.METAL_TYPES)
					.filter(type -> type.hasMaterialItem() && type != Metals.MetalType.COPPER)
					.collect(Collectors.toMap(
							Function.identity(),
							metalType -> BLOCKS.registerWithRarity(
									metalType.getName() + Constants.RegNameStubs.BLOCK,
									() -> new MetalBlock(metalType),
									metalType.getRarity())));


	public static final Map<OreType, OreBlockType> METAL_ORE =
			Arrays.stream(CosmereEnumUtils.ORE_TYPES)
					.collect(Collectors.toMap(
							Function.identity(),
							oreType ->
							{
								final Metals.MetalType metalType = oreType.getMetalType();
								final BlockRegistryObject<MetalOreBlock, BlockItem> stoneOre = BLOCKS.registerWithRarity(
										metalType.getName() + Constants.RegNameStubs.ORE,
										() -> new MetalOreBlock(metalType),
										metalType.getRarity());
								final BlockRegistryObject<MetalOreBlock, BlockItem> deepslateOre = BLOCKS.registerWithRarity(
										Constants.RegNameStubs.DEEPSLATE + metalType.getName() + Constants.RegNameStubs.ORE,
										() -> new MetalOreBlock(metalType),
										metalType.getRarity());
								return new OreBlockType(stoneOre, deepslateOre);
							}));

}
