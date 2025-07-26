package leaf.cosmere.api.investiture;

import leaf.cosmere.api.manifestation.Manifestation;

import java.util.ArrayList;
import java.util.List;

public interface IInvestitureEntityContainer extends IInvestitureContainer
{
	ArrayList<Investiture> availableInvestitures(Manifestation manifest);

	default int handleUseInvestiture(Manifestation manifest)
	{
		ArrayList<Investiture> availibles = availableInvestitures(manifest);
		int totalInvestiture = 0;
		for(int i = 5; i > 0; i--)
		{
			int finalI = i;
			List<Investiture> current = availibles.stream().filter(investiture -> finalI == investiture.getPriority()).toList();
			int subTotal = current.stream().mapToInt(Investiture::getBEU).sum();
			if(subTotal + totalInvestiture >= manifest.maxInvestitureDraw(this))
			{
				int quo = (manifest.maxInvestitureDraw(this) - totalInvestiture) / current.size();
				int mod = (manifest.maxInvestitureDraw(this) - totalInvestiture) % current.size();
				current.forEach(invest -> invest.removeBEU(quo));
				current.get(0).removeBEU(mod);
				totalInvestiture =  manifest.maxInvestitureDraw(this);
				break;
			}
			else
			{
				int quo = subTotal / current.size();
				int mod = subTotal % current.size();
				current.forEach(invest -> invest.removeBEU(quo));
				current.get(0).removeBEU(mod);
				totalInvestiture += subTotal;
			}
		}
		return totalInvestiture;
	}

	Investiture findInvestiture(Manifestation[] appManifest);

	boolean hasInvestiture(Investiture investiture);

	void mergeOrAddInvestiture(IInvestiture invest);
}
