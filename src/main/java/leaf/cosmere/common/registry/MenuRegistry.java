package leaf.cosmere.common.registry;

import leaf.cosmere.client.gui.MetalworkingTableMenu;
import leaf.cosmere.common.Cosmere;
import leaf.cosmere.common.recipe.MetalworkingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuRegistry
{

	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Cosmere.MODID);

	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Cosmere.MODID);

	public static final RegistryObject<MenuType<MetalworkingTableMenu>> METALWORKING_TABLE_MENU =
			MENUS.register("metalworking_table",
					() -> IForgeMenuType.create((windowId, inv, buf) ->
							new MetalworkingTableMenu(windowId, inv, BlockPos.of(buf.readLong()))));

	// Only shapeless 4x4
	public static final RegistryObject<RecipeSerializer<MetalworkingRecipe>> METALWORKING_SERIALIZER =
			RECIPE_SERIALIZERS.register("metalworking", MetalworkingRecipe.Serializer::new);

}
