package leaf.cosmere.sandmastery.common.investiture;

import leaf.cosmere.api.Manifestations;
import leaf.cosmere.api.investiture.IInvContainer;
import leaf.cosmere.api.investiture.Investiture;
import leaf.cosmere.api.investiture.InvHelpers;

public class SandmasteryInvestiture extends Investiture
{
	public SandmasteryInvestiture(IInvContainer<?> container, int BEU)
	{
		super(container,
				InvHelpers.Shards.AUTONOMY,
				InvHelpers.InvestitureSources.LUHEL_BOND,
				BEU,
				Manifestations.ManifestArrayBuilder.getAllType(Manifestations.ManifestationTypes.SANDMASTERY));
	}

}
