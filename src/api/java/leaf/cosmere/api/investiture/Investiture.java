package leaf.cosmere.api.investiture;

import leaf.cosmere.api.CosmereAPI;
import leaf.cosmere.api.manifestation.Manifestation;
import leaf.cosmere.api.spiritweb.ISpiritweb;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class Investiture implements IInvestiture
{

	private IInvestitureEntityContainer container = null;
	private ItemStack stackContainer = null;
	private InvestitureConstants.Shards shard;
	private InvestitureConstants.InvestitureSources source;
	private Manifestation[] applicableManifestations;
	private int priority = 1;
	private int beu;
	private int decayRate;
	private CompoundTag nbt;


	public Investiture(IInvestitureEntityContainer container,
	                   InvestitureConstants.Shards shard,
	                   InvestitureConstants.InvestitureSources source,
	                   int beu,
	                   Manifestation[] applicableManifestations,
	                   int decayRate)
	{

		this.beu = beu;
		this.applicableManifestations = applicableManifestations;
		this.container = container;
		this.container.mergeOrAddInvestiture(this);
		this.decayRate = decayRate;
		this.shard = shard;
		this.source = source;
	}

	public Investiture(IInvestitureEntityContainer container,
	                   InvestitureConstants.Shards shard,
	                   InvestitureConstants.InvestitureSources source,
	                   int beu,
	                   Manifestation[] applicableManifestations)
	{

		this.beu = beu;
		this.applicableManifestations = applicableManifestations;
		this.container = container;
		this.container.mergeOrAddInvestiture(this);
		this.shard = shard;
		this.source = source;
		this.decayRate = 0;
	}

	public Investiture(ItemStack stack,
	                   InvestitureConstants.Shards shard,
	                   InvestitureConstants.InvestitureSources source,
	                   int beu,
	                   Manifestation[] applicableManifestations,
	                   int decayRate)
	{

		this.beu = beu;
		this.applicableManifestations = applicableManifestations;
		this.stackContainer = stack;
		this.decayRate = decayRate;
		this.shard = shard;
		this.source = source;
	}


	public int getBEU()
	{
		return beu;
	}

	public void setBEU(int beu)
	{
		this.beu = beu;
	}

	public int removeBEU(int remove)
	{
		if (remove > beu)
		{
			int ret = remove - beu;
			beu = 0;
			return ret;
		}
		else
		{
			beu -= remove;
			return 0;
		}
	}

	public void addBEU(int add)
	{
		beu += add;
	}

	public int getPriority(){return priority;}

	public void setPriority(int priority)
	{
		this.priority = priority;
	}

	public Manifestation[] getApplicableManifestations()
	{
		return applicableManifestations;
	}

	@Override
	public InvestitureConstants.Shards getShard()
	{
		return shard;
	}

	@Override
	public InvestitureConstants.InvestitureSources getSource()
	{
		return source;
	}

	public IInvestitureContainer getContainer()
	{
		if(container != null && stackContainer != null)
		{
			// something went wrong. Only one of these should have a value.
			IInvestitureItemContainer item = (IInvestitureItemContainer) stackContainer.getItem();
			if(container.hasInvestiture(this) && item.hasInvestiture(stackContainer, this))
			{
				// something went really wrong. Both containers contain this investiture.
				// We'll default to returning it to the player, and removing it from the ItemStack.
				item.clearInvestiture(stackContainer, this);
				stackContainer = null;
				return container;

			}
			else if(container.hasInvestiture(this))
			{
				stackContainer = null;
				return container;
			}
			else if(item.hasInvestiture(stackContainer, this))
			{
				container = null;
				return stackContainer;
			}
			else
			{
				//neither owns it, default to entity, and reattach

			}

		}
	}

	public ISpiritweb getSpiritweb()
	{
		return (ISpiritweb) container;
	}

	public int getDecayRate()
	{
		return decayRate;
	}

	public void setDecayRate(int decayRate)
	{
		this.decayRate = decayRate;
	}

	public boolean isUsable(Manifestation manifest1)
	{
		for(Manifestation manifest: applicableManifestations)
		{
			if(manifest.equals(manifest1))
			{
				return true;
			}
		}
		return false;
	}

	public boolean merge(Investiture other)
	{
		if(this == other)
		{
			return false;
		}
		if(this.getPriority() == other.getPriority()
			&& this.shard == other.shard
			&& this.getContainer().equals(other.getContainer()))
		{
			this.beu += other.getBEU();
			return true;
			//other.close;
		}
		return false;
	}
	
	//protected void close() {this = null;}

	public void decay()
	{
		beu -= decayRate;
	}

	public void reattach()
	{
		if
		container.mergeOrAddInvestiture(this);
	}

	public CompoundTag serializeNBT()
	{
		if (this.nbt == null)
		{
			this.nbt = new CompoundTag();
		}
		nbt.putString("shard", shard.getName().toLowerCase());
		nbt.putString("source", source.getName().toLowerCase());
		nbt.putInt("manifestations_length", applicableManifestations.length);
		final CompoundTag manifestationNBT = new CompoundTag();
		for (int i = 0; i < applicableManifestations.length; i++)
		{
			manifestationNBT.putInt(applicableManifestations[i].getRegistryName().toString(), i);
		}
		nbt.put("manifestations", manifestationNBT);
		nbt.putInt("priority", priority);
		nbt.putInt("decay_rate", decayRate);
		nbt.putInt("beu", beu);

		return nbt;
	}

	public void deserializeNBT(CompoundTag nbt)
	{
		this.nbt = nbt;
		decayRate = nbt.getInt("decay_rate");
		beu = nbt.getInt("beu");
		shard = InvestitureConstants.Shards.valueOf(nbt.getString("shard"));
		source = InvestitureConstants.InvestitureSources.valueOf(nbt.getString("source"));
		priority = nbt.getInt("priority");
		applicableManifestations = new Manifestation[nbt.getInt("manifestations_length")];
		CompoundTag manifestNBT = nbt.getCompound("manifestations");
		for (Manifestation manifestation : CosmereAPI.manifestationRegistry())
		{
			final String manifestationLoc = manifestation.getRegistryName().toString();

			if (manifestNBT.contains(manifestationLoc))
			{
				applicableManifestations[manifestNBT.getInt(manifestationLoc)] = manifestation;
			}
		}


	}

	public static Investiture buildFromNBT(CompoundTag nbt, IInvestitureContainer data)
	{
		Manifestation[] array = new Manifestation[nbt.getInt("manifestations_length")];
		CompoundTag manifestNBT = nbt.getCompound("manifestations");
		for (Manifestation manifestation : CosmereAPI.manifestationRegistry())
		{
			final String manifestationLoc = manifestation.getRegistryName().toString();

			if (manifestNBT.contains(manifestationLoc))
			{
				array[manifestNBT.getInt(manifestationLoc)] = manifestation;
			}
		}
		Investiture invest = new Investiture(data,
				InvestitureConstants.Shards.valueOf(nbt.getString("shard")),
				InvestitureConstants.InvestitureSources.valueOf(nbt.getString("source")),
				nbt.getInt("beu"),array, nbt.getInt("decay_rate"));
		invest.nbt = nbt;
		invest.setPriority(nbt.getInt("priority"));
		return invest;

	}
}
