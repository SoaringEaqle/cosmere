/*
 * File updated ~ 10 - 8 - 2024 ~ Leaf
 */

package leaf.cosmere.surgebinding.common.items;

import leaf.cosmere.api.Roshar;
import leaf.cosmere.surgebinding.common.Surgebinding;
import leaf.cosmere.surgebinding.common.items.tiers.ShardplateArmorMaterial;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.awt.*;

public class DeadplateItem extends ShardplateItem
{

	public DeadplateItem(ShardplateArmorMaterial material, ArmorItem.Type pType, Properties properties)
	{
		super(material, pType, properties);
	}

	@Nonnull
	@Override
	public final String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type)
	{
		return Surgebinding.MODID + ":" + "textures/models/armor/shardplate_base.png";
	}


	@Override
	public Color getColour()
	{
		return Roshar.getDeadplate();
	}
}
