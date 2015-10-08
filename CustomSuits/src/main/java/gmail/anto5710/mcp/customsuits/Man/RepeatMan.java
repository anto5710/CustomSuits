package gmail.anto5710.mcp.customsuits.Man;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.SchedulerHunger;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.ManUtils;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;

import java.awt.List;
import java.util.ArrayList;
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
			ArrayList<Player>removed = new ArrayList<>();
			if(HidePlayer.isEmpty()){
				ThorUtils.cancel(getTaskId());
				
			}
			Iterator<Player> iterator = HidePlayer.iterator();
			
			while(iterator.hasNext()){
				Player player = iterator.next();
				if(!ManUtils.Man(player)){
					removed.add(player);
					iterator.remove();
				}
				if(player.getFoodLevel()>=Values.ManInvisibleHunger*-1){
				SchedulerHunger.hunger(player, Values.ManInvisibleHunger);
				}else{
					
				
				removed.add(player);
				iterator.remove();
				}
			
				
				
			}
			ManUtils.HiddenPlayers.remove(removed);	
			for(Player removedPlayer: removed){
				ManUtils.setVisible(removedPlayer);
			
			}
			
		
		}
		public static void addPlayer (Player player ){
			if(!ManUtils.HiddenPlayers.contains(player)){
				
			
			ManUtils.HiddenPlayers.add(player);
				
			}
		}
	
		
	
}
