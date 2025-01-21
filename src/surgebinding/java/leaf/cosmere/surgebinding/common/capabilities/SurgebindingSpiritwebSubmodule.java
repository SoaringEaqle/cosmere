/*
 * File updated ~ 14 - 1 - 2025 ~ Leaf
 */

package leaf.cosmere.surgebinding.common.capabilities;

import leaf.cosmere.api.ISpiritwebSubmodule;
import leaf.cosmere.api.Manifestations;
import leaf.cosmere.api.helpers.EffectsHelper;
import leaf.cosmere.api.spiritweb.ISpiritweb;
import leaf.cosmere.surgebinding.common.capabilities.ideals.IdealsManager;
import leaf.cosmere.surgebinding.common.config.SurgebindingConfigs;
import leaf.cosmere.surgebinding.common.items.tiers.ShardplateArmorMaterial;
import leaf.cosmere.surgebinding.common.manifestation.SurgeProgression;
import leaf.cosmere.surgebinding.common.registries.SurgebindingDimensions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ServerChatEvent;

import java.util.stream.Stream;

public class SurgebindingSpiritwebSubmodule implements ISpiritwebSubmodule
{

	//stormlight stored

	private int stormlightStored = 0;

	//a little ew, I'd rather this in an enum utils, but it's the only place that needs it
	public static final ShardplateArmorMaterial[] ARMOR_MATERIALS = ShardplateArmorMaterial.values();
	IdealsManager idealsManager = new IdealsManager();
	private ISpiritweb spiritweb;


