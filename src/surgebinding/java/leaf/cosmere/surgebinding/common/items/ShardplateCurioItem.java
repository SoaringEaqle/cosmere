package leaf.cosmere.surgebinding.common.items;

import leaf.cosmere.api.IHasColour;
import leaf.cosmere.api.Roshar;
import leaf.cosmere.surgebinding.client.render.renderer.ArmorRenderer;
import leaf.cosmere.surgebinding.common.items.tiers.ShardplateArmorMaterial;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.function.Consumer;

public class ShardplateCurioItem extends Item implements ICurioItem, IHasColour
{
	ShardplateArmorMaterial material;

	public ShardplateCurioItem(Properties properties)
	{
		super(properties);
		this.material = ShardplateArmorMaterial.DEADPLATE;
	}


	public ShardplateCurioItem(ShardplateArmorMaterial material, Properties properties)
	{
		super(properties);
		this.material = material;
	}

	public boolean isLiving()
	{
		return false;
	}


	@Override
	public Color getColour()
	{
		return Roshar.getDeadplate();
	}
}
