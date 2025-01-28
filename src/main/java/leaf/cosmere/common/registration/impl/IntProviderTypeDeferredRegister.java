/*
 * File updated ~ 20 - 11 - 2024 ~ Leaf
 */

package leaf.cosmere.common.registration.impl;

import com.mojang.serialization.Codec;
import leaf.cosmere.common.registration.WrappedDeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviderType;

import java.util.function.Supplier;

public class IntProviderTypeDeferredRegister extends WrappedDeferredRegister<IntProviderType<?>>
{

	public IntProviderTypeDeferredRegister(String modid)
	{
		super(modid, Registries.INT_PROVIDER_TYPE);
	}

	public <PROVIDER extends IntProvider> IntProviderTypeRegistryObject<PROVIDER> register(String name, Codec<PROVIDER> codec)
	{
		return register(name, () -> () -> codec);
	}

	public <PROVIDER extends IntProvider> IntProviderTypeRegistryObject<PROVIDER> register(String name, Supplier<? extends IntProviderType<PROVIDER>> sup)
	{
		return register(name, sup, IntProviderTypeRegistryObject::new);
	}
}