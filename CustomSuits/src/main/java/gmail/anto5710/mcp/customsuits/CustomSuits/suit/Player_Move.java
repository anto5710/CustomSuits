package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.Utils.CustomEffects;
import gmail.anto5710.mcp.customsuits.Utils.PotionBrewer;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
			if(!CustomSuitPlugin.isMarkEntity(player) || player.isDead()){
				removed.add(player);
				iterator.remove();
			}
			if(!player.getAllowFlight()){
				player.setAllowFlight(true);
			}
			addPotionEffects(player);
			playStateEffect(player);
		}
		for(Player player:removed){
			Player_Move.removeEffects(player);
		}
		list.removeAll(removed);
		removed.clear();
	}
	
	private void playStateEffect(Player player){
		if(player.isFlying()){
			if(isUnder_Water(player)){
				CustomEffects.playSuit_Move_Under_Water_Effect(player);
			}else{
				CustomEffects.playSuit_Move_Fly_Effect(player);
			}			
		}else{
			if(isUnder_Water(player)){
				CustomEffects.playSuit_Move_Under_Water_Effect(player);
			}else{
				CustomEffects.playSuit_Move_Effect(player);
			}			
		}
	}
	
	private void addPotionEffects(Player player) {
		int level = CustomSuitPlugin.getSuitLevel(player);
		PotionBrewer.addPotion(player, PotionEffectType.HEALTH_BOOST, 99999999, (int)(level/16D) + 1);
		PotionBrewer.addPotion(player, PotionEffectType.NIGHT_VISION, 99999999, 1 + level);
		PotionBrewer.addPotion(player, PotionEffectType.FIRE_RESISTANCE, 99999999, 2 + level);
		PotionBrewer.addPotion(player, PotionEffectType.INCREASE_DAMAGE, 99999999, (int)(level/16D) + 1);
		PotionBrewer.addPotion(player, PotionEffectType.SPEED, 99999999, (int)(level/32D) + 2);
		PotionBrewer.addPotion(player, PotionEffectType.WATER_BREATHING, 99999999, 1);
		PotionBrewer.addPotion(player, PotionEffectType.JUMP, 99999999, 2);
		PotionBrewer.addPotion(player, PotionEffectType.REGENERATION, 99999999, (int)(level/16D) + 1);
	}
	
	public static void removeEffects(Player player) {
		player.setFlying(false);
		player.setAllowFlight(player.getGameMode()==GameMode.CREATIVE);
		player.setFlySpeed(0.5F);
		
		player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
		player.removePotionEffect(PotionEffectType.ABSORPTION);
		player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
		player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
		player.removePotionEffect(PotionEffectType.JUMP);
		player.removePotionEffect(PotionEffectType.SPEED);
		player.removePotionEffect(PotionEffectType.REGENERATION);
		player.removePotionEffect(PotionEffectType.WATER_BREATHING);
		player.removePotionEffect(PotionEffectType.NIGHT_VISION);
		player.removePotionEffect(PotionEffectType.SLOW);
	}

	public static boolean isUnder_Water(Player player){	
		Material checkEye = player.getEyeLocation().getBlock().getType();
		Material checkUnder = player.getLocation().getBlock().getType();
		Material checkMiddle = player.getLocation().add(0, 1, 0).getBlock().getType();
		
		return isWater(checkEye) && isWater(checkMiddle) && isWater(checkUnder);
	}
	
	private static boolean isWater(Material m){
		return m!=null && (m==Material.WATER || m==Material.LEGACY_STATIONARY_WATER);
	}
}
