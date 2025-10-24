/*
* File updated ~ 15 - 9 - 25 ~ Soar
 */

package leaf.cosmere.common.cap.entity;

import leaf.cosmere.api.investiture.*;
import leaf.cosmere.api.manifestation.Manifestation;
import leaf.cosmere.api.spiritweb.ISpiritweb;
import leaf.cosmere.common.Cosmere;
import leaf.cosmere.common.cap.InfusionContainer;
import leaf.cosmere.common.network.packets.SyncPlayerInvestitureContainerMessage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class InvestitureContainer extends InfusionContainer<Entity> implements IInvContainer<Entity>
{


	public static final Capability<IInvContainer<Entity>> CAPABILITY = CapabilityManager.get(new CapabilityToken<>()
	{
	});

	//detect if capability has been set up yet
	private boolean didSetup = false;

	public final Set<SpiritwebInvestiture> swInvestitures = new HashSet<>();

	private int lastAccessedTick; // Used for cleaning. If not accessed after several ticks, clear it out.

	public InvestitureContainer(Entity parent)
	{
		super(parent);
	}

	@Nonnull
	public static LazyOptional<IInfuseContainer<?>> get(LivingEntity entity)
	{
		return entity != null ? entity.getCapability(InvestitureContainer.CAPABILITY, null).cast()
		                      : LazyOptional.empty();
	}

	@Override
	public void tick()
	{
		//if server
		if (!getEntityAssociate().level().isClientSide)
		{
			if(!didSetup)
			{
				syncToClients(null);
				didSetup = true;
			}
			// Clean up and decay at the end of every other second
			if(getEntityAssociate().tickCount % 40 == 39)
			{
				tickServer();
			}
			if(getEntityAssociate().tickCount % 20 == 19)
			{
				for (IInvestiture invest : investitures)
				{
					invest.calculateCurrentMaxDraw();
				}
			}
		}
		else
		{
			tickClient();
		}
	}

	@Override
	public void syncToClients(@Nullable ServerPlayer serverPlayerEntity)
	{
		if (super.getParent() != null && getEntityAssociate().level().isClientSide)
		{
			throw new IllegalStateException("Don't sync client -> server");
		}

		CompoundTag nbt = serializeNBT();

		if (serverPlayerEntity == null)
		{
			Cosmere.packetHandler().sendToAllInWorld(new SyncPlayerInvestitureContainerMessage(this.getEntityAssociate().getId(), nbt), (ServerLevel) getEntityAssociate().level());
		}
		else
		{
			Cosmere.packetHandler().sendTo(new SyncPlayerInvestitureContainerMessage(this.getEntityAssociate().getId(), nbt), serverPlayerEntity);
		}
	}
	@Override
	public void onPlayerClone(PlayerEvent.Clone event, IInvContainer<Entity> oldInvContainer)
	{

		var oldContainer = (InvestitureContainer) oldInvContainer;

		//TODO config options that let you choose what can be transferred


		//forcibly serialize the old web, then deserialize it into the new one
		//before, it was just a copy of whatever was saved the last time it was synced.
		deserializeNBT(oldContainer.serializeNBT().copy());

		if (event.isWasDeath())
		{

		}
	}

	public void tickServer()
	{
		for(IInvestiture invest: investitures)
		{
			if(invest instanceof KineticInvestiture investiture)
			{
				investiture.decay();

			}
			if (invest.getBEU() <= 0)
			{
				investitures.remove(invest);
			}
		}

	}

	public void tickClient()
	{
		//Anything on client side
		//Later phase.
	}


	@Override
	public LazyOptional<ISpiritweb> getSpiritweb()
	{
		if(super.getParent() instanceof LivingEntity ent)
		{
			return SpiritwebCapability.get(ent);
		}
		return LazyOptional.empty();
	}


	@Override
	public CompoundTag serializeNBT()
	{
		if(!investitures.isEmpty())
		{
			ListTag investStored = new ListTag();

			for (IInvestiture investiture : investitures)
			{
				investStored.add(investiture.serializeNBT());
			}
			nbt.put("kinInvest", investStored);
		}
		if(!swInvestitures.isEmpty())
		{
			ListTag swInvest = new ListTag();
			for (SpiritwebInvestiture investiture : swInvestitures)
			{
				swInvest.add(investiture.serializeNBT());
			}
			nbt.put("swInvestitures", swInvest);
		}

		nbt.putInt("lastAccessedTick", lastAccessedTick);

		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag compoundTag)
	{
		if (nbt == null)
		{
			return;
		}

		if(compoundTag.contains("investContainer"))//The item/entity nbt, not the investiture container
		{
			nbt = compoundTag.getCompound("investContainer");
		}
		else
		{
			nbt = compoundTag;
		}

		this.maxBEU = nbt.getDouble("maxBEU");
		this.lastAccessedTick = nbt.getInt("lastAccessedTick");

		if (nbt.contains("kinInvest"))
		{
			ListTag listtag = nbt.getList("kinInvest", Tag.TAG_LIST);
			for (int i = 0; i < listtag.size(); i++)
			{
				CompoundTag sub = listtag.getCompound(i);
				KineticInvestiture temp = KineticInvestiture.buildFromNBT(sub, this);
				investitures.add(temp);

			}

		}
		if(nbt.contains("swInvestitures"))
		{
			ListTag listTag = nbt.getList("swInvestitures", Tag.TAG_LIST);
			for(int i = 0; i < listTag.size(); i++)
			{
				CompoundTag sub = listTag.getCompound(i);
				SpiritwebInvestiture temp = SpiritwebInvestiture.buildFromNBT(sub, this);
				swInvestitures.add(temp);
			}

		}
	}


	@Override
	public HashSet<IInvestiture> availableInvestitures(Manifestation manifest)
	{
		HashSet<IInvestiture> availables = new HashSet<>();
		for (IInvestiture invest : investitures)
		{
			if (invest instanceof KineticInvestiture investiture && investiture.isUsable(manifest))
			{
				availables.add(invest);
			}
		}
		return availables;
	}

	@Override
	public void mergeOrAddInvestiture(IInvestiture invest)
	{
		if (invest instanceof SpiritwebInvestiture swInvest)
		{
			for (SpiritwebInvestiture investiture : swInvestitures)
			{
				if(swInvest.merge(investiture))
				{
					investiture.setBEU(0);
				}
			}
			swInvestitures.add(swInvest);
		}
		else if (invest instanceof KineticInvestiture iInvest)
		{
			for (KineticInvestiture investiture : investitures)
			{
				if(iInvest.merge(investiture))
				{
					investiture.setBEU(0);
				}
			}
			investitures.add(iInvest);
		}
	}

	@Override
	public KineticInvestiture findInvestiture(Manifestation[] appManifest)
	{
		for (KineticInvestiture invest : investitures)
		{
			if (Arrays.equals(invest.getApplicableManifestations(), appManifest))
			{
				return invest;
			}
		}
		return null;
	}

	@Override
	public boolean hasInvestiture(KineticInvestiture investiture)
	{
		return !investitures.stream().filter(investiture1 -> investiture == investiture1).toList().isEmpty();
	}


	public int currentBEUDraw(List<KineticInvestiture> list)
	{
		int sub = 0;
		for(KineticInvestiture invest: list)
		{
			sub += invest.getCurrentMaxDraw();
		}
		return sub;
	}

	@Override
	public double currentBEU()
	{
		int sub = 0;
		for(KineticInvestiture invest: investitures)
		{
			sub+= invest.getBEU();
		}
		return sub;
	}


	@Override
	// Clears out empty investiture objects from the ArrayList and the game memory
	// Objects in use elsewhere will not be removed, and can re-attach themselves later using the "reattach()" method
	public void clean()
	{
		investitures.removeIf(investiture -> investiture.getBEU() == 0);
		System.gc();
	}

	@Override
	public InvHelpers.InvestitureSource containerSource()
	{
		if(super.parent instanceof LivingEntity entity)
		{
			return InvHelpers.InvestitureSource.SELF;
		}
		else if(parent != null)
		{

		}
		return null;
	}

	public double runInvestiturePull(Manifestation manifestation)
	{

		/*Steps
		1. Available list of all investiture that can apply to input.
		2. Create an output variable.
		3. Sum max currently available
		4. If - sum is less than manifestation min
			T. a. return 0.
		5.  for 5x - Start at 1:
			a. If - output >= manifestation max
				T. a. break.
			b. Temp list of all available with the given priority.
			c. If - temp is empty
				T. a. continue;
			d. Find the max currently available beu's for the priority
			e. Create temp variable.
			f. If - the max currently available is less than or equal to manifestation max:
				T. a. For - each in Temp list
					i. add max currently available to temp
				F. a. see below.
			g. Add last pull to output.
		6. clean list.
		7. return output
		 */


		ArrayList<KineticInvestiture> availInvestitures = new ArrayList<>();
		for(IInvestiture invest: availableInvestitures(manifestation))
		{
			if(invest instanceof KineticInvestiture invest1)
			{
				availInvestitures.add(invest1);
			}
		}
		double out = 0;

		//Test to see if the minimum amount of investiture is available
		//If not return
		if(currentBEUDraw(availInvestitures) < manifestation.minInvestitureDraw(getSpiritweb().resolve().get()))
		{
			return 0;
		}


		//Loops through each priority
		for(int i = 1; i <= 5; i++)
		{
			if(out > manifestation.maxInvestitureDraw(getSpiritweb().resolve().get()))
			{
				break;
			}
			//Temporary list of values of given priority
			ArrayList<KineticInvestiture> temp = new ArrayList<>();
			double tempTotal = 0;

			//Adds infusions to temporary list
			for (KineticInvestiture invest : availInvestitures)
			{
				// remove empty investiture sets
				if(invest.getBEU() == 0)
				{
					availInvestitures.remove(invest);
				}
				else if(invest.getPriority() == i)
				{
					temp.add(invest);
					tempTotal += invest.getBEU();
				}
			}
			//Don't need to loop through an empty list.
			if(temp.isEmpty())
			{
				continue;
			}
			double theoryMax = manifestation.maxInvestitureDraw(getSpiritweb().resolve().get()) - out;
			//If current total is less than max, pull all investiture from each source of the priority
			if(tempTotal <= theoryMax)
			{
				for(KineticInvestiture invest: temp)
				{
					out += invest.drain();

				}
			}
			//Else, pull a percentage relative to each investiture's total.
			else
			{

				double tempOut = 0;
				for(KineticInvestiture invest: temp)
				{
					double toDraw =  ((invest.getBEU()/tempTotal) * theoryMax);
					tempOut += invest.removeBEU(toDraw, true);
				}
				int index = 0;

				//Checking that investiture gets pulled correctly
				while(tempOut < theoryMax)
				{
					KineticInvestiture invest = temp.get(index);
					if(invest.getBEU() > 0)
					{
						tempOut += 1;
						invest.removeBEU(1, true);
					}
					if(invest.getBEU() == 0)
					{
						temp.remove(index);
					}
					index = index >= temp.size() ? 0 : index + 1;
				}

				out += tempOut;
			}
		}
		clean();
		return out;
	}

	@Override
	public Entity getEntityAssociate()
	{
		return entityAssociate;
	}
	@Override
	public void setEntityAssociate(Entity entityAssociate)
	{
		this.entityAssociate = entityAssociate;
	}
}
