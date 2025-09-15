package leaf.cosmere.surgebinding.common.investiture;

import leaf.cosmere.api.Manifestations;
import leaf.cosmere.api.investiture.IInvContainer;
import leaf.cosmere.api.investiture.Investiture;
import leaf.cosmere.api.investiture.InvHelpers;

public class Towerlight extends Investiture
{
	public Towerlight(IInvContainer<?> container,
	                  int beu)
	{
		super(container,
				InvHelpers.Shards.HONOR,
				//Feels most accurate
				InvHelpers.InvestitureSources.MISTS,
				beu,
				Manifestations.ManifestArrayBuilder.getAllType(Manifestations.ManifestationTypes.SURGEBINDING));

		//Decay rate is high, but towerlight constantly renews while in Urithiru.
		//Meaning infinite light in the tower, but none once you leave.
		super.setDecayRate(100000);

	}
}
