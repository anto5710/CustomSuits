package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import org.bukkit.Color;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI.Inventories;
import gmail.anto5710.mcp.customsuits.Utils.InventoryUtil;
import gmail.anto5710.mcp.customsuits.Utils.ItemUtil;

public class SuitSettings {	
	final Player p;
	private CustomEntities sentity;
	private CustomEntities vehicle;
	
	private Queue<LivingEntity> targets = new ArrayBlockingQueue<>(20);
	private int summonAmount = 1;
	
	public Inventory command_equipment;
	public Inventory helmetEnchants;
	public Inventory chestEnchants;
	public Inventory leggingsEnchants;
	public Inventory bootsEnchants;
	public Inventory main;
	public Inventory armor;
	private Color helmetColor = Color.MAROON, chestColor = Color.MAROON, leggingsColor = Color.MAROON, bootsColor = Color.MAROON;

	public SuitSettings(Player p) {
		this.p = p;
		reinitUInven(true);
		setSentityType(CustomEntities.WARRIOR);
		setVehicleType(CustomEntities.NONE);
	}
	
	public void reinitUInven(boolean forced){
		if(command_equipment==null || forced){
			command_equipment = InventoryUtil.copy(Inventories.commandCenter, p, Inventories.commandinventory_name + ":" + p.getDisplayName());
			updatePlayerIcon();
		}
		if(main==null || forced){
			main = InventoryUtil.copy(Inventories.main, p, Inventories.maininventory_name);
		}
		if(armor==null || forced){
			armor = InventoryUtil.copy(Inventories.armorinventory, p, Inventories.armorinventory_name);
		}
		if(helmetEnchants==null || forced){
			helmetEnchants = InventoryUtil.copy(Inventories.helmetinventory, p, Inventories.helmetinventory_name);
		}
		if(chestEnchants==null || forced){
			chestEnchants = InventoryUtil.copy(Inventories.chestinventory, p, Inventories.chestinventory_name);
		}
		if(leggingsEnchants==null || forced){
			leggingsEnchants = InventoryUtil.copy(Inventories.leggingsinventory, p, Inventories.leggingsinventory_name);
		}
		if(bootsEnchants==null || forced){
			bootsEnchants = InventoryUtil.copy(Inventories.bootsinventory, p, Inventories.bootsinventory_name);
		}
		p.updateInventory();
	}
	
	private void updateColorIcon(int slot, Color color) {
		ItemStack itemstack = armor.getItem(slot);
		ItemUtil.dye(itemstack, color);
		armor.setItem(slot, itemstack);
		p.updateInventory();
	}
	
	public void updateColorIcons(){
		updateColorIcon(25, helmetColor);
		updateColorIcon(34, chestColor);
		updateColorIcon(43, leggingsColor);
		updateColorIcon(52, bootsColor);
	}
	
	private void updatePlayerIcon(){ // for Party Protocol
		ItemStack head = ItemUtil.decapitate(p.getName());
		ItemUtil.name(head, ItemUtil.getName(command_equipment.getItem(14)));
		command_equipment.setItem(14, head);
	}
	
	public int level(){
		return Math.max(1, main.getItem(8).getAmount());
	}
	
	public LivingEntity getCurrentTarget(){
		return targets.peek();
	}
	
	public boolean isTargetting(Entity entity){
		return targets.contains(entity);
	}
	
	public void putTarget(LivingEntity entity) {
		if(!targets.contains(entity)){
			targets.offer(entity);
		}
	}

	public void removeDeadTargets() {
		targets.removeIf(e->e.isDead());
	}

	public void setSentityType(CustomEntities type) {
		if (type != null && type != CustomEntities.NONE) {
			sentity = type;
			main.setItem(18, type.getIcon());
			p.updateInventory();
		}
	}
	
	public void setVehicleType(CustomEntities type) {
		vehicle = type;
		main.setItem(22, type.getIcon());
		p.updateInventory();
	}
	
	public boolean asessSentityType(String name){
		CustomEntities type = CustomEntities.get(name);
		boolean legit = type != null && type != CustomEntities.NONE;
		if(legit) setSentityType(type);
		
		return legit;
	}
	
	public boolean assessVehicleType(String name){
		CustomEntities type = CustomEntities.get(name);
		boolean legit = type != null;
		if (legit) setVehicleType(type);
		
		return legit;
	}

	public int getCount() {
		return summonAmount;
	}
	
	public void setCount(int spnCnt) {
		summonAmount = Math.max(1, spnCnt);
	}
	
	public void setHelmetColor(Color helmetColor) {
		if(helmetColor!=null){
			this.helmetColor = helmetColor;
			updateColorIcons();
		}
	}
	public void setChestColor(Color chestColor) {
		if(chestColor!=null){
			this.chestColor = chestColor;
			updateColorIcons();
		}
	}
	public void setLeggingsColor(Color leggingsColor) {
		if(leggingsColor!=null){
			this.leggingsColor = leggingsColor;
			updateColorIcons();
		}
	}
	public void setBootsColor(Color bootsColor) {
		if(bootsColor!=null){
			this.bootsColor = bootsColor;
			updateColorIcons();
		}
	}
	
	public Color getHelmetColor() {
		return helmetColor;
	}
	public Color getChestColor() {
		return chestColor;
	}
	public Color getLeggingsColor() {
		return leggingsColor;
	}
	public Color getBootsColor() {
		return bootsColor;
	}
	public CustomEntities getSentity() {
		return sentity;
	}
	public CustomEntities getVehicle() {
		return vehicle;
	}
}
