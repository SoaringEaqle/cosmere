package leaf.cosmere.api.investiture;

import leaf.cosmere.api.manifestation.Manifestation;

public interface IInvestiture
{
	int getBEU();

	void setBEU(int beu);
	
	Manifestation[] getApplicableManifestations();

	InvestitureHelpers.Shards getShard();

	InvestitureHelpers.InvestitureSources getSource();

	IInvestitureContainer<?> getContainer();

}
