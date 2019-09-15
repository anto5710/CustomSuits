package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.weapons.SuitWeapons;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.weapons.repulsor.ArcCompressor;
import gmail.anto5710.mcp.customsuits.Utils.CustomEffects;
import gmail.anto5710.mcp.customsuits.Utils.InventoryUtil;
import gmail.anto5710.mcp.customsuits.Utils.PotionBrewer;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.LinearEncompassor;

public class SuitEffecter extends LinearEncompassor<Player>{

	public SuitEffecter(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
	}

	@Override
	public boolean toRemove(Player p) {
		return !CustomSuitPlugin.isMarkEntity(p) || p.isDead();
	}

	@Override
	public void particulate(Player player) {
		if (!player.getAllowFlight()) {
			player.setAllowFlight(true);
		}
		playStateEffect(player);
		if(t%10==0) addSuitEffects(player);
		
		tick();
	}

	@Override
	public void remove(Player player) {
		removeSuitEffects(player);
		super.remove(player);
	}
	
	public static void playStateEffect(Player player){
		if (SuitUtils.inWater(player)) {
			CustomEffects.playSuit_Move_Under_Water_Effect(player);
			
		} else if (player.isFlying()) {
			CustomEffects.playSuit_Move_Fly_Effect(player);
			
		} else if (player.isGliding()) {
			CustomEffects.playSuit_Glide_Effect(player);
			
		} else {
			CustomEffects.playSuit_Move_Effect(player);
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
		equipRepulsor(player);
	}
	
	private static void equipRepulsor(Player player){
		if(!player.getInventory().contains(ArcCompressor.bow) && !player.getInventory().contains(ArcCompressor.star)) {
			InventoryUtil.give(player, ArcCompressor.star);
			SuitUtils.playSound(player, Sound.BLOCK_LEVER_CLICK, 4F, 14F);
			SuitUtils.playSound(player, Sound.BLOCK_METAL_PLACE, 4F, 14F);
			SuitUtils.runAfter(()->SuitUtils.playSound(player, Sound.BLOCK_STONE_BUTTON_CLICK_OFF, 4F, 14F), 4);
			SuitUtils.runAfter(()->SuitUtils.playSound(player, Sound.BLOCK_STONE_BUTTON_CLICK_OFF, 4F, 14F), 7);
		}
		InventoryUtil.replete(player, Material.ARROW);
	}
	
	public static void removeSuitEffects(Player player) {
		player.setFlying(false);
		player.setAllowFlight(player.getGameMode()==GameMode.CREATIVE);
		player.setFlySpeed(0.5F);
		
		PotionBrewer.removePotionEffectsByType(player, 
			PotionEffectType.FIRE_RESISTANCE,
			PotionEffectType.ABSORPTION,
			PotionEffectType.HEALTH_BOOST,
			PotionEffectType.INCREASE_DAMAGE,
			PotionEffectType.JUMP,
			PotionEffectType.SPEED,
			PotionEffectType.REGENERATION,
			PotionEffectType.WATER_BREATHING,
			PotionEffectType.NIGHT_VISION,
			PotionEffectType.SLOW);
		InventoryUtil.removeAll(player, ArcCompressor.star);
		InventoryUtil.removeAll(player, ArcCompressor.bow);
		SuitWeapons.compressor.disclose(player);
	}
}
