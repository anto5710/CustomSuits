package gmail.anto5710.mcp.customsuits.CustomSuits;



import io.netty.buffer.ByteBuf;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.xml.stream.events.StartDocument;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.ManUtils;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits._Thor.Hammer;
import gmail.anto5710.mcp.customsuits._Thor.Thor_Changing;
import gmail.anto5710.mcp.customsuits._Thor.Thor_Move;
import net.minecraft.server.v1_8_R2.Entity;
import net.minecraft.server.v1_8_R2.EntityPlayer;
import net.minecraft.server.v1_8_R2.EnumParticle;
import net.minecraft.server.v1_8_R2.Item;
import net.minecraft.server.v1_8_R2.ItemStack;
import net.minecraft.server.v1_8_R2.Packet;
import net.minecraft.server.v1_8_R2.PacketDataSerializer;
import net.minecraft.server.v1_8_R2.PacketPlayInFlying.PacketPlayInPosition;
import net.minecraft.server.v1_8_R2.PacketPlayInFlying.PacketPlayInPositionLook;
import net.minecraft.server.v1_8_R2.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_8_R2.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R2.PlayerConnection;
import net.minecraft.server.v1_8_R2.Position;
import net.minecraft.server.v1_8_R2.WorldServer;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftFirework;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;


public class PlayEffect {
	static PlayEffect playEffect;
	static CustomSuitPlugin plugin;
	static float distance_between = 10F;
	public PlayEffect(CustomSuitPlugin plugin){
		this.plugin = plugin;
	}

	
	public static boolean play_Suit_Get(Location Location, Player player ) {
		double radius =2;
		double Y = Location.getY();
		for(double y = 0; y<10 ; y+=0.01){
		
			double x = Math.sin(y*radius);
			double z = Math.cos(y*radius);
			Location loc = Location.clone();
			loc.setX(loc.getX()+x);
			loc.setY(Y+y);
			loc.setZ(loc.getZ()+z);
			
			play_Arc_Reactor(loc);
			loc.subtract(x, 0, z);
		}
		return true;
		
	}
	public static void play_Rotation_Effect(double t,Location loc , Player player){
		
		double x;double y;double z;
		double xi;double yi;double zi;
		double xii;double yii; double zii;
		
		
		double r = 0.2;
		
		xii = Math.sin(2*t);
		yii= 2* Math.cos(t);
		zii = Math.sin(3*t);
		
		t-=Math.PI/200;
		
		xi = Math.sin(2*t);
		yi= 2* Math.cos(t);
		zi = Math.sin(3*t);
		t+=Math.PI/200;
		
		org.bukkit.util.Vector direction = new org.bukkit.util.Vector(xii-xi, yii-yi, zii-zi);
		
		Location finalLoc = new Location(player.getWorld(), 0, 0,0).setDirection(direction.normalize());
		
		finalLoc.setDirection(direction.normalize());
		
		if(t%1.0<0.1){
			for(double c = 0; c<=2*Math.PI; c+=Math.PI/8){
			x = 0.2*t;
			y = r*Math.sin(c)+2*Math.sin(10*t)+2.8;
			z = r*Math.cos(c);
			org.bukkit.util.Vector vector = new org.bukkit.util.Vector(x, y, z);
			vector = rotate(vector, finalLoc);
			loc.add(vector.getX(), vector.getY(), vector.getZ());
SuitUtils.playEffect(loc, EnumParticle.FLAME, 1, 0, 0);
			if(c==0){
				SuitUtils.playEffect(loc, EnumParticle.LAVA, 1, 0, 0);
			}
			loc.subtract(vector.getX(), vector.getY(), vector.getZ());
			
			}
		}
		
	}
		
