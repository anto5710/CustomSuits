package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;

import java.time.temporal.ChronoUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Skull;

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
					if (e.getInventory().getItem(8).getAmount() == 64) {
						e.getInventory().getItem(8).setAmount(1);
					} else {
						i.getItem(8).setAmount(
								e.getInventory().getItem(8).getAmount() + 1);
					}
					

					e.setCancelled(true);

				}
			}
		}
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
			if (e.getSlot() == 1) {
				CustomSuitPlugin.runSpawn((Player) e.getWhoClicked());
				e.setCancelled(true);
			} else if (e.getSlot() == 3) {
				OpenList(e, (Player) e.getWhoClicked());
				e.setCancelled(true);
			} else if (e.getSlot() == 5) {
				CustomSuitPlugin.spawnall((Player) e.getWhoClicked());
				e.setCancelled(true);
			} else if (e.getSlot() == 7) {
				CustomSuitPlugin.spawnfireworks((Player) e.getWhoClicked());
				e.setCancelled(true);
			} else {
				e.setCancelled(true);
			}
		}
	}

	private void OpenList(InventoryClickEvent e, Player whoClicked) {
		Inventory list = Bukkit.createInventory(null, 36, "[Online Players]");

		for (Player player : whoClicked.getServer().getOnlinePlayers()) {
			ItemStack skull = new ItemStack(397, 1, (short) 0, (byte) 3);

			ItemMeta meta = skull.getItemMeta();
			meta.setDisplayName(player.getName());
			skull.setItemMeta(meta);
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
		if (e.getClick() == ClickType.LEFT) {
			if (e.getClickedInventory().getName().contains("[Online Players]")) {
				if (e.getClickedInventory().getItem(e.getSlot()) != null) {
					ItemStack item = e.getClickedInventory().getItem(
							e.getSlot());

					if (item.getType() == Material.SKULL_ITEM) {
						if (item.getItemMeta().getDisplayName() != null) {
							String name = item.getItemMeta().getDisplayName();
						
							Player player = WhoClicked.getServer().getPlayer(name);

							if (CustomSuitPlugin.MarkEntity(WhoClicked)) {
								CustomSuitPlugin.targetPlayer(WhoClicked, player);
								e.setCancelled(true);
							} else {
								WhoClicked.sendMessage(Values.NoSuchEntity);
								e.setCancelled(true);
							}
						}

					}
				}
			}
		}
	}

	@EventHandler
	public void clickAir(InventoryClickEvent e) {
		if(e.getClickedInventory()==null){
			return;
		}
		if(e.getClickedInventory().getName()==null){
			return;
		}
		if (e.getClickedInventory().getName().contains("[Settings]")) {

			int slot = e.getSlot();
			if (slot == 1 || slot == 2 || slot == 3 || slot == 5 || slot == 6
					|| slot == 7) {
				e.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void clickAirArmor(InventoryClickEvent e) {
		if(e.getClickedInventory()==null){
			return;
		}
		if(e.getClickedInventory().getName()==null){
			return;
		}
		if (e.getClickedInventory().getName().contains("[Armor]")) {

			int slot = e.getSlot();
			if (slot == 1 ||  slot == 3 || slot == 5 || slot == 7
					) {
				e.setCancelled(true);
			}
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
			if (e.getClick() == ClickType.RIGHT) {
				Player player = (Player) e.getWhoClicked();
			CustomSuitPlugin.resetInventory((Player) e.getWhoClicked());

				int slot = e.getSlot();
				if (slot == 0) {
					player.openInventory(CustomSuitPlugin.helmetequipment
							.get(player));
					e.setCancelled(true);
				}
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
			if (e.getClick() == ClickType.RIGHT) {

				Player player = (Player) e.getWhoClicked();
				CustomSuitPlugin.resetInventory((Player) e.getWhoClicked());


				int slot = e.getSlot();
				if (slot == 2) {
					player.openInventory(CustomSuitPlugin.chestequipment
							.get(player));
					e.setCancelled(true);
				}

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
			if (e.getClick() == ClickType.RIGHT) {

				Player player = (Player) e.getWhoClicked();
				CustomSuitPlugin.resetInventory((Player) e.getWhoClicked());


				int slot = e.getSlot();
				if (slot == 4) {
					player.openInventory(CustomSuitPlugin.leggingsequipment
							.get(player));
					e.setCancelled(true);
				}

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
			if (e.getClick() == ClickType.RIGHT) {
				Player player = (Player) e.getWhoClicked();
				CustomSuitPlugin.resetInventory((Player) e.getWhoClicked());


				int slot = e.getSlot();
				if (slot == 6) {
					player.openInventory(CustomSuitPlugin.bootsequipment
							.get(player));
					e.setCancelled(true);
				}

			}
		}
	}

	public void clickHand(InventoryClickEvent e) {
		if(e.getClickedInventory()==null){
			return;
		}
		if(e.getClickedInventory().getName()==null){
			return;
		}
		if (e.getClickedInventory().getName().contains("[Armor]")) {
			if (e.getClick() == ClickType.RIGHT) {
				Player player = (Player) e.getWhoClicked();
				CustomSuitPlugin.resetInventory((Player) e.getWhoClicked());


				int slot = e.getSlot();
				if (slot == 8) {
					player.openInventory(CustomSuitPlugin.handequipment
							.get(player));
					e.setCancelled(true);
				}

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
				player.openInventory(CustomSuitPlugin.CommandInventory);
				e.setCancelled(true);
			} else if (slot == 4) {
				CustomSuitPlugin.resetInventory((Player) e.getWhoClicked());

				e.getWhoClicked().openInventory(
						CustomSuitPlugin.armorequipment.get(e.getWhoClicked()));
				e.setCancelled(true);
			}
			

		}
	}

}
