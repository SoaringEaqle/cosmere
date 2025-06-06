/*
 * File updated ~ 12 - 11 - 2023 ~ Leaf
 */

package leaf.cosmere.client;

import leaf.cosmere.api.Activator;
import leaf.cosmere.api.manifestation.Manifestation;
import leaf.cosmere.common.Cosmere;
import leaf.cosmere.common.cap.entity.SpiritwebCapability;
import leaf.cosmere.common.fog.FogManager;
import leaf.cosmere.common.network.packets.ChangeManifestationModeMessage;
import leaf.cosmere.common.network.packets.ChangeSelectedManifestationMessage;
import leaf.cosmere.common.network.packets.DeactivateManifestationsMessage;
import leaf.cosmere.common.network.packets.SetSelectedManifestationMessage;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.InputEvent.MouseScrollingEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cosmere.MODID, value = Dist.CLIENT)
public class ClientForgeEvents
{

	@SubscribeEvent
	public static void handleScroll(MouseScrollingEvent event)
	{
		final LocalPlayer player = Minecraft.getInstance().player;
		final ItemStack held = player.getItemInHand(InteractionHand.MAIN_HAND);

		SpiritwebCapability.get(player).ifPresent(spiritweb ->
		{

			if (held.isEmpty() && player.isCrouching() && event.isRightDown())
			{
				final int delta = Mth.clamp((int) Math.round(event.getScrollDelta()), -1, 1);

				Cosmere.packetHandler().sendToServer(new ChangeManifestationModeMessage(spiritweb.getSelectedManifestation(), delta));

				event.setCanceled(true);

			}
		});
	}

	@SubscribeEvent
	public static void onKey(InputEvent.Key event)
	{
		final LocalPlayer player = Minecraft.getInstance().player;

		if (player == null)
		{
			return;
		}

		SpiritwebCapability.get(player).ifPresent(spiritweb ->
		{
			Manifestation selected = spiritweb.getSelectedManifestation();
			if (isKeyPressed(event, Keybindings.MANIFESTATIONS_DEACTIVATE))
			{
				// just deactivate
				Cosmere.packetHandler().sendToServer(new DeactivateManifestationsMessage());
			}

			//check keybinds with modifiers first?
			if (isKeyPressed(event, Keybindings.MANIFESTATION_PREVIOUS))
			{
				Cosmere.packetHandler().sendToServer(new ChangeSelectedManifestationMessage(-1));
			}
			else if (isKeyPressed(event, Keybindings.MANIFESTATION_NEXT))
			{
				Cosmere.packetHandler().sendToServer(new ChangeSelectedManifestationMessage(1));
			}

			final boolean modeIncreasePressed = isKeyPressed(event, Keybindings.MANIFESTATION_MODE_INCREASE);
			final boolean modeDecreasedPressed = isKeyPressed(event, Keybindings.MANIFESTATION_MODE_DECREASE);

			if (modeIncreasePressed || modeDecreasedPressed)
			{
				int modifier;
				if (Screen.hasShiftDown())
				{
					modifier = 5;
				}
				else if (Screen.hasControlDown())
				{
					modifier = 10;
				}
				else
				{
					modifier = 1;
				}
				Cosmere.packetHandler().sendToServer(new ChangeManifestationModeMessage(selected, modeIncreasePressed ? modifier : -modifier));
			}

			for (Activator activator : Keybindings.activators)
			{
				if (isKeyPressed(event, activator.getKeyMapping()))
				{
					Manifestation manifestation = activator.getManifestation();
					Cosmere.packetHandler().sendToServer(new SetSelectedManifestationMessage(manifestation));
					selected = manifestation;

					int modifier = -selected.getMode(spiritweb);

					if (!selected.isActive(spiritweb))
					{
						if (activator.getCategory().equals("feruchemy"))
						{
							if (Screen.hasShiftDown() && Screen.hasControlDown())
                            {
                                modifier -= 5;
                            }
                            else if (Screen.hasControlDown())
                            {
                                modifier -= 1;
                            }
                            else if (Screen.hasShiftDown())
                            {
                                modifier += 5;
                            }
                            else {
                                modifier += 1;
                            }
                            Cosmere.packetHandler().sendToServer(new ChangeManifestationModeMessage(selected,modifier));
                        }
						else
						{
                            if (Screen.hasShiftDown() && Screen.hasControlDown())
                            {
                                modifier -= 2;
                            }
                            else if (Screen.hasControlDown())
                            {
                                modifier -= 1;
                            }
                            else if (Screen.hasShiftDown())
                            {
                                modifier += 2;
                            }
                            else
                            {
                                modifier += 1;
                            }
                            Cosmere.packetHandler().sendToServer(new ChangeManifestationModeMessage(selected,modifier));
                        }
					}
					else
					{
                        Cosmere.packetHandler().sendToServer(new ChangeManifestationModeMessage(selected,modifier));
                    }
                }
			}
		});
	}

	private static boolean isKeyPressed(InputEvent.Key event, KeyMapping keyBinding)
	{
		return event.getKey() == keyBinding.getKey().getValue() && keyBinding.consumeClick();
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onRenderLevelLastEvent(final RenderLevelStageEvent event)
	{
		if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES)
		{
			return;
		}

		Minecraft mc = Minecraft.getInstance();
		ProfilerFiller profiler = mc.getProfiler();
		LocalPlayer playerEntity = mc.player;
		{
			profiler.push("cosmere-world-effects");
			{
				SpiritwebCapability.get(playerEntity).ifPresent(spiritweb ->
				{
					spiritweb.renderWorldEffects(event);
				});
			}
			profiler.pop();
		}

	}

	@SubscribeEvent
	public static void onClientPlayerClone(ClientPlayerNetworkEvent.Clone event)
	{
		FogManager.resetFog();
	}
}
