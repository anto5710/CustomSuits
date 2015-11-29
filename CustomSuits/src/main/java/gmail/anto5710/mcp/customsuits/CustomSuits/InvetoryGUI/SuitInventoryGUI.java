package gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI;


import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.PlayerEffect;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class SuitInventoryGUI implements Listener {

	CustomSuitPlugin plugin;

	public SuitInventoryGUI(CustomSuitPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void clickExpOnInventory(InventoryClickEvent e) {
		if(e.getClickedInventory()==null){
			return;
		}
		if(e.getClickedInventory().getName()==null){
			return;
		}
		if (e.getClickedInventory().getName().contains("[Settings]")) {
			Inventory i = e.getInventory();
			if (e.getInventory().getItem(8).getType() == Material.EXP_BOTTLE) {
				if (e.getSlot() == 8) {
					if(e.isLeftClick()){
					if (e.getInventory().getItem(8).getAmount() == 64) {
						e.getInventory().getItem(8).setAmount(1);
					} else {
						i.getItem(8).setAmount(
								e.getInventory().getItem(8).getAmount() + 1);
					}
					}else if(e.isRightClick()){
						if(e.getInventory().getItem(8).getAmount() >1){
							i.getItem(8).setAmount(
									e.getInventory().getItem(8).getAmount() - 1);
						}
					}
					

					e.setCancelled(true);

				}
			}
		}
	}
	
	
	@EventHandler
	public void ClickSetEntityType(InventoryClickEvent e){
		if(e.getClickedInventory()==null){
			return;
		}
		if(e.getClickedInventory().getName()==null){
			return;
		}
		if (e.getClickedInventory().getName().contains("[EntityType]")) {
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
		if(e.getClickedInventory()==null){
			return;
		}
		if(e.getClickedInventory().getName()==null){
			return;
		}
		if (e.getClickedInventory().getName().contains("[Vehicle_EntityType]")) {
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
		if(e.getClickedInventory()==null){
			return;
		}
		if(e.getClickedInventory().getName()==null){
			return;
		}
		if (e.getClickedInventory().getName().contains("[Command]")) {
			if (e.getSlot() == 10) {
				CustomSuitPlugin.runSpawn((Player) e.getWhoClicked());
				e.setCancelled(true);
			} else if (e.getSlot() == 12) {
				OpenList(e, (Player) e.getWhoClicked());
				e.setCancelled(true);
			} else if (e.getSlot() == 14) {
				CustomSuitPlugin.spawnall((Player) e.getWhoClicked());
				e.setCancelled(true);
			} else if (e.getSlot() == 16) {
				PlayerEffect.spawnfireworks((Player) e.getWhoClicked());
				e.setCancelled(true);
			} else {
				e.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void ClickColor(InventoryClickEvent e) {
		if(e.getClickedInventory()==null){
			return;
		}
		if(e.getClickedInventory().getName()==null){
			return;
		}
		List<String>names = Arrays.asList(CustomSuitPlugin.HelmetColorInventory.getName()
				,	CustomSuitPlugin.ChestPlateColorInventory.getName()
				,	CustomSuitPlugin.LeggingsColorInventory.getName()
				,	CustomSuitPlugin.BootsColorInventory.getName()
					);
			if (names.contains(e.getInventory().getName())) {
				String name = e.getInventory().getName();
				ItemStack item = e.getCurrentItem();
				if(Arrays.asList(Material.LEATHER_HELMET , Material.LEATHER_CHESTPLATE , Material.LEATHER_LEGGINGS , Material.LEATHER_BOOTS).contains(item.getType())){
					Color color = ((LeatherArmorMeta)item.getItemMeta()).getColor();
					Player player = (Player) e.getWhoClicked();
					if(name.equals(CustomSuitPlugin.HelmetColorInventory.getName())){
						CustomSuitPlugin.resetColorIcon(item , player);
						CustomSuitPlugin.HelmetColorMap.put(player,color);
					}else if(name.equals(CustomSuitPlugin.ChestPlateColorInventory.getName())){
						CustomSuitPlugin.resetColorIcon(item , player);
						CustomSuitPlugin.ChestPlatecolorMap.put(player,color);
					}else if(name.equals(CustomSuitPlugin.LeggingsColorInventory.getName())){
						CustomSuitPlugin.resetColorIcon(item , player);
						CustomSuitPlugin.LeggingsColorMap.put(player,color);
					}else if(name.equals(CustomSuitPlugin.BootsColorInventory.getName())){
						CustomSuitPlugin.resetColorIcon(item , player);
						CustomSuitPlugin.BootsColorMap.put(player,color);
					}
					
					e.setCancelled(true);
				}
			}
	}


	private void OpenList(InventoryClickEvent e, Player whoClicked) {
		Inventory list = Bukkit.createInventory(null, 36, "[Online Players]");

		for (Player player : whoClicked.getServer().getOnlinePlayers()) {
			ItemStack skull = CustomSuitPlugin.Head(player.getName());
			list.addItem(skull);

		}
		
		whoClicked.openInventory(list);
	}

	@EventHandler
	public void clickPlayer(InventoryClickEvent e) {
		if(e.getClickedInventory()==null){
			return;
		}
		if(e.getClickedInventory().getName()==null){
			return;
		}
		Player WhoClicked = (Player) e.getWhoClicked();
			if (e.getClickedInventory().getName().contains("[Online Players]")) {
				if (e.getCurrentItem()!=null) {
					ItemStack item = e.getCurrentItem();
					if(item.getType()==Material.SKULL_ITEM){
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

	
	@EventHandler
	public void clickHelmetColor(InventoryClickEvent e) {
		if(e.getClickedInventory()==null){
			return;
		}
		if(e.getClickedInventory().getName()==null){
			return;
		}
		if (e.getClickedInventory().getName().contains("[HelmetColor]")) {
			
		}
	}

	@EventHandler
	public void clickHelmet(InventoryClickEvent e) {
		if(e.getClickedInventory()==null){
			return;
		}
		if(e.getClickedInventory().getName()==null){
			return;
		}
		if (e.getClickedInventory().getName().contains("[Armor]")) {
				Player player = (Player) e.getWhoClicked();
			CustomSuitPlugin.resetInventory((Player) e.getWhoClicked());

				int slot = e.getSlot();
				if (slot == 22) {
					player.openInventory(CustomSuitPlugin.helmetequipment
							.get(player));
					e.setCancelled(true);
				}else if(slot ==25){
					player.openInventory(CustomSuitPlugin.HelmetColorInventory);
					e.setCancelled(true);
				}
			}
	}

	@EventHandler
	public void clickChestplate(InventoryClickEvent e) {
		if(e.getClickedInventory()==null){
			return;
		}
		if(e.getClickedInventory().getName()==null){
			return;
		}
		if (e.getClickedInventory().getName().contains("[Armor]")) {

				Player player = (Player) e.getWhoClicked();
				CustomSuitPlugin.resetInventory((Player) e.getWhoClicked());


				int slot = e.getSlot();
				if (slot == 31) {
					player.openInventory(CustomSuitPlugin.chestequipment
							.get(player));
					e.setCancelled(true);
				}
				else if(slot ==34){
					player.openInventory(CustomSuitPlugin.ChestPlateColorInventory);
					e.setCancelled(true);
				}

			}
	}

	@EventHandler
	public void clickLeggings(InventoryClickEvent e) {
		if(e.getClickedInventory()==null){
			return;
		}
		if(e.getClickedInventory().getName()==null){
			return;
		}
		if (e.getClickedInventory().getName().contains("[Armor]")) {

				Player player = (Player) e.getWhoClicked();
				CustomSuitPlugin.resetInventory((Player) e.getWhoClicked());


				int slot = e.getSlot();
				if (slot == 40) {
					player.openInventory(CustomSuitPlugin.leggingsequipment
							.get(player));
					e.setCancelled(true);
				}
				else if(slot ==43){
					player.openInventory(CustomSuitPlugin.LeggingsColorInventory);
					e.setCancelled(true);
				}
		}
	}
	@EventHandler
	public void clickBoots(InventoryClickEvent e) {
		if(e.getClickedInventory()==null){
			return;
		}
		if(e.getClickedInventory().getName()==null){
			return;
		}
		if (e.getClickedInventory().getName().contains("[Armor]")) {
				Player player = (Player) e.getWhoClicked();
				CustomSuitPlugin.resetInventory((Player) e.getWhoClicked());


				int slot = e.getSlot();
				if (slot == 49) {
					player.openInventory(CustomSuitPlugin.bootsequipment
							.get(player));
					e.setCancelled(true);
				}
				else if(slot ==52){
					player.openInventory(CustomSuitPlugin.BootsColorInventory);
					e.setCancelled(true);
				}

			}
		}

	@EventHandler
	public void clickGUI(PlayerInteractEvent e) {
		Player player = (Player) e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_AIR
				|| e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Material m = CustomSuitPlugin.suitremote.getType();
			if(SuitUtils.CheckItem(CustomSuitPlugin.suitremote,player.getItemInHand())){
						CustomSuitPlugin.resetInventory((Player) e.getPlayer());


						e.getPlayer().openInventory(
								CustomSuitPlugin.equipment.get(e.getPlayer()));
						e.setCancelled(true);
					}
			}
			
		}
	

	@EventHandler
	public void clickMenu(InventoryClickEvent e) {
		if(e.getClickedInventory()==null){
			return;
		}
		if(e.getClickedInventory().getName()==null){
			return;
		}
		if (e.getClickedInventory().getName().contains("[Settings]")) {

			Player player = (Player) e.getWhoClicked();

			int slot = e.getSlot();
			if (slot == 0) {
				player.openInventory(CustomSuitPlugin.command_equipment.get(player));
				e.setCancelled(true);
			} else if (slot == 4) {
				CustomSuitPlugin.resetInventory((Player) e.getWhoClicked());

				e.getWhoClicked().openInventory(
						CustomSuitPlugin.armorequipment.get(e.getWhoClicked()));
				e.setCancelled(true);
			}else if(slot==18){
				CustomSuitPlugin.resetInventory((Player) e.getWhoClicked());

				e.getWhoClicked().openInventory(CustomSuitPlugin.type_inventory);
				e.setCancelled(true);
			}else if(slot == 22){
				CustomSuitPlugin.resetInventory((Player) e.getWhoClicked());

				e.getWhoClicked().openInventory(CustomSuitPlugin.vehicle_inventory);
				e.setCancelled(true);
			}
			
			e.setCancelled(true);
		}
	}

}
