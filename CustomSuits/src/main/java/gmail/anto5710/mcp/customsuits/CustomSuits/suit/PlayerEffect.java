package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.CustomSuits.FireworkProccesor;

import gmail.anto5710.mcp.customsuits.CustomSuits.dao.SpawningDao;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.MathUtils;
import gmail.anto5710.mcp.customsuits.Utils.CustomEffects;
import gmail.anto5710.mcp.customsuits.Utils.ItemUtil;
import gmail.anto5710.mcp.customsuits.Utils.PotionBrewer;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

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
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * 
 * @author anto5710
 *
 */

public class PlayerEffect implements Listener {
	private static CustomSuitPlugin mainPlugin;
	private Logger logger;
	static HungerScheduler hungerscheduler;
	String regex = Values.gun_regex;
	static HashMap<Player, Boolean>Zoom = new HashMap<>();

	public PlayerEffect(CustomSuitPlugin main) {
		PlayerEffect.mainPlugin = main;
		logger = mainPlugin.getLogger();
		hungerscheduler = main.hscheduler;
	}

	public static void spawnfireworks(final Player whoClicked) {
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
						int power = (int) (MathUtils.randomRadius(3) + 1.5);
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
		Location playerlocation = player.getLocation();
		LivingEntity lentity = (LivingEntity) entity;

		if (entity.getType() != EntityType.PLAYER
				&& PlayerEffect.hasArmor(lentity.getEquipment().getArmorContents())
				&& SuitUtils.isArmable(lentity)
				&& CustomSuitPlugin.isMarkEntity(lentity)
				&& CustomSuitPlugin.dao.isCreatedBy(lentity, player) 
				&& entity.getVehicle() == null) {

			Location entitylocation = lentity.getLocation();
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
		if(!ItemUtil.checkItem(CustomSuitPlugin.suitremote, SuitUtils.getHoldingItem(player)) || !SuitUtils.isLeftClick(event)){
			return;
		}
		Location location_entity =SuitUtils.getTargetBlock(player, Values.spawnSuit_max_target_distance).getLocation();
		location_entity.add(0, 1.5, 0);
		
		CustomSuitPlugin.spawnSuit(player, location_entity);
	}

	@EventHandler
	public void onPlayermove(PlayerMoveEvent moveevent) {
		Player player = moveevent.getPlayer();

		if (!CustomSuitPlugin.isMarkEntity(player) || Player_Move.list.contains(player)) return;
		
		if (CustomSuitPlugin.isMarkEntity(player)) {
			if (Player_Move.list.isEmpty()) {
				new Player_Move(mainPlugin).runTaskTimer(mainPlugin, 0, 1);
			}

			Player_Move.list.add(player);
			if (HungerScheduler.containPlayer(player)) {
				return;
			}
			if (player.getFoodLevel() >= Values.SuitFlyHunger) {
				hungerscheduler.addFlyingPlayer(player);
			}
		}
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		Entity DeadEntity = event.getEntity();
		if (SpawningDao.spawnMap.containsKey(DeadEntity.getEntityId())) {
			if (!(DeadEntity instanceof Player)) {
				SpawningDao dao = PlayerEffect.mainPlugin.getDao();
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
		
		SpawningDao dao = PlayerEffect.mainPlugin.getDao();
		if(!(entity instanceof LivingEntity) || !dao.isCreatedBy(entity, player)) return;

		LivingEntity lentity = (LivingEntity) entity;
		if(!hasArmor(lentity.getEquipment().getArmorContents())){
			return;
		}
		
		// 4개를 다 가지고 있음!
		player.getEquipment().setArmorContents(lentity.getEquipment().getArmorContents());
		lentity.damage(10000000D);
		player.updateInventory();
		CustomEffects.play_Suit_Get(player.getLocation() , player );

		SuitUtils.playSound(player, Values.SuitSound, 9.0F, 9.0F);
		dao.remove(lentity);
	}

	public static boolean hasArmor(ItemStack[] armorContents) {
		int checkCount = 0;
		for (int index = 0; index < armorContents.length; index++) {
			ItemStack arm = armorContents[index]; 
			if (SuitUtils.isAir(arm)) checkCount++; 
		}
		return checkCount < armorContents.length;
	}

	@EventHandler
	public void zoom(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (SuitUtils.isLeftClick(event) && CustomSuitPlugin.isMarkEntity(player)
         && SuitWeapons.checkGun(player, CustomSuitPlugin.getGun())) {

			if (Zoom == null)
				return;

			if (Zoom.containsKey(player)) {
				boolean isZoomed = Zoom.get(player);
				if (isZoomed) {
					player.removePotionEffect(PotionEffectType.SLOW); //de-zoom
				} else {
					zoom(player);
				}
				
				Zoom.put(player, !isZoomed);
			} else {
				Zoom.put(player, true);
				zoom(player);
			}
		}
	}
	
	private void zoom(Player p){
		PotionBrewer.addPotion(p, PotionEffectType.SLOW, 999999999, 10);
	}
	
	@EventHandler
	public void onPlayerSneak(PlayerToggleSneakEvent p) {
		final Player player = p.getPlayer();

		if (CustomSuitPlugin.isMarkEntity(player)) {
			if (player.getFoodLevel() < Values.leastFlyHunger) {
				SuitUtils.lack(player, "Energy");
				return;
			}
			if (player.isFlying() && HungerScheduler.containPlayer(player)) {
				return;
			}

			SuitUtils.playSound(player, Values.SuitSneakSound, 1F, 1.0F);

			player.setFlySpeed(1F);
			player.setAllowFlight(true);
			player.setFlying(true);
			player.setVelocity(new Vector(0, 1, 0));

			hungerscheduler.addFlyingPlayer(player);
		}
	}
}
