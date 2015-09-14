package gmail.anto5710.mcp.customsuits.Setting;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;


public class Enchant {
	CustomSuitPlugin plugin;
	public static ItemStack Protection = new ItemStack(Material.ENCHANTED_BOOK);
	public static ItemStack Blast_Protection = new ItemStack(Material.ENCHANTED_BOOK);
	public static ItemStack Fire_Protection = new ItemStack(Material.ENCHANTED_BOOK);
	public static ItemStack Feather_Falling = new ItemStack(Material.ENCHANTED_BOOK);
	public static ItemStack Respiration = new ItemStack(Material.ENCHANTED_BOOK);
	public static ItemStack Aqua_Affinity = new ItemStack(Material.ENCHANTED_BOOK);
	public static ItemStack Thorns = new ItemStack(Material.ENCHANTED_BOOK);
	public static ItemStack Unbreaking = new ItemStack(Material.ENCHANTED_BOOK);
	public static ItemStack Sharpness = new ItemStack(Material.ENCHANTED_BOOK);
	public static ItemStack KnockBack = new ItemStack(Material.ENCHANTED_BOOK);
	public static ItemStack Fire_Aspect = new ItemStack(Material.ENCHANTED_BOOK);
	public static ItemStack Looting = new ItemStack(Material.ENCHANTED_BOOK);

	
	public Enchant(CustomSuitPlugin plugin){
		this.plugin = plugin;
		
	}
	
	
	
	public static void enchantmentbook(ItemStack item, Enchantment enchantment, int level,
			boolean IgnoreLevelLimit) {
		EnchantmentStorageMeta Meta = (EnchantmentStorageMeta) item
				.getItemMeta();
		
		Meta.addStoredEnchant(enchantment, level, IgnoreLevelLimit);
		item.setItemMeta(Meta);
	}
	public static void enchantment(ItemStack item, Enchantment enchantment, int level,
			boolean IgnoreLevelLimit) {
		ItemMeta Meta = item.getItemMeta();
		Meta.addEnchant(enchantment, level, IgnoreLevelLimit);
		item.setItemMeta(Meta);
	}
	public static void enchantBooks(){
	
		enchantmentbook(Protection, Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);

	
		enchantmentbook(Blast_Protection, Enchantment.PROTECTION_EXPLOSIONS, 4,
				true);

	
		enchantmentbook(Fire_Protection, Enchantment.PROTECTION_FIRE, 4, true);

		
		enchantmentbook(Feather_Falling, Enchantment.PROTECTION_FALL, 4, true);

		
		enchantmentbook(Respiration, Enchantment.OXYGEN, 3, true);

		
		enchantmentbook(Aqua_Affinity, Enchantment.WATER_WORKER, 2, true);

		
		enchantmentbook(Thorns, Enchantment.THORNS, 3, true);

		
		enchantmentbook(Unbreaking, Enchantment.DURABILITY, 4, true);

		
		enchantmentbook(Sharpness, Enchantment.DAMAGE_ALL, 4, true);

		
		enchantmentbook(KnockBack, Enchantment.KNOCKBACK, 3, true);

		
		enchantmentbook(Fire_Aspect, Enchantment.FIRE_ASPECT, 3, true);

		
		enchantmentbook(Looting, Enchantment.LOOT_BONUS_MOBS, 3, true);
	}
}
