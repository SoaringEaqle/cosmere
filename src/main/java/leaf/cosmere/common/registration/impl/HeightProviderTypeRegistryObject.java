/*
 * File updated ~ 20 - 11 - 2024 ~ Leaf
 */

package leaf.cosmere.common.registration.impl;

import leaf.cosmere.common.registration.WrappedRegistryObject;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.heightproviders.HeightProviderType;
import net.minecraftforge.registries.RegistryObject;

public class HeightProviderTypeRegistryObject<PROVIDER extends HeightProvider> extends WrappedRegistryObject<HeightProviderType<PROVIDER>>
{
	public HeightProviderTypeRegistryObject(RegistryObject<HeightProviderType<PROVIDER>> registryObject)
	{
		super(registryObject);
	}
}