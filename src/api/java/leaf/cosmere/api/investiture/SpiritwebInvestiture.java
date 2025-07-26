package leaf.cosmere.api.investiture;

import leaf.cosmere.api.manifestation.Manifestation;

public class SpiritwebInvestiture implements IInvestiture
{
	private Manifestation[] appManfestations;
	
	private int beu;
	
	private IInvestitureContainer container;

	private InvestitureConstants.Shards shard;
	private InvestitureConstants.InvestitureSources source;
	
	public SpiritwebInvestiture(IInvestitureContainer investitureContainer,
	                            InvestitureConstants.Shards shard,
	                            InvestitureConstants.InvestitureSources source,
	                            Manifestation[] appManifest)
	{
		this.shard = shard;
		this.source = source;
		this.beu = 9 * 15;
		this.appManfestations = appManifest;
		container = investitureContainer;
		container.mergeOrAddInvestiture(this);

	}
	
	public SpiritwebInvestiture(IInvestitureContainer investitureContainer, int beu, Manifestation[] appManifest)
	{
		this.beu = beu;
		this.appManfestations = appManifest;
		container = investitureContainer;
		container.mergeOrAddInvestiture(this);
	}
	
	public int getBEU()
	{return beu;}

	public void setBEU(int beu) 
	{
		this.beu = beu;
	}
	public Manifestation[] getApplicableManifestations()
	{
		return appManfestations;
	}

	@Override
	public InvestitureConstants.Shards getShard()
	{
		return null;
	}

	@Override
	public InvestitureConstants.InvestitureSources getSource()
	{
		return null;
	}


	public IInvestitureContainer getContainer()
	{
		return container;
	}
	
	public int getStrength()
	{
		return beu/15;
	}
	
	public void setStrength(int strength)
	{
		beu = strength * 15;
	}
	
	public void merge(SpiritwebInvestiture other)
	{
		if(this.getApplicableManifestations()==(other.getApplicableManifestations())
			&& this.getContainer().equals(other.getContainer()))
		{
			this.beu += other.getBEU();
			//other.close();
		}
	}
	
	//public void close() {this = null;}

}
