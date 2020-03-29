package me.gw3.onigokko.customEntities;

import java.lang.reflect.Field;
import java.util.Map;

import org.bukkit.entity.EntityType;

import me.gw3.onigokko.customEntities.CustomSkeleton;
import me.gw3.onigokko.customEntities.CustomZombie;
import net.minecraft.server.v1_8_R3.EntityTypes;

public class NMSUtil {
	
	@SuppressWarnings("deprecation")
	public static void registerEntities() {
		a(CustomZombie.class, "Zombiez", EntityType.ZOMBIE.getTypeId());
		a(CustomSkeleton.class, "Skeleton", EntityType.SKELETON.getTypeId());
	}
	@SuppressWarnings({ "rawtypes", "unused" })
	private static Object getPrivateStatic(Class clazz, String f) throws Exception {
		Field filed = clazz.getDeclaredField(f);
		filed.setAccessible(true);
		return filed.get(null);
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void a(Class paramClass, String paramString, int paramInt) {
		try {
			((Map) getPrivateStatic(EntityTypes.class, "c")).put(paramString, paramClass);
			((Map) getPrivateStatic(EntityTypes.class, "d")).put(paramClass, paramString);
			((Map) getPrivateStatic(EntityTypes.class, "e")).put(paramInt,paramClass);
			((Map) getPrivateStatic(EntityTypes.class, "f")).put(paramClass,paramInt);
			((Map) getPrivateStatic(EntityTypes.class, "g")).put(paramString,paramInt);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
