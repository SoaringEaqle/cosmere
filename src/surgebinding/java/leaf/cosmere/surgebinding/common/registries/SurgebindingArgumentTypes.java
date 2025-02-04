/*
 * File updated ~ 4 - 2 - 2025 ~ Leaf
 */

package leaf.cosmere.surgebinding.common.registries;

import leaf.cosmere.surgebinding.common.Surgebinding;
import leaf.cosmere.surgebinding.common.commands.arguments.RadiantOrderArgumentType;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SurgebindingArgumentTypes
{
	public static final DeferredRegister<ArgumentTypeInfo<?, ?>> ARGUMENT_TYPE_INFOS = DeferredRegister.create(ForgeRegistries.COMMAND_ARGUMENT_TYPES, Surgebinding.MODID);

	public static final RegistryObject<ArgumentTypeInfo<?, ?>> RADIANT_ORDER_ARGUMENT_TYPE = ARGUMENT_TYPE_INFOS.register("radiant_order_argument_type",
			() -> ArgumentTypeInfos.registerByClass(
					RadiantOrderArgumentType.class,
					SingletonArgumentInfo.contextFree(RadiantOrderArgumentType::createArgument)));
}
