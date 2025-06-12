package leaf.cosmere.api;

import leaf.cosmere.api.investiture.Investiture;
import leaf.cosmere.api.investiture.InvestitureContainer;

public interface IInvestitureSource
	//Used when creating new investiture
	//ex: using allomancy, being near perpendicularity, recharging stormlight, etc.
	//todo: make this
{
	default Investiture newInvest(InvestitureContainer data)
	{
		Investiture sub = new Investiture(data, data.maxBEU()-data.currentBEU(),Manifestations.manifestArrayBuilder.getAll());
		return sub;
	}
}
