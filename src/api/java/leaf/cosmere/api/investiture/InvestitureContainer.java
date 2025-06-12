package leaf.cosmere.api.investiture;

import leaf.cosmere.api.manifestation.Manifestation;

import java.util.ArrayList;

public abstract class InvestitureContainer
{
	private ArrayList<Investiture> investitures = new ArrayList<Investiture>();
	private ArrayList<SpiritwebInvestiture> swInvests = new ArrayList<>();

	public ArrayList<Investiture> availableInvestitures(Manifestation manifest)
	{
		ArrayList<Investiture> availables = new ArrayList<>();
		for (Investiture invest : investitures)
		{
			if (invest.isUsable(manifest))
			{
				availables.add(invest);
			}
		}
		return availables;
	}

	public void addInvestiture(IInvestiture invest)
	{
		if (invest instanceof SpiritwebInvestiture swInvest)
		{
			for (SpiritwebInvestiture investiture : swInvests)
			{
				swInvest.merge(investiture);
			}
			swInvests.add(swInvest);
		}
		else if (invest instanceof Investiture iInvest)
		{
			investitures.add(iInvest);
		}
	}

	public Investiture findInvestiture(Manifestation[] appManifest)
	{
		for (Investiture invest : investitures)
		{
			if (invest.getApplicableManifestations().equals(appManifest))
			{
				return invest;
			}
		}
		return null;
	}

	public int currentBEU()
	{
		int sub = 0;
		for(Investiture invest: investitures)
		{
			sub+= invest.getBEU();
		}
		return sub;
	}


	// Clears out empty investiture objects from the ArrayList and the game memory
	// Objects in use elsewhere will not be removed, and can re-attach themselves later using the "reattach()" method
	public void clean()
	{
		for(Investiture investiture: investitures)
		{
			if(investiture.getBEU() == 0)
			{
				investitures.remove(investiture);
			}
		}
		System.gc();
	}

}
