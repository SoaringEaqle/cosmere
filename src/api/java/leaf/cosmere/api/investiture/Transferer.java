package leaf.cosmere.api.investiture;

import leaf.cosmere.api.manifestation.Manifestation;

public class Transferer
{
	protected Investiture investIn;
	protected Investiture investOut;
	private int rate;
	private int cycles = Integer.MAX_VALUE;
	private int cycNum = 0;

	private int killAmount = 0;

	public Transferer(Investiture investIn, IInvContainer containerOut, int transfeRate, int decayRate, int cycles)
	{
		this(investIn, containerOut, transfeRate, decayRate);
		this.cycles = cycles;
	}
	public Transferer(Investiture investIn, IInvContainer containerOut, int transferRate, int decayRate)
	{
		this.investIn = investIn;
		InvHelpers.Shards shard = investIn.getShard();
		InvHelpers.InvestitureSources source = investIn.getContainer().containerSource();
		Manifestation[] man = investIn.getApplicableManifestations();
		this.investOut = new Investiture(containerOut, shard, source, investIn.removeBEU(transferRate), man, decayRate);
		rate = transferRate;
		InvHelpers.TransferHelper.addTransferer(this);
	}

	public Transferer(Investiture investIn, IInvContainer containerOut, int transferRate)
	{
		this(investIn, containerOut, transferRate, 0);
	}

	protected Transferer(Investiture investIn, Investiture investOut, int transferRate, int cycles)
	{
		this.investOut = investOut;
		this.investIn = investIn;
		this.rate = transferRate;
		this.cycles = cycles;
	}

	public void transfer()
	{
		cycNum++;
		investOut.addBEU(investIn.removeBEU(rate));
		investIn.reattach();
		if (cycNum == cycles || cycNum * rate >= killAmount)
		{
			endTransfer();
		}
	}

	public int getRate()
	{
		return rate;
	}

	public Investiture getInvestIn()
	{
		return investIn;
	}

	public Investiture getInvestOut()
	{
		return investOut;
	}

	public void setKillAmount(int amount)
	{
		killAmount = amount;
	}

	public void endTransfer()
	{
		investIn = null;
		investOut = null;
		InvHelpers.TransferHelper.removeTransferer(this);
	}
	public void clean()
	{
		if(investIn.getBEU() == 0)
		{
			investIn = null;
		}
	}
}
