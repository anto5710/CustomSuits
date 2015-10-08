package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;
import gmail.anto5710.mcp.customsuits._Thor.Repeat;

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
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.entity.Flying;
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
	private static   BlockingQueue<Player> playerQueue = new ArrayBlockingQueue(Bukkit.getServer().getMaxPlayers());
	float maxFly_Speed = 0.75F;
	
	public SchedulerHunger(CustomSuitPlugin main) {
		this.mainPlugin = main;
		this.thisThread = new Thread(this, "T-HUNGER");
	}

	public void startThread() {
		this.thisThread.start();
	}

	public void run() throws IllegalStateException {
		int taskID = 0;
	try {
		
		 taskID = this.getTaskId();
		
	} catch (IllegalStateException e) {
		
	}
		
			if (this.playerQueue.isEmpty()) {
				this.mainPlugin.logger.info("EMPTY QUEUQ");
				
				ThorUtils.cancel(taskID);
				
			
			}else{
			Iterator<Player> itrerator = getPlayer().iterator();
			
			
			while (itrerator.hasNext()) {
				

				if (playerQueue.size() == 1) {
					Player player = itrerator.next();
					EffectRun(player);
				} else if (playerQueue.size() > 1) {
					for (Player player : playerQueue) {
						EffectRun(player);
					}
					
				}
			}
			}
		
	}
	

	public void EffectRun(Player player) {
		
	

		if (player.isFlying()) {

			if (CustomSuitPlugin.MarkEntity(player)&&player.getGameMode()!=GameMode.CREATIVE) {
				
			
				float speed = (float) ((player.getFoodLevel() / 20.0D)-(1-maxFly_Speed));
				if (!hunger(player, -Values.SuitFlyDisableWhen)) {
					
					player.setFlying(false);
					player.setAllowFlight(false);
				} else {
					player.setFlySpeed(speed);
				}
				if (player.getFoodLevel() < Values.SuitEnoughFly) {
					
					
					SuitUtils.Warn(player, Values.FlyEnergyWarn);
				}
				if (player.getFoodLevel() <= Values.SuitFlyDisableWhen) {
					SuitUtils.Wrong(player, "Fly Energy");
					
					player.setFlying(false);
					player.setAllowFlight(false);
				}
			}
		}
		if (CustomSuitPlugin.MarkEntity(player)) {
			repairarmor(player);
		
			hunger(player, Values.SuitHungerRelod);
			
		}
		
		
	}
	
	public static boolean containPlayer(Player player){
		return playerQueue.contains(player);
	}
	private void repairarmor(Player p) {
		if (p.getEquipment().getHelmet() != null) {

			short HelemtDurability = p.getEquipment().getHelmet().getDurability();
			if(HelemtDurability!=0){
			p.getEquipment().getHelmet().setDurability((short) (HelemtDurability - 1));
			}
			
		}
		if (p.getEquipment().getChestplate() != null) {
			short ChestplateDurability = p.getEquipment().getChestplate().getDurability();
			if(ChestplateDurability!=0){
			p.getEquipment().getChestplate().setDurability((short) (ChestplateDurability - 1));
			}

		}
		if (p.getEquipment().getLeggings() != null) {

			short leggingsDurability = p.getEquipment().getLeggings().getDurability();
			if(leggingsDurability!=0){
			p.getEquipment().getLeggings().setDurability((short) (leggingsDurability - 1));
			}
		}
		if (p.getEquipment().getBoots() != null) {
			short BootsDurability = p.getEquipment().getBoots().getDurability();
			if(BootsDurability!=0){
			p.getEquipment().getBoots().setDurability((short) (BootsDurability - 1));
			}

		}
		
		

	}

	

	private List<Player> getPlayer() {
		ArrayList<Player> players = new ArrayList();
		players.addAll(this.playerQueue);
		return players;
	}

	public void addFlyingPlayer(Player flyingPlayer) throws IllegalStateException{

		if (playerQueue.contains(flyingPlayer) == false) {
			
			if(playerQueue.size()==0){
				try {
					this.runTaskTimer(mainPlugin, 0, Values.SuitHungerDelay);
					
				} catch (IllegalStateException e) {
					
				}
			}
			this.playerQueue.add(flyingPlayer);
			
			

		}
	}

	public void removeflyingplayer(Player player) {
		
		if(playerQueue.contains(player)){
		this.playerQueue.remove(player);
		}

	}
	public BlockingQueue<Player> getList(){
		return playerQueue;
	}
	public static boolean hunger(Player player , int hunger){
		int playerhunger = player.getFoodLevel();
		int result = playerhunger+hunger;
		if(result>=0&&result<=20){
			player.setFoodLevel(result);
			return true;
		}
		return false;
	}
  
}
