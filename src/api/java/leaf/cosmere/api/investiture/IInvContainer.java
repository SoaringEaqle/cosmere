package leaf.cosmere.api.investiture;

import leaf.cosmere.api.manifestation.Manifestation;
import leaf.cosmere.api.spiritweb.ISpiritweb;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;

public interface IInvContainer<T> extends IInfuseContainer<net.minecraft.world.entity.Entity>
{
	LazyOptional<ISpiritweb> getSpiritweb();




	KineticInvestiture findInvestiture(Manifestation[] appManifest);
	boolean hasInvestiture(KineticInvestiture investiture);

	//int currentBEUDraw(List<KineticInvestiture> list);

	double runInvestiturePull(Manifestation manifestation);

	void onPlayerClone(PlayerEvent.Clone event, IInvContainer<T> oldContainer);


}
