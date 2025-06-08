package leaf.cosmere.common.investiture;

import leaf.cosmere.api.Investiture.IInvestiture;
import leaf.cosmere.api.manifestation.Manifestation;
import leaf.cosmere.api.spiritweb.ISpiritweb;

import java.util.ArrayList;

public class Investiture implements IInvestiture
{

	private final ISpiritweb spiritweb;
	private Manifestation[] applicableManifestations;
	private int priority = 1;
	private int beu;




	public Investiture(ISpiritweb web,
	                   int beu,
	                   Manifestation[] applicableManifestations)
	{

		this.beu = beu;
		this.applicableManifestations = applicableManifestations;
		spiritweb = web;
		spiritweb.addInvestiture(this);
	}

	public Investiture(ISpiritweb web,
	                   int beu,
	                   String manifest)
	{

		this.beu = beu;
		this.applicableManifestations = applicableManifestations;
		spiritweb = web;
		spiritweb.addInvestiture(this);
	}

	public int getBEU()
	{
		return beu;
	}

	public void setBEU(int beu)
	{
		this.beu = beu;
	}

	public int removeBEU(int remove)
	{
		if (remove > beu)
		{
			int ret = remove - beu;
			beu = 0;
			return ret;
		}
		else
		{
			beu -= remove;
			return 0;
		}
	}

	public void addBEU(int add)
	{
		beu += add;
	}

	public int getPriority(){return priority;}

	public void setPriority(int priority)
	{
		this.priority = priority;
	}

	public Manifestation[] getApplicableManifestations()
	{
		return applicableManifestations;
	}

	public ISpiritweb getSpiritweb()
	{
		return spiritweb;
	}

	public boolean isUsable(Manifestation manifest1)
	{
		for(Manifestation manifest: applicableManifestations)
		{
			if(manifest.equals(manifest1))
			{
				return true;
			}
		}
		return false;
	}

	public void merge(Investiture other)
	{
		if(this.getPriority() == other.getPriority()
			&& this.getApplicableManifestations()==(other.getApplicableManifestations())
			&& this.getSpiritweb().equals(other.getSpiritweb()))
		{
			this.beu += other.getBEU();
			//other.close;
		}
	}
	
	//protected void close() {this = null;}

}
