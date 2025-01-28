/*
 * File updated ~ 20 - 11 - 2024 ~ Leaf
 */

package leaf.cosmere.common.registration.impl;

import com.mojang.serialization.Codec;
import leaf.cosmere.common.registration.WrappedDeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.heightproviders.HeightProviderType;

import java.util.function.Supplier;

public class HeightProviderTypeDeferredRegister extends WrappedDeferredRegister<HeightProviderType<?>>
{
	public HeightProviderTypeDeferredRegister(String modid)
	{
		super(modid, Registries.HEIGHT_PROVIDER_TYPE);
	}

	public <PROVIDER extends HeightProvider> HeightProviderTypeRegistryObject<PROVIDER> register(String name, Codec<PROVIDER> codec)
	{
		return register(name, () -> () -> codec);
	}

	public <PROVIDER extends HeightProvider> HeightProviderTypeRegistryObject<PROVIDER> register(String name, Supplier<? extends HeightProviderType<PROVIDER>> sup)
	{
		return register(name, sup, HeightProviderTypeRegistryObject::new);
	}
}