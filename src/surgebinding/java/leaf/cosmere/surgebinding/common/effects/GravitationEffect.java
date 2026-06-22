package leaf.cosmere.surgebinding.common.effects;

import leaf.cosmere.api.Roshar;
import leaf.cosmere.api.cosmereEffect.CosmereEffectInstance;
import leaf.cosmere.api.helpers.EffectsHelper;
import leaf.cosmere.surgebinding.common.registries.SurgebindingEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class GravitationEffect extends SurgebindingEffectBase
{
	public GravitationEffect()
	{
		super(Roshar.Surges.GRAVITATION);
	}


}
