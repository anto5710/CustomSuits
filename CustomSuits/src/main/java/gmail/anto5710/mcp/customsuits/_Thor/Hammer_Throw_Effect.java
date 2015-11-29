package gmail.anto5710.mcp.customsuits._Thor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import gmail.anto5710.mcp.customsuits.CustomSuits.PlayEffect;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.WeaponListner;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Hammer_Throw_Effect extends BukkitRunnable {


	static boolean isRunning = false;
	public static HashMap<Item, Player> listPlayer = new HashMap<>();
	public static ArrayList<Item>removed = new ArrayList<>();
	
	public Hammer_Throw_Effect() {
	}
	
	@Override
	public void run() throws IllegalStateException{
		Location loc;
		isRunning = true;
		
			Iterator<Item>iterator = listPlayer.keySet().iterator();
			if(listPlayer.isEmpty()){
				try{
					isRunning = false;
					ThorUtils.cancel(getTaskId());
				}catch(IllegalStateException e){
					
				}
			}
			while (iterator.hasNext()) {
				Item item = iterator.next();
				loc = item.getLocation();
				Player player = listPlayer.get(item);
				
				item.setFireTicks(0);

				item.setPickupDelay(1);
				java.util.List<Entity> list;
				
				SuitUtils.playEffect(loc, Values.HammerDefaultEffect, 55, 0, 4);
				list = WeaponListner.findEntity(loc, player, 1);
		
				ThorUtils.damage(list, Hammer.HammerDeafultDamage, player);
				
				if (ThorUtils.isOnGround(item)|| item.isDead()) {
				
					PlayEffect.play_Hammer_Hit_Ground(item);
					item.getWorld().strikeLightning(item.getLocation());
					player.getInventory().addItem(item.getItemStack());
					item.remove();

					SuitUtils.createExplosion(loc, 6F, false, true);
					removed.add(item);					
					iterator.remove();				
			}
		}
			for(Item item: removed){
				listPlayer.remove(item);
			}
	}

	/**
	 * Return is this Task currently running
	 * @return is this Task currently running
	 */
	public static boolean isRunning(){
		return isRunning;
		
	}
	
}
