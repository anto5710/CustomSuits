package gmail.anto5710.mcp.customsuits.Utils;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.PlayerEffect;
import gmail.anto5710.mcp.customsuits._Thor.Hammer_Throw_Effect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
	/**
	 * Find Thor's Hammer from World
	 * @param world World to Find
	 * @param player Player
	 * @return
	 */
	public static Item getItem(World world, Player player) {
		for(Entity entity : world.getEntitiesByClass(Item.class )){
				Item item = (Item) entity;
				if(SuitUtils.CheckItem(CustomSuitPlugin.hammer, item.getItemStack())){
					return item;
				}
		}
	return null;
	}
	/**
	 * If Player is holding Thor's Hammer , return true;
	 * @param player Player to Check
	 * @return is player holding Hammer
	 */
	public static boolean isHammerinHand(Player player){
		return SuitUtils.CheckItem(CustomSuitPlugin.hammer, player.getItemInHand());
	}
	/**
	 * 
	 * @param location Center location
	 * @param radius Radius
	 * @param player Player 
	 * @return Entities in radius
	 */
	public static List<Entity> findEntity(Location location , double radius , Player player){
	
		ArrayList<Entity>list =new ArrayList<>();
		Collection<Entity>EntityInWorld = location.getWorld().getEntitiesByClasses(Damageable.class);
		EntityInWorld.remove(player);
		
		for(Entity entity : EntityInWorld){
				if(entity instanceof Damageable && distance2D(entity, location, radius)){
					list.add(entity);
			}
		}	
		return list;
	}
	/**
	 * 
	 * Check Distance of Location and Entity with 2D
	 * @param entity Entity to Check
	 * @param Location Target Location
	 * @param radius Radius
	 * @return is entity's distance from location smaller than radius
	 */
	public static boolean distance2D(Entity entity , Location Location , double radius){
		Location entityLoc = entity.getLocation();
		double EntityX = entityLoc.getX();
		double EntityZ = entityLoc.getZ();
		
		double X = Location.getX();
		double Z = Location.getZ();

		if (X - radius <= EntityX && EntityX <= X + radius
			&& Z - radius <= EntityZ && EntityZ <= Z + radius) {
				return true;
		}
		return false;
	}
	/**
	 * Remove PotionEffect
	 * @param PotionEffectType PotionEffectType
	 * @param player Player to remove PotionEfffect
	 */
	public static void removePotionEffectType(PotionEffectType PotionEffectType, Player player) {
		if(player.hasPotionEffect(PotionEffectType)){
			player.removePotionEffect(PotionEffectType);
		}
	}
	/**
	 * Remove PotionEffect
	 * @param PotionEffect PotionEffect
	 * @param player Player to remove PotionEfffect
	 */
	public static void removePotionEffect(PotionEffect PotionEffect, Player player) {
		if(PlayerEffect.ContainPotionEffect(player, PotionEffect.getType(), PotionEffect.getAmplifier())){
			player.removePotionEffect(PotionEffect.getType());
		}
	}
	/**
	 * Check is entity burning
	 * @param entity Entity to Check
	 * @return is entity burning
	 */
	public static boolean isBurning(Entity entity) {
	if	((entity.getFireTicks() == -1 || entity.getFireTicks() == 0) == false){
		return false;
	}
		return false;
	}
	/**
	 * Check is item onGround
	 * @param item Item to check
	 * @return is item onGround
	 */
	public static boolean isOnGround(Item item) {
		if(item.isOnGround()){
			return true;
		}
		Location location = item.getLocation().add(0, -1, 0);
		Block block = location.getBlock();
		
			Material matareial = block.getType();
			List<Material>Ignore_materials = Arrays.asList(Material.WATER , Material.STATIONARY_WATER , Material.AIR);
			if(!Ignore_materials.contains(matareial)){
				return true;
			}
		return false;
	}
	/**
	 * Remove item from Thorwn Hammers
	 * @param item Item to remove from Thorwn Hammer list 
	 */
	public static void remove(Item item) {
			Hammer_Throw_Effect.listPlayer.remove(item);
	}
	/**
	 * Cancel That BukkitTask
	 * @param taskId TaskID
	 * @throws IllegalStateException
	 */
	public static void cancel(int taskId) throws IllegalStateException{
		 Bukkit.getScheduler().cancelTask(taskId);
	}
	/**
	 * Damage entities in list
	 * @param list Entities to Damage
	 * @param damage Damage
	 * @param player player
	 */
	public static void damage(java.util.List<Entity> list, double damage,
			Player player) {
			list.remove(player);
		for (Entity e : list) {
			if (e instanceof Damageable) {
				((Damageable) e).damage(damage);

			}
		}
	}
	/**
	 * Return new random number
	 * @param a radius of Random number 
	 * @return new random number
	 */
	public static double Random(double a ){
		double b = a / 2;
		double random = (Math.random() * a) - b;
		return random;
	}
	/**
	 * Strike Lightning
	 * @param loc Target Location
	 * @param player player
	 * @param amount Amount of Striking
	 */
	public static void strikeLightnings(Location loc, Player player , int amount) {
	
		for (int c = 0; c < amount; c++) {
			loc.getWorld().strikeLightning(loc);
		}
	}



}
