package leaf.cosmere.common.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import leaf.cosmere.api.Constants;
import leaf.cosmere.api.EnumUtils;
import leaf.cosmere.api.Metals.MetalType;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class MetalArgumentType implements ArgumentType<MetalType>
{
	private static final Collection<String> EXAMPLES = Stream.of(MetalType.STEEL.getName()).toList();

	public static final DynamicCommandExceptionType INVALID_METAL_EXCEPTION =
			new DynamicCommandExceptionType((metal) ->
					Component.translatable(Constants.Strings.POWER_INVALID, metal));

	public static SuggestionsBuilder addMetalNamesWithTooltip(SuggestionsBuilder builder)
	{
		for (MetalType metalType : EnumUtils.METAL_TYPES)
		{
			builder.suggest(metalType.getName(), Component.translatable(metalType.getTranslationKey()));
		}

		builder.buildFuture();
		return builder;
	}

	public static MetalArgumentType createArgument()
	{
		return new MetalArgumentType();
	}

	@Override
	public MetalType parse(StringReader reader) throws CommandSyntaxException
	{
		try
		{
			String read = reader.readString();
			return MetalType.valueOf(read.toUpperCase(Locale.ROOT));
		} catch (IllegalArgumentException e)
		{
			throw INVALID_METAL_EXCEPTION.create(MetalType.valueOf(reader.getRead()));
		}
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder)
	{
		return context.getSource() instanceof SharedSuggestionProvider
		       ? SharedSuggestionProvider.suggest(Collections.emptyList(), addMetalNamesWithTooltip(builder))
		       : Suggestions.empty();
	}

	@Override
	public Collection<String> getExamples()
	{
		return EXAMPLES;
	}
}
