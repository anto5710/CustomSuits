package gmail.anto5710.mcp.customsuits.Utils;

import java.util.Arrays;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionBrewer {

	public static boolean containSamePotionEffect(LivingEntity livingEntity, PotionEffectType type, int level) {
		for (PotionEffect effect : livingEntity.getActivePotionEffects()) {
			if (effect.getType().equals(type) && effect.getAmplifier() == level) {
				return true;
			}
		}
		return false;
	}

	public static void addPotions(LivingEntity lentity, PotionEffect...effects) {
		Arrays.stream(effects).forEach(effect->addPotion(lentity, effect));
	}
	
	public static void addPotion(LivingEntity lentity, PotionEffect effect){
		if (!containSamePotionEffect(lentity, effect.getType(), effect.getAmplifier())) {
			lentity.removePotionEffect(effect.getType());
		}
		lentity.addPotionEffect(effect);
	}

	public static void addPotion(LivingEntity livingEntity, PotionEffectType type, int duration, int amplifier) {
		addPotions(livingEntity, new PotionEffect(type, duration, amplifier));
	}
	
	/**
	 * Remove PotionEffect
	 * @param PotionEffect PotionEffect
	 * @param player Player to remove PotionEfffect
	 */
	public static void removePotionEffect(LivingEntity lentity, PotionEffect PotionEffect) {
		if(containSamePotionEffect(lentity, PotionEffect.getType(), PotionEffect.getAmplifier())){
			lentity.removePotionEffect(PotionEffect.getType());
		}
	}
	
	public static void removePotionEffects(LivingEntity lentity, PotionEffect...PotionEffects) {
		Arrays.stream(PotionEffects).forEach(effect->removePotionEffect(lentity, effect));
	}

	/**
	 * Remove PotionEffect
	 * @param PotionEffectType PotionEffectType
	 * @param player Player to remove PotionEfffect
	 */
	public static void removePotionEffectByType(PotionEffectType PotionEffectType, Player player) {
		if(player.hasPotionEffect(PotionEffectType)){
			player.removePotionEffect(PotionEffectType);
		}
	}
}
