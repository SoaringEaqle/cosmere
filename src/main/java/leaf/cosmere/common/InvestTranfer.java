package leaf.cosmere.common;

import leaf.cosmere.api.spiritweb.ISpiritweb;
import leaf.cosmere.common.investiture.Investiture;

public class InvestTranfer
	//Used when changing the location of investiture, ex. when moving investiture from a gem to a spiritweb.
{
	Investiture invest1;
	int rate;
	Investiture invest2;

	public InvestTranfer(Investiture invest, int rate, int newDecay, ISpiritweb toGo)
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

}
