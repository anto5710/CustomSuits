package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.CustomSuits.SuitUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class SchedulerHunger extends BukkitRunnable {
	
	
	
	private CustomSuitPlugin mainPlugin;
	private Thread thisThread;
	private BlockingQueue<Player> playerQueue = new ArrayBlockingQueue(10);
	
	public SchedulerHunger(CustomSuitPlugin main) {
		this.mainPlugin = main;
		this.thisThread = new Thread(this, "T-HUNGER");
	}

	public void startThread() {
		this.thisThread.start();
	}

	public void run() {
		
	
		
		
		
			if (this.playerQueue.isEmpty()) {
				this.mainPlugin.logger.info("EMPTY QUEUQ");
				
			
			}else{
			Iterator<Player> itr = getPlayer().iterator();
			
			
			while (itr.hasNext()) {
				

				if (playerQueue.size() == 1) {
					Player p = itr.next();
					EffectRun(p);
				} else if (playerQueue.size() > 1) {
					for (Player p : playerQueue) {
						EffectRun(p);
					}
					
				}
			}
			}
		
	}
	public void EffectRun(Player player) {
		
	

		if (player.isFlying()) {

			if (CustomSuitPlugin.MarkEntity(player)&&player.getGameMode()!=GameMode.CREATIVE) {
				player.setFoodLevel(player.getFoodLevel() - 2);

				float speed = (float) (player.getFoodLevel() / 20.0D);
				if (player.getFoodLevel() <= 2) {
					
					player.setFlying(false);
					player.setAllowFlight(false);
				} else {
					player.setFlySpeed(speed);
				}
				if (player.getFoodLevel() < 10) {
					
					
					SuitUtils.Wrong(player, "Fly Energy");
				}
				if (player.getFoodLevel() <= 2) {
					SuitUtils.Wrong(player, "Fly Energy");
					
					player.setFlying(false);
					player.setAllowFlight(false);
				}
			}
		}
		if (CustomSuitPlugin.MarkEntity(player)) {
			repairarmor(player);
		
			player.setFoodLevel(Math.min(player.getFoodLevel() + 1, 20));
			
		}
		
		
	}
	

	private void repairarmor(Player p) {
		if (p.getEquipment().getHelmet() != null) {

			short h = p.getEquipment().getHelmet().getDurability();
			if(h!=0){
			p.getEquipment().getHelmet().setDurability((short) (h - 1));
			}
			
		}
		if (p.getEquipment().getChestplate() != null) {
			short c = p.getEquipment().getChestplate().getDurability();
			if(c!=0){
			p.getEquipment().getChestplate().setDurability((short) (c - 1));
			}

		}
		if (p.getEquipment().getLeggings() != null) {

			short l = p.getEquipment().getLeggings().getDurability();
			if(l!=0){
			p.getEquipment().getLeggings().setDurability((short) (l - 1));
			}
		}
		if (p.getEquipment().getBoots() != null) {
			short b = p.getEquipment().getBoots().getDurability();
			if(b!=0){
			p.getEquipment().getBoots().setDurability((short) (b - 1));
			}

		}
		
		

	}

	

	private List<Player> getPlayer() {
		ArrayList<Player> players = new ArrayList();
		players.addAll(this.playerQueue);
		return players;
	}

	public void addFlyingPlayer(Player flyingPlayer) {

		if (playerQueue.contains(flyingPlayer) == false) {

			this.runTaskTimer(mainPlugin, 0, 60);
			this.playerQueue.add(flyingPlayer);
			
			

		}
	}

	public void removeflyingplayer(Player spp) {
		
		if(playerQueue.contains(spp)){
		this.playerQueue.remove(spp);
		}

	}
	public BlockingQueue<Player> getList(){
		return playerQueue;
	}
  
}
