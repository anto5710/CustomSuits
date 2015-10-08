package gmail.anto5710.mcp.customsuits.CustomSuits;



import java.util.List;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits._Thor.Hammer;
import gmail.anto5710.mcp.customsuits._Thor.Thor_Changing;
import gmail.anto5710.mcp.customsuits._Thor.Thor_Move;
import net.minecraft.server.v1_8_R2.EnumParticle;
import net.minecraft.server.v1_8_R2.Item;
import net.minecraft.server.v1_8_R2.ItemStack;
import net.minecraft.server.v1_8_R2.PacketPlayOutWorldParticles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;


public class PlayEffect {
	static PlayEffect playEffect;
	static CustomSuitPlugin plugin;
	public PlayEffect(CustomSuitPlugin plugin){
		this.plugin = plugin;
	}

	
	public static void play_Suit_Get(Location Location, Player player ) {
		double radius =2;
		double count = 0;
		for(double y = 0; y<10 ; y+=0.01){
			count +=0.05;
			double x = Math.sin(count*radius);
			double z = Math.cos(count*radius);
			Location loc = Location.clone();
			loc.setX(loc.getX()+x);
			loc.setY(loc.getY()+y);
			loc.setZ(loc.getZ()+z);	
			SuitUtils.playEffect(loc, EnumParticle.FLAME, 1,0, 0 );
			loc.setX(loc.getX()-2*x);
			loc.setY(loc.getY()-2*y);
			loc.setZ(loc.getZ()-2*z);	
			SuitUtils.playEffect(loc, EnumParticle.FLAME, 1,0, 0 );
		}
		
	}
	public static void play_Suit_Spawning_Effect(Location loc , int amount , int data , Player player , org.bukkit.inventory.ItemStack[] itemStacks){
		
		float x = (float)loc.getX();
		float y = (float) loc.getY();
		float z = (float) loc.getZ();
		if (itemStacks.length==0) {
			for(double addy = 0; y<=2 ; y+=0.5)
			loc.setY(y+addy);
			
			
			SuitUtils.playEffect(loc, EnumParticle.SPELL_MOB,1,0,0);
		}
		for(org.bukkit.inventory.ItemStack item : itemStacks){
			
		loc.setY(y);
		
		
		SuitUtils.playEffect(loc, EnumParticle.ITEM_CRACK,1,item.getTypeId(),0);
		y+=0.5;
		
		}
		
	}
	public static void play_Suit_NoDamageTime(Player player){
		drawsphere(0, player.getLocation(), EnumParticle.FLAME , 0.8);
	}
	public static void play_Suit_Missile_Effect(Location currentLoc,
			EnumParticle effect,int amount, int data , Player player ,boolean roof) {
	
			
			float x = (float) currentLoc.getX();
			float y = (float) currentLoc.getY();
			float z = (float) currentLoc.getZ();
			
			PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
				   effect , true, x, y, z, 0.001F,0.001F, 0.001F, amount, 0 , data , 0 , 0 );
			((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
			if(roof){
			PacketPlayOutWorldParticles packet2 = new PacketPlayOutWorldParticles(
					effect , true, x, y, z, 0,0, 0, amount, 0 , data , 0 , 0 );
			PacketPlayOutWorldParticles packet3 = new PacketPlayOutWorldParticles(
					effect					   , true, x, y, z, -0.001F,-0.001F, -0.001F, amount, 0 , data , 0 , 0 );
			((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet2);
			((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet3);
			}
				
			
	}
	public static void Explode_Missile(Player player , Location loc , int count, double r ){
	
		loc.add(0, -1, 0);
		 double t = Math.PI/4;
         drawring(t, loc, count, player);
//         	double phi = 0;
//         	drawsphere(phi , loc.clone() , player);
                 }
                
         
                                
	public static void drawsphere(double phi, Location loc, EnumParticle effect, double r) {
		for( ; phi<=8*Math.PI ;phi+=Math.PI/10){
			run(r, loc, phi, effect);
		}
		
	}

	private static void run(double r , Location loc , double phi , EnumParticle effect) {
		for(double theta = 0 ;theta <= 2*Math.PI ; theta +=Math.PI/40){
			double x = r*Math.cos(theta)*Math.sin(phi);
			double y = r*Math.cos(phi) + 1.5;
			double z = r*Math.sin(theta)*Math.sin(phi);
			loc.add(x, y, z);
			SuitUtils.playEffect(loc,effect, 2, 0,0);
			loc.subtract(x, y, z);
		}
	}

	private static void drawring(double t , Location loc , int count , Player player) {
		for(int c = 0 ;c <=count ; c++){
            t = t + 0.1*Math.PI;
            for (double theta = 0; theta <= 2*Math.PI; theta+= Math.PI/32){
           	 double x = t*Math.cos(theta);
				double y = 2*Math.exp(-0.1*t) * Math.sin(t) + 1.5;
				double z = t*Math.sin(theta);
				loc.add(x,y,z);
				SuitUtils.playEffect(loc, EnumParticle.CLOUD	, 2, 0,0);
				
				loc.subtract(x,y,z);
				
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
		float distance_between = 10;
		
		Location newloc =location.clone();
		float yaw = location.getYaw()+90;
		if(yaw>360){
			yaw -=360;
		}
		newloc.setYaw(yaw);
		newloc.setPitch(90-distance_between);
		org.bukkit.util.Vector v = newloc.getDirection();
		v.setY(0);		
        location.add(v);
    	SuitUtils.playEffect(location, EnumParticle.FLAME, 5, 17, 0);
		
       
	
        
//;					
					location.subtract(v);
					 yaw = location.getYaw()+90+180;
					if(yaw>360){
						yaw -=360;
					}
					newloc.setYaw(yaw);
					newloc.setPitch(90-distance_between);
					v = newloc.getDirection();
					v.setY(5);
					v.setX(10);
					SuitUtils.playEffect(location, EnumParticle.FLAME, 5, 17, 0);
				
	}

	public static void playSuit_Move_Under_Water_Effect(Location location, Player player) {
		float distance_between = 10;
		
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
			Player player) {
float distance_between = 10;
		
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
        
        
;					SuitUtils.playEffect(location, EnumParticle.FLAME, 4,0, 0 );
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
					SuitUtils.playEffect(location, EnumParticle.FLAME, 4,0, 0 );
		
	}
	public static void play_Thor_Change_Effect(Player player , double phi) {
		if(Hammer.thor==null){
			Hammer.thor = player;
		}
		if(player!=Hammer.thor){
			return;
		}
			
          Location location = player.getLocation();
          if(!Thor_Changing.players.containsKey(player)){
        	  if(Thor_Changing.players.isEmpty()){
        		 BukkitTask task = new Thor_Changing(plugin).runTaskTimer(plugin, 0, 3);
        	  }
        	  Thor_Changing.players.put(player, location);
          }
         
        
	}


	public static void play_Thunder_Strike_Start_Effect(Location location,
			Player player) {
		
	}
	
	


	
	
	

}
