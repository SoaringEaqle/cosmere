package leaf.cosmere.api;

public interface IInvestitureSource
{
	public static final Manifestation[] appManifest;
	
	public Investiture newInvest(ISpiritweb data);
	
	public int addPerTick;
	
	public int addTotal;
	
	public void failFill();
	
}
