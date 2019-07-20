package gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI;


import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.PlayerEffect;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.MathUtils;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.google.common.collect.Sets;

public class SuitInventoryGUI extends InventoryNames implements Listener {

	CustomSuitPlugin plugin;

	public SuitInventoryGUI(CustomSuitPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void clickExpOnInventory(InventoryClickEvent e) {
		if (authenticateAccess(e, inventory_name)) {
			Inventory i = e.getInventory();
			ItemStack icon = i.getItem(8);

			if (icon != null && icon.getType() == Material.EXPERIENCE_BOTTLE && e.getSlot() == 8) {
				int amount = icon.getAmount();
				if (e.isLeftClick()) {
					if (amount >= 64) {
						amount = 1;
					} else {
						amount ++;
					}
				} else if (e.isRightClick() && amount > 1) {
					amount--;
				}
				icon.setAmount(amount);
				e.setCancelled(true);
			}
		}
	}
	

	
	@EventHandler
	public void ClickSetEntityType(InventoryClickEvent e){
		if(authenticateAccess(e, type_inventory_name)){
			ItemStack Clicked = e.getClickedInventory().getItem(e.getSlot());
			if(Clicked == null){
				return;
			}
			if(Clicked.getItemMeta().getDisplayName() ==null){
				return;
			}
			String name = 	ChatColor.stripColor(Clicked.getItemMeta().getDisplayName());
			if(CustomSuitPlugin.entity_Icon_Map.containsKey(name)){
				CustomSuitPlugin.Type_Map.put((Player) e.getWhoClicked(),name);
				Player player = (Player)e.getWhoClicked();
				Inventory inventory = CustomSuitPlugin.equipment.get(player);
				inventory.setItem(18, Clicked);
				CustomSuitPlugin.equipment.put(player , inventory);
				e.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void Clickvehicle_type(InventoryClickEvent e){
		if(authenticateAccess(e, vehicle_inventory_name)) {
			ItemStack Clicked = e.getClickedInventory().getItem(e.getSlot());
			if(Clicked == null){
				return;
			}
			if(Clicked.getItemMeta().getDisplayName() ==null){
				return;
			}
			
			String name = 	ChatColor.stripColor(Clicked.getItemMeta().getDisplayName());
			if(CustomSuitPlugin.vehicle_Icon_Map.containsKey(name)){
				Player player = (Player)e.getWhoClicked();
				Inventory inventory = CustomSuitPlugin.equipment.get(player);
				inventory.setItem(22, Clicked);
				CustomSuitPlugin.equipment.put(player , inventory);
				CustomSuitPlugin.vehicle_map.put((Player) e.getWhoClicked(),name);
				e.setCancelled(true);
			}
		}
	}
	private String getKey(String name) {
		Iterator<String>iterator = CustomSuitPlugin.entity_Icon_Map.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			if(key.endsWith(name)){
				return key;
			}
		}
		return null;
	}

	@EventHandler
	public void ClickCommand(InventoryClickEvent e) {
		if(authenticateAccess(e, CommandInventory_name)) {
			Player p = (Player) e.getWhoClicked();
			if (e.getSlot() == 10) {
				CustomSuitPlugin.runSpawn(p);
				e.setCancelled(true);
				
			} else if (e.getSlot() == 12) {
				OpenList(e, p);
				e.setCancelled(true);
				
			} else if (e.getSlot() == 14) {
				CustomSuitPlugin.spawnall(p);
				e.setCancelled(true);
				
			} else if (e.getSlot() == 16) {
				PlayerEffect.spawnfireworks(p);
				e.setCancelled(true);
				
			} else {
				e.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void ClickColor(InventoryClickEvent e) {
		if(authenticateAccess(e, true, 
				HelmetColorInventory_name, ChestPlateColorInventory_name, 
				LeggingsColorInventory_name, BootsColorInventory_name)){
		
				String name = e.getView().getTitle();
				ItemStack item = e.getCurrentItem();
				if(Arrays.asList(Material.LEATHER_HELMET , Material.LEATHER_CHESTPLATE , 
						Material.LEATHER_LEGGINGS , Material.LEATHER_BOOTS).contains(item.getType())){
					Color color = ((LeatherArmorMeta)item.getItemMeta()).getColor();
					Player player = (Player) e.getWhoClicked();
				
					
					if(name.equals(HelmetColorInventory_name)){
						CustomSuitPlugin.HelmetColorMap.put(player,color);
						
					}else if(name.equals(ChestPlateColorInventory_name)){
						CustomSuitPlugin.ChestPlatecolorMap.put(player,color);
							
					}else if(name.equals(LeggingsColorInventory_name)){
						CustomSuitPlugin.LeggingsColorMap.put(player,color);
					
					}else if(name.equals(BootsColorInventory_name)){
						CustomSuitPlugin.BootsColorMap.put(player,color);
						
					}
					CustomSuitPlugin.resetColorIcon(item , player);
					e.setCancelled(true);
				}
			}
	}


	private void OpenList(InventoryClickEvent e, Player whoClicked) {
		Inventory list = Bukkit.createInventory(null, 36, list_name);

		for (Player player : whoClicked.getServer().getOnlinePlayers()) {
			ItemStack skull = CustomSuitPlugin.Head(player.getName());
			list.addItem(skull);

		}
		
		whoClicked.openInventory(list);
	}

	@EventHandler
	public void clickPlayer(InventoryClickEvent e) {
		if(authenticateAccess(e, true, list_name)) {
			if (e.getCurrentItem()!=null) {
					Player WhoClicked = (Player) e.getWhoClicked();
					ItemStack item = e.getCurrentItem();
					if(item.getType()==Material.SKELETON_SKULL){
					SkullMeta skull = (SkullMeta) item.getItemMeta();
					String name =skull.getOwner();
					Player player = Bukkit.getPlayer(name);
					if(player!=null){
						CustomSuitPlugin.putTarget(WhoClicked, player);
					}else {
						WhoClicked.sendMessage(Values.NoSuchEntity);
						
					}
				}
			}
			e.setCancelled(true);
		}
	}

//	
//	@EventHandler
//	public void clickHelmetColor(InventoryClickEvent e) {
//		if (authenticateAccess(e, "[HelmetColor]") && e.getWhoClicked() instanceof Player) {
//			Player p = (Player) e.getWhoClicked();
//			
//			e.getCurrentItem().get;
//			
//		}
//	}

	@EventHandler
	public void clickHelmet(InventoryClickEvent e) {
		if (authenticateAccess(e, true, "[Armor]")) {
			Player player = (Player) e.getWhoClicked();
			CustomSuitPlugin.refreshInventory(player);

			int slot = e.getSlot();
			if (slot == 22) {
				player.openInventory(CustomSuitPlugin.helmetequipment.get(player));
				e.setCancelled(true);
			} else if (slot == 25) {
				player.openInventory(CustomSuitPlugin.HelmetColorInventory);
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void clickChestplate(InventoryClickEvent e) {
		if (authenticateAccess(e, true, "[Armor]")) {
			Player player = (Player) e.getWhoClicked();
			CustomSuitPlugin.refreshInventory(player);

			int slot = e.getSlot();
			if (slot == 31) {
				player.openInventory(CustomSuitPlugin.chestequipment.get(player));
				e.setCancelled(true);
			} else if (slot == 34) {
				player.openInventory(CustomSuitPlugin.ChestplateColorInventory);
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void clickLeggings(InventoryClickEvent e) {
		if (authenticateAccess(e, true, "[Armor]")) {
			Player player = (Player) e.getWhoClicked();
			CustomSuitPlugin.refreshInventory(player);
			
			int slot = e.getSlot();
			if (slot == 40) {
				player.openInventory(CustomSuitPlugin.leggingsequipment.get(player));
				e.setCancelled(true);
			} else if (slot == 43) {
				player.openInventory(CustomSuitPlugin.LeggingsColorInventory);
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void clickBoots(InventoryClickEvent e) {
		if (authenticateAccess(e, true, "[Armor]")) {
			Player player = (Player) e.getWhoClicked();
			CustomSuitPlugin.refreshInventory((Player) e.getWhoClicked());

			int slot = e.getSlot();
			if (slot == 49) {
				player.openInventory(CustomSuitPlugin.bootsequipment.get(player));
				e.setCancelled(true);
			} else if (slot == 52) {
				player.openInventory(CustomSuitPlugin.BootsColorInventory);
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void clickGUI(PlayerInteractEvent e) {
		Player player = (Player) e.getPlayer();
		if (SuitUtils.isRightClick(e)) {
			// Material m = CustomSuitPlugin.suitremote.getType();
			if (SuitUtils.checkItem(CustomSuitPlugin.suitremote, SuitUtils.getHoldingItem(player))) {
				CustomSuitPlugin.refreshInventory((Player) e.getPlayer());

				e.getPlayer().openInventory(CustomSuitPlugin.equipment.get(e.getPlayer()));
				e.setCancelled(true);
			}
		}
	}	

	@EventHandler
	public void clickMenu(InventoryClickEvent e) {
		if (authenticateAccess(e, "[Settings]")) {
			Player player = (Player) e.getWhoClicked();

			int slot = e.getSlot();
			if (slot == 0) {
				player.openInventory(CustomSuitPlugin.command_equipment.get(player));
				e.setCancelled(true);
			} else if (slot == 4) {
				CustomSuitPlugin.refreshInventory(player);

				player.openInventory(CustomSuitPlugin.armorequipment.get(player));
				e.setCancelled(true);
			}else if(slot==18){
				CustomSuitPlugin.refreshInventory(player);

				player.openInventory(CustomSuitPlugin.type_inventory);
				e.setCancelled(true);
			}else if(slot == 22){
				CustomSuitPlugin.refreshInventory(player);

				player.openInventory(CustomSuitPlugin.vehicle_inventory);
				e.setCancelled(true);
			}
			
			e.setCancelled(true);
		}
	}

	
	public static boolean authenticateAccess(InventoryEvent e, String token){
//		return true;
		return token != null && e!=null && e.getInventory() != null &&
			   e.getView().getTitle() !=null && e.getView().getTitle().contains(token);
		
	}
	
	public static boolean authenticateAccess(InventoryEvent e, boolean mustBePlayer, String...tokens){
//		return true;
		if (tokens == null || e==null || e.getInventory() == null){
			return false;
		}
		if(mustBePlayer){
			if(e instanceof InventoryClickEvent && !(((InventoryClickEvent) e).getWhoClicked() instanceof Player)) return false;
			
			if(e instanceof InventoryDragEvent && !(((InventoryDragEvent) e).getWhoClicked() instanceof Player)) return false;
		}
		
		String title = e.getView().getTitle();
		for(String token: tokens){
			if(title.contains(token)) return true;
		}
		return false;				  
	}
}
