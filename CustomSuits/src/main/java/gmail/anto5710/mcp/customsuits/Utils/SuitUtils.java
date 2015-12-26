package gmail.anto5710.mcp.customsuits.Utils;

import gmail.anto5710.mcp.customsuits.CustomSuits.FireworkPlay;
import gmail.anto5710.mcp.customsuits.CustomSuits.PlayEffect;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.Player_Move;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.WeaponListner;
import gmail.anto5710.mcp.customsuits.Setting.Values;

import java.awt.SystemColor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.GrassSpecies;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.craftbukkit.v1_8_R2.CraftEffect;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;

import net.minecraft.server.v1_8_R2.EnumParticle;
import net.minecraft.server.v1_8_R2.Packet;
import net.minecraft.server.v1_8_R2.PacketPlayOutWorldParticles;

import org.bukkit.entity.Ambient;
import org.bukkit.entity.ComplexEntityPart;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.avaje.ebeaninternal.server.persist.BindValues.Value;
public class SuitUtils {
	static CustomSuitPlugin plugin;
	public SuitUtils(CustomSuitPlugin plugin){
		SuitUtils.plugin = plugin;
	}
	public static void LineParticle(final Location target,final Location location,
			final Entity shooter, final EnumParticle effect, final int amount, final int data,
			 final double damage, final double radius,final boolean isSuitProjectile  , final boolean isMissile ,final boolean isSneaking, final int Effect_Count_Second) {
		Vector vectorStart = location.toVector();
		
		Vector vectorEnd = target.toVector();
		
		Vector difference = vectorEnd.subtract(vectorStart);
		
		
		final double distance = difference.length();
		final Vector v = location.getDirection().normalize().multiply(0.5);
		if (distance < 0) {
			return;
		}
		
		new BukkitRunnable() {
			Location currentLoc = location.clone();
			double count  = 0;
			@Override
			public void run() {
				
				for(int c = 0;c<Effect_Count_Second;c++){
					if(count>=distance){
						if (isMissile) {
							float power = Values.BimExplosionPower;
							SuitUtils.createExplosion(currentLoc, power, false, true);

						} else {
							WeaponListner.breakblock(target.getBlock());

						}
						this.cancel();
						break;
						}
				currentLoc.add(v);

				
				PlayEffect.play_Suit_Missile_Effect(currentLoc  ,  effect, amount , data, isSneaking ,(isMissile&&isSuitProjectile));
				WeaponUtils
							.damageandeffect(currentLoc, damage, shooter, isMissile, isSuitProjectile, radius);
				count+=0.5;
				}
				
			}
		}.runTaskTimer(plugin, 0, 1);
		

	}

