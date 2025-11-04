/*
 * File updated ~ 27 - 2 - 2023 ~ Leaf
 */

package leaf.cosmere.common.commands.subcommands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import leaf.cosmere.api.Metals.MetalType;
import leaf.cosmere.common.commands.arguments.MetalArgumentType;
import leaf.cosmere.common.items.InvestedMetalNuggetItem;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;

import static leaf.cosmere.common.registry.ItemsRegistry.INVESTED_METAL_NUGGET;

public class MetalAlloyCommand extends ModCommand
{

	@Override
	public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
	{
		return SINGLE_SUCCESS;
	}



	private static int give(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
	{
		ItemStack itemStack = new ItemStack(INVESTED_METAL_NUGGET.get());
		HashSet<MetalType> metalTypes = new HashSet<>();

		int sizeArg = context.getArgument("size", Integer.class);

		for(int i = 0; i < 3; i++)
		{
			try {
				MetalType metalTypeArg = context.getArgument("metal" + i, MetalType.class);

				metalTypes.add(metalTypeArg);
			} catch (IllegalArgumentException e) {
				break;
			}
		}

		if(InvestedMetalNuggetItem.writeMetalAlloyNbtData(itemStack.getOrCreateTag(), metalTypes, sizeArg))
		{
			itemStack.setCount(1);
			context.getSource().getPlayerOrException().getInventory().add(itemStack);
		} else
		{
			String errorMsg = "Invalid Metals / Ratios!";
			throw new CommandSyntaxException(new SimpleCommandExceptionType(Component.literal(errorMsg)), Component.literal(errorMsg));
		}

		return SINGLE_SUCCESS;
	}

	public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher)
	{
		 return Commands.literal("metallic_alloy").requires(context -> context.hasPermission(2))
				 .then(Commands.argument("size", IntegerArgumentType.integer(1, 16))
						 .then(Commands.argument("metal0", MetalArgumentType.createArgument())
						 .executes(MetalAlloyCommand::give)
								 .then(Commands.argument("metal1", MetalArgumentType.createArgument())
							     .executes(MetalAlloyCommand::give))));
	}
}