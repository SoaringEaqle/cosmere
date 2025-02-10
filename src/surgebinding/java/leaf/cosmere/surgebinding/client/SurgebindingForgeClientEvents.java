/*
 * File updated ~ 4 - 2 - 2025 ~ Leaf
 */

package leaf.cosmere.surgebinding.client;

import leaf.cosmere.common.cap.entity.SpiritwebCapability;
import leaf.cosmere.surgebinding.common.Surgebinding;
import leaf.cosmere.surgebinding.common.network.packets.BreatheStormlight;
import leaf.cosmere.surgebinding.common.network.packets.DrawStormlight;
import leaf.cosmere.surgebinding.common.network.packets.SummonShardblade;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Surgebinding.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SurgebindingForgeClientEvents
{
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

			if (isKeyPressed(event, SurgebindingKeybindings.SHARDBLADE))
			{
				Surgebinding.packetHandler().sendToServer(new SummonShardblade());
			}

			if (isKeyPressed(event, SurgebindingKeybindings.BREATHE_STORMLIGHT))
			{
				Surgebinding.packetHandler().sendToServer(new BreatheStormlight());
			}
			if (isKeyPressed(event, SurgebindingKeybindings.DRAW_STORMLIGHT))
			{
				Surgebinding.packetHandler().sendToServer(new DrawStormlight());
			}
		});
	}

	private static boolean isKeyPressed(InputEvent.Key event, KeyMapping keyBinding)
	{
		return event.getKey() == keyBinding.getKey().getValue() && keyBinding.consumeClick();
	}

}
