package leaf.cosmere.api.investiture;

import leaf.cosmere.api.manifestation.Manifestation;
import leaf.cosmere.api.spiritweb.ISpiritweb;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface IInvContainer<T> extends INBTSerializable<CompoundTag>
{

	T getParent();
	LazyOptional<ISpiritweb> getSpiritweb();

	Entity getEntityAssociate();
	void setEntityAssociate(Entity entityAssociate);

	ArrayList<Investiture> availableInvestitures(Manifestation manifest);

	void mergeOrAddInvestiture(IInvestiture invest);
	Investiture findInvestiture(Manifestation[] appManifest);
	boolean hasInvestiture(Investiture investiture);

	int currentBEU();
	int currentBEUDraw(List<Investiture> list);
	int getMaxBEU();
	void setMaxBEU(int maxBEU);

	void clean();

	InvHelpers.InvestitureSources containerSource();

	int runInvestiturePull(Manifestation manifestation);

	void tick();
	void syncToClients(@Nullable ServerPlayer serverPlayerEntity);
	void onPlayerClone(PlayerEvent.Clone event, IInvContainer<T> oldContainer);


}
