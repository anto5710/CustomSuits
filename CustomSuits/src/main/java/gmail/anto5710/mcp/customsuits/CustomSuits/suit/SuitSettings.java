package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import org.bukkit.Color;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI.InventoryNames;
import gmail.anto5710.mcp.customsuits.Utils.ItemUtil;

public class SuitSettings {	
	final Player p;
	private CustomEntities sentity = CustomEntities.WARRIOR;
	private CustomEntities vehicle = null;
	
	private Queue<LivingEntity> targets = new ArrayBlockingQueue<>(20);
	private int summonAmount = 1;
	
	public Inventory command_equipment, handequipment;
	public Inventory helmetequipment;
	public Inventory chestequipment;
	public Inventory leggingsequipment;
	public Inventory bootsequipment;
	public Inventory equipment;
	public Inventory armorequipment;
	private Color helmetColor = Color.MAROON, chestColor = Color.MAROON, leggingsColor = Color.MAROON, bootsColor = Color.MAROON;

	public SuitSettings(Player p) {
		this.p = p;
		reinitUInv();
	}
	
	public void reinitUInv(){
		if(command_equipment==null){
			command_equipment = CustomSuitPlugin.copyCommandGUI(p, CustomSuitPlugin.commandInventory);
		}
		if(equipment==null){
			equipment = CustomSuitPlugin.copyInven(p, CustomSuitPlugin.inventory, InventoryNames.inventory_name);
		}
		if(armorequipment==null){
			armorequipment = CustomSuitPlugin.copyInven(p, CustomSuitPlugin.armorinventory, InventoryNames.armorinventory_name);
		}
		if(helmetequipment==null){
			helmetequipment = CustomSuitPlugin.copyInven(p, CustomSuitPlugin.helmetinventory, InventoryNames.helmetinventory_name);
		}
		if(chestequipment==null){
			chestequipment = CustomSuitPlugin.copyInven(p, CustomSuitPlugin.chestinventory, InventoryNames.chestinventory_name);
		}
		if(leggingsequipment==null){
			leggingsequipment = CustomSuitPlugin.copyInven(p, CustomSuitPlugin.leggingsinventory, InventoryNames.leggingsinventory_name);
		}
		if(bootsequipment==null){
			bootsequipment = CustomSuitPlugin.copyInven(p, CustomSuitPlugin.bootsinventory, InventoryNames.bootsinventory_name);
		}
		p.updateInventory();
	}
	
	private void resetUInv(){
		command_equipment = CustomSuitPlugin.copyCommandGUI(p, CustomSuitPlugin.commandInventory);
		equipment = CustomSuitPlugin.copyInven(p, CustomSuitPlugin.inventory, InventoryNames.inventory_name);
		armorequipment = CustomSuitPlugin.copyInven(p, CustomSuitPlugin.armorinventory, InventoryNames.armorinventory_name);
		helmetequipment = CustomSuitPlugin.copyInven(p, CustomSuitPlugin.helmetinventory, InventoryNames.helmetinventory_name);
		chestequipment = CustomSuitPlugin.copyInven(p, CustomSuitPlugin.chestinventory, InventoryNames.chestinventory_name);
		leggingsequipment = CustomSuitPlugin.copyInven(p, CustomSuitPlugin.leggingsinventory, InventoryNames.leggingsinventory_name);
		bootsequipment = CustomSuitPlugin.copyInven(p, CustomSuitPlugin.bootsinventory, InventoryNames.bootsinventory_name);
	}
	
	private void updateColorIcon(int slot, Color color) {
		ItemStack itemstack = armorequipment.getItem(slot);
		ItemUtil.dye(itemstack, color);
		armorequipment.setItem(slot, itemstack);
		p.updateInventory();
	}
	
	public void updateColorIcons(){
		updateColorIcon(25, helmetColor);
		updateColorIcon(34, chestColor);
		updateColorIcon(43, leggingsColor);
		updateColorIcon(52, bootsColor);
	}
	
	public int level(){
		return Math.max(1, equipment.getItem(8).getAmount());
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
		if (type != null) {
			sentity = type;
			equipment.setItem(18, type.getIcon());
			p.updateInventory();
		}
	}
	
	public void setVehicleType(CustomEntities type) {
		vehicle = type;
		equipment.setItem(22, type.getIcon());
		p.updateInventory();
	}
	
	public boolean asessSentityType(String name){
		CustomEntities type = CustomEntities.get(name);
		boolean legit = type != null && type != CustomEntities.NONE;
		if(legit){
			setSentityType(type);
		}
		return legit;
	}
	
	public boolean assessVehicleType(String name){
		CustomEntities type = CustomEntities.get(name);
		if (type != null) {
			setVehicleType(type);
		}
		return type != null;
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
