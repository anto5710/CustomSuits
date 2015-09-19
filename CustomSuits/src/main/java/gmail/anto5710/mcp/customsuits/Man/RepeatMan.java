package gmail.anto5710.mcp.customsuits.Man;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.SchedulerHunger;
import gmail.anto5710.mcp.customsuits.Utils.ManUtils;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;


import java.awt.List;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RepeatMan extends BukkitRunnable{
	private CustomSuitPlugin mainPlugin;
	private static Thread thisThread;
	
	
	public RepeatMan(CustomSuitPlugin main) {
		this.mainPlugin = main;
		this.thisThread = new Thread(this,"T-MAN");
	}

	public void startThread() {
		this.thisThread.start();
	}
	
		public void run() {
			
			java.util.List<Player>HidePlayer = ManUtils.HiddenPlayers;
			if(HidePlayer == null){
				try {
					thisThread.sleep(864000L);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				
			}
			Iterator<Player> iterator = HidePlayer.iterator();
			while(iterator.hasNext()){
				Player player = iterator.next();
				player.sendMessage(player+"");
				if(!SchedulerHunger.hunger(player, -1)){
					
					ManUtils.changeVisiblility(player , false ,false);
					
					
				}
				
			}
			try {
				thisThread.sleep(1000L);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			thisThread.interrupt();
		}
		public static void addPlayer (Player player ){
			if(!ManUtils.HiddenPlayers.contains(player)){
				
			
			ManUtils.HiddenPlayers.add(player);
			if(!thisThread.isInterrupted()){
			thisThread.interrupt();
			}
			}
		}
		public static void removePlayer (Player player ){
			if(!ManUtils.HiddenPlayers.contains(player)){
				return;
			}
			ManUtils.HiddenPlayers.remove(player);
		}
		
	
}
