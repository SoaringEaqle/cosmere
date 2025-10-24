package leaf.cosmere.api.investiture;

import leaf.cosmere.api.manifestation.Manifestation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Set;

public interface IInfuseContainer<T extends CapabilityProvider<T>> extends INBTSerializable<CompoundTag>
{
	T getParent();

	Entity getEntityAssociate();
	void setEntityAssociate(Entity entityAssociate);

	double currentBEU();

	double getMaxBEU();
	void setMaxBEU(double maxBEU);

	Set<IInvestiture> availableInvestitures(Manifestation manifest);

	void mergeOrAddInvestiture(IInvestiture invest);

	void clean();

	InvHelpers.InvestitureSource containerSource();

	void tick();
	void syncToClients(@Nullable ServerPlayer serverPlayerEntity);
}
