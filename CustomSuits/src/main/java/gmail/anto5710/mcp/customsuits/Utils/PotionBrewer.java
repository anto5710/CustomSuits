package gmail.anto5710.mcp.customsuits.Utils;

import java.util.Arrays;

import javax.annotation.Nonnull;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionBrewer {

	public static boolean hasExactPotionEffect(@Nonnull LivingEntity lentity, PotionEffect effect) {
		return lentity.hasPotionEffect(effect.getType()) && lentity.getPotionEffect(effect.getType()).getAmplifier() == effect.getAmplifier();
	}
	
	public static boolean hasSimilarPotionEffect(@Nonnull LivingEntity lentity, PotionEffect effect) {
		return lentity.hasPotionEffect(effect.getType()) && lentity.getPotionEffect(effect.getType()).getAmplifier() <= effect.getAmplifier();
	}

	public static void addPotions(@Nonnull LivingEntity lentity, @Nonnull PotionEffect...effects) {
		Arrays.stream(effects).forEach(effect->addPotion(lentity, effect));
	}
	
	public static void addPotion(@Nonnull LivingEntity lentity, PotionEffect effect){
		if (!hasSimilarPotionEffect(lentity, effect)) {
			lentity.removePotionEffect(effect.getType());
			lentity.addPotionEffect(effect);
		}
	}

	public static void addPotion(@Nonnull LivingEntity livingEntity, PotionEffectType type, int duration, int amplifier) {
		addPotion(livingEntity, new PotionEffect(type, duration, amplifier));
	}
	
	public static void permaPotion(@Nonnull LivingEntity livingEntity, PotionEffectType type, int amplifier) {
		addPotion(livingEntity, new PotionEffect(type, Integer.MAX_VALUE, amplifier));
	}
	
	/**
	 * Remove PotionEffect
	 * @param effect PotionEffect
	 * @param player Player to remove PotionEfffect
	 */
	public static void removePotionEffect(@Nonnull LivingEntity lentity, PotionEffect effect) {
		if(hasExactPotionEffect(lentity, effect)){
			lentity.removePotionEffect(effect.getType());
		}
	}
	
	public static void removePotionEffects(@Nonnull LivingEntity lentity, @Nonnull PotionEffect...effects) {
		Arrays.stream(effects).forEach(effect->removePotionEffect(lentity, effect));
	}

	/**
	 * Remove PotionEffect
	 * @param lentity Player to remove PotionEfffect
	 * @param type PotionEffectType
	 */
	public static void removePotionEffectByType(@Nonnull LivingEntity lentity, PotionEffectType type) {
		if(lentity.hasPotionEffect(type)){
			lentity.removePotionEffect(type);
		}
	}
	
	/**
	 * Remove PotionEffect
	 * @param PotionEffectType PotionEffectType
	 * @param lentity LivingEntity to remove PotionEfffect
	 */
	public static void removePotionEffectsByType(@Nonnull LivingEntity lentity, @Nonnull PotionEffectType...types) {
		Arrays.stream(types).forEach(type->removePotionEffectByType(lentity, type));
	}
	
	public static void zoom(LivingEntity lentity, int magnification){
		addPotion(lentity, PotionEffectType.SLOW, Integer.MAX_VALUE, magnification);
	}
	
	public static void dezoom(LivingEntity lentity){
		removePotionEffectByType(lentity, PotionEffectType.SLOW);
	}
}
