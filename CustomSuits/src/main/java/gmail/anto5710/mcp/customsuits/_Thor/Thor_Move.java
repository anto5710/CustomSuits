package gmail.anto5710.mcp.customsuits._Thor;

import gmail.anto5710.mcp.customsuits.CustomSuits.PlayEffect;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.server.v1_8_R2.EnumParticle;
import net.minecraft.server.v1_8_R2.World;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class Thor_Move extends BukkitRunnable{
	
	   
		CustomSuitPlugin plugin;
		static List<Player>list = new ArrayList<>();
		static List<Player>removed = new ArrayList<>();
		static HashMap<Player,Location>locations = new HashMap<>();
		static HashMap<Player,Double>Add_Y_Offset = new HashMap<>();
		static HashMap<Player, Boolean>is_Landing = new HashMap<>();
		
		static HashMap<Player, Double>count = new HashMap<>();
		static double radius = 1;
		public Thor_Move(CustomSuitPlugin plugin){
			this.plugin = plugin;
		}
		@Override
		public void run() throws IllegalStateException{
			Iterator<Player>iterator = list.iterator();
			if(list.isEmpty()){
				try {
					ThorUtils.cancel(getTaskId());
					
				} catch (IllegalStateException e) {
				}
			}
			while(iterator.hasNext()){
				Player player = iterator.next();
				if(removed.contains(player)){
					iterator.remove();
				}
				if(!Hammer.Thor(player)){
					removed.add(player);
					iterator.remove();
				}
				if(player.isDead()){
					removed.add(player);
					iterator.remove();
				}
				if(!count.containsKey(player)){
					count.put(player, (double) 0);
				}
				count.put(player, count.get(player)+1.50);
					locations.put(player, player.getLocation());
				
					if(!Add_Y_Offset.containsKey(player)){
						Add_Y_Offset.put(player, (double) 0);
					}
					if(!is_Landing.containsKey(player)){
						is_Landing.put(player, true);
					}
					Location loc = locations.get(player).clone();
					
					
					double y = loc.getY()+Add_Y_Offset.get(player);
					double add = count.get(player);
					double x = Math.sin(add*radius);
					double z = Math.cos(add*radius);
					Location locClone = loc.clone();
					
					loc.setX(loc.getX()+x);
					loc.setZ(loc.getZ()+z);	
					loc.setY(y);
							
							
						PlayEffect(loc);
					

					locClone.setX(locClone.getX()-x);
					locClone.setZ(locClone.getZ()-z);	
					locClone.setY(y);
					
					
					PlayEffect(locClone);
					
					if(!is_Landing.get(player)){
							Add_Y_Offset.put(player, Add_Y_Offset.get(player)+0.02);
					}else{
						Add_Y_Offset.put(player, Add_Y_Offset.get(player)-0.02);
					}
					if(Add_Y_Offset.get(player)>=2){
						is_Landing.put(player, true);
					}
					if(Add_Y_Offset.get(player)<=0){
						is_Landing.put(player, false);
					}
					
			}
			
			list.removeAll(removed);
			remove(removed);
			removed.clear();
		}
		private void PlayEffect(Location loc) {
				SuitUtils.playEffect(loc, EnumParticle.FIREWORKS_SPARK,5, 0, 0);
				
					
					
		}

		   
		    /**
		     * Internal method, used as shorthand to grab our method in a nice friendly manner
		     * @param cl
		     * @param method
		     * @return Method (or null)
		     */
		    
		 
		
		 
		   
		
		private void remove(List<Player> removed) {
		for(Player player: removed){
			locations.remove(player);
			Add_Y_Offset.remove(player);
		}
			
		}
		
	

}
