package leaf.cosmere.common.investiture;

import leaf.cosmere.api.Investiture.IInvestiture;

public class SpiritwebInvestiture implements IInvestiture
{
	private Manifestation[] appManfestations;
	
	private int beu;
	
	private ISpiritweb spiritweb;
	
	public SpiritwebInvestiture(ISpiritweb web, Manifestation[] appManifest)
	{
		this.beu = 9 * 15;
		this.applicableManifestations = applicableManifestations;
		spiritweb = web;
		spiritweb.addInvestiture(this);

	}
	
	public SpiritwebInvestiture(ISpiritweb web, int beu, Manifestation[] appManifest)
	{
		this.beu = beu;
		this.applicableManifestations = applicableManifestations;
		spiritweb = web;
		spiritweb.addInvestiture(this);
	}
	
	public int getBEU()
	{return beu;}

	public void setBEU(int beu) 
	{
		this.beu = beu;
	}
	public Manifestation[] getApplicableManifestations();
	{
		return appManifestations;
	}
	

	public ISpiritweb getSpiritweb();
	{return spiritweb;}
	
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
			&& this.getSpiritweb().equals(other.getSpiritweb()))
		{
			this.beu += other.getBEU();
			other.close();
		}
	}
	
	protected void close()
	{
		this = null;
	}

}
