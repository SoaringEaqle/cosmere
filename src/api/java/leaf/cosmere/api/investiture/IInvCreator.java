package leaf.cosmere.api.investiture;

import leaf.cosmere.api.Manifestations;


public interface IInvCreator
	//Used when creating new investiture
	//ex: using allomancy, being near perpendicularity, recharging stormlight, etc.
	//todo: make this
{
	default Investiture newInvest(IInvContainer<?> data)
	{
		return new Investiture(data, InvHelpers.Shards.PURE,
				InvHelpers.InvestitureSources.DIRECT, data.getMaxBEU()-data.currentBEU(),
				Manifestations.ManifestArrayBuilder.getAll());
	}
}
