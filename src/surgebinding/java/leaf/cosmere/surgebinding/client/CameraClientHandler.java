package leaf.cosmere.surgebinding.client;

import leaf.cosmere.common.registry.DimensionRegistry;
import leaf.cosmere.surgebinding.common.entity.PlayerTransportationStandin;
import leaf.cosmere.surgebinding.common.registries.SurgebindingEntityTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.core.jmx.Server;

public class CameraClientHandler
{
	private static PlayerTransportationStandin fakeCam;
	private static ClientLevel camWorld; // the client-side world we spawn camera into
	private static final double TELEPORT_THRESHOLD = 3.0;// snap threshold

	/** Activate the fake camera. Spawns it client-side in the Overworld and switches camera. */
	public static void activate() {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player == null || fakeCam != null) return;

		// We want the client to render Overworld - get the client Overworld world
		ResourceKey<Level> resourcekey = mc.player.level().dimension() == DimensionRegistry.SHADESMAR_DIM_KEY ? Level.OVERWORLD : DimensionRegistry.SHADESMAR_DIM_KEY;// client doesn't have that world


		EntityType<PlayerTransportationStandin> type = (EntityType<PlayerTransportationStandin>) SurgebindingEntityTypes.PLAYER_STANDIN.get();
		fakeCam = new PlayerTransportationStandin(type, camWorld);

		LocalPlayer player = mc.player;
		fakeCam.setPos(player.getX(), player.getY(), player.getZ());
		fakeCam.setYRot(player.getYRot());
		fakeCam.setXRot(player.getXRot());

		// add to client world and switch camera
		camWorld.addEntity(-1, fakeCam); // -1 id for client-only entity; addEntity expects id and entity
		mc.setCameraEntity(fakeCam);
	}

	/** Deactivate the fake camera and return camera control to the player. */
	public static void deactivate() {
		Minecraft mc = Minecraft.getInstance();
		if (fakeCam == null) return;

		// restore camera
		if (mc.player != null) mc.setCameraEntity(mc.player);

		// remove fake entity client-side
		if (camWorld != null && fakeCam != null) {
			// remove safely: mark removed
			fakeCam.remove(net.minecraft.world.entity.Entity.RemovalReason.DISCARDED);
		}

		fakeCam = null;
		camWorld = null;
	}

	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event) {
		if (event.phase != TickEvent.Phase.END) return;

		Minecraft mc = Minecraft.getInstance();
		if (mc.player == null) return;
		if (fakeCam == null) return;

		LocalPlayer player = mc.player;

		// Mirror rotation: make camera look where the player looks
		fakeCam.setYRot(player.getYRot());
		fakeCam.setXRot(player.getXRot());

		// Mirror movement: copy player's deltaMovement and apply on the fake cam
		Vec3 motion = player.getDeltaMovement();
		fakeCam.applyClientMotion(motion);

		// Teleport-correction: if server corrected the player's position, snap the camera so it stays close
		double dx = player.getX() - fakeCam.getX();
		double dy = player.getY() - fakeCam.getY();
		double dz = player.getZ() - fakeCam.getZ();
		if (dx*dx + dy*dy + dz*dz > TELEPORT_THRESHOLD*TELEPORT_THRESHOLD) {
			fakeCam.setPos(player.getX(), player.getY(), player.getZ());
			fakeCam.setDeltaMovement(Vec3.ZERO);
		}
	}

	// Optional: you can also listen for input events if you want to trigger activation/deactivation via a key
	@SubscribeEvent
	public static void onKeyInput(InputEvent.Key event) {
		// Example: (you would use a KeyMapping in a real mod)
	}
}
