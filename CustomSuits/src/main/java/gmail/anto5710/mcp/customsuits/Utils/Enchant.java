package gmail.anto5710.mcp.customsuits.Utils;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class Enchant {

	public static ItemStack
		Protection = new ItemStack(Material.ENCHANTED_BOOK),
		Blast_Protection = new ItemStack(Material.ENCHANTED_BOOK),
		Fire_Protection = new ItemStack(Material.ENCHANTED_BOOK),
		Feather_Falling = new ItemStack(Material.ENCHANTED_BOOK),
		Projectile_Protection = new ItemStack(Material.ENCHANTED_BOOK),
		Respiration = new ItemStack(Material.ENCHANTED_BOOK),
		Aqua_Affinity = new ItemStack(Material.ENCHANTED_BOOK),
		Thorns = new ItemStack(Material.ENCHANTED_BOOK),
		Depth_Strider = new ItemStack(Material.ENCHANTED_BOOK),
		Frost_Walker = new ItemStack(Material.ENCHANTED_BOOK),
		
		Sharpness = new ItemStack(Material.ENCHANTED_BOOK),
		Smite = new ItemStack(Material.ENCHANTED_BOOK),
		Bane_of_Arthropods = new ItemStack(Material.ENCHANTED_BOOK),
		KnockBack = new ItemStack(Material.ENCHANTED_BOOK),
		Fire_Aspect = new ItemStack(Material.ENCHANTED_BOOK),
		Looting = new ItemStack(Material.ENCHANTED_BOOK),
		Sweeping_Edge = new ItemStack(Material.ENCHANTED_BOOK),
	
		Power = new ItemStack(Material.ENCHANTED_BOOK),
		Punch = new ItemStack(Material.ENCHANTED_BOOK),
		Flame = new ItemStack(Material.ENCHANTED_BOOK),
		Infinity = new ItemStack(Material.ENCHANTED_BOOK),
		
		Unbreaking = new ItemStack(Material.ENCHANTED_BOOK),
		Mending = new ItemStack(Material.ENCHANTED_BOOK),
	
		Impalling = new ItemStack(Material.ENCHANTED_BOOK),
		Loyalty = new ItemStack(Material.ENCHANTED_BOOK),
		Riptide = new ItemStack(Material.ENCHANTED_BOOK);

	
	private static Enchantment glow;
	public Enchant(){
		glow = new Glow();
	}

	public static void enchantBook(ItemStack item, Enchantment enchantment, int level, boolean IgnoreLevelLimit) {
		EnchantmentStorageMeta Meta = (EnchantmentStorageMeta) item.getItemMeta();
		
		Meta.addStoredEnchant(enchantment, level, IgnoreLevelLimit);
		item.setItemMeta(Meta);
	}

	public static void enchantFromBook(ItemStack item, ItemStack enchant, int level){
		if(SuitUtils.anyNull(item, enchant)) return;
		
		
		
		ItemMeta meta = item.getItemMeta();
		if (enchant.getType() == Material.ENCHANTED_BOOK) {
			EnchantmentStorageMeta enchantmeta = (EnchantmentStorageMeta) enchant.getItemMeta();
			Map<Enchantment, Integer> Enchantments = enchantmeta.getStoredEnchants();
			
			for (Enchantment key : Enchantments.keySet()) {	
				meta.addEnchant(key, level, true);
			}
			item.setItemMeta(meta);
		}
	}
	
	
	public static void englow(ItemStack item){
		item.addUnsafeEnchantment(glow, 1);
	}
	
	public static void enchantment(ItemStack item, Enchantment enchantment, int level, boolean IgnoreLevelLimit) {
		item.addUnsafeEnchantment(enchantment, level);
	}

	public static void enchantBooks() {
		enchantBook(Protection, Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
		enchantBook(Blast_Protection, Enchantment.PROTECTION_EXPLOSIONS, 4, true);
		enchantBook(Fire_Protection, Enchantment.PROTECTION_FIRE, 4, true);
		enchantBook(Feather_Falling, Enchantment.PROTECTION_FALL, 4, true);
		enchantBook(Projectile_Protection, Enchantment.PROTECTION_PROJECTILE, 4, true);
		enchantBook(Respiration, Enchantment.OXYGEN, 3, true);
		enchantBook(Aqua_Affinity, Enchantment.WATER_WORKER, 2, true);
		enchantBook(Thorns, Enchantment.THORNS, 3, true);
		enchantBook(Depth_Strider, Enchantment.DEPTH_STRIDER, 3, true);
		enchantBook(Frost_Walker, Enchantment.FROST_WALKER, 2, true);
		
		enchantBook(Sharpness, Enchantment.DAMAGE_ALL, 5, true);
		enchantBook(Smite, Enchantment.DAMAGE_UNDEAD, 5, true);
		enchantBook(Bane_of_Arthropods, Enchantment.DAMAGE_ARTHROPODS, 5, true);
		enchantBook(KnockBack, Enchantment.KNOCKBACK, 3, true);
		enchantBook(Fire_Aspect, Enchantment.FIRE_ASPECT, 3, true);
		enchantBook(Looting, Enchantment.LOOT_BONUS_MOBS, 3, true);
		enchantBook(Sweeping_Edge, Enchantment.SWEEPING_EDGE, 3, true);

		enchantBook(Power, Enchantment.ARROW_DAMAGE, 5, true);
		enchantBook(Punch, Enchantment.ARROW_KNOCKBACK, 2, true);
		enchantBook(Flame, Enchantment.ARROW_FIRE, 1, true);
		enchantBook(Infinity, Enchantment.ARROW_INFINITE, 1, true);
		
		enchantBook(Unbreaking, Enchantment.DURABILITY, 4, true);
		enchantBook(Mending, Enchantment.MENDING, 4, true);
		
		enchantBook(Impalling, Enchantment.IMPALING, 5, true);
		enchantBook(Loyalty, Enchantment.LOYALTY, 3, true);
		enchantBook(Riptide, Enchantment.RIPTIDE, 3, true);
	}
}
