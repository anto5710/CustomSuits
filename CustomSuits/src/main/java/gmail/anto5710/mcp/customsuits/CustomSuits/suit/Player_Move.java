package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.CustomSuits.PlayEffect;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Player_Move extends BukkitRunnable{
	CustomSuitPlugin plugin;
	static List<Player>list = new ArrayList<>();
	static HashMap<Player, Integer>levels = new HashMap<>();
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
			final Player player = iterator.next();
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
			if(!player.getAllowFlight()){
				player.setAllowFlight(true);
			}
			add_potion_effect(player);
			Display.reloadData(player);
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
		for(Player player:removed){
			Display.clear_Scoreboard(player);
			PlayerEffect.removingeffects(player);
		}
		list.removeAll(removed);
		removed.clear();
	}
	private void add_potion_effect(Player player) {
		int level = CustomSuitPlugin.getLevel(player);
		PlayerEffect.addpotion(new PotionEffect(PotionEffectType.HEALTH_BOOST, 99999999,
				((int) level / 16) + 1), player);
		PlayerEffect.addpotion(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999999,
				1 + level), player);
		PlayerEffect.addpotion(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,
				99999999, 2 + level), player);
		
		PlayerEffect.addpotion(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,
				99999999, 1 + ((int) level / 16)), player);
		PlayerEffect.addpotion(new PotionEffect(PotionEffectType.SPEED, 99999999,
				2 + ((int) level / 32)), player);
		PlayerEffect.addpotion(new PotionEffect(PotionEffectType.WATER_BREATHING,
				99999999, 1), player);
		PlayerEffect.addpotion(new PotionEffect(PotionEffectType.JUMP, 99999999, 2),
				player);
		PlayerEffect.addpotion(new PotionEffect(PotionEffectType.REGENERATION, 99999999,
				1 + (int) level / 16), player);
		
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
