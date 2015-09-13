package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Cooldown extends BukkitRunnable{
	 private final JavaPlugin plugin;
	 private final Player player;
	 
	    public Cooldown(JavaPlugin plugin , Player player) {
	        this.plugin = plugin;
	        this.player = player;
	    }
	 
	    @Override
	    public void run() {
	    	player.playSound(player.getLocation(), Sound.IRONGOLEM_HIT, 4.0F, 4.0F);	
        	
        	
        	
        	player.playSound(player.getLocation(), Sound.DIG_WOOD, 15.0F,3.5F);
        	player.playSound(player.getLocation(), Sound.DOOR_CLOSE, 4.0F, 2.5F);
        	player.playSound(player.getLocation(), Sound.DOOR_OPEN, 4.0F, 4.0F);	
	    WeaponListner.cooldowns.put(player, false);
	    
	       
	        
	    }
}
