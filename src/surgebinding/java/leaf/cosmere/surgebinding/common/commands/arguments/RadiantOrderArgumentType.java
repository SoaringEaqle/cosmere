/*
 * File updated ~ 4 - 2 - 2025 ~ Leaf
 */

package leaf.cosmere.surgebinding.common.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import leaf.cosmere.api.EnumUtils;
import leaf.cosmere.api.Roshar;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class RadiantOrderArgumentType implements ArgumentType<Roshar.RadiantOrder>
{
	private static final Collection<String> EXAMPLES = Arrays.stream(Roshar.RadiantOrder.values()).map(Roshar.RadiantOrder::getName).toList();

	@Override
	public Collection<String> getExamples()
	{
		return EXAMPLES;
	}

	public static final DynamicCommandExceptionType INVALID_ORDER_EXCEPTION =
			new DynamicCommandExceptionType((order) ->
					Component.translatable("argument.surgebinding.order.invalid", order));

	public static RadiantOrderArgumentType createArgument()
	{
		return new RadiantOrderArgumentType();
	}

	@Override
	public Roshar.RadiantOrder parse(StringReader reader) throws CommandSyntaxException
	{
		final String readerRead = reader.readUnquotedString().toUpperCase(Locale.ROOT);

		try
		{
			return Roshar.RadiantOrder.valueOf(readerRead);
		}
		catch (IllegalArgumentException e)
		{
			throw INVALID_ORDER_EXCEPTION.create(readerRead);
		}
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder)
	{
		return context.getSource() instanceof SharedSuggestionProvider
		       ? SharedSuggestionProvider.suggest(Collections.emptyList(), addSuggestions(builder))
		       : Suggestions.empty();
	}

	public static SuggestionsBuilder addSuggestions(SuggestionsBuilder builder)
	{
		for (Roshar.RadiantOrder order : EnumUtils.RADIANT_ORDERS)
		{
			builder.suggest(order.getName());
		}

		builder.buildFuture();
		return builder;
	}
}