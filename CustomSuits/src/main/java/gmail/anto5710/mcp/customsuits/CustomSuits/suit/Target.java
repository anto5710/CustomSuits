package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageTranscoder;

import gmail.anto5710.mcp.customsuits.CustomSuits.dao.SpawningDao;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R2.util.UnsafeList.Itr;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Target extends BukkitRunnable{
	
	
	CustomSuitPlugin plugin;
	static boolean isRunning = false;
	public Thread thread;
	public Target(CustomSuitPlugin plugin){
		this.plugin = plugin;
		this.thread = new Thread(this,"T-SUIT_TARGET");
	}
	public void start(){
		this.thread.start();
	}
	public void run() throws IllegalStateException {
		
			int taskID = 0;
		try {
			
			 taskID = this.getTaskId();
			
		} catch (IllegalStateException e) {
			
		}
		
		if(SpawningDao.spawnMap.isEmpty()){
			isRunning = false;
			ThorUtils.cancel(taskID);
		}else{
			isRunning = true;
			Iterator<Player>iterator = getPlayers(SpawningDao.spawnMap);
			while(iterator.hasNext()){
				Player player = iterator.next();
				
				LivingEntity target = null;
				CustomSuitPlugin.removeDeadTarget(player);
				if(CustomSuitPlugin.target.containsKey(player)){
					
					target = CustomSuitPlugin.getTarget(player);
				}

					CustomSuitPlugin.targetPlayer(player, target , false);
				
			}
		}
		
	}
	private Iterator<Player> getPlayers(Map<String, String> Map) {
		List<Player>list = new ArrayList<Player>();
		Iterator<String>iterator = Map.values().iterator();
		while(iterator.hasNext()){
			String name = iterator.next();
			Player player =	Bukkit.getServer().getPlayer(name);
			if(list.isEmpty()&&player!=null){
				list.add(player);
			}else
			if(player!=null&&!list.contains(player)){
				list.add(player);
			}
		}
		
		return list.iterator();
	}
		
	
	

}
