package gmail.anto5710.mcp.customsuits.Setting;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionEffects {
	final public static PotionEffect
		Thor_HEALTH_BOOST= new PotionEffect(PotionEffectType.HEALTH_BOOST, 99999999, 20),
		Thor_INCREASE_DAMAGE =new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999999, 20),
		Thor_SPEED =new PotionEffect(PotionEffectType.SPEED, 99999999, 2),
		Thor_JUMP =new PotionEffect(PotionEffectType.JUMP, 99999999, 3),
		Thor_WATER_BREATHING =new PotionEffect(PotionEffectType.WATER_BREATHING, 99999999, 2),
		Thor_FAST_DIGGING=new PotionEffect(PotionEffectType.FAST_DIGGING, 99999999, 10),
		Thor_FIRE_RESISTANCE =new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 99999999, 10),
		Thor_REGENERATION =new PotionEffect(PotionEffectType.REGENERATION, 99999999, 15),
		Thor_ANTIDAMAGE =new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999999, 30),
	
		Man_Invisiblility = new PotionEffect(PotionEffectType.INVISIBILITY , 999999999 , 30),
		Man_Invisible_SPEED = new PotionEffect(PotionEffectType.SPEED , 999999999 , 14),
		Man_HEALTH_BOOST = new PotionEffect(PotionEffectType.HEALTH_BOOST ,999999999, 5),
		Man_FIRE_RESISTANCE = new PotionEffect(PotionEffectType.FIRE_RESISTANCE ,999999999, 5),
		Man_WATER_BREATHING = new PotionEffect(PotionEffectType.WATER_BREATHING ,999999999, 5),
		Man_INCREASE_DAMAGE = new PotionEffect(PotionEffectType.INCREASE_DAMAGE ,999999999, 15),
		Man_JUMP = new PotionEffect(PotionEffectType.JUMP ,999999999, 5),
		Man_SPEED = new PotionEffect(PotionEffectType.SPEED ,999999999, 5),
		Man_REGENARATION = new PotionEffect(PotionEffectType.REGENERATION ,999999999, 5);
	
	final public static int Man_Boost_Tick = 400;
	final public static PotionEffect Man_BOOST_REGENARATION = new PotionEffect(PotionEffectType.REGENERATION ,Man_Boost_Tick, 25),
		Man_BOOST_SPEED = new PotionEffect(PotionEffectType.SPEED ,Man_Boost_Tick, 40),
		Man_BOOST_JUMP = new PotionEffect(PotionEffectType.JUMP ,Man_Boost_Tick, 8),
		Man_BOOST_HEALTH_BOOST = new PotionEffect(PotionEffectType.HEALTH_BOOST ,Man_Boost_Tick,30),
		Man_BOOST_INCREASE_DAMAGE = new PotionEffect(PotionEffectType.INCREASE_DAMAGE ,Man_Boost_Tick, 15);
	
}
