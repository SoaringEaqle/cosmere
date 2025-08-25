package leaf.cosmere.surgebinding.common.investiture;

import leaf.cosmere.api.Manifestations;
import leaf.cosmere.api.investiture.IInvestitureContainer;
import leaf.cosmere.api.investiture.Investiture;
import leaf.cosmere.api.investiture.InvestitureHelpers;

public class Towerlight extends Investiture
{
	public Towerlight(IInvestitureContainer<?> container,
	                  int beu)
	{
		super(container,
				InvestitureHelpers.Shards.HONOR,
				//Feels most accurate
				InvestitureHelpers.InvestitureSources.MISTS,
				beu,
				Manifestations.ManifestArrayBuilder.getAllType(Manifestations.ManifestationTypes.SURGEBINDING));

		//Decay rate is high, but towerlight constantly renews while in Urithiru.
		//Meaning infinite light in the tower, but none once you leave.
		super.setDecayRate(100000);

	}
}
