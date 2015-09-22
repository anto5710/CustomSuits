package gmail.anto5710.mcp.customsuits.Utils;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.PlayerEffect;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.WeaponListner;
import gmail.anto5710.mcp.customsuits._Thor.Repeat;

import java.util.HashMap;
import java.util.List;

import javax.swing.text.Position;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

public class ThorUtils {

	public static Item getItem(World world, Player player) {
		for(Entity entity : world.getEntitiesByClass(Item.class)){
			if(entity instanceof Item){
				Item item = (Item) entity;
				if(SuitUtils.CheckItem(CustomSuitPlugin.hammer, item.getItemStack())){
					return item;
				}
			}
		}
	
	
	return null;
	}
	public static void getRing(double radius, Player player , float power , double damage , boolean setFire , boolean BreakBlocks) {
		int points = 12; // amount of points to be generated
		for (int i = 0; i < 360; i += 360 / points) {
			double angle = (i * Math.PI / 180);
			double x = radius * Math.cos(angle);
			double z = radius * Math.sin(angle);
			Location loc = player.getLocation().add(x, 1, z);
			SuitUtils.createExplosion(loc, power, setFire, BreakBlocks);
			damage(WeaponListner.findEntity(loc, player, 6.5),
					 damage, player);
		}
	}
	
	public static void removePotionEffectType(PotionEffectType PotionEffectType, Player player) {
		if(player.hasPotionEffect(PotionEffectType)){
			player.removePotionEffect(PotionEffectType);
		}
		
		
	}
	public static void removePotionEffect(PotionEffect PotionEffect, Player player) {
		if(PlayerEffect.ContainPotionEffect(player, PotionEffect.getType(), PotionEffect.getAmplifier())){
			player.removePotionEffect(PotionEffect.getType());
		}
		
		
	}
	public static boolean isFire(Item item) {
	if	((item.getFireTicks() == -1 || item.getFireTicks() == 0) == false){
		return false;
	}
		return false;
	}

	public static boolean isOnGround(Item item) {
		if(item.isOnGround()){
			return true;
		}
		Location location = item.getLocation();
		double Y = location.getY()-1;
		
			Block block = new Location(location.getWorld(), location.getX(), Y, location.getZ()).getBlock();
			Material matareial = block.getType();
			if(matareial!=Material.AIR&&matareial !=Material.WATER){
				return true;
			}
		
		
		return false;
	}

	public static void remove(Item item) {
		
		if (Repeat.listPlayer.containsKey(item)) {
			Repeat.listPlayer.remove(item);
		}
		if (Repeat.listTeleport.containsKey(item)) {
			Repeat.listTeleport.remove(item);
		}

	}

	public static void cancel(int taskId) throws IllegalStateException{

		BukkitScheduler scheduler = Bukkit.getScheduler();
	
			
			scheduler.cancelTask(taskId);
		

	}

	public static void damage(java.util.List<Entity> list, double damage,
			Player player) {
		if (list.contains(player)) {
			list.remove(player);
		}
		for (Entity e : list) {
			if (e instanceof Damageable) {
				((Damageable) e).damage(damage);

			}
		}
	}
	public static double Random(double a ){
		double b = a / 2;
		double random = (Math.random() * a) - b;
		return random;
	}
	public static void strikeLightning(Location loc, Player player, int amount,
			double damageRadius, double damage) {
	
		for (int c = 0; c < amount; c++) {
			loc.getWorld().strikeLightning(loc);
			ThorUtils.damage(WeaponListner.findEntity(loc, player, damageRadius),
					damage, player);
		}
	}

}
