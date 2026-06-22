/*
 * File updated ~ 8 - 10 - 2022 ~ Leaf
 */

package leaf.cosmere.surgebinding.common.manifestation;

import leaf.cosmere.api.Roshar;
import leaf.cosmere.api.spiritweb.ISpiritweb;
import leaf.cosmere.client.Keybindings;
import leaf.cosmere.common.registry.DimensionRegistry;
import leaf.cosmere.surgebinding.common.capabilities.SurgebindingSpiritwebSubmodule;
import leaf.cosmere.surgebinding.common.config.SurgebindingConfigs;
import leaf.cosmere.surgebinding.common.entity.PlayerTransportationStandin;
import leaf.cosmere.surgebinding.common.registries.SurgebindingEntityTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;


public class SurgeTransportation extends SurgebindingManifestation
{

	private static final double TELEPORT_THRESHOLD = 3.0; // snap threshold


	public SurgeTransportation(Roshar.Surges surge)
	{
		super(surge);
	}


	//travel between realms or locations


	@Override
	protected void applyEffectTick(ISpiritweb data)
	{
		if(Keybindings.MANIFESTATION_USE_ACTIVE.isDown() && (getMode(data) >= 3) &&
				(SurgebindingSpiritwebSubmodule.getSubmodule(data).getStormlight() > (int) (SurgebindingConfigs.SERVER.PLAYER_MAX_STORMLIGHT.get() * 0.85))
				&& data.getSelectedManifestation() instanceof SurgeTransportation)
		{
			changeDimension(data);
			data.deactivateCurrentManifestation();
		}

	}

	@Override
	public boolean tick(ISpiritweb data)
	{
		LivingEntity entity = data.getLiving();
		SurgebindingSpiritwebSubmodule ssm = SurgebindingSpiritwebSubmodule.getSubmodule(data);

		//if peering across dimensions, then we need to update both models.
		if(ssm.isPeeringToShadesmar())
		{
			PlayerTransportationStandin fakeCam = ssm.getPeeringEnt();
			ssm.getPeeringEnt().setYRot(entity.getYRot());
			fakeCam.setXRot(entity.getXRot());

			// Mirror movement: copy player's deltaMovement and apply on the fake cam
			Vec3 motion = entity.getDeltaMovement();
			fakeCam.applyClientMotion(motion);

			// Teleport-correction: if server corrected the player's position, snap the camera so it stays close
			double dx = entity.getX() - fakeCam.getX();
			double dy = entity.getY() - fakeCam.getY();
			double dz = entity.getZ() - fakeCam.getZ();
			if (dx*dx + dy*dy + dz*dz > TELEPORT_THRESHOLD*TELEPORT_THRESHOLD) {
				fakeCam.setPos(entity.getX(), entity.getY(), entity.getZ());
				fakeCam.setDeltaMovement(Vec3.ZERO);
			}
		}

		//then we can go to check everything.
		return super.tick(data);

	}

	@Override
	public void onModeChange(ISpiritweb data, int lastMode)
	{
		super.onModeChange(data, lastMode);

		//No need to change anything if it's still active
		if(getMode(data) > 0 && lastMode > 0)
		{
			return;
		}
		else if(getMode(data) > 0)
		{
			activatePeer(data);
		}
		else if(getMode(data) == 0)
		{
			//todo: Increase render distance based on level.
			deactivatePeer(data);
		}
	}

	public void changeDimension(ISpiritweb data)
	{
		LivingEntity entity = data.getLiving();
		SurgebindingSpiritwebSubmodule ssm = SurgebindingSpiritwebSubmodule.getSubmodule(data);
		ServerLevel serverlevel = (ServerLevel)entity.level();
		MinecraftServer minecraftserver = serverlevel.getServer();

		if((getMode(data) >= 3) && (ssm.getStormlight() > (int) ((double)SurgebindingConfigs.SERVER.PLAYER_MAX_STORMLIGHT.get() * 0.85)))
		//travel
		// Takes 85% max stormlight
		{
			ResourceKey<Level> resourcekey = entity.level().dimension() == DimensionRegistry.SHADESMAR_DIM_KEY ? Level.OVERWORLD: DimensionRegistry.SHADESMAR_DIM_KEY;
			ServerLevel serverlevel1 = minecraftserver.getLevel(resourcekey);
			BlockPos pPos = entity.mainSupportingBlockPos.get();
			if(entity.canChangeDimensions())
			{
				if (serverlevel1 == null) {
					return;
				}
				BlockPos pos = entity.blockPosition(); // safer than mainSupportingBlockPos
				entity.changeDimension(serverlevel1, new ITeleporter() {
					@Override
					public Entity placeEntity(Entity ent, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
						Entity newEntity = repositionEntity.apply(false);
						newEntity.teleportTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
						return newEntity;
					}
				});
				ssm.adjustStormlight((int) -(SurgebindingConfigs.SERVER.PLAYER_MAX_STORMLIGHT.get() * 0.85), true);
			}
		}
	}

	public void activatePeer(ISpiritweb data)
	{
		ServerPlayer player = data.getLiving() instanceof ServerPlayer ? (ServerPlayer) data.getLiving() : null;
		SurgebindingSpiritwebSubmodule ssm = SurgebindingSpiritwebSubmodule.getSubmodule(data);
		ServerLevel serverlevel = (ServerLevel)player.level();
		MinecraftServer minecraftserver = serverlevel.getServer();
		Minecraft mc = Minecraft.getInstance();


		if( player == null || ssm.isPeeringToShadesmar())
		{
			return;
		}

		// We want the client to render Overworld - get the client Overworld world
		ResourceKey<Level> resourcekey = player.level().dimension() == DimensionRegistry.SHADESMAR_DIM_KEY ? Level.OVERWORLD : DimensionRegistry.SHADESMAR_DIM_KEY;
		ServerLevel serverlevel1 = minecraftserver.getLevel(resourcekey); // client doesn't have that world


		EntityType<PlayerTransportationStandin> type = SurgebindingEntityTypes.PLAYER_STANDIN.get();
		PlayerTransportationStandin standin = new PlayerTransportationStandin(type, serverlevel1);

		ssm.setPeering(standin, serverlevel1);
		standin.setPos(player.getX(), player.getY(), player.getZ());
		standin.setYRot(player.getYRot());
		standin.setXRot(player.getXRot());

		// add to client world and switch camera
		assert serverlevel1 != null;
		serverlevel1.addFreshEntity(standin);
		mc.setCameraEntity(standin);
	}

	/** Deactivate the fake camera and return camera control to the player. */
	public void deactivatePeer(ISpiritweb data) {
		ServerPlayer player = data.getLiving() instanceof ServerPlayer ? (ServerPlayer) data.getLiving() : null;
		SurgebindingSpiritwebSubmodule ssm = SurgebindingSpiritwebSubmodule.getSubmodule(data);
		ServerLevel serverlevel = (ServerLevel)player.level();
		MinecraftServer minecraftserver = serverlevel.getServer();
		Minecraft mc = Minecraft.getInstance();

		if (ssm.getPeeringEnt() == null) return;

		// restore camera
		if (mc.player != null) mc.setCameraEntity(mc.player);

		// remove fake entity client-side
		if (ssm.getPeeringLev() != null && ssm.getPeeringEnt() != null) {
			// remove safely: mark removed
			ssm.getPeeringEnt().remove(net.minecraft.world.entity.Entity.RemovalReason.DISCARDED);
		}

		ssm.stopPeering();
	}

}
