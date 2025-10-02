package leaf.cosmere.api.investiture;

import leaf.cosmere.api.CosmereAPI;
import leaf.cosmere.api.Manifestations;
import leaf.cosmere.api.manifestation.Manifestation;
import leaf.cosmere.api.spiritweb.ISpiritweb;
import net.minecraft.nbt.CompoundTag;

public class SpiritwebInvestiture implements IInvestiture
{
	private Manifestation[] applicableManifestations;
	
	private int beu;


	private IInvContainer<?> container;
	private InvHelpers.Shards shard;
	private InvHelpers.InvestitureSources source;
	private int lastPullInvestiture = 0;
	private int mode = 0;

	private CompoundTag nbt;

	private int currentMaxDraw;

	public SpiritwebInvestiture(IInvContainer<?> invContainer, Manifestation manifestation, ISpiritweb web)
	{
		container = invContainer;
		beu = InvHelpers.Math.strengthToBEU(manifestation.getStrength(web, true));
		shard = InvHelpers.Shards.getShardOfManifest(manifestation);
		source = InvHelpers.InvestitureSources.DIRECT;
		applicableManifestations = Manifestations.ManifestArrayBuilder.getArray(manifestation);
		container.mergeOrAddInvestiture(this);
	}
	
	public SpiritwebInvestiture(IInvContainer<?> investitureContainer,
	                            double strength,
	                            InvHelpers.Shards shard,
	                            InvHelpers.InvestitureSources source,
	                            Manifestation[] appManifest)
	{
		this.shard = shard;
		this.source = source;
		this.beu = (int) (strength * 15);
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

	@Override
	public int getCurrentMaxDraw()
	{
		return currentMaxDraw;
	}

	@Override
	public void calculateCurrentMaxDraw()
	{
		currentMaxDraw = beu;
	}

	public double getStrength()
	{
		return InvHelpers.Math.beuToStrength(beu);
	}
	
	public void setStrength(double strength)
	{
		beu = InvHelpers.Math.strengthToBEU(strength);
	}

	public int getLastPullInvestiture()
	{
		return lastPullInvestiture;
	}

	public void setLastPullInvestiture(int lastPullInvestiture)
	{
		this.lastPullInvestiture = lastPullInvestiture;
	}

	public int getMode()
	{
		return mode;
	}

	public void setMode(int mode)
	{
		this.mode = mode;
	}

	public boolean merge(SpiritwebInvestiture other)
	{
		if(this.getApplicableManifestations()==(other.getApplicableManifestations())
			&& this.getContainer().equals(other.getContainer())
		&& this.source != InvHelpers.InvestitureSources.LIFEFORCE
		&& other.source != InvHelpers.InvestitureSources.LIFEFORCE)
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

		nbt.putInt("lpi",lastPullInvestiture);

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

		lastPullInvestiture = nbt.getInt("lpi");


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
		invest.lastPullInvestiture = nbt.getInt("lpi");
		invest.nbt = nbt;


		return invest;
	}

}
