package leaf.cosmere.surgebinding.common.investiture;

import leaf.cosmere.api.Manifestations;
import leaf.cosmere.api.investiture.Investiture;
import leaf.cosmere.api.investiture.InvestitureContainer;

public class Voidlight extends Investiture
{
	public Voidlight(InvestitureContainer container,
	                 int beu)
	{
		//As we start implementing voidbinding, add that to the list of manifestations.
		//Not much is known about voidlight, but it's here for the future.
		super(container,beu, Manifestations.manifestArrayBuilder.getAllType(Manifestations.ManifestationTypes.SURGEBINDING));
	}
}
