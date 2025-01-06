/*
 * File updated ~ 7 - 6 - 2023 ~ Leaf
 */

package leaf.cosmere.allomancy.common.config;

import leaf.cosmere.common.config.ICosmereConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig.Type;

public class AllomancyServerConfig implements ICosmereConfig
{

	private final ForgeConfigSpec configSpec;

	public final ForgeConfigSpec.IntValue GOD_METAL_EAT_STRENGTH_MINIMUM;
	public final ForgeConfigSpec.DoubleValue IRON_STEEL_RANGE;

	// Boost amount for Duralumin and Nicrosil
	//public final ForgeConfigSpec.DoubleValue boostAmount;


	AllomancyServerConfig()
	{
		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
		builder.comment("Allomancy Config. This config is synced between server and client.").push("allomancy");

		GOD_METAL_EAT_STRENGTH_MINIMUM = builder.comment("When the user eats a lerasium nugget, what should their strength in allomancy be set to").defineInRange("godMetalMinimumStrength", 16, 1, 32);
		//can't modify registry objects like that
		//boostAmount = builder.comment("Boost amount for Duralumin and Nicrosil").defineInRange("boostAmount", 0.334D, 0D, 1D);

		// max 10 might seem low, but the scanning thread chugs so hard at 10 that it's unusable and I doubt anyone will try it except for fun >> Gerbagel
		IRON_STEEL_RANGE = builder.comment("Multiplier for iron and steel range. At base strength (9) the range is 9 while burning and 18 while flaring").defineInRange("ironSteelRange", 1.0D, 0D, 10.0D);

		builder.pop();
		configSpec = builder.build();
	}

	@Override
	public String getFileName()
	{
		return "AllomancyServer";
	}

	@Override
	public ForgeConfigSpec getConfigSpec()
	{
		return configSpec;
	}

	@Override
	public Type getConfigType()
	{
		return Type.SERVER;
	}

	@Override
	public void clearCache()
	{
		GOD_METAL_EAT_STRENGTH_MINIMUM.clearCache();
	}
}