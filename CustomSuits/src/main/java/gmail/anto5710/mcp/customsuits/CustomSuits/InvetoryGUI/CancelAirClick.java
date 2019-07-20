package gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Sets;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;

public class CancelAirClick extends InventoryNames implements Listener{
	CustomSuitPlugin plugin;

	public CancelAirClick(CustomSuitPlugin plugin) {
		this.plugin = plugin;
	}
	/**
	 * Cancel InventoryClickEvenet if player clicked air
	 * @param event InventoryClickEvent
	 */
	@EventHandler
	public void onClickAir_in_Inventorys(InventoryClickEvent event) {	
		if(SuitInventoryGUI.authenticateAccess(event, true, 
			inventory_name, list_name, type_inventory_name, vehicle_inventory_name, CommandInventory_name)){
			if (event.getCurrentItem().getType() == Material.AIR) {
				event.setCurrentItem(new ItemStack(Material.AIR));
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
		if(SuitInventoryGUI.authenticateAccess(event, inventory_name)){
			event.setCancelled(true);
		}
	}
	/**
	 * Cancel Drag Event in OnlinePlayerList Invnetory
	 * @param event InventoryDragEvent
	 */
	@EventHandler
	public void onDragPlayerList(InventoryDragEvent event) {
		if(SuitInventoryGUI.authenticateAccess(event, list_name)){
			event.setCancelled(true);
		}
	}
	/**
	 * Cancel InventoryClickEvent in Armor Setting Inventory if Player clicked Air
	 * @param event InventoryClickEvent
	 */
	@EventHandler
	public void onClickAirInArmorInventory(InventoryClickEvent event) {
		if(SuitInventoryGUI.authenticateAccess(event, armorinventory_name)) {
				if(!Sets.newHashSet(0, 1, 2, 3, 4, 5, 6, 7, 8, 19, 28, 37, 46, 29).contains(event.getSlot()) && 
						event.getCurrentItem().getType() == Material.AIR){
					event.setCancelled(true);
			}
		}
	}
	/**
	 * Cancel Drag Event in Armor Setting Invnetory
	 * @param event InventoryDragEvent
	 */
	@EventHandler
	public void onDragArmorSettingInventory(InventoryDragEvent event) {
		if(SuitInventoryGUI.authenticateAccess(event, armorinventory_name)){
			event.setCancelled(true);
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
			event.getCurrentItem().getType() == Material.AIR){
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
				LeggingsColorInventory_name, BootsColorInventory_name)){		
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
		if(!SuitInventoryGUI.authenticateAccess(event, CommandInventory_name)) return;
	}

}
