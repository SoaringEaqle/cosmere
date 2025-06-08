package leaf.cosmere.api.Investiture;

import leaf.cosmere.api.manifestation.Manifestation;
import leaf.cosmere.api.spiritweb.ISpiritweb;

public interface IInvestiture
{
	int getBEU();

	void setBEU(int beu);
	
	Manifestation[] getApplicableManifestations();
	

	ISpiritweb getSpiritweb();

}
