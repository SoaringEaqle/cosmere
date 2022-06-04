/*
 * File created ~ 27 - 4 - 2021 ~ Leaf
 */

/*
 * File updated ~ 03 - 6 - 2022 ~ Leaf
 *
 */

package leaf.cosmere.utils.helpers;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class XPHelper
{

	/**
	 * A copy of the related function in Player, except without the call to forge event
	 * see {@link net.minecraft.world.entity.player.Player#giveExperiencePoints}
	 */
	public static void giveExperiencePoints(Player player, int points)
	{
		player.increaseScore(points);
		player.experienceProgress += (float) points / (float) player.getXpNeededForNextLevel();
		player.totalExperience = Mth.clamp(player.totalExperience + points, 0, Integer.MAX_VALUE);

		while (player.experienceProgress < 0.0F)
		{
			float f = player.experienceProgress * (float) player.getXpNeededForNextLevel();
			if (player.experienceLevel > 0)
			{
				player.giveExperienceLevels(-1);
				player.experienceProgress = 1.0F + f / (float) player.getXpNeededForNextLevel();
			}
			else
			{
				player.giveExperienceLevels(-1);
				player.experienceProgress = 0.0F;
			}
		}

		while (player.experienceProgress >= 1.0F)
		{
			player.experienceProgress = (player.experienceProgress - 1.0F) * (float) player.getXpNeededForNextLevel();
			player.giveExperienceLevels(1);
			player.experienceProgress /= (float) player.getXpNeededForNextLevel();
		}
	}


	/**
	 * see {@link net.minecraft.world.entity.player.Player#getXpNeededForNextLevel}#()
	 */
	public static int getXpNeededForNextLevel(int playerLevel)
	{
		if (playerLevel >= 30)
		{
			return 112 + (playerLevel - 30) * 9;
		}
		else if (playerLevel >= 15)
		{
			return 37 + (playerLevel - 15) * 5;
		}
		else
		{
			return 7 + playerLevel * 2;
		}
	}

	public static int getLevelForTotalExperience(int experience)
	{
		int i = 0;
		int xp = 0;
		while (xp <= experience)
		{
			xp += getXpNeededForNextLevel(i);

			i++;
		}
		return i - 1;
	}
}
