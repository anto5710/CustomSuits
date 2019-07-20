package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.Utils.ParticleUtil;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_13_R2.util.UnsafeList.Itr;
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
				ParticleUtil.playEffect(Particle.CRIT, snowball.getLocation(), 1);
			}
		}
		snowballs.removeAll(removed);
		removed.clear();
	}
	

	
}
