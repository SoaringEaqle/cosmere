/*
 * File updated ~ 9 - 3 - 2025 ~ Leaf
 */

package leaf.cosmere.surgebinding.client.render.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class ChullModel<T extends Entity> extends AgeableListModel<T>
{
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart backRightLeg;
	private final ModelPart backLeftLeg;
	private final ModelPart middleRightLeg;
	private final ModelPart middleLeftLeg;
	private final ModelPart frontRightLeg;
	private final ModelPart frontLeftLeg;
	private final ModelPart rightForeleg;
	private final ModelPart leftForeleg;

	public ChullModel(ModelPart pRoot)
	{
		this.root = pRoot.getChild("Everything");
		this.head = root.getChild("head");
		this.body = root.getChild("body");

		this.backRightLeg = root.getChild("back_right_leg");
		this.backLeftLeg = root.getChild("back_left_leg");

		this.middleRightLeg = root.getChild("middle_right_leg");
		this.middleLeftLeg = root.getChild("middle_left_leg");

		this.frontRightLeg = root.getChild("front_right_leg");
		this.frontLeftLeg = root.getChild("front_left_leg");

		this.rightForeleg = root.getChild("right_foreleg");
		this.leftForeleg = root.getChild("left_foreleg");
	}

	@Override
	protected Iterable<ModelPart> headParts()
	{
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelPart> bodyParts()
	{
		return ImmutableList.of(this.root);
	}


	public static LayerDefinition createBodyLayer()
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Everything = partdefinition.addOrReplaceChild("Everything", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition body = Everything.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-28.0F, -12.25F, -29.0F, 32.0F, 16.0F, 32.0F, new CubeDeformation(0.0F))
				.texOffs(0, 48).addBox(-27.0F, -28.0F, -28.0F, 30.0F, 16.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offset(12.0F, -5.75F, 13.0F));

		PartDefinition head = Everything.addOrReplaceChild("head", CubeListBuilder.create().texOffs(44, 112).addBox(-2.0F, -2.5F, 0.0F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.5F, 16.0F));

		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(114, 7).mirror().addBox(-0.5F, -3.1262F, 1.2302F, 1.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -0.0723F, 3.0096F, -0.5236F, -0.1309F, 0.0F));

		PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(114, 7).mirror().addBox(-0.5F, -4.7265F, 3.8452F, 1.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -0.0723F, 3.0096F, -0.8727F, -0.1309F, 0.0F));

		PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(114, 7).addBox(-0.5F, -4.7265F, 3.8452F, 1.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -0.0723F, 3.0096F, -0.8727F, 0.1309F, 0.0F));

		PartDefinition cube_r4 = head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(114, 7).addBox(-0.5F, -3.1262F, 1.2302F, 1.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -0.0723F, 3.0096F, -0.5236F, 0.1309F, 0.0F));

		PartDefinition cube_r5 = head.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(86, 109).addBox(2.0F, 2.5F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(86, 109).addBox(5.0F, 2.5F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -8.7754F, 8.6396F, -0.1745F, 0.0F, 0.0F));

		PartDefinition stem1_r1 = head.addOrReplaceChild("stem1_r1", CubeListBuilder.create().texOffs(0, 0).addBox(2.5F, 0.5F, -2.5F, 0.5F, 3.0F, 0.5F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(5.5F, 0.5F, -2.5F, 0.5F, 3.0F, 0.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.25F, -6.0254F, 7.8896F, -0.1745F, 0.0F, 0.0F));

		PartDefinition cube_r6 = head.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(62, 109).addBox(-3.0F, -2.5F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 3.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition right_foreleg = Everything.addOrReplaceChild("right_foreleg", CubeListBuilder.create(), PartPose.offset(10.0F, -7.0F, 15.0F));

		PartDefinition pincer2_r1 = right_foreleg.addOrReplaceChild("pincer2_r1", CubeListBuilder.create().texOffs(109, 95).addBox(7.2475F, -19.0731F, 5.3153F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5574F, 0.5545F, 0.5268F, -1.0313F, -0.5364F, 0.2852F));

		PartDefinition pincer1_r1 = right_foreleg.addOrReplaceChild("pincer1_r1", CubeListBuilder.create().texOffs(83, 95).mirror().addBox(7.2475F, -20.0853F, 10.5819F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 94).addBox(6.7475F, -20.5853F, -0.4181F, 5.0F, 7.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5574F, 0.5545F, 0.5268F, -0.7695F, -0.5364F, 0.2852F));

		PartDefinition arm2_r1 = right_foreleg.addOrReplaceChild("arm2_r1", CubeListBuilder.create().texOffs(62, 94).addBox(-1.0574F, 3.3415F, 4.4809F, 3.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5574F, 1.0545F, 1.5268F, 0.6981F, 0.2618F, 0.0F));

		PartDefinition arm1_r1 = right_foreleg.addOrReplaceChild("arm1_r1", CubeListBuilder.create().texOffs(92, 94).addBox(-1.5574F, -1.5545F, -1.5268F, 4.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5574F, 0.5545F, 1.5268F, 0.0F, 0.2618F, 0.0F));

		PartDefinition front_right_leg = Everything.addOrReplaceChild("front_right_leg", CubeListBuilder.create(), PartPose.offset(15.0F, -10.0F, 11.0F));

		PartDefinition leg1_r1 = front_right_leg.addOrReplaceChild("leg1_r1", CubeListBuilder.create().texOffs(0, 112).addBox(-2.2566F, -2.8606F, -2.8637F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4437F, 1.1094F, 0.4247F, 0.0F, -0.7854F, 0.0F));

		PartDefinition leg2_r1 = front_right_leg.addOrReplaceChild("leg2_r1", CubeListBuilder.create().texOffs(105, 60).addBox(-2.4703F, -7.2836F, -1.6566F, 7.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4437F, 3.1094F, 7.4247F, 0.6537F, -0.4718F, -1.0353F));

		PartDefinition cube_r7 = front_right_leg.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(92, 107).addBox(-2.628F, -9.1719F, -4.4066F, 4.0F, 6.0625F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(90, 48).addBox(-1.628F, 2.8906F, -7.4066F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(114, 11).addBox(-1.628F, 5.3906F, -8.8441F, 5.0F, 1.5F, 1.4375F, new CubeDeformation(0.0F))
				.texOffs(114, 5).addBox(-2.128F, 5.3906F, 6.5934F, 6.0F, 1.5F, 1.4375F, new CubeDeformation(0.0F))
				.texOffs(96, 0).addBox(-2.128F, 2.8906F, 3.5934F, 6.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(32, 94).addBox(-2.628F, -3.1094F, -4.4066F, 7.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4437F, 3.1094F, 7.4247F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r8 = front_right_leg.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(24, 112).addBox(-3.9F, -3.5F, -1.5F, 7.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.9319F, -2.2811F, 7.8075F, 0.5236F, 0.7854F, 0.0F));

		PartDefinition cube_r9 = front_right_leg.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(90, 55).addBox(-1.053F, 3.8596F, -1.2F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4437F, 3.1094F, 7.4247F, -1.0472F, -0.7854F, 0.0F));

		PartDefinition cube_r10 = front_right_leg.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(114, 0).addBox(-1.053F, 6.2067F, -4.969F, 4.0F, 2.875F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4437F, 3.1094F, 7.4247F, -0.5236F, -0.7854F, 0.0F));

		PartDefinition cube_r11 = front_right_leg.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(96, 0).addBox(-2.5867F, 2.8906F, 4.3787F, 6.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(114, 5).addBox(-2.5867F, 5.3906F, 7.3787F, 6.0F, 1.5F, 1.4375F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4437F, 3.1094F, 7.4247F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r12 = front_right_leg.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 120).addBox(-2.1117F, 3.8354F, -1.814F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4437F, 3.1094F, 7.4247F, 1.0472F, 0.7854F, 0.0F));

		PartDefinition cube_r13 = front_right_leg.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(96, 7).addBox(-2.1117F, 6.1927F, 1.9448F, 5.0F, 2.875F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4437F, 3.1094F, 7.4247F, 0.5236F, 0.7854F, 0.0F));

		PartDefinition cube_r14 = front_right_leg.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(96, 7).addBox(-1.653F, 5.8F, 1.2647F, 5.0F, 2.875F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4437F, 3.1094F, 7.4247F, 0.5236F, -0.7854F, 0.0F));

		PartDefinition cube_r15 = front_right_leg.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, 120).addBox(-1.653F, 3.1553F, -2.2067F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4437F, 3.1094F, 7.4247F, 1.0472F, -0.7854F, 0.0F));

		PartDefinition middle_right_leg = Everything.addOrReplaceChild("middle_right_leg", CubeListBuilder.create().texOffs(105, 52).addBox(-0.2352F, -1.7512F, -1.75F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(92, 107).addBox(9.0F, -6.0625F, -4.0F, 4.0F, 6.0625F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(90, 48).addBox(10.0F, 6.0F, -7.0F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(114, 5).addBox(9.5F, 8.5F, 7.0F, 6.0F, 1.5F, 1.4375F, new CubeDeformation(0.0F))
				.texOffs(96, 0).addBox(9.5F, 6.0F, 4.0F, 6.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(32, 94).addBox(9.0F, 0.0F, -4.0F, 7.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(114, 11).addBox(10.0F, 8.5F, -8.4375F, 5.0F, 1.5F, 1.4375F, new CubeDeformation(0.0F)), PartPose.offset(16.0F, -10.0F, 0.0F));

		PartDefinition cube_r16 = middle_right_leg.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, 120).addBox(-1.4951F, 3.5075F, -2.0034F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.4951F, 3.1094F, 0.0F, 1.0472F, 0.0F, 0.0F));

		PartDefinition cube_r17 = middle_right_leg.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(96, 7).addBox(-1.4951F, 6.0034F, 1.6169F, 5.0F, 2.875F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.4951F, 3.1094F, 0.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition cube_r18 = middle_right_leg.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(114, 5).addBox(-2.9933F, 5.3906F, 7.5116F, 6.0F, 1.5F, 1.4375F, new CubeDeformation(0.0F))
				.texOffs(96, 0).addBox(-2.9933F, 2.8906F, 4.5116F, 6.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.4951F, 3.1094F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r19 = middle_right_leg.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(0, 120).addBox(-2.5184F, 3.9505F, -1.7476F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.4951F, 3.1094F, 0.0F, 0.0F, 1.5708F, -1.0472F));

		PartDefinition cube_r20 = middle_right_leg.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(90, 55).addBox(-0.9701F, 3.5075F, -0.9967F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.4451F, 3.1094F, 0.0F, -1.0472F, 0.0F, 0.0F));

		PartDefinition cube_r21 = middle_right_leg.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(24, 112).addBox(-3.4951F, -7.4404F, 2.456F, 7.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.4951F, 3.1094F, 0.0F, 0.0F, 1.5708F, -0.5236F));

		PartDefinition leg2_r2 = middle_right_leg.addOrReplaceChild("leg2_r2", CubeListBuilder.create().texOffs(105, 60).addBox(-2.3849F, -7.1818F, -1.25F, 7.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.4951F, 3.1094F, 0.0F, 0.0F, 0.0F, -0.8727F));

		PartDefinition back_right_leg = Everything.addOrReplaceChild("back_right_leg", CubeListBuilder.create(), PartPose.offset(15.0F, -10.0F, -11.0F));

		PartDefinition cube_r22 = back_right_leg.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(32, 94).addBox(-2.628F, -3.1094F, -3.5934F, 7.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(114, 5).addBox(-2.128F, 5.3906F, -8.0309F, 6.0F, 1.5F, 1.4375F, new CubeDeformation(0.0F))
				.texOffs(114, 11).addBox(-1.628F, 5.3906F, 7.4066F, 5.0F, 1.5F, 1.4375F, new CubeDeformation(0.0F))
				.texOffs(90, 63).addBox(-1.628F, 2.8906F, 4.4066F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(92, 107).addBox(-2.628F, -9.1719F, -3.5934F, 4.0F, 6.0625F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 112).addBox(-12.8632F, -4.8606F, -1.8434F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4437F, 3.1094F, -7.4247F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r23 = back_right_leg.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(90, 71).addBox(-3.0F, -2.0F, -1.5F, 6.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.4587F, 8.0F, -11.6429F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r24 = back_right_leg.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(0, 120).addBox(-1.603F, 3.1553F, -0.7933F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4437F, 3.1094F, -7.4247F, -1.0472F, 0.7854F, 0.0F));

		PartDefinition cube_r25 = back_right_leg.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(96, 7).addBox(-1.603F, 5.8F, -4.2647F, 5.0F, 2.875F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4437F, 3.1094F, -7.4247F, -0.5236F, 0.7854F, 0.0F));

		PartDefinition cube_r26 = back_right_leg.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(114, 5).addBox(-2.5867F, 5.3906F, -8.8162F, 6.0F, 1.5F, 1.4375F, new CubeDeformation(0.0F))
				.texOffs(90, 71).addBox(-2.5867F, 2.8906F, -7.3787F, 6.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4437F, 3.1094F, -7.4247F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r27 = back_right_leg.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(96, 7).addBox(-16.4617F, 10.1927F, 0.0552F, 5.0F, 2.875F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.2644F, -1.2995F, -2.9434F, 0.0F, 1.5708F, -0.5236F));

		PartDefinition cube_r28 = back_right_leg.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(96, 7).addBox(-2.0617F, 6.1927F, -4.9448F, 5.0F, 2.875F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4437F, 3.1094F, -7.4247F, -0.5236F, -0.7854F, 0.0F));

		PartDefinition cube_r29 = back_right_leg.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(0, 120).addBox(-2.0617F, 3.8354F, -1.186F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4437F, 3.1094F, -7.4247F, -1.0472F, -0.7854F, 0.0F));

		PartDefinition cube_r30 = back_right_leg.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(114, 0).addBox(-1.153F, 6.2067F, 1.969F, 4.0F, 2.875F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4437F, 3.1094F, -7.4247F, 0.5236F, 0.7854F, 0.0F));

		PartDefinition cube_r31 = back_right_leg.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(90, 55).addBox(-1.153F, 3.8596F, -1.8F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4437F, 3.1094F, -7.4247F, 1.0472F, 0.7854F, 0.0F));

		PartDefinition cube_r32 = back_right_leg.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(24, 112).addBox(-3.525F, -3.5F, -1.5F, 7.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.6667F, -2.2811F, -8.0726F, -2.618F, 0.7854F, 3.1416F));

		PartDefinition leg2_r3 = back_right_leg.addOrReplaceChild("leg2_r3", CubeListBuilder.create().texOffs(105, 60).addBox(-2.4703F, -7.2836F, -1.3434F, 7.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4437F, 3.1094F, -7.4247F, -0.6537F, 0.4718F, -1.0353F));

		PartDefinition left_foreleg = Everything.addOrReplaceChild("left_foreleg", CubeListBuilder.create(), PartPose.offset(-10.0F, -7.0F, 15.0F));

		PartDefinition hand_r1 = left_foreleg.addOrReplaceChild("hand_r1", CubeListBuilder.create().texOffs(0, 94).mirror().addBox(-11.7475F, -20.5853F, -0.4181F, 5.0F, 7.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(83, 95).addBox(-11.2475F, -20.0853F, 10.5819F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5574F, 0.5545F, 0.5268F, -0.7695F, 0.5364F, -0.2852F));

		PartDefinition pincer2_r2 = left_foreleg.addOrReplaceChild("pincer2_r2", CubeListBuilder.create().texOffs(109, 95).addBox(-11.2475F, -19.0731F, 5.3153F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5574F, 0.5545F, 0.5268F, -1.0313F, 0.5364F, -0.2852F));

		PartDefinition arm1_r2 = left_foreleg.addOrReplaceChild("arm1_r2", CubeListBuilder.create().texOffs(62, 94).mirror().addBox(-1.9426F, 3.3415F, 4.4809F, 3.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5574F, 1.0545F, 1.5268F, 0.6981F, -0.2618F, 0.0F));

		PartDefinition arm2_r2 = left_foreleg.addOrReplaceChild("arm2_r2", CubeListBuilder.create().texOffs(92, 94).mirror().addBox(-2.4426F, -1.5545F, -1.5268F, 4.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5574F, 0.5545F, 1.5268F, 0.0F, -0.2618F, 0.0F));

		PartDefinition front_left_leg = Everything.addOrReplaceChild("front_left_leg", CubeListBuilder.create(), PartPose.offset(-15.0F, -10.0F, 11.0F));

		PartDefinition cube_r33 = front_left_leg.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(32, 94).mirror().addBox(-4.372F, -3.1094F, -4.4066F, 7.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(96, 0).mirror().addBox(-3.872F, 2.8906F, 3.5934F, 6.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(114, 5).mirror().addBox(-3.872F, 5.3906F, 6.5934F, 6.0F, 1.5F, 1.4375F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(113, 13).mirror().addBox(-3.372F, 5.3906F, -8.8441F, 5.0F, 1.5F, 1.4375F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(90, 48).mirror().addBox(-3.372F, 2.8906F, -7.4066F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(92, 107).mirror().addBox(-1.372F, -9.1719F, -4.4066F, 4.0F, 6.0625F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 112).mirror().addBox(4.8632F, -4.8606F, -2.1566F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.4437F, 3.1094F, 7.4247F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r34 = front_left_leg.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(0, 120).mirror().addBox(-3.397F, 3.1553F, -2.2067F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.4437F, 3.1094F, 7.4247F, 1.0472F, 0.7854F, 0.0F));

		PartDefinition cube_r35 = front_left_leg.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(96, 7).mirror().addBox(-3.397F, 5.8F, 1.2647F, 5.0F, 2.875F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.4437F, 3.1094F, 7.4247F, 0.5236F, 0.7854F, 0.0F));

		PartDefinition cube_r36 = front_left_leg.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(114, 5).mirror().addBox(-3.4133F, 5.3906F, 7.3787F, 6.0F, 1.5F, 1.4375F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(96, 0).mirror().addBox(-3.4133F, 2.8906F, 4.3787F, 6.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.4437F, 3.1094F, 7.4247F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r37 = front_left_leg.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(96, 7).mirror().addBox(-2.8383F, 6.1927F, 1.9448F, 5.0F, 2.875F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(24, 112).mirror().addBox(-3.8816F, -7.5068F, 2.341F, 7.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.4437F, 3.1094F, 7.4247F, 0.5236F, -0.7854F, 0.0F));

		PartDefinition cube_r38 = front_left_leg.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(0, 120).mirror().addBox(-2.8383F, 3.8354F, -1.814F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.4437F, 3.1094F, 7.4247F, 1.0472F, -0.7854F, 0.0F));

		PartDefinition cube_r39 = front_left_leg.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(114, 0).mirror().addBox(-2.847F, 6.2067F, -4.969F, 4.0F, 2.875F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.4437F, 3.1094F, 7.4247F, -0.5236F, 0.7854F, 0.0F));

		PartDefinition cube_r40 = front_left_leg.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(90, 55).mirror().addBox(-2.847F, 3.8596F, -1.2F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.4437F, 3.1094F, 7.4247F, -1.0472F, 0.7854F, 0.0F));

		PartDefinition leg2_r4 = front_left_leg.addOrReplaceChild("leg2_r4", CubeListBuilder.create().texOffs(105, 60).mirror().addBox(-4.5297F, -7.2836F, -1.6566F, 7.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.4437F, 3.1094F, 7.4247F, 0.6537F, 0.4718F, 1.0353F));

		PartDefinition middle_left_leg = Everything.addOrReplaceChild("middle_left_leg", CubeListBuilder.create().texOffs(32, 94).mirror().addBox(-16.0F, 0.0F, -4.0F, 7.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(96, 0).mirror().addBox(-15.5F, 6.0F, 4.0F, 6.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(114, 5).mirror().addBox(-15.5F, 8.5F, 7.0F, 6.0F, 1.5F, 1.4375F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(114, 11).mirror().addBox(-15.0F, 8.5F, -8.4375F, 5.0F, 1.5F, 1.4375F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(90, 48).mirror().addBox(-15.0F, 6.0F, -7.0F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(92, 107).mirror().addBox(-13.0F, -6.0625F, -4.0F, 4.0F, 6.0625F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(105, 52).mirror().addBox(-6.7648F, -1.7512F, -1.75F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-16.0F, -10.0F, 0.0F));

		PartDefinition leg2_r5 = middle_left_leg.addOrReplaceChild("leg2_r5", CubeListBuilder.create().texOffs(105, 60).mirror().addBox(-4.6151F, -7.1818F, -1.25F, 7.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-11.4951F, 3.1094F, 0.0F, 0.0F, 0.0F, 0.8727F));

		PartDefinition cube_r41 = middle_left_leg.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(24, 112).mirror().addBox(-3.5049F, -7.4404F, 2.456F, 7.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(96, 7).mirror().addBox(-2.5316F, 6.2591F, 2.0599F, 5.0F, 2.875F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-11.4951F, 3.1094F, 0.0F, 0.0F, -1.5708F, 0.5236F));

		PartDefinition cube_r42 = middle_left_leg.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(90, 55).mirror().addBox(-3.0299F, 3.5075F, -0.9967F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-11.4951F, 3.1094F, 0.0F, -1.0472F, 0.0F, 0.0F));

		PartDefinition cube_r43 = middle_left_leg.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(114, 0).mirror().addBox(6.5701F, 6.0034F, -4.6169F, 4.0F, 2.875F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(114, 0).mirror().addBox(-50.4299F, 6.0034F, -4.6169F, 4.0F, 2.875F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(35.9049F, 3.1094F, 0.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition cube_r44 = middle_left_leg.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(96, 0).mirror().addBox(-3.0067F, 2.8906F, 4.5116F, 6.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(114, 5).mirror().addBox(-3.0067F, 5.3906F, 7.5116F, 6.0F, 1.5F, 1.4375F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-11.4951F, 3.1094F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r45 = middle_left_leg.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(0, 120).mirror().addBox(-2.5316F, 3.9505F, -1.7476F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-11.4951F, 3.1094F, 0.0F, 0.0F, -1.5708F, 1.0472F));

		PartDefinition cube_r46 = middle_left_leg.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(96, 7).mirror().addBox(-3.5299F, 6.0034F, 1.6169F, 5.0F, 2.875F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-11.4951F, 3.1094F, 0.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition cube_r47 = middle_left_leg.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(0, 120).mirror().addBox(-3.4299F, 3.5075F, -2.0034F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-11.4951F, 3.1094F, 0.0F, 1.0472F, 0.0F, 0.0F));

		PartDefinition back_left_leg = Everything.addOrReplaceChild("back_left_leg", CubeListBuilder.create(), PartPose.offset(-15.0F, -10.0F, -11.0F));

		PartDefinition leg1_r2 = back_left_leg.addOrReplaceChild("leg1_r2", CubeListBuilder.create().texOffs(0, 112).mirror().addBox(4.8632F, -4.8606F, -1.8434F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(92, 107).mirror().addBox(-1.372F, -9.1719F, -3.5934F, 4.0F, 6.0625F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(90, 63).mirror().addBox(-3.372F, 2.8906F, 4.4066F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(114, 11).mirror().addBox(-3.372F, 5.3906F, 7.4066F, 5.0F, 1.5F, 1.4375F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(114, 5).mirror().addBox(-3.872F, 5.3906F, -8.0309F, 6.0F, 1.5F, 1.4375F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(90, 71).mirror().addBox(-3.872F, 2.8906F, -6.5934F, 6.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(32, 94).mirror().addBox(-4.372F, -3.1094F, -3.5934F, 7.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.4437F, 3.1094F, -7.4247F, 0.0F, -0.7854F, 0.0F));

		PartDefinition leg2_r6 = back_left_leg.addOrReplaceChild("leg2_r6", CubeListBuilder.create().texOffs(105, 60).mirror().addBox(-4.5297F, -7.2836F, -1.3434F, 7.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.4437F, 3.1094F, -7.4247F, -0.6537F, -0.4718F, 1.0353F));

		PartDefinition cube_r48 = back_left_leg.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(24, 112).mirror().addBox(-3.525F, -3.5F, -1.5F, 7.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-9.6667F, -2.2811F, -8.0726F, -2.618F, -0.7854F, -3.1416F));

		PartDefinition cube_r49 = back_left_leg.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(90, 55).mirror().addBox(-2.897F, 3.8596F, -1.8F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.4437F, 3.1094F, -7.4247F, 1.0472F, -0.7854F, 0.0F));

		PartDefinition cube_r50 = back_left_leg.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(114, 0).mirror().addBox(-2.897F, 6.2067F, 1.969F, 4.0F, 2.875F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.4437F, 3.1094F, -7.4247F, 0.5236F, -0.7854F, 0.0F));

		PartDefinition cube_r51 = back_left_leg.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(90, 71).mirror().addBox(-3.4133F, 2.8906F, -7.3787F, 6.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(114, 5).mirror().addBox(-3.4133F, 5.3906F, -8.8162F, 6.0F, 1.5F, 1.4375F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.4437F, 3.1094F, -7.4247F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r52 = back_left_leg.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(0, 120).mirror().addBox(-2.9383F, 3.8354F, -1.186F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.4437F, 3.1094F, -7.4247F, -1.0472F, 0.7854F, 0.0F));

		PartDefinition cube_r53 = back_left_leg.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(96, 7).mirror().addBox(-2.9383F, 6.1927F, -4.9448F, 5.0F, 2.875F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.4437F, 3.1094F, -7.4247F, -0.5236F, 0.7854F, 0.0F));

		PartDefinition cube_r54 = back_left_leg.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(96, 7).mirror().addBox(-3.297F, 5.8F, -4.2647F, 5.0F, 2.875F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.4437F, 3.1094F, -7.4247F, -0.5236F, -0.7854F, 0.0F));

		PartDefinition cube_r55 = back_left_leg.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(0, 120).mirror().addBox(-3.297F, 3.1553F, -0.7933F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.4437F, 3.1094F, -7.4247F, -1.0472F, -0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}


	@Override
	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch)
	{
		this.head.yRot = pNetHeadYaw * ((float) Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float) Math.PI / 180F);

		//reset to zero, the models have default position baked in
		this.backRightLeg.zRot = 0;
		this.backLeftLeg.zRot = 0;
		this.middleRightLeg.zRot = 0;
		this.middleLeftLeg.zRot = 0;
		this.frontRightLeg.zRot = 0;
		this.frontLeftLeg.zRot = 0;
		this.rightForeleg.zRot = 0;
		this.leftForeleg.zRot = 0;

		//reset to zero, the models have default position baked in
		this.backRightLeg.yRot = 0;
		this.backLeftLeg.yRot = 0;
		this.middleRightLeg.yRot = 0;
		this.middleLeftLeg.yRot = 0;
		this.frontRightLeg.yRot = 0;
		this.frontLeftLeg.yRot = 0;
		this.rightForeleg.yRot = 0;
		this.leftForeleg.yRot = 0;

		float backLegRotationY = (-(Mth.cos(pLimbSwing * 0.6662F) * 0.4F) * pLimbSwingAmount);
		float middleLegRotationY = (-(Mth.cos(pLimbSwing * 0.6662F + (float) Math.PI) * 0.4F) * pLimbSwingAmount);
		float frontLegRotationY = (-(Mth.cos(pLimbSwing * 0.6662F + ((float) Math.PI / 2F)) * 0.4F) * pLimbSwingAmount);
		float forelegRotationY = (-(Mth.cos(pLimbSwing * 0.6662F + ((float) Math.PI * 1.5F)) * 0.4F) * pLimbSwingAmount);

		float backLegRotationZ = (Mth.sin(pLimbSwing * 0.6662F + 0.0F) * 0.4F) * pLimbSwingAmount;
		float middleLegRotationZ = (Mth.sin(pLimbSwing * 0.6662F + (float) Math.PI) * 0.4F) * pLimbSwingAmount;
		float frontLegRotationZ = (Mth.sin(pLimbSwing * 0.6662F + ((float) Math.PI / 2F)) * 0.4F) * pLimbSwingAmount;
		float forelegRotationZ = (Mth.sin(pLimbSwing * 0.6662F + ((float) Math.PI * 1.5F)) * 0.4F) * pLimbSwingAmount;

		// the right side needs to have a negative value to go forwards
		this.frontRightLeg.yRot = -frontLegRotationY;
		this.middleRightLeg.yRot = -middleLegRotationY;
		this.backRightLeg.yRot = -backLegRotationY;

		// the left side needs to have a positive value to go forwards
		this.frontLeftLeg.yRot = frontLegRotationY;
		this.middleLeftLeg.yRot = middleLegRotationY;
		this.backLeftLeg.yRot = backLegRotationY;

		//We clamp these values so that it looks like the foot is planted on the ground as it moves forward
		{
			//the left side needs to be positive to have the leg go up
			this.frontLeftLeg.zRot = Mth.clamp(frontLegRotationZ, 0, 25);
			this.middleLeftLeg.zRot = Mth.clamp(middleLegRotationZ, 0, 25);
			this.backLeftLeg.zRot = Mth.clamp(backLegRotationZ, 0, 25);

			//the right side needs to be negative to have the leg go up.
			this.frontRightLeg.zRot = Mth.clamp(-frontLegRotationZ, -25, 0);
			this.middleRightLeg.zRot = Mth.clamp(-middleLegRotationZ, -25, 0);
			this.backRightLeg.zRot = Mth.clamp(-backLegRotationZ, -25, 0);
		}


		//todo decide what to do about the forelegs
		this.rightForeleg.zRot += -forelegRotationZ;
		this.leftForeleg.zRot += forelegRotationZ;

		this.rightForeleg.yRot += forelegRotationY;
		this.leftForeleg.yRot += -forelegRotationY;
	}
}