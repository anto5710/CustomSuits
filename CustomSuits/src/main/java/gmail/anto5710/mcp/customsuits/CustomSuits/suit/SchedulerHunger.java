package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.CustomSuits.dao.SpawningDao;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.server.v1_8_R2.Items;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class SchedulerHunger extends BukkitRunnable {
	
	
	
	private CustomSuitPlugin mainPlugin;
	boolean isRunning = false;
	SpawningDao dao;
	public static  List<Player> playerQueue = new ArrayList<>();
	public static ArrayList<Player>removedPlayer = new ArrayList<>();
	float maxFly_Speed = 0.75F;
	long count = 0;
	
	public SchedulerHunger(CustomSuitPlugin main) {
		this.mainPlugin = main;
	}

	@Override
	public void run()  {
	try {
		
		
	} catch (IllegalStateException e) {
		
	}
		
			if (SchedulerHunger.playerQueue.isEmpty()) {
				this.mainPlugin.logger.info("EMPTY QUEUQ");
				try {
					
					isRunning = false;
					cancel();
				} catch (IllegalStateException e) {
				}
			
			}
				isRunning = true;
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
							if(count%25==0){
							hunger(player, Values.SuitHungerRelod);
							repairarmor(player);
							}
						}
					}
				}
				count ++;
				playerQueue.removeAll(removedPlayer);
				removedPlayer.clear();
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
	private void repairarmor(Player player) {
		ItemStack[]armor = player.getEquipment().getArmorContents();
		addDurability(armor[0], (short)1);
		addDurability(armor[1], (short)1);
		addDurability(armor[2], (short)1);
		addDurability(armor[3], (short)1);
		
		

	}
	public static void addDurability(ItemStack itemStack , short addDurability){
		if(itemStack.getType() == Material.AIR){
			return;
		}
		short durability = itemStack.getDurability();
		short max_durability = itemStack.getType().getMaxDurability();
		short final_durability =0;
		
		if((durability-addDurability)>max_durability){
			final_durability = max_durability;
		}else{
			final_durability = (short) (durability-addDurability);
		}
		if(final_durability<0){
			final_durability = 0;
		}
		itemStack.setDurability(final_durability);
		
	}
	

	private List<Player> getPlayer() {
		return playerQueue;
	}

	public void addFlyingPlayer(Player flyingPlayer) throws IllegalStateException{

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
					new SchedulerHunger(mainPlugin).runTaskTimer(mainPlugin, 0,1);
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
