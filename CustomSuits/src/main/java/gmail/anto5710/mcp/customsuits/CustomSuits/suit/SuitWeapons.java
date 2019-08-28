package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.CustomSuits.FireworkProccesor;
import gmail.anto5710.mcp.customsuits.Setting.Enchant;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.Glow;
import gmail.anto5710.mcp.customsuits.Utils.ItemUtil;
import gmail.anto5710.mcp.customsuits.Utils.MathUtils;
import gmail.anto5710.mcp.customsuits.Utils.PacketUtil;
import gmail.anto5710.mcp.customsuits.Utils.ParticleUtil;
import gmail.anto5710.mcp.customsuits.Utils.CustomEffects;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SuitWeapons implements Listener {

	static int maxformachine = Values.MachineGunAmmoAmount;
	static int maxforsniper = Values.SnipeAmmoAmount;
	static ArrayList<Fireball> listFireball = new ArrayList<>();

	public static String gun_regex = Values.gun_regex;

	public static CustomSuitPlugin plugin;

	public static HashMap<Player, Boolean> charging = new HashMap<>();
	public static HashMap<Player, Boolean> cooldowns = new HashMap<>();

	public static HashSet<Player> TNT_cooldowns = new HashSet<>();

	public SuitWeapons(CustomSuitPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void interectShield(PlayerInteractEvent event) {
		System.out.println("sdasd" + event.getAction());
		final Player player = event.getPlayer();
		if (SuitUtils.isRightClick(event) &&player.isSneaking()) {
			if (CustomSuitPlugin.isMarkEntity(player)) {
				ItemStack itemInHand = SuitUtils.getHoldingItem(player);
				if (itemInHand != null && itemInHand.getType() == Values.SuitLauncher) {
					if (HungerScheduler.deltaHunger(player, Values.SuitShieldHunger)) {
						final int sec = (CustomSuitPlugin.getSuitLevel(player)) / 20 + 3;
						player.setNoDamageTicks(sec * 20);
						player.sendMessage(ChatColor.BLUE + "[Info]: " + ChatColor.AQUA + "Created Shield: "+ ChatColor.DARK_AQUA + sec + " Seconds! ");
						new BukkitRunnable() {
							int tick;

							@Override
							public void run() {
								if (tick >= sec * 20) {
									this.cancel();
								}
								CustomEffects.play_Suit_NoDamageTime(player, null);
								tick += 5;
							}
						}.runTaskTimer(plugin, 0, 5);
						SuitUtils.playSound(player, Values.SuitShieldSound, 2.0F, 2.0F);
					} else {
						SuitUtils.lack(player, "Energy");
					}
				}
			}
		}
	}

	@EventHandler
	public void LaunchFireball(PlayerInteractEvent clickevent) {
		Player player = clickevent.getPlayer();
		if (SuitUtils.isRightClick(clickevent)
				&& ItemUtil.checkItem(CustomSuitPlugin.missileLauncher, SuitUtils.getHoldingItem(player))) {
			Material ammoMaterial = Values.LauncherAmmo;
			ItemStack ammo = new ItemStack(ammoMaterial, 1);
			
			if (player.getInventory().contains(ammoMaterial, 1)) {
				player.getInventory().removeItem(ammo);
				player.updateInventory();

				SuitUtils.playSound(player, Values.LauncherSound, 5.0F, 5.0F);
				fireball(player);
			} else {
				SuitUtils.lack(player, "Missile");
			}
		}
	}

	private void fireball(Player player) {
		Location targetloc = SuitUtils.getTargetBlock(player, 1000).getLocation();
		Location locationplayer = player.getLocation();
		Vector vector = targetloc.toVector().subtract(locationplayer.toVector()).normalize().multiply(2);

		Fireball fireball = player.launchProjectile(Fireball.class, vector);

		fireball.setIsIncendiary(true);
		listFireball.add(fireball);

	}

	@EventHandler
	public void explodeFireball(ProjectileHitEvent event) {
		Projectile e = event.getEntity();
		if (e instanceof Fireball) {
			ProjectileSource s = e.getShooter();
			if (s != null && s instanceof Player && listFireball.contains(e)) {
				e.getWorld().createExplosion(e.getLocation(), Values.LauncherPower);
			}
		}
	}

	@EventHandler
	public void ThrowTNT(PlayerInteractEvent clickevent) {
		Material launcher = Values.SuitLauncher;
		if ((clickevent.getAction() == Action.LEFT_CLICK_AIR) || (clickevent.getAction() == Action.LEFT_CLICK_BLOCK)) {

			Player player = clickevent.getPlayer();

			Material ammo = Material.TNT;
			if (TNT_cooldowns != null) {
				if (TNT_cooldowns.contains(player)) {
					return;
				}
			}
			if (!player.isSneaking()) {
				return;
			}
			if (CustomSuitPlugin.isMarkEntity(player)) {
				if (SuitUtils.getHoldingItem(player).getType() == launcher) {
					if (player.getInventory().contains(ammo)) {
						removeItem(player, new ItemStack(ammo), 1);
						TNT_cooldowns.add(player);
						throwTNT(player, 10);
					} else {
						SuitUtils.lack(player, ammo.name());
					}
				}
			}
		}
	}

	private final float TNT_strength = 3;
	private void throwTNT(final Player player, int amount) {
		new BukkitRunnable() {
			int count = 0;

			@Override
			public void run() {
				if (count == 5) {
					TNT_cooldowns.remove(player);
					SuitUtils.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 10F, 0F);
					this.cancel();
				}
				Location location = player.getEyeLocation();
				shootTNT(player, location);
				count++;
			}
		}.runTaskTimer(plugin, 0, 4);
	}
	
	private void shootTNT(Player player, Location location){
		Vector v = player.getLocation().getDirection().multiply(TNT_strength).add(MathUtils.randomVector(0.5));
		ItemStack itemStack = new ItemStack(Material.TNT);
		ItemUtil.name(ChatColor.AQUA + "[Bomb]", itemStack);
		Enchant.enchantment(itemStack, new Glow(), 1, true);
		Item tnt = player.getWorld().dropItem(location, itemStack);

		tnt.setFallDistance(0);
		tnt.setVelocity(v);
		tnt.setPickupDelay(20);
		playTNTeffect(tnt);
	}
	
	@EventHandler
	public static void pickUpTNT(PlayerPickupItemEvent event) {
		Item item = event.getItem();
		ItemStack itemStack = item.getItemStack();
		if (itemStack.getItemMeta().getDisplayName() == null || itemStack.getType() != Material.TNT) {
			return;
		}
		if (itemStack.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "[Bomb]")) {
			Location location = item.getLocation();
			SuitUtils.createExplosion(location, 6F, true, true);
		}
	}

	private void playTNTeffect(final Item tnt) {
		new BukkitRunnable() {
			int count = 0;

			@Override
			public void run() {
				Location loc = tnt.getLocation();
				ParticleUtil.playEffect(Particle.EXPLOSION_NORMAL, loc, 1);
				if (count % 5 == 0) {
					SuitUtils.playSound(loc, Sound.BLOCK_DISPENSER_FAIL, 1F, 1F);
				}
				if (tnt.isDead() || tnt.isOnGround()) {
					SuitUtils.createExplosion(loc, 4F, true, true);
					this.cancel();
				}
				count++;

			}
		}.runTaskTimer(plugin, 0, 1);
	}

	private void removeItem(Player player, ItemStack itemStack, int amount) {
		Inventory inventory = player.getInventory();
		if (inventory.containsAtLeast(itemStack, amount)) {
			inventory.removeItem(itemStack);
		}
		player.updateInventory();
	}

	@EventHandler
	public void onPlayerLeftClick(PlayerInteractEvent e) {
		Material launcher = Values.SuitLauncher;
		if (SuitUtils.isLeftClick(e)) {

			Player player = e.getPlayer();
			String message = Values.BimMessage;

			int energy = Values.BimHunger;

			if (CustomSuitPlugin.isMarkEntity(player) && !player.isSneaking()
					&& SuitUtils.getHoldingItem(player).getType() == launcher) {

				if (HungerScheduler.deltaHunger(player, energy)) {
					launch(player, message);
				} else {
					SuitUtils.lack(player, "Energy");
				}
			}
		}
	}

	private void launch(Player player, String message) {
		Block targetblock = SuitUtils.getTargetBlock(player, 500);
		Location targetloc = targetblock.getLocation();
		Location loc = player.getLocation();
		
		playEffect(targetloc, loc, player, true);
		
//		double damage = Values.Bim * (CustomSuitPlugin.getLevel(player) / 32D + 1);
//		Fireball ball = player.launchProjectile(Fireball.class);
//		
	
		player.sendMessage(message);

	}

	private void playEffect(Location to, Location from, final Player player, boolean isMissile) {
		double radius, damage;
		int amount, data, effect_count;
		Particle effect;
		
		if(isMissile){
			radius = Values.BimRadius;
			damage = Values.Bim * ((CustomSuitPlugin.getSuitLevel(player) / 32D) + 1);
			amount = Values.BimEffectAmount;
			
			data = Values.SuitBim_MissileEffectData;
			effect = Values.SuitProjectileEffect;
			effect_count = 3;
		}else{ // sniper
			radius = Values.SniperRadius;
			damage = Values.SniperDamage;
			amount = Values.SniperEffectAmount; 
			
			data = Material.ANVIL.getId();
			effect = Values.SniperEffect;
			effect_count = 100;
		}
		
		SuitUtils.playSound(player, Sound.ENTITY_WITHER_SHOOT, 1F, 5F);
		SuitUtils.playSound(player, Sound.ENTITY_GENERIC_EXPLODE, 1F, 0F);
		SuitUtils.playSound(player, Sound.ENTITY_BLAZE_AMBIENT, 1F, -1F);
		SuitUtils.lineParticle(to, player.getEyeLocation(), player, effect, amount, data, damage, radius, true, 
				isMissile, player.isSneaking(), effect_count);
	}

	public static void breakblock(Block block) {
		Material material = block.getType();

		if (!Values.IgnoreMaterials_Gun.contains(material)) {
			ParticleUtil.playBlockEffect(Particle.BLOCK_CRACK, block.getLocation(), 10, 5D, block.getBlockData());
			block.breakNaturally();
		}
	}

	public static ArrayList<Entity> findEntity(Location currentLoc, Entity shooter, double radius) {
		Collection<Entity> near = currentLoc.getWorld().getNearbyEntities(currentLoc, radius, radius, radius,
				e -> e instanceof Damageable);
		near.remove(shooter);
		if (shooter.isInsideVehicle()) {
			near.remove(shooter.getVehicle());
		}
		// currentLoc.getWorld().getNearbyEntities(arg0, arg1, arg2, arg3);
		ArrayList<Entity> list = new ArrayList<>();
		for (Entity entity : near) {
			if (MathUtils.distanceBody(currentLoc, entity, radius) && !list.contains(entity)) {
				list.add(entity);
			}
		}
		return list;
	}

	public static void firework(Location location, Entity shooter) {
		FireworkEffect effect = FireworkProccesor.getRandomEffect();
		Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
		FireworkMeta meta = firework.getFireworkMeta();
		meta.addEffect(effect);
		meta.setPower((int) (MathUtils.randomRadius(3) + 1.5));
		firework.setFireworkMeta(meta);
		if (shooter != null) {
			SuitUtils.playSound(shooter, Sound.ENTITY_GENERIC_EXPLODE, 14.0F, 14.0F);
			SuitUtils.playSound(shooter, Sound.ENTITY_WITHER_DEATH, 14.0F, 14.0F);
		}
	}

	@EventHandler
	public void damageByGun(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Snowball) {
			Snowball snowball = (Snowball) event.getDamager();
			Entity shooter = (Entity) snowball.getShooter();
			if (shooter instanceof Player && Gun_Effect.snowballs.contains(snowball)) {
				event.setDamage(Values.MachineGunDamage);
				Gun_Effect.removed.add(snowball);
			}
		}
	}

	@EventHandler
	public void hit(ProjectileHitEvent event) {
		if (event.getEntity() instanceof Snowball) {

			Snowball snowball = (Snowball) event.getEntity();
			Entity shooter = (Entity) snowball.getShooter();
			if (shooter instanceof Player && Gun_Effect.snowballs.contains(snowball)) {
				Gun_Effect.removed.add(snowball);
			}
		}
	}

	public static boolean isCharging(Player player) {
		return charging.containsKey(player) && charging.get(player);
	}

	@EventHandler
	public void gun(PlayerInteractEvent clickevent) throws InterruptedException {
		Player player = clickevent.getPlayer();
		ItemStack Gun = CustomSuitPlugin.getGun();

		if (SuitUtils.isRightClick(clickevent) && CustomSuitPlugin.isMarkEntity(player)
				&& SuitWeapons.checkGun(player, Gun)) {
			ItemStack gunInHand = SuitUtils.getHoldingItem(player);
			String name = gunInHand.getItemMeta().getDisplayName();
			String[] values = name.split(gun_regex);
			values[0] = values[0].replace(ChatColor.YELLOW + Values.MachineGunName + "«", "");
			String gunfirstName = ChatColor.YELLOW + Values.MachineGunName + " «";
			ItemStack copy = gunInHand;
			boolean isSniper = player.isSneaking();

			if (!isSniper) {
				if ((Integer.parseInt(values[0].replace(gunfirstName, "")) <= 0)) {

					if (!player.getInventory().contains(Material.FLINT, 1)) {
						SuitUtils.lack(player, "Machine Gun Ammo");
						return;
					} else if (!isCharging(player)) {

						int amount = SuitWeapons.charge(player, Material.FLINT, Values.MachineGunAmmoAmount, plugin);
						ItemUtil.name(gunfirstName + amount + gun_regex + values[1].replace("»", "") + "»", copy);

						SuitUtils.setHoldingItem(player, copy);
					}
				}

				if (isCharging(player)) {
					return;
				}
				
				CustomEffects.play_Gun_Shot_Effect(player);
				shot_Machine_Gun(player);
				ItemUtil.name(gunfirstName + (Integer.parseInt(values[0].replace(gunfirstName, "")) - 1) + gun_regex
						+ values[1].replace("»", "") + "»", copy);

				SuitUtils.setHoldingItem(player, copy);

			} else {
				if ((Integer.parseInt(values[1].replace("»", "")) <= 0)) {

					if (!player.getInventory().contains(Material.GHAST_TEAR, 1)) {
						SuitUtils.lack(player, "Sniper Ammo");
						return;
					} else if (!isCharging(player)) {

						int amount = SuitWeapons.charge(player, Material.GHAST_TEAR, Values.SnipeAmmoAmount, plugin);
						ItemUtil.name(values[0] + gun_regex + amount + "»", copy);

						SuitUtils.setHoldingItem(player, copy);
					}
				}
				if (isCharging(player)) {
					return;
				}
				player.getInventory().remove(new ItemStack(Material.GHAST_TEAR, 1));

				Block targetblock = SuitUtils.getTargetBlock(player, Values.Suit_Gun_Shot_Radius);
				Location target = targetblock.getLocation();
				if (!SuitWeapons.isInCooldown(player)) {
					Location location = player.getEyeLocation();
					Vector direction = location.getDirection();
					location.setDirection(
							MathUtils.randomLoc(target.clone().subtract(location), 5).toVector().normalize());
					if (PlayerEffect.Zoom.containsKey(player) && PlayerEffect.Zoom.get(player)) {
						location.setDirection(direction);
					}

					SuitUtils.playSound(player, Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 3.0F, 3.0F);
					SuitUtils.playSound(player, Sound.BLOCK_ANVIL_LAND, 0.3F, 0.3F);
					SuitUtils.playSound(player, Sound.ENTITY_GENERIC_EXPLODE, 23.0F, 21.0F);
					SuitUtils.playSound(player, Sound.ENTITY_IRON_GOLEM_HURT, 4.0F, 4.0F);

					CustomEffects.play_Gun_Shot_Effect(player);
					SuitUtils.lineParticle(target, location, player, Values.SniperEffect, 1, 0, Values.SniperDamage,
							0.5, true, false, false, 20);

					SuitWeapons.cooldown(2, plugin, player);
					ItemUtil.name(values[0] + gun_regex + (Integer.parseInt(values[1].replace("»", "")) - 1) + "»",
							copy);
					SuitUtils.setHoldingItem(player, copy);
				}
			}
			player.updateInventory();
		}
	}
	
	private void shot_Machine_Gun(final Player player) {
		double spread = 0.3;
		shoot(player, spread);
		recoil(player, 0.5);

		if (PlayerEffect.Zoom.containsKey(player) && PlayerEffect.Zoom.get(player)) {
			spread = MathUtils.randomRadius(0.025);
		}

		final double Spread = spread;
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> shoot(player, Spread), 3L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> shoot(player, Spread), 3L);

		if (!Gun_Effect.isRunning) {
			new Gun_Effect(plugin).runTaskTimer(plugin, 0, 2);
		}
	}

	public static boolean checkGun(Player player, ItemStack sample) {
		ItemStack itemInHand = SuitUtils.getHoldingItem(player);
		return ItemUtil.checkName(itemInHand, gun_regex) && sample.getType() == itemInHand.getType();
	}

	public static int charge(final Player player, Material ammomat, int amount, final CustomSuitPlugin plugin) {
		int ammoamount = 0;
		ItemStack ammo = new ItemStack(ammomat, 1);
		if (player.getInventory().contains(ammomat, 1)) {
			charging.put(player, true);
			for (int i = 0; i < amount; i++) {
				if (player.getInventory().contains(ammomat, 1)) {
					player.getInventory().removeItem(ammo);
					ammoamount++;
				}
			}
	
			player.updateInventory();
			SuitUtils.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 4.0F, 1.0F);
	
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, ()->{
				Location loc = player.getLocation();
				SuitUtils.playSound(loc, Sound.BLOCK_ANVIL_LAND, 4.0F, 4.0F);
				SuitUtils.playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 4.0F, 4.0F);
				SuitUtils.playSound(loc, Sound.ENTITY_VILLAGER_HURT, 4.0F, 4.0F);
				SuitUtils.playSound(loc, Sound.UI_BUTTON_CLICK, 4.0F, 4.0F);
				SuitUtils.playSound(loc, Sound.ENTITY_CREEPER_PRIMED, 4.0F, 4.0F);
			}, 40);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, ()->{
				Location loc = player.getLocation();
				SuitUtils.playSound(loc, Sound.ENTITY_IRON_GOLEM_HURT, 4.0F, 4.0F);
				SuitUtils.playSound(loc, Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 15.0F, 3.5F);
				SuitUtils.playSound(loc, Sound.BLOCK_IRON_DOOR_CLOSE, 4.0F, 2.5F);
				SuitUtils.playSound(loc, Sound.BLOCK_IRON_DOOR_OPEN, 4.0F, 4.0F);
				charging.put(player, false);
			}, 65);
	
		} else {
			SuitUtils.lack(player, "Ammo");
			player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 6.0F, 6.0F);
		}
		return ammoamount;
	}

	public static void cooldown(double msec, CustomSuitPlugin plugin, Player player) {
		// 1 tick = 0.05 sec
		// 1 sec = 20 tick
		long tick = (long) msec * 20;
	
		cooldowns.put(player, true);
		new BukkitRunnable() {
	
			@Override
			public void run() {
				Location loc = player.getLocation();
				SuitUtils.playSound(loc, Sound.ENTITY_IRON_GOLEM_HURT, 4.0F, 4.0F);
				SuitUtils.playSound(loc, Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 15.0F, 3.5F);
				SuitUtils.playSound(loc, Sound.BLOCK_WOODEN_DOOR_CLOSE, 4.0F, 2.5F);
				SuitUtils.playSound(loc, Sound.BLOCK_WOODEN_DOOR_OPEN, 4.0F, 4.0F);
				cooldowns.put(player, false);
			}
		}.runTaskLater(plugin, tick);
	}

	public static boolean isInCooldown(Player player) {
		return cooldowns.containsKey(player) && cooldowns.get(player);
	}

	public static boolean isChargingGun(Player player) {		
		return charging.containsKey(player) && charging.get(player);
	}

	private static void playGunSound(Player player) {
		Location loc = player.getLocation();
		SuitUtils.playSound(loc, Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 3.0F, 3.0F);
		SuitUtils.playSound(loc, Sound.BLOCK_ANVIL_LAND, 1.0F, 1.0F);
		SuitUtils.playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);
		SuitUtils.playSound(loc, Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 3.0F, 3.0F);
		SuitUtils.playSound(loc, Sound.BLOCK_ANVIL_LAND, 0.3F, 0.3F);
		SuitUtils.playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 23.0F, 21.0F);
		SuitUtils.playSound(loc, Sound.ENTITY_IRON_GOLEM_HURT, 4.0F, 4.0F);
	}

	private static void shoot(Player player, double spread) {
		playGunSound(player);

		Snowball snowball = player.launchProjectile(Snowball.class);
//		enshroud(snowball);;
		Vector v = player.getLocation().getDirection();
		v.multiply(3.0);
		v.add(MathUtils.randomVector(spread));

		snowball.setVelocity(v);

		Gun_Effect.snowballs.add(snowball);

		CustomEffects.play_Gun_Shot_Effect(player);
	}

	private static void recoil(Player player, double amplitude) {
		Vector direction = player.getLocation().getDirection().multiply(-amplitude);
		direction.setY(0.1);
		player.setVelocity(direction);
	}
}
