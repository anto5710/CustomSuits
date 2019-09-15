package gmail.anto5710.mcp.customsuits.Utils;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryUtil {

	public static void equip(LivingEntity lentity, ItemStack helemt, ItemStack chestplate, ItemStack leggings, ItemStack boots){
		if(lentity==null) return;
	
		lentity.getEquipment().setHelmet(helemt);
		lentity.getEquipment().setChestplate(chestplate);
		lentity.getEquipment().setLeggings(leggings);
		lentity.getEquipment().setBoots(boots);
		if(lentity.getType()==EntityType.PLAYER) ((Player)lentity).updateInventory();
	}
	
	public static void give(Player player, ItemStack item){
		player.getInventory().addItem(item); 
		player.updateInventory();
	}
	
	public static void give(Player player, Material type, int amount){
		give(player, new ItemStack(type, amount));
	}
	
	public static void give(Player player, Material type){
		give(player, type, 1);
	}

	public static boolean sufficeMaterial(Player player, Material material, int amount){
		return InventoryUtil.sufficeItem(player, new ItemStack(material, amount), 1);
	}

	public static boolean sufficeItem(Player player, ItemStack itemStack, int amount) {
		Inventory inventory = player.getInventory();
		boolean suffice = inventory.containsAtLeast(itemStack, amount); 
		if (suffice){
			inventory.removeItem(itemStack);
			player.updateInventory();
		}
		return suffice;
	}
	
	public static void replete(@Nonnull Player player, @Nonnull ItemStack item){
		if(!player.getInventory().contains(item)){
			give(player, item);
		}
	}

	public static void replete(@Nonnull Player player, Material type, int amount){
		replete(player, new ItemStack(type, amount));
	}
	
	public static void replete(@Nonnull Player player, Material type){
		replete(player, type, 1);
	}
	
	public static void removeAll(@Nonnull Player player, @Nonnull ItemStack item){
		PlayerInventory inven = player.getInventory();
		while(inven.contains(item)){
			inven.remove(item);
		}
		player.updateInventory();
	}

	public static boolean sufficeMaterial(Player player, Material material) {
		return sufficeMaterial(player, material, 1);
	}

	public static boolean sufficeItem(Player player, ItemStack itemStack){
		return sufficeItem(player, itemStack, 1);
	}

	public static void removeMainItem(Player player){
		ItemStack ItemInHand = InventoryUtil.getMainItem(player);
		ItemInHand.setAmount(ItemInHand.getAmount()-1);
		player.getInventory().setItemInMainHand(ItemInHand);
		player.updateInventory();
	}

	public static int mainSlot(@Nonnull Player p){
		return p.getInventory().getHeldItemSlot();
	}
	
	public static int offSlot(){
		return 45;
	}
	
	public static boolean inMainHand(Player player, ItemStack sample) {
		return !SuitUtils.anyNull(player, sample) && ItemUtil.checkItem(sample, InventoryUtil.getMainItem(player));
	}
	
	public static boolean inOffHand(Player player, ItemStack sample){
		return !SuitUtils.anyNull(player, sample) && ItemUtil.checkItem(sample, InventoryUtil.getOffItem(player));
	}

	public static boolean inAnyHand(Player player, ItemStack sample) {
		return inMainHand(player, sample) || InventoryUtil.inOffHand(player, sample);
	}

	public static boolean emptyHand(Player player){
		ItemStack item = InventoryUtil.getMainItem(player);
		return ItemUtil.isAir(item);
	}

	public static ItemStack getMainItem(Player player){
		return player.getInventory().getItemInMainHand();
	}

	public static ItemStack getOffItem(Player player){
		return player.getInventory().getItemInOffHand();
	}

	public static void setMainItem(Player player, ItemStack item){
		player.getInventory().setItemInMainHand(item);
	}

	public static void setOffItem(Player player, ItemStack item) {
		player.getInventory().setItemInOffHand(item);	
	}

	public static boolean hasArmor(LivingEntity lentity) {
		ItemStack[]armorContents = lentity.getEquipment().getArmorContents();
		int emptySlots = 0;
		for (int index = 0; index < armorContents.length; index++) {
			ItemStack arm = armorContents[index]; 
			if (ItemUtil.isAir(arm)) emptySlots++; 
		}
		return emptySlots < armorContents.length;
	}
}