	public static void playEffect( Location location , EnumParticle effect ,int amount, int data , int radius ){
		CraftWorld world = (CraftWorld)location.getWorld();
		float x = (float) location.getX();
		float y = (float) location.getY();
		float z = (float) location.getZ();
		world.getHandle().sendParticles(null, effect, true, x, y, z, 0, 0, 0, amount, 0, data , 0, 0, 0, 0);
			
	
			
		
	}
	public static void playEffect( EnumParticle effect , Location location ,float x_spread , float y_spread , float z_spread ,float speed ,int amount , int distance , int data){
		float x = (float) location.getX();
		float y = (float) location.getY();
		float z = (float) location.getZ();
		PacketPlayOutWorldParticles packet = new 
				PacketPlayOutWorldParticles(effect, true, x, y, z, x_spread, y_spread, z_spread, speed, amount, distance , data);
		for(Player player : Bukkit.getOnlinePlayers()){
			((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
		}
	
			
		
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
	public static boolean distance(Location currentLoc, Entity entity , double radius ) {
		Location location = entity.getLocation();
		if(location.distance(currentLoc)<=radius){
			return true;
		}
		double height = ((CraftEntity)entity).getHandle().getHeadHeight();
		for(double y= -0.25; y<=height; y+=0.25){
			location.add(0, y, 0);
			if(location.distance(currentLoc)<=radius){
				return true;
			}
			location.subtract(0, y, 0);
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
				+ "[Warn]: "+ChatColor.RED+"You don't have enough "+ ChatColor.GOLD+warn+ChatColor.RED+" !");
		
		player.playSound(player.getLocation(), Sound.NOTE_STICKS, 6.0F, 6.0F);
	}
	public static void Warn(Player player,String warn){
		player.sendMessage(ChatColor.DARK_RED
				+ "[Warn]: "+ChatColor.RED+warn+" !");
		
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
	private  static Color[] getColors(Color color){
		float [] HSB =RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue());
		float H = HSB[0];
		float S = HSB[1];
		float B = HSB[2];
		int N = 200;
		float add = -0.125F;
		
		Color[] colors= new Color[N];
		for(int n = 0; n<N ; n++){
			if(isOutOfHSB(add, H, S ,B)){
				add*=-1;
				
			}
			colors[n]=HSBtoRGB(H, S, B);
		}
		return colors;
		
	}
	public static FireworkEffect getRandomEffect(){
	    int type_index = (int) (MathUtils.Random(Type.values().length)+Type.values().length/2);
	    Type type = Type.values()[type_index];
	    int R=(int) (MathUtils.Random(255)+127.5);
	    int G=(int) (MathUtils.Random(255)+127.5);
	    int B=(int) (MathUtils.Random(255)+127.5);
	    
	    
		Color[] colors= getColors(Color.fromRGB(R, G, B));
		Color[] fadecolors  = getColors(Color.WHITE);
		
		
		FireworkEffect effect = FireworkEffect.builder().trail(true).flicker(true).with(type).withColor(colors).withFade(fadecolors).withFlicker().withTrail().build();
		return effect;
	}
	
	public static Color HSBtoRGB(float H , float S ,float B){
        
		  int rgb = java.awt.Color.HSBtoRGB(H ,S ,B);
		    int red = (rgb >> 16) & 0xFF;
		    int green = (rgb >> 8) & 0xFF;
		    int blue = rgb & 0xFF;
		return Color.fromRGB(red, green, blue);
	}
	private static boolean isOutOfHSB(float general_FadeColor_Add,float fH, float fS, float fB) {
		float H = fH+general_FadeColor_Add;
		float S = fS+general_FadeColor_Add;
		float B = fB+general_FadeColor_Add;
		if(B>1||B<0){
			return true;
		}
		
		return false;
	}
	public static Block getTargetBlock(Player player , int MaxDistance){
		
			HashSet<Byte>hashSet = new HashSet<>(Arrays.asList((byte)0, (byte)Material.WATER.getId(),(byte)Material.STATIONARY_WATER.getId() , (byte)Material.LAVA.getId() , 
					(byte) Material.STATIONARY_LAVA.getId() , (byte)6,(byte)30 , (byte)31 , (byte)31 ,(byte)106 ));
			Block targetblock = player.getTargetBlock(hashSet, MaxDistance);
		
		return targetblock;
		
	}
	public static void WrongCommand(Player player, Command command) {
		String message =ChatColor.DARK_RED+ "[Info]: "+ChatColor.RED+"Wrong Command!"+"\n"+
						ChatColor.DARK_RED+"[Usage]: "+ChatColor.RED+command.getUsage();
		player.sendMessage(message);
		
	}
	public static float[] RGBtoHSB(int R , int G , int B){
		float[] HSB = new float[3];
		java.awt.Color.RGBtoHSB(R, G, B, HSB);
		return HSB;
	}
	public static FireworkEffect getEffect(Color color , Type type) {
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
	    
	    
		Color[] colors= getColors(Color.fromRGB(r, g, b));
		Color[] fadecolors  = getColors(Color.WHITE);
		
		
		FireworkEffect effect = FireworkEffect.builder().trail(true).flicker(true).with(type).withColor(colors).withFade(fadecolors).withFlicker().withTrail().build();
		return effect;
	}
}
