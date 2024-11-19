/*
 * File updated ~ 20 - 11 - 2024 ~ Leaf
 */

package leaf.cosmere.api;

import net.minecraft.core.Direction;

//constant static enum arrays for enums in api that get used often. Save memory allocation where possible
public class EnumUtils
{
	//cosmere
	public static final Metals.MetalType[] METAL_TYPES = Metals.MetalType.values();

	//roshar
	public static final Roshar.Gemstone[] GEMSTONE_TYPES = Roshar.Gemstone.values();
	public static final Roshar.GemSize[] GEM_SIZES = Roshar.GemSize.values();
	public static final Roshar.Surges[] SURGES = Roshar.Surges.values();

	//yes I know it's a vanilla enum
	public static final Direction[] DIRECTIONS = Direction.values();
}
