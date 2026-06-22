package leaf.cosmere.surgebinding;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

public class SurgebindingIdealsGen implements DataProvider
{
	private final Map<String, String> data = new TreeMap<>();
	private final PackOutput output;
	private final String modid;
	private final String locale;

	public SurgebindingIdealsGen(PackOutput output, String modid, String locale) {
		this.output = output;
		this.modid = modid;
		this.locale = locale;
	}


	@Override
	public CompletableFuture<?> run(CachedOutput pOutput)
	{
		addIdeals();

		if (!data.isEmpty())
			return save(cache, this.output.getOutputFolder(PackOutput.Target.RESOURCE_PACK).resolve(this.modid).resolve("lang").resolve(this.locale + ".json"));

		return CompletableFuture.allOf();
	}

	@Override
	public String getName()
	{
		return "";
	}

	public void addIdeals()
	{

	}
}
