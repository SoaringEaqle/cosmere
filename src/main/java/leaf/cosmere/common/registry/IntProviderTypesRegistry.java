/*
 * File updated ~ 20 - 11 - 2024 ~ Leaf
 */

package leaf.cosmere.common.registry;

import leaf.cosmere.common.Cosmere;
import leaf.cosmere.common.registration.impl.IntProviderTypeDeferredRegister;
import leaf.cosmere.common.registration.impl.IntProviderTypeRegistryObject;
import leaf.cosmere.common.world.ConfigurableConstantInt;

public class IntProviderTypesRegistry
{
	public static final IntProviderTypeDeferredRegister INT_PROVIDER_TYPES = new IntProviderTypeDeferredRegister(Cosmere.MODID);

	public static final IntProviderTypeRegistryObject<ConfigurableConstantInt> CONFIGURABLE_CONSTANT = INT_PROVIDER_TYPES.register("configurable_constant", ConfigurableConstantInt.CODEC);
}