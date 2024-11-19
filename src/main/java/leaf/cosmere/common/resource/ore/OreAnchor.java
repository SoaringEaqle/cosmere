/*
 * File updated ~ 20 - 11 - 2024 ~ Leaf
 */

package leaf.cosmere.common.resource.ore;

import leaf.cosmere.common.world.height.AnchorType;

//anchor types for ore generation
public record OreAnchor(AnchorType type, int value)
{
	//must by y = x
	public static OreAnchor absolute(int value)
	{
		return new OreAnchor(AnchorType.ABSOLUTE, value);
	}

	// x above or below the bottom of the generating world
	public static OreAnchor aboveBottom(int value)
	{
		return new OreAnchor(AnchorType.ABOVE_BOTTOM, value);
	}

	// x above or below the top of the generating world
	public static OreAnchor belowTop(int value)
	{
		return new OreAnchor(AnchorType.BELOW_TOP, value);
	}
}