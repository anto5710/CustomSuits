package gmail.anto5710.mcp.customsuits.Utils;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class Enchant {

	public static void exscribe(@Nonnull ItemStack dest, @Nonnull ItemStack book) {
		if (book.getType() == Material.ENCHANTED_BOOK) {
			ItemMeta destmeta = dest.getItemMeta();
			EnchantmentStorageMeta bookmeta = (EnchantmentStorageMeta) book.getItemMeta();

			bookmeta.getStoredEnchants().forEach((ment, level) -> destmeta.addEnchant(ment, level, true));
			dest.setItemMeta(destmeta);
		}
	}
	
	public static void exscribe(@Nonnull ItemStack dest, @Nonnull ItemStack book, int unilevel) {
		if (book.getType() == Material.ENCHANTED_BOOK) {
			ItemMeta destmeta = dest.getItemMeta();
			EnchantmentStorageMeta bookmeta = (EnchantmentStorageMeta) book.getItemMeta();

			bookmeta.getStoredEnchants().keySet().forEach(ment -> destmeta.addEnchant(ment, unilevel, true));
			dest.setItemMeta(destmeta);
		}
	}
	
	public static void manuscribe(ItemStack book, Enchantment enchantment, int level, boolean IgnoreLevelLimit) {
		EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
		
		meta.addStoredEnchant(enchantment, level, IgnoreLevelLimit);
		book.setItemMeta(meta);
	}
	
	public static ItemStack manuscribe(Enchantment ment, int level, boolean IgnoreLevelLimit){
		ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
		manuscribe(book, ment, level, IgnoreLevelLimit);
		return book;
	}
	
	public static ItemStack manuscribe(Enchantment ment){
		return manuscribe(ment, ment.getMaxLevel(), false);
	}
	
	public static void englow(ItemStack item){
		item.addUnsafeEnchantment(Glow.getInstance(), 1);
	}
}
