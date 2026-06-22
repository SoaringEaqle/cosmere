/*
* File created ~ 12 - 7 - 2025 ~ Soar
 */
package leaf.cosmere.surgebinding.client.render.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import leaf.cosmere.surgebinding.common.Surgebinding;
import leaf.cosmere.surgebinding.common.capabilities.DynamicShardplateData;
import leaf.cosmere.surgebinding.common.capabilities.ShardData;
import leaf.cosmere.surgebinding.common.items.ShardplateCurioItem;
import leaf.cosmere.surgebinding.common.utils.ShardHelper;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;

import java.awt.*;

public class DynamicShardplateModel extends HumanoidModel<LivingEntity>
{
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ResourceLocation TEXTURE = Surgebinding.rl("textures/models/armor/shardplate_base.png");
	public static final ResourceLocation VISOR = Surgebinding.rl("textures/models/armor/shardplate_visors.png");
	public static final ResourceLocation CRACKS1 = Surgebinding.rl("textures/models/armor/shardplate_cracks_1.png");
	public static final ResourceLocation TRIM = Surgebinding.rl("textures/models/armor/shardplate_trims.png");

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Surgebinding.rl("shardplate"), "main");
	public final ModelPart root;


	public DynamicShardplateModel(ModelPart root)
	{
		super(root.getChild("root"));

		this.root = root.getChild("root");
	}


	public static LayerDefinition createBodyLayer()
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(5.0F, -0.25F, -3.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-5.0F, 0.75F, 3.0F));

		PartDefinition faceplate = head.addOrReplaceChild("faceplate", CubeListBuilder.create(), PartPose.offset(0.0F, -9.0F, -2.0F));

		PartDefinition faceplate1 = faceplate.addOrReplaceChild("faceplate1", CubeListBuilder.create().texOffs(87, 87).addBox(-4.0F, 1.0F, -0.5F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.6F))
				.texOffs(96, 77).addBox(-2.0F, 1.0F, -1.5F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.6F)), PartPose.offset(0.0F, -0.75F, -0.5F));

		PartDefinition cube_r1 = faceplate1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(96, 67).mirror().addBox(1.4F, -8.0F, 0.5F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.6F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 9.0F, -0.5F, 0.0F, 0.5672F, 0.0F));

		PartDefinition cube_r2 = faceplate1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(86, 77).addBox(-5.4F, -8.0F, 0.5F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(5.0F, 9.0F, -0.5F, 0.0F, -0.5672F, 0.0F));

		PartDefinition faceplate2 = faceplate.addOrReplaceChild("faceplate2", CubeListBuilder.create().texOffs(108, 85).addBox(-4.0F, 1.0F, -0.5F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.6F))
				.texOffs(116, 75).addBox(-2.0F, 1.0F, -1.5F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.6F)), PartPose.offset(0.0F, -0.75F, -0.5F));

		PartDefinition cube_r3 = faceplate2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(106, 75).mirror().addBox(1.4F, -8.0F, 0.5F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.6F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 9.0F, -0.5F, 0.0F, 0.5672F, 0.0F));

		PartDefinition cube_r4 = faceplate2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(116, 65).addBox(-5.4F, -8.0F, 0.5F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(5.0F, 9.0F, -0.5F, 0.0F, -0.5672F, 0.0F));

		PartDefinition faceplate3 = faceplate.addOrReplaceChild("faceplate3", CubeListBuilder.create().texOffs(87, 117).addBox(-4.0F, 1.0F, -0.5F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.6F))
				.texOffs(95, 107).addBox(-2.0F, 1.0F, -1.5F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.6F)), PartPose.offset(0.0F, -0.75F, -0.5F));

		PartDefinition cube_r5 = faceplate3.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(85, 107).mirror().addBox(1.4F, -8.0F, 0.5F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.6F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 9.0F, -0.5F, 0.0F, 0.5672F, 0.0F));

		PartDefinition cube_r6 = faceplate3.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(95, 97).addBox(-5.4F, -8.0F, 0.5F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(5.0F, 9.0F, -0.5F, 0.0F, -0.5672F, 0.0F));

		PartDefinition faceplate4 = faceplate.addOrReplaceChild("faceplate4", CubeListBuilder.create().texOffs(108, 117).addBox(-4.0F, 1.0F, -0.5F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.6F))
				.texOffs(116, 107).addBox(-2.0F, 1.0F, -1.5F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.6F)), PartPose.offset(0.0F, -0.75F, -0.5F));

		PartDefinition cube_r7 = faceplate4.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(116, 97).mirror().addBox(1.4F, -8.0F, 0.5F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.6F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 9.0F, -0.5F, 0.0F, 0.5672F, 0.0F));

		PartDefinition cube_r8 = faceplate4.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(106, 107).addBox(-5.4F, -8.0F, 0.5F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(5.0F, 9.0F, -0.5F, 0.0F, -0.5672F, 0.0F));

		PartDefinition head1 = head.addOrReplaceChild("head1", CubeListBuilder.create().texOffs(99, 49).addBox(-4.0F, -8.75F, -2.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.6F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(-5.0F, 0.25F, 3.0F));

		PartDefinition body1 = body.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(99, 3).addBox(-4.0F, 0.75F, -2.5F, 8.0F, 3.0F, 5.0F, new CubeDeformation(0.75F))
				.texOffs(75, 3).addBox(-4.0F, 3.5F, -2.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(-3.1F, 12.25F, 3.0F));

		PartDefinition left_leg_main = left_leg.addOrReplaceChild("leftLegMain", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_leg_main1 = left_leg_main.addOrReplaceChild("left_leg_main1", CubeListBuilder.create().texOffs(50, 112).addBox(0.35F, -11.6F, -2.1F, 4.0F, 9.25F, 4.0F, new CubeDeformation(0.4F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

		PartDefinition left_boot = left_leg.addOrReplaceChild("leftBoot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_boot_tip = left_boot.addOrReplaceChild("leftBootTip", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_boot_tip1 = left_boot_tip.addOrReplaceChild("left_boot_tip1", CubeListBuilder.create().texOffs(68, 121).addBox(0.75F, -2.5F, -4.75F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

		PartDefinition left_boot1 = left_boot.addOrReplaceChild("left_boot1", CubeListBuilder.create().texOffs(64, 105).addBox(0.5F, -5.5F, -2.25F, 4.0F, 5.0F, 4.25F, new CubeDeformation(0.5F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

		PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-6.9F, 12.25F, 3.0F));

		PartDefinition right_leg_main = right_leg.addOrReplaceChild("right_leg_main", CubeListBuilder.create(), PartPose.offset(3.8F, 0.0F, 0.0F));

		PartDefinition right_leg_main1 = right_leg_main.addOrReplaceChild("right_leg_main1", CubeListBuilder.create().texOffs(15, 113).addBox(-4.35F, -11.6F, -2.1F, 4.0F, 9.25F, 4.0F, new CubeDeformation(0.4F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

		PartDefinition right_boot = right_leg.addOrReplaceChild("right_boot", CubeListBuilder.create(), PartPose.offset(3.8F, 0.0F, 0.0F));

		PartDefinition right_boot_tip = right_boot.addOrReplaceChild("right_boot_tip", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_boot_tip1 = right_boot_tip.addOrReplaceChild("right_boot_tip1", CubeListBuilder.create().texOffs(35, 120).addBox(-4.05F, -2.5F, -4.75F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

		PartDefinition right_boot1 = right_boot.addOrReplaceChild("right_boot1", CubeListBuilder.create().texOffs(31, 105).addBox(-4.5F, -5.5F, -2.25F, 4.0F, 5.0F, 4.25F, new CubeDeformation(0.5F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

		PartDefinition kama = root.addOrReplaceChild("kama", CubeListBuilder.create(), PartPose.offset(-5.0F, 12.25F, 5.0F));

		PartDefinition kama1 = kama.addOrReplaceChild("kama1", CubeListBuilder.create().texOffs(93, 36).mirror().addBox(-4.0F, -2.0F, 3.75F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 2.0F, -4.0F));

		PartDefinition right_kama1 = kama1.addOrReplaceChild("right_kama1", CubeListBuilder.create().texOffs(107, 25).addBox(-1.0F, -2.0F, -5.25F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 0.0F, 4.0F));

		PartDefinition right_front_kama1 = right_kama1.addOrReplaceChild("right_front_kama1", CubeListBuilder.create().texOffs(117, 25).mirror().addBox(-8.0F, -2.0F, 6.75F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.0F, 0.0F, -12.0F));

		PartDefinition left_kama1 = kama1.addOrReplaceChild("left_kama1", CubeListBuilder.create().texOffs(107, 36).mirror().addBox(0.0F, -2.0F, -5.25F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.0F, 0.0F, 4.0F));

		PartDefinition left_front_kama1 = left_kama1.addOrReplaceChild("left_front_kama1", CubeListBuilder.create().texOffs(117, 36).addBox(4.0F, -2.0F, 6.75F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 0.0F, -12.0F));

		PartDefinition kama2 = kama.addOrReplaceChild("kama2", CubeListBuilder.create().texOffs(87, 44).addBox(-3.0F, 0.0F, -1.0F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(84, 48).addBox(-2.0F, 2.0F, -1.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(95, 48).addBox(-1.0F, 4.0F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));

		PartDefinition right_arm = root.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-10.0F, 2.25F, 3.0F));

		PartDefinition right_arm_main = right_arm.addOrReplaceChild("right_arm_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_arm_main1 = right_arm_main.addOrReplaceChild("right_arm_main1", CubeListBuilder.create().texOffs(2, 84).addBox(-1.0689F, -1.7947F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.7F)), PartPose.offset(-2.5625F, 0.5447F, 0.0F));

		PartDefinition right_pauldron = right_arm.addOrReplaceChild("right_pauldron", CubeListBuilder.create(), PartPose.offset(10.0F, 0.0F, 0.0F));

		PartDefinition right_pauldron1 = right_pauldron.addOrReplaceChild("right_pauldron1", CubeListBuilder.create(), PartPose.offset(-12.5625F, 0.5447F, 0.0F));

		PartDefinition right_arm_r1 = right_pauldron1.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(1, 56).addBox(1.1217F, 2.2811F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.7F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -2.138F));

		PartDefinition right_arm_r2 = right_pauldron1.addOrReplaceChild("right_arm_r2", CubeListBuilder.create().texOffs(13, 58).addBox(-2.5765F, -1.5268F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.7F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition right_arm_r3 = right_pauldron1.addOrReplaceChild("right_arm_r3", CubeListBuilder.create().texOffs(10, 65).addBox(-1.4913F, -2.4945F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.7F))
				.texOffs(1, 64).addBox(-3.1984F, -2.9749F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.7F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.6109F));

		PartDefinition right_pauldron2 = right_pauldron.addOrReplaceChild("right_pauldron2", CubeListBuilder.create().texOffs(33, 93).mirror().addBox(-1.0689F, -3.0447F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(1.0F)).mirror(false), PartPose.offset(-12.5625F, 0.7947F, 0.0F));

		PartDefinition left_arm = root.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(0.0F, 2.25F, 3.0F));

		PartDefinition left_arm_main = left_arm.addOrReplaceChild("left_arm_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_arm_main1 = left_arm_main.addOrReplaceChild("left_arm_main1", CubeListBuilder.create().texOffs(64, 85).addBox(-2.931F, -1.7947F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.7F)), PartPose.offset(2.5625F, 0.5447F, 0.0F));

		PartDefinition left_pauldron = left_arm.addOrReplaceChild("left_pauldron", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_pauldron1 = left_pauldron.addOrReplaceChild("left_pauldron1", CubeListBuilder.create(), PartPose.offset(2.5625F, 0.7947F, 0.0F));

		PartDefinition left_arm_r1 = left_pauldron1.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(54, 54).addBox(-4.3325F, 2.4154F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.7F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 2.138F));

		PartDefinition left_arm_r2 = left_pauldron1.addOrReplaceChild("left_arm_r2", CubeListBuilder.create().texOffs(54, 63).addBox(-0.3694F, -1.7709F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.7F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

		PartDefinition left_arm_r3 = left_pauldron1.addOrReplaceChild("left_arm_r3", CubeListBuilder.create().texOffs(66, 57).addBox(0.3418F, -3.1797F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.7F))
				.texOffs(64, 65).addBox(0.6347F, -2.6993F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.7F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition left_pauldron2 = left_pauldron.addOrReplaceChild("left_pauldron2", CubeListBuilder.create().texOffs(33, 85).addBox(-2.931F, -3.0447F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(1.0F)), PartPose.offset(2.5625F, 0.7947F, 0.0F));

		PartDefinition hat = root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(-5.0F, 24.25F, 3.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}


	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		this.head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.leftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.rightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.leftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void render(ItemStack pStack, SlotContext slot, PoseStack matrixStack, MultiBufferSource buffer, int light)
	{
		this.root.visible = true;

		//We can leave this alone, since it doesn't do anything.
		//Maybe we should attach the faceplate to this layer?
		this.hat.getAllParts().forEach(part -> part.visible = false);
		//now we need to get the actual data from the itemstack
		//and set the correct pieces to be visible

		if (!pStack.getCapability(ShardData.SHARD_DATA).isPresent())
		{
			return;
		}


		final ShardplateCurioItem item = (ShardplateCurioItem) pStack.getItem();

		final DynamicShardplateData data = item.getShardData(pStack);

		final LivingEntity entity = slot.entity();

		for (var comp: ShardHelper.COMPONENTS)
		{
			renderComponent(comp, data);
		}


		Color color = item.getColour(pStack);
		// First texture layer
		VertexConsumer baseLayer = buffer.getBuffer(RenderType.armorCutoutNoCull(TEXTURE));
		renderToBuffer(matrixStack,
				baseLayer,
				light,
				OverlayTexture.NO_OVERLAY,
				color.getRed() / 255f,
				color.getGreen() / 255f,
				color.getBlue() / 255f,
				1f);

		// Second overlay texture (e.g., glowing runes, color tint)


		// Third layer
		if (data.isLiving())
		{
			String location = "textures/models/armor/glyphs/" + data.getOrder().getName().toLowerCase() + "_glyph.png";
			ResourceLocation glyphRL = Surgebinding.rl(location);
			Color glyphColor = item.getOrder(pStack).getColor();
			glyphColor = glyphColor.brighter();
/*
		Trims, if and when we have them.
			VertexConsumer lightLayer = buffer.getBuffer(RenderType.armorCutoutNoCull(TRIM));
			renderToBuffer(matrixStack,
					lightLayer,
					LightTexture.FULL_BRIGHT,
					OverlayTexture.NO_OVERLAY,
					glyphColor.getRed()/255F,
					glyphColor.getGreen()/255F,
					glyphColor.getBlue()/255F,
					1F);
*/

			VertexConsumer glyphLayer = buffer.getBuffer(RenderType.armorCutoutNoCull(glyphRL));
			renderToBuffer(matrixStack,
					glyphLayer,
					LightTexture.FULL_BRIGHT,
					OverlayTexture.NO_OVERLAY,
					glyphColor.getRed() / 255f,
					glyphColor.getGreen() / 255f,
					glyphColor.getBlue() / 255f,
					1f);

			VertexConsumer overlayLayer = buffer.getBuffer(RenderType.armorCutoutNoCull(VISOR));
			renderToBuffer(matrixStack,
					overlayLayer,
					LightTexture.FULL_BRIGHT,
					OverlayTexture.NO_OVERLAY,
					glyphColor.getRed() / 255f,
					glyphColor.getGreen() / 255f,
					glyphColor.getBlue() / 255f,
					1f);


		}
		else
		{
			VertexConsumer overlayLayer = buffer.getBuffer(RenderType.armorCutoutNoCull(VISOR));
			renderToBuffer(matrixStack,
					overlayLayer,
					LightTexture.FULL_BRIGHT,
					OverlayTexture.NO_OVERLAY,
					0.6F,
					0.8F,
					1,
					1f);
		}

		//Cracked Layer
		if (item.getMaxCharge(pStack) - item.getCharge(pStack) > item.getMaxCharge(pStack) / 4)
		{
			VertexConsumer cracksLayer = buffer.getBuffer(RenderType.armorCutoutNoCull(CRACKS1));
			renderToBuffer(matrixStack,
					cracksLayer,
					15728700,
					OverlayTexture.NO_OVERLAY,
					1f,
					1f,
					1f,
					1f);
		}


	}

	public void setupAnim(@NotNull LivingEntity entity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch)
	{
		if (entity instanceof ArmorStand)
		{
			super.setupAnim(entity, 0, 0, 0, 0, 0);
		}
		else
		{
			super.setupAnim(entity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
		}
	}

	private void renderComponent(ShardHelper.PlateComponent component, DynamicShardplateData data)
	{
		String compName = component.lowerSnake;
		ModelPart part = root.getChild(compName);

		//Turn everything off.
		part.visible = false;
		int n = 1;
		while (part.hasChild(compName + n))
		{
			part.getChild(compName + n).getAllParts().forEach(subPart -> subPart.visible = false);
			n++;
		}

		//turn the right thing on again.
		int id = data.id(component);
		if(id >= 1)
		{
			part.visible = true;
			part.getChild(data.compID(component)).getAllParts().forEach(modelPart -> modelPart.visible = true);
		}
	}

}
