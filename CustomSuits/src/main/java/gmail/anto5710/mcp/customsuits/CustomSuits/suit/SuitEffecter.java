package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import gmail.anto5710.mcp.customsuits.Utils.CustomEffects;
import gmail.anto5710.mcp.customsuits.Utils.PotionBrewer;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.LinearEncompassor;

public class SuitEffecter extends LinearEncompassor<Player>{

	public SuitEffecter(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
		
	}

	@Override
	public boolean toBeRemoved(Player p) {
		return !CustomSuitPlugin.isMarkEntity(p) || p.isDead();
	}

	@Override
	public void particulate(Player player) {
		if (!player.getAllowFlight()) {
			player.setAllowFlight(true);
		}
		addSuitEffects(player);
		playStateEffect(player);
	}

	@Override
	public void remove(Player player) {
		removeSuitEffects(player);
		super.remove(player);
	}
	
	public static void playStateEffect(Player player){
		if(player.isFlying()){
			if(SuitUtils.isUnderWater(player)){
				CustomEffects.playSuit_Move_Under_Water_Effect(player);
			}else{
				CustomEffects.playSuit_Move_Fly_Effect(player);
			}			
		}else{
			if(SuitUtils.isUnderWater(player)){
				CustomEffects.playSuit_Move_Under_Water_Effect(player);
			}else{
				CustomEffects.playSuit_Move_Effect(player);
			}			
		}
	}
	
	public void addSuitEffects(Player player) {
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
	
	public static void removeSuitEffects(Player player) {
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
}
