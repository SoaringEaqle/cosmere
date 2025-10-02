package leaf.cosmere.surgebinding.common.investiture;

import leaf.cosmere.api.investiture.IInvContainer;
import leaf.cosmere.api.investiture.InvHelpers;
import leaf.cosmere.api.investiture.Investiture;
import leaf.cosmere.api.investiture.Transferer;
import leaf.cosmere.api.manifestation.Manifestation;

public class LightTransferer extends Transferer
{
	public LightTransferer(Investiture investIn, IInvContainer containerOut, int transferRate, int decayRate, int cycles)
	{
		super(investIn, new Stormlight(containerOut, transferRate), transferRate, cycles);
	}
}
