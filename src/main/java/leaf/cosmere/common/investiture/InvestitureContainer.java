/*
* File updated ~ 15 - 9 - 25 ~ Soar
 */

package leaf.cosmere.common.investiture;

import leaf.cosmere.api.CosmereAPI;
import leaf.cosmere.api.ISpiritwebSubmodule;
import leaf.cosmere.api.investiture.*;
import leaf.cosmere.api.manifestation.Manifestation;
import leaf.cosmere.api.spiritweb.ISpiritweb;
import leaf.cosmere.common.Cosmere;
import leaf.cosmere.common.cap.entity.SpiritwebCapability;
import leaf.cosmere.common.items.ChargeableItemBase;
import leaf.cosmere.common.network.packets.SyncPlayerInvestitureContainerMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class InvestitureContainer<T extends net.minecraftforge.common.capabilities.CapabilityProvider<T>> implements IInvContainer<T>
{

	public static final Capability<IInvContainer> CAPABILITY = CapabilityManager.get(new CapabilityToken<>()
	{
	});

	//detect if capability has been set up yet
	private boolean didSetup = false;

	private final T parent;
	private Entity entityAssociate;


	private CompoundTag nbt;

	public final List<Investiture> investitures = new ArrayList<>();
	public final List<SpiritwebInvestiture> swInvestitures = new ArrayList<>();
	private int maxBEU;
	private int lastAccessedTick; // Used for cleaning. If not accessed after several ticks, clear it out.

	public InvestitureContainer(T parent)
	{
		this.parent = parent;
		if(this.parent instanceof Entity entity)
		{
			entityAssociate = entity;
		}
	}

	@Nonnull
	public static LazyOptional<IInvContainer> get(LivingEntity entity)
	{
		return entity != null ? entity.getCapability(InvestitureContainer.CAPABILITY, null)
		                      : LazyOptional.empty();
	}

	@Nonnull
	public static LazyOptional<IInvContainer> get(ItemStack stack)
	{
		return stack != null ? stack.getCapability(InvestitureContainer.CAPABILITY, null)
		                     : LazyOptional.empty();
	}

	@Nonnull
	public static LazyOptional<IInvContainer> get(BlockEntity entity)
	{
		return entity != null ? entity.getCapability(InvestitureContainer.CAPABILITY, null)
		                      : LazyOptional.empty();
	}

	@Nonnull
	public static LazyOptional<IInvContainer> get(net.minecraftforge.common.capabilities.CapabilityProvider<?> object)
	{
		if(object instanceof LivingEntity entity)
		{
			return get(entity);
		}
		else if(object instanceof ItemStack stack)
		{
			return get(stack);
		}
		else if(object instanceof BlockEntity entity)
		{
			return get(entity);
		}
		else
		{
			return object != null ? object.getCapability(InvestitureContainer.CAPABILITY, null)
			                      : LazyOptional.empty();
		}
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
		}
		else
		{
			tickClient();
		}
	}

	@Override
	public void syncToClients(@Nullable ServerPlayer serverPlayerEntity)
	{
		if (parent != null && getEntityAssociate().level().isClientSide)
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
	public void onPlayerClone(PlayerEvent.Clone event, IInvContainer<T> oldInvContainer)
	{

		var oldContainer = (InvestitureContainer<T>) oldInvContainer;

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
		for(Investiture invest: investitures)
		{
			invest.decay();
			if(invest.getBEU() <= 0)
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
	public T getParent()
	{
		return parent;
	}

	@Override
	public LazyOptional<ISpiritweb> getSpiritweb()
	{
		if(parent instanceof LivingEntity ent)
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

			for (Investiture investiture : investitures)
			{
				investStored.add(investiture.serializeNBT());
			}
			nbt.put("investitures", investStored);
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

		nbt.putInt("maxBEU", maxBEU);

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

		this.maxBEU = nbt.getInt("maxBEU");
		this.lastAccessedTick = nbt.getInt("lastAccessedTick");

		if (nbt.contains("investitures"))
		{
			ListTag listtag = nbt.getList("investitures", Tag.TAG_LIST);
			for (int i = 0; i < listtag.size(); i++)
			{
				CompoundTag sub = listtag.getCompound(i);
				Investiture temp = Investiture.buildFromNBT(sub, this);
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
	public ArrayList<Investiture> availableInvestitures(Manifestation manifest)
	{
		ArrayList<Investiture> availables = new ArrayList<>();
		for (Investiture invest : investitures)
		{
			if (invest.isUsable(manifest))
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
				swInvest.merge(investiture);
			}
			swInvestitures.add(swInvest);
		}
		else if (invest instanceof Investiture iInvest)
		{
			iInvest.merge(
					investitures.stream()
							.filter(investiture -> investiture.getShard() == iInvest.getShard())
							.findFirst()
							.orElse(iInvest));
			investitures.add(iInvest);
		}
	}

	@Override
	public Investiture findInvestiture(Manifestation[] appManifest)
	{
		for (Investiture invest : investitures)
		{
			if (Arrays.equals(invest.getApplicableManifestations(), appManifest))
			{
				return invest;
			}
		}
		return null;
	}

	@Override
	public boolean hasInvestiture(Investiture investiture)
	{
		return !investitures.stream().filter(investiture1 -> investiture == investiture1).toList().isEmpty();
	}

	@Override
	public int currentBEU()
	{
		int sub = 0;
		for(Investiture invest: investitures)
		{
			sub+= invest.getBEU();
		}
		return sub;
	}

	@Override
	public int currentBEUDraw(List<Investiture> list)
	{
		int sub = 0;
		for(Investiture invest: list)
		{
			sub += invest.getCurrentMaxDraw();
		}
		return sub;
	}

	@Override
	public int getMaxBEU()
	{
		return maxBEU;
	}

	@Override
	public void setMaxBEU(int maxBEU)
	{
		this.maxBEU = maxBEU;
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
	public InvHelpers.InvestitureSources containerSource()
	{
		if(parent instanceof LivingEntity entity)
		{
			return InvHelpers.InvestitureSources.SELF;
		}
		else if(parent != null)
		{

		}
		return null;
	}

	public int runInvestiturePull(Manifestation manifestation)
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


		ArrayList<Investiture> availInvestitures = availableInvestitures(manifestation);
		int out = 0;

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
			ArrayList<Investiture> temp = new ArrayList<>();
			int tempTotal = 0;

			//Adds investitures to temporary list
			for (Investiture invest : availInvestitures)
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
			int theoryMax = manifestation.maxInvestitureDraw(getSpiritweb().resolve().get()) - out;
			//If current total is less than max, pull all investiture from each source of the priority
			if(tempTotal <= theoryMax)
			{
				for(Investiture invest: temp)
				{
					out += invest.drain();

				}
			}
			//Else, pull a percentage relative to each investiture's total.
			else
			{

				int tempOut = 0;
				for(Investiture invest: temp)
				{
					int toDraw = (int) (((double)(invest.getBEU())/tempTotal) * theoryMax);
					tempOut += invest.removeBEU(toDraw, true);
				}
				int index = 0;

				//Checking that investiture gets pulled correctly
				while(tempOut < theoryMax)
				{
					Investiture invest = temp.get(index);
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
