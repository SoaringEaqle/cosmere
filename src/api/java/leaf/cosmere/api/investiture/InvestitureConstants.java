package leaf.cosmere.api.investiture;

import leaf.cosmere.api.EnumUtils;
import leaf.cosmere.api.Manifestations;
import leaf.cosmere.api.Metals;
import leaf.cosmere.api.manifestation.Manifestation;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

public class InvestitureConstants
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

		public static Optional<InvestitureConstants.Shards> valueOf(int value)
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
				case HONOR -> Manifestations.ManifestationTypes.SURGEBINDING;
				case ODIUM -> Manifestations.ManifestationTypes.SURGEBINDING;
				case RETRIBUTION -> Manifestations.ManifestationTypes.SURGEBINDING;
				case CULTIVATION -> Manifestations.ManifestationTypes.SURGEBINDING;
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

		public static Optional<InvestitureConstants.InvestitureSources> valueOf(int value)
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
}
