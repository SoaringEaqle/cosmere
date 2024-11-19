/*
 * File updated ~ 20 - 11 - 2024 ~ Leaf
 */

package leaf.cosmere.common.resource.ore;

import leaf.cosmere.common.world.height.HeightShape;

//based on BaseOreConfig from Mekanism
// https://github.com/mekanism/Mekanism/blob/a11d886aafc01cc67dd23d24b5e9c87909e88c24/src/main/java/mekanism/common/resource/ore/BaseOreConfig.java#L5
public record BaseOreConfig(String name,
                            int perChunk,
                            double discardChanceOnAirExposure,
                            int maxVeinSize,
                            HeightShape shape,
                            OreAnchor min,
                            OreAnchor max,
                            int plateau)
{

	public BaseOreConfig
	{
		if (plateau > 0 && shape != HeightShape.TRAPEZOID)
		{
			throw new IllegalArgumentException("Plateau are only supported by trapezoid shape");
		}
	}

	public BaseOreConfig(String name, int perChunk, float discardChanceOnAirExposure, int maxVeinSize, HeightShape shape, OreAnchor min, OreAnchor max)
	{
		this(name, perChunk, discardChanceOnAirExposure, maxVeinSize, shape, min, max, 0);
	}
}