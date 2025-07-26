package leaf.cosmere.api;

import leaf.cosmere.api.investiture.Investiture;
import leaf.cosmere.api.investiture.InvestitureConstants;
import leaf.cosmere.api.investiture.IInvestitureContainer;

public interface IInvestitureSource
	//Used when creating new investiture
	//ex: using allomancy, being near perpendicularity, recharging stormlight, etc.
	//todo: make this
{
	default Investiture newInvest(IInvestitureContainer data)
	{
		Investiture sub = new Investiture(data, InvestitureConstants.Shards.PURE,
				InvestitureConstants.InvestitureSources.DIRECT, data.getMaxBEU()-data.currentBEU(),
				Manifestations.manifestArrayBuilder.getAll());
		return sub;
	}
}
