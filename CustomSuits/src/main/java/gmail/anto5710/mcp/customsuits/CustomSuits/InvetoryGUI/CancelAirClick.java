package gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI;

import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import com.google.common.collect.Sets;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.ItemUtil;

public class CancelAirClick extends Inventories implements Listener{
	CustomSuitPlugin plugin;

	public CancelAirClick(CustomSuitPlugin plugin) {
		this.plugin = plugin;
	}
	
	private boolean withinUpperInv(InventoryClickEvent e, Inventory inv){
		return e.getSlot() < inv.getSize();
	}
	
	private boolean anyWithinUpperInv(InventoryDragEvent e, Inventory inv){
		int size = inv.getSize();
		return e.getInventorySlots().stream().anyMatch(s->  s<size);
	}
	
	/**
	 * Cancel InventoryClickEvenet if player clicked air
	 * @param event InventoryClickEvent
	 */
	@EventHandler
	public void onClickAir_in_Inventorys(InventoryClickEvent event) {	
		if(SuitInventoryGUI.authenticateAccess(event, true, 
			maininventory_name, list_name, type_inventory_name, vehicle_inventory_name, commandinventory_name)){
			if (ItemUtil.isAir(event.getCurrentItem()) && withinUpperInv(event, Inventories.type_inventory)) {
				event.setCancelled(true);
			}
		}
	}
	/**
	 * Cancel Drag Event in SettingInventory
	 * @param event InventoryDragEvent
	 */
	@EventHandler
	public void onDragSettingInventory(InventoryDragEvent event) {
		if(SuitInventoryGUI.authenticateAccess(event, maininventory_name) && anyWithinUpperInv(event, Inventories.main)){
			event.setCancelled(true);
		}
	}
	/**
	 * Cancel Drag Event in OnlinePlayerList Invnetory
	 * @param event InventoryDragEvent
	 */
	@EventHandler
	public void onDragPlayerList(InventoryDragEvent event) {
		if(SuitInventoryGUI.authenticateAccess(event, list_name) && event.getInventorySlots().stream().anyMatch(s->s<36)){
			event.setCancelled(true);
		}
	}
	/**
	 * Cancel InventoryClickEvent in Armor Setting Inventory if Player clicked Air
	 * @param event InventoryClickEvent
	 */
	private static Set<Integer> accessable = Sets.newHashSet(19,28,37,46,29);//Sets.newHashSet(0, 1, 2, 3, 4, 5, 6, 7, 8, 19, 28, 37, 46, 29);
	
	@EventHandler
	public void onClickAirInArmorInventory(InventoryClickEvent event) {
		if(SuitInventoryGUI.authenticateAccess(event, armorinventory_name) && event.getWhoClicked() instanceof Player) {
			Player p = (Player) event.getWhoClicked();
			
			if(event.getClickedInventory().equals(CustomSuitPlugin.handle(p).armor) && !accessable.contains(event.getSlot())) event.setCancelled(true);
		}
	}
	/**
	 * Cancel Drag Event in Armor Setting Invnetory
	 * @param event InventoryDragEvent
	 */
	@EventHandler
	public void onDragArmorSettingInventory(InventoryDragEvent event) {
		if(SuitInventoryGUI.authenticateAccess(event, armorinventory_name) && anyWithinUpperInv(event, Inventories.armorinventory) ){
			if(!accessable.containsAll(event.getInventorySlots())){
				event.setCancelled(true);
			}
		}	
	}
	/**
	 * Cancel InventoryClickEvent in ArmorColorSettingInventory if player clicked Air
	 * @param event InventoryClickEvent
	 */
	@EventHandler
	public void onClickAirInArmorColorInventorys(InventoryClickEvent event) {		
		if(SuitInventoryGUI.authenticateAccess(event, true, HelmetColorInventory_name, ChestPlateColorInventory_name, 
															LeggingsColorInventory_name, BootsColorInventory_name) &&
			ItemUtil.isAir(event.getCurrentItem()) && withinUpperInv(event, Inventories.HelmetColorInventory)){
			
					event.setCancelled(true);
			}
		}
	/**
	 * Cancel Drag Event in Armor Setting Invnetory
	 * @param event InventoryDragEvent
	 */
	@EventHandler
	public void onDragColorArmorInventory(InventoryDragEvent event) {
		if(SuitInventoryGUI.authenticateAccess(event, true, HelmetColorInventory_name, ChestPlateColorInventory_name, 
				LeggingsColorInventory_name, BootsColorInventory_name) && anyWithinUpperInv(event, Inventories.HelmetColorInventory)){		
			event.setCancelled(true);
		}
	}
	/**
	 * Cancel InventoryClickEvent in EntityTypeInventory if player clicked Air
	 * @param event InventoryClickEvent
	 */
	@EventHandler
	public void onClickAirInEntityTypeInventory(InventoryDragEvent event) {
		if(SuitInventoryGUI.authenticateAccess(event, true, type_inventory_name, vehicle_inventory_name)){	
			event.setCancelled(true);
		}
	}
	/**
	 * Cancel Drag Event in Command Invnetory
	 * @param event InventoryDragEvent
	 */
	@EventHandler
	public void onDragCommandInventory(InventoryDragEvent event) {
		if(SuitInventoryGUI.authenticateAccess(event, commandinventory_name) && anyWithinUpperInv(event, Inventories.commandCenter)){
			event.setCancelled(true);
		}	
	}

}
