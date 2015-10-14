package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.CustomSuits.FireworkPlay;
import gmail.anto5710.mcp.customsuits.CustomSuits.PlayEffect;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.ManUtils;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;
import gmail.anto5710.mcp.customsuits.Utils.WeaponUtils;

import java.awt.Color;
import java.net.Proxy.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.naming.ldap.Rdn;
import javax.print.DocFlavor.CHAR_ARRAY;

import net.minecraft.server.v1_8_R2.EnumParticle;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Bukkit;
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
	static ArrayList<Fireball>listFireball = new ArrayList<>();
	
	
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

							

							int sec = (CustomSuitPlugin.getLevel(player)) / 20 + 3;
							
							player.setNoDamageTicks(sec*20);
							player.sendMessage(ChatColor.BLUE + "[Info]: "
									+ ChatColor.AQUA + "No Damage Time for: "
									+ ChatColor.DARK_AQUA + sec + " Seconds! ");
							PlayEffect.play_Suit_NoDamageTime(player ,null);
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
		Location targetloc = SuitUtils.getTargetBlock(player, 1000).getLocation();
		Location locationplayer = player.getLocation();
		Vector vector = targetloc.toVector()
				.subtract(locationplayer.toVector()).normalize().multiply(2);

		Fireball fireball = player.launchProjectile(Fireball.class, vector);

		fireball.setIsIncendiary(true);
		listFireball.add(fireball);

	}

	@EventHandler
	public void explodeFireball(ProjectileHitEvent event) {
		if (event.getEntity() instanceof Fireball) {
			if (event.getEntity().getShooter() != null) {
				if (event.getEntity().getShooter() instanceof Player) {

					if (listFireball.contains(event.getEntity())) {

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

		Block targetblock = SuitUtils.getTargetBlock( player, 500);
		Location targetloc = targetblock.getLocation();

		playEffect(targetloc, loc, player, true);
		player.sendMessage(message);

		player.playSound(loc, sound, 4.0F, 4.0F);

	}

	private void playEffect(Location to, Location from,
			Player player, boolean isMissile) {

		setOption(isMissile, player);

		damage = damage * (CustomSuitPlugin.getLevel(player)/32+1);

		EnumParticle effect = Values.SniperEffect;
		int data = Material.ANVIL.getId();
		if (isMissile) {
			effect = Values.SuitProjectileEffect;
			data =Values.SuitBim_MissileEffectData;
		}

		SuitUtils.LineParticle(to, from, player, effect, amount,
				data, effectradius, damage,radius,  player.isSneaking(),isMissile );
		if (isMissile) {
			int count = 2;
			double r = 1.2;
			if(player.isSneaking()){
				count  = 10;
				r = 25;
			}
			SuitUtils.createExplosion(to.add(0,-2, 0), power, false, true);

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

			} else {
				radius = Values.MissileRadius;
				damage = Values.Missile;
				power = Values.MissileExplosionPower;
				amount = Values.MissileEffectAmount;

			}
		} else {

			radius = Values.SniperRadius;
			damage = Values.SniperDamage;
			amount = Values.SniperEffectAmount;
		}
	}

	

	public static void breakblock(Block block) {
		Material material = block.getType();
		if (!Values.IgnoreMaterials_Gun.contains(material)) {

			SuitUtils.playEffect(block.getLocation(), EnumParticle.BLOCK_CRACK, 10, block.getType().getId(), 5);
			block.breakNaturally();
		
		}
	}

	public static ArrayList<Entity> findEntity(Location currentLoc,
			Player player, double radius) {

		Collection<Entity> near = currentLoc.getWorld().getEntities();
		ArrayList<Entity> list = new ArrayList<>();
		for (Entity entity : near) {
			if (entity instanceof Damageable && entity != player
					&& entity != player.getVehicle()
					&& SuitUtils.distance(currentLoc, entity, radius , 2)) {
				list.add(entity);

			}
		}
		return list;

	}



	public static void firework(Location location, Player player) {
		FireworkEffect effect = SuitUtils.getRandomEffect();
		FireworkPlay.spawn(location, effect, player);
		if(player!=null){
		location.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 14.0F, 14.0F);

		location.getWorld().playSound(player.getLocation(), Sound.WITHER_DEATH, 14.0F, 14.0F);
		}

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
									Material.FLINT, maxformachine, cnt, snipe , plugin);
							CustomSuitPlugin.SetDisplayName((names[0] + regex
									+ ammoamount + regex + snipe),
									player.getItemInHand());
						}
						if (snipe == 0 && player.isSneaking()) {
							int ammoamount = WeaponUtils.charge(player, names[0],
									Material.GHAST_TEAR, maxforsniper, cnt,
									snipe , plugin);

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
	public void hit(ProjectileHitEvent event){
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

	@EventHandler
	public void gun(PlayerInteractEvent clickevent) throws InterruptedException {
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

							Location targetloc = SuitUtils.getTargetBlock(player, Values.Suit_Gun_Shot_Radius).getLocation();
							Location locationplayer = player.getEyeLocation();

							if (!player.isSneaking()) {

								cnt = Integer.parseInt(names[1]);
								if (cnt != 0 && WeaponUtils.isCooldown(player) == false
										&& player.isSneaking() == false) {
									shot_Machine_Gun(player, values[0], ""+(cnt-1), snipe);
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
										
										PlayEffect.play_Gun_Shot_Effect(player);

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

	private void shot_Machine_Gun(final Player player , final String name , final String cnt ,final int sniperAmmo)  {
		
					
						
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
								name+ regex + cnt
								+ regex + sniperAmmo,
								player.getItemInHand());
						Snowball snowball = player
								.launchProjectile(
										Snowball.class);
						
						Location target = SuitUtils.getTargetBlock(player, 10).getLocation();
						Vector v = target.toVector().subtract(snowball.getLocation().toVector());
						snowball.setVelocity(v);
						Gun_Effect.snowballs.add(snowball);
						
						PlayEffect.play_Gun_Shot_Effect(player);
				
		
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, 
				new Runnable() {
					
					@Override
					public void run() {
					
						
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
								name+ regex + cnt
								+ regex + sniperAmmo,
								player.getItemInHand());
						Snowball snowball = player
								.launchProjectile(
										Snowball.class);
						Gun_Effect.snowballs.add(snowball);
						
						PlayEffect.play_Gun_Shot_Effect(player);
					}
				}, 3L);	
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, 
					new Runnable() {
						
						@Override
						public void run() {
						
							
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
									name+ regex + cnt
									+ regex + sniperAmmo,
									player.getItemInHand());
							Snowball snowball = player
									.launchProjectile(
											Snowball.class);
							Gun_Effect.snowballs.add(snowball);
							
							PlayEffect.play_Gun_Shot_Effect(player);
						}
					} ,3L);	
			
			if(!Gun_Effect.isRunning){
				BukkitTask task = new Gun_Effect(plugin).runTaskTimer(plugin, 0, 2);
			}
			
	

		}
		
	



}
