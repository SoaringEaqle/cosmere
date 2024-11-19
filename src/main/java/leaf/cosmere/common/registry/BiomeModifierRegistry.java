/*
 * File updated ~ 20 - 11 - 2024 ~ Leaf
 */

package leaf.cosmere.common.registry;

import leaf.cosmere.common.Cosmere;
import leaf.cosmere.common.registration.impl.BiomeModifierSerializerDeferredRegister;

public class BiomeModifierRegistry
{
	public static final BiomeModifierSerializerDeferredRegister BIOME_MODIFIER_SERIALIZERS = new BiomeModifierSerializerDeferredRegister(Cosmere.MODID);


}
