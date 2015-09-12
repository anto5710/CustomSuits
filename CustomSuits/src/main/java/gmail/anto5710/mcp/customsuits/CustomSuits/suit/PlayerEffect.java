package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.CustomSuits.dao.SpawningDao;
import gmail.anto5710.mcp.customsuits.Setting.Values;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;
import org.junit.internal.matchers.IsCollectionContaining;

/**
 * 플레이어의 갑옷 착용과 착용 후 능력에 관여합니다. ex:
 *
 * 엔티티를 우클릭 하였을 떄 그 엔티티가 이름이 Mark인 갑옷을 입고 있고 , 갑옷 이름에서 확인한 주인과 플레이어가 일치할 때 ,
 * 플레이어에게 갑옷을 압혀줍니다. 갑옷을 입고 쉬프트를 누르면 날 수 있는 능력과 각종 포션 효과를 걸어줍니다.
 *
 * iron barding (철 말 갑옷 )을 들고 우클릭을 하였을 시 인벤토리에서 부싯돌(총알)을 하나 삭제하며 총알을 발사해줍니다.
 * 
 * @author anto5710
 *
 */

public class PlayerEffect implements Listener {
	private CustomSuitPlugin mainPlugin;
	private Logger logger;
	SchedulerHunger hungerscheduler;
	String regex = Values.regex;

	public PlayerEffect(CustomSuitPlugin main) {
		this.mainPlugin = main;
		logger = mainPlugin.getLogger();
		hungerscheduler = main.hscheduler;
	}
	
	@EventHandler
	public void kill(PlayerDeathEvent e) {
		Player p = (Player) e.getEntity();
		if (this.hungerscheduler.getList().contains(p)
				&& p.getGameMode() != GameMode.CREATIVE) {
			removingeffects(p);

		}
		// if (e.getKeepInventory()) {
		// if (p.getInventory().getLeggings().getItemMeta().getDisplayName()
		// .endsWith("Mark")) {
		// CustomSuitPlugin.removeplayer(p);
		// }
		// } else {
		// List<ItemStack> ls = e.getDrops();
		// ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS);
		// item.getItemMeta().setDisplayName("Mark");
		// if (ls.contains(item)) {
		// CustomSuitPlugin.removeplayer(p);
		// }
		// }
	}

