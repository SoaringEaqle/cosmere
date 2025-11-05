package leaf.cosmere.api;

import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public interface IHasAlloy
{
	HashSet<Metals.MetalType> readMetalAlloyNbtData(ItemStack itemStack);

	boolean writeMetalAlloyNbtData(ItemStack itemStack, HashSet<Metals.MetalType> alloyedMetals);

	Color mixColors(ArrayList<Color> colors);

	Color getColour(HashSet<Metals.MetalType> metals);

	int getColourValue(HashSet<Metals.MetalType> metals);

}
