/*
 * File updated ~ 10 - 8 - 2024 ~ Leaf
 */

package leaf.cosmere.surgebinding.common.items;

import leaf.cosmere.api.IHasColour;
import leaf.cosmere.api.IHasGemType;
import leaf.cosmere.api.Roshar;
import leaf.cosmere.surgebinding.common.Surgebinding;
import leaf.cosmere.surgebinding.common.items.tiers.ShardplateArmorMaterial;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Optional;

public class LivingplateItem extends ShardplateItem implements IHasGemType
{
	public Roshar.RadiantOrder order;

	public LivingplateItem(ShardplateArmorMaterial material, Type pType, Properties properties, Roshar.RadiantOrder order)
	{
		super(material, pType, properties);
		this.order = order;
	}

	@Nonnull
	@Override
	public final String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type)
	{
		return Surgebinding.MODID + ":" + "textures/models/armor/shardplate_base.png";
	}

	@Override
	public Roshar.Gemstone getGemType()
	{
		return order.getGemstone();
	}
	public Roshar.RadiantOrder getOrder()
	{
		return order;
	}
}