	public static org.bukkit.util.Vector rotate(org.bukkit.util.Vector v , Location loc){
		double yawR = loc.getY()/180.0*Math.PI;
		double pitchR = loc.getPitch()/180.0*Math.PI;
		
		v = rotateX(v, pitchR);
		v = rotateY(v, -yawR);
		return v
				;
	}
	public static org.bukkit.util.Vector rotateX(org.bukkit.util.Vector v , double a){
		
		double y = Math.cos(a)*v.getX()-Math.sin(a)*v.getZ();
		double z = Math.sin(a)*v.getY()+Math.cos(a)*v.getZ();
		
		return v.setY(y).setZ(z);
		
		
	}
	public static org.bukkit.util.Vector rotateY(org.bukkit.util.Vector v , double a){
		
		double x = Math.cos(a)*v.getX()+Math.sin(a)*v.getZ();
		double z = -Math.sin(a)*v.getX()+Math.cos(a)*v.getZ();
		
		return v.setX(x).setZ(z);
		
		
	}
	
   public static org.bukkit.util.Vector rotateZ(org.bukkit.util.Vector v , double a){
		
		double x = Math.cos(a)*v.getX()-Math.sin(a)*v.getY();
		double y = Math.sin(a)*v.getX()+Math.cos(a)*v.getY();
		
		return v.setX(x).setY(y);
		
		
	}



	public static void play_Suit_NoDamageTime(Player player ,EnumParticle Effect){
		drawsphere(0, player.getLocation(), 0.8 , Effect);
	}
	public static void play_Suit_Missile_Effect(Location currentLoc,
			EnumParticle effect,int amount, int data ,boolean isRoof , boolean isMissile) {
	
			
			float x = (float) currentLoc.getX();
			float y = (float) currentLoc.getY();
			float z = (float) currentLoc.getZ();
			if(isMissile){
			if(isRoof)	{
				((CraftWorld)currentLoc.getWorld()).getHandle().sendParticles(null, EnumParticle.SPELL_MOB, true, x, y, z, 0, 0, 2.5, 1, 3, 1 , 0, 0, 0, 0 ,0);
				((CraftWorld)currentLoc.getWorld()).getHandle().sendParticles(null, EnumParticle.SPELL_MOB, true, x, y, z, 0, 0, 0.1, 1, 3, 1 , 0, 0, 0, 0 ,0);
				((CraftWorld)currentLoc.getWorld()).getHandle().sendParticles(null, EnumParticle.SPELL_MOB_AMBIENT, true, x, y, z, 0, 0, 2.5, 1, 3, 1 , 0, 0, 0, 0 ,0);
				((CraftWorld)currentLoc.getWorld()).getHandle().sendParticles(null, EnumParticle.SPELL_MOB_AMBIENT, true, x, y, z, 0, 0, 0.1, 1, 3, 1 , 0, 0, 0, 0 ,0);
				
			}else{
				play_Arc_Reactor(currentLoc);
			
			}
			}else{
				((CraftWorld)currentLoc.getWorld()).getHandle().sendParticles(null, effect, true, x, y, z, 0, 0, 0, 1, 0, 0 , 0, 0, 0, 0 ,0);
			}
			
			
			
	}
	


//	public static void Explode_Missile(Player player , Location loc , int count, double r ){
//	
//		loc.add(0, -1, 0);
//		 double t = Math.PI/4;
//         drawring(t, loc, count, player);
////         	double phi = 0;
////         	drawsphere(phi , loc.clone() , player);
//                 }
//                
         
                                
	public static void drawsphere(double phi, Location loc,double r , EnumParticle effect) {
		for( ; phi<=8*Math.PI ;phi+=Math.PI/10){
			run(r, loc, phi ,effect);
		}
		
	}

	private static void run(double r , Location loc , double phi , EnumParticle particle ) {
		for(double theta = 0 ;theta <= 2*Math.PI ; theta +=Math.PI/40){
			double x = r*Math.cos(theta)*Math.sin(phi);
			double y = r*Math.cos(phi) + 1.5;
			double z = r*Math.sin(theta)*Math.sin(phi);
			loc.add(x, y, z);
			if(particle==null){
				play_Arc_Reactor(loc);
			}else{
				((CraftWorld)loc.getWorld()).getHandle().sendParticles(null,particle, true, loc.getX(), loc.getY(), loc.getZ(), 1, 0, 0, 0,0,0,0);
			}
			loc.subtract(x, y, z);
		}
	}

