package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.CustomSuits.FireworkPlay;
import gmail.anto5710.mcp.customsuits.CustomSuits.PlayEffect;
import gmail.anto5710.mcp.customsuits.CustomSuits.dao.SpawningDao;
import gmail.anto5710.mcp.customsuits.Man.Man;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.ManUtils;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;
import gmail.anto5710.mcp.customsuits.Utils.WeaponUtils;

import java.awt.Color;
import java.awt.Event;
import java.net.Proxy.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
import org.bukkit.craftbukkit.v1_8_R2.util.UnsafeList.Itr;
import org.bukkit.entity.ComplexEntityPart;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderDragonPart;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Firework;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
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
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
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

import oshi.PlatformEnum;

import com.google.common.primitives.Ints;

public class WeaponListner implements Listener {

	static int maxformachine = Values.MachineGunAmmoAmount;
	static int maxforsniper = Values.SnipeAmmoAmount;
	static ArrayList<Fireball> listFireball = new ArrayList<>();

	String regex = Values.regex;

	public static CustomSuitPlugin plugin;

	public static HashMap<Player, Boolean> charging = new HashMap<>();
	public static HashMap<Player, Boolean> cooldowns = new HashMap<>();

	public WeaponListner(CustomSuitPlugin plugin) {
		this.plugin = plugin;
	}

		
		

	@EventHandler
	public void cancel_Target_Owner(EntityDamageByEntityEvent event) {
		Entity entity = event.getDamager();
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (CustomSuitPlugin.isCreatedBy(entity, player)) {
				if (CustomSuitPlugin.target.containsKey(entity)) {
					LivingEntity target = CustomSuitPlugin.getTarget(player);
					((Creature) entity).setTarget(target);
				}
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void damage_By_Projectile(EntityDamageByEntityEvent event){
		if(event.getDamager() instanceof Projectile){
			if(event.getEntity() instanceof Player){
			Projectile projectile = (Projectile) event.getDamager();
			Player player = (Player) event.getEntity();
			if(projectile.getShooter()==null){
				return;
			}
			
			if(projectile.getShooter() instanceof LivingEntity){
				LivingEntity livingEntity = (LivingEntity)projectile.getShooter();
				CustomSuitPlugin.putTarget(player, livingEntity);
			}
			}
		}
	}

	@EventHandler
	public void targetTo_Protect(EntityDamageByEntityEvent event) {

		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (CustomSuitPlugin.MarkEntity(player)
					&& event.getDamager() instanceof LivingEntity) {
				if (!CustomSuitPlugin.isCreatedBy(event.getDamager(), player)) {
					LivingEntity livingEntity = (LivingEntity) event
							.getDamager();

					CustomSuitPlugin.putTarget(player, livingEntity);
				}
			}
		}
	}

	@EventHandler
	public void target(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if (CustomSuitPlugin.MarkEntity(player)
					&& !CustomSuitPlugin.isCreatedBy(event.getEntity(), player)
					&& event.getEntity() instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity) event.getEntity();
					CustomSuitPlugin.putTarget(player, livingEntity);
			}
		}
	}

	@EventHandler
	public void interectshield(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.RIGHT_CLICK_AIR
				|| event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (CustomSuitPlugin.MarkEntity(player)) {

				if (player.getItemInHand() != null) {
					if (player.getItemInHand().getType() == Values.SuitLauncher) {
						if (SchedulerHunger.hunger(player,
								Values.SuitShieldHunger)) {

							int sec = (CustomSuitPlugin.getLevel(player)) / 20 + 3;

							player.setNoDamageTicks(sec * 20);
							player.sendMessage(ChatColor.BLUE + "[Info]: "
									+ ChatColor.AQUA + "No Damage Time for: "
									+ ChatColor.DARK_AQUA + sec + " Seconds! ");
							PlayEffect.play_Suit_NoDamageTime(player, null);
							player.playSound(player.getLocation(),
									Values.SuitShieldSound, 2.0F, 2.0F);
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
		Location targetloc = SuitUtils.getTargetBlock(player, 1000)
				.getLocation();
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

			int energy = Values.BimHunger;

			if (CustomSuitPlugin.MarkEntity(player)) {
				if (player.getItemInHand().getType() == launcher) {
					if (player.isSneaking()) {
						energy = Values.MissileHunger;
						message = Values.MissileMessage;
					}
					if (SchedulerHunger.hunger(player, energy)) {
						launch(player,  message, energy);
					} else {
						SuitUtils.Wrong(player, "Energy");
					}

				}

			}

		}
	}

	private void launch(Player player,String message, int energy) {

		Location loc = player.getEyeLocation();

		Block targetblock = SuitUtils.getTargetBlock(player, 500);
		Location targetloc = targetblock.getLocation();

		playEffect(targetloc, loc, player, true);
		player.sendMessage(message);

	}

	private void playEffect(Location to, Location from, final Player player,
			boolean isMissile) {

		double radius = Values.BimRadius;
		double damage = Values.Bim;
		int amount = Values.BimEffectAmount;
		if (isMissile) {

			if (player.isSneaking()) {

				radius = Values.MissileRadius;
				damage = Values.Missile;
				amount = Values.MissileEffectAmount;

			}
			damage = damage * (CustomSuitPlugin.getLevel(player) / 32 + 1);
		} else {

			radius = Values.SniperRadius;
			damage = Values.SniperDamage;
			amount = Values.SniperEffectAmount;
		}

		EnumParticle effect = Values.SniperEffect;
		int data = Material.ANVIL.getId();
		int effect_count = 10;
		if (isMissile) {
			effect_count = 3;
			effect = Values.SuitProjectileEffect;
			data = Values.SuitBim_MissileEffectData;
		}
		
		final double Radius = radius;
		final boolean IsMissile = isMissile;
		final Location To = to;
		final EnumParticle Effect = effect;
		final int Amount = amount;
		final int Data = data;
		final double Damage =damage;
		final int effect_Count = effect_count;
			
				player.playSound(player.getLocation(), Sound.WITHER_SHOOT, 1F, 5F);
				player.playSound(player.getLocation(), Sound.EXPLODE, 1F, 0F);
				player.playSound(player.getLocation(), Sound.BLAZE_BREATH, 1F, -1F);
					player.playSound(player.getLocation(), Sound.WITHER_SPAWN, 1F, 1F);
				SuitUtils.LineParticle(To, player.getEyeLocation(), player, Effect, Amount, Data, Damage,
						Radius, true ,IsMissile, player.isSneaking(), effect_Count);
	

	}

	public static void breakblock(Block block) {
		Material material = block.getType();
		if (!Values.IgnoreMaterials_Gun.contains(material)) {

			SuitUtils.playEffect(block.getLocation(), EnumParticle.BLOCK_CRACK,
					10, block.getType().getId(), 5);
			block.breakNaturally();

		}
	}

	public static ArrayList<Entity> findEntity(Location currentLoc,
			Entity shooter, double radius) {
		
		Collection<Entity> near = currentLoc.getWorld().getEntitiesByClasses(Damageable.class);
		near.remove(shooter);
		if(shooter.isInsideVehicle()){
			near.remove(shooter.getVehicle());
		}
		ArrayList<Entity> list = new ArrayList<>();
		for (Entity entity : near) {
			
				if (SuitUtils.distance(currentLoc, entity ,radius )){
					if(!list.contains(entity)){
						
						list.add(entity);
					}

				}
			
		}
		return list;

	}

	public static void firework(Location location, Entity shooter) {
		FireworkEffect effect = SuitUtils.getRandomEffect();
		Firework firework = (Firework) location.getWorld().spawnEntity(
				location, EntityType.FIREWORK);
		FireworkMeta meta = firework.getFireworkMeta();
		meta.addEffect(effect);
		meta.setPower((int) (ManUtils.Random(5) + 2.5));
		firework.setFireworkMeta(meta);
		if (shooter != null) {
			location.getWorld().playSound(shooter.getLocation(), Sound.EXPLODE,
					14.0F, 14.0F);

			location.getWorld().playSound(shooter.getLocation(),
					Sound.WITHER_DEATH, 14.0F, 14.0F);
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
	
	public static boolean isCharging(Player player ){
		if(charging.containsKey(player)){
			if(charging.get(player)){
				return true;
			}
		}
			return false;
			
	}

	@EventHandler
	public void gun(PlayerInteractEvent clickevent) throws InterruptedException {
		Player player = clickevent.getPlayer();
		ItemStack Gun = CustomSuitPlugin.getGun();

		if (clickevent.getAction() == Action.RIGHT_CLICK_AIR
				|| clickevent.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (CustomSuitPlugin.MarkEntity(player)) {

				if (WeaponUtils.checkgun(player, player.getItemInHand(), Gun)) {
					
					String name = player.getItemInHand().getItemMeta()
							.getDisplayName();
					String[] values = name.split(regex);
					values[0]=values[0].replace(ChatColor.YELLOW + "Knif-1220" +"«", "");
					String gunfirstName = ChatColor.YELLOW + "Knif-1220 " 
							+"«";
					ItemStack itemInhand = player.getItemInHand();
					boolean isSniper = player.isSneaking();
					
					if(!isSniper){
						if((Integer.parseInt(values[0].replace(gunfirstName, ""))<=0)){
							
							if(!player.getInventory().contains(Material.FLINT,1)){
							SuitUtils.Wrong(player, "Machine Gun Ammo");
							return;
							}else if(!isCharging(player)){
								
							int amount=	WeaponUtils.charge(player, Material.FLINT, Values.MachineGunAmmoAmount, plugin);
							CustomSuitPlugin.SetDisplayName(gunfirstName+amount+regex+values[1].replace("»", "")+"»", itemInhand);
							
							player.setItemInHand(itemInhand);
								
							}
						}
						
						if (isCharging(player)) {
							return;
						}
						PlayEffect.play_Gun_Shot_Effect(player);
						shot_Machine_Gun(player);
						CustomSuitPlugin.SetDisplayName(gunfirstName+(Integer.parseInt(values[0].replace(gunfirstName, ""))-1)+regex+values[1].replace("»", "")+"»", itemInhand);
						
						player.setItemInHand(itemInhand);
						
					}else{
						if((Integer.parseInt(values[1].replace("»", ""))<=0)){
							
							if(!player.getInventory().contains(Material.GHAST_TEAR,1)){
								SuitUtils.Wrong(player, "Sniper Ammo");
								return;
							}else if(!isCharging(player)){
								
								int amount=	WeaponUtils.charge(player, Material.GHAST_TEAR, Values.SnipeAmmoAmount, plugin);
								CustomSuitPlugin.SetDisplayName(values[0]
										+regex+amount+"»", itemInhand);
								
								player.setItemInHand(itemInhand);
							}
						}
						if(isCharging(player)){
							return;
						}
						player.getInventory().remove(new ItemStack(Material.GHAST_TEAR,1));
						
						Block targetblock = SuitUtils.getTargetBlock(player,Values.Suit_Gun_Shot_Radius);
						Location target = targetblock.getLocation();
						if (!WeaponUtils.isCooldown(player)) {
							Location location = player.getEyeLocation();
							Vector direction = location.getDirection();
							location.setDirection(WeaponUtils.setRandomLoc(target.clone().subtract(location) , 10).toVector().normalize());
							if(PlayerEffect.Zoom.containsKey(player)){
								if(PlayerEffect.Zoom.get(player)){
									location.setDirection(direction);
								}
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
							SuitUtils.LineParticle(target, location, player, Values.SniperEffect, 1, 0, Values.SniperDamage, 0.5, true, false, false, 20);
							
							WeaponUtils.cooldown(2,plugin, player);
							CustomSuitPlugin.SetDisplayName(values[0]
									+regex+(Integer.parseInt(values[1].replace("»",""))-1)+"»", itemInhand);
						player.setItemInHand(itemInhand);
						}
					}
					
				player.updateInventory();
				
			

				}
			}
		
		}

	}

//	player.playSound(player.getLocation(),
//			Sound.ZOMBIE_WOOD, 3.0F, 3.0F);
//	player.playSound(player.getLocation(),
//			Sound.ANVIL_LAND, 0.3F, 0.3F);
//	player.playSound(player.getLocation(),
//			Sound.EXPLODE, 23.0F, 21.0F);
//	player.playSound(player.getLocation(),
//			Sound.IRONGOLEM_HIT, 4.0F, 4.0F);

	private void shot_Machine_Gun(final Player player) {

		player.playSound(player.getLocation(), Sound.ZOMBIE_WOOD, 3.0F, 3.0F);
		player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
		player.playSound(player.getLocation(), Sound.EXPLODE, 1.0F, 1.0F);
		player.playSound(player.getLocation(), Sound.ZOMBIE_WOOD, 3.0F, 3.0F);
		player.playSound(player.getLocation(), Sound.ANVIL_LAND, 0.3F, 0.3F);
		player.playSound(player.getLocation(), Sound.EXPLODE, 23.0F, 21.0F);
		player.playSound(player.getLocation(), Sound.IRONGOLEM_HIT, 4.0F, 4.0F);
	 Snowball   snowball = player.launchProjectile(Snowball.class  );
			;
	double spread_ = 0.3;
	
	Vector v = player.getLocation().getDirection();
	v.multiply(3.0);
	double x = ManUtils.Random(spread_);
	double y = ManUtils.Random(spread_);
	
	double z = ManUtils.Random(spread_);
	Vector spread = new Vector(x, y, z);
	v.add(spread);
		snowball.setVelocity(v);
		
    Vector direction = player.getLocation().getDirection().multiply(-0.5);
    direction.setY(0.1);
    player.setVelocity(direction);
		Gun_Effect.snowballs.add(snowball);

		PlayEffect.play_Gun_Shot_Effect(player);
		if(PlayerEffect.Zoom.containsKey(player)){
			if(PlayerEffect.Zoom.get(player)){
				spread_=ManUtils.Random(0.05);
			}
		}
		final double Spread = spread_;
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {

				player.playSound(player.getLocation(), Sound.ZOMBIE_WOOD, 3.0F,
						3.0F);
				player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F,
						1.0F);
				player.playSound(player.getLocation(), Sound.EXPLODE, 1.0F,
						1.0F);
				player.playSound(player.getLocation(), Sound.ZOMBIE_WOOD, 3.0F,
						3.0F);
				player.playSound(player.getLocation(), Sound.ANVIL_LAND, 0.3F,
						0.3F);
				player.playSound(player.getLocation(), Sound.EXPLODE, 23.0F,
						21.0F);
				player.playSound(player.getLocation(), Sound.IRONGOLEM_HIT,
						4.0F, 4.0F);

				
				Snowball snowball = player.launchProjectile(Snowball.class);

				Vector v = player.getLocation().getDirection();
				v.multiply(3.0);
				double x = ManUtils.Random(Spread);
				double y = ManUtils.Random(Spread);
				
				double z = ManUtils.Random(Spread);
				Vector spread = new Vector(x, y, z);
				v.add(spread);
					snowball.setVelocity(v);
				Gun_Effect.snowballs.add(snowball);

				PlayEffect.play_Gun_Shot_Effect(player);
			}
		}, 3L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

			@Override
			public void run() {

				player.playSound(player.getLocation(), Sound.ZOMBIE_WOOD, 3.0F,
						3.0F);
				player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F,
						1.0F);
				player.playSound(player.getLocation(), Sound.EXPLODE, 1.0F,
						1.0F);
				player.playSound(player.getLocation(), Sound.ZOMBIE_WOOD, 3.0F,
						3.0F);
				player.playSound(player.getLocation(), Sound.ANVIL_LAND, 0.3F,
						0.3F);
				player.playSound(player.getLocation(), Sound.EXPLODE, 23.0F,
						21.0F);
				player.playSound(player.getLocation(), Sound.IRONGOLEM_HIT,
						4.0F, 4.0F);

				Snowball snowball = player.launchProjectile(Snowball.class);
				Vector v = player.getLocation().getDirection();
				v.multiply(3.0);
				double x = ManUtils.Random(Spread);
				double y = ManUtils.Random(Spread);
				
				double z = ManUtils.Random(Spread);
				Vector spread = new Vector(x, y, z);
				v.add(spread);
					snowball.setVelocity(v);				
				Gun_Effect.snowballs.add(snowball);

				PlayEffect.play_Gun_Shot_Effect(player);
			}
		}, 3L);

		if (!Gun_Effect.isRunning) {
			BukkitTask task = new Gun_Effect(plugin).runTaskTimer(plugin, 0, 2);
		}

	}

}
