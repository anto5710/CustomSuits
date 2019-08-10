package gmail.anto5710.mcp.customsuits.Setting;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class Enchant {
	CustomSuitPlugin plugin;
	
	public static ItemStack
	Protection = new ItemStack(Material.ENCHANTED_BOOK),
	Blast_Protection = new ItemStack(Material.ENCHANTED_BOOK),
	Fire_Protection = new ItemStack(Material.ENCHANTED_BOOK),
	Feather_Falling = new ItemStack(Material.ENCHANTED_BOOK),
	Respiration = new ItemStack(Material.ENCHANTED_BOOK),
	Aqua_Affinity = new ItemStack(Material.ENCHANTED_BOOK),
	Thorns = new ItemStack(Material.ENCHANTED_BOOK),
	Unbreaking = new ItemStack(Material.ENCHANTED_BOOK),
	Sharpness = new ItemStack(Material.ENCHANTED_BOOK),
	KnockBack = new ItemStack(Material.ENCHANTED_BOOK),
	Fire_Aspect = new ItemStack(Material.ENCHANTED_BOOK),
	Looting = new ItemStack(Material.ENCHANTED_BOOK);

	public Enchant(CustomSuitPlugin plugin) {
		this.plugin = plugin;
	}

	public static void enchantBook(ItemStack item, Enchantment enchantment, int level, boolean IgnoreLevelLimit) {
		EnchantmentStorageMeta Meta = (EnchantmentStorageMeta) item.getItemMeta();

		Meta.addStoredEnchant(enchantment, level, IgnoreLevelLimit);
		item.setItemMeta(Meta);
	}

	public static void enchantFromBook(ItemStack item, ItemStack enchant){
		if(SuitUtils.anyNull(item, enchant)) return;
		
		ItemMeta meta = item.getItemMeta();
		if (enchant.getType() == Material.ENCHANTED_BOOK) {
			EnchantmentStorageMeta enchantmeta = (EnchantmentStorageMeta) enchant.getItemMeta();
			Map<Enchantment, Integer> Enchantments = enchantmeta.getStoredEnchants();
			
			for (Enchantment key : Enchantments.keySet()) {	
				meta.addEnchant(key, enchantmeta.getStoredEnchants().get(key), true);
			}
			item.setItemMeta(meta);
		}
	}
	
	public static void enchantment(ItemStack item, Enchantment enchantment, int level, boolean IgnoreLevelLimit) {
		item.addUnsafeEnchantment(enchantment, level);
//		ItemMeta meta = item.getItemMeta();
//		meta.addEnchant(enchantment, level, IgnoreLevelLimit);
//		item.setItemMeta(meta);
	}

	public static void enchantBooks() {
		enchantBook(Protection, Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);

		enchantBook(Blast_Protection, Enchantment.PROTECTION_EXPLOSIONS, 4, true);

		enchantBook(Fire_Protection, Enchantment.PROTECTION_FIRE, 4, true);

		enchantBook(Feather_Falling, Enchantment.PROTECTION_FALL, 4, true);

		enchantBook(Respiration, Enchantment.OXYGEN, 3, true);

		enchantBook(Aqua_Affinity, Enchantment.WATER_WORKER, 2, true);

		enchantBook(Thorns, Enchantment.THORNS, 3, true);

		enchantBook(Unbreaking, Enchantment.DURABILITY, 4, true);

		enchantBook(Sharpness, Enchantment.DAMAGE_ALL, 4, true);

		enchantBook(KnockBack, Enchantment.KNOCKBACK, 3, true);

		enchantBook(Fire_Aspect, Enchantment.FIRE_ASPECT, 3, true);

		enchantBook(Looting, Enchantment.LOOT_BONUS_MOBS, 3, true);
	}
}