	private static void drawring(double t , Location loc , int count ,EnumParticle effect) {
		for(int c = 0 ;c <=count ; c++){
            t = t + 0.1*Math.PI;
            for (double theta = 0; theta <= 2*Math.PI; theta+= Math.PI/32){
           	 double x = t*Math.cos(theta);
				double y = 2*Math.exp(-0.1*t) * Math.sin(t) + 1.5;
				double z = t*Math.sin(theta);
				loc.add(x,y,z);
				SuitUtils.playEffect(loc, effect	, 2, 0,0);
				
				loc.subtract(x,y,z);
            }
            }
	}
		
		private static void drawring_flat(double t , Location loc , int count ,EnumParticle effect) {
			for(int c = 0 ;c <=count ; c++){
	            t = t + 0.1*Math.PI;
	            for (double theta = 0; theta <= 2*Math.PI; theta+= Math.PI/32){
	           	 double x = t*Math.cos(theta);
					double z = t*Math.sin(theta);
					loc.add(x,0,z);
					SuitUtils.playEffect(loc, effect	, 2, 0,0);
					
					loc.subtract(x,0,z);
					
	            }
		}




	}
	public static void play_Gun_Shot_Effect(Player player) {
		EnumParticle e = EnumParticle.BLOCK_CRACK;
		SuitUtils.playEffect(player.getEyeLocation(), e	, 10, Material.ANVIL.getId(),0);
		
	}

	public static void play_Man_Bomb_Effect(Location location) {
		
		
	}

	public static void play_Man_Smoke_Effect(Location location) {
		
		
	}

	public static void play_Man_Sword_Shot_Effect(Location currentLoc, Player player) {
	
		
	}

	public static void play_Man_Move(Player player) {
		
	}

	public static void play_Man_visible(Player player) {
	}


