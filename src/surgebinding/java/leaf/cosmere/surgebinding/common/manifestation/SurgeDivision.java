/*
 * File updated ~ 8 - 10 - 2022 ~ Leaf
 */

package leaf.cosmere.surgebinding.common.manifestation;

import leaf.cosmere.api.Roshar;
import leaf.cosmere.api.spiritweb.ISpiritweb;
import leaf.cosmere.client.Keybindings;
import leaf.cosmere.surgebinding.common.capabilities.SurgebindingSpiritwebSubmodule;
import leaf.cosmere.surgebinding.common.config.SurgebindingConfigs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SurgeDivision extends SurgebindingManifestation
{
	public SurgeDivision(Roshar.Surges surge)
	{
		super(surge);
	}

	//power over destruction and decay


	@Override
	public boolean tick(ISpiritweb data)
	{
		if(isActive(data)
				&& isActiveTick(data)
				&& Keybindings.MANIFESTATION_USE_ACTIVE.isDown()
				&& data.getSelectedManifestation() instanceof SurgeDivision)
		{
			return performEffectServer(data);
		}
		return false;
	}

	public boolean performEffectServer(ISpiritweb data)
	{
		if(getMode(data) == 0)
		{
			return false;
		}
		SurgebindingSpiritwebSubmodule ssm = SurgebindingSpiritwebSubmodule.getSubmodule(data);
		int stormlight = ssm.getStormlight();
		float spendFactor = 0;

		for(int i = getMode(data); i >= 0; i--)
		{
			if(stormlight >= 3000 * i)
			{
				spendFactor = i;
				break;
			}
			if (i == 0)
			{
				return false;
			}
		}
		LivingEntity entity = data.getLiving();
		Vec3 loc = entity.position();
		double x = loc.x;
		double y = loc.y;
		double z = loc.z;
		float radius = Math.min(1.5F * spendFactor, 10);

		float damage = 3 * spendFactor;
		boolean causesFire = getMode(data) > 2;
		Level level = entity.level();

		Explosion explosion = new Explosion(level, entity, x, y, z, radius, causesFire, Explosion.BlockInteraction.DESTROY_WITH_DECAY);
		explosion.explode();
		explosion.finalizeExplosion(true);

		AABB area = new AABB(x - radius, y - radius, z - radius,
				x + radius, y + radius, z + radius);
		List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area);

		for (LivingEntity target : entities) {
			if (target == entity) continue; // Skip activator

			double distanceSq = target.distanceToSqr(x, y, z);
			if (distanceSq <= radius * radius) {
				float damageScale = 1.0F - (float)Math.sqrt(distanceSq) / radius;
				target.hurt(level.damageSources().explosion(explosion),
						damage * damageScale);

				// Optional: small chance to ignite entity if explosion is flaming
				if (causesFire && level.random.nextFloat() < 0.5F) {
					target.setSecondsOnFire(5);
				}
			}
		}
		ssm.adjustStormlight((int) (-3000 * spendFactor), true);
		return true;
	}
}
