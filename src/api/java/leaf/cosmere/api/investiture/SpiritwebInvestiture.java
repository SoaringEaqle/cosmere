package leaf.cosmere.api.investiture;

import leaf.cosmere.api.CosmereAPI;
import leaf.cosmere.api.manifestation.Manifestation;
import net.minecraft.nbt.CompoundTag;

public class SpiritwebInvestiture implements IInvestiture
{
	private Manifestation[] applicableManifestations;
	
	private int beu;


	private IInvContainer<?> container;
	private InvHelpers.Shards shard;
	private InvHelpers.InvestitureSources source;

	private CompoundTag nbt;
	
	public SpiritwebInvestiture(IInvContainer<?> investitureContainer,
	                            int strength,
	                            InvHelpers.Shards shard,
	                            InvHelpers.InvestitureSources source,
	                            Manifestation[] appManifest)
	{
		this.shard = shard;
		this.source = source;
		this.beu = strength * 15;
		this.applicableManifestations = appManifest;
		container = investitureContainer;
		container.mergeOrAddInvestiture(this);

	}
	

	
	public int getBEU()
	{
		return beu;
	}

	public void setBEU(int beu) 
	{
		this.beu = beu;
	}
	public Manifestation[] getApplicableManifestations()
	{
		return applicableManifestations;
	}

	@Override
	public InvHelpers.Shards getShard()
	{
		return null;
	}

	@Override
	public InvHelpers.InvestitureSources getSource()
	{
		return null;
	}

	@Override
	public IInvContainer<?> getContainer()
	{
		return container;
	}
	
	public int getStrength()
	{
		return beu/15;
	}
	
	public void setStrength(int strength)
	{
		beu = strength * 15;
	}
	
	public boolean merge(SpiritwebInvestiture other)
	{
		if(this.getApplicableManifestations()==(other.getApplicableManifestations())
			&& this.getContainer().equals(other.getContainer()))
		{
			this.beu += other.getBEU();
			other.setBEU(0);
			return true;
			//other.close();
		}
		return false;
	}
	
	//public void close() {this = null;}

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
		nbt.putInt("beu", beu);

		return nbt;
	}

	public void deserializeNBT(CompoundTag nbt)
	{
		this.nbt = nbt;
		beu = nbt.getInt("beu");
		shard = InvHelpers.Shards.valueOf(nbt.getString("shard"));
		source = InvHelpers.InvestitureSources.valueOf(nbt.getString("source"));
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

	public static SpiritwebInvestiture buildFromNBT(CompoundTag nbt, IInvContainer<?> data)
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
		SpiritwebInvestiture invest = new SpiritwebInvestiture(data,
				nbt.getInt("beu"),
				InvHelpers.Shards.valueOf(nbt.getString("shard")),
				InvHelpers.InvestitureSources.valueOf(nbt.getString("source")),
				array);
		invest.nbt = nbt;

		return invest;
	}

}
