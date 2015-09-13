package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.WeaponUtils;

import java.awt.Color;
import java.net.Proxy.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.naming.ldap.Rdn;
import javax.print.DocFlavor.CHAR_ARRAY;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Firework;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.hamcrest.core.IsSame;

import com.google.common.primitives.Ints;

public class WeaponListner implements Listener {

	static int maxformachine = Values.MachineGunAmmoAmount;
	static int maxforsniper = Values.SnipeAmmoAmount;

	static double damage = 0;
	public static double radius = 0;
	static int amount = 0;
	float power = 0;
	static int effectradius = 0;

	String regex = Values.regex;

	public static CustomSuitPlugin plugin;

	public static HashMap<Player, Boolean> charging = new HashMap<>();
	public static HashMap<Player, Boolean> cooldowns = new HashMap<>();

	public WeaponListner(CustomSuitPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void interectshield(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.RIGHT_CLICK_AIR
				|| event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (CustomSuitPlugin.MarkEntity(player)) {

				if (player.getItemInHand() != null) {
					if (player.getItemInHand().getType() == Values.SuitLauncher) {
						if (SchedulerHunger.hunger(player, Values.SuitShieldHunger)) {

							player.setNoDamageTicks(200 * CustomSuitPlugin
									.getLevel(player));

							double sec = (CustomSuitPlugin.getLevel(player)) / 20 + 3;
							SuitUtils.playEffect(player.getLocation(),
									Effect.TILE_BREAK, 600, 0, 400);

							player.sendMessage(ChatColor.BLUE + "[Info]: "
									+ ChatColor.AQUA + "No Damage Time for: "
									+ ChatColor.DARK_AQUA + sec + " Seconds! ");

							player.playSound(player.getLocation(), Values.SuitShieldSound,
									2.0F, 2.0F);
						} else {

							SuitUtils.Wrong(player, "Energy");
						}
					}

				}

			}
		}
	}

	@EventHandler
	public void LaunchFireball(PlayerInteractEvent clickevent) {
		Player player = clickevent.getPlayer();
		if (clickevent.getAction() == Action.RIGHT_CLICK_AIR
				|| clickevent.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (SuitUtils.CheckItem(CustomSuitPlugin.missileLauncher,
					player.getItemInHand())) {
				Material ammoMaterial = Values.LauncherAmmo;

				ItemStack ammo = new ItemStack(ammoMaterial, 1);
				if (player.getInventory().contains(ammoMaterial, 1)) {
					player.getInventory().removeItem(ammo);
					player.updateInventory();

					player.playSound(player.getLocation(),
						Values.LauncherSound, 5.0F, 5.0F);
					fireball(player);
				} else {
					SuitUtils.Wrong(player, "Missile");
				}
			}

		}

	}

	private void fireball(Player player) {
		Location targetloc = SuitUtils.getTargetBlock(player, 10000).getLocation();
		Location locationplayer = player.getLocation();
		Vector vector = targetloc.toVector()
				.subtract(locationplayer.toVector()).normalize().multiply(2);

		Fireball fireball = player.launchProjectile(Fireball.class, vector);

		fireball.setIsIncendiary(true);

	}

	@EventHandler
	public void explodeFireball(ProjectileHitEvent event) {
		if (event.getEntity() instanceof Fireball) {
			if (event.getEntity().getShooter() != null) {
				if (event.getEntity().getShooter() instanceof Player) {

					Player player = (Player) event.getEntity().getShooter();
					if (SuitUtils.CheckItem(CustomSuitPlugin.missileLauncher,
							player.getItemInHand())) {

						event.getEntity()
								.getWorld()
								.createExplosion(
										event.getEntity().getLocation(),
										Values.LauncherPower);
					}
				}
			}
		}

	}

	@EventHandler
	public void onPlayerLeftClick(PlayerInteractEvent clickevent) {
		Material launcher = Values.SuitLauncher;
		if ((clickevent.getAction() == Action.LEFT_CLICK_AIR)
				|| (clickevent.getAction() == Action.LEFT_CLICK_BLOCK)) {

			Player player = clickevent.getPlayer();
			String message = Values.BimMessage;

			Location loc = player.getEyeLocation();
			int energy = Values.BimHunger;
			Sound sound = Values.BimSound;
			

			if (CustomSuitPlugin.MarkEntity(player)) {
				if (player.getItemInHand().getType() == launcher) {
					if (player.isSneaking()) {
						energy = Values.MissileHunger;
						sound = Values.MissileSound;
						message = Values.MissileMessage;
					}
					if (SchedulerHunger.hunger(player, energy)) {
						launch(player, sound, message, energy);
					} else {
						SuitUtils.Wrong(player, "Energy");
					}

				}

			}

		}
	}

