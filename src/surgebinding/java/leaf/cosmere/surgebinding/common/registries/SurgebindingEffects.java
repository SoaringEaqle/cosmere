package leaf.cosmere.surgebinding.common.registries;

import leaf.cosmere.api.cosmereEffect.CosmereEffect;
import leaf.cosmere.common.registration.impl.CosmereEffectDeferredRegister;
import leaf.cosmere.common.registration.impl.CosmereEffectRegistryObject;
import leaf.cosmere.surgebinding.common.Surgebinding;
import leaf.cosmere.surgebinding.common.effects.GravitationEffect;

public class SurgebindingEffects
{
	public static final CosmereEffectDeferredRegister EFFECTS = new CosmereEffectDeferredRegister(Surgebinding.MODID);

	public static final CosmereEffectRegistryObject<CosmereEffect> GRAVITATION_EFFECT = EFFECTS.register("gravitation", GravitationEffect::new);

}
