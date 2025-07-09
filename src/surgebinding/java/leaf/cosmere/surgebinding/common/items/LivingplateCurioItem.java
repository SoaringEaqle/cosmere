package leaf.cosmere.surgebinding.common.items;

import leaf.cosmere.api.Roshar;
import leaf.cosmere.surgebinding.common.items.tiers.ShardplateArmorMaterial;

import java.awt.*;

public class LivingplateCurioItem extends ShardplateCurioItem
{
	public Roshar.RadiantOrder order;
	public LivingplateCurioItem(Properties properties, Roshar.RadiantOrder order)
	{
		super(ShardplateArmorMaterial.LIVINGPLATE, properties);
		this.order = order;
	}

	@Override
	public Color getColour()
	{
		return order.getPlateColor();
	}

	@Override
	public boolean isLiving()
	{
		return true;
	}

	public Roshar.RadiantOrder getOrder()
	{
		return order;
	}
}
