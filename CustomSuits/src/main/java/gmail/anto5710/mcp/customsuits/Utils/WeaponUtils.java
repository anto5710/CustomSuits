package gmail.anto5710.mcp.customsuits.Utils;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.Cooldown;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.WeaponListner;
import gmail.anto5710.mcp.customsuits.Setting.Values;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

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

	public static void setRandomLoc(Location loc, double a) {

		double b = a / 2;
		double random = (Math.random() * a) - b;

		loc.setX(loc.getX() + random);
		random = (Math.random() * a) - b;
		loc.setY(loc.getY() + random);
		random = (Math.random() * a) - b;
		loc.setZ(loc.getZ() + random);
	}

	public static int charge(Player player, String name, Material ammomat,
			int amount, int cnt, int snipe) {

		int ammoamount = 0;

		ItemStack ammo = new ItemStack(ammomat, 1);
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
			cooldown(2.0, player);
			SuitUtils.sleep(2000);

			player.playSound(player.getLocation(), Sound.ANVIL_LAND, 4.0F, 4.0F);
			player.playSound(player.getLocation(), Sound.EXPLODE, 4.0F, 4.0F);
			player.playSound(player.getLocation(), Sound.VILLAGER_HIT, 4.0F,
					4.0F);
			player.playSound(player.getLocation(), Sound.CLICK, 4.0F, 4.0F);
			player.playSound(player.getLocation(), Sound.CREEPER_HISS, 4.0F,
					4.0F);

			cooldown(0.5, player);
			SuitUtils.sleep(500);
			player.playSound(player.getLocation(), Sound.IRONGOLEM_HIT, 4.0F,
					4.0F);

			player.playSound(player.getLocation(), Sound.DIG_WOOD, 15.0F, 3.5F);
			player.playSound(player.getLocation(), Sound.DOOR_CLOSE, 4.0F, 2.5F);
			player.playSound(player.getLocation(), Sound.DOOR_OPEN, 4.0F, 4.0F);
			WeaponListner.charging.put(player, false);

		} else {
			SuitUtils.Wrong(player, "Ammo");
			player.playSound(player.getLocation(), Sound.NOTE_STICKS, 6.0F,
					6.0F);
		}
		return ammoamount;
	}
	public static void damageandeffect(Location currentLoc, double damage,
			Player player, boolean isMissile ,double  radius) {
		
		for (Entity entity : WeaponListner.findEntity(currentLoc, player,radius)) {

			if (player != entity && entity instanceof Damageable) {
				Damageable damageable = (Damageable) entity;

				if (!isMissile) {
					if (currentLoc.distance(entity.getLocation()) <= 0.5) {
						damage = damage * 2;
						WeaponListner.firework(currentLoc, player);
					}
				}
				damageable.damage(damage,player);
			}
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

	public static void cooldown(double msec, Player player) {

		// 1 tick = 0.05 sec
		// 1 sec = 20 tick
		long tick = (long) msec * 20;

		WeaponListner.cooldowns.put(player, true);

		BukkitTask task = new Cooldown(WeaponListner.plugin, player).runTaskLater(WeaponListner.plugin,
				tick);
	}
}
