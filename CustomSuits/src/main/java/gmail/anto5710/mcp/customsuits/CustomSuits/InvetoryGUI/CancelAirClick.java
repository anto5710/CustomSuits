package gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;

public class CancelAirClick  implements Listener{
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
		if(event.getClickedInventory()==null){
			return;
		}
		if(event.getClickedInventory().getName()==null){
			return;
		}
		List<String> Inventorys = Arrays.asList("[Settings]" , "[Online Players]"
			,	CustomSuitPlugin.type_inventory.getName()
			,	CustomSuitPlugin.vehicle_inventory.getName()
			,	CustomSuitPlugin.CommandInventory.getName()	);
		
		if (Inventorys.contains(event.getClickedInventory().getName())) {

			if (event.getCurrentItem().getType() == Material.AIR) {
				event.setCurrentItem(new ItemStack(Material.AIR));
				event.setCancelled(true);
				return;
			}
		}
	}
	/**
	 * Cancel Drag Event in SettingInventory
	 * @param event InventoryDragEvent
	 */
	@EventHandler
	public void onDragSettingInventory(InventoryDragEvent event) {
		if(event.getInventory()==null){
			return;
		}
		if(event.getInventory().getName()==null){
			return;
		}
		if (event.getInventory().getName().contains("[Settings]")) {
			
			event.setCancelled(true);
			return;
		}
	}
	/**
	 * Cancel Drag Event in OnlinePlayerList Invnetory
	 * @param event InventoryDragEvent
	 */
	@EventHandler
	public void onDragPlayerList(InventoryDragEvent event) {
		if(event.getInventory()==null){
			return;
		}
		if(event.getInventory().getName()==null){
			return;
		}
		if (event.getInventory().getName().contains("[Online Players]")) {
			
			event.setCancelled(true);
			return;
		}
	}
	/**
	 * Cancel InventoryClickEvent in Armor Setting Inventory if Player clicked Air
	 * @param event InventoryClickEvent
	 */
	@EventHandler
	public void onClickAirInArmorInventory(InventoryClickEvent event) {
		if(event.getClickedInventory()==null){
			return;
		}
		if(event.getClickedInventory().getName()==null){
			return;
		}
		if (event.getClickedInventory().getName().contains("[Armor]")) {
				if(!Arrays.asList(0 , 1 ,2 , 3 , 4 , 5 , 6 , 7 ,8,19 , 28 , 37 , 46 ,29).contains(event.getSlot())){
					if(event.getCurrentItem().getType() == Material.AIR){
						event.setCancelled(true);
				}
			}
		}
	}
	/**
	 * Cancel Drag Event in Armor Setting Invnetory
	 * @param event InventoryDragEvent
	 */
	@EventHandler
	public void onDragArmorSettingInventory(InventoryDragEvent event) {
		if(event.getInventory()==null){
			return;
		}
		if(event.getInventory().getName()==null){
			return;
		}
		if (event.getInventory().getName().contains("[Armor]")) {
				
				event.setCancelled(true);
			}
		}
	/**
	 * Cancel InventoryClickEvent in ArmorColorSettingInventory if player clicked Air
	 * @param event InventoryClickEvent
	 */
	@EventHandler
	public void onClickAirInArmorColorInventorys(InventoryClickEvent event) {
		if(event.getClickedInventory()==null){
			return;
		}
		if(event.getClickedInventory().getName()==null){
			return;
		}
		List<String>names = Arrays.asList(CustomSuitPlugin.HelmetColorInventory.getName()
			,	CustomSuitPlugin.ChestPlateColorInventory.getName()
			,	CustomSuitPlugin.LeggingsColorInventory.getName()
			,	CustomSuitPlugin.BootsColorInventory.getName()
				);
		if (names.contains(event.getClickedInventory().getName())) {
			
					if(event.getCurrentItem().getType() == Material.AIR){
						event.setCancelled(true);
					}
				}
			}
	/**
	 * Cancel Drag Event in Armor Setting Invnetory
	 * @param event InventoryDragEvent
	 */
	@EventHandler
	public void onDragColorArmorInventory(InventoryDragEvent event) {
		if(event.getInventory()==null){
			return;
		}
		if(event.getInventory().getName()==null){
			return;
		}
		List<String>names = Arrays.asList(CustomSuitPlugin.HelmetColorInventory.getName()
			,	CustomSuitPlugin.ChestPlateColorInventory.getName()
			,	CustomSuitPlugin.LeggingsColorInventory.getName()
			,	CustomSuitPlugin.BootsColorInventory.getName()
				);
		if (names.contains(event.getInventory().getName())) {
			
			event.setCancelled(true);
		}
	}
	/**
	 * Cancel InventoryClickEvent in EntityTypeInventory if player clicked Air
	 * @param event InventoryClickEvent
	 */
	@EventHandler
	public void onClickAirInEntityTypeInventory(InventoryDragEvent event) {
		if(event.getInventory()==null){
			return;
		}
		if(event.getInventory().getName()==null){
			return;
		}
		List<String>names = Arrays.asList(CustomSuitPlugin.type_inventory.getName(),CustomSuitPlugin.vehicle_inventory.getName());
		if (names.contains(event.getInventory().getName())) {
			event.setCancelled(true);
		}
	}
	/**
	 * Cancel Drag Event in Command Invnetory
	 * @param event InventoryDragEvent
	 */
	@EventHandler
	public void onDragCommandInventory(InventoryDragEvent event) {
		if(event.getInventory()==null){
			return;
		}
		if(event.getInventory().getName()==null){
			return;
		}
		
		if(CheckInventory(event.getInventory(), CustomSuitPlugin.CommandInventory)) {
			
						event.setCancelled(true);
				}
			}
	
	/**
	 * 
	 * @param inventory Inventory
	 * @param check Inventory to check
	 * @return is Inventory's name equals check Inventory's
	 */
	public static boolean CheckInventory(Inventory inventory , Inventory check){
		
		return inventory.getName().equals(check.getName());
	}
		
}
