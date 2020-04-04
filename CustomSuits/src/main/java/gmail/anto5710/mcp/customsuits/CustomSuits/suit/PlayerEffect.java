package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.dao.SpawningDao;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.weapons.MachineGun;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.CustomEffects;
import gmail.anto5710.mcp.customsuits.Utils.MathUtil;
import gmail.anto5710.mcp.customsuits.Utils.PotionBrewer;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.fireworks.FireworkProccesor;
import gmail.anto5710.mcp.customsuits.Utils.items.InventoryUtil;
import gmail.anto5710.mcp.customsuits.Utils.items.ItemUtil;

/**
 * 
 * @author anto5710
 *
 */

public class PlayerEffect implements Listener {
	private static CustomSuitPlugin mainPlugin;
	static HungerScheduler hscheduler;
	String regex = Values.gun_regex;
	public static Set<Player> zoomed = new HashSet<>();

	public PlayerEffect(CustomSuitPlugin main) {
		PlayerEffect.mainPlugin = main;
		mainPlugin.getLogger();
		hscheduler = main.hscheduler;
	}

	public static void spawnFireworks(final Player whoClicked) {
		final List<Entity> list = whoClicked.getWorld().getEntities();
		new BukkitRunnable() {
			boolean isPlayed = false;

			@Override
			public void run() {
				for (Entity entity : list) {
					if (CustomSuitPlugin.isCreatedBy(entity, whoClicked) && entity instanceof Damageable) {
						entity.getWorld().createExplosion(entity.getLocation(), 8.0F);
						Damageable damgaeable = (Damageable) entity;
						damgaeable.damage(1000000.0D, whoClicked);
						Location location = entity.getLocation();
						Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);

						FireworkMeta meta = firework.getFireworkMeta();
						FireworkEffect effect = FireworkProccesor.getRandomEffect();
						meta.addEffect(effect);
						int power = (int) (MathUtil.randomRadius(3) + 1.5);
						if (power < 1) {
							power++;
						}
						meta.setPower(power);
						firework.setFireworkMeta(meta);
						isPlayed = true;
					}
				}
				if (!isPlayed) {
					whoClicked.sendMessage(Values.NoSuchEntity);
				} else {
					whoClicked.sendMessage(ChatColor.BLUE + "[Info]: " + ChatColor.AQUA + "Fireworks");
				}
			}

		}.runTaskLater(mainPlugin, 20);
	}

	public static void playSpawningEffect(Entity entity, final Player player) {
		LivingEntity lentity = (LivingEntity) entity;

		if (entity.getType() != EntityType.PLAYER
				&& InventoryUtil.hasArmor(lentity)
				&& SuitUtils.isArmable(lentity)
				&& CustomSuitPlugin.isMarkEntity(lentity)
				&& CustomSuitPlugin.dao.isCreatedBy(lentity, player) 
				&& entity.getVehicle() == null) {

			CustomSuitPlugin.runSummon(lentity, player);
			
			new BukkitRunnable() {
				ItemStack helmet = lentity.getEquipment().getHelmet();
				ItemStack chestplate = lentity.getEquipment().getChestplate();
				ItemStack leggings = lentity.getEquipment().getLeggings();
				ItemStack boots = lentity.getEquipment().getBoots();
				
				@Override
				public void run() {
					if (SuitUtils.isArmable(lentity) && CustomSuitPlugin.isMarkEntity(lentity)
							&& CustomSuitPlugin.dao.isCreatedBy(lentity, player)) {
						player.setNoDamageTicks(20);
						CustomEffects.play_Suit_Get(player.getLocation(), player);
						
						Bukkit.getScheduler().runTaskLater(mainPlugin, () -> {
							if (helmet != null) {
								player.getEquipment().setHelmet(helmet);
								SuitUtils.playSound(player, Sound.BLOCK_ANVIL_USE, 9.0F, 9.0F);
							}
						}, 2);
						Bukkit.getScheduler().runTaskLater(mainPlugin, () -> {
							if (chestplate != null) {
								player.getEquipment().setChestplate(chestplate);
								SuitUtils.playSound(player, Sound.BLOCK_ANVIL_LAND, 9.0F, 9.0F);
							}
						}, 12);
						Bukkit.getScheduler().runTaskLater(mainPlugin, () -> {
							if (leggings != null) {
								player.getEquipment().setLeggings(leggings);
								SuitUtils.playSound(player, Sound.BLOCK_ANVIL_LAND, 9.0F, 9.0F);
							}
						}, 16);
						Bukkit.getScheduler().runTaskLater(mainPlugin, () -> {
							if (boots != null) {
								player.getEquipment().setBoots(boots);
								SuitUtils.playSound(player, Sound.BLOCK_ANVIL_LAND, 9.0F, 9.0F);
							}
						}, 24);

						lentity.damage(1000000.0D);
						SuitUtils.playSound(player, Sound.ENTITY_ENDER_DRAGON_DEATH, 9.0F, 9.0F);
						player.sendMessage(Values.SuitCallMessage);
						player.updateInventory();
					}
				}
			}.runTaskLater(mainPlugin, 10);
		}
	}

	@EventHandler
	public void clickToSpawnSuit(PlayerInteractEvent event){
		Player player = event.getPlayer();
		if (ItemUtil.compare(CustomSuitPlugin.suitremote, InventoryUtil.getMainItem(player))
				&& SuitUtils.isLeftClick(event)) {

			if (player.isSneaking()) {
				CustomSuitPlugin.summonNearestSuit(player);
			} else {
				Location spawnLoc = SuitUtils.getTargetLoc(player, Values.spawnSuit_max_target_distance).add(0, 1.5, 0);
				CustomSuitPlugin.spawnSuit(player, spawnLoc);
			}
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent moveEvent) {
		Player player = moveEvent.getPlayer();
		
		if (CustomSuitPlugin.isMarkEntity(player) && !CustomSuitPlugin.suitEffecter.isRegistered(player)) {
			
			CustomSuitPlugin.suitEffecter.register(player);
			if (!hscheduler.isRegistered(player) && player.getFoodLevel() >= Values.SuitFlyHunger) {
				hscheduler.register(player);
			}
		}
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		Entity deadEntity = event.getEntity();
		if (SpawningDao.spawnMap.containsKey(deadEntity.getEntityId()+"") && !(deadEntity instanceof Player)) {
			CustomSuitPlugin.dao.remove(deadEntity);
		}
	}

	/**
	 * Get the Suit
	 * 
	 * @param event
	 */
	@EventHandler
	public void onPlayerInteract(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		Entity entity = event.getRightClicked();
		
		SpawningDao dao = CustomSuitPlugin.dao;
		if (entity instanceof LivingEntity && dao.isCreatedBy(entity, player)) {
			LivingEntity lentity = (LivingEntity) entity;
			if (InventoryUtil.hasArmor(lentity)) {
				// 실오라기라도 하나 걸치고 있음
				InventoryUtil.equip(player, lentity.getEquipment().getArmorContents());
				lentity.damage(100000000D);
				player.updateInventory();
				CustomEffects.play_Suit_Get(player.getLocation(), player);

				SuitUtils.playSound(player, Values.SuitSound, 9.0F, 9.0F);
				dao.remove(lentity);
			}
		}
	}

	@EventHandler
	public void zoom(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (SuitUtils.isLeftClick(event) && CustomSuitPlugin.isMarkEntity(player)
         && MachineGun.checkGun(player, CustomSuitPlugin.gunitem)) {
			toggleZoom(player);
		}
	}
	
	private void toggleZoom(Player player){
		boolean toZoom = !isZooming(player);
		if(toZoom){
			PotionBrewer.zoom(player, 10);
			zoomed.add(player);
		}else{
			PotionBrewer.dezoom(player);
			zoomed.remove(player);
		}
	}
	
	public static boolean isZooming(Player player){
		return zoomed.contains(player);
	}
	
	@EventHandler
	public void onPlayerFly(PlayerToggleFlightEvent e) {
		Player player = e.getPlayer();

		if (CustomSuitPlugin.isMarkEntity(player)) {
			if (player.getFoodLevel() < Values.leastFlyHunger) {
				SuitUtils.lack(player, "Energy");
				return;
			}
			if (player.isFlying() && hscheduler.isRegistered(player)) {
				return;
			}

			SuitUtils.playSound(player, Values.SuitSneakSound, 1F, 1.0F);

			player.setFlySpeed(CustomSuitPlugin.handle(player).flyingSpeed());
			player.setAllowFlight(true);
			player.setFlying(true);

			hscheduler.register(player);
		}
	}
}
