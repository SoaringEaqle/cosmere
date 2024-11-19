/*
 * File updated ~ 20 - 11 - 2024 ~ Leaf
 */

package leaf.cosmere.common.world.height;

import net.minecraft.world.level.levelgen.WorldGenerationContext;

//based on AnchorType from Mekanism
// https://github.com/mekanism/Mekanism/blob/a11d886aafc01cc67dd23d24b5e9c87909e88c24/src/main/java/mekanism/common/world/height/AnchorType.java#L9
public enum AnchorType
{
	ABSOLUTE((context, value) -> value),
	ABOVE_BOTTOM((context, value) -> context.getMinGenY() + value),
	BELOW_TOP((context, value) -> context.getGenDepth() - 1 + context.getMinGenY() - value);

	private final YResolver yResolver;

	AnchorType(YResolver yResolver)
	{
		this.yResolver = yResolver;
	}

	public int resolveY(WorldGenerationContext context, int value)
	{
		return yResolver.resolve(context, value);
	}

	@FunctionalInterface
	private interface YResolver
	{
		int resolve(WorldGenerationContext context, int value);
	}
}