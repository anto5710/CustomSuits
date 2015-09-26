package gmail.anto5710.mcp.customsuits.Man;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.SchedulerHunger;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.ManUtils;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;

import java.awt.List;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RepeatMan extends BukkitRunnable{
	private CustomSuitPlugin mainPlugin;
	
	
	
	public RepeatMan(CustomSuitPlugin main) {
		this.mainPlugin = main;
		
		
	}

	
	@Override
		public void run() {
			
			java.util.List<Player>HidePlayer = ManUtils.HiddenPlayers;
		
			if(HidePlayer == null){
				ThorUtils.cancel(getTaskId());
				
			}
			Iterator<Player> iterator = HidePlayer.iterator();
			if(HidePlayer.size()==1){
				Player player = HidePlayer.get(0);
				if(player.getFoodLevel()>=Values.ManInvisibleHunger*-1){
					SchedulerHunger.hunger(player, Values.ManInvisibleHunger);
				}else{	
					ManUtils.setVisible(player);
				}
					
				
			}else{
			while(iterator.hasNext()){
				Player player = iterator.next();
				if(player.getFoodLevel()>=Values.ManInvisibleHunger*-1){
				SchedulerHunger.hunger(player, Values.ManInvisibleHunger);
				}else{
					
				ManUtils.setVisible(player);
				}
			
				
				
			}
			}
			
		
		}
		public static void addPlayer (Player player ){
			if(!ManUtils.HiddenPlayers.contains(player)){
				
			
			ManUtils.HiddenPlayers.add(player);
				
			}
		}
		public static void removePlayer (Player player ){
			if(!ManUtils.HiddenPlayers.contains(player)){
				return;
			}
			ManUtils.HiddenPlayers.remove(player);
		}
		
	
}
