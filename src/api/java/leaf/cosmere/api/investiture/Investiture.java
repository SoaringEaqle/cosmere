/*
* File updated ~ 15 - 9 - 25 ~ Soar
 */

package leaf.cosmere.api.investiture;

import leaf.cosmere.api.CosmereAPI;
import leaf.cosmere.api.manifestation.Manifestation;
import leaf.cosmere.api.spiritweb.ISpiritweb;
import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nonnull;

public class Investiture implements IInvestiture
{

	private IInvContainer<?> container;
	private InvHelpers.Shards shard;
	private InvHelpers.InvestitureSources source;
	private Manifestation[] applicableManifestations;
	private int priority = 1;
	private int beu;
	private int decayRate;
	private int currentMaxDraw;

	private CompoundTag nbt;


	public Investiture(@Nonnull IInvContainer<?> container,
	                   InvHelpers.Shards shard,
	                   InvHelpers.InvestitureSources source,
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

	public Investiture(@Nonnull IInvContainer<?> container,
	                   InvHelpers.Shards shard,
	                   InvHelpers.InvestitureSources source,
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


	public int getBEU()
	{
		return beu;
	}

	public void setBEU(int beu)
	{
		this.beu = beu;
	}

	/*
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
	*/

	public int removeBEU(int remove)
	{
		if(remove > beu)
		{
			int out = beu;
			beu = 0;
			return out;
		}
		else
		{
			beu -= remove;
			return remove;
		}
	}

	public int removeBEU(int remove, boolean upToCurrent)
	{
		if(upToCurrent)
		{
			if (remove > currentMaxDraw)
			{
				beu -= currentMaxDraw;
				return currentMaxDraw;
			}
			else
			{
				beu -= remove;
				return remove;
			}
		}
		else
		{
			if (remove > beu)
			{
				int out = beu;
				beu = 0;
				return out;
			}
			else
			{
				beu -= remove;
				return remove;
			}
		}
	}

	public int drain()
	{
		int temp = beu;
		beu = 0;
		return temp;
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
	public InvHelpers.Shards getShard()
	{
		return shard;
	}

	@Override
	public InvHelpers.InvestitureSources getSource()
	{
		return source;
	}

	public IInvContainer<?> getContainer()
	{
		return container;
	}

	public int getCurrentMaxDraw()
	{
		return currentMaxDraw;
	}
	@Override
	public void calculateCurrentMaxDraw()
	{
		int x = 0;
		for(Manifestation manifest: applicableManifestations)
		{
			if(manifest.isActive(getSpiritweb()))
			{
				x++;
			}
		}
		currentMaxDraw = beu/x;
	}

	public ISpiritweb getSpiritweb()
	{
		return getContainer().getSpiritweb().resolve().orElse(null);
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
		if(!container.hasInvestiture(this))
		{
			container.mergeOrAddInvestiture(this);
		}
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
		shard = InvHelpers.Shards.valueOf(nbt.getString("shard"));
		source = InvHelpers.InvestitureSources.valueOf(nbt.getString("source"));
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

	public static Investiture buildFromNBT(CompoundTag nbt, IInvContainer<?> data)
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
				InvHelpers.Shards.valueOf(nbt.getString("shard")),
				InvHelpers.InvestitureSources.valueOf(nbt.getString("source")),
				nbt.getInt("beu"),array, nbt.getInt("decay_rate"));
		invest.nbt = nbt;
		invest.setPriority(nbt.getInt("priority"));
		return invest;

	}
}
