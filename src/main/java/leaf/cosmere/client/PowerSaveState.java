/*
* File created ~ 2 - 5 - 2025 ~ SoaringEaqle
 */

package leaf.cosmere.client;

import leaf.cosmere.api.manifestation.Manifestation;
import leaf.cosmere.common.Cosmere;
import leaf.cosmere.common.network.packets.ChangeManifestationModeMessage;

import java.util.HashMap;

public class PowerSaveState
{

	public enum PowerSaves
	{
		POWER_SAVE_1(0),
		POWER_SAVE_2(1),
		POWER_SAVE_3(2),
		POWER_SAVE_4(3),
		POWER_SAVE_5(4),
		POWER_SAVE_6(5),
		POWER_SAVE_7(6),
		POWER_SAVE_8(7),
		POWER_SAVE_9(8);

		private final HashMap<Manifestation, Integer> manifestations = new HashMap<Manifestation, Integer>();
		private boolean isActive;
		private final int num;

		PowerSaves(int num)
		{
			this.num = num;
			isActive = false;
		}

		public String getName()
		{
			return Integer.toString(num);
		}
		public int getNum()
		{
			return num;
		}

		public boolean isActive()
		{
			return isActive;
		}

		public boolean hasManifestation(Manifestation manifestation)
		{
			return manifestations.containsKey(manifestation);
		}

		public void activate()
		{
			for (Manifestation manifestation : manifestations.keySet())
			{
				int modifier = manifestations.get(manifestation);
				Cosmere.packetHandler().sendToServer(new ChangeManifestationModeMessage(manifestation,
						isActive ? -modifier : modifier));
			}
			isActive = !isActive;
		}

		public void addManifestations(HashMap<Manifestation,Integer> manifests)
		{
			manifestations.clear();
			manifestations.putAll(manifests);
		}


	}

}
