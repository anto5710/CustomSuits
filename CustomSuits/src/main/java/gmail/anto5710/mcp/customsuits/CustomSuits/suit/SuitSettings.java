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
		reinitUInven();
		setSentityType(CustomEntities.WARRIOR);
		setVehicleType(CustomEntities.NONE);
	}
	
	public void reinitUInven(){
		if(command_equipment==null){
			command_equipment = CustomSuitPlugin.copyCommandGUI(p, Inventories.commandCenter);
		}
		if(main==null){
			main = CustomSuitPlugin.copyInven(p, Inventories.main, Inventories.maininventory_name);
		}
		if(armor==null){
			armor = CustomSuitPlugin.copyInven(p, Inventories.armorinventory, Inventories.armorinventory_name);
		}
		if(helmetEnchants==null){
			helmetEnchants = CustomSuitPlugin.copyInven(p, Inventories.helmetinventory, Inventories.helmetinventory_name);
		}
		if(chestEnchants==null){
			chestEnchants = CustomSuitPlugin.copyInven(p, Inventories.chestinventory, Inventories.chestinventory_name);
		}
		if(leggingsEnchants==null){
			leggingsEnchants = CustomSuitPlugin.copyInven(p, Inventories.leggingsinventory, Inventories.leggingsinventory_name);
		}
		if(bootsEnchants==null){
			bootsEnchants = CustomSuitPlugin.copyInven(p, Inventories.bootsinventory, Inventories.bootsinventory_name);
		}
		p.updateInventory();
	}
	
	@SuppressWarnings("unused")
	private void resetUInven(){
		command_equipment = CustomSuitPlugin.copyCommandGUI(p, Inventories.commandCenter);
		main = CustomSuitPlugin.copyInven(p, Inventories.main, Inventories.maininventory_name);
		armor = CustomSuitPlugin.copyInven(p, Inventories.armorinventory, Inventories.armorinventory_name);
		helmetEnchants = CustomSuitPlugin.copyInven(p, Inventories.helmetinventory, Inventories.helmetinventory_name);
		chestEnchants = CustomSuitPlugin.copyInven(p, Inventories.chestinventory, Inventories.chestinventory_name);
		leggingsEnchants = CustomSuitPlugin.copyInven(p, Inventories.leggingsinventory, Inventories.leggingsinventory_name);
		bootsEnchants = CustomSuitPlugin.copyInven(p, Inventories.bootsinventory, Inventories.bootsinventory_name);
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
