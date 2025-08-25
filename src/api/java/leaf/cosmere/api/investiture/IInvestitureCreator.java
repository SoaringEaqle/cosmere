package leaf.cosmere.api.investiture;

import leaf.cosmere.api.Manifestations;


public interface IInvestitureCreator
	//Used when creating new investiture
	//ex: using allomancy, being near perpendicularity, recharging stormlight, etc.
	//todo: make this
{
	default Investiture newInvest(IInvestitureContainer<?> data)
	{
		Investiture sub = new Investiture(data, InvestitureHelpers.Shards.PURE,
				InvestitureHelpers.InvestitureSources.DIRECT, data.getMaxBEU()-data.currentBEU(),
				Manifestations.ManifestArrayBuilder.getAll());
		return sub;
	}
}
