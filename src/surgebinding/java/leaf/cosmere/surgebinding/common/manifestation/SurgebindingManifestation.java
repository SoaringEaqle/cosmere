/*
 * File updated ~ 10 - 2 - 2023 ~ Leaf
 */

package leaf.cosmere.surgebinding.common.manifestation;

import leaf.cosmere.api.EnumUtils;
import leaf.cosmere.api.Manifestations;
import leaf.cosmere.api.Roshar;
import leaf.cosmere.api.manifestation.Manifestation;
import leaf.cosmere.api.spiritweb.ISpiritweb;
import leaf.cosmere.common.cap.entity.SpiritwebCapability;
import leaf.cosmere.surgebinding.common.capabilities.SurgebindingSpiritwebSubmodule;
import leaf.cosmere.surgebinding.common.registries.SurgebindingManifestations;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

public class SurgebindingManifestation extends Manifestation
{
	protected final Roshar.Surges surge;

	public SurgebindingManifestation(Roshar.Surges surge)
	{
		super(Manifestations.ManifestationTypes.SURGEBINDING);
		this.surge = surge;
	}

	@Override
	public int getPowerID()
	{
		return surge.getID();
	}

	@Override
	public boolean isActive(ISpiritweb data)
	{
		//surgebinding is different to most powers.

		final SpiritwebCapability spiritwebCapability = (SpiritwebCapability) data;
		SurgebindingSpiritwebSubmodule sb = (SurgebindingSpiritwebSubmodule) spiritwebCapability.getSubmodule(Manifestations.ManifestationTypes.SURGEBINDING);
		return data.hasManifestation(this) && sb.getStormlight() > 0;
	}

	@Override
	public int modeMax(ISpiritweb data)
	{
		return (int) getStrength(data, false);
	}

	//Testing-> resets max strength to 5
	public void resetStrength(ISpiritweb data)
	{
		if(data.hasManifestation(this))
		{
			if(getStrength(data, true) > 5)
			{
				final Attribute attribute = getAttribute();
				if (attribute == null)
				{
					return;
				}
				AttributeInstance manifestationAttribute = data.getLiving().getAttribute(attribute);

				if (manifestationAttribute != null)
				{
					manifestationAttribute.setBaseValue(5);
				}
			}
		}
	}

	@Override
	public boolean tick(ISpiritweb data)
	{
		if (!isActive(data))
		{
			return false;
		}

		int mode = getMode(data);
		final int cost = Mth.abs(mode);

		SurgebindingSpiritwebSubmodule allo = (SurgebindingSpiritwebSubmodule) ((SpiritwebCapability) data).getSubmodule(Manifestations.ManifestationTypes.SURGEBINDING);

		//don't check every tick.
		LivingEntity livingEntity = data.getLiving();
		boolean isActiveTick = isActiveTick(data);

		//if active do surgebinding
		if (mode > 0)
		{
			applyEffectTick(data);
			return true;
		}

		return false;
	}
}
