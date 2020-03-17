package gmail.anto5710.mcp.customsuits.Utils;

import java.lang.reflect.Field;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class Glow extends Enchantment {
	public static NamespacedKey id = NamespacedKey.minecraft("illum");
	private static Enchantment glow;
	
	public Glow() {
		super(id);
	}
	
	public static Enchantment getInstance(){
		return glow;
	}

	public static void register() {
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Glow glow = new Glow();
			if(Enchantment.getByKey(id)==null){
				Enchantment.registerEnchantment(glow);
			}
			Glow.glow = glow;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean canEnchantItem(ItemStack arg0) {
		return false;
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) {
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		return null;
	}

	@Override
	public int getMaxLevel() {
		return 0;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public int getStartLevel() {
		return 0;
	}

	@Override
	public boolean isCursed() {
		return false;
	}

	@Override
	public boolean isTreasure() {
		return false;
	}
}
