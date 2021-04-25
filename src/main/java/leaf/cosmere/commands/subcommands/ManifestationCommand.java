/*
 * File created ~ 24 - 4 - 2021 ~ Leaf
 */

package leaf.cosmere.commands.subcommands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import leaf.cosmere.cap.entity.SpiritwebCapability;
import leaf.cosmere.commands.arguments.ManifestationsArgumentType;
import leaf.cosmere.constants.Constants;
import leaf.cosmere.helpers.TextHelper;
import leaf.cosmere.manifestation.AManifestation;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import static leaf.cosmere.constants.Constants.Strings.POWERS_FOUND;

public class ManifestationCommand extends ModCommand
{

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException
    {
        return Command.SINGLE_SUCCESS;
    }

    private static int check(CommandContext<CommandSource> context, ServerPlayerEntity player)
    {
        SpiritwebCapability.get(player).ifPresent(spiritweb ->
        {
            CommandSource source = context.getSource();

            TranslationTextComponent powersFound = new TranslationTextComponent(POWERS_FOUND, TextHelper.getPlayerTextObject(context.getSource().getWorld(), player.getUniqueID()));

            final TextComponent leftBracketTextComponent = new StringTextComponent("[");
            final TextComponent rightBracketTextComponent = new StringTextComponent("]");
            final StringTextComponent space = new StringTextComponent(" ");

            //figure out which manifestations a player has
            for (AManifestation manifestation : spiritweb.getAvailableManifestations())
            {
                powersFound.appendSibling(leftBracketTextComponent);
                powersFound.appendSibling(TextHelper.createTextWithTooltip(
                        manifestation.translation(),
                        manifestation.description()));
                powersFound.appendSibling(rightBracketTextComponent);
                powersFound.appendSibling(space);
            }
            source.sendFeedback(powersFound, true);
        });

        return Command.SINGLE_SUCCESS;
    }

    private static int clear(CommandContext<CommandSource> context, ServerPlayerEntity player)
    {
        SpiritwebCapability.get(player).ifPresent(iSpiritweb ->
        {
            CommandSource source = context.getSource();
            iSpiritweb.clearManifestations();
            iSpiritweb.syncToClients(null);
            TextComponent playerTextObject = TextHelper.getPlayerTextObject(context.getSource().getWorld(), player.getUniqueID());
            source.sendFeedback(new TranslationTextComponent(Constants.Strings.POWER_SET_SUCCESS, playerTextObject), false);
        });

        return Command.SINGLE_SUCCESS;
    }

    private static int set(CommandContext<CommandSource> context, ServerPlayerEntity player)
    {
        CommandSource source = context.getSource();
        AManifestation manifestation = context.getArgument("manifestation", AManifestation.class);

        TextComponent playerText = TextHelper.getPlayerTextObject(source.getWorld(), player.getUniqueID());

        TextComponent manifestationText = TextHelper.createTextWithTooltip(manifestation.translation(), manifestation.description());

        if (player == null || manifestation == null)
        {
            source.sendErrorMessage(new TranslationTextComponent(Constants.Strings.POWER_SET_FAIL, playerText, manifestationText));
            return 0;
        }
        SpiritwebCapability.get(player).ifPresent((spiritweb) ->
        {
            spiritweb.clearManifestations();
            spiritweb.giveManifestation(manifestation.getManifestationType(), manifestation.getPowerID());
            spiritweb.syncToClients(null);
            source.sendFeedback(new TranslationTextComponent(Constants.Strings.POWER_SET_SUCCESS, playerText, manifestationText), false);
        });
        return Command.SINGLE_SUCCESS;
    }

    private static int give(CommandContext<CommandSource> context, ServerPlayerEntity player)
    {
        CommandSource source = context.getSource();
        AManifestation manifestation = context.getArgument("manifestation", AManifestation.class);

        TextComponent playerText = TextHelper.getPlayerTextObject(source.getWorld(), player.getUniqueID());

        TextComponent manifestationText = TextHelper.createTextWithTooltip(manifestation.translation(), manifestation.description());

        if (player == null || manifestation == null)
        {
            source.sendErrorMessage(new TranslationTextComponent(Constants.Strings.POWER_SET_FAIL, playerText, manifestationText));
            return 0;
        }
        SpiritwebCapability.get(player).ifPresent((spiritweb) ->
        {
            spiritweb.giveManifestation(manifestation.getManifestationType(), manifestation.getPowerID());
            source.sendFeedback(new TranslationTextComponent(Constants.Strings.POWER_SET_SUCCESS, playerText, manifestationText), false);
            spiritweb.syncToClients(null);
        });
        return Command.SINGLE_SUCCESS;
    }

    private static int remove(CommandContext<CommandSource> context, ServerPlayerEntity player)
    {
        CommandSource source = context.getSource();
        AManifestation manifestation = context.getArgument("manifestation", AManifestation.class);

        TextComponent playerText = TextHelper.getPlayerTextObject(source.getWorld(), player.getUniqueID());

        TextComponent manifestationText = TextHelper.createTextWithTooltip(manifestation.translation(), manifestation.description());

        if (player == null || manifestation == null)
        {
            source.sendErrorMessage(new TranslationTextComponent(Constants.Strings.POWER_SET_FAIL, playerText, manifestationText));
            return 0;
        }
        SpiritwebCapability.get(player).ifPresent((spiritweb) ->
        {
            spiritweb.removeManifestation(manifestation.getManifestationType(), manifestation.getPowerID());
            spiritweb.syncToClients(null);
            source.sendFeedback(new TranslationTextComponent(Constants.Strings.POWER_SET_SUCCESS, playerText, manifestationText), false);
        });
        return Command.SINGLE_SUCCESS;
    }

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher)
    {
        return Commands.literal("powers")
                .requires(context -> context.hasPermissionLevel(2))
                .then(Commands.literal("check")
                        .executes(context -> check(context, context.getSource().asPlayer())))
                .then(Commands.literal("clear")
                        .executes(context -> clear(context, context.getSource().asPlayer())))
                .then(Commands.literal("give")
                        .then(Commands.argument("manifestation", ManifestationsArgumentType.createArgument())
                                .executes(context -> give(context, context.getSource().asPlayer()))))
                .then(Commands.literal("remove")
                        .then(Commands.argument("manifestation", ManifestationsArgumentType.createArgument())
                                .executes(context -> remove(context, context.getSource().asPlayer()))))
                .then(Commands.literal("set")
                        .then(Commands.argument("manifestation", ManifestationsArgumentType.createArgument())
                                .executes(context -> set(context, context.getSource().asPlayer()))
                        )); // end add
    }

}