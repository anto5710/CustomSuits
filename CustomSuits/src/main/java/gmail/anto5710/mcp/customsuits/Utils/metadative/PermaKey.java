package gmail.anto5710.mcp.customsuits.Utils.metadative;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;

public class PermaKey <T>{
	private PersistentDataType<T, T> type;
	private String name;
	private NamespacedKey namedkey;
	
	public PermaKey(String name, PersistentDataType<T, T> type, CustomSuitPlugin plugin){
		this.name = name;
		this.type = type;
		this.namedkey = new NamespacedKey(plugin, name);
	}
	
	public PersistentDataType<T, T> getType() {
		return type;
	}

	public String getKeyString() {
		return name;
	}
	
	public NamespacedKey getNamedKey() {
		return namedkey;
	}
}
