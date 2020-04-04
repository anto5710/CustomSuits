package gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI;


import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomEntities;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.PlayerEffect;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.settings.SuitIUISetting;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.MathUtil;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.items.InventoryUtil;
import gmail.anto5710.mcp.customsuits.Utils.items.ItemUtil;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;


public class SuitIUI extends Inventories implements Listener {

	CustomSuitPlugin plugin;

	public SuitIUI(CustomSuitPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void clickExpOnInventory(InventoryClickEvent e) {
		if (authenticateAccess(e, maininventory_name)) {
			Inventory i = e.getInventory();
			ItemStack icon = i.getItem(8);

			if (icon != null && icon.getType() == Material.EXPERIENCE_BOTTLE && e.getSlot() == 8) {
				int amount = icon.getAmount();
				int a = e.isLeftClick()? 1:-1;
				
				if(e.getClick()==ClickType.MIDDLE){
					a = 10;
					
				} else if (e.isShiftClick()) a *= Values.SuitMaxLevel;

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
		if(authenticateAccess(e, commandinventory_name)) {
			Player p = (Player) e.getWhoClicked();
			if (e.getSlot() == 10) {
				CustomSuitPlugin.summonNearestSuit(p);

			} else if (e.getSlot() == 12) {
				openTargetPlayerList(e, p);

			} else if (e.getSlot() == 14) {
				CustomSuitPlugin.summonAll(p);

			} else if (e.getSlot() == 16) {
				PlayerEffect.spawnFireworks(p);
			}
			e.setCancelled(true);
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
				SuitIUISetting hdle = CustomSuitPlugin.handle(player);

				if (name.equals(HelmetColorInventory_name)) {
					hdle.dyeHelmet(color);
				} else if (name.equals(ChestPlateColorInventory_name)) {
					hdle.dyeChestplate(color);
				} else if (name.equals(LeggingsColorInventory_name)) {
					hdle.dyeLeggings(color);
				} else if (name.equals(BootsColorInventory_name)) {
					hdle.dyeBoots(color);
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
				Player whoClicked = (Player) e.getWhoClicked();
				ItemStack item = e.getCurrentItem();
				Player player = ItemUtil.capitate(item);
				if (player != null) {
					CustomSuitPlugin.handle(player).putTarget(whoClicked);
				} else {
					whoClicked.sendMessage(Values.NoSuchEntity);
				}
			}
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void clickArmorGUI(InventoryClickEvent e) {
		if (authenticateAccess(e, true, Inventories.armorinventory_name)) {
			Player player = (Player) e.getWhoClicked();
			
			int slot = e.getSlot();
			boolean toCancel = true;
		
			if (slot == 22) {
				player.openInventory(CustomSuitPlugin.handle(player).helmetEnchants);
			} else if (slot == 25) {
				player.openInventory(Inventories.HelmetColorInventory);
			} else if (slot == 31) {
				player.openInventory(CustomSuitPlugin.handle(player).chestEnchants);
			} else if (slot == 34) {
				player.openInventory(Inventories.ChestplateColorInventory);
			} else if (slot == 40) {
				player.openInventory(CustomSuitPlugin.handle(player).leggingsEnchants);
			} else if (slot == 43) {
				player.openInventory(Inventories.LeggingsColorInventory);
			} else if (slot == 49) {
				player.openInventory(CustomSuitPlugin.handle(player).bootsEnchants);
			} else if (slot == 52) {
				player.openInventory(Inventories.BootsColorInventory);				
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
			if (InventoryUtil.inAnyHand(player, CustomSuitPlugin.suitremote)) {
				CustomSuitPlugin.refreshInventory(player);

				player.openInventory(CustomSuitPlugin.handle(player).main);
				e.setCancelled(true);
			}
		}
	}	

	@EventHandler
	public void clickMenu(InventoryClickEvent e) {
		if (authenticateAccess(e, Inventories.maininventory_name)) {
			Player player = (Player) e.getWhoClicked();
			System.out.println(e.getInventory() == CustomSuitPlugin.handle(player).main);
			int slot = e.getSlot();
			if (slot == 0) {
				player.openInventory(CustomSuitPlugin.handle(player).command_equipment);
				
			} else if (slot == 4) {
				CustomSuitPlugin.refreshInventory(player);
				player.openInventory(CustomSuitPlugin.handle(player).armor);
				
			}else if(slot==18){
				CustomSuitPlugin.refreshInventory(player);
				player.openInventory(Inventories.type_inventory);
				
			}else if(slot == 22){
				CustomSuitPlugin.refreshInventory(player);
				player.openInventory(Inventories.vehicle_inventory);
			}
			e.setCancelled(true);
		}
	}
	
	public static boolean authenticateAccess(InventoryEvent e, String token){
		return ! SuitUtils.anyNull(token, e, e.getInventory(), e.getView().getTitle()) && 
				e.getView().getTitle().contains(token);
	}
	
	public static boolean authenticateAccess(InventoryEvent e, boolean mustBePlayer, String...tokens){
		if(SuitUtils.anyNull(tokens, e, e.getInventory())) return false;
		
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