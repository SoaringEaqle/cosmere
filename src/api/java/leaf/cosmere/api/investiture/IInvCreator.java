package leaf.cosmere.api.investiture;

import leaf.cosmere.api.Manifestations;


public interface IInvCreator
	//Used when creating new investiture
	//ex: using allomancy, being near perpendicularity, recharging stormlight, etc.
{
	default KineticInvestiture newInvest(IInfuseContainer data)
	{
		return new KineticInvestiture(data, InvHelpers.Shard.PURE,
				InvHelpers.InvestitureSource.DIRECT,
				data.getMaxBEU()-data.currentBEU(),
				Manifestations.ManifestArrayBuilder.getAll());
	}

	KineticInvestiture newInvest(IInfuseContainer data, double beu, double decay);

	KineticInvestiture newInvest(IInfuseContainer data, double beu);
}
