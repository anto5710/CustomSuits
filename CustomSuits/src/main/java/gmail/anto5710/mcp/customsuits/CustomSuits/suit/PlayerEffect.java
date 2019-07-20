package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.CustomSuits.FireworkProccesor;
import gmail.anto5710.mcp.customsuits.CustomSuits.PlayEffect;
import gmail.anto5710.mcp.customsuits.CustomSuits.dao.SpawningDao;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.MathUtils;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;
import gmail.anto5710.mcp.customsuits.Utils.WeaponUtils;
import gmail.anto5710.mcp.customsuits._Thor.Hammer;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Item;
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
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
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
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
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
	String regex = Values.regex;
	static HashMap<Player, Boolean>Zoom = new HashMap<>();

	public PlayerEffect(CustomSuitPlugin main) {
		PlayerEffect.mainPlugin = main;
		logger = mainPlugin.getLogger();
		hungerscheduler = main.hscheduler;
	}
	
	public static void spawnfireworks(final Player whoClicked) {
		final List<Entity> list = whoClicked.getWorld().getEntities();
		new BukkitRunnable() {
			boolean isPlayed=false;
			@Override
			public void run() {
				for (Entity entity : list) {
					if (CustomSuitPlugin.isCreatedBy(entity, whoClicked) && entity instanceof Damageable) {
						entity.getWorld().createExplosion(entity.getLocation(), 8.0F);
						Damageable damgaeable = (Damageable) entity;
						damgaeable.damage(1000000.0D, whoClicked);
						Location location = entity.getLocation();
						Firework firework = (Firework) location.getWorld().spawnEntity(
								location, EntityType.FIREWORK);
						
						FireworkMeta meta = firework.getFireworkMeta();
						FireworkEffect effect = FireworkProccesor.getRandomEffect();
						meta.addEffect(effect);
						int power = (int) ( MathUtils.randomRadius(3)+1.5);
						if(power<1){
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
					whoClicked.sendMessage(ChatColor.BLUE + "[Info]: " + ChatColor.AQUA
							+ "Fireworks");
				}
			}
			
		}.runTaskLater(mainPlugin, 20);
	}

	public static void playSpawningEffect(Entity entity, final Player player) {
		Location playerlocation = player.getLocation();
		LivingEntity lentity = (LivingEntity) entity;

		if (entity.getType() != EntityType.PLAYER
				&& PlayerEffect.hasArmor(lentity.getEquipment().getArmorContents())
				&& SuitUtils.canWearArmor(lentity)
				&& CustomSuitPlugin.isMarkEntity(lentity)
				&& CustomSuitPlugin.dao.isCreatedBy(lentity, player) 
				&& entity.getVehicle() == null) {

			Location entitylocation = lentity.getLocation();
			CustomSuitPlugin.runSpawn(entitylocation, playerlocation, lentity, player);
			
			new BukkitRunnable() {

				ItemStack helmet = lentity.getEquipment().getHelmet();
				ItemStack chestplate = lentity.getEquipment().getChestplate();

				LivingEntity Entity = lentity;

				@Override
				public void run() {
					if (SuitUtils.canWearArmor(lentity) && CustomSuitPlugin.isMarkEntity(lentity)
							&& CustomSuitPlugin.dao.isCreatedBy(lentity, player)) {
						player.setNoDamageTicks(20);
						PlayEffect.play_Suit_Get(player.getLocation(), player);
						Bukkit.getScheduler().runTaskLater(mainPlugin, new Runnable() {

							@Override
							public void run() {
								if (Entity.getEquipment().getHelmet() != null) {

									player.getEquipment().setHelmet(helmet);
									player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 9.0F, 9.0F);
								}
							}
						}, 2);
						Bukkit.getScheduler().runTaskLater(mainPlugin, new Runnable() {

							@Override
							public void run() {
								if (Entity.getEquipment().getChestplate() != null) {

									player.getEquipment().setChestplate(chestplate);
									player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 9.0F, 9.0F);
								}
							}
						}, 12);
						Bukkit.getScheduler().runTaskLater(mainPlugin, new Runnable() {

							@Override
							public void run() {
								if (Entity.getEquipment().getLeggings() != null) {
									ItemStack leggings = Entity.getEquipment().getLeggings();
									player.getEquipment().setLeggings(leggings);
									player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 9.0F, 9.0F);

								}
							}
						}, 16);
						Bukkit.getScheduler().runTaskLater(mainPlugin, new Runnable() {

							@Override
							public void run() {
								if (Entity.getEquipment().getBoots() != null) {
									ItemStack boots = Entity.getEquipment().getBoots();
									player.getEquipment().setBoots(boots);
									player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 9.0F, 9.0F);

								}
							}
						}, 24);

						lentity.damage(1000000.0D);
						player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 9.0F, 9.0F);
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
		if(!SuitUtils.checkItem(CustomSuitPlugin.suitremote, SuitUtils.getHoldingItem(player))){
			return;
		}
		if(!SuitUtils.isLeftClick(event)){
			return;
		}
		Location location_entity =SuitUtils.getTargetBlock(player, Values.spawnSuit_max_target_distance).getLocation();
		location_entity.add(0, 1.5, 0);

		String vehicle = null;
		if(CustomSuitPlugin.vehicle_map.containsKey(player)){
			vehicle = CustomSuitPlugin.vehicle_map.get(player);
		}
		if(!CustomSuitPlugin.Type_Map.containsKey(player)){
			CustomSuitPlugin.Type_Map.put(player, "warrior");
		}
		if(!CustomSuitPlugin.amount.containsKey(player)){
			CustomSuitPlugin.amount.put(player, 1);
		}
		LivingEntity target = null;
		if(CustomSuitPlugin.target.containsKey(player)){
		 target = CustomSuitPlugin.getTarget(player);
		}
		String entityname = CustomSuitPlugin.Type_Map.get(player);
		int amount = CustomSuitPlugin.amount.get(player);
				
		CustomSuitPlugin.spawnEntity(entityname,vehicle, amount, target,player, location_entity);
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
		ItemStack chestItem = lentity.getEquipment().getChestplate();
		ItemStack leggings = lentity.getEquipment().getLeggings();
		ItemStack boots = lentity.getEquipment().getBoots();
		ItemStack helmet = lentity.getEquipment().getHelmet();

		player.getEquipment().setHelmet(helmet);
		player.getEquipment().setChestplate(chestItem);
		player.getEquipment().setLeggings(leggings);
		player.getEquipment().setBoots(boots);
		lentity.damage(10000000D);
		player.updateInventory();
		PlayEffect.play_Suit_Get(player.getLocation() , player );

		player.playSound(player.getLocation(),Values.SuitSound, 9.0F, 9.0F);
		dao.remove(lentity);
	}

	public static boolean hasArmor(ItemStack[] armorContents) {
		int checkCount = 0;
		for (int index = 0; index < armorContents.length; index++) {
			ItemStack arm = armorContents[index]; 
			if (SuitUtils.isNull(arm)) checkCount++; 
		}
		return checkCount < armorContents.length;
	}

	@EventHandler
	public void zoom(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (SuitUtils.isLeftClick(event) && CustomSuitPlugin.isMarkEntity(player)
         && WeaponUtils.checkgun(player, CustomSuitPlugin.getGun())) {

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
		addpotion(new PotionEffect(PotionEffectType.SLOW, 999999999, 10), p);
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

			player.playSound(player.getLocation(), Values.SuitSneakSound, 1F, 1.0F);

			player.setFlySpeed(1F);
			player.setAllowFlight(true);
			player.setFlying(true);
			player.setVelocity(new Vector(0, 1, 0));

			hungerscheduler.addFlyingPlayer(player);
		}
	}

	public static void addpotion(PotionEffect effect, LivingEntity livingEntity) {
		if (!containPotionEffect(livingEntity, effect.getType(), effect.getAmplifier())) {
			livingEntity.removePotionEffect(effect.getType());
		}
		livingEntity.addPotionEffect(effect);
	}

	public static boolean containPotionEffect(LivingEntity livingEntity, PotionEffectType type, int level) {
		for (PotionEffect effect : livingEntity.getActivePotionEffects()) {
			if (effect.getType().equals(type) && effect.getAmplifier() == level) {
				return true;
			}
		}
		return false;
	}

	public static void removeEffects(Player player) {
		player.setFlying(false);
		player.setAllowFlight(false);
		player.setFlySpeed(0.5F);
		
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
	}
}
