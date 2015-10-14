package gmail.anto5710.mcp.customsuits._Thor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R2.util.UnsafeList.Itr;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;
import net.minecraft.server.v1_8_R2.EnumParticle;

public class Thor_Changing extends BukkitRunnable{
	CustomSuitPlugin plugin;
	public static HashMap<Player, Location>players = new HashMap<>();
	public static HashMap<Player, Double>Phi = new HashMap<>();
	
	List<Player>removed = new ArrayList<>();
	 
	public Thor_Changing(CustomSuitPlugin plugin){
		this.plugin = plugin;
	}
	
	

	@Override
	public void run() {
		Iterator<Player>iterator = players.keySet().iterator();
		
		if(players.isEmpty()){
			try {
				for(Player player: removed){
					players.remove(player);
					Phi.remove(player);
					
				}
				ThorUtils.cancel(getTaskId());
				
			} catch (IllegalStateException e) {
			}
		}
		
		
		while(iterator.hasNext()){
			
			Player player = iterator.next();
			if(player.isDead()){
				if(!removed.contains(player)){
				 removed.add(player);
					iterator.remove();
				}
			}
			if(!Bukkit.getServer().getOnlinePlayers().contains(player)){
				if(!removed.contains(player)){
				
					 removed.add(player);
						iterator.remove();
					}
			}
			
			if(!Phi.containsKey(player)){
				Phi.put(player, 0D);
			}
			double phi =Phi.get(player);
		                                     
              double x = 0, y = 0, z = 0;                
             
              Location location = player.getLocation();
              runEffect( phi , x , y ,z , location);
             
              if(phi > 10*Math.PI){                                          
                    removed.add(player);
                	iterator.remove();
                    Hammer.setThor(player);
              }else{
              Phi.put(player, phi+=Math.PI/8);
               
              player.playSound(player.getLocation(), Sound.FUSE, 6F, 6F);
              }
                 
         
	 }
		for(Player player: removed){
			players.remove(player);
			Phi.remove(player);
			
		}
		removed.clear();
	 }



	private void runEffect(double phi, double x, double y, double z , Location location ) {
		for (double t = 0; t <= 2*Math.PI; t = t + Math.PI/16){
           runEffectII(t , location , phi , x , y, z);
    }              
		
	}



	private void runEffectII(double t, Location location, double phi ,double x , double y ,double z) {
		 for (double i = 0; i <= 1; i = i + 1){
             x = 0.4*(2*Math.PI-t)*0.5*Math.cos(t + phi + i*Math.PI);
             y = 0.5*t;
             z = 0.4*(2*Math.PI-t)*0.5*Math.sin(t + phi + i*Math.PI);
             location.add(x, y, z);
             SuitUtils.playEffect(location, EnumParticle.REDSTONE, 1,0, 0);
             location.subtract(x,y,z);
     }
    
		
	}
		
}