	private void launch(Player player, Sound sound, String message, int energy) {

		Location loc = player.getEyeLocation();

		Block targetblock = SuitUtils.getTargetBlock(player, 500);
		Location targetloc = targetblock.getLocation();

		playEffect(targetloc, loc, player, true);
		player.sendMessage(message);

		player.playSound(loc, sound, 4.0F, 4.0F);

	}

	private void playEffect(Location to, Location from,
			Player player, boolean isMissile) {

		setOption(isMissile, player);

		damage = damage * (CustomSuitPlugin.getLevel(player)  + 1);

		Effect effect = Values.SuitProjectileEffect;
		int data = Material.ANVIL.getId();
		if (isMissile) {

			data =Values.SuitBim_MissileEffectData;
		}

		SuitUtils.LineParticle(to, from, player, effect, amount,
				data, effectradius, damage, 200, isMissile);
		if (isMissile) {
			SuitUtils.createExplosion(to, power, false, true);

		} else {
			breakblock(to.getBlock());

		}

	}

	private void setOption(boolean isMissile, Player player) {

		if (isMissile) {
			if (!player.isSneaking()) {
				radius = Values.BimRadius;
				damage = Values.Bim;
				power = Values.BimExplosionPower;
				amount = Values.BimEffectAmount;
				effectradius = Values.BimEffectRadius;

			} else {
				radius = Values.MissileRadius;
				damage = Values.Missile;
				power = Values.MissileExplosionPower;
				amount = Values.MissileEffectAmount;
				effectradius = Values.MissileEffectRadius;

			}
		} else {

			radius = Values.SniperRadius;
			damage = Values.SniperDamage;
			amount = Values.SniperEffectAmount;
			effectradius = Values.SniperEffectRadius;
		}
	}

	

	public static void breakblock(Block block) {
		Material material = block.getType();
		if (material != Material.AIR && material != Material.BEDROCK
				&& material != Material.OBSIDIAN && material != Material.WATER
				&& material != Material.LAVA) {

			block.breakNaturally();
			SuitUtils.playEffect(block.getLocation(), Effect.TILE_BREAK, 50,
					material.getId(), 10);
		
		}
	}

	public static ArrayList<Entity> findEntity(Location currentLoc,
			Player player, double radius) {

		Collection<Entity> near = currentLoc.getWorld().getEntities();
		ArrayList<Entity> list = new ArrayList<>();
		for (Entity entity : near) {
			if (entity instanceof Damageable && entity != player
					&& entity != player.getVehicle()
					&& SuitUtils.distance(currentLoc, entity, radius)) {
				list.add(entity);

			}
		}
		return list;

	}



	public static void firework(Location location, Player player) {

		SuitUtils.spawnFirework(org.bukkit.Color.RED,
				org.bukkit.FireworkEffect.Type.STAR, 2, true, true,
				org.bukkit.Color.WHITE, location);

		player.playSound(player.getLocation(), Sound.EXPLODE, 14.0F, 14.0F);

		player.playSound(player.getLocation(), Sound.WITHER_DEATH, 14.0F, 14.0F);

	}



	@EventHandler
	public void charge(PlayerInteractEvent clickevent) {
		Player player = clickevent.getPlayer();
		ItemStack item = CustomSuitPlugin.getGun();

		if (clickevent.getAction() == Action.RIGHT_CLICK_AIR
				|| clickevent.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (CustomSuitPlugin.MarkEntity(player)) {

				if (WeaponUtils.checkgun(player, player.getItemInHand(), item)) {
					String name = item.getItemMeta().getDisplayName();
					String gunname = player.getItemInHand().getItemMeta()
							.getDisplayName();
					String[] values = name.split(regex);

					String[] names = gunname.split(regex);
					if (values[0].endsWith(names[0])) {
						int cnt = Integer.parseInt(names[1]);
						int snipe = Integer.parseInt(names[2]);
						if (cnt == 0 && !player.isSneaking()) {

							int ammoamount = WeaponUtils.charge(player, names[0],
									Material.FLINT, maxformachine, cnt, snipe);
							CustomSuitPlugin.SetDisplayName((names[0] + regex
									+ ammoamount + regex + snipe),
									player.getItemInHand());
						}
						if (snipe == 0 && player.isSneaking()) {
							int ammoamount = WeaponUtils.charge(player, names[0],
									Material.GHAST_TEAR, maxforsniper, cnt,
									snipe);

							CustomSuitPlugin.SetDisplayName((names[0] + regex
									+ cnt + regex + ammoamount),
									player.getItemInHand());

						}
					}
				}
			}
		}
	}

