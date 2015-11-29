package gmail.anto5710.mcp.customsuits.CustomSuits;





import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits._Thor.Hammer;
import gmail.anto5710.mcp.customsuits._Thor.Thor_Changing;
import net.minecraft.server.v1_8_R2.EnumParticle;
import net.minecraft.server.v1_8_R2.PacketPlayOutWorldParticles;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;


public class PlayEffect {
	static PlayEffect playEffect;
	static CustomSuitPlugin plugin;
	static float distance_between = 10F;
	public PlayEffect(CustomSuitPlugin plugin){
		this.plugin = plugin;
	}

	
	public static void play_Suit_Get(final Location Location, Player player ) {
		new BukkitRunnable() {
			double radius =2;
			double Y = Location.getY();
			double y= 0;
			@Override
			public void run() {
				for(int c = 0 ; c<10 ; c++){
				if(y>10){
					this.cancel();
				}
				double x = Math.sin(y*radius);
				double z = Math.cos(y*radius);
				Location loc = Location.clone();
				loc.setX(loc.getX()+x);
				loc.setY(Y+y);
				loc.setZ(loc.getZ()+z);
				
				play_Arc_Reactor(loc);
				org.bukkit.util.Vector vector =new org.bukkit.util.Vector(x, 0, z).multiply(-1);
				loc.subtract(x, 0, z);
				play_Arc_Reactor(loc.clone().add(vector));
				y+=0.05;
				}
			}
		}.runTaskTimer(plugin, 0, 1);
		
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



	public static void play_Suit_NoDamageTime(final Player player ,final EnumParticle Effect){
		
			
		FireworkPlay.spawn(player.getLocation(), SuitUtils.getEffect(Color.AQUA , Type.STAR), null);
				
			
	}
	public static void play_Suit_Missile_Effect(Location currentLoc,
			EnumParticle effect,int amount, int data ,boolean isRoof , boolean isMissile) {
	
			
			float x = (float) currentLoc.getX();
			float y = (float) currentLoc.getY();
			float z = (float) currentLoc.getZ();
			if(isMissile){
			
				play_Arc_Reactor(currentLoc);
			
			}else{
				((CraftWorld)currentLoc.getWorld()).getHandle().sendParticles(null, effect, true, x, y, z, 0, 0, 0, 1, 0, 0 , 0, 0, 0, 0 ,0);
			}
			
			
			
	}
	

                                
	public static void drawsphere(final double phi, final Location loc,final double r , final EnumParticle effect) {
			new BukkitRunnable() {
				double PI = phi;
				@Override
				public void run() {
					if(PI>8*Math.PI){
						this.cancel();
					}
					playEffect.run(r, loc, PI, effect);
					
					
					PI+=Math.PI/10;
				}
		}.runTaskTimer(plugin, 0, 1);
		
	}

	public static void run(double r , Location loc , double phi , EnumParticle particle ) {
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
            t+=0.1*Math.PI;
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
	 SuitUtils.playEffect( EnumParticle.SPELL_MOB,  location.getX(), location.getY(), location.getZ(), 0, 0, 2.25, 1, 3, 1 , 0, 0, 0, 0 ,0);
	 SuitUtils.playEffect( EnumParticle.SPELL_MOB, location.getX(), location.getY(), location.getZ(), 0, 0, 2, 1, 3, 1 , 0, 0, 0, 0 ,0);
	 SuitUtils.playEffect( EnumParticle.SPELL_MOB_AMBIENT, location.getX(), location.getY(), location.getZ(), 0, 0, 2.25, 1, 3, 1 , 0, 0, 0, 0 ,0);
	 SuitUtils.playEffect( EnumParticle.SPELL_MOB_AMBIENT,location.getX(), location.getY(), location.getZ(), 0, 0, 2, 1, 3, 1 , 0, 0, 0, 0 ,0);
	 SuitUtils.playEffect( EnumParticle.SPELL_MOB, location.getX(), location.getY(), location.getZ(), 0, 0, 2.25, 1, 3, 1 , 0, 0, 0, 0 ,0);
	 SuitUtils.playEffect( EnumParticle.SPELL_MOB, location.getX(), location.getY(), location.getZ(), 0, 0, 2, 1, 3, 1 , 0, 0, 0, 0 ,0);
	 SuitUtils.playEffect( EnumParticle.SPELL_MOB_AMBIENT, location.getX(), location.getY(), location.getZ(), 0, 0, 2.25, 1, 3, 1 , 0, 0, 0, 0 ,0);
	 SuitUtils.playEffect( EnumParticle.SPELL_MOB_AMBIENT, location.getX(), location.getY(), location.getZ(), 0, 0, 2, 1, 3, 1 , 0, 0, 0, 0 ,0);
				
				
	}
	public static void play_Thor_Change_Effect(Player player , double phi) {
		if(player!=Hammer.thor){
		if(Hammer.thor!=null){
			return;
		}
		
		}
			
          Location location = player.getLocation();
          if(!Thor_Changing.Changing_players.containsKey(player)){
        	  if(Thor_Changing.Changing_players.isEmpty()){
        		 BukkitTask task = new Thor_Changing(plugin).runTaskTimer(plugin, 0, 3);
        	  }
        	  Thor_Changing.Changing_players.put(player, location);
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


	public static void play_Hammer_Hit_Ground(final org.bukkit.entity.Item item) {
//		int n = 1;
//		Location location = item.getLocation();
//		Vector directionI = new Vector(-n,  0 ,0);
//		Vector directionII = new Vector(n,  0 ,0);
//		Vector directionIII = new Vector(-n,  0 ,n);
//		Vector directionIV = new Vector(-n,  0 ,-n);
//		Vector directionV = new Vector(n,  0 ,n);
//		Vector directionVI = new Vector(n,  0 ,-n);
//		Vector directionVII = new Vector(0,  0 ,n);
//		Vector directionVIII = new Vector(0,  0 ,-n);
//		
//		
//		Particletrail( location,directionI ,2);
//		Particletrail( location,directionII ,2);
//		Particletrail( location,directionIII ,2);
//		Particletrail( location,directionIV ,2);
//		Particletrail( location,directionV ,2);
//		Particletrail( location,directionVI ,2);
//		Particletrail( location,directionVII ,2);
//		Particletrail( location,directionVIII ,2);
		new BukkitRunnable() {
			Location loc = item.getLocation();
			double t = 0;
			int count = 0;
			double a = 0.2;
			@Override
			public void run() {
				if(count>=500){
					this.cancel();
				}
			for(int c =1 ; c<15 ;c++){	
				t+=0.01*Math.PI;
				for (double theta = 0; theta <= 2*Math.PI; theta+= Math.PI/4){
		           	 double x = t*Math.cos(theta)*a;
						double y = 0.65*Math.exp(-0.1*t) * Math.sin(t) + 1.5;
						double z = t*Math.sin(theta)*a;
					loc.add(x,y,z);
					SuitUtils.playEffect(loc,EnumParticle.FLAME	, 2, 0,0);
					loc.subtract(x,y,z);
				}
				count++;
			}
		}
			
            }.runTaskTimer(plugin, 0, 1);
	}


//	private static void Particletrail( final Location location,Vector direction, int r) {
//		final double max_y_offset = 0.25;
//		direction.normalize();
//		direction.multiply(0.1);
//		final Vector v = direction;
//		new BukkitRunnable() {
//			double add_y_offset = 0.02;
//			double current_y_offset = 0;
//			int count = 0;
//			Location loc = location.clone();
//			@Override
//			public void run() {
//				if(count>=40){
//					this.cancel();
//				}
//				loc.add(v);
//				loc.add(0, add_y_offset, 0);
//				current_y_offset+=add_y_offset;
//				SuitUtils.playEffect(loc, EnumParticle.FLAME, 1, 0, 0);
//				if(current_y_offset>=max_y_offset){
//					add_y_offset*=-1;
//				}
//				else if(current_y_offset<=0){
//					add_y_offset*=-1;
//				}
//				
//				count ++;
//			}
//		}.runTaskTimer(plugin, 0, 1);
//	}


	public static void play_Thunder_Strike_End(Wither wither) {
		FireworkEffect effect = SuitUtils.getEffect(Color.MAROON, Type.BALL_LARGE);
		FireworkPlay.spawn(wither.getLocation(), effect, Hammer.thor);
		}

		
			
			
		


	


	
	
	

}
