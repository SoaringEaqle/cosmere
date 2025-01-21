/*
 * File updated ~ 10 - 10 - 2024 ~ Leaf
 */

package leaf.cosmere.common.registry;

import leaf.cosmere.common.Cosmere;
import leaf.cosmere.common.registration.impl.FeatureDeferredRegister;
import leaf.cosmere.common.registration.impl.FeatureRegistryObject;
import leaf.cosmere.common.world.ResizableOreFeature;
import leaf.cosmere.common.world.ResizableOreFeatureConfig;

public class FeatureRegistry
{
	public static final FeatureDeferredRegister FEATURES = new FeatureDeferredRegister(Cosmere.MODID);

	public static final FeatureRegistryObject<ResizableOreFeatureConfig, ResizableOreFeature> ORE = FEATURES.register("ore", ResizableOreFeature::new);


}
