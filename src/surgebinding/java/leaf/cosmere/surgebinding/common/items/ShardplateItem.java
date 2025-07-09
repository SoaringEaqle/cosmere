/*
 * File updated ~ 10 - 8 - 2024 ~ Leaf
 */

package leaf.cosmere.surgebinding.common.items;

import leaf.cosmere.api.IHasColour;
import leaf.cosmere.client.render.CosmereRenderers;
import leaf.cosmere.surgebinding.client.render.renderer.ArmorRenderer;
import leaf.cosmere.surgebinding.client.render.renderer.ShardbladeItemRenderer;
import leaf.cosmere.surgebinding.common.Surgebinding;
import leaf.cosmere.surgebinding.common.capabilities.DynamicShardplateData;
import leaf.cosmere.surgebinding.common.items.tiers.ShardplateArmorMaterial;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmorStandModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Optional;
import java.util.function.Consumer;

public class ShardplateItem extends ArmorItem implements IHasColour, ICurioItem
{
	public static final Capability<DynamicShardplateData> CAPABILITY = CapabilityManager.get(new CapabilityToken<>()
	{
	});

	public ShardplateItem(ShardplateArmorMaterial material, Type pType, Properties properties)
	{
		super(material, pType, properties);

	}


	@OnlyIn(Dist.CLIENT)
	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer)
	{
		consumer.accept(new IClientItemExtensions()
		{
			
			@Nullable
			@Override
			public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original)
			{
				Optional<ICurioRenderer> armorModel = CosmereRenderers.getRenderer(itemStack.getItem());
/*
				if (armorModel.isPresent() && armorModel.get() instanceof ArmorRenderer armorRenderer)
				{
					final boolean isHead = equipmentSlot == EquipmentSlot.HEAD;
					final boolean isChest = equipmentSlot == EquipmentSlot.CHEST;
					final boolean isLegs = equipmentSlot == EquipmentSlot.LEGS;
					final boolean isFeet = equipmentSlot == EquipmentSlot.FEET;

					var model = armorRenderer.model;


					if (!itemStack.getCapability(DeadplateItem.CAPABILITY).isPresent())
					{
						return model;
					}
					final DynamicShardplateData data = itemStack.getCapability(ShardplateItem.CAPABILITY).resolve().get();




					//set the parts on the base model visible
					model.right_leg.visible = true;
					model.left_leg.visible = true;

					model.head.visible = isHead;
					model.faceplate.visible = isHead;


					model.body.visible = isChest;
					//chestplate is done separately because pants have to set body visible for some reason
					model.right_arm.visible = isChest;
					model.left_arm.visible = isChest;

					model.left_leg_top.visible = isLegs;
					model.right_leg_top.visible = isLegs;

					model.left_boot.visible = isFeet;
					model.right_boot.visible = isFeet;

					original.setAllVisible(false);

					if (isHead)
					{
						model.head.getAllParts().forEach(part -> part.visible = false);
						model.faceplate.getAllParts().forEach(part -> part.visible = false);


						model.head.getChild(data.getHeadID()).visible = true;

						model.faceplate.getChild(data.getFaceplateID()).visible = true;
					}
					if (isChest)
					{
						model.body.getAllParts().forEach(part -> part.visible = false);
						model.left_arm.getAllParts().forEach(part -> part.visible = false);
						model.right_arm.getAllParts().forEach(part -> part.visible = false);



						model.body.getChild(data.getBodyID()).visible = true;

						model.right_arm_main.getChild(data.getRightArmID()).visible = true;
						if(!data.getRightPaldronsID().equals("right_paldron0"))
						{
							model.right_paldron.getChild(data.getRightPaldronsID()).visible = true;
						}

						model.left_arm_main.getChild(data.getLeftArmID()).visible = true;
						if(!data.getLeftPaldronsID().equals("left_paldron0"))
						{
							model.left_paldron.getChild(data.getLeftPaldronsID()).visible = true;
						}


					}
					//then set the actual child legs/boots visibility.
					//kinda janky but it works
					if (isLegs)
					{
						model.left_leg_top.getAllParts().forEach(part -> part.visible = false);
						model.right_leg_top.getAllParts().forEach(part -> part.visible = false);
						model.kama.getAllParts().forEach(part -> part.visible = false);

						if(!data.getKamaID().equals("kama0"))
						{
							model.kama.getChild(data.getKamaID()).visible = true;
						}
						model.right_leg_top.getChild(data.getRightLegID()).visible = true;
						model.left_leg_top.getChild(data.getLeftLegID()).visible = true;

					}
					if (isFeet)
					{
						model.right_boot.getAllParts().forEach(part -> part.visible = false);
						model.left_boot.getAllParts().forEach(part -> part.visible = false);

						model.left_boot_tip.getChild(data.getLeftBootTipID()).visible = true;
						model.left_boot_outside.getChild(data.getLeftBootOutsideID()).visible = true;
						model.right_boot_tip.getChild(data.getRightBootTipID()).visible = true;
						model.right_boot_outside.getChild(data.getRightBootOutsideID()).visible = true;
					}

					return model;

				}
				return null;*/
				if(armorModel.isPresent() && armorModel.get() instanceof ArmorRenderer armorRenderer)
				{
					return armorRenderer.model;
				}
				return null;
			}
		});
	}


	@Override
	public Color getColour()
	{
		return null;
	}
}
