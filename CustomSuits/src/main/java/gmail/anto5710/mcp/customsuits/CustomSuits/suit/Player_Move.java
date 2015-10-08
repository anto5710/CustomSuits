package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.CustomSuits.PlayEffect;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Player_Move extends BukkitRunnable{
	CustomSuitPlugin plugin;
	static List<Player>list = new ArrayList<>();
	static List<Player>removed = new ArrayList<>();
	public Player_Move(CustomSuitPlugin plugin){
		this.plugin = plugin;
	}
	@Override
	public void run() throws IllegalStateException{
		Iterator<Player>iterator = list.iterator();
		if(list.isEmpty()){
			try {
				ThorUtils.cancel(getTaskId());
				
			} catch (IllegalStateException e) {
			}
		}
		while(iterator.hasNext()){
			Player player = iterator.next();
			if(removed.contains(player)){
				iterator.remove();
			}
			if(!CustomSuitPlugin.MarkEntity(player)){
				removed.add(player);
				iterator.remove();
			}
			if(player.isDead()){
				removed.add(player);
				iterator.remove();
			}
			if(player.isFlying()){
				if(isUnder_Water(player)){
					PlayEffect.playSuit_Move_Under_Water_Effect(player.getLocation(), player);
				}else{
			
					PlayEffect.playSuit_Move_Fly_Effect(player.getLocation(), player);
			
				}
				
			}else{
				if(isUnder_Water(player)){
					PlayEffect.playSuit_Move_Under_Water_Effect(player.getLocation(), player);
				}else{
					PlayEffect.playSuit_Move_Effect(player.getLocation(), player);
				}
				
			}
		}

		list.removeAll(removed);
		removed.clear();
	}
	public static boolean isUnder_Water(Player player){
		Material checkEye = player.getEyeLocation().getBlock().getType();
		Material checkUnder = player.getLocation().getBlock().getType();
		Material checkMiddle = player.getLocation().add(0, 1, 0).getBlock().getType();
		if((checkEye == Material.WATER|| checkEye == Material.STATIONARY_WATER)&& (checkUnder == Material.WATER|| checkUnder==Material.STATIONARY_WATER)&&
				(checkMiddle == Material.WATER|| checkMiddle==Material.STATIONARY_WATER)){
			return true;
		}
		return false;
	}
}
