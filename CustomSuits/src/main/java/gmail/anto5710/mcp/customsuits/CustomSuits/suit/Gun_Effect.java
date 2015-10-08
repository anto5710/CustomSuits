package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.server.v1_8_R2.EnumParticle;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R2.util.UnsafeList.Itr;
import org.bukkit.entity.Snowball;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Gun_Effect extends BukkitRunnable{
	CustomSuitPlugin plugin;
    static ArrayList<Snowball>snowballs = new ArrayList<>();
	public static ArrayList<Snowball> removed = new ArrayList<>();
	public static boolean isRunning = false;
	public Gun_Effect(CustomSuitPlugin plugin){
		this.plugin = plugin;
	}
	@Override
	public void run() throws IllegalStateException{
		
		if(snowballs.size() == 0){
			try {
				isRunning = false;
				ThorUtils.cancel(getTaskId());
			} catch (IllegalStateException e) {
			}
		}
		Iterator<Snowball>iterator = snowballs.iterator();
		while(iterator.hasNext()){
			isRunning = true;
			Snowball snowball = iterator.next();
			if(removed.contains(snowball)){
				iterator.remove();
			}
			else{
				SuitUtils.playEffect(snowball.getLocation(), EnumParticle.CRIT, 1, 0, 0);
			}
		}
		snowballs.removeAll(removed);
		removed.clear();
	}
	

	
}
