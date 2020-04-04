package gmail.anto5710.mcp.customsuits.CustomSuits.suit.settings;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import javax.annotation.Nonnull;


import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI.Inventories;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomEntities;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.items.Enchant;
import gmail.anto5710.mcp.customsuits.Utils.items.InventoryUtil;
import gmail.anto5710.mcp.customsuits.Utils.items.ItemUtil;

public class SuitIUISetting{
	
	final Player p;
	private CustomEntities sentity;
	private CustomEntities vehicle;
	
	private Queue<LivingEntity> targets = new ArrayBlockingQueue<>(20);
	private int summonAmount = 1;
	private int level = 1;

	public SuitIUISetting(Player p) {
		this.p = p;
		reinitIUI(false);
		setSentityType(CustomEntities.WARRIOR);
		setVehicleType(CustomEntities.NONE);
		dyeChestplate(Color.RED);
		dyeBoots(Color.RED);
	}
	
	public Inventory helmetEnchants, chestEnchants, leggingsEnchants, bootsEnchants, mainitemEnchants, offitemEnchants;
	public Inventory command_equipment;

	public Inventory main;
	public Inventory armor;
	
	public void reinitIUI(boolean forced){
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
		if(mainitemEnchants==null || forced){
			mainitemEnchants = InventoryUtil.copy(Inventories.mainIteminventory, p, Inventories.mainIteminventory_name);
		}
		if(offitemEnchants==null || forced){
			offitemEnchants = InventoryUtil.copy(Inventories.offIteminventory, p, Inventories.offIteminventory_name);
		}
		p.updateInventory();
	}
	
	public void applyModifiers(){
		modify(mainitem(), helmetEnchants);
		modify(chestplate(), chestEnchants);
		modify(leggings(), leggingsEnchants);
		modify(boots(), bootsEnchants);
		modify(offitem(), mainitemEnchants);
		modify(mainitem(), offitemEnchants);
	}
	
	private void modify(ItemStack item, Inventory enchantInven){
		if (!ItemUtil.isAir(item) && enchantInven != null) {
			ItemUtil.name(item, ChatColor.AQUA + Values.SuitName + Values.SuitInforegex + level());
		
			int level = (int) Math.sqrt(level());
			enchantInven.forEach(book ->{ if(book!=null) Enchant.exscribe(item, book, level);});
		}
	}

	private void updatePlayerIcon(){ // for Party Protocol
		ItemStack head = ItemUtil.decapitate(p.getName());
		ItemUtil.name(head, ItemUtil.getName(command_equipment.getItem(14)));
		command_equipment.setItem(14, head);
		p.updateInventory();
	}
	
	public void setSentityType(CustomEntities type) {
		sentity = type;
		main.setItem(18, type.getIcon());
		p.updateInventory();
	}
	
	public void setVehicleType(CustomEntities type) {
		vehicle = type;
		main.setItem(22, type.getIcon());
		p.updateInventory();
	}
	
	public void updateColorIcons(){
		updateColorIcon(25, helmet());
		updateColorIcon(34, chestplate());
		updateColorIcon(43, leggings());
		updateColorIcon(52, boots());
	}
	
	private static ItemStack indyeable = ItemUtil.createWithName(Material.CAULDRON, ChatColor.BLACK + "Non-Dyeable"); 
	
	private void updateColorIcon(int slot, ItemStack item) {
		ItemStack icon;
		if(ItemUtil.dyeable(item)){
			icon = new ItemStack(item.getType());
			ItemUtil.dye(icon, ItemUtil.extractColor(item));
		}else{
			icon = indyeable;
		}
		armor.setItem(slot, icon);
		p.updateInventory();
	}
	
	private void dye(ItemStack item, Color color){
		if(ItemUtil.dyeable(item)){
			ItemUtil.dye(item, color);
		}
		updateColorIcons();
	}
	
	public void dyeHelmet(@Nonnull Color color) {
		dye(helmet(), color);
	}
	
	public void dyeChestplate(@Nonnull Color color) {
		dye(chestplate(), color);
	}
	
	public void dyeLeggings(@Nonnull Color color) {
		dye(leggings(), color);
	}
	
	public void dyeBoots(@Nonnull Color color) {
		dye(boots(), color);
	}	
	
	public ItemStack[] getArmors(){
		ItemStack[] armorset = {boots().clone(), leggings().clone(), chestplate().clone(), helmet().clone()};
		return armorset;
	}
	
	public ItemStack getMainItem(){
		return mainitem().clone();
	}
	public ItemStack getOffItem(){
		return offitem().clone();
	}
	protected ItemStack helmet() {
		return armor.getItem(19);
	}	
	protected ItemStack chestplate() {
		return armor.getItem(28);
	}
	protected ItemStack leggings() {
		return armor.getItem(37);
	}
	protected ItemStack boots() {
		return armor.getItem(46);
	}
	protected ItemStack mainitem() {
		return armor.getItem(29);
	}
	protected ItemStack offitem() {
		return armor.getItem(27);
	}

	public int level(){
		level(main.getItem(8).getAmount());
		return level;
	}
	
	public void level(int level){
		this.level = Math.max(1, level);
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

	public CustomEntities getSentity() {
		return sentity;
	}
	
	public CustomEntities getVehicle() {
		return vehicle;
	}

	public float flyingSpeed() {
		return (float) (0.1 * (1 + Math.sqrt(level())/27));
	}
}
