/*
* File updated ~ 15 - 9 - 25 ~ Soar
 */

package leaf.cosmere.common.investiture;

import leaf.cosmere.api.investiture.*;
import leaf.cosmere.api.manifestation.Manifestation;
import leaf.cosmere.api.spiritweb.ISpiritweb;
import leaf.cosmere.common.cap.entity.SpiritwebCapability;
import leaf.cosmere.common.charge.IChargeable;
import leaf.cosmere.common.items.InvestedItemBase;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import java.util.*;

public class InvestitureContainer<T extends ICapabilitySerializable<CompoundTag>> implements IInvContainer<T>
{
	private static final Map<ICapabilitySerializable<CompoundTag>, InvestitureContainer<?>> containers = new HashMap<>();

	public static InvestitureContainer<LivingEntity> findOrCreateContainer(LivingEntity entity)
	{
		if(containers.containsKey(entity))
		{
			return (InvestitureContainer<LivingEntity>) containers.get(entity);

		}
		else
		{
			InvestitureContainer<LivingEntity> containTemp = new InvestitureContainer<LivingEntity>(entity);
			containers.put(entity, containTemp);
			return containTemp;
		}
	}

	public static InvestitureContainer<ItemStack> findOrCreateContainer(ItemStack stack)
	{
		if(containers.containsKey(stack))
		{
			return (InvestitureContainer<ItemStack>) containers.get(stack);
		}
		else
		{
			InvestitureContainer<ItemStack> containTemp = new InvestitureContainer<>(stack);
			containers.put(stack, containTemp);
			return containTemp;
		}
	}

	public static InvestitureContainer<LivingEntity> findOrCreateContainer(ISpiritweb spiritweb)
	{
		return findOrCreateContainer(spiritweb.getLiving());
	}

	public static void clear()
	{
		for(ICapabilitySerializable<CompoundTag> entry: containers.keySet())
		{
			InvestitureContainer<?> value = containers.get(entry);
			if(value.lastAccessedTick > 20)
			{
				value.saveNBT();
				containers.remove(entry);
			}
		}

	}

	private final T parent;

	private CompoundTag nbt;

	public final List<Investiture> investitures = new ArrayList<>();
	public final List<SpiritwebInvestiture> swInvestitures = new ArrayList<>();
	private int maxBEU;
	private int lastAccessedTick; // Used for cleaning. If not accessed after several ticks, clear it out.

	private InvestitureContainer(T parent)
	{
		this.parent = parent;
		if(this.parent instanceof LivingEntity entity && SpiritwebCapability.get(entity).resolve().isPresent())
		{
			ISpiritweb web = SpiritwebCapability.get(entity).resolve().get();
			CompoundTag tag = web.getCompoundTag();
			if (tag.contains("investContainer"))
			{
				nbt = tag.getCompound("investContainer");
				deserializeNBT(nbt);
			}
			else
			{
				nbt = new CompoundTag();
				maxBEU = web.maxBEU();
			}
		}
		else if(this.parent instanceof LivingEntity entity)
		{
			//todo: save nbt Data from living entity.
		}
		else if(this.parent instanceof ItemStack stack
				&& stack.getItem() instanceof InvestedItemBase itemBase)
		{
			CompoundTag tag = stack.getOrCreateTag();
			if (tag.contains("investContainer"))
			{
				nbt = tag.getCompound("investContainer");
				deserializeNBT(nbt);
			}
			else
			{
				nbt = new CompoundTag();
				maxBEU = itemBase.getMaxCharge(stack);
			}
		}
	}

	@Override
	public T getParent()
	{
		return parent;
	}

	public ISpiritweb getSpiritweb()
	{
		if(parent instanceof LivingEntity entity)
		{
			return SpiritwebCapability.get(entity).resolve().orElse(null);
		}
		if(parent instanceof ItemStack stack)
		{
			if(stack.getItem() instanceof IChargeable item)
			{
				UUID connection = item.getAttunedPlayer(stack);
				Player player = null;
				for(Level level: Minecraft.getInstance().level.getServer().getAllLevels())
				{
					player = level.getPlayerByUUID(connection);
					if(player != null)
					{
						return SpiritwebCapability.get(player).resolve().orElse(null);
					}
				}

			}
		}
		return null;
	}
	public CompoundTag saveNBT()
	{
		serializeNBT();
		if(this.parent instanceof LivingEntity entity && SpiritwebCapability.get(entity).isPresent())
		{
			//spiritwebs manage their own containers, since it's accessed so much.
			return nbt;
		}
		else if(this.parent instanceof LivingEntity entity)
		{
			//todo: attach nbt to entity
			//entity.
		}
		else if(this.parent instanceof ItemStack stack
				&& stack.getItem() instanceof InvestedItemBase item)
		{
			item.saveContainer(stack, nbt);
			containers.remove(parent);
			return nbt;
		}
		return nbt;
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
		else if(parent instanceof ItemStack stack)
		{

		}
		return null;
	}

	public int runInvestiturePull(Manifestation manifestation)
	{
		ArrayList<Investiture> investitures = availableInvestitures(manifestation);
		int out = 0;

		//Test to see if the minimum amount of investiture is available
		//If not return
		int possible = 0;
		for(Investiture invest: investitures)
		{
			possible += invest.getBEU();
		}
		if(possible < manifestation.minInvestitureDraw(this))
		{
			return 0;
		}


		//Loops through each priority
		for(int i = 1; i <= 5; i++)
		{
			//Temporary list of values of given priority
			ArrayList<Investiture> temp = new ArrayList<>();
			int sub = 0;
			//Adds investitures to temporary list
			for (Investiture invest : investitures)
			{
				// remove empty investitures
				if(invest.getBEU() == 0)
				{
					investitures.remove(invest);
				}
				if(invest.getPriority() != i)
				{
					continue;
				}
				temp.add(invest);
				sub += invest.getBEU();
			}
			//Don't need to loop through an empty list.
			if(temp.isEmpty())
			{
				continue;
			}

			//If current total is less than max, pull all investiture from each source of the priority
			if(sub + out <= manifestation.maxInvestitureDraw(this))
			{
				for(Investiture invest: temp)
				{
					out += invest.drain();

				}
			}
			//Else, pull a percentage relative to each investiture's total.
			else
			{
				int sub2 = manifestation.maxInvestitureDraw(this) - out;
				int sub3 = 0;
				for(Investiture invest: temp)
				{
					int toDraw = (invest.getBEU()/sub) * sub2;
					sub3 += toDraw;
					invest.removeBEU(toDraw);
				}
				int index = 0;

				//Checking that investiture gets pulled correctly
				while(sub3 < sub2)
				{
					Investiture invest = temp.get(index);
					if(invest.getBEU() > 0)
					{
						sub3 += 1;
						invest.removeBEU(1);
					}
					if(invest.getBEU() == 0)
					{
						temp.remove(index);
					}
					index = index >= temp.size() ? 0 : index + 1;
				}

				out += sub3;
			}
		}
		return out;
	}

}
