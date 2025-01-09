/*
 * File updated ~ 10 - 1 - 2025 ~ Leaf
 */

package leaf.cosmere.surgebinding.common.items;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;

public class NightbloodItem extends ShardbladeItem
{
	public NightbloodItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn)
	{
		super(tier, attackDamageIn, attackSpeedIn, builderIn);

		//todo spoilers!
		//todo check a config for whether nightblood has talked to the other shardblades?e
	}


	public boolean canSummonDismiss(Player player)
	{
		return false;
	}
}
