package leaf.cosmere.api.investiture;

import leaf.cosmere.api.manifestation.Manifestation;
import net.minecraft.nbt.CompoundTag;

public interface IInvestiture
{
	double getBEU();

	void setBEU(double beu);
	
	Manifestation[] getApplicableManifestations();

	InvHelpers.Shard getShard();

	InvHelpers.InvestitureSource getSource();

	IInfuseContainer<?> getContainer();

	double getCurrentMaxDraw();

	void calculateCurrentMaxDraw();

	CompoundTag serializeNBT();
	void deserializeNBT(CompoundTag nbt);

}
