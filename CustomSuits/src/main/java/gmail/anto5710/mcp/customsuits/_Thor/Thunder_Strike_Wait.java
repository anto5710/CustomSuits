package gmail.anto5710.mcp.customsuits._Thor;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Thunder_Strike_Wait extends BukkitRunnable{
	static CustomSuitPlugin plugin;
	public Thunder_Strike_Wait(CustomSuitPlugin plugin){
		this.plugin = plugin;
	}
	public void run(){
		
		Start(Hammer.thor);
	}
	public static void Start(Player player) {
		Thunder_Strike.BaseLocation = player.getLocation();
		int Y = Thunder_Strike.BaseLocation.getBlockY()+200;
	
		Thunder_Strike.playEffect_Spawn(Thunder_Strike.BaseLocation ,player , Y);
			
						
				           
				       
				           
				        
		
		
			if(!Thunder_Strike.isStriking){
				
				Thunder_Strike.BaseLocation.getWorld().setStorm(true);
				Thunder_Strike.BaseLocation.getWorld().setThundering(true);
			BukkitTask task = new Thunder_Strike(plugin).runTaskTimer(plugin, 0,20);
			}
		
		
	}
}