	@EventHandler
	public void damageByGun(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		if (damager.getType() == EntityType.SNOWBALL) {
			Snowball snowball = (Snowball) damager;
			Entity shooter = (Entity) snowball.getShooter();
			if (shooter instanceof Player) {
				if (WeaponUtils.checkgun(((Player) shooter),
						((Player) shooter).getItemInHand(),
						CustomSuitPlugin.gunitem)) {
					event.setDamage(Values.MachineGunDamage);

				}
			}
		}

	}

	@EventHandler
	public void gun(PlayerInteractEvent clickevent) {
		Player player = clickevent.getPlayer();
		ItemStack item = CustomSuitPlugin.getGun();

		if (clickevent.getAction() == Action.RIGHT_CLICK_AIR
				|| clickevent.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (CustomSuitPlugin.MarkEntity(player)) {

				if (WeaponUtils.checkgun(player, player.getItemInHand(), item)) {
					String name = item.getItemMeta().getDisplayName();
					String gunname = player.getItemInHand().getItemMeta()
							.getDisplayName();
					String[] values = name.split(regex);

					String[] names = gunname.split(regex);
					if (values[0].endsWith(names[0])) {
						int cnt = Integer.parseInt(names[1]);
						int snipe = Integer.parseInt(names[2]);

						if (!WeaponUtils.ischarging(player)) {

							Location targetloc = player.getTargetBlock(
									(HashSet<Byte>) null, 10000).getLocation();
							Location locationplayer = player.getEyeLocation();

							if (!player.isSneaking()) {

								cnt = Integer.parseInt(names[1]);
								if (cnt != 0 && WeaponUtils.isCooldown(player) == false
										&& player.isSneaking() == false) {
									for (int c = 0; c < 3; c++) {

										player.playSound(player.getLocation(),
												Sound.ZOMBIE_WOOD, 3.0F, 3.0F);
										player.playSound(player.getLocation(),
												Sound.ANVIL_LAND, 1.0F, 1.0F);
										player.playSound(player.getLocation(),
												Sound.EXPLODE, 1.0F, 1.0F);
										player.playSound(player.getLocation(),
												Sound.ZOMBIE_WOOD, 3.0F, 3.0F);
										player.playSound(player.getLocation(),
												Sound.ANVIL_LAND, 0.3F, 0.3F);
										player.playSound(player.getLocation(),
												Sound.EXPLODE, 23.0F, 21.0F);
										player.playSound(player.getLocation(),
												Sound.IRONGOLEM_HIT, 4.0F, 4.0F);

										CustomSuitPlugin.SetDisplayName(
												values[0] + regex + (cnt - 1)
														+ regex + snipe,
												player.getItemInHand());
										Vector vector = targetloc
												.toVector()
												.subtract(
														locationplayer
																.toVector())
												.normalize().multiply(2);
										Snowball snowball = player
												.launchProjectile(
														Snowball.class, vector);

										SuitUtils.playEffect(locationplayer,
												Effect.STEP_SOUND, 1,
												Material.STONE.getId(), 1);
										SuitUtils.sleep(100);

									}
								}

							} else {
								if (snipe != 0) {
									if (!WeaponUtils.isCooldown(player)) {
										if (player
												.hasPotionEffect(PotionEffectType.SLOW) == false) {

											WeaponUtils.setRandomLoc(targetloc, 6);

										}

										player.playSound(player.getLocation(),
												Sound.ZOMBIE_WOOD, 3.0F, 3.0F);
										player.playSound(player.getLocation(),
												Sound.ANVIL_LAND, 0.3F, 0.3F);
										player.playSound(player.getLocation(),
												Sound.EXPLODE, 23.0F, 21.0F);
										player.playSound(player.getLocation(),
												Sound.IRONGOLEM_HIT, 4.0F, 4.0F);

										SuitUtils.playEffect(locationplayer,
												Effect.STEP_SOUND, 1,
												Material.STONE.getId(), 1);

										playEffect(targetloc, locationplayer,
												player, false);
										WeaponUtils.cooldown(2, player);

										CustomSuitPlugin.SetDisplayName(
												values[0] + regex + (cnt)
														+ regex + (snipe - 1),
												player.getItemInHand());

									}
								}
							}
						}
					}
				}
			}

		}

	}



}