	@EventHandler
	public void onPlayermove(PlayerMoveEvent moveevent) {
		Material material = Material.STONE;
		Player player = moveevent.getPlayer();

		Boolean m = false;
		if (!Mark(player)) {
			return;
		}

		if (Mark(player)) {
			int level = CustomSuitPlugin.getLevel(player);
			addpotion(new PotionEffect(PotionEffectType.HEALTH_BOOST, 99999999,
					((int) level / 16) + 5), player);
			addpotion(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999999,
					10 + level), player);
			addpotion(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,
					99999999, 10 + level), player);
			addpotion(new PotionEffect(PotionEffectType.ABSORPTION, 99999999,
					5 + level), player);
			addpotion(new PotionEffect(PotionEffectType.HEALTH_BOOST, 99999999,
					5 + ((int) level / 16)), player);
			addpotion(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,
					99999999, 6 + ((int) level / 16)), player);
			addpotion(new PotionEffect(PotionEffectType.SPEED, 99999999,
					5 + ((int) level / 32)), player);
			addpotion(new PotionEffect(PotionEffectType.WATER_BREATHING,
					99999999, 5 + level), player);
			addpotion(new PotionEffect(PotionEffectType.JUMP, 99999999, 2),
					player);
			addpotion(new PotionEffect(PotionEffectType.REGENERATION, 99999999,
					8 + (int) level / 16), player);
			

			Location baseLocation = player.getLocation();
			if (player.isFlying()) {
				m = true;
			} else if (player.isFlying() == false && player.isOnGround()) {
				Location ploc = player.getLocation();
				Double playerY = player.getLocation().getY() - 1;
				ploc.setY(playerY);
				if (ploc.getBlock().getType() != Material.AIR) {
					material = ploc.getBlock().getType();
				} else {
					m = true;
				}
			} else {
				m = true;
			}
			if (m) {

				SuitUtils.playEffect(baseLocation, Effect.MOBSPAWNER_FLAMES, 3,
						0, 40);
			} else {
				SuitUtils.playEffect(baseLocation, Effect.TILE_BREAK, 3,
						material.getId(), 40);
			}

		}
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {

		LivingEntity deadEtt = e.getEntity();
		// deadEtt.getEntityId();
		if (deadEtt.getType() != EntityType.PLAYER) {

			SpawningDao dao = this.mainPlugin.getDao();
			dao.remove(deadEtt);
		}

	}

	/**
	 * 자신이 만든 엔티티를 우클릭 시 그 엔티티의 옷을 뺏어입게됩니다.
	 * 
	 * @param event
	 */
	@EventHandler
	public void onPlayerInteracted(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		Entity entity = event.getRightClicked();
		// if ( ! isEntityCreatedBy( entity, pl )) {
		// return ;
		// }

		SpawningDao dao = this.mainPlugin.getDao();

		if (dao.isCreatedBy(entity, player)) {

			LivingEntity livingentity = (LivingEntity) entity;
			if (livingentity.getType() != EntityType.HORSE) {

				// 4개를 다 가지고 있음!

				ItemStack chestItem = livingentity.getEquipment()
						.getChestplate();

				ItemStack leggings = livingentity.getEquipment().getLeggings();
				ItemStack boots = livingentity.getEquipment().getBoots();
				ItemStack helmet = livingentity.getEquipment().getHelmet();

				player.getEquipment().setHelmet(helmet);
				player.getEquipment().setChestplate(chestItem);
				player.getEquipment().setLeggings(leggings);
				player.getEquipment().setBoots(boots);
				player.updateInventory();
				livingentity.damage(10000000D);

				player.getWorld()

				.playEffect(player.getLocation(), Effect.TILE_BREAK,
						Material.COBBLESTONE.getId(), 300);

				player.playSound(player.getLocation(), Sound.ENDERDRAGON_DEATH,
						9.0F, 9.0F);
				dao.remove(livingentity);
			}
		}
	}

	@EventHandler
	public void zoom(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() == Action.LEFT_CLICK_AIR
				|| e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (Mark(p)) {
				if (p.getItemInHand().getItemMeta().getDisplayName() != null) {

					String name = CustomSuitPlugin.getGun().getItemMeta()
							.getDisplayName();
					String gunname = p.getItemInHand().getItemMeta()
							.getDisplayName();
					if (gunname.contains(regex)) {
						String[] values = name.split(regex);
						String[] names = gunname.split(regex);
						if (values[0].endsWith(names[0])) {
							if (p.hasPotionEffect(PotionEffectType.SLOW)) {
								p.sendMessage(ChatColor.BLUE + "[Info]: "
										+ ChatColor.AQUA + "Zoom in ("
										+ ChatColor.RED + "Off"
										+ ChatColor.AQUA + ")");
								p.removePotionEffect(PotionEffectType.SLOW);
							} else {

								p.addPotionEffect(new PotionEffect(
										PotionEffectType.SLOW, 100000, 10));
								p.sendMessage(ChatColor.BLUE + "[Info]: "
										+ ChatColor.AQUA + "Zoom in ("
										+ ChatColor.RED + "On" + ChatColor.AQUA
										+ ")");

							}

						}
					}
				}
			}
		}
	}

	@EventHandler
	public void suit(PlayerToggleSneakEvent p) {
		Player player = p.getPlayer();

		if (Mark(player)) {
			if (player.getFoodLevel() < 2) {
				SuitUtils.Wrong(player, "Energy");

			}

			else {

				int level = CustomSuitPlugin.getLevel(player);

				int hunger = player.getFoodLevel();

				player.setAllowFlight(true);
				player.setFlying(true);
				player.setFlySpeed(1.0F);

				player.setFoodLevel(hunger);

				hungerscheduler.addFlyingPlayer(player);

				player.playSound(player.getLocation(), Sound.WITHER_SPAWN,
						1.0F, 1.0F);
			}
		}
	}

	public static void addpotion(PotionEffect effect, Player player) {
		if (!ContainPotionEffect(player, effect.getType(),
				effect.getAmplifier())) {
			player.removePotionEffect(effect.getType());
		}
		player.addPotionEffect(effect);
	}

	public static boolean ContainPotionEffect(Player player,
			PotionEffectType type, int level) {

		for (PotionEffect e : player.getActivePotionEffects()) {

			if (e.getType().equals(type) && e.getAmplifier() == level) {

				return true;

			}
		}
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean Mark(Player p) {

		if (p.getEquipment().getLeggings() != null) {

			String leggings = p.getEquipment().getLeggings().getItemMeta()
					.getDisplayName();
			if (leggings != null) {
				if (leggings.contains("Mark:")) {
					return true;
				}

			}
		}
		if (p.getEquipment().getHelmet() != null) {
			String helmetname = p.getEquipment().getHelmet().getItemMeta()
					.getDisplayName();
			if (helmetname != null) {

				if (helmetname.contains("Mark:")) {
					return true;
				}
			}
		}
		return false;

	}

	@EventHandler
	public void onquit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		hungerscheduler.removeflyingplayer(player);

	}

	@EventHandler
	public void stopDisabledPlayer(PlayerMoveEvent moveEvent) {
		Player player = moveEvent.getPlayer();

		if (this.hungerscheduler.getList().contains(player)) {
			if (player.getGameMode() != GameMode.CREATIVE) {

				if (!Mark(player)) {
					removingeffects(player);

				}

			}
		}
	}

	@EventHandler
	public void RestartToFlyinCreative(PlayerMoveEvent moveEvent) {
		Player player = moveEvent.getPlayer();

		if (player.getGameMode() == GameMode.CREATIVE) {
			player.setAllowFlight(true);
		}
	}

	private void removingeffects(Player player) {

		player.setFlying(false);
		player.setAllowFlight(false);

		hungerscheduler.removeflyingplayer(player);

		player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
		player.removePotionEffect(PotionEffectType.ABSORPTION);
		player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
		player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
		player.removePotionEffect(PotionEffectType.JUMP);
		player.removePotionEffect(PotionEffectType.SPEED);
		player.removePotionEffect(PotionEffectType.REGENERATION);
		player.removePotionEffect(PotionEffectType.WATER_BREATHING);
		player.removePotionEffect(PotionEffectType.NIGHT_VISION);
		player.removePotionEffect(PotionEffectType.SLOW);
		player.setFlySpeed(0.1F);

		player.playSound(player.getLocation(), Sound.ANVIL_LAND, 3.0F, 2.0F);
	}

}
