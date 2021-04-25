/*
 * File created ~ 24 - 4 - 2021 ~ Leaf
 */

package leaf.cosmere.registry;

import leaf.cosmere.Cosmere;
import leaf.cosmere.config.Config;
import leaf.cosmere.constants.Constants;
import leaf.cosmere.constants.Metals;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FeatureRegistry
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Cosmere.MODID);

    public static final Map<Metals.MetalType, RegistryObject<Feature<OreFeatureConfig>>> ORE_FEATURES =
            Arrays.stream(Metals.MetalType.values())
                    .filter(Metals.MetalType::hasOre)
                    .collect(Collectors.toMap(
                            Function.identity(),
                            type -> FEATURES.register(
                                    type.name().toLowerCase() + Constants.RegNameStubs.ORE,
                                    () -> new OreFeature(OreFeatureConfig.CODEC))));


    // The "New Tardis Mod" code says we should register configured versions of the features in FMLCommonSetup
    // since it helps prevent mod incompatibility issues. No need to delete other mod's world gen. Thank you 50!

    public static class ConfiguredFeatures
    {
        public static final Map<Metals.MetalType, ConfiguredFeature<?, ?>> ORE_FEATURES =
                Arrays.stream(Metals.MetalType.values())
                        .filter(Metals.MetalType::hasOre)
                        .collect(Collectors.toMap(
                                Function.identity(),
                                metalType ->
                                        FeatureRegistry.ORE_FEATURES.get(metalType).get().withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,
                                                BlocksRegistry.METAL_ORE.get(metalType).get().getDefaultState(), 5)) //vein size of 5
                                                .withPlacement(
                                                        Placement.RANGE.configure(new TopSolidRangeConfig(6, 0, 64))
                                                                .chance(5))//Config.COMMON_SPEC.oreSpawnChance.get(metalType).get()))
                                                .square()
                                                .count(5)
                        ));
    }

    public static void registerConfiguredFeatures()
    {
        for (Metals.MetalType metalType : Metals.MetalType.values())
        {
            if (metalType.hasOre())
            {
                registerConfiguredFeature(metalType.name().toLowerCase() + Constants.RegNameStubs.ORE, ConfiguredFeatures.ORE_FEATURES.get(metalType));
            }
        }
    }

    private static <T extends Feature<?>> void registerConfiguredFeature(String registryName, ConfiguredFeature<?, ?> configuredFeature)
    {
        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        Registry.register(registry, new ResourceLocation(Cosmere.MODID, registryName), configuredFeature);
    }
}
