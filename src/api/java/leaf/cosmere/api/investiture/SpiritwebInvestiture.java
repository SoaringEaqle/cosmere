package leaf.cosmere.api.investiture;

import leaf.cosmere.api.CosmereAPI;
import leaf.cosmere.api.Manifestations;
import leaf.cosmere.api.manifestation.Manifestation;
import leaf.cosmere.api.spiritweb.ISpiritweb;
import net.minecraft.nbt.CompoundTag;

public class SpiritwebInvestiture implements IInvestiture
{
	private IInvContainer<?> container;
	private InvHelpers.Shard shard;
	private InvHelpers.InvestitureSource source;

	private Manifestation[] applicableManifestations;
	
	private double beu;

	private int mode = 0;

	private CompoundTag nbt;

	public SpiritwebInvestiture(IInvContainer<?> invContainer, Manifestation manifestation, ISpiritweb web)
	{
		container = invContainer;
		beu = InvHelpers.InvMath.strengthToBEU(manifestation.getStrength(web, true));
		shard = InvHelpers.Shard.getShardOfManifest(manifestation);
		source = InvHelpers.InvestitureSource.DIRECT;
		applicableManifestations = Manifestations.ManifestArrayBuilder.getArray(manifestation);
		container.mergeOrAddInvestiture(this);
	}
	
	public SpiritwebInvestiture(IInvContainer<?> investitureContainer,
	                            double strength,
	                            InvHelpers.Shard shard,
	                            InvHelpers.InvestitureSource source,
	                            Manifestation[] appManifest)
	{
		this.shard = shard;
		this.source = source;
		this.beu = (int) (strength * 15);
		this.applicableManifestations = appManifest;
		container = investitureContainer;
		container.mergeOrAddInvestiture(this);

	}
	

	
	public double getBEU()
	{
		return beu;
	}

	public void setBEU(double beu)
	{
		this.beu = beu;
	}
	public Manifestation[] getApplicableManifestations()
	{
		return applicableManifestations;
	}

	@Override
	public InvHelpers.Shard getShard()
	{
		return null;
	}

	@Override
	public InvHelpers.InvestitureSource getSource()
	{
		return null;
	}

	@Override
	public IInfuseContainer<?> getContainer()
	{
		return container;
	}

	@Override
	public double getCurrentMaxDraw()
	{
		return beu;
	}

	@Override
	public void calculateCurrentMaxDraw()
	{
	}

	public double getStrength()
	{
		return InvHelpers.InvMath.beuToStrength(beu);
	}
	
	public void setStrength(double strength)
	{
		beu = InvHelpers.InvMath.strengthToBEU(strength);
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
		&& this.source != InvHelpers.InvestitureSource.LIFEFORCE
		&& other.source != InvHelpers.InvestitureSource.LIFEFORCE)
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
		nbt.putDouble("beu", beu);


		return nbt;
	}

	public void deserializeNBT(CompoundTag nbt)
	{
		this.nbt = nbt;
		beu = nbt.getDouble("beu");
		shard = InvHelpers.Shard.valueOf(nbt.getString("shard"));
		source = InvHelpers.InvestitureSource.valueOf(nbt.getString("source"));
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
				nbt.getDouble("beu"),
				InvHelpers.Shard.valueOf(nbt.getString("shard")),
				InvHelpers.InvestitureSource.valueOf(nbt.getString("source")),
				array);
		invest.nbt = nbt;


		return invest;
	}

}
