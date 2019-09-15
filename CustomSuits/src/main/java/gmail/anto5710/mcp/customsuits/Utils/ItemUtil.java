package gmail.anto5710.mcp.customsuits.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
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

	public static void name(ItemStack item, String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
	}

	private static void setLore(ItemStack item, List<String>base, @Nonnull String...lines){
		if(base==null) base = new ArrayList<>();
		
		base.addAll(Arrays.asList(lines));
		ItemMeta meta = item.getItemMeta();
		meta.setLore(base);
		item.setItemMeta(meta);
	}
	
	public static void setLore(ItemStack item, String...lines){
		setLore(item, null, lines);
	}
	
	public static void addLore(ItemStack item, String... lines){
		setLore(item, item.getItemMeta().getLore(), lines);
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
		name(item, name);
		return item;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack decapitate(String name) {
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
		skullMeta.setOwner(name);
		skull.setItemMeta(skullMeta);
		return skull;
	}
	
	public static Player capitate(ItemStack skull){
		if(skull.getType() == Material.PLAYER_HEAD){
			SkullMeta meta = (SkullMeta) skull.getItemMeta();
			@SuppressWarnings("deprecation")
			String name = meta.getOwner();
			return Bukkit.getPlayer(name);
		}
		return null;
	}
	
	public static ItemStack[] dyeSpectrum(ItemStack itemstack) {
		ItemStack[] items = new ItemStack[ColorUtil.colorMap.size()];
		Iterator<String> colors = ColorUtil.colorMap.keySet().iterator();
		int index = 0;
		while (colors.hasNext()) {
			String colorName = colors.next();
			Color color = ColorUtil.colorMap.get(colorName);
			
			ItemStack item = itemstack.clone();
			dye(item, color);
			name(item, (colorName).toUpperCase());
			items[index] = item;
			index++;
		}
		return items;
	}

	public static boolean checkName(ItemStack sample, ItemStack check) {
		if(sample == null || check == null) return sample == check;
		
		String sampleName = getName(sample);
		String checkName = getName(check);
		
		if(sampleName == null && checkName == null) return true;
		
		return sampleName != null && checkName != null && sampleName.endsWith(checkName);
	}

	public static boolean checkName(ItemStack item, String token){
		if (item==null || token ==null || token.isEmpty() || !item.hasItemMeta()) return false;
		
		String name = getName(item);
		return name != null && name.contains(token);
	}
	
	public static String getName(ItemStack item){
		return (item!=null && item.hasItemMeta()) ? item.getItemMeta().getDisplayName() : null;
	}

	public static boolean checkLore(ItemStack sample, ItemStack check) {
		boolean sampleHasLore = sample.getItemMeta().hasLore();
		boolean checkHasLore = check.getItemMeta().hasLore();
		if(!sampleHasLore || !checkHasLore) return sampleHasLore == checkHasLore ; //anyNull -> then if null=null return true
	
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

	public static boolean checkItem(ItemStack sample, ItemStack check){
		if(check==null || sample == null){
			return check == sample; // 둘 다 null(air)?
		}
		return sample.getType()==check.getType () && checkName(sample , check) && checkLore(sample, check);
	}

	@SuppressWarnings("deprecation")
	public static void addDurability(ItemStack item, short delta) {
		if (ItemUtil.isAir(item)) {
			return;
		}
		short durability = item.getDurability();
		short max_durability = item.getType().getMaxDurability();
		short final_durability = (short) MathUtil.bound(0, durability - delta, max_durability);
	
		item.setDurability(final_durability);
	}
	
	public static void suffix(ItemStack item, Attribute type, String name, double amount, Operation op, EquipmentSlot slot){
		if(SuitUtils.anyNull(item, type, name, op)) return;
		
		ItemMeta meta = item.getItemMeta();
		if(slot!=null){
			meta.addAttributeModifier(type, new AttributeModifier(UUID.randomUUID(), name, amount, op, slot));
		}else{ //slot null = apply to all slots
			meta.addAttributeModifier(type, new AttributeModifier(name, amount, op));
		}
		item.setItemMeta(meta);
	}

	public static void suffix(ItemStack item, Attribute type, double amount) {
		suffix(item, type, type.name(), amount, Operation.ADD_NUMBER, null);
	}
	
	public static void suffix(ItemStack item, Attribute type, double amount, EquipmentSlot slot) {
		suffix(item, type, type.name(), amount, Operation.ADD_NUMBER, slot);
	}

	public static boolean isAir(ItemStack item){
		return item==null || item.getType() == Material.AIR;
	}
	
}
