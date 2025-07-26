package leaf.cosmere.api.investiture;

import leaf.cosmere.api.manifestation.Manifestation;

public interface IInvestiture
{
	int getBEU();

	void setBEU(int beu);
	
	Manifestation[] getApplicableManifestations();

	InvestitureConstants.Shards getShard();

	InvestitureConstants.InvestitureSources getSource();

	IInvestitureContainer getContainer();

}
