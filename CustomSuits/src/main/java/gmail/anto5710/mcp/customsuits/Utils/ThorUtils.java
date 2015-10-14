package gmail.anto5710.mcp.customsuits.Utils;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.PlayerEffect;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.WeaponListner;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits._Thor.Repeat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.text.Position;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

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

	
	public static ArrayList<Entity> findEntity(Location location , double radius , Player player){
	
		ArrayList<Entity>list =new ArrayList<>();
		List<Entity>EntityInWorld = location.getWorld().getEntities();
		for(Entity entity : EntityInWorld){
			if(entity !=player){
				
			
			if(entity instanceof Damageable && distance(entity, location, radius)){
				list.add(entity);
			}
		}
		}	
			
		
		return list;
		
	}
	public static boolean distance(Entity entity , Location Location , double radius){
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
			List<Material>Ignore_materials = Arrays.asList(Material.WATER , Material.STATIONARY_WATER , Material.AIR);
			if(!Ignore_materials.contains(matareial)){
				return true;
				
			}
		
		
		return false;
	}

	public static void remove(Item item) {
		
		if (Repeat.listPlayer.containsKey(item)) {
			Repeat.listPlayer.remove(item);
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
	public static void spreadItems( Location loc,ItemStack itemstack){
		for( int c = 0 ; c <= 20 ; c++){
			Item item = loc.getWorld().dropItem(loc, itemstack);
		 float x = (float) -1 + (float) (Math.random() * ((1 - -1) + 1));
	        float y = 1;
	        float z = (float) -0.3 + (float)(Math.random() * ((0.3 - -0.3) + 1));
	        item.setVelocity(new Vector(x, y, z));
	        item.setPickupDelay(20);
		}
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
