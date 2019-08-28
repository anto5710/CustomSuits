package gmail.anto5710.mcp.customsuits.Utils;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemUtil {	
	public static boolean isHorseArmor(ItemStack item){
		if(item==null) return false;
		
		Material type = item.getType();
		return type == Material.IRON_HORSE_ARMOR || type == Material.GOLDEN_HORSE_ARMOR || type == Material.DIAMOND_HORSE_ARMOR;
	}

	public static boolean dyeable(ItemStack item){
		if(item==null) return false;
		
		Material type = item.getType();
		return type == Material.LEATHER_HELMET || type == Material.LEATHER_CHESTPLATE || type == Material.LEATHER_LEGGINGS || type == Material.LEATHER_BOOTS;
	}

	public static void name(String name, ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
	}

	public static void dye(ItemStack item, Color color) {
		if(dyeable(item)){
			LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
			meta.setColor(color);
			item.setItemMeta(meta);
		}
	}

	public static ItemStack createWithName(Material material, String name) {
		ItemStack item = new ItemStack(material, 1);
		name(name, item);
		return item;
	}

	public static ItemStack decapitate(String name) {
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
		skullMeta.setOwner(name);
		skull.setItemMeta(skullMeta);
		return skull;
	}

	public static ItemStack[] dyeSpectrum(ItemStack itemstack) {
		ItemStack[] items = new ItemStack[ColorUtils.colorMap.size()];
		Iterator<String> colors = ColorUtils.colorMap.keySet().iterator();
		int index = 0;
		while (colors.hasNext()) {
			String colorName = colors.next();
			Color color = ColorUtils.colorMap.get(colorName);
			
			ItemStack item = itemstack.clone();
			dye(item, color);
			name((colorName).toUpperCase(), item);
			items[index] = item;
			index++;
		}
		return items;
	}

	public static boolean checkName(ItemStack sample, ItemStack check) {
		if(sample == null || check == null) return sample == check;
		
		String sampleName = sample.getItemMeta().getDisplayName();
		String checkName = check.getItemMeta().getDisplayName();
		
		if(sampleName == null && checkName == null) return true;
		
		return sampleName != null && checkName != null && sampleName.endsWith(checkName);
	}

	public static boolean checkName(ItemStack item, String token){
		if (item==null || token ==null || token.isEmpty() || !item.hasItemMeta()) return false;
		
		String name = item.getItemMeta().getDisplayName();
		return name != null && name.contains(token);
	}

	public static boolean checkLore(ItemStack sample, ItemStack check) {
		if (!sample.getItemMeta().hasLore() && !check.getItemMeta().hasLore()) return true;
		
		if (sample.getItemMeta().hasLore() && check.getItemMeta().hasLore()) {
			
			List<String> sampleList = sample.getItemMeta().getLore();
			List<String> checkList = check.getItemMeta().getLore();
	
			if (sampleList.size() != checkList.size()) {
				return false;
			}
			for (int index = 0; index <= checkList.size() - 1; index++) {
				if (!checkList.get(index).endsWith(sampleList.get(index))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public static boolean checkItem(ItemStack sample, ItemStack check){
		if(check==null || sample == null){
			return check == sample; // 둘 다 null(air)?
		}
		return sample.getType()==check.getType () && checkName(sample , check) && checkLore(sample, check);
	}
}
