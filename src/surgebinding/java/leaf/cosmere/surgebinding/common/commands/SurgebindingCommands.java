/*
 * File updated ~ 4 - 2 - 2025 ~ Leaf
 */

package leaf.cosmere.surgebinding.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import leaf.cosmere.common.Cosmere;
import leaf.cosmere.surgebinding.common.commands.subcommands.HeraldCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;


public class SurgebindingCommands
{

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
	{
		dispatcher.register(Commands.literal(Cosmere.MODID)
				.then(HeraldCommand.register(dispatcher))
		);
	}
}
