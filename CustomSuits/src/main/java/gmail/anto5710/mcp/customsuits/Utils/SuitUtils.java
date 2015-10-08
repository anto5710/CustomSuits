package gmail.anto5710.mcp.customsuits.Utils;

import gmail.anto5710.mcp.customsuits.CustomSuits.PlayEffect;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.Player_Move;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.WeaponListner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;

import net.minecraft.server.v1_8_R2.EnumParticle;
import net.minecraft.server.v1_8_R2.Packet;
import net.minecraft.server.v1_8_R2.PacketPlayOutWorldParticles;

import org.bukkit.entity.Ambient;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;
public class SuitUtils {

	public static void LineParticle(Location target,Location location,
			Player player, EnumParticle effect, int amount, int data,
			int effectradius, double damage, double radius,boolean isMissile ) {
		
	
		Vector vectorStart = location.toVector();
		
		Vector vectorEnd = target.toVector();
		
		Vector difference = vectorStart.subtract(vectorEnd);
		
		
		double distance = difference.length();
		if (distance < 0) {
			return;
		}

		Location currentLoc = target.clone();
		double dx = (difference.getX() / distance) * 0.5;
		double dy = (difference.getY() / distance) * 0.5;
		double dz = (difference.getZ() / distance) * 0.5;
		
		for (double i = 0; i <= distance; i += 0.2) {
			currentLoc.add(dx, dy, dz);

		
		PlayEffect.play_Suit_Missile_Effect(currentLoc  ,  effect, amount , data ,player, isMissile);
		WeaponUtils
					.damageandeffect(currentLoc, damage, player, isMissile, radius);
			

		}
		

	}

	public static void playEffect( Location location , EnumParticle effect ,int amount, int data , int radius ){
		CraftWorld world = (CraftWorld)location.getWorld();
		float x = (float) location.getX();
		float y = (float) location.getY();
		float z = (float) location.getZ();
		
//		PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
//				   effect , true, x, y, z, 0,0, 0, amount, 0 , data , 0 , 0 );
		world.getHandle().sendParticles(null, effect, true, x, y, z, 0, 0, 0, amount, 0, data);
			
	
			
		
	}
	
	public static void spawnFirework(Color color, FireworkEffect.Type type ,int power,boolean Usetrail,boolean flicker,Color fadecolor, Location location){
		
		FireworkEffect effect = FireworkEffect.builder().withColor(color)
		.with(type).trail(Usetrail).withFade(fadecolor).flicker(flicker).build();
		
		Firework firework =location.getWorld().spawn(location, Firework.class);
		FireworkMeta meta = firework.getFireworkMeta();
		meta.setPower(power);
		
		meta.addEffect(effect);
		firework.setFireworkMeta(meta);
		
		
	}
	public static Vector calculateVelocity(Vector from, Vector to, double gravity,int heightGain)
    {
       
        
        // Block locations
        int endGain = to.getBlockY() - from.getBlockY();
        double horizDist = Math.sqrt(distanceSquared(from, to));
        // Height gain
        int gain = heightGain;
        double maxGain = gain > (endGain + gain) ? gain : (endGain + gain);
        // Solve quadratic equation for velocity
        double a = -horizDist * horizDist / (4 * maxGain);
        double b = horizDist;
        double c = -endGain;
        double slope = -b / (2 * a) - Math.sqrt(b * b - 4 * a * c) / (2 * a);
        // Vertical velocity
        double vy = Math.sqrt(maxGain * gravity);
        // Horizontal velocity
        double vh = vy / slope;
        // Calculate horizontal direction
        int dx = to.getBlockX() - from.getBlockX();
        int dz = to.getBlockZ() - from.getBlockZ();
        double mag = Math.sqrt(dx * dx + dz * dz);
        double dirx = dx / mag;
        double dirz = dz / mag;
        // Horizontal velocity components
        double vx = vh * dirx;
        double vz = vh * dirz;
        return new Vector(vx, vy, vz);
    }
	private static double distanceSquared(Vector from, Vector to)
    {
        double dx = to.getBlockX() - from.getBlockX();
        double dz = to.getBlockZ() - from.getBlockZ();
        return dx * dx + dz * dz;
    }
	public static boolean distance(Location currentLoc, Entity entity, double radius , double addYRadius) {
		Location location = entity.getLocation();
		
		for(double y = 0 ; y<=addYRadius ; y+=0.5){
			location.add( 0 , y , 0);
			if(location.distance(currentLoc)<=radius){
				return true;
			}
		}
		return false;
	}
	public static void createExplosion(Location location, float power , boolean setFire , boolean BlockBreak ){
		
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();
		
		location.getWorld().createExplosion(x, y, z, power, setFire, BlockBreak);
	}
	
	public static void sleep(long msec) {
		try {
			Thread.sleep(msec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void Wrong(Player player,String warn){
		player.sendMessage(ChatColor.DARK_RED
				+ "[Warn]:"+ChatColor.RED+" You don't have enough "+ ChatColor.GOLD+warn+ChatColor.RED+" !");
		
		player.playSound(player.getLocation(), Sound.NOTE_STICKS, 6.0F, 6.0F);
	}
	public static void Warn(Player player,String warn){
		player.sendMessage(ChatColor.DARK_RED
				+ "[Warn]:"+ChatColor.RED+warn+" !");
		
		player.playSound(player.getLocation(), Sound.NOTE_STICKS, 6.0F, 6.0F);
	}
	public static boolean CheckItem(ItemStack sample , ItemStack check ){
		if(check==null){
			return false;
		}
		if(sample.getType()==check.getType()&&checkLore(sample , check)){
			if(checkName(sample , check)){
			return true;
			}
		}
		return false;
	}

	public static boolean checkLore(ItemStack sample, ItemStack check) {
	
			if(!sample.getItemMeta().hasLore()&&sample.getItemMeta().hasLore()){
				return true;
			}else if(sample.getItemMeta().hasLore()&&check.getItemMeta().hasLore()){
				List<String>Samplelist = sample.getItemMeta().getLore();
				List<String>Checklist = check.getItemMeta().getLore();
				
				if(Samplelist.size()!=Checklist.size()){
					return false;
				}
			for(int index = 0 ; index <=Checklist.size()-1 ; index++){
				if(!Checklist.get(index).endsWith(Samplelist.get(index))){
				return false;
				}
			}
		}
		return true;
	}

	public static boolean checkName(ItemStack sample, ItemStack check) {
		if(sample.getItemMeta().getDisplayName()==null&&sample.getItemMeta().getDisplayName()==null){
			return true;
		}
		if(sample.getItemMeta().getDisplayName()!=null&&check.getItemMeta().getDisplayName()!=null){
			if(sample.getItemMeta().getDisplayName().endsWith(check.getItemMeta().getDisplayName())){
				return true;
			}
		}
		return false;
	}
	public static Block getTargetBlock(Player player , int MaxDistance){
		
			HashSet<Byte>hashSet = new HashSet<>(Arrays.asList((byte)0, (byte)Material.WATER.getId(),(byte)Material.STATIONARY_WATER.getId()));
			Block targetblock = player.getTargetBlock(hashSet, MaxDistance);
		
		return targetblock;
		
	}
}
