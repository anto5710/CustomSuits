package gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI;


import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomEntities;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.PlayerEffect;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.SuitSettings;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.InventoryUtil;
import gmail.anto5710.mcp.customsuits.Utils.ItemUtil;
import gmail.anto5710.mcp.customsuits.Utils.MathUtil;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;


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
				int a = e.isLeftClick()? 1:-1;
				if (e.isShiftClick()) a *= Values.SuitMaxLevel;

				amount = (int) MathUtil.bound(1, amount+a, Values.SuitMaxLevel);
				icon.setAmount(amount);
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void clickSetEntityType(InventoryClickEvent e) {
		if (authenticateAccess(e, type_inventory_name)) {
			ItemStack Clicked = e.getClickedInventory().getItem(e.getSlot());
			if (Clicked == null || Clicked.getItemMeta().getDisplayName() == null) {
				return;
			}
			CustomEntities type = CustomEntities.get(Clicked);
			if (type != null) {
				Player player = (Player) e.getWhoClicked();
				CustomSuitPlugin.handle(player).setSentityType(type);
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void clickvehicle_type(InventoryClickEvent e) {
		if (authenticateAccess(e, vehicle_inventory_name)) {
			ItemStack Clicked = e.getClickedInventory().getItem(e.getSlot());
			if (Clicked == null || Clicked.getItemMeta().getDisplayName() == null) {
				return;
			}
			CustomEntities type = CustomEntities.get(Clicked);
			if (type != null) {
				Player player = (Player) e.getWhoClicked();
				CustomSuitPlugin.handle(player).setVehicleType(type);
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void ClickCommand(InventoryClickEvent e) {
		if(authenticateAccess(e, CommandInventory_name)) {
			Player p = (Player) e.getWhoClicked();
			if (e.getSlot() == 10) {
				CustomSuitPlugin.summonNearestSuit(p);;
				e.setCancelled(true);
				
			} else if (e.getSlot() == 12) {
				openTargetPlayerList(e, p);
				e.setCancelled(true);
				
			} else if (e.getSlot() == 14) {
				CustomSuitPlugin.summonAll(p);
				e.setCancelled(true);
				
			} else if (e.getSlot() == 16) {
				PlayerEffect.spawnFireworks(p);
				e.setCancelled(true);
				
			} else {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void ClickColor(InventoryClickEvent e) {
		if (authenticateAccess(e, true, HelmetColorInventory_name, ChestPlateColorInventory_name,
				LeggingsColorInventory_name, BootsColorInventory_name)) {

			String name = e.getView().getTitle();
			ItemStack item = e.getCurrentItem();
			if (ItemUtil.dyeable(item)) {
				Color color = ((LeatherArmorMeta) item.getItemMeta()).getColor();
				Player player = (Player) e.getWhoClicked();
				SuitSettings hdle = CustomSuitPlugin.handle(player);

				if (name.equals(HelmetColorInventory_name)) {
					hdle.setHelmetColor(color);
				} else if (name.equals(ChestPlateColorInventory_name)) {
					hdle.setChestColor(color);
				} else if (name.equals(LeggingsColorInventory_name)) {
					hdle.setLeggingsColor(color);
				} else if (name.equals(BootsColorInventory_name)) {
					hdle.setBootsColor(color);
				}
				e.setCancelled(true);
			}
		}
	}


	private void openTargetPlayerList(InventoryClickEvent e, Player whoClicked) {
		Inventory list = Bukkit.createInventory(null, 36, list_name);

		for (Player player : whoClicked.getServer().getOnlinePlayers()) {
			ItemStack skull = ItemUtil.decapitate(player.getName());
			list.addItem(skull);
		}
		whoClicked.openInventory(list);
	}

	@EventHandler
	public void clickPlayer(InventoryClickEvent e) {
		if (authenticateAccess(e, true, list_name)) {
			if (e.getCurrentItem() != null) {
				Player WhoClicked = (Player) e.getWhoClicked();
				ItemStack item = e.getCurrentItem();
				Player player = ItemUtil.capitate(item);
				if (player != null) {
					CustomSuitPlugin.handle(player).putTarget(WhoClicked);
				} else {
					WhoClicked.sendMessage(Values.NoSuchEntity);
				}
			}
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void clickArmorGUI(InventoryClickEvent e) {
		if (authenticateAccess(e, true, "[Armor]")) {
			Player player = (Player) e.getWhoClicked();
			
			int slot = e.getSlot();
			boolean toCancel = true;
		
			if (slot == 22) {
				player.openInventory(CustomSuitPlugin.handle(player).helmetequipment);
			} else if (slot == 25) {
				player.openInventory(CustomSuitPlugin.HelmetColorInventory);
			} else if (slot == 31) {
				player.openInventory(CustomSuitPlugin.handle(player).chestequipment);
			} else if (slot == 34) {
				player.openInventory(CustomSuitPlugin.ChestplateColorInventory);
			} else if (slot == 40) {
				player.openInventory(CustomSuitPlugin.handle(player).leggingsequipment);
			} else if (slot == 43) {
				player.openInventory(CustomSuitPlugin.LeggingsColorInventory);
			} else if (slot == 49) {
				player.openInventory(CustomSuitPlugin.handle(player).bootsequipment);
			} else if (slot == 52) {
				player.openInventory(CustomSuitPlugin.BootsColorInventory);				
			} else {
				toCancel = false;
			}
			
			if(toCancel){
				CustomSuitPlugin.refreshInventory(player);
			}
			e.setCancelled(toCancel);
		}
	}

	@EventHandler
	public void clickGUI(PlayerInteractEvent e) {
		Player player = (Player) e.getPlayer();
		if (SuitUtils.isRightClick(e)) {
			if (ItemUtil.checkItem(CustomSuitPlugin.suitremote, InventoryUtil.getMainItem(player))) {
				CustomSuitPlugin.refreshInventory((Player) e.getPlayer());

				e.getPlayer().openInventory(CustomSuitPlugin.handle(player).equipment);
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
				player.openInventory(CustomSuitPlugin.handle(player).command_equipment);
				e.setCancelled(true);
			} else if (slot == 4) {
				CustomSuitPlugin.refreshInventory(player);

				player.openInventory(CustomSuitPlugin.handle(player).armorequipment);
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
		return token != null && e!=null && e.getInventory() != null &&
			   e.getView().getTitle() !=null && e.getView().getTitle().contains(token);
		
	}
	
	public static boolean authenticateAccess(InventoryEvent e, boolean mustBePlayer, String...tokens){
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


