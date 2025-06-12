package leaf.cosmere.surgebinding.common.investiture;

import leaf.cosmere.api.Manifestations;
import leaf.cosmere.api.investiture.Investiture;
import leaf.cosmere.api.investiture.InvestitureContainer;

public class Towerlight extends Investiture
{
	public Towerlight(InvestitureContainer container,
	                  int beu)
	{
		super(container,beu, Manifestations.manifestArrayBuilder.getAllType(Manifestations.ManifestationTypes.SURGEBINDING));

		//Decay rate is high, but towerlight constantly renews while in Urithiru.
		//Meaning infinite light in the tower, but none once you leave.
		super.setDecayRate(100000);

	}
}
