/*
 * File updated ~ 8 - 10 - 2022 ~ Leaf
 */

package leaf.cosmere.allomancy.client;

import leaf.cosmere.allomancy.common.Allomancy;
import leaf.cosmere.allomancy.common.manifestation.AllomancyManifestation;
import leaf.cosmere.api.Metals;
import leaf.cosmere.client.Keybindings;
import leaf.cosmere.api.Activator;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.lwjgl.glfw.GLFW;
import leaf.cosmere.api.EnumUtils;
import java.util.function.Function;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static leaf.cosmere.allomancy.common.registries.AllomancyManifestations.ALLOMANCY_POWERS;
import static leaf.cosmere.api.Constants.Strings.*;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Allomancy.MODID, bus = Bus.MOD)
public class AllomancyKeybindings
{
	public static KeyMapping ALLOMANCY_STEEL_PUSH;
	public static KeyMapping ALLOMANCY_IRON_PULL;
	public static KeyMapping ALLOMANCY_SOOTHE;
	public static KeyMapping ALLOMANCY_RIOT;

	//Activation keybindings;
	/*
	public static KeyMapping ALLOMANCY_IRON;
	public static KeyMapping ALLOMANCY_STEEL;
	public static KeyMapping ALLOMANCY_TIN;
	public static KeyMapping ALLOMANCY_PEWTER;
	public static KeyMapping ALLOMANCY_ZINC;
	public static KeyMapping ALLOMANCY_BRASS;
	public static KeyMapping ALLOMANCY_COPPER;
	public static KeyMapping ALLOMANCY_BRONZE;
	public static KeyMapping ALLOMANCY_CADMIUM;
	public static KeyMapping ALLOMANCY_BENDALLOY;
	public static KeyMapping ALLOMANCY_GOLD;
	public static KeyMapping ALLOMANCY_ELECTRUM;
	public static KeyMapping ALLOMANCY_CHROMIUM;
	public static KeyMapping ALLOMANCY_NICROSIL;
	public static KeyMapping ALLOMANCY_ALUMINUM;
	public static KeyMapping ALLOMANCY_DURALUMIN;
	public static KeyMapping ALLOMANCY_ATIUM;
	public static KeyMapping ALLOMANCY_MALATIUM;
*/
	public static final Map<Metals.MetalType,KeyMapping> ALLOMANCY_POWER =
			Arrays.stream(EnumUtils.METAL_TYPES)
				.filter(Metals.MetalType::hasAssociatedManifestation)
					.collect(Collectors.toMap(
						Function.identity(),
						metalType ->
							new KeyMapping(KEY_ALLOMANCY + metalType.getName(),GLFW.GLFW_KEY_UNKNOWN,KEYS_CATEGORY)
					));

	@SubscribeEvent
	public static void register(RegisterKeyMappingsEvent event)
	{
		event.register(ALLOMANCY_STEEL_PUSH = new KeyMapping(KEY_ALLOMANCY_STEEL_PUSH, GLFW.GLFW_KEY_TAB, KEYS_CATEGORY));
		event.register(ALLOMANCY_IRON_PULL = new KeyMapping(KEY_ALLOMANCY_IRON_PULL, GLFW.GLFW_KEY_R, KEYS_CATEGORY));
		event.register(ALLOMANCY_SOOTHE = new KeyMapping(KEY_ALLOMANCY_SOOTHE, GLFW.GLFW_KEY_UNKNOWN, KEYS_CATEGORY));
		event.register(ALLOMANCY_RIOT = new KeyMapping(KEY_ALLOMANCY_RIOT, GLFW.GLFW_KEY_UNKNOWN, KEYS_CATEGORY));

		// new keybinding registry
		/*
		event.register(ALLOMANCY_STEEL = new KeyMapping(KEY_ALLOMANCY_STEEL,GLFW.GLFW_KEY_UNKNOWN, KEYS_CATEGORY));
		event.register(ALLOMANCY_TIN = new KeyMapping(KEY_ALLOMANCY_TIN,GLFW.GLFW_KEY_UNKNOWN, KEYS_CATEGORY));
		event.register(ALLOMANCY_PEWTER = new KeyMapping(KEY_ALLOMANCY_PEWTER,GLFW.GLFW_KEY_UNKNOWN, KEYS_CATEGORY));
		event.register(ALLOMANCY_ZINC = new KeyMapping(KEY_ALLOMANCY_ZINC,GLFW.GLFW_KEY_UNKNOWN, KEYS_CATEGORY));
		event.register(ALLOMANCY_BRASS = new KeyMapping(KEY_ALLOMANCY_BRASS,GLFW.GLFW_KEY_UNKNOWN, KEYS_CATEGORY));
		event.register(ALLOMANCY_COPPER = new KeyMapping(KEY_ALLOMANCY_COPPER,GLFW.GLFW_KEY_UNKNOWN, KEYS_CATEGORY));
		event.register(ALLOMANCY_BRONZE = new KeyMapping(KEY_ALLOMANCY_BRONZE,GLFW.GLFW_KEY_UNKNOWN, KEYS_CATEGORY));
		event.register(ALLOMANCY_CADMIUM = new KeyMapping(KEY_ALLOMANCY_CADMIUM,GLFW.GLFW_KEY_UNKNOWN, KEYS_CATEGORY));
		event.register(ALLOMANCY_BENDALLOY = new KeyMapping(KEY_ALLOMANCY_BENDALLOY,GLFW.GLFW_KEY_UNKNOWN, KEYS_CATEGORY));
		event.register(ALLOMANCY_GOLD = new KeyMapping(KEY_ALLOMANCY_GOLD,GLFW.GLFW_KEY_UNKNOWN, KEYS_CATEGORY));
		event.register(ALLOMANCY_ELECTRUM = new KeyMapping(KEY_ALLOMANCY_ELECTRUM,GLFW.GLFW_KEY_UNKNOWN, KEYS_CATEGORY));
		event.register(ALLOMANCY_ALUMINUM = new KeyMapping(KEY_ALLOMANCY_ALUMINUM,GLFW.GLFW_KEY_UNKNOWN, KEYS_CATEGORY));
		event.register(ALLOMANCY_DURALUMIN = new KeyMapping(KEY_ALLOMANCY_DURRALUMIN,GLFW.GLFW_KEY_UNKNOWN, KEYS_CATEGORY));
		event.register(ALLOMANCY_CHROMIUM = new KeyMapping(KEY_ALLOMANCY_CHROMIUM,GLFW.GLFW_KEY_UNKNOWN, KEYS_CATEGORY));
		event.register(ALLOMANCY_NICROSIL = new KeyMapping(KEY_ALLOMANCY_NICROSIL,GLFW.GLFW_KEY_UNKNOWN, KEYS_CATEGORY));
		event.register(ALLOMANCY_ATIUM = new KeyMapping(KEY_ALLOMANCY_ATIUM,GLFW.GLFW_KEY_UNKNOWN, KEYS_CATEGORY));
		event.register(ALLOMANCY_MALATIUM = new KeyMapping(KEY_ALLOMANCY_MALATIUM,GLFW.GLFW_KEY_UNKNOWN, KEYS_CATEGORY));
*/

		for (Metals.MetalType metalType: ALLOMANCY_POWER.keySet()){
			KeyMapping key = ALLOMANCY_POWER.get(metalType);
			AllomancyManifestation manifest = ALLOMANCY_POWERS.get(metalType).getManifestation();
			event.register(key);
			Activator entry = new Activator(key, manifest);
			entry.setCategory("allomancy");
			Keybindings.activators.add(entry);
		}
	}

}
