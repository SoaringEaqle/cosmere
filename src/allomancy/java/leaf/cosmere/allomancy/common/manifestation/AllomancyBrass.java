/*
 * File updated ~ 15 - 11 - 2023 ~ Leaf
 */

package leaf.cosmere.allomancy.common.manifestation;

import leaf.cosmere.allomancy.client.AllomancyKeybindings;
import leaf.cosmere.allomancy.common.Allomancy;
import leaf.cosmere.allomancy.common.network.packets.EntityAllomancyActivateMessage;
import leaf.cosmere.allomancy.common.registries.AllomancyEffects;
import leaf.cosmere.api.CosmereAPI;
import leaf.cosmere.api.Metals;
import leaf.cosmere.api.helpers.EffectsHelper;
import leaf.cosmere.api.helpers.EntityHelper;
import leaf.cosmere.api.helpers.PlayerHelper;
import leaf.cosmere.api.spiritweb.ISpiritweb;
import leaf.cosmere.client.Keybindings;
import leaf.cosmere.common.config.CosmereConfigs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.*;

public class AllomancyBrass extends AllomancyManifestation
{
	public static final HashMap<String, BrassThread> playerThreadMap = new HashMap<>();

	public AllomancyBrass(Metals.MetalType metalType)
	{
		super(metalType);
	}

	//Dampens Emotions
	@Override
	public void applyEffectTick(ISpiritweb data)
	{
		if (data.getLiving().level().isClientSide())
		{
			if (isActiveTick(data))
			{
				boolean isSingleTarget = (AllomancyKeybindings.ALLOMANCY_SOOTHE.isDown() || (Keybindings.MANIFESTATION_USE_ACTIVE.isDown()) && data.getSelectedManifestation().equals(getManifestation()));
				int singleTargetEntityId = 0;

				if (isSingleTarget)
				{
					HitResult ray = PlayerHelper.pickWithRange(data.getLiving(), (int) Math.floor(getRange(data) * CosmereConfigs.SERVER_CONFIG.EMOTIONAL_POWERS_SINGLE_TARGET_RANGE_MULTIPLIER.get()));
					if (ray instanceof EntityHitResult entityHitResult)
					{
						singleTargetEntityId = entityHitResult.getEntity().getId();
					}
				}

				Allomancy.packetHandler().sendToServer(new EntityAllomancyActivateMessage(Metals.MetalType.BRASS, isSingleTarget, singleTargetEntityId));
			}
		}
		else
		{
			performEffectServer(data);
		}
	}

	private void performEffectServer(ISpiritweb data)
	{
		int mode = getMode(data);
		String uuid = data.getLiving().getStringUUID();
		AllomancyBrass.BrassThread playerThread = playerThreadMap.get(data.getLiving().getStringUUID());
		boolean isSingleTarget = false;
		if (playerThread != null)
		{
			isSingleTarget = playerThread.isSingleTarget;
		}

		// data processing
		{
			// this is the only way to check if the player is still online, thanks forge devs
			if (data.getLiving().level().getServer().getPlayerList().getPlayer(data.getLiving().getUUID()) == null)
			{
				return;
			}

			//todo, replace x * mode with config based value
			double allomanticStrength = getStrength(data, false);

			if (playerThreadMap.get(uuid) == null)
			{
				playerThreadMap.put(uuid, new BrassThread(data));
			}

			// don 't remove old code comments yet, still testing
			List<LivingEntity> entitiesToAffect = new ArrayList<>();

			if (isSingleTarget)
			{
				if (data.getLiving().level().getEntity(playerThreadMap.get(uuid).singleTargetEntityID) instanceof LivingEntity entity)
				{
					entitiesToAffect.add(entity);
				}
			}
			else
			{
				entitiesToAffect.addAll(playerThreadMap.get(uuid).requestEntityList());
			}

			for (LivingEntity e : entitiesToAffect)
			{
				if (e instanceof Mob mob)
				{
					switch (mode)
					{
						case 2:
							if (allomanticStrength > 15)
								mob.addEffect(EffectsHelper.getNewEffect(
										AllomancyEffects.ALLOMANTIC_BRASS_STUN.getMobEffect(),
										0,      // no amplification system in place
										20 * 5
								));
							mob.setTarget(null);
						case 1:
							mob.setAggressive(false);
						default://stop angry targets from attacking things
							mob.setLastHurtByMob(null);
					}
				}
			}

			if (!isSingleTarget)
			{
				playerThreadMap.get(uuid).releaseEntityList();
			}
		}
	}

	@Override
	public void onModeChange(ISpiritweb data, int lastMode)
	{
		String uuid = data.getLiving().getStringUUID();
		if (getMode(data) <= 0)
		{
			playerThreadMap.remove(uuid);
		}

		super.onModeChange(data, lastMode);
	}

	@Override
	public boolean tick(ISpiritweb data)
	{
		// data thread management
		{
			String uuid = data.getLiving().getStringUUID();
			if (!playerThreadMap.containsKey(uuid))
			{
				playerThreadMap.put(uuid, new BrassThread(data));
			}

			playerThreadMap.entrySet().removeIf(entry -> !entry.getValue().isRunning || AllomancyEntityThread.serverShutdown || entry.getValue() == null);
		}

		return super.tick(data);
	}

	public class BrassThread extends AllomancyEntityThread
	{
		public boolean isSingleTarget = false;
		public int singleTargetEntityID = 0;

		public BrassThread(ISpiritweb data)
		{
			super(data);

			Thread t = new Thread(this, "brass_thread_" + data.getLiving().getDisplayName().getString());
			t.start();
		}

		@Override
		public void run()
		{
			List<LivingEntity> newEntityList;
			while (true)
			{
				int mode = getMode(data);
				if (serverShutdown)
				{
					break;
				}

				if (mode <= 0)
				{
					break;
				}

				try
				{
					if (lock.tryLock())
					{
						int range = getRange(data);

						newEntityList = EntityHelper.getLivingEntitiesInRange(data.getLiving(), range, true);
						setEntityList(newEntityList);
						lock.unlock();
					}

					// sleep thread for 1 tick (50ms)
					Thread.sleep(50);
				}
				catch (Exception e)
				{
					CosmereAPI.logger.debug(Arrays.toString(e.getStackTrace()));

					if (e instanceof ConcurrentModificationException)
					{
						lock.unlock();
					}

					break;
				}
			}
			isRunning = false;
		}
	}
}
