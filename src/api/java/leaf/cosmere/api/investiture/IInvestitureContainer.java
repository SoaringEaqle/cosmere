package leaf.cosmere.api.investiture;

public interface IInvestitureContainer
{

	int currentBEU();

	int getMaxBEU();

	void setMaxBEU(int maxBEU);

	// Clears out empty investiture objects from the ArrayList and the game memory
	// Objects in use elsewhere will not be removed, and can re-attach themselves later using the "reattach()" method
	void clean();

}
