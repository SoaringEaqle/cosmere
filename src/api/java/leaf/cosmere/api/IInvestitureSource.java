package leaf.cosmere.api;

import leaf.cosmere.api.manifestation.Manifestation;

public interface IInvestitureSource
	//Used when creating new investiture
	//ex: using allomancy, being near perpendicularity, recharging stormlight, etc.
	//todo: make this
{
	public static final Manifestation[] appManifest;
	
	public Investiture newInvest(ISpiritweb data);
	

	
}
