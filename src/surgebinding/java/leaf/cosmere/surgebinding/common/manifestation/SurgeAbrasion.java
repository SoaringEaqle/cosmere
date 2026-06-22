/*
 * File updated ~ 8 - 10 - 2022 ~ Leaf
 */

package leaf.cosmere.surgebinding.common.manifestation;

import leaf.cosmere.api.Roshar;
import leaf.cosmere.api.spiritweb.ISpiritweb;
import leaf.cosmere.surgebinding.common.capabilities.SurgebindingSpiritwebSubmodule;
import net.minecraft.world.entity.LivingEntity;

public class SurgeAbrasion extends SurgebindingManifestation
{
	public SurgeAbrasion(Roshar.Surges surge)
	{
		super(surge);
	}


	//change frictional force
	@Override
	public void applyEffectTick(ISpiritweb data)
	{
		float friction =  1F - ((float)getMode(data)/10F);
		LivingEntity entity = data.getLiving();
		entity.handleRelativeFrictionAndCalculateMovement(entity.getDeltaMovement(),friction);
		SurgebindingSpiritwebSubmodule.getSubmodule(data).adjustStormlight(-20, true);
	}

}
