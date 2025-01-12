/*
 * File updated ~ 12 - 1 - 2025 ~ Leaf
 */

package leaf.cosmere.surgebinding.common.capabilities;

import leaf.cosmere.api.*;
import leaf.cosmere.api.helpers.EffectsHelper;
import leaf.cosmere.api.spiritweb.ISpiritweb;
import leaf.cosmere.common.cap.entity.SpiritwebCapability;
import leaf.cosmere.common.util.TaskQueueManager;
import leaf.cosmere.surgebinding.common.Surgebinding;
import leaf.cosmere.surgebinding.common.config.SurgebindingConfigs;
import leaf.cosmere.surgebinding.common.config.SurgebindingServerConfig;
import leaf.cosmere.surgebinding.common.items.tiers.ShardplateArmorMaterial;
import leaf.cosmere.surgebinding.common.manifestation.SurgeProgression;
import leaf.cosmere.surgebinding.common.registries.SurgebindingDimensions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ServerChatEvent;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class SurgebindingSpiritwebSubmodule implements ISpiritwebSubmodule
{
	//todo move
	private static ResourceLocation SWEAR_IDEAL = new ResourceLocation(Surgebinding.MODID, "swear_ideal");

	//stormlight stored

	private int stormlightStored = 0;

	//a little ew, I'd rather this in an enum utils, but it's the only place that needs it
	public static final ShardplateArmorMaterial[] ARMOR_MATERIALS = ShardplateArmorMaterial.values();
	private String order = "";
	private int ideal = 0;


	@Override
	public void tickServer(ISpiritweb spiritweb)
	{
		final LivingEntity livingEntity = spiritweb.getLiving();
		final boolean surgebindingActiveTick = (livingEntity.tickCount + Manifestations.ManifestationTypes.SURGEBINDING.getID()) % 20 == 0;

		//boolean anySurges = SurgebindingManifestations.SURGEBINDING_POWERS.values().stream().anyMatch((manifestation -> spiritweb.hasManifestation(manifestation.getManifestation())));
		boolean isOathed = !order.isEmpty();

		//tick stormlight
		if (isOathed)
		{
			if (livingEntity.level().dimension().equals(SurgebindingDimensions.ROSHAR_DIM_KEY)
					&& livingEntity.level().isThundering()
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
		final CompoundTag spiritwebCompoundTag = spiritweb.getCompoundTag();
		stormlightStored = spiritwebCompoundTag.getInt("stored_stormlight");
		order = spiritwebCompoundTag.getString("order");
		ideal = spiritwebCompoundTag.getInt("ideal");
	}

	@Override
	public void serialize(ISpiritweb spiritweb)
	{
		final CompoundTag compoundTag = spiritweb.getCompoundTag();

		compoundTag.putInt("stored_stormlight", stormlightStored);
		compoundTag.putString("order", order);
		compoundTag.putInt("ideal", ideal);
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

	public void onChatMessageReceived(@NotNull SpiritwebCapability spiritweb, ServerChatEvent event)
	{
		//players can swear an ideal by typing a specific message in chat
		//it's more efficient to check if a chat message is relevant before checking
		// if the player meets any requirements needed to do something with those messages

		//todo maybe we should check if the player is in the right dimension first?

		//todo
		{
			//a player can potentially bond _two_ spren, not just a single one
			//this means they can have two sets of surges, and two sets of ideals
			//so maybe it's fine to hard code to only two sets of radiant orders?
			//todo config for allowing a second order
			//tbd on the conditions required before a player can bond a second spren
		}

		String playerMessage = SurgebindingServerConfig.cleanIdeal(event.getRawText());
		int idealToSwear = 0;
		String idealOrder = "";

		//every order's first ideal is the same

		//if it's not the first ideal

		//for each order
		//yes its weird to iterate over gems, but gems enum knows what orders are associated with it
		for (Roshar.Gemstone gemstone : EnumUtils.GEMSTONE_TYPES)
		{
			//for each ideal
			for (int ideal = 1; ideal <= 5; ideal++)
			{
				String thisIdeal = SurgebindingConfigs.SERVER.getIdeal(ideal, gemstone.getID());
				if (thisIdeal.equals(SurgebindingServerConfig.IDEAL_NOT_IMPLEMENTED))
				{
					//skip ideals that are not yet implemented
					continue;
				}

				if (playerMessage.contains(thisIdeal))
				{
					//check if the message matches the ideal
					//if it does, set idealToSwear to the ideal's index
					//also set which order the ideal belongs to
					//break out of the loop
					idealToSwear = ideal;
					if (idealToSwear != 1)
					{
						//only the first ideal is universal,
						idealOrder = gemstone.getAssociatedOrder();
					}
					break;
				}
			}
		}

		if (idealToSwear == 0)
		{
			//not relevant, exit
			return;
		}

		if (this.ideal > 2 && this.order.isEmpty())
		{
			CosmereAPI.logger.error("Player has sworn a higher ideal but has no order set?? How does this happen. Resetting to zero.");
			this.ideal = 0;
		}

		//assuming we have found a message that matches trying to qualify for an ideal
		boolean successfulSwear = false;
		//check the current state of the player's spiritweb
		//swearing the first or second ideal
		if (this.order.isEmpty())
		{

			//we want to know whether they are unoathed, or have sworn an ideal
			//if they have sworn an ideal, we want to know which one
			//ideal has to be zero, check if swearing the first ideal.
			if (idealToSwear == 1 && this.ideal == 0)
			{
				this.ideal++;
				successfulSwear = true;
			}
			else if (idealToSwear == 2 && this.ideal == 1)
			{
				this.ideal++;
				this.order = idealOrder;
				successfulSwear = true;
			}
			// else we

			if (successfulSwear)
			{
				onSuccessfulIdealSworn(spiritweb);
			}

			//no need to continue
			return;
		}

		//else they must be trying to swear a higher ideal
		if (this.order.equals(idealOrder))
		{
			boolean swearingHigherIdeal = idealToSwear > 2;
			//swearing a higher ideal
			if (idealToSwear == this.ideal + 1)
			{
				this.ideal++;
				successfulSwear = true;
			}

			if (successfulSwear)
			{
				onSuccessfulIdealSworn(spiritweb);
			}
			else
			{
				//only the higher ideas tell you if words are accepted?
				//todo translatable
				event.setCanceled(true);
				event.getPlayer().sendSystemMessage(Component.literal("THESE WORDS ARE NOT ACCEPTED."));
			}
		}
	}

	private void onSuccessfulIdealSworn(@NotNull SpiritwebCapability spiritweb)
	{
		//rumble if ideal 2
		if (spiritweb.getLiving() instanceof ServerPlayer player)
		{
			//todo we should check the configs for whether this should be broadcasted to all players, or just the player themselves

			final Runnable work = () ->
			{
				//todo translatable
				if (this.ideal != 1)
				{
					player.sendSystemMessage(Component.literal("THESE WORDS ARE ACCEPTED."));
				}
				//player.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 1000, 0.8F + player.getRandom().nextFloat() * 0.2F);
				player.level().playSound(
						null,//null so it also sends to triggering player
						player.getX(),
						player.getY(),
						player.getZ(),
						SoundEvents.LIGHTNING_BOLT_THUNDER,
						SoundSource.WEATHER,
						10000.0F,
						0.8F + player.getRandom().nextFloat() * 0.2F
				);


				//todo - if the player is trying to swear an ideal, check if they have stormlight nearby to facilitate the process
				{
					//if they do, we should consume the stormlight and give them the appropriate buffs
				}
				{
					//if they don't, we should inform them that they need more stormlight to swear an ideal?
					//or maybe just allow the words to be accepted but don't heal them or give them the buffs
				}

				//todo spawn order glyph at player if higher ideal

			};

			TaskQueueManager.submitDelayedTask(SWEAR_IDEAL, 60, new TaskQueueManager.OneOffTask(work));
		}
	}

}