/*
 * File updated ~ 20 - 11 - 2024 ~ Leaf
 */

package leaf.cosmere.surgebinding.client;

import leaf.cosmere.api.CosmereAPI;
import leaf.cosmere.surgebinding.client.render.SurgebindingLayerDefinitions;
import leaf.cosmere.surgebinding.client.render.SurgebindingRenderers;
import leaf.cosmere.surgebinding.client.render.model.ChullModel;
import leaf.cosmere.surgebinding.client.render.model.CrypticModel;
import leaf.cosmere.surgebinding.client.render.model.ShardplateModel;
import leaf.cosmere.surgebinding.common.Surgebinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Surgebinding.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SurgebindingModClientEvents
{

	@SubscribeEvent
	public static void registerGuiOverlays(RegisterGuiOverlaysEvent guiOverlaysEvent)
	{
		guiOverlaysEvent.registerAbove(
				VanillaGuiOverlay.EXPERIENCE_BAR.id(),
				"hud",
				(forgeGui, gui, partialTick, width, height) -> HUDHandler.onDrawScreenPost(gui)
		);
	}

	@SubscribeEvent
	public static void registerLayers(final EntityRenderersEvent.RegisterLayerDefinitions evt)
	{
		//shardplate
		evt.registerLayerDefinition(SurgebindingLayerDefinitions.SHARDPLATE, ShardplateModel::createBodyLayer);
		evt.registerLayerDefinition(SurgebindingLayerDefinitions.CHULL, ChullModel::createBodyLayer);
		evt.registerLayerDefinition(SurgebindingLayerDefinitions.CRYPTIC, CrypticModel::createBodyLayer);
	}


	@SubscribeEvent
	public static void init(final FMLClientSetupEvent event)
	{
		SurgebindingRenderers.register();

		CosmereAPI.logger.info("Surgebinding client setup complete!");
	}

}
