package gmail.anto5710.mcp.customsuits.Utils.metadative;

import javax.annotation.Nonnull;

import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;

public class Permadative{
	private static CustomSuitPlugin plugin;
	public Permadative(CustomSuitPlugin plugin) {
		Permadative.plugin = plugin;
	}
		
	public static void remove(@Nonnull PersistentDataHolder e, PermaKey<?> key) {
		if(hasKey(e, key)) e.getPersistentDataContainer().remove(key.getNamedKey());
	}
	
	public static <T> void imprint(PersistentDataHolder e, PermaKey<T> key, T val){
		if(SuitUtils.anyNull(e, key)) return;
		
		e.getPersistentDataContainer().set(key.getNamedKey(), key.getType(), val);
	}
	
	public static <T> T get(PersistentDataHolder e, PermaKey<T> key) {
		return e.getPersistentDataContainer().get(key.getNamedKey(), key.getType());
	}

	public static boolean hasKey(PersistentDataHolder e, @Nonnull PermaKey<?> key) {
		return e.getPersistentDataContainer().has(key.getNamedKey(), key.getType());
	}
	
	public static <T> PermaKey<T> createKey(String name, PersistentDataType<T, T> type){
		return new PermaKey<>(name, type, plugin);
	}
}