	@Override
	public void tickServer(ISpiritweb spiritweb)
	{
		final LivingEntity livingEntity = spiritweb.getLiving();
		final boolean surgebindingActiveTick = (livingEntity.tickCount + Manifestations.ManifestationTypes.SURGEBINDING.getID()) % 20 == 0;

		//boolean anySurges = SurgebindingManifestations.SURGEBINDING_POWERS.values().stream().anyMatch((manifestation -> spiritweb.hasManifestation(manifestation.getManifestation())));
		boolean isOathed = idealsManager.getOrder() != null;

		//tick stormlight
		if (isOathed)
		{
			if (livingEntity.level().isThundering()
					&& livingEntity.level().dimension().equals(SurgebindingDimensions.ROSHAR_DIM_KEY)
					&& livingEntity.level().isRainingAt(livingEntity.blockPosition()))
			{
				//if player has max of 1000, it will take just under a minute to spend?
				//in the books, it's almost instantly full
				final int maxPlayerStormlight = SurgebindingConfigs.SERVER.PLAYER_MAX_STORMLIGHT.get();
				stormlightStored = maxPlayerStormlight;
			}

			if (stormlightStored > 0 && surgebindingActiveTick)
			{
				//being hurt takes priority
				if (livingEntity.getHealth() < livingEntity.getMaxHealth())
				{
					//todo healing stormlight config
					final int stormlightHealingCostMultiplier = 20;

					if (adjustStormlight(-stormlightHealingCostMultiplier, true))
					{
						//todo stormlight healing better
						SurgeProgression.heal(livingEntity, livingEntity.getHealth() + 1);
					}
				}
				//otherwise conditional effects
				else
				{
					if (livingEntity.getCombatTracker().inCombat)
					{
						//todo combat effect cost
						//todo replace with non-vanilla effects
						livingEntity.addEffect(EffectsHelper.getNewEffect(MobEffects.DAMAGE_BOOST, 0));
						livingEntity.addEffect(EffectsHelper.getNewEffect(MobEffects.MOVEMENT_SPEED, 0));
						livingEntity.addEffect(EffectsHelper.getNewEffect(MobEffects.JUMP, 0));
						adjustStormlight(-30, true);
					}
					else if (livingEntity.isUnderWater())
					{
						//todo replace with non-vanilla effects
						livingEntity.addEffect(EffectsHelper.getNewEffect(MobEffects.WATER_BREATHING, 0));
						//todo waterbreathing stormlight cost
						adjustStormlight(-10, true);
					}
					else
					{
						//todo detect better based on what the player is doing? mining means haste,
						//travelling means movement etc. Not sure if that's really feasible though
						//todo replace with non-vanilla effects
						livingEntity.addEffect(EffectsHelper.getNewEffect(MobEffects.DIG_SPEED, 0));
						livingEntity.addEffect(EffectsHelper.getNewEffect(MobEffects.MOVEMENT_SPEED, 0));
						livingEntity.addEffect(EffectsHelper.getNewEffect(MobEffects.JUMP, 0));
						adjustStormlight(-30, true);
					}
				}

				int drainRate = SurgebindingConfigs.SERVER.STORMLIGHT_DRAIN_RATE.get();
				//todo maybe reducing cost based on how many ideals they have sworn?
				int idealsSworn = 1;

				adjustStormlight(-(drainRate / idealsSworn), true);
			}
		}

		//special effects for wearing shardplate.

		if (surgebindingActiveTick)
		{
			ItemStack helmet = livingEntity.getItemBySlot(EquipmentSlot.HEAD);
			ItemStack breastplate = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
			ItemStack leggings = livingEntity.getItemBySlot(EquipmentSlot.LEGS);
			ItemStack boots = livingEntity.getItemBySlot(EquipmentSlot.FEET);

			//check wearing full suit of armor
			if (Stream.of(helmet, breastplate, leggings, boots).allMatch(armorStack -> !armorStack.isEmpty() && armorStack.getItem() instanceof ArmorItem))
			{
				//check armor matches same material
				for (ShardplateArmorMaterial material : ARMOR_MATERIALS)
				{
					if (Stream.of(helmet, breastplate, leggings, boots).allMatch((armorStack -> ((ArmorItem) armorStack.getItem()).getMaterial() == material)))
					{
						int amplifier = material == ShardplateArmorMaterial.DEADPLATE ? 0 : 1;

						//todo make our own effect for wearing shardplate
						//todo replace with non-vanilla effects
						livingEntity.addEffect(EffectsHelper.getNewEffect(MobEffects.MOVEMENT_SPEED, amplifier));
						livingEntity.addEffect(EffectsHelper.getNewEffect(MobEffects.DIG_SPEED, amplifier));
						livingEntity.addEffect(EffectsHelper.getNewEffect(MobEffects.DAMAGE_BOOST, amplifier));
						livingEntity.addEffect(EffectsHelper.getNewEffect(MobEffects.JUMP, amplifier));

						//todo oathed radiant shardplate stormlight cost
						//todo deadplate stormlight cost
						stormlightStored--;
						break;
					}
				}
			}
		}
	}

	@Override
	public void deserialize(ISpiritweb spiritweb)
	{
		this.spiritweb = spiritweb;
		final CompoundTag spiritwebCompoundTag = spiritweb.getCompoundTag();
		stormlightStored = spiritwebCompoundTag.getInt("stored_stormlight");
		idealsManager.deserialize(spiritweb);
	}

	@Override
	public void serialize(ISpiritweb spiritweb)
	{
		final CompoundTag compoundTag = spiritweb.getCompoundTag();

		compoundTag.putInt("stored_stormlight", stormlightStored);
		idealsManager.serialize(spiritweb);
	}

	public int getStormlight()
	{
		return stormlightStored;
	}

	public boolean adjustStormlight(int amountToAdjust, boolean doAdjust)
	{
		int stormlight = getStormlight();

		final int newSLValue = stormlight + amountToAdjust;
		if (newSLValue >= 0)
		{
			if (doAdjust)
			{
				stormlightStored = newSLValue;
			}

			return true;
		}

		return false;
	}

	public void onChatMessageReceived(ServerChatEvent event)
	{
		idealsManager.onChatMessageReceived(event);
	}
}