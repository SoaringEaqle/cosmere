package leaf.cosmere.surgebinding.client.render.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import leaf.cosmere.surgebinding.common.Surgebinding;
import leaf.cosmere.surgebinding.common.capabilities.DynamicShardplateData;
import leaf.cosmere.surgebinding.common.items.DeadplateItem;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;

public class DynamicShardplateModel extends HumanoidModel<LivingEntity>
{
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ResourceLocation TEXTURE = Surgebinding.rl("textures/models.armor/windrunner_shardplat.png");
	public static final int TOTAL_HELMET_IDS = 1;
	public static final int TOTAL_ARM_IDS = 1;
	public static final int TOTAL_TORSO_IDS = 1;
	public static final int TOTAL_LEG_IDS = 1;
	public static final int TOTAL_KAMA_IDS = 1;
	public static final int TOTAL_BOOT_IDS = 1;

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "windrunner_shardplate"), "main");
	public final ModelPart root;

	public final ModelPart mHead;
	public final ModelPart head1;
	public final ModelPart faceplate1;

	public final ModelPart mBody;
	public final ModelPart body1;
	public final ModelPart chestplate1;
	public final ModelPart breastplate1;
	public final ModelPart right_plate;
	public final ModelPart left_plate;

	public final ModelPart left_legs;
	public final ModelPart left_leg1;
	public final ModelPart leftleg1;
	public final ModelPart left_boot1;
	public final ModelPart leftboot_outside1;
	public final ModelPart leftboot_tip1;

	public final ModelPart right_legs;
	public final ModelPart right_leg1;
	public final ModelPart rightleg1;
	public final ModelPart right_boot1;
	public final ModelPart rightboot_outside1;
	public final ModelPart rightboot_tip1;

	public final ModelPart mKama;
	public final ModelPart kama1;
	public final ModelPart left_kama1;
	public final ModelPart left_front_kama1;
	public final ModelPart right_kama1;
	public final ModelPart right_front_kama1;

	public final ModelPart arms;
	public final ModelPart right_arm1;
	public final ModelPart right_armb1;
	public final ModelPart right_paldron1;
	public final ModelPart left_arm1;
	public final ModelPart left_armb1;
	public final ModelPart left_paldron1;
	public final ModelPart hat;

	public DynamicShardplateModel(ModelPart root) {

		super(root, RenderType::entityCutoutNoCull);

		this.root = root.getChild("root");

		this.mHead = this.root.getChild("mHead");
		this.head1 = this.mHead.getChild("head1");
		this.faceplate1 = this.head1.getChild("faceplate1");

		this.mBody = this.root.getChild("mBody");
		this.body1 = this.mBody.getChild("body1");
		this.chestplate1 = this.body1.getChild("chestplate1");
		this.breastplate1 = this.body1.getChild("breastplate1");
		this.right_plate = this.breastplate1.getChild("right_plate");
		this.left_plate = this.breastplate1.getChild("left_plate");


		this.left_legs = this.root.getChild("left_legs");
		this.left_leg1 = this.left_legs.getChild("left_leg1");
		this.leftleg1 = this.left_leg1.getChild("leftleg1");
		this.left_boot1 = this.left_leg1.getChild("left_boot1");
		this.leftboot_outside1 = this.left_boot1.getChild("leftboot_outside1");
		this.leftboot_tip1 = this.left_boot1.getChild("leftboot_tip1");

		this.right_legs = this.root.getChild("right_legs");
		this.right_leg1 = this.right_legs.getChild("right_leg1");
		this.rightleg1 = this.right_leg1.getChild("rightleg1");
		this.right_boot1 = this.right_leg1.getChild("right_boot1");
		this.rightboot_outside1 = this.right_boot1.getChild("rightboot_outside1");
		this.rightboot_tip1 = this.right_boot1.getChild("rightboot_tip1");

		this.mKama = this.root.getChild("mKama");
		this.kama1 = this.mKama.getChild("kama1");
		this.left_kama1 = this.kama1.getChild("left_kama1");
		this.left_front_kama1 = this.left_kama1.getChild("left_front_kama");
		this.right_kama1 = this.kama1.getChild("right_kama1");
		this.right_front_kama1 = this.right_kama1.getChild("right_front_kama1");

		this.arms = this.root.getChild("arms");
		this.right_arm1 = this.arms.getChild("right_arm1");
		this.right_armb1 = this.right_arm1.getChild("right_armb1");
		this.right_paldron1 = this.right_arm1.getChild("right_paldron1");
		this.left_arm1 = this.arms.getChild("left_arm1");
		this.left_armb1 = this.left_arm1.getChild("left_armb1");
		this.left_paldron1 = this.left_arm1.getChild("left_paldron1");


		this.hat = root.getChild("hat");
	}

	public static LayerDefinition createBodyLayer()
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(5.0F, -0.25F, -3.0F));

		PartDefinition mHead = root.addOrReplaceChild("mHead", CubeListBuilder.create(), PartPose.offset(-5.0F, 0.75F, 3.0F));

		PartDefinition head1 = mHead.addOrReplaceChild("head1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.75F, -2.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.6F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition faceplate1 = head1.addOrReplaceChild("faceplate1", CubeListBuilder.create().texOffs(33, 38).addBox(-4.0F, 1.0F, -0.5F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.6F))
				.texOffs(64, 30).addBox(-2.0F, 1.0F, -1.5F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.6F)), PartPose.offset(0.0F, -9.75F, -2.5F));

		PartDefinition cube_r1 = faceplate1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(32, 62).mirror().addBox(1.4F, -8.0F, 0.5F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.6F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 9.0F, -0.5F, 0.0F, 0.5672F, 0.0F));

		PartDefinition cube_r2 = faceplate1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(47, 63).addBox(-5.4F, -8.0F, 0.5F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(5.0F, 9.0F, -0.5F, 0.0F, -0.5672F, 0.0F));

		PartDefinition mBody = root.addOrReplaceChild("mBody", CubeListBuilder.create(), PartPose.offset(-5.0F, 0.25F, 3.0F));

		PartDefinition body1 = mBody.addOrReplaceChild("body1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition chestplate1 = body1.addOrReplaceChild("chestplate1", CubeListBuilder.create().texOffs(24, 14).addBox(-4.0F, 0.75F, -2.5F, 8.0F, 3.0F, 5.0F, new CubeDeformation(0.75F))
				.texOffs(0, 14).addBox(-4.0F, 3.5F, -2.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition breastplate1 = body1.addOrReplaceChild("breastplate1", CubeListBuilder.create().texOffs(4, 18).addBox(-4.0F, -4.0F, 1.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 7.5F, -3.0F));

		PartDefinition right_plate = breastplate1.addOrReplaceChild("right_plate", CubeListBuilder.create().texOffs(30, 19).addBox(1.0F, -3.25F, 0.5F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.75F)), PartPose.offset(-5.0F, -3.5F, 0.0F));

		PartDefinition left_plate = breastplate1.addOrReplaceChild("left_plate", CubeListBuilder.create().texOffs(24, 28).addBox(-4.0F, -3.25F, 0.5F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.75F)), PartPose.offset(5.0F, -3.5F, 0.0F));

		PartDefinition legs = root.addOrReplaceChild("left_legs", CubeListBuilder.create(), PartPose.offset(-3.1F, 12.25F, 3.0F));

		PartDefinition left_leg1 = legs.addOrReplaceChild("left_leg1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leftleg1 = left_leg1.addOrReplaceChild("leftleg1", CubeListBuilder.create().texOffs(16, 37).addBox(0.35F, -11.6F, -2.1F, 4.0F, 9.25F, 4.0F, new CubeDeformation(0.4F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

		PartDefinition left_boot1 = left_leg1.addOrReplaceChild("left_boot1", CubeListBuilder.create(), PartPose.offset(-1.9F, 12.0F, 0.0F));

		PartDefinition leftboot_outside1 = left_boot1.addOrReplaceChild("leftboot_outside1", CubeListBuilder.create().texOffs(40, 22).addBox(0.5F, -5.5F, -2.25F, 4.0F, 5.0F, 4.25F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leftboot_tip1 = left_boot1.addOrReplaceChild("leftboot_tip1", CubeListBuilder.create().texOffs(50, 41).addBox(0.75F, -2.5F, -4.75F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_leg1 = legs.addOrReplaceChild("right_leg1", CubeListBuilder.create(), PartPose.offset(-3.8F, 0.0F, 0.0F));

		PartDefinition rightleg1 = right_leg1.addOrReplaceChild("rightleg1", CubeListBuilder.create().texOffs(28, 0).addBox(-4.35F, -11.6F, -2.1F, 4.0F, 9.25F, 4.0F, new CubeDeformation(0.4F)), PartPose.offset(1.9F, 12.0F, 0.0F));

		PartDefinition right_boot1 = right_leg1.addOrReplaceChild("right_boot1", CubeListBuilder.create(), PartPose.offset(1.9F, 12.0F, 0.0F));

		PartDefinition rightboot_outside1 = right_boot1.addOrReplaceChild("rightboot_outside1", CubeListBuilder.create().texOffs(0, 41).addBox(-4.5F, -5.5F, -2.25F, 4.0F, 5.0F, 4.25F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition rightboot_tip1 = right_boot1.addOrReplaceChild("rightboot_tip1", CubeListBuilder.create().texOffs(50, 37).addBox(-4.05F, -2.5F, -4.75F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition mKama = root.addOrReplaceChild("mKama", CubeListBuilder.create(), PartPose.offset(-5.0F, 14.25F, 1.0F));

		PartDefinition kama1 = mKama.addOrReplaceChild("kama1", CubeListBuilder.create().texOffs(0, 56).mirror().addBox(-4.0F, -2.0F, 3.75F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_kama = kama1.addOrReplaceChild("left_kama1", CubeListBuilder.create().texOffs(44, 52).mirror().addBox(0.0F, -2.0F, -5.25F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.0F, 0.0F, 4.0F));

		PartDefinition left_front_kama = left_kama.addOrReplaceChild("left_front_kama", CubeListBuilder.create().texOffs(54, 52).addBox(-4.0F, -2.0F, -0.75F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.5F));

		PartDefinition right_kama = kama1.addOrReplaceChild("right_kama1", CubeListBuilder.create().texOffs(22, 51).addBox(-1.0F, -2.0F, -5.25F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 0.0F, 4.0F));

		PartDefinition right_front_kama = right_kama.addOrReplaceChild("right_front_kama1", CubeListBuilder.create().texOffs(37, 52).mirror().addBox(0.0F, -2.0F, -0.75F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, -4.5F));

		PartDefinition arms = root.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(-5.0F, 7.75F, 0.0F));

		PartDefinition right_arm1 = arms.addOrReplaceChild("right_arm1", CubeListBuilder.create(), PartPose.offset(-5.0F, -5.5F, 3.0F));

		PartDefinition right_armb1 = right_arm1.addOrReplaceChild("right_armb1", CubeListBuilder.create().texOffs(0, 26).addBox(-1.0689F, -1.7947F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.7F)), PartPose.offset(-2.5625F, 0.5447F, 0.0F));

		PartDefinition right_paldron1 = right_arm1.addOrReplaceChild("right_paldron1", CubeListBuilder.create(), PartPose.offset(-2.5625F, 0.5447F, 0.0F));

		PartDefinition right_arm_r1 = right_paldron1.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(50, 14).addBox(1.1217F, 2.2811F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.7F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -2.138F));

		PartDefinition right_arm_r2 = right_paldron1.addOrReplaceChild("right_arm_r2", CubeListBuilder.create().texOffs(0, 50).addBox(-2.5765F, -1.5268F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.7F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition right_arm_r3 = right_paldron1.addOrReplaceChild("right_arm_r3", CubeListBuilder.create().texOffs(44, 7).addBox(-1.4913F, -2.4945F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.7F))
				.texOffs(40, 31).addBox(-3.1984F, -2.9749F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.7F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.6109F));

		PartDefinition left_arm1 = arms.addOrReplaceChild("left_arm1", CubeListBuilder.create(), PartPose.offset(5.0F, -5.5F, 3.0F));

		PartDefinition left_armb1 = left_arm1.addOrReplaceChild("left_armb1", CubeListBuilder.create().texOffs(24, 22).addBox(-2.931F, -1.7947F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.7F)), PartPose.offset(2.5625F, 0.5447F, 0.0F));

		PartDefinition left_paldron1 = left_arm1.addOrReplaceChild("left_paldron1", CubeListBuilder.create(), PartPose.offset(2.5625F, 0.7947F, 0.0F));

		PartDefinition left_arm_r1 = left_paldron1.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(14, 50).addBox(-4.3325F, 2.4154F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.7F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 2.138F));

		PartDefinition left_arm_r2 = left_paldron1.addOrReplaceChild("left_arm_r2", CubeListBuilder.create().texOffs(46, 46).addBox(-0.3694F, -1.7709F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.7F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

		PartDefinition left_arm_r3 = left_paldron1.addOrReplaceChild("left_arm_r3", CubeListBuilder.create().texOffs(32, 46).addBox(0.3418F, -3.1797F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.7F))
				.texOffs(44, 0).addBox(0.6347F, -2.6993F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.7F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}


	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setup( LivingEntity entity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch)
	{
			this.root.visible = true;

			//kinda yuck that we can't just have block bench not include these folders in the first place
			final String helmetGuide = "helmet_guide";
			final String armGuide = "arm_guide";
			final String torsoGuide = "torso_guide";
			final String legGuide = "leg_guide";
			final String kamaGuide = "kama_guide";

			this.mHead.getChild(helmetGuide).visible = false;
			this.arms.getChild(armGuide).visible = false;
			this.mBody.getChild(torsoGuide).visible = false;
			this.left_legs.getChild(legGuide).visible = false;
			this.right_legs.getChild(legGuide).visible = false;
			this.mKama.getChild(kamaGuide).visible = false;

			this.mHead.getAllParts().forEach(part -> part.visible = false);
			this.arms.getAllParts().forEach(part -> part.visible = false);
			this.mBody.getAllParts().forEach(part -> part.visible = false);
			this.right_legs.getAllParts().forEach(part -> part.visible = false);
			this.left_legs.getAllParts().forEach(part -> part.visible = false);
			this.mKama.getAllParts().forEach(part -> part.visible = false);

			this.head.visible = true;
			this.arms.visible = true;
			this.body.visible = true;
			this.left_legs.visible = true;
			this.right_legs.visible = true;

			//now we need to get the actual data from the itemstack
			//and set the correct pieces to be visible

		if (!pStack.getCapability(DeadplateItem.CAPABILITY).isPresent())
		{
			return;
		}

		final DynamicShardplateData data = pStack.getCapability(DeadplateItem.CAPABILITY).resolve().get();


		this.mHead.getChild(data.getHeadID()).visible = true;
		this.arms.getChild(data.getArmsID()).visible = true;
		this.body.getChild(data.getBodyID()).visible = true;
		this.left_legs.getChild(data.getLegsID()).visible = true;
		this.right_legs.getChild(data.getLegsID()).visible = true;
		this.mKama.getChild(data.getKamaID()).visible = true;

		if (entity instanceof ArmorStand)
		{
			super.setupAnim(entity, 0, 0, 0, 0, 0);
		}
		else
		{
			super.setupAnim(entity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
		}



	}
	public static final AnimationDefinition DISMISS = AnimationDefinition.Builder.withLength(1f)
			.addAnimation("head",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.08333f, KeyframeAnimations.posVec(0f, 2f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 7f, 7f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 10f, 12f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 10f, 18f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("head",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.08333f, KeyframeAnimations.degreeVec(-10f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(-32.5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(-57.5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(-90f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("head",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.08333f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("body",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("chestplate",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 6f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 1f, 13f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 5f, 22f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 5f, 33f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("chestplate",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.08333f, KeyframeAnimations.degreeVec(5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(25f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(30f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(60f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("chestplate",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.375f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_arm",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("rightleg",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.posVec(-2f, 3f, 5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 9f, 20f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("rightleg",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.125f, KeyframeAnimations.degreeVec(-12.5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(-52.26f, -5.94f, -4.58f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(-90f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("rightleg",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.08333f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("rightboot_outside2",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.08333f, KeyframeAnimations.posVec(0f, 1f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.posVec(-3f, 3f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(-4f, 4f, 4f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.posVec(-5f, 6f, 9f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(-5f, 9f, 15f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("rightboot_outside2",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.08333f, KeyframeAnimations.degreeVec(0f, 0f, -22.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, -35f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(-22.5f, 0f, -35f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(-56.51f, 10.07f, -23.82f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(-67.82f, 3.05f, -13.41f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("rightboot_outside2",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.08333f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("rightboot_tip",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.08333f, KeyframeAnimations.posVec(-3f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.posVec(-5f, 0f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.375f, KeyframeAnimations.posVec(-5f, 1f, 3f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(-6f, 3f, 8f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.posVec(-6f, 7f, 14f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(-6f, 12f, 19f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("rightboot_tip",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 27.5f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.375f, KeyframeAnimations.degreeVec(22.74f, 65.48f, 24.74f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(42.24f, 76.19f, 45.26f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(81.78f, 38.32f, 90.43f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("rightboot_tip",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.08333f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leftleg",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.posVec(2f, 3f, 5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 9f, 20f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leftleg",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.125f, KeyframeAnimations.degreeVec(-12.5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(-52.26f, 5.94f, 4.58f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(-90f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leftleg",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.08333f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leftboot_outside",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.08333f, KeyframeAnimations.posVec(0f, 1f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.posVec(3f, 3f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(4f, 4f, 4f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.posVec(5f, 6f, 9f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(5f, 9f, 15f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leftboot_outside",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.08333f, KeyframeAnimations.degreeVec(0f, 0f, 22.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 35f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(-22.5f, 0f, 35f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(-56.51f, -10.07f, 23.82f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(-67.82f, -3.05f, 13.41f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leftboot_outside",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.08333f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leftboot_tip",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.08333f, KeyframeAnimations.posVec(3f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.posVec(5f, 0f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.375f, KeyframeAnimations.posVec(5f, 1f, 3f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(6f, 3f, 8f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.posVec(6f, 7f, 14f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(6f, 12f, 19f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leftboot_tip",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, -27.5f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.375f, KeyframeAnimations.degreeVec(22.74f, -65.48f, -24.74f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(42.24f, -76.19f, -45.26f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(81.78f, -38.32f, -90.43f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leftboot_tip",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.08333f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_armb",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.08333f, KeyframeAnimations.posVec(0f, -2f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.posVec(-5f, -2f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(-8f, -4f, 4f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.posVec(-9f, -4f, 8f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(-9f, -4f, 18f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_armb",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0.08333f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, -25f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(-20f, 0f, -25f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(-52.5f, 0f, -25f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(-90.08f, 5.14f, 0.48f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_armb",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.08333f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_paldron",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.08333f, KeyframeAnimations.posVec(-2f, 2f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.posVec(-4f, 4f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(-4f, 4f, 8f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.posVec(-6f, 2f, 17f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(-7f, 2f, 22f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_paldron",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, -12.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(32.5f, 0f, -12.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(12.5f, 0f, -12.5f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_paldron",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_armb",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.08333f, KeyframeAnimations.posVec(0f, -2f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.posVec(5f, -2f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(8f, -4f, 4f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.posVec(9f, -4f, 8f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(9f, -4f, 18f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_armb",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0.08333f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 25f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(-20f, 0f, 25f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(-52.5f, 0f, 25f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(-90.08f, -5.14f, -0.48f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_armb",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.08333f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_paldron",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.08333f, KeyframeAnimations.posVec(2f, 2f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.posVec(4f, 4f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(4f, 4f, 8f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.posVec(6f, 2f, 17f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(7f, 2f, 22f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_paldron",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(32.5f, 0f, 12.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(12.5f, 0f, 12.5f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_paldron",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_front_kama",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.20834334f, KeyframeAnimations.degreeVec(0f, 42.5f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, -62.5f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("breastplate",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("faceplate",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(-17.5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("kama",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0.08343333f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 18f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 7f, 23f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("kama",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(50f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("kama",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.08343333f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_kama",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.2916767f, KeyframeAnimations.degreeVec(0f, 37.5f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(0f, 37.5f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, -50f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_kama",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.2916767f, KeyframeAnimations.degreeVec(0f, -37.5f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(0f, -37.5f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 50f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_front_kama",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.20834334f, KeyframeAnimations.degreeVec(0f, -42.5f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 62.5f, 0f),
									AnimationChannel.Interpolations.LINEAR))).build();
	public static final AnimationDefinition SUMMON = AnimationDefinition.Builder.withLength(1f)
			.addAnimation("head",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 10f, 18f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 10f, 12f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 7f, 7f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.91667f, KeyframeAnimations.posVec(0f, 2f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("head",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(-90f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(-57.5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(-32.5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.91667f, KeyframeAnimations.degreeVec(-10f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("head",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.91667f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("body",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("chestplate",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 5f, 33f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 5f, 22f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 1f, 13f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 6f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("chestplate",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(60f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(30f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(25f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.91667f, KeyframeAnimations.degreeVec(5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("chestplate",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.625f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_arm",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("rightleg",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 9f, 20f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.posVec(-2f, 3f, 5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("rightleg",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(-90f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(-52.26f, -5.94f, -4.58f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.875f, KeyframeAnimations.degreeVec(-12.5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("rightleg",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.91667f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("rightboot_outside2",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(-5f, 9f, 15f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.posVec(-5f, 6f, 9f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(-4f, 4f, 4f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.posVec(-3f, 3f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.91667f, KeyframeAnimations.posVec(0f, 1f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("rightboot_outside2",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(-67.82f, 3.05f, -13.41f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(-56.51f, 10.07f, -23.82f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(-22.5f, 0f, -35f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, -35f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.91667f, KeyframeAnimations.degreeVec(0f, 0f, -22.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("rightboot_outside2",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.91667f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("rightboot_tip",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(-6f, 12f, 19f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.posVec(-6f, 7f, 14f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(-6f, 3f, 8f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.625f, KeyframeAnimations.posVec(-5f, 1f, 3f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.posVec(-5f, 0f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.91667f, KeyframeAnimations.posVec(-3f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("rightboot_tip",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(81.78f, 38.32f, 90.43f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(42.24f, 76.19f, 45.26f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.625f, KeyframeAnimations.degreeVec(22.74f, 65.48f, 24.74f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 27.5f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("rightboot_tip",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.91667f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leftleg",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 9f, 20f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.posVec(2f, 3f, 5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leftleg",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(-90f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(-52.26f, 5.94f, 4.58f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.875f, KeyframeAnimations.degreeVec(-12.5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leftleg",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.91667f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leftboot_outside",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(5f, 9f, 15f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.posVec(5f, 6f, 9f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(4f, 4f, 4f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.posVec(3f, 3f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.91667f, KeyframeAnimations.posVec(0f, 1f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leftboot_outside",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(-67.82f, -3.05f, 13.41f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(-56.51f, -10.07f, 23.82f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(-22.5f, 0f, 35f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 35f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.91667f, KeyframeAnimations.degreeVec(0f, 0f, 22.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leftboot_outside",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.91667f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leftboot_tip",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(6f, 12f, 19f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.posVec(6f, 7f, 14f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(6f, 3f, 8f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.625f, KeyframeAnimations.posVec(5f, 1f, 3f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.posVec(5f, 0f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.91667f, KeyframeAnimations.posVec(3f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leftboot_tip",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(81.78f, -38.32f, -90.43f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(42.24f, -76.19f, -45.26f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.625f, KeyframeAnimations.degreeVec(22.74f, -65.48f, -24.74f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, -27.5f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leftboot_tip",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.91667f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_armb",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(-9f, -4f, 18f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.posVec(-9f, -4f, 8f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(-8f, -4f, 4f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.posVec(-5f, -2f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.91667f, KeyframeAnimations.posVec(0f, -2f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_armb",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(-90.08f, 5.14f, 0.48f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(-52.5f, 0f, -25f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(-20f, 0f, -25f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, -25f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.91667f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_armb",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.91667f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_paldron",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(-7f, 2f, 22f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.posVec(-6f, 2f, 17f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(-4f, 4f, 8f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.posVec(-4f, 4f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.91667f, KeyframeAnimations.posVec(-2f, 2f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_paldron",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(12.5f, 0f, -12.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(32.5f, 0f, -12.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, -12.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_paldron",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_armb",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(9f, -4f, 18f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.posVec(9f, -4f, 8f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(8f, -4f, 4f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.posVec(5f, -2f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.91667f, KeyframeAnimations.posVec(0f, -2f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_armb",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(-90.08f, -5.14f, -0.48f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(-52.5f, 0f, 25f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(-20f, 0f, 25f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 25f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.91667f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_armb",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.91667f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_paldron",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(7f, 2f, 22f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.posVec(6f, 2f, 17f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(4f, 4f, 8f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.posVec(4f, 4f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.91667f, KeyframeAnimations.posVec(2f, 2f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_paldron",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(12.5f, 0f, 12.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(32.5f, 0f, 12.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_paldron",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_front_kama",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -62.5f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.7916766f, KeyframeAnimations.degreeVec(0f, 42.5f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("breastplate",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("faceplate",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(-17.5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("kama",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 7f, 23f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 18f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.9167666f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("kama",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(50f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("kama",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.9167666f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_kama",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -50f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5834333f, KeyframeAnimations.degreeVec(0f, 37.5f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.7083433f, KeyframeAnimations.degreeVec(0f, 37.5f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_kama",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 50f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5834333f, KeyframeAnimations.degreeVec(0f, -37.5f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.7083433f, KeyframeAnimations.degreeVec(0f, -37.5f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_front_kama",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 62.5f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.7916766f, KeyframeAnimations.degreeVec(0f, -42.5f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR))).build();
	public static final AnimationDefinition OPEN_MASK = AnimationDefinition.Builder.withLength(0.79167f)
			.addAnimation("faceplate",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(-70f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("faceplate",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.33333f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.79167f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR))).build();


}
