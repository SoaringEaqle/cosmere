/*
* File updated ~ 15 - 9 - 25 ~ Soar
 */
package leaf.cosmere.common.items;

import leaf.cosmere.api.helpers.StackNBTHelper;
import leaf.cosmere.common.investiture.InvestitureContainer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public abstract class InvestedItemBase extends ChargeableItemBase
{
	public InvestedItemBase(Properties properties)
	{
		super(properties);
	}

	public InvestitureContainer<ItemStack> getContainer(ItemStack stack)
	{
		return InvestitureContainer.findOrCreateContainer(stack);
	}

	public void saveContainer(ItemStack stack, CompoundTag tag)
	{
		StackNBTHelper.set(stack, "investContainer", tag);
	}

}
