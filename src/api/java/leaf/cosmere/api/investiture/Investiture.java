package leaf.cosmere.api.investiture;

import leaf.cosmere.api.manifestation.Manifestation;
import leaf.cosmere.api.spiritweb.ISpiritweb;

public class Investiture implements IInvestiture
{

	private final InvestitureContainer container;
	private Manifestation[] applicableManifestations;
	private int priority = 1;
	private int beu;
	private int decayRate;


	public Investiture(InvestitureContainer container,
	                   int beu,
	                   Manifestation[] applicableManifestations,
	                   int decayRate)
	{

		this.beu = beu;
		this.applicableManifestations = applicableManifestations;
		this.container = container;
		this.container.addInvestiture(this);
		this.decayRate = decayRate;
	}

	public Investiture(InvestitureContainer container,
	                   int beu,
	                   Manifestation[] applicableManifestations)
	{

		this.beu = beu;
		this.applicableManifestations = applicableManifestations;
		this.container = container;
		this.container.addInvestiture(this);
		this.decayRate = 0;
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

	public InvestitureContainer getContainer()
	{
		return container;
	}

	public ISpiritweb getSpiritweb()
	{
		return (ISpiritweb) container;
	}

	public int getDecayRate()
	{
		return decayRate;
	}

	public void setDecayRate(int decayRate)
	{
		this.decayRate = decayRate;
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
			&& this.getContainer().equals(other.getContainer()))
		{
			this.beu += other.getBEU();
			//other.close;
		}
	}
	
	//protected void close() {this = null;}

	public void decay()
	{
		beu -= decayRate;
	}

	public void reattach()
	{
		container.addInvestiture(this);
	}
}
