/*
* File updated ~ 15 - 9 - 25 ~ Soar
 */

package leaf.cosmere.api.investiture;

import leaf.cosmere.api.EnumUtils;
import leaf.cosmere.api.Manifestations;
import leaf.cosmere.api.manifestation.Manifestation;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

public class InvHelpers
{
	public enum Shards
	{
		NONE(0),
		AUTONOMY(1),
		RUIN(2),
		PRESERVATION(16),
		HARMONY(17),
		HONOR(10),
		CULTIVATION(3),
		ODIUM(9),
		RETRIBUTION(18),
		ENDOWMENT(4),
		VIRTUOSITY(5),
		INVENTION(6),
		VALOR(7),
		WHIMSY(8),
		REASON(11),
		DEVOTION(12),
		DOMINION(13),
		AMBITION(14),
		MERCY(15),
		DOR(19),
		AETHER(20),
		PURE(21);


		final int id;

		Shards(int number)
		{
			this.id = number;
		}

		public static Optional<InvHelpers.Shards> valueOf(int value)
		{
			return Arrays.stream(values())
					.filter(shard -> shard.id == value)
					.findFirst();
		}

		public int getID()
		{
			return id;
		}

		public String getName()
		{
			return name().toLowerCase(Locale.ROOT);
		}

		public Shards[] getComponentShards()
		{
			return switch (this)
			{
				case DOR -> new Shards[]{DEVOTION, DOMINION};
				case HARMONY -> new Shards[]{RUIN, PRESERVATION};
				case RETRIBUTION -> new Shards[]{HONOR, ODIUM};
				case PURE -> EnumUtils.SHARDS;
				default -> new Shards[]{this};

			};
		}

		public Manifestations.ManifestationTypes getManifestationType()
		{
			return switch (this)
			{
				case HARMONY -> Manifestations.ManifestationTypes.FERUCHEMY;
				case PRESERVATION -> Manifestations.ManifestationTypes.ALLOMANCY;
				case RUIN -> Manifestations.ManifestationTypes.HEMALURGY;
				case AUTONOMY -> Manifestations.ManifestationTypes.SANDMASTERY;
				case DOR -> Manifestations.ManifestationTypes.AON_DOR;
				case ENDOWMENT -> Manifestations.ManifestationTypes.AWAKENING;
				case HONOR, ODIUM, RETRIBUTION, CULTIVATION -> Manifestations.ManifestationTypes.SURGEBINDING;
				default -> Manifestations.ManifestationTypes.NONE;
			};
		}
	}
	// All possible sources for gaining kinetic investiture
	public enum InvestitureSources
	{
		DIRECT(0),
		MISTS(1),
		GEMSTONE(2),
		HIGHSTORM(3),
		SELF(4),
		LUHEL_BOND(5),
		NAHEL_BOND(6),
		LIFEFORCE(7),
		PERPENDICULARITY(8);

		final int id;

		InvestitureSources(int id)
		{
			this.id = id;
		}

		public static Optional<InvHelpers.InvestitureSources> valueOf(int value)
		{
			return Arrays.stream(values())
					.filter(shard -> shard.id == value)
					.findFirst();
		}

		public int getID()
		{
			return id;
		}

		public String getName()
		{
			return name().toLowerCase(Locale.ROOT);
		}

	}

	public static class Math
	{
		int beuToStrength(int beu)
		{
			return beu/Constants.beuStrengthRatio;
		}

		int strengthToBEU(int strength)
		{
			return strength*Constants.beuStrengthRatio;
		}


	}

	public static class Constants
	{
		//Tick Counters
		//Any manifestations that activate on ticks 3,0, or 1 get free activation until tick 2.
		//During this tick, investiture is collected and filled. All movement of investiture objects is handled this tick.
		public static final int collectionTick = 0;
		//Investiture gets managed. Sources and Investiture are merged and/or deleted as fit
		public static final int mangagementTick = 1;
		//Manifestations check how much investiture they need and if all sources have the required amount.
		// Investiture is counted.
		public static final int checkTick = 2;
		//Manifestations request beu to continue for another cycle.
		public static final int pullTick = 3;


		public static final int beuStrengthRatio = 15;
	}

	public class Transferer
	{
		private Investiture investIn;
		private Investiture investOut;
		private int rate;

		public Transferer(Investiture investIn, IInvContainer containerOut, int transferRate, int decayRate)
		{
			this.investIn = investIn;
			Shards shard = investIn.getShard();
			InvestitureSources source = investIn.getContainer().containerSource();
			Manifestation[] man = investIn.getApplicableManifestations();
			this.investOut = new Investiture(containerOut, shard, source, transferRate, man, decayRate);
			rate = transferRate;
			investIn.removeBEU(transferRate);
		}

		public Transferer(Investiture investIn, IInvContainer containerOut, int transferRate)
		{
			this(investIn, containerOut, transferRate, 0);
		}

		public void transfer()
		{
			investOut.addBEU(rate - investIn.removeBEU(rate));
		}

		public int getRate()
		{
			return rate;
		}

		public Investiture getInvestIn()
		{
			return investIn;
		}

		public Investiture getInvestOut()
		{
			return investOut;
		}


		public void clean()
		{
			if(investIn.getBEU() == 0)
			{
				investIn = null;
			}
			if(investOut.getBEU() == 0)
			{
				investOut = null;
			}
		}
	}
}
