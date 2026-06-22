package leaf.cosmere.surgebinding.common.utils;

import com.google.common.base.CaseFormat;
import leaf.cosmere.api.math.MathHelper;

import java.util.HashMap;
import java.util.Map;

public class ShardHelper
{
	//Shardplate Components
	public static final int TOTAL_HEAD_IDS = 1;
	public static final int TOTAL_FACEPLATE_IDS = 4;
	public static final int TOTAL_ARM_IDS = 1;
	public static final int TOTAL_PAULDRON_IDS = 2;
	public static final int TOTAL_TORSO_IDS = 1;
	public static final int TOTAL_LEG_IDS = 1;
	public static final int TOTAL_KAMA_IDS = 1;
	public static final int TOTAL_BOOT_IDS = 1;


	public enum PlateComponent
	{
		HEAD(TOTAL_HEAD_IDS),
		FACEPLATE(TOTAL_FACEPLATE_IDS),
		BODY(TOTAL_TORSO_IDS),
		KAMA(TOTAL_KAMA_IDS),
		RIGHT_ARM_MAIN(TOTAL_ARM_IDS),
		RIGHT_PAULDRON(TOTAL_PAULDRON_IDS),
		RIGHT_LEG_MAIN(TOTAL_LEG_IDS),
		RIGHT_BOOT(TOTAL_BOOT_IDS),
		RIGHT_BOOT_TIP(TOTAL_BOOT_IDS),
		LEFT_ARM_MAIN(TOTAL_ARM_IDS),
		LEFT_PAULDRON(TOTAL_PAULDRON_IDS),
		LEFT_LEG_MAIN(TOTAL_LEG_IDS),
		LEFT_BOOT(TOTAL_BOOT_IDS),
		LEFT_BOOT_TIP(TOTAL_BOOT_IDS);



		public static final Map<PlateComponent, PlateComponent> rightSide = Map.of(
				LEFT_ARM_MAIN, RIGHT_ARM_MAIN,
				LEFT_PAULDRON, RIGHT_PAULDRON,
				LEFT_LEG_MAIN, RIGHT_LEG_MAIN,
				LEFT_BOOT, RIGHT_BOOT);

		public final int maxID;
		public final String lowerCamel = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name()) + "ID";
		public final String lowerSnake = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, name());

		PlateComponent(int maxID){
			this.maxID = maxID;
		}

		public static HashMap<PlateComponent, Integer> randomComponentMap()
		{
			HashMap<PlateComponent, Integer> map = new HashMap<>();
			for(var component: COMPONENTS)
			{
				if(component.name().contains("LEFT"))
				{
					var rightID = map.getOrDefault(rightSide.get(component), MathHelper.randomInt(1, component.maxID));
					map.put(component, rightID);
				}
				else
				{
					map.put(component, MathHelper.randomInt(component == KAMA ? 0:1, component.maxID));
				}
			}
			return map;
		}

		public boolean isValid(int id)
		{
			return id >= 0 && id <= maxID;
		}

		public String componentNameID(Map<PlateComponent, Integer> compMap)
		{
			if(!compMap.containsKey(this))
			{
				return null;
			}
			return lowerSnake + compMap.get(this).toString();
		}
	}

	public static final PlateComponent[] COMPONENTS = PlateComponent.values();

	//Shardblade Components
	public static final int TOTAL_BLADE_IDS = 2;
	public static final int TOTAL_HANDLE_IDS = 1;
	public static final int TOTAL_POMMEL_IDS = 1;
	public static final int TOTAL_CROSSGUARD_IDS = 1;

	public enum BladeComponent{
		BLADE(TOTAL_BLADE_IDS),
		HANDEL(TOTAL_HANDLE_IDS),
		POMMEL(TOTAL_POMMEL_IDS),
		CROSSGUARD(TOTAL_CROSSGUARD_IDS);

		public final int maxID;
		public final String lowerCamel = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name()) + "ID";
		public final String lowerSnake = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, name());

		BladeComponent(int maxID){
			this.maxID = maxID;
		}

		public static HashMap<PlateComponent, Integer> randomComponentMap()
		{
			HashMap<ShardHelper.PlateComponent, java.lang.Integer> map = new HashMap<>();
			for(var component: COMPONENTS)
			{
				map.put(component, MathHelper.randomInt(1, component.maxID));
			}
			return map;
		}

		public boolean isValid(int id)
		{
			return id >= 0 && id <= maxID;
		}

		public String componentNameID(Map<BladeComponent, Integer> compMap)
		{
			if(!compMap.containsKey(this))
			{
				return null;
			}
			return lowerSnake + compMap.get(this).toString();
		}
	}
}
