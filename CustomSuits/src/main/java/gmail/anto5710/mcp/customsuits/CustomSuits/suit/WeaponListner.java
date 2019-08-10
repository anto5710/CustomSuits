package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.CustomSuits.FireworkPlay;
import gmail.anto5710.mcp.customsuits.CustomSuits.FireworkProccesor;
import gmail.anto5710.mcp.customsuits.CustomSuits.PlayEffect;
import gmail.anto5710.mcp.customsuits.Setting.Enchant;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.Glow;
import gmail.anto5710.mcp.customsuits.Utils.MathUtils;
import gmail.anto5710.mcp.customsuits.Utils.PacketUtil;
import gmail.anto5710.mcp.customsuits.Utils.ParticleUtil;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.WeaponUtils;
import net.minecraft.server.v1_13_R2.Items;
import net.minecraft.server.v1_13_R2.PacketPlayOutEntityDestroy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftSnowball;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class WeaponListner implements Listener {

	static int maxformachine = Values.MachineGunAmmoAmount;
	static int maxforsniper = Values.SnipeAmmoAmount;
	static ArrayList<Fireball> listFireball = new ArrayList<>();

	String regex = Values.regex;

	public static CustomSuitPlugin plugin;

	public static HashMap<Player, Boolean> charging = new HashMap<>();
	public static HashMap<Player, Boolean> cooldowns = new HashMap<>();

	public static HashSet<Player> TNT_cooldowns = new HashSet<>();

	public WeaponListner(CustomSuitPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void cancelTarget(EntityTargetEvent event) {
		Entity t = event.getTarget();
		Entity e = event.getEntity();
		if (t instanceof Player && CustomSuitPlugin.dao.isCreatedBy(e, (Player) t)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void cancel_Target_Owner(EntityDamageByEntityEvent event) {
		Entity entity = event.getDamager();
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (CustomSuitPlugin.isCreatedBy(entity, player)) {
				SuitSettings hdle = CustomSuitPlugin.hdle(player);
				if (hdle.isTargetting(entity)) {
					LivingEntity target = hdle.getCurrentTarget();
					((Creature) entity).setTarget(target);
				}
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void damage_By_Projectile(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Projectile) {
			if (event.getEntity() instanceof Player) {
				Projectile projectile = (Projectile) event.getDamager();
				Player player = (Player) event.getEntity();
				if (projectile.getShooter() == null) {
					return;
				}

				if (projectile.getShooter() instanceof LivingEntity) {
					LivingEntity lentity = (LivingEntity) projectile.getShooter();
					CustomSuitPlugin.hdle(player).putTarget(lentity);
				}
			}
		}
	}

	@EventHandler
	public void targetToProtect(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (CustomSuitPlugin.isMarkEntity(player) && event.getDamager() instanceof LivingEntity) {
				if (!CustomSuitPlugin.isCreatedBy(event.getDamager(), player)) {
					LivingEntity lentity = (LivingEntity) event.getDamager();
					CustomSuitPlugin.hdle(player).putTarget(lentity);
				}
			}
		}
	}

	@EventHandler
	public void cooperate(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if (CustomSuitPlugin.isMarkEntity(player) && !CustomSuitPlugin.isCreatedBy(event.getEntity(), player) && event.getEntity() instanceof LivingEntity) {
				LivingEntity lentity = (LivingEntity) event.getEntity();
				CustomSuitPlugin.hdle(player).putTarget(lentity);
			}
		}
	}

	@EventHandler
	public void interectshield(PlayerInteractEvent event) {
		final Player player = event.getPlayer();
		if (SuitUtils.isRightClick(event)) {
			if (CustomSuitPlugin.isMarkEntity(player)) {
				ItemStack itemInHand = SuitUtils.getHoldingItem(player);
				if (itemInHand != null) {
					if (itemInHand.getType() == Values.SuitLauncher) {
						if (HungerScheduler.addHunger(player, Values.SuitShieldHunger)) {

							final int sec = (CustomSuitPlugin.getLevel(player)) / 20 + 3;

							player.setNoDamageTicks(sec * 20);
							player.sendMessage(ChatColor.BLUE + "[Info]: " + ChatColor.AQUA + "Created Shield: "
									+ ChatColor.DARK_AQUA + sec + " Seconds! ");
							new BukkitRunnable() {
								int tick;

								@Override
								public void run() {
									if (tick >= sec * 20) {
										this.cancel();
									}
									PlayEffect.play_Suit_NoDamageTime(player, null);
									tick += 5;
								}
							}.runTaskTimer(plugin, 0, 5);
							player.playSound(player.getLocation(), Values.SuitShieldSound, 2.0F, 2.0F);
						} else {

							SuitUtils.lack(player, "Energy");
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void LaunchFireball(PlayerInteractEvent clickevent) {
		Player player = clickevent.getPlayer();
		if (clickevent.getAction() == Action.RIGHT_CLICK_AIR || clickevent.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (SuitUtils.checkItem(CustomSuitPlugin.missileLauncher, SuitUtils.getHoldingItem(player))) {
				Material ammoMaterial = Values.LauncherAmmo;

				ItemStack ammo = new ItemStack(ammoMaterial, 1);
				if (player.getInventory().contains(ammoMaterial, 1)) {
					player.getInventory().removeItem(ammo);
					player.updateInventory();

					player.playSound(player.getLocation(), Values.LauncherSound, 5.0F, 5.0F);
					fireball(player);
				} else {
					SuitUtils.lack(player, "Missile");
				}
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
	public void ENTITY_GENERIC_EXPLODEFireball(ProjectileHitEvent event) {
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

	private void throwTNT(final Player player, int amount) {
		final float strength = 3;
		new BukkitRunnable() {
			int count = 0;

			@Override
			public void run() {
				if (count == 5) {
					TNT_cooldowns.remove(player);
					player.playSound(player.getLocation(), Sound.BLOCK_PISTON_CONTRACT, 10F, 0F);
					this.cancel();
				}
				Location location = player.getEyeLocation();
				Vector velocity = player.getLocation().getDirection().multiply(strength).add(getRandomVector(0.5));
				ItemStack itemStack = new ItemStack(Material.TNT);
				CustomSuitPlugin.name(ChatColor.AQUA + "[Bomb]", itemStack);
				Enchant.enchantment(itemStack, new Glow(), 1, true);
				Item tnt = player.getWorld().dropItem(location, itemStack);

				tnt.setFallDistance(0);
				tnt.setVelocity(velocity);
				tnt.setPickupDelay(20);
				playTNTeffect(tnt);
				count++;
			}
		}.runTaskTimer(plugin, 0, 4);
	}

	@EventHandler
	public static void PickUpTNT(PlayerPickupItemEvent event) {
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

				Location location = tnt.getLocation();
				ParticleUtil.playEffect(Particle.EXPLOSION_NORMAL, location, 1);
				if (count % 5 == 0) {
					location.getWorld().playSound(location, Sound.BLOCK_DISPENSER_FAIL, 1F, 1F);
				}
				if (tnt.isDead()) {
					SuitUtils.createExplosion(location, 4F, true, true);
					this.cancel();
				}
				if (tnt.isOnGround()) {
					SuitUtils.createExplosion(location, 4F, true, true);
					this.cancel();
				}
				count++;

			}
		}.runTaskTimer(plugin, 0, 1);
	}

	private Vector getRandomVector(double r) {
		double x = MathUtils.randomRadius(r);
		double y = MathUtils.randomRadius(r);
		double z = MathUtils.randomRadius(r);

		return new Vector(x, y, z);
	}

	private void removeItem(Player player, ItemStack itemStack, int amount) {
		Inventory inventory = player.getInventory();
		if (inventory.containsAtLeast(itemStack, amount)) {
			inventory.removeItem(itemStack);
		}
		player.updateInventory();
	}

	@EventHandler
	public void onPlayerLeftClick(PlayerInteractEvent clickevent) {
		Material launcher = Values.SuitLauncher;
		if ((clickevent.getAction() == Action.LEFT_CLICK_AIR) || (clickevent.getAction() == Action.LEFT_CLICK_BLOCK)) {

			Player player = clickevent.getPlayer();
			String message = Values.BimMessage;

			int energy = Values.BimHunger;

			if (CustomSuitPlugin.isMarkEntity(player) && !player.isSneaking()) {
				if (player.getItemInHand().getType() == launcher) {
					if (HungerScheduler.addHunger(player, energy)) {
						launch(player, message, energy);
					} else {
						SuitUtils.lack(player, "Energy");
					}

				}

			}

		}
	}

	private void launch(Player player, String message, int energy) {

		Location loc = player.getEyeLocation();

		Block targetblock = SuitUtils.getTargetBlock(player, 500);
		Location targetloc = targetblock.getLocation();

		playEffect(targetloc, loc, player, true);
		player.sendMessage(message);

	}

	private void playEffect(Location to, Location from, final Player player, boolean isMissile) {

		double radius = Values.BimRadius;
		double damage = Values.Bim;
		int amount = Values.BimEffectAmount;
		if (isMissile) {

			damage = damage * (CustomSuitPlugin.getLevel(player) / 32 + 1);
		} else {

			radius = Values.SniperRadius;
			damage = Values.SniperDamage;
			amount = Values.SniperEffectAmount;
		}

		Particle effect = Values.SniperEffect;
		int data = Material.ANVIL.getId();
		int effect_count = 100;
		if (isMissile) {
			effect_count = 3;
			effect = Values.SuitProjectileEffect;
			data = Values.SuitBim_MissileEffectData;
		}

		final double Radius = radius;
		final boolean IsMissile = isMissile;
		final Location To = to;
		final Particle Effect = effect;
		final int Amount = amount;
		final int Data = data;
		final double Damage = damage;
		final int effect_Count = effect_count;

		player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 1F, 5F);
		player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1F, 0F);
		player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1F, -1F);
		SuitUtils.LineParticle(To, player.getEyeLocation(), player, Effect, Amount, Data, Damage, Radius, true,
				IsMissile, player.isSneaking(), effect_Count);
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
			if (MathUtils.distance(currentLoc, entity, radius)) {
				if (!list.contains(entity)) {
					list.add(entity);
				}
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
			location.getWorld().playSound(shooter.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 14.0F, 14.0F);

			location.getWorld().playSound(shooter.getLocation(), Sound.ENTITY_WITHER_DEATH, 14.0F, 14.0F);
		}
	}

	@EventHandler
	public void damageByGun(EntityDamageByEntityEvent event) {
		if (event.getDamager().getType() == EntityType.SNOWBALL) {

			Snowball snowball = (Snowball) event.getDamager();
			Entity shooter = (Entity) snowball.getShooter();
			if (shooter instanceof Player) {
				if (Gun_Effect.snowballs.contains(snowball)) {
					event.setDamage(Values.MachineGunDamage);
					Gun_Effect.removed.add(snowball);
				}
			}
		}

	}

	@EventHandler
	public void hit(ProjectileHitEvent event) {
		if (event.getEntity().getType() == EntityType.SNOWBALL) {

			Snowball snowball = (Snowball) event.getEntity();
			Entity shooter = (Entity) snowball.getShooter();
			if (shooter instanceof Player) {
				if (Gun_Effect.snowballs.contains(snowball)) {
					Gun_Effect.removed.add(snowball);
				}
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

		if (SuitUtils.isRightClick(clickevent)) {
			if (CustomSuitPlugin.isMarkEntity(player)) {

				if (WeaponUtils.checkgun(player, Gun)) {
					ItemStack gunInHand = SuitUtils.getHoldingItem(player);
					String name = gunInHand.getItemMeta().getDisplayName();
					String[] values = name.split(regex);
					values[0] = values[0].replace(ChatColor.YELLOW + "Knif-1220" + "«", "");
					String gunfirstName = ChatColor.YELLOW + "Knif-1220 " + "«";
					ItemStack copy = gunInHand;
					boolean isSniper = player.isSneaking();

					if (!isSniper) {
						if ((Integer.parseInt(values[0].replace(gunfirstName, "")) <= 0)) {

							if (!player.getInventory().contains(Material.FLINT, 1)) {
								SuitUtils.lack(player, "Machine Gun Ammo");
								return;
							} else if (!isCharging(player)) {

								int amount = WeaponUtils.charge(player, Material.FLINT, Values.MachineGunAmmoAmount,
										plugin);
								CustomSuitPlugin.name(
										gunfirstName + amount + regex + values[1].replace("»", "") + "»", copy);

								SuitUtils.setHoldingItem(player, copy);
							}
						}

						if (isCharging(player)) {
							return;
						}
						PlayEffect.play_Gun_Shot_Effect(player);
						shot_Machine_Gun(player);
						CustomSuitPlugin.name(
								gunfirstName + (Integer.parseInt(values[0].replace(gunfirstName, "")) - 1) + regex
										+ values[1].replace("»", "") + "»",
								copy);

						SuitUtils.setHoldingItem(player, copy);

					} else {
						if ((Integer.parseInt(values[1].replace("»", "")) <= 0)) {

							if (!player.getInventory().contains(Material.GHAST_TEAR, 1)) {
								SuitUtils.lack(player, "Sniper Ammo");
								return;
							} else if (!isCharging(player)) {

								int amount = WeaponUtils.charge(player, Material.GHAST_TEAR, Values.SnipeAmmoAmount,
										plugin);
								CustomSuitPlugin.name(values[0] + regex + amount + "»", copy);

								SuitUtils.setHoldingItem(player, copy);
							}
						}
						if (isCharging(player)) {
							return;
						}
						player.getInventory().remove(new ItemStack(Material.GHAST_TEAR, 1));

						Block targetblock = SuitUtils.getTargetBlock(player, Values.Suit_Gun_Shot_Radius);
						Location target = targetblock.getLocation();
						if (!WeaponUtils.isCooldown(player)) {
							Location location = player.getEyeLocation();
							Vector direction = location.getDirection();
							location.setDirection(WeaponUtils.setRandomLoc(target.clone().subtract(location), 10)
									.toVector().normalize());
							if (PlayerEffect.Zoom.containsKey(player)) {
								if (PlayerEffect.Zoom.get(player)) {
									location.setDirection(direction);
								}
							}

							player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 3.0F, 3.0F);
							player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.3F, 0.3F);
							player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 23.0F, 21.0F);
							player.playSound(player.getLocation(), Sound.ENTITY_IRON_GOLEM_HURT, 4.0F, 4.0F);

							PlayEffect.play_Gun_Shot_Effect(player);
							SuitUtils.LineParticle(target, location, player, Values.SniperEffect, 1, 0,
									Values.SniperDamage, 0.5, true, false, false, 20);

							WeaponUtils.cooldown(2, plugin, player);
							CustomSuitPlugin.name(
									values[0] + regex + (Integer.parseInt(values[1].replace("»", "")) - 1) + "»", copy);
							SuitUtils.setHoldingItem(player, copy);
						}
					}
					player.updateInventory();
				}
			}
		}
	}

	// player.playSound(player.getLocation(),
	// Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 3.0F, 3.0F);
	// player.playSound(player.getLocation(),
	// Sound.BLOCK_ANVIL_LAND, 0.3F, 0.3F);
	// player.playSound(player.getLocation(),
	// Sound.ENTITY_GENERIC_EXPLODE, 23.0F, 21.0F);
	// player.playSound(player.getLocation(),
	// Sound.ENTITY_IRON_GOLEM_HURT, 4.0F, 4.0F);

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

	private static void playGunSound(Player player) {
		Location loc = player.getLocation();
		player.playSound(loc, Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 3.0F, 3.0F);
		player.playSound(loc, Sound.BLOCK_ANVIL_LAND, 1.0F, 1.0F);
		player.playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);
		player.playSound(loc, Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 3.0F, 3.0F);
		player.playSound(loc, Sound.BLOCK_ANVIL_LAND, 0.3F, 0.3F);
		player.playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 23.0F, 21.0F);
		player.playSound(loc, Sound.ENTITY_IRON_GOLEM_HURT, 4.0F, 4.0F);
	}

	private static void shoot(Player player, double spread) {
		playGunSound(player);

		Snowball snowball = player.launchProjectile(Snowball.class);
		enshroud(snowball);;
		Vector v = player.getLocation().getDirection();
		v.multiply(3.0);
		v.add(MathUtils.randomVector(spread));

		snowball.setVelocity(v);

		Gun_Effect.snowballs.add(snowball);

		PlayEffect.play_Gun_Shot_Effect(player);
	}
	
	private static void enshroud(Entity e){
		PacketUtil.castDestroyPacket(e);
	}

	private static void recoil(Player player, double amplitude) {
		Vector direction = player.getLocation().getDirection().multiply(-amplitude);
		direction.setY(0.1);
		player.setVelocity(direction);
	}
}
