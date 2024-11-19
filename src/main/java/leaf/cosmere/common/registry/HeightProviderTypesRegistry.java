/*
 * File updated ~ 20 - 11 - 2024 ~ Leaf
 */

package leaf.cosmere.common.registry;

import leaf.cosmere.common.Cosmere;
import leaf.cosmere.common.registration.impl.HeightProviderTypeDeferredRegister;
import leaf.cosmere.common.registration.impl.HeightProviderTypeRegistryObject;
import leaf.cosmere.common.world.height.ConfigurableHeightProvider;

public class HeightProviderTypesRegistry
{
	public static final HeightProviderTypeDeferredRegister HEIGHT_PROVIDER_TYPES = new HeightProviderTypeDeferredRegister(Cosmere.MODID);

	public static final HeightProviderTypeRegistryObject<ConfigurableHeightProvider> CONFIGURABLE = HEIGHT_PROVIDER_TYPES.register("configurable", ConfigurableHeightProvider.CODEC);
}