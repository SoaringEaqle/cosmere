/*
 * File updated ~ 10 - 10 - 2024 ~ Leaf
 */

package leaf.cosmere.surgebinding;

import leaf.cosmere.api.EnumUtils;
import leaf.cosmere.api.Manifestations;
import leaf.cosmere.api.Roshar;
import leaf.cosmere.api.helpers.RegistryHelper;
import leaf.cosmere.api.manifestation.Manifestation;
import leaf.cosmere.api.providers.IAttributeProvider;
import leaf.cosmere.api.providers.IEntityTypeProvider;
import leaf.cosmere.api.text.StringHelper;
import leaf.cosmere.common.registration.impl.ManifestationRegistryObject;
import leaf.cosmere.surgebinding.common.Surgebinding;
import leaf.cosmere.surgebinding.common.registries.SurgebindingAttributes;
import leaf.cosmere.surgebinding.common.registries.SurgebindingEntityTypes;
import leaf.cosmere.surgebinding.common.registries.SurgebindingManifestations;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;

import static leaf.cosmere.api.Constants.Strings.KEY_SHARDBLADE;

public class SurgebindingEngLangGen extends LanguageProvider
{
	final String advancementTitleFormat = "advancements.surgebinding.%s.title";
	final String advancementDescriptionFormat = "advancements.surgebinding.%s.description";

	public SurgebindingEngLangGen(PackOutput output)
	{
		super(output, Surgebinding.MODID, "en_us");
	}

	@Override
	protected void addTranslations()
	{
		addItemsAndBlocks();
		addEntities();
		addAdvancements();
		addManifestations();
		addAttributes();
		addPatchouli();
		addCreativeTabs();
		addTooltips();
		addDamageSources();
		addMobEffects();
		addCurioIdentifiers();
		addConfigs();
		addCommands();
		addKeybindings();
		addStats();
		addOaths();

	}


	private void addItemsAndBlocks()
	{
		//Items and Blocks
		for (Item item : ForgeRegistries.ITEMS.getValues())
		{
			final ResourceLocation registryName = RegistryHelper.get(item);
			if (registryName.getNamespace().contentEquals(Surgebinding.MODID))
			{
				String localisedString = StringHelper.fixCapitalisation(registryName.getPath());
				add(item.getDescriptionId(), localisedString);
			}
		}

	}

	private void addEntities()
	{
		for (IEntityTypeProvider type : SurgebindingEntityTypes.ENTITY_TYPES.getAllEntityTypes())
		{
			final ResourceLocation id = type.getRegistryName();
			add(type.getEntityType().getDescriptionId(), StringHelper.fixCapitalisation(id.getPath()));
		}
	}

	private void addAdvancements()
	{
		Manifestations.ManifestationTypes value = Manifestations.ManifestationTypes.SURGEBINDING;
		{
			add(String.format(advancementTitleFormat, value.getName()), StringHelper.fixCapitalisation(value.getName()));
			add(String.format(advancementDescriptionFormat, value.getName()), "Test description: " + StringHelper.fixCapitalisation(value.getName()));
		}
	}

	private void addManifestations()
	{
		for (ManifestationRegistryObject<Manifestation> manifestationRegistryObject : SurgebindingManifestations.SURGEBINDING_POWERS.values())
		{
			Manifestation manifestation = manifestationRegistryObject.getManifestation();

			//power type
			String key = manifestation.getTranslationKey();
			String name = manifestation.getName();

			//description
			String description = "Needs description";

			String tabName = manifestation.getManifestationType().getName();

			add(String.format(advancementTitleFormat, tabName + "." + name), StringHelper.fixCapitalisation(name));
			add(String.format(advancementDescriptionFormat, tabName + "." + name), "Test description: " + StringHelper.fixCapitalisation(name));

			//Name
			add(key, StringHelper.fixCapitalisation(name));

			//todo decide about manifestation descriptions?
			final ResourceLocation regName = manifestation.getRegistryName();
			add("manifestation." + regName.getNamespace() + "." + regName.getPath() + ".description", description);
		}

	}

	private void addAttributes()
	{
		//Attributes
		for (IAttributeProvider registryObject : SurgebindingAttributes.ATTRIBUTES.getAllAttributes())
		{
			final String descriptionId = registryObject.getAttribute().getDescriptionId();
			//no duplicates pls
			//manifestation section handles adding attributes lang gen for themselves
			if (!descriptionId.startsWith("manifestation"))
			{
				String translation = descriptionId.split("\\.")[1];
				add(descriptionId, StringHelper.fixCapitalisation(translation));
			}
		}
	}

