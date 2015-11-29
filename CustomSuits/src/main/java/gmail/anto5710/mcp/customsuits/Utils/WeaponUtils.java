package gmail.anto5710.mcp.customsuits.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.WeaponListner;
import gmail.anto5710.mcp.customsuits.Setting.Values;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class WeaponUtils {
	static String regex = Values.regex;
	public static boolean checkgun(Player player, ItemStack checkitem, ItemStack same) {
		if (player.getItemInHand().getType() == same.getType()) {

			if (player.getItemInHand().getItemMeta().getDisplayName() != null
					&& player.getItemInHand().getItemMeta().getDisplayName()
							.contains(regex)) {

				return true;
			}
		}
		return false;
	}

	public static Location setRandomLoc(Location location, double a) {
		Location loc = location.clone();
		double b = a / 2;
		double random = (Math.random() * a) - b;

		loc.setX(loc.getX() + random);
		random = (Math.random() * a) - b;
		loc.setY(loc.getY() + random);
		random = (Math.random() * a) - b;
		loc.setZ(loc.getZ() + random);
		return loc;
	}

	public static int charge(final Player player, Material ammomat,
			int amount, final CustomSuitPlugin plugin) {
		int ammoamount = 0;
		final ItemStack ammo = new ItemStack(ammomat, 1);
		if (player.getInventory().contains(ammomat, 1)) {
			WeaponListner.charging.put(player, true);
			for (int i = 0; i < amount; i++) {
				if (player.getInventory().contains(ammomat, 1)) {

					player.getInventory().removeItem(ammo);

					ammoamount++;
				}
			}

			player.updateInventory();

			player.playSound(player.getLocation(), Sound.LEVEL_UP, 4.0F, 1.0F);
			
			
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				
				@Override
				public void run() {
					
					player.playSound(player.getLocation(), Sound.ANVIL_LAND, 4.0F, 4.0F);
					player.playSound(player.getLocation(), Sound.EXPLODE, 4.0F, 4.0F);
					player.playSound(player.getLocation(), Sound.VILLAGER_HIT, 4.0F,
							4.0F);
					player.playSound(player.getLocation(), Sound.CLICK, 4.0F, 4.0F);
					player.playSound(player.getLocation(), Sound.CREEPER_HISS, 4.0F,
							4.0F);
					
				}
			}, 40);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				
				@Override
				public void run() {
			
			
			player.playSound(player.getLocation(), Sound.IRONGOLEM_HIT, 4.0F,
					4.0F);

			player.playSound(player.getLocation(), Sound.DIG_WOOD, 15.0F, 3.5F);
			player.playSound(player.getLocation(), Sound.DOOR_CLOSE, 4.0F, 2.5F);
			player.playSound(player.getLocation(), Sound.DOOR_OPEN, 4.0F, 4.0F);
			WeaponListner.charging.put(player, false);
				} 
				}, 65);

		} else {
			SuitUtils.Wrong(player, "Ammo");
			player.playSound(player.getLocation(), Sound.NOTE_STICKS, 6.0F,
					6.0F);
		}
		return ammoamount;
	}
	 public static Vector determinePosition(Player player, boolean dualWield, boolean leftClick)
	  {
	    int leftOrRight = 90;
	    if ((dualWield) && (leftClick)) {
	      leftOrRight = -90;
	    }
	    double playerYaw = (player.getLocation().getYaw() + 90.0F + leftOrRight) * 3.141592653589793D / 180.0D;
	    double x = Math.cos(playerYaw);
	    double y = Math.sin(playerYaw);
	    Vector vector = new Vector(x, 0.0D, y);
	    
	    return vector;
	  }
	public static void damageandeffect(Location currentLoc, double damage,
			Entity shooter, boolean isMissile, boolean isProjectile ,double  radius ) {
		
		for (Entity entity : WeaponListner.findEntity(currentLoc, shooter,radius)) {

		
				Damageable damageable = (Damageable) entity;

				if (!isMissile&&isProjectile) {
					if (entity instanceof LivingEntity) {
						if(((LivingEntity)entity).getEyeLocation().distance(currentLoc)<=0.35){
						damage = damage * 2;
						WeaponListner.firework(currentLoc, shooter);
						}
					}
				
			}
				damageable.damage(damage,shooter);
		}
		
	}

	public static boolean ischarging(Player player) {
		if (!WeaponListner.charging.containsKey(player)) {
			return false;
		} else {
			if (WeaponListner.charging.get(player) == false) {
				return false;
			}
		}

		return true;
	}

	public static boolean isCooldown(Player player) {
		if (WeaponListner.cooldowns.containsKey(player)) {
			if (WeaponListner.cooldowns.get(player) == false) {
				return false;
			} else {
				return true;
			}
		} else {

			return false;
		}
	}

	public static void cooldown(double msec, CustomSuitPlugin plugin,final Player player) {

		// 1 tick = 0.05 sec
		// 1 sec = 20 tick
		long tick = (long) msec * 20;

		WeaponListner.cooldowns.put(player, true);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				player.playSound(player.getLocation(), Sound.IRONGOLEM_HIT, 4.0F, 4.0F);	
				
				
				
				player.playSound(player.getLocation(), Sound.DIG_WOOD, 15.0F,3.5F);
				player.playSound(player.getLocation(), Sound.DOOR_CLOSE, 4.0F, 2.5F);
				player.playSound(player.getLocation(), Sound.DOOR_OPEN, 4.0F, 4.0F);	
				WeaponListner.cooldowns.put(player, false);
			}
		}.runTaskLater(plugin, tick);
    
	}
}
