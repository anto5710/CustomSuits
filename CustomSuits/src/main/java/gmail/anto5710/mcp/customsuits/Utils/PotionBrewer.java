package gmail.anto5710.mcp.customsuits.Utils;

import java.util.Arrays;

import javax.annotation.Nonnull;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionBrewer {

	public static boolean containSamePotionEffect(@Nonnull LivingEntity livingEntity, PotionEffectType type, int level) {
		for (PotionEffect effect : livingEntity.getActivePotionEffects()) {
			if (effect.getType().equals(type) && effect.getAmplifier() == level) {
				return true;
			}
		}
		return false;
	}

	public static void addPotions(@Nonnull LivingEntity lentity, @Nonnull PotionEffect...effects) {
		Arrays.stream(effects).forEach(effect->addPotion(lentity, effect));
	}
	
	public static void addPotion(@Nonnull LivingEntity lentity, PotionEffect effect){
		if (!containSamePotionEffect(lentity, effect.getType(), effect.getAmplifier())) {
			lentity.removePotionEffect(effect.getType());
		}
		lentity.addPotionEffect(effect);
	}

	public static void addPotion(@Nonnull LivingEntity livingEntity, PotionEffectType type, int duration, int amplifier) {
		addPotion(livingEntity, new PotionEffect(type, duration, amplifier));
	}
	
	/**
	 * Remove PotionEffect
	 * @param PotionEffect PotionEffect
	 * @param player Player to remove PotionEfffect
	 */
	public static void removePotionEffect(@Nonnull LivingEntity lentity, PotionEffect PotionEffect) {
		if(containSamePotionEffect(lentity, PotionEffect.getType(), PotionEffect.getAmplifier())){
			lentity.removePotionEffect(PotionEffect.getType());
		}
	}
	
	public static void removePotionEffects(@Nonnull LivingEntity lentity, @Nonnull PotionEffect...PotionEffects) {
		Arrays.stream(PotionEffects).forEach(effect->removePotionEffect(lentity, effect));
	}

	/**
	 * Remove PotionEffect
	 * @param lentity Player to remove PotionEfffect
	 * @param PotionEffectType PotionEffectType
	 */
	public static void removePotionEffectByType(@Nonnull LivingEntity lentity, PotionEffectType PotionEffectType) {
		if(lentity.hasPotionEffect(PotionEffectType)){
			lentity.removePotionEffect(PotionEffectType);
		}
	}
	
	/**
	 * Remove PotionEffect
	 * @param PotionEffectType PotionEffectType
	 * @param lentity LivingEntity to remove PotionEfffect
	 */
	public static void removePotionEffecstByType(@Nonnull LivingEntity lentity, @Nonnull PotionEffectType...PotionEffectTypes) {
		Arrays.stream(PotionEffectTypes).forEach(type->removePotionEffectByType(lentity, type));
	}
}
