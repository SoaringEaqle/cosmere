package leaf.cosmere.api.Investiture;

public interface IInvestiture
{
	public int getBEU();

	public void setBEU(int beu);
	
	public Manifestation[] getApplicableManifestations();
	

	public ISpiritweb getSpiritweb();
	
	protected void close();

}