	private void addPatchouli()
	{
		//todo surgebinding patchouli localisation
	}


	private void addCreativeTabs()
	{
		//ItemGroups/Tabs
		add("tabs.surgebinding.items", "Surgebinding");

	}

	private void addTooltips()
	{

	}

	private void addDamageSources()
	{
	}

	private void addMobEffects()
	{

	}

	private void addCurioIdentifiers()
	{

	}

	private void addConfigs()
	{

	}

	private void addCommands()
	{

	}

	private void addKeybindings()
	{
		add(KEY_SHARDBLADE, "Summon/Dismiss Shardblade");
	}

	private void addStats()
	{

	}

	private void addOaths()
	{
		String oath = "first";



		for(Roshar.RadiantOrder order: EnumUtils.RADIANT_ORDERS)
		{

			for(int i = 1; i <= 5; i++)
			{
				add(order.getOath(i), getOath(order,i));
			}
		}
	}

	private String getOath(Roshar.RadiantOrder order, int ideal)
	{
		final String IDEAL_NOT_IMPLEMENTED = "notyetimplemented";
		if (ideal == 1)
		{
			return "Life before Death, Strength before Weakness, Journey before Destination";
		}
		else if(ideal == 2)
		{
			return List.of(
					"I will protect those who cannot protect themselves.", //windrunner
					"I swear to seek justice, to let it guide me, until I find a more perfect Ideal.", //skybreaker
					"To control my power, I will control myself.", //dustbringer
					"I will remember those who have been forgotten", //edgedancer
					"I will seek truth wherever it is hidden.", //truthwatcher //todo better?
					IDEAL_NOT_IMPLEMENTED, //lightweaver
					"I will reach my potential", //elsecaller
					"I will seek freedom for those in bondage.", //willshaper
					"I will step forward when others fall back.", //stoneward
					"I will unite instead of divide." //bondsmith
			).get(order.getID());
		}
		else if(ideal == 3)
		{
			return List.of(
					"I will protect even those I hate", //windrunner
					"I swear to follow", //skybreaker
					"To understand my power, I will understand what power is.", //dustbringer
					"I will listen to those who have been ignored.", //edgedancer
					"I will reveal truth to all who seek it.", //truthwatcher
					IDEAL_NOT_IMPLEMENTED, //lightweaver
					"I will achieve my goals, no matter the cost", //elsecaller
					"I will fight oppression", //willshaper
					"I will be the foundation on which others can build.", //stoneward
					"I will take responsibility for what I have done. If I must fall, I will rise each time a better" //bondsmith
			).get(order.getID());
		}
		else if(ideal == 4)
		{
			return List.of(
					"I accept that there will be those I cannot protect.", //windrunner
					IDEAL_NOT_IMPLEMENTED, //skybreaker
					"I will accept that destruction is sometimes necessary", //dustbringer
					IDEAL_NOT_IMPLEMENTED, //edgedancer
					IDEAL_NOT_IMPLEMENTED, //truthwatcher
					IDEAL_NOT_IMPLEMENTED, //lightweaver
					"I will accept that some goals are unobtainable", //elsecaller
					IDEAL_NOT_IMPLEMENTED, //willshaper
					IDEAL_NOT_IMPLEMENTED, //stoneward
					IDEAL_NOT_IMPLEMENTED //bondsmith
			).get(order.getID());
		}
		else if (ideal == 5)
		{
			List.of(
					"I will protect myself, so that I may continue to protect others. ", //windrunner
					"I am the law", //skybreaker
					IDEAL_NOT_IMPLEMENTED, //dustbringer
					IDEAL_NOT_IMPLEMENTED, //edgedancer
					IDEAL_NOT_IMPLEMENTED, //truthwatcher
					IDEAL_NOT_IMPLEMENTED, //lightweaver
					IDEAL_NOT_IMPLEMENTED, //elsecaller
					IDEAL_NOT_IMPLEMENTED, //willshaper
					IDEAL_NOT_IMPLEMENTED, //stoneward
					IDEAL_NOT_IMPLEMENTED //bondsmith
			).get(order.getID());
		}
		return IDEAL_NOT_IMPLEMENTED;
	}
}
