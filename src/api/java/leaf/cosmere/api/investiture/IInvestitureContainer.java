package leaf.cosmere.api.investiture;

import leaf.cosmere.api.manifestation.Manifestation;
import leaf.cosmere.api.spiritweb.ISpiritweb;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;

public interface IInvestitureContainer<T> extends INBTSerializable<CompoundTag>
{
	T getParent();
	ISpiritweb getSpiritweb();

	ArrayList<Investiture> availableInvestitures(Manifestation manifest);

	void mergeOrAddInvestiture(IInvestiture invest);
	Investiture findInvestiture(Manifestation[] appManifest);
	boolean hasInvestiture(Investiture investiture);
	int currentBEU();
	int getMaxBEU();
	void setMaxBEU(int maxBEU);

	void clean();
}
