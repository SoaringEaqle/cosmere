package leaf.cosmere.common;

import leaf.cosmere.api.investiture.Investiture;
import leaf.cosmere.api.investiture.InvestitureContainer;

public class InvestTranfer
	//Used when changing the location of investiture, ex. when moving investiture from a gem to a spiritweb.
{
	Investiture invest1;
	int rate;
	Investiture invest2;

	public InvestTranfer(Investiture invest, int rate, int newDecay, InvestitureContainer toGo)
	{
		this.invest1 = invest;
		this.rate = rate;
		invest2 = new Investiture(toGo,1,invest1.getApplicableManifestations());
	}

	public void tick()
	{
		if(invest1.getBEU()>rate)
		{
			invest1.removeBEU(rate);
			invest2.addBEU(rate);
		}
		else
		{
			invest2.addBEU(invest1.getBEU());
			invest1.removeBEU(rate);
		}
	}

	public void clean()
	{
		if(invest1.getBEU() == 0)
		{
			invest1 = null;
		}
		if(invest2.getBEU() == 0)
		{
			invest2 = null;
		}
	}


}
