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
import leaf.cosmere.api.IHasAlloy;
import leaf.cosmere.api.IHasSize;
import leaf.cosmere.api.Metals.MetalType;
import leaf.cosmere.common.commands.arguments.MetalArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;

import static leaf.cosmere.common.registry.ItemsRegistry.GOD_METAL_NUGGETS;

public class MetalNuggetCommand extends ModCommand
{

	@Override
	public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
	{
		return SINGLE_SUCCESS;
	}



	private static int give(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
	{
		MetalType godMetal = null;
		HashSet<MetalType> metalTypes = new HashSet<>();

		int sizeArg = context.getArgument("size", Integer.class);

		for(int i = 0; i < 2; i++)
		{
			try {
				MetalType metalTypeArg = context.getArgument("metal" + i, MetalType.class);
				metalTypes.add(metalTypeArg);
				if (metalTypeArg.isGodMetal())
				{
					godMetal = metalTypeArg;
				}
			} catch (IllegalArgumentException e) {
				break;
			}
		}

		if(godMetal != null)
		{
			Item item = GOD_METAL_NUGGETS.get(godMetal).get();
			ItemStack itemStack = new ItemStack(item, 1);

			if (item instanceof IHasSize sizeItem)
			{
				if(!sizeItem.writeMetalAlloySizeNbtData(itemStack, sizeArg))
				{
					String errorMsg = "Invalid Metals";
					throw new CommandSyntaxException(new SimpleCommandExceptionType(Component.literal(errorMsg)), Component.literal(errorMsg));
				}
			}

			if(item instanceof IHasAlloy alloyItem)
			{
				if(!alloyItem.writeMetalAlloyNbtData(itemStack, metalTypes))
				{
					String errorMsg = "Invalid Size";
					throw new CommandSyntaxException(new SimpleCommandExceptionType(Component.literal(errorMsg)), Component.literal(errorMsg));
				}
			}
			context.getSource().getPlayerOrException().getInventory().add(itemStack);
		}

		return SINGLE_SUCCESS;
	}

	public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher)
	{
		 return Commands.literal("metal_nugget").requires(context -> context.hasPermission(2))
				 .then(Commands.argument("size", IntegerArgumentType.integer(1, 16))
						 .then(Commands.argument("metal0", MetalArgumentType.createArgument())
						 .executes(MetalNuggetCommand::give)
								 .then(Commands.argument("metal1", MetalArgumentType.createArgument())
							     .executes(MetalNuggetCommand::give))));
	}
}