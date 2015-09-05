package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

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

	static int maxformachine = 50;
	static int maxforsniper = 8;
	
	static double damage = 0;
	static double radius = 0;
	static int amount = 0;
	float power = 0;
	static int effectradius =0;
	
	
	
	CustomSuitPlugin plugin;
	
	static String regex = CustomSuitPlugin.regex;
	
	private HashMap<Player, Boolean> charging = new HashMap<>();
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
					if (player.getItemInHand().getType() == Material.NETHER_STAR) {
						if (player.getFoodLevel()-18 >= 0) {
							
							player.setNoDamageTicks(200 * CustomSuitPlugin
									.getLevel(player));
							
							double sec = (CustomSuitPlugin.getLevel(player)) / 20 + 3;
							SuitUtils.playEffect(player.getLocation(),
									Effect.TILE_BREAK, 600, 0, 400);
							player.setFoodLevel(player.getFoodLevel() - 18);
							player.sendMessage(ChatColor.BLUE + "[Info]: "
									+ ChatColor.AQUA
									+ "No Damage Time for: " +ChatColor.DARK_AQUA+ sec
									+ " Seconds! ");

							player.playSound(player.getLocation(), Sound.FUSE, 2.0F, 2.0F);
						} else {

							SuitUtils.Wrong(player,"Energy");
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
				Material ammoMaterial = Material.FIREWORK_CHARGE;
				

				ItemStack ammo = new ItemStack(ammoMaterial, 1);
				if (player.getInventory().contains(ammoMaterial, 1)) {
					player.getInventory().removeItem(ammo);
					player.updateInventory();

					player.playSound(player.getLocation(), Sound.FIREWORK_LAUNCH,
							5.0F, 5.0F);
					fireball(player );
				}
				else {
					SuitUtils.Wrong(player, "Missile");
				}
			}

		}

	}
	
	private void fireball(Player player ) {
		Location targetloc = player.getTargetBlock((HashSet<Byte>)null, 10000).getLocation();
		Location locationplayer = player.getLocation();
		Vector vector = targetloc.toVector().subtract(locationplayer.toVector())
				.normalize().multiply(2);

		Fireball fireball =	player.launchProjectile(Fireball.class,vector);
		
		fireball.setIsIncendiary(true);
		
		
		
	}
	@EventHandler
	public void explodeFireball(ProjectileHitEvent event){
		if(event.getEntity() instanceof Fireball){
			if(event.getEntity().getShooter()!=null){
			Player player =	(Player)event.getEntity().getShooter();
				if(SuitUtils.CheckItem(CustomSuitPlugin.missileLauncher, player.getItemInHand())){
					
					event.getEntity().getWorld().createExplosion(event.getEntity().getLocation(), 5.0F);
				}
			}
		}
			
	}

	@EventHandler
	public void onPlayerLeftClick(PlayerInteractEvent clickevent) {
		Material nether_star = Material.NETHER_STAR;
		if ((clickevent.getAction() == Action.LEFT_CLICK_AIR)
				|| (clickevent.getAction() == Action.LEFT_CLICK_BLOCK)) {

			Player player = clickevent.getPlayer();
			String message = ChatColor.BLUE + "[Info]: " + ChatColor.AQUA
					+ "Fired a Repulser Bim!";
			Sound sound = Sound.BLAZE_BREATH;

			Location loc = player.getLocation();
			int energy = 2;
			loc.setY(loc.getY() + 1.25D);

			if (CustomSuitPlugin.MarkEntity(player)) {
				if (player.getItemInHand().getType() == nether_star) {
					if (player.isSneaking()) {
						energy = 8;
						sound = Sound.WITHER_DEATH;
						message = ChatColor.BLUE + "[Info]: " + ChatColor.AQUA
								+ "Fired a Repulser Missile!";
					}
					if(player.getFoodLevel()-energy>=0){
					launch(player, sound, message, energy);
					}else{
						SuitUtils.Wrong(player , "Energy");
					}

				}

			}
			
		}
	}

	private void launch(Player player, Sound sound , String message , int energy) {
		
		Location loc = player.getLocation();
		
			Block targetblock = player.getTargetBlock(
					(HashSet<Byte>) null, 10000);
			Location targetloc = targetblock.getLocation();
		
			loc.setY(loc.getY() + 1.25);
			
			playEffect(targetloc, loc, player,
					true);
			player.sendMessage(message);

			player.playSound(loc,sound, 4.0F, 4.0F);
			
			player.setFoodLevel(player.getFoodLevel() - energy);
	}

	private void playEffect(Location location1, Location location2,
			Player player, boolean isMissile) {
		
		

		setOption(isMissile, player);
		
		damage = damage * (CustomSuitPlugin.getLevel(player) / 1 + 1);
		
		Effect effect = Effect.TILE_BREAK;
		int data = Material.ANVIL.getId() ;
		if(isMissile){
			 
			
			 data = Material.DIAMOND_BLOCK.getId();
		}
		
		SuitUtils.LineParticle(location1, location2, player, effect,amount, data,effectradius,  damage ,200, isMissile );
		if(isMissile){
		SuitUtils.createExplosion(location1, power, false, true);
		
		}
		else {
			breakblock(location1.getBlock());
			
		}


	}
	
	private void setOption(boolean isMissile, Player player) {
		
		if (isMissile) {
			if (!player.isSneaking()) {
				radius = 2D;
				damage = 35D;
				power = 4F;
				amount = 40;
				effectradius = 2;
				
			} else {
				radius = 2D;
				damage = 75D;
				power = 50F;
				amount = 40;
				effectradius = 2;
				
			}
		} else {
			
				radius = 0.75D;
				damage = 30D;
				amount =15;
				effectradius = 2;
		}
	}

	/**
	 * +--------------------
	 * 
	 * @param near
	 * @param currentLoc
	 * @param player
	 * @param radius
	 * @return
	 */
	

	public static void breakblock( Block block) {
		Material material = block.getType();
		if (material != Material.AIR && material != Material.BEDROCK
				&& material != Material.OBSIDIAN && material != Material.WATER
				&& material != Material.LAVA) {

			block.breakNaturally();
			SuitUtils.playEffect(block.getLocation(), Effect.TILE_BREAK, 50, material.getId(), 10);
			SuitUtils.playEffect(block.getLocation(), Effect.STEP_SOUND, 50, material.getId(), 10);
		}
	}
	
	public static ArrayList<Entity> findEntity(Location currentLoc, Player player,
			double radius) {

		Collection<Entity> near = currentLoc.getWorld().getEntities();
		ArrayList<Entity> list = new ArrayList<>();
		for (Entity entity : near) {
			if (entity instanceof Damageable && entity != player
					&& entity != player.getVehicle()
					&& distance(currentLoc, entity, radius)) {
				list.add(entity);

			}
		}
		return list;
		

	}

	

	public static void damageandeffect(Location currentLoc, double damage,
			Player player,boolean isMissile) {
		for (Entity entity : findEntity(currentLoc, player,radius)) {

			if (player != entity && entity instanceof Damageable) {
				Damageable damageable = (Damageable) entity;
				
				if(!isMissile){
					if(currentLoc.distance(entity.getLocation()) <=0.5){
						damage =damage* 2;
						firework(currentLoc, player);
					}
				}
				damageable.damage(damage);
			}
		}
		/*
		 * 1 d0.5
		 */
	}
	
	public static void firework(Location location, Player player) {
		
		
		
		
		SuitUtils.spawnFirework(org.bukkit.Color.RED, org.bukkit.FireworkEffect.Type.STAR, 2, true, true, org.bukkit.Color.WHITE, location);

		
		
		player.playSound(player.getLocation(), Sound.EXPLODE, 14.0F, 14.0F);
		
		player.playSound(player.getLocation(), Sound.WITHER_DEATH, 14.0F, 14.0F);

	}

	public static boolean distance(Location currentLoc, Entity e, double radius) {
		Location entityLoc = e.getLocation();
		double EntityX = entityLoc.getX();
		double EntityY = entityLoc.getY();
		double EntityZ = entityLoc.getZ();
		double X = currentLoc.getX();
		double Y = currentLoc.getY();
		double Z = currentLoc.getZ();
		
		if(X-radius<=EntityX&&EntityX<=X+radius&&
				Y-1.5<=EntityY&&EntityY<=Y+1.5&&
				Z-radius<=EntityZ&&EntityZ<=Z+radius){
			return true;
		}
		return false;
	}
	@EventHandler
	public void charge(PlayerInteractEvent clickevent) {
		Player player = clickevent.getPlayer();
		ItemStack item = CustomSuitPlugin.getGun();

		if (clickevent.getAction() == Action.RIGHT_CLICK_AIR
				|| clickevent.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (CustomSuitPlugin.MarkEntity(player)) {

				if (checkgun(player, player.getItemInHand(), item)) {
					String name = item.getItemMeta().getDisplayName();
					String gunname = player.getItemInHand().getItemMeta()
							.getDisplayName();
					String[] values = name.split(regex);

					String[] names = gunname.split(regex);
					if (values[0].endsWith(names[0])) {
						int cnt = Integer.parseInt(names[1]);
						int snipe = Integer.parseInt(names[2]);
						if (cnt == 0&&!player.isSneaking()) {

							int ammoamount = charge(player, names[0],
									Material.FLINT, maxformachine, cnt, snipe);
							CustomSuitPlugin.SetDisplayName((names[0] + regex
									+ ammoamount + regex + snipe),
									player.getItemInHand());
						}
						if (snipe == 0 && player.isSneaking()) {
							int ammoamount = charge(player, names[0],
									Material.GHAST_TEAR, maxforsniper, cnt,
									snipe);

							CustomSuitPlugin.SetDisplayName((names[0]
									+ regex + cnt + regex + ammoamount),
									player.getItemInHand());

						}
					}
				}
			}
		}
	}
	@EventHandler
	public void damageByGun(EntityDamageByEntityEvent event){
		Entity damager = event.getDamager();
		if(damager.getType()==EntityType.SNOWBALL){
			Snowball snowball = (Snowball)damager;
			Entity shooter =(Entity) snowball.getShooter();
			if(shooter instanceof Player){
			if(	checkgun(((Player) shooter),((Player) shooter).getItemInHand(), CustomSuitPlugin.gunitem)){
				event.setDamage(6);
				
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

				if (checkgun(player, player.getItemInHand(), item)) {
					String name = item.getItemMeta().getDisplayName();
					String gunname = player.getItemInHand().getItemMeta()
							.getDisplayName();
					String[] values = name.split(regex);

					String[] names = gunname.split(regex);
					if (values[0].endsWith(names[0])) {
						int cnt = Integer.parseInt(names[1]);
						int snipe = Integer.parseInt(names[2]);
					
						if (!ischarging(player)) {
							
							Location targetloc = player.getTargetBlock(
									(HashSet<Byte>) null, 10000).getLocation();
							Location locationplayer = player.getLocation();
							
							
							if (!player.isSneaking()) {
								
									
									cnt = Integer.parseInt(names[1]);
								if (cnt != 0&&isCooldown(player)==false&&player.isSneaking()==false) {
									for(int c = 0; c< 3 ; c++){
									
									
									

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

									locationplayer
											.setY(locationplayer.getY() + 1.25);
								
							
									
									CustomSuitPlugin.SetDisplayName(
											values[0] + regex + (cnt - 1)
											+ regex + snipe,
											player.getItemInHand());
									Vector vector = targetloc.toVector().subtract(locationplayer.toVector())
											.normalize().multiply(2);
								Snowball snowball=			player.launchProjectile(Snowball.class, vector);
								
									SuitUtils.playEffect(locationplayer,
											Effect.STEP_SOUND, 1,
											Material.STONE.getId(), 1);
									SuitUtils.sleep(100);
									
								
								}
								}

							} else {
								if (snipe != 0) {
									if (!isCooldown(player)) {
										if (player
												.hasPotionEffect(PotionEffectType.SLOW) == false) {
											
											setRandomLoc(targetloc, 6);

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
										
										
										playEffect(targetloc, locationplayer, player, false);
										cooldown(2, player);

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


	public boolean checkgun(Player player, ItemStack checkitem, ItemStack same) {
		if (player.getItemInHand().getType() ==same.getType()) {
			
			if (player.getItemInHand().getItemMeta().getDisplayName() != null
					&& player.getItemInHand().getItemMeta().getDisplayName()
							.contains(regex)) {

				return true;
			}
		}
		return false;
	}
	


	private void setRandomLoc(Location loc, double  a) {
		
		double b = a/2;
		double random = (Math.random() * a) - b;

	
		loc.setX(loc.getX() + random);
		random = (Math.random() * a) - b;
		loc.setY(loc.getY() + random);
		random = (Math.random() * a) - b;
		loc.setZ(loc.getZ() + random);
	}

	private int charge(Player player ,String name,Material ammomat , int amount,int cnt,int snipe  ) {
		
	
		int ammoamount = 0;

		ItemStack ammo = new ItemStack(ammomat, 1);
		if (player.getInventory().contains(ammomat, 1)) {
			charging.put(player, true);
			for (int i = 0; i < amount; i++) {
				if (player.getInventory().contains(
						ammomat, 1)) {

					player.getInventory().removeItem(
							ammo);

					ammoamount++;
				}
			}

			player.updateInventory();

			player.playSound(player.getLocation(),
					Sound.LEVEL_UP, 4.0F, 1.0F);
			cooldown(2.0, player);
			SuitUtils.sleep(2000);
		
			
		
			player.playSound(player.getLocation(),
					Sound.ANVIL_LAND, 4.0F, 4.0F);
			player.playSound(player.getLocation(),
					Sound.EXPLODE, 4.0F, 4.0F);
			player.playSound(player.getLocation(),
					Sound.VILLAGER_HIT, 4.0F, 4.0F);
			player.playSound(player.getLocation(),
					Sound.CLICK, 4.0F, 4.0F);
			player.playSound(player.getLocation(),
					Sound.CREEPER_HISS, 4.0F, 4.0F);

			cooldown(0.5, player);
			SuitUtils.sleep(500);
			player.playSound(player.getLocation(),
					Sound.IRONGOLEM_HIT, 4.0F, 4.0F);

			player.playSound(player.getLocation(),
					Sound.DIG_WOOD, 15.0F, 3.5F);
			player.playSound(player.getLocation(),
					Sound.DOOR_CLOSE, 4.0F, 2.5F);
			player.playSound(player.getLocation(),
					Sound.DOOR_OPEN, 4.0F, 4.0F);
			charging.put(player, false);

		} else {
			SuitUtils.Wrong(player, "Ammo");
			player.playSound(player.getLocation(),
					Sound.NOTE_STICKS, 6.0F, 6.0F);
		}
		return ammoamount;
	}

	private boolean ischarging(Player player) {
		if (!charging.containsKey(player)) {
			return false;
		} else {
			if (charging.get(player) == false) {
				return false;
			}
		}
		
		return true;
	}

	private boolean isCooldown(Player player) {
		if (cooldowns.containsKey(player)) {
			if (cooldowns.get(player) == false) {
				return false;
			} else {
				return true;
			}
		} else {

			return false;
		}
	}

	

	private void cooldown(double msec, Player player) {

		// 1 tick = 0.05 sec
		// 1 sec = 20 tick
		long tick = (long) msec * 20;

		WeaponListner.cooldowns.put(player, true);

		BukkitTask task = new Cooldown(plugin, player).runTaskLater(plugin,
				tick);
	}

	

}
