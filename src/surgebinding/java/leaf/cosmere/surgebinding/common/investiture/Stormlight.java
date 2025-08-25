package leaf.cosmere.surgebinding.common.investiture;

import leaf.cosmere.api.Manifestations;
import leaf.cosmere.api.investiture.IInvestitureContainer;
import leaf.cosmere.api.investiture.Investiture;
import leaf.cosmere.api.investiture.InvestitureHelpers;
import leaf.cosmere.common.cap.entity.SpiritwebCapability;
import leaf.cosmere.surgebinding.common.capabilities.SurgebindingSpiritwebSubmodule;
import leaf.cosmere.surgebinding.common.config.SurgebindingConfigs;

public class Stormlight extends Investiture
{
	public Stormlight(IInvestitureContainer<?> container,
	                  int beu)
	{
		super(container,
				InvestitureHelpers.Shards.HONOR,
				InvestitureHelpers.InvestitureSources.HIGHSTORM,
				beu,
				Manifestations.ManifestArrayBuilder.getAllType(Manifestations.ManifestationTypes.SURGEBINDING));
		int decayRate;
		if(container instanceof SpiritwebCapability)
		{
			SpiritwebCapability sw = (SpiritwebCapability) container;
			SurgebindingSpiritwebSubmodule ssm = (SurgebindingSpiritwebSubmodule) sw.getSubmodule(Manifestations.ManifestationTypes.SURGEBINDING);
			decayRate = (6 - ssm.getIdeal()) * SurgebindingConfigs.SERVER.STORMLIGHT_DRAIN_RATE.get() / 2;
		}
		else
		{
			decayRate = 1;
		}
		super.setDecayRate(decayRate);
	}
}
