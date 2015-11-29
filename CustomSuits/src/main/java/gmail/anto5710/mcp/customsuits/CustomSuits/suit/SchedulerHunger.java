package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.CustomSuits.dao.SpawningDao;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SchedulerHunger extends BukkitRunnable {
	
	
	
	private CustomSuitPlugin mainPlugin;
	boolean isRunning = false;
	SpawningDao dao;
	private Thread thisThread;
	public static  List<Player> playerQueue = new ArrayList<>();
	public static ArrayList<Player>removedPlayer = new ArrayList<>();
	float maxFly_Speed = 0.75F;
	long count = 0;
	
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
		
			if (SchedulerHunger.playerQueue.isEmpty()) {
				this.mainPlugin.logger.info("EMPTY QUEUQ");
				
				ThorUtils.cancel(taskID);
				
			
			}else{
			Iterator<Player> itrerator = getPlayer().iterator();
			
			
				while (itrerator.hasNext()) {
					Player player = itrerator.next();
					if(getRemovePlayer(player)){
						
						removedPlayer.add(player);
						itrerator.remove();
					}else{
						if(player.isFlying()){
							if(!has_hunger(player, Values.leastFlyHunger)){
								player.setFlySpeed((float) 0.5);
								player.setFlying(false);
							}else{
								if(count%60==0){
									hunger(player, Values.SuitFlyHunger);
								}
								if(count%100==0){
									if(!has_hunger(player, Values.SuitEnoughFly)){
										SuitUtils.Warn(player, Values.FlyEnergyWarn);
									}
								}
								
							}
							
						}else{
							hunger(player, Values.SuitHungerRelod);
						}
					}
				}
				playerQueue.removeAll(removedPlayer);
				removedPlayer.clear();
			}
	}
	
	private boolean getRemovePlayer(Player player) {
		if(!player.isOnline()){
			return true;
		}
		if(player.isDead()){
			return true;
		}
		if(!CustomSuitPlugin.MarkEntity(player)){
			return true;
		}
		return false;
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
		return playerQueue;
	}

	public void addFlyingPlayer(Player flyingPlayer) throws IllegalStateException{
		flyingPlayer.sendMessage(playerQueue+"");

		if (playerQueue.contains(flyingPlayer)) {
			return;
		}
		if(flyingPlayer.isDead()){
			return;
		}
		if(!flyingPlayer.isOnline()){
			return;
		}
			if(!isRunning){
					thisThread.interrupt();
					this.runTaskTimer(mainPlugin, 0,1);
					if(!Target.isRunning){
					new Target(mainPlugin).runTaskTimer(mainPlugin, 0, 1);
					}
			}
			SchedulerHunger.playerQueue.add(flyingPlayer);
			
			

		
	}

	public List<Player> getList(){
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
	
	private boolean has_hunger(Player player,int hunger) {
		
		return player.getFoodLevel()>=hunger;
	}
	
	
	

}