	/**
	 * 
	 * +--+--+--+--+
	 * |  |  |  |  |
	 * +--C--C--C--+
	 * |  |  |  |  |
	 * +--+--P--C--+
	 * |  |  |  |  |
	 * +--C--+--C--+
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public static void playSuit_Move_Fly_Effect(Location location, Player player)    {
location.add(0, -0.2, 0);
		
		Location newloc =location.clone();
		float yaw = location.getYaw()+90;
		if(yaw>360){
			yaw -=360;
		}
		newloc.setYaw(yaw);
		newloc.setPitch(90-distance_between);
		org.bukkit.util.Vector v = newloc.getDirection();
		v.setY(0);		
        location.add(v)
        
        
;				play_Arc_Reactor(location);
					
				location.subtract(v);
					 yaw = location.getYaw()+90+180;
					if(yaw>360){
						yaw -=360;
					}
					newloc.setYaw(yaw);
					newloc.setPitch(90-distance_between);
					v = newloc.getDirection();
					v.setY(0);
					location.add(v);
					play_Arc_Reactor(location);
						
	}

	public static void playSuit_Move_Under_Water_Effect(Location location, Player player) {
		location.add(0, -0.2, 0);
		
		Location newloc =location.clone();
		float yaw = location.getYaw()+90;
		if(yaw>360){
			yaw -=360;
		}
		newloc.setYaw(yaw);
		newloc.setPitch(90-distance_between);
		org.bukkit.util.Vector v = newloc.getDirection();
		v.setY(0);		
        location.add(v)
        
        
;					SuitUtils.playEffect(location, EnumParticle.WATER_BUBBLE, 40,0, 0 );
					location.subtract(v);
					 yaw = location.getYaw()+90+180;
					if(yaw>360){
						yaw -=360;
					}
					newloc.setYaw(yaw);
					newloc.setPitch(90-distance_between);
					v = newloc.getDirection();
					v.setY(0);
					location.add(v);
					
					SuitUtils.playEffect(location, EnumParticle.WATER_BUBBLE, 40,0, 0 );
		
	}



	public static void playSuit_Move_Effect(Location location,
			org.bukkit.entity.Entity entity) {
		location.add(0, -0.2, 0);
		
		Location newloc =location.clone();
		float yaw = location.getYaw()+90;
		if(yaw>360){
			yaw -=360;
		}
		newloc.setYaw(yaw);
		newloc.setPitch(90-distance_between);
		org.bukkit.util.Vector v = newloc.getDirection();
		v.setY(0);		
        location.add(v)
        
        
;				play_Arc_Reactor(location);
					
				location.subtract(v);
					 yaw = location.getYaw()+90+180;
					if(yaw>360){
						yaw -=360;
					}
					newloc.setYaw(yaw);
					newloc.setPitch(90-distance_between);
					v = newloc.getDirection();
					v.setY(0);
					location.add(v);
					play_Arc_Reactor(location);
					
	}
	public static void play_Arc_Reactor(Location location){
		((CraftWorld)location.getWorld()).getHandle().sendParticles(null, EnumParticle.SPELL_MOB, true, location.getX(), location.getY(), location.getZ(), 0, 0, 3.2, 1, 3, 1 , 0, 0, 0, 0 ,0);
		((CraftWorld)location.getWorld()).getHandle().sendParticles(null, EnumParticle.SPELL_MOB, true,location.getX(), location.getY(), location.getZ(), 0, 0, 2, 1, 3, 1 , 0, 0, 0, 0 ,0);
		 ((CraftWorld)location.getWorld()).getHandle().sendParticles(null, EnumParticle.SPELL_MOB_AMBIENT, true, location.getX(), location.getY(), location.getZ(), 0, 0, 3.2, 1, 3, 1 , 0, 0, 0, 0 ,0);
			((CraftWorld)location.getWorld()).getHandle().sendParticles(null, EnumParticle.SPELL_MOB_AMBIENT, true,location.getX(), location.getY(), location.getZ(), 0, 0, 2, 1, 3, 1 , 0, 0, 0, 0 ,0);
			
	}
	public static void play_Thor_Change_Effect(Player player , double phi) {
		if(player!=Hammer.thor){
		if(Hammer.thor!=null){
			return;
		}
		
		}
			
          Location location = player.getLocation();
          if(!Thor_Changing.players.containsKey(player)){
        	  if(Thor_Changing.players.isEmpty()){
        		 BukkitTask task = new Thor_Changing(plugin).runTaskTimer(plugin, 0, 3);
        	  }
        	  Thor_Changing.players.put(player, location);
          }
         
        
	}


	public static void play_Thunder_Strike_Start_Effect(final Location location,
			final Player player) {
	
					
					double y= 0 ;
					double radius = 5;
					Location Location = location.clone();
						
					for(;y<=200;y+=0.1){	
							
							double x = Math.sin(y*radius);
							double z = Math.cos(y*radius);
							Location loc = Location.clone();
							loc.add(x, y, z);
							int Int_Y= (int) y;
							if(Int_Y%50==0){
								loc.getWorld().playSound(loc, Sound.IRONGOLEM_DEATH, 6F,6F);
							}
							SuitUtils.playEffect(loc, EnumParticle.FLAME, 1, 0, 0);
							SuitUtils.playEffect(loc, EnumParticle.SMOKE_LARGE, 1, 0, 0);
							loc.subtract(x ,  y ,z);
					}
					
					new BukkitRunnable() {
						
						@Override
						public void run() {
							
							Hammer.Start_Thunder_strike(player);
						}
					}.runTaskLater(plugin, 20);
				
	}


	public static void play_Hammer_Hit_Ground(org.bukkit.entity.Item item) {
		Location location = item.getLocation();
		double Y = location.getY();
		Y--;
		double radius = 0.5;
		for(;radius<=2;radius+=0.5){
			drawring_flat(radius, location, 1 , EnumParticle.FIREWORKS_SPARK);
			location.setY(Y);
			
			Y+=0.8;
		}
	}


	public static void play_Thunder_Strike_End(Wither wither) {
		FireworkEffect effect = FireworkEffect.builder().with(Type.BALL_LARGE).withColor(Color.RED , Color.MAROON ).withFade(Color.GRAY , Color.BLACK  ,Color.WHITE , Color.SILVER).withFlicker().withTrail().trail(true).build();
		FireworkPlay.spawn(wither.getLocation(), effect, Hammer.thor);
		}


	


	
	
	

}
