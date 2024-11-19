/*
 * File updated ~ 20 - 11 - 2024 ~ Leaf
 */

package leaf.cosmere.common.world.height;

import leaf.cosmere.common.resource.ore.OreAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;


//based on ConfigurableVerticalAnchor from Mekanism
// https://github.com/mekanism/Mekanism/blob/7de496745c721fb15d00d590ddcacf00570f3f1b/src/main/java/mekanism/common/world/height/ConfigurableVerticalAnchor.java#L14
public record ConfigurableVerticalAnchor(Supplier<AnchorType> anchorType, Supplier<Integer> value)
{

	public static ConfigurableVerticalAnchor create(ForgeConfigSpec.Builder builder,
	                                                String path,
	                                                String comment,
	                                                OreAnchor defaultAnchor,
	                                                @Nullable ConfigurableVerticalAnchor minAnchor)
	{
		builder.comment(comment).push(path);
		ForgeConfigSpec.EnumValue<AnchorType> type = builder.comment(
						"Type of anchor.",
						"Absolute -> y = value",
						"Above Bottom -> y = minY + value",
						"Below Top -> y = depth - 1 + minY - value")
				.defineEnum("type", defaultAnchor.type());

		ForgeConfigSpec.Builder valueBuilder = builder.comment("Value used for calculating y for the anchor based on the type.");
		ConfigValue<Integer> value;

		if (minAnchor == null)
		{
			value = valueBuilder.define("value", defaultAnchor.value());
		}
		else
		{
			value = valueBuilder.define("value", defaultAnchor.value(), o ->
			{
				if (o instanceof Integer v)
				{
					return minAnchor.anchorType.get() != type.get() || v >= minAnchor.value.get();
				}
				return false;
			});
		}
		builder.pop();
		return new ConfigurableVerticalAnchor(type, value);
	}

	public int resolveY(WorldGenerationContext context)
	{
		return anchorType.get().resolveY(context, value.get());
	}

	@Override
	public String toString()
	{
		return switch (anchorType.get())
		{
			case ABSOLUTE -> value.get() + " absolute";
			case ABOVE_BOTTOM -> value.get() + " above bottom";
			case BELOW_TOP -> value.get() + " below top";
		};
	}
}