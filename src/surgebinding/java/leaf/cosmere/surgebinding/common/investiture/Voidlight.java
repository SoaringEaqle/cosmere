package leaf.cosmere.surgebinding.common.investiture;

import leaf.cosmere.api.Manifestations;
import leaf.cosmere.api.investiture.IInvContainer;
import leaf.cosmere.api.investiture.Investiture;
import leaf.cosmere.api.investiture.InvHelpers;

public class Voidlight extends Investiture
{
	public Voidlight(IInvContainer<?> container,
	                 int beu)
	{
		//As we start implementing voidbinding, add that to the list of manifestations.
		//Not much is known about voidlight, but it's here for the future.
		super(container,
				InvHelpers.Shards.ODIUM,
				InvHelpers.InvestitureSources.HIGHSTORM,
				beu,
				Manifestations.ManifestArrayBuilder.getAllType(Manifestations.ManifestationTypes.SURGEBINDING));
	}
}
