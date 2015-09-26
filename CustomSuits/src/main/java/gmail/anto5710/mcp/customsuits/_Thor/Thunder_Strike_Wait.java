package gmail.anto5710.mcp.customsuits._Thor;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;

import org.bukkit.scheduler.BukkitRunnable;

public class Thunder_Strike_Wait extends BukkitRunnable{
	CustomSuitPlugin plugin;
	public Thunder_Strike_Wait(CustomSuitPlugin plugin){
		this.plugin = plugin;
	}
	public void run(){
		
		Thunder_Strike.Start(Hammer.thor);
	}
}
