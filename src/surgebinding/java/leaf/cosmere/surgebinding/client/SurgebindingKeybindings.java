/*
 * File updated ~ 4 - 2 - 2025 ~ Leaf
 */

package leaf.cosmere.surgebinding.client;

import leaf.cosmere.surgebinding.common.Surgebinding;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.lwjgl.glfw.GLFW;

import static leaf.cosmere.api.Constants.Strings.KEY_BREATHE_STORMLIGHT;
import static leaf.cosmere.api.Constants.Strings.KEY_SHARDBLADE;

// Really only has its own file to more nicely reference keybindings.
// Otherwise, could have lived in mod client events
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Surgebinding.MODID, bus = Bus.MOD)
public class SurgebindingKeybindings
{
	public static KeyMapping SHARDBLADE;
	public static KeyMapping BREATHE_STORMLIGHT;

	@SubscribeEvent
	public static void register(RegisterKeyMappingsEvent event)
	{
		event.register(SHARDBLADE = new KeyMapping(KEY_SHARDBLADE, GLFW.GLFW_KEY_X, "keys.surgebinding.main"));
		event.register(BREATHE_STORMLIGHT = new KeyMapping(KEY_BREATHE_STORMLIGHT, GLFW.GLFW_KEY_Z, "keys.surgebinding.main"));
	}

}
