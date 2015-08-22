package gmail.anto5710.mcp.customsuits.CustomSuits;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class SchedulerHunger implements Runnable {
	ScoreboardManager manager = Bukkit.getScoreboardManager();
	Scoreboard scoreboard =manager.getNewScoreboard();
	Objective objective = scoreboard.registerNewObjective("info","dummy");
	
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
		
		objective.setDisplayName(ChatColor.BLUE+"[Info]");
		
		
		for (;;) {
			if (this.playerQueue.isEmpty()) {
				this.mainPlugin.logger.info("EMPTY QUEUQ");
				sleep(86400000L);
			}
			Iterator<Player> itr = getPlayer().iterator();
			this.mainPlugin.logger.info("descreasing player hunger");
			
			while (itr.hasNext()) {
				String warn = "You don't have enough energy to fly.";

				if (playerQueue.size() == 1) {
					Player p = itr.next();

			
					if (p.isFlying()) {
						
					 
						if(Mark(p)) {
							p.setFoodLevel(p.getFoodLevel() - 2);

							float speed = (float) (p.getFoodLevel() / 20.0D);
							if (p.getFoodLevel() <= 2) {
								p.setFlying(false);
								p.setAllowFlight(false);
							} else {
								p.setFlySpeed(speed);
							}
							if (p.getFoodLevel() < 10) {
								p.setFlySpeed(0.5F);
								warn = "FlySpeed decreased";
								p.sendMessage(ChatColor.RED + "[Warn]: " + warn);
							}
							if (p.getFoodLevel() <= 2) {
								p.sendMessage(ChatColor.RED + "[Warn]: " + warn);
								p.setFlying(false);
								p.setAllowFlight(false);
							}
						}
					}
					if (Mark(p)) {
						repairarmor(p);
						
					
						
						Score energy = objective.getScore(ChatColor.AQUA+"Energy");
						Score speed = objective.getScore(ChatColor.AQUA+"FlySpeed");
						Score health = objective.getScore(ChatColor.AQUA+"Health");
						
						energy.setScore(p.getFoodLevel()*5);
						speed.setScore((int) (p.getFlySpeed()*100));
						health.setScore((int)(p.getHealth()));
					    
						
				
						p.setScoreboard(scoreboard);
						p.setFoodLevel(Math.min(p.getFoodLevel() + 1, 20));

					}
					sleep(3000L);
					Thread.interrupted();
				} else if (playerQueue.size() > 1) {
					for (Player p : playerQueue) {

				
						
						if (p.isFlying()) {
							if (Mark(p)) {
								p.setFoodLevel(p.getFoodLevel() - 2);

								float speed = (float) (p.getFoodLevel() / 20.0D);
								if (p.getFoodLevel() <= 2) {
									p.setFlying(false);
									p.setAllowFlight(false);
								} else {
									p.setFlySpeed(speed);
								}
								if (p.getFoodLevel() < 10) {
									p.setFlySpeed(0.5F);
									warn = "FlySpeed decreased";
									p.sendMessage(ChatColor.RED + "[Warn]: "
											+ warn);
								}
								if (p.getFoodLevel() <= 2) {
									p.sendMessage(ChatColor.RED + "[Warn]: "
											+ warn);
									p.setFlying(false);
									p.setAllowFlight(false);
								}
							}
						}
						if (Mark(p)) {
	
							
							repairarmor(p);
							
							Score energy = objective.getScore(ChatColor.AQUA+"Energy");
							Score speed = objective.getScore(ChatColor.AQUA+"FlySpeed");
							Score health = objective.getScore(ChatColor.AQUA+"Health");
							
							energy.setScore(p.getFoodLevel()*5);
							speed.setScore((int) (p.getFlySpeed()*100));
							health.setScore((int)(p.getHealth()));
						
							
					
							
							p.setFoodLevel(Math.min(p.getFoodLevel() + 1, 20));
           
						}
					}
					sleep(3000L);
					Thread.interrupted();
				}
			}
		}
	}

	private boolean Mark(Player p) {
		if(PlayerEffect.Mark(p)){
			return true;
		}
		return false;
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
		
		// TODO Auto-generated method stub

	}

	private void sleep(long msec) {
		try {
			Thread.sleep(msec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private List<Player> getPlayer() {
		ArrayList<Player> players = new ArrayList();
		players.addAll(this.playerQueue);
		return players;
	}

	public void addFlyingPlayer(Player flyingPlayer) {

		if (playerQueue.contains(flyingPlayer) == false) {

			this.thisThread.interrupt();
			this.playerQueue.add(flyingPlayer);
			objective.setDisplaySlot(DisplaySlot.SIDEBAR);	
			

		}
	}

	public void removeflyingplayer(Player spp) {
		// TODO Auto-generated method stub
		if(playerQueue.contains(spp)){
		this.playerQueue.remove(spp);
		}

	}
	public BlockingQueue<Player> getList(){
		return playerQueue;
	}
    public  void returnscoreboard(Player p ){
    	
    }
}
