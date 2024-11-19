/*
 * File updated ~ 20 - 11 - 2024 ~ Leaf
 */

package leaf.cosmere.surgebinding.common.registries;

import leaf.cosmere.common.registration.impl.ConfiguredFeatureDeferredRegister;
import leaf.cosmere.common.registration.impl.PlacedFeatureDeferredRegister;
import leaf.cosmere.surgebinding.common.Surgebinding;

public class SurgebindingFeatures
{
	public static final ConfiguredFeatureDeferredRegister CONFIGURED_FEATURES = new ConfiguredFeatureDeferredRegister(Surgebinding.MODID);
	public static final PlacedFeatureDeferredRegister PLACED_FEATURES = new PlacedFeatureDeferredRegister(Surgebinding.MODID);

}
