package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.CustomSuits.dao.SpawningDao;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;

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
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
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
	
	}

	@EventHandler
	public void onPlayermove(PlayerMoveEvent moveevent) {
		Material material = Material.STONE;
		Player player = moveevent.getPlayer();

		Boolean m = false;
		if (!CustomSuitPlugin.MarkEntity(player)) {
			return;
		}

		if (CustomSuitPlugin.MarkEntity(player)) {
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
	public void onEntityDeath(EntityDeathEvent event) {
		Entity DeadEntity = event.getEntity();
		if(SpawningDao.spawnMap.containsKey(DeadEntity.getEntityId())){
		
		if (DeadEntity instanceof Player==false) {

			SpawningDao dao = this.mainPlugin.getDao();
			dao.remove(DeadEntity);
			}
		}

	}

	/**
	 * Get the Suit
	 * 
	 * @param event
	 */
	@EventHandler
	public void onPlayerInteracted(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		Entity entity = event.getRightClicked();
		

		SpawningDao dao = this.mainPlugin.getDao();

		if (dao.isCreatedBy(entity, player)&&entity instanceof Vehicle==false) {

			LivingEntity livingentity = (LivingEntity) entity;
			if (CustomSuitPlugin.MarkEntity(livingentity)) {

				

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
				

				SuitUtils.playEffect(player.getEyeLocation(), Effect.TILE_BREAK, 30, Material.COBBLESTONE.getId(), 5);

				player.playSound(player.getLocation(), Values.SuitSound,
						9.0F, 9.0F);
				
			}
			dao.remove(livingentity);
			livingentity.damage(90000000D);
		}
	}

	@EventHandler
	public void zoom(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.LEFT_CLICK_AIR
				|| event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (CustomSuitPlugin.MarkEntity(player)) {
				if (player.getItemInHand().getItemMeta().getDisplayName() != null) {

					String name = CustomSuitPlugin.getGun().getItemMeta()
							.getDisplayName();
					String gunname = player.getItemInHand().getItemMeta()
							.getDisplayName();
					if (gunname.contains(regex)) {
						String[] values = name.split(regex);
						String[] names = gunname.split(regex);
						if (values[0].endsWith(names[0])) {
							if (ContainPotionEffect(player, PotionEffectType.SLOW, 10)) {
								player.sendMessage(Values.ZoomOutMessage);
								player.removePotionEffect(PotionEffectType.SLOW);
							} else {

								player.addPotionEffect(new PotionEffect(
										PotionEffectType.SLOW, 100000, 10));
								player.sendMessage(Values.ZoomOnMessage);

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

		if (CustomSuitPlugin.MarkEntity(player)) {
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

				if (!CustomSuitPlugin.MarkEntity(player)) {
					removingeffects(player);

				}

			}
		}
	}

	@EventHandler
	public void RestartToFlyinCreative(PlayerMoveEvent moveEvent) {
		
		Player player = moveEvent.getPlayer();
		if(player.getAllowFlight()==false){

			if (player.getGameMode() == GameMode.CREATIVE) {
			player.setAllowFlight(true);
			}
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
