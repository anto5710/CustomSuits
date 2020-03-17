package gmail.anto5710.mcp.customsuits.Utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.enchantments.Enchantment;

public class EnchantBuilder {
	private Map<Enchantment, Integer> enchants = new HashMap<>();
	
	public EnchantBuilder enchant(Enchantment type, int level){
		if(type!=null && level >= 0) enchants.put(type, level);
		
		return this;
	}
	
	public void remove(Enchantment type){
		enchants.remove(type);
	}
	
	public Map<Enchantment, Integer> serialize(){
		return enchants;
	}
}
