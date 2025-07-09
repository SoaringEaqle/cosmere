/*
 * File updated ~ 26 - 2 - 2023 ~ Leaf
 */

package leaf.cosmere.surgebinding.client.render.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import leaf.cosmere.surgebinding.client.render.SurgebindingLayerDefinitions;
import leaf.cosmere.surgebinding.client.render.model.DynamicShardplateModel;
import leaf.cosmere.surgebinding.common.items.ShardplateItem;
import leaf.cosmere.surgebinding.common.registries.SurgebindingItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmorStandModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.awt.*;

public class ArmorRenderer implements ICurioRenderer
{
	public DynamicShardplateModel model;


	public ArmorRenderer(Color color)
	{
		final ModelPart modelPart = Minecraft.getInstance().getEntityModels().bakeLayer(SurgebindingLayerDefinitions.SHARDPLATE);
		model = new DynamicShardplateModel(modelPart, color);


	}

	@Override
	public <T extends LivingEntity, M extends EntityModel<T>> void render(
			ItemStack stack,
			SlotContext slotContext,
			PoseStack matrixStack,
			RenderLayerParent<T, M> renderLayerParent,
			MultiBufferSource renderTypeBuffer,
			int light, float limbSwing,
			float limbSwingAmount,
			float partialTicks,
			float ageInTicks,
			float netHeadYaw,
			float headPitch)
	{

		LivingEntity entity = slotContext.entity();

		EntityModel<?> entityModel = renderLayerParent.getModel();

		MobEffectInstance effectInstance = entity.getEffect(MobEffects.INVISIBILITY);
		if (effectInstance != null && effectInstance.getDuration() > 0)
		{
			return;
		}

		this.model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
		this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

		this.model.setup(stack, slotContext, matrixStack, renderTypeBuffer, light);

		if(entityModel instanceof HumanoidModel humanoidModel && !(entityModel instanceof ArmorStandModel))
		{
			humanoidModel.setAllVisible(true);

			if(entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ShardplateItem)
			{
				humanoidModel.head.visible = false;
			}
			if(entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ShardplateItem)
			{
				humanoidModel.leftArm.visible = false;
				humanoidModel.rightArm.visible = false;
				humanoidModel.body.visible = false;
			}
			if (entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ShardplateItem
					&& entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ShardplateItem)
			{
				humanoidModel.leftLeg.visible = false;
				humanoidModel.rightLeg.visible = false;
			}
		}

		//this.model.render(stack, slotContext, matrixStack, renderTypeBuffer, light);

	}



}
