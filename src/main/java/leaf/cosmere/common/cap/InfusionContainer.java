package leaf.cosmere.common.cap;

import leaf.cosmere.api.investiture.*;
import leaf.cosmere.api.manifestation.Manifestation;
import leaf.cosmere.common.investiture.Infusion;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.*;

public class InfusionContainer<T extends CapabilityProvider<T>> implements IInfuseContainer<T>
{
	public static final Capability<IInfuseContainer<?>> CAPABILITY = CapabilityManager.get(new CapabilityToken<>()
	{
	});

	//detect if capability has been set up yet
	private boolean didSetup = false;

	protected final T parent;
	protected Entity entityAssociate;


	protected CompoundTag nbt;

	public final Set<IInvestiture> investitures = new HashSet<>();
	protected double maxBEU;

	public InfusionContainer(T parent)
	{
		this.parent = parent;
		if(this.parent instanceof Entity entity)
		{
			entityAssociate = entity;
		}
	}

	@Nonnull
	public static LazyOptional<IInfuseContainer<?>> get(LivingEntity entity)
	{
		return entity != null ? entity.getCapability(InfusionContainer.CAPABILITY, null)
		                      : LazyOptional.empty();
	}

	@Nonnull
	public static LazyOptional<IInfuseContainer<?>> get(ItemStack stack)
	{
		return stack != null ? stack.getCapability(InfusionContainer.CAPABILITY, null)
		                     : LazyOptional.empty();
	}

	@Nonnull
	public static LazyOptional<IInfuseContainer<?>> get(BlockEntity entity)
	{
		return entity != null ? entity.getCapability(InfusionContainer.CAPABILITY, null)
		                      : LazyOptional.empty();
	}

	@Nonnull
	public static LazyOptional<IInfuseContainer<?>> get(net.minecraftforge.common.capabilities.CapabilityProvider<?> object)
	{
		if(object instanceof LivingEntity entity)
		{
			return get(entity);
		}
		else if(object instanceof ItemStack stack)
		{
			return get(stack);
		}
		else if(object instanceof BlockEntity entity)
		{
			return get(entity);
		}
		else
		{
			return object != null ? object.getCapability(InfusionContainer.CAPABILITY, null)
			                      : LazyOptional.empty();
		}
	}

	@Override
	public T getParent()
	{
		return parent;
	}

	@Override
	public Entity getEntityAssociate()
	{
		return entityAssociate;
	}

	@Override
	public void setEntityAssociate(Entity entityAssociate)
	{
		this.entityAssociate = entityAssociate;
	}

	@Override
	public double currentBEU()
	{
		double out = 0;
		for(IInvestiture invest : investitures)
		{
			out += invest.getBEU();
		}
		return out;
	}

	@Override
	public double getMaxBEU()
	{
		return maxBEU;
	}

	@Override
	public void setMaxBEU(double maxBEU)
	{
		this.maxBEU = maxBEU;
	}

	@Override
	public HashSet<IInvestiture> availableInvestitures(Manifestation manifest)
	{
		HashSet<IInvestiture> out = new HashSet<>();
		for (IInvestiture invest : investitures)
		{
			if(Arrays.stream(invest.getApplicableManifestations()).anyMatch(m1 -> m1.equals(manifest)))
			{
				out.add(invest);
			}
		}
		return out;
	}

	@Override
	public void clean()
	{
		investitures.removeIf(investiture -> investiture.getBEU() == 0);
		System.gc();
	}

	@Override
	public InvHelpers.InvestitureSource containerSource()
	{
		if(parent instanceof LivingEntity)
		{
			return InvHelpers.InvestitureSource.SELF;
		}
		else if(parent instanceof ItemStack)
		{
			return InvHelpers.InvestitureSource.GEMSTONE;
		}
		else if(parent != null)
		{

		}
		return null;
	}

	@Override
	@SubscribeEvent
	public void tick()
	{

	}

	@Override
	public void syncToClients(@Nullable ServerPlayer serverPlayerEntity)
	{

	}

	@Override
	public CompoundTag serializeNBT()
	{
		if(nbt == null)
		{
			nbt = new CompoundTag();
		}
		if(!investitures.isEmpty())
		{
			ListTag investStored = new ListTag();

			for (IInvestiture investiture : investitures)
			{
				investStored.add(investiture.serializeNBT());
			}
			nbt.put("investitures", investStored);
		}

		nbt.putDouble("maxBEU", maxBEU);
		return nbt;

	}

	@Override
	public void deserializeNBT(CompoundTag nbt)
	{
		if(nbt == null || nbt.isEmpty())
		{
			return;
		}
		this.nbt = nbt;
		if (nbt.contains("investitures"))
		{
			ListTag listtag = nbt.getList("investitures", Tag.TAG_LIST);
			for (int i = 0; i < listtag.size(); i++)
			{
				CompoundTag sub = listtag.getCompound(i);
				IInvestiture temp = Infusion.buildFromNBT(sub, this);
				investitures.add(temp);

			}

		}
		maxBEU = nbt.getDouble("maxBEU");
	}
}
