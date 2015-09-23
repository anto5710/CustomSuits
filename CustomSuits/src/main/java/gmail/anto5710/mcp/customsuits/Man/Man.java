package gmail.anto5710.mcp.customsuits.Man;

import java.util.ArrayList;
import java.util.Random;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.PlayerEffect;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.SchedulerHunger;
import gmail.anto5710.mcp.customsuits.Setting.PotionEffects;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.ManUtils;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerChannelEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Man implements Listener{
	CustomSuitPlugin plugin;
	public Man(CustomSuitPlugin plugin){
		this.plugin = plugin;
		ManUtils manutils = new ManUtils(plugin);
		
	}
	@EventHandler
	public void DamagedManPlayer(EntityDamageEvent event){
		if(event.getCause() == DamageCause.LAVA || event.getCause()==DamageCause.FIRE||event.getCause()==DamageCause.FIRE_TICK||event.getCause() == DamageCause.FALL){
			return;
		}
		Entity Entity = event.getEntity();
		
		if(Entity instanceof Player){
			Player player = (Player) Entity;
			if(ManUtils.Man(player)&&ManUtils.HiddenPlayers.contains(player)){
				
				ManUtils.changeVisiblility(player , true ,false);
				
			}
		}
	}
	@EventHandler
	public void ManCritical(EntityDamageByEntityEvent event){
		Entity damager = event.getDamager();
		Entity entity = event.getEntity();
		if(damager.getType()!=EntityType.PLAYER){
			return;
		}
		Player player = (Player)damager;
		if(ManUtils.Man(player)){
			if(SuitUtils.CheckItem(CustomSuitPlugin.Sword_Man, player.getItemInHand())){
				double addDamage_Random = ManUtils.Random(100);
				
				event.setDamage((1+addDamage_Random/100)*event.getDamage());
				
			}
		}
	}
	@EventHandler
	public void ManBoost(PlayerToggleSneakEvent event){
		
		Player player = event.getPlayer();
		if(!ManUtils.Man(player)||!player.isBlocking()){
			return;
		}
		if(SuitUtils.CheckItem(CustomSuitPlugin.Sword_Man, player.getItemInHand())&&SchedulerHunger.hunger(player, Values.ManBoostHunger)){
			
			PlayerEffect.addpotion(PotionEffects.Man_BOOST_REGENARATION, player);
			PlayerEffect.addpotion(PotionEffects.Man_BOOST_HEALTH_BOOST, player);
			PlayerEffect.addpotion(PotionEffects.Man_BOOST_SPEED, player);
			PlayerEffect.addpotion(PotionEffects.Man_BOOST_JUMP, player);
			PlayerEffect.addpotion(PotionEffects.Man_BOOST_INCREASE_DAMAGE, player);
			
			
			
			
		}
	}

	@EventHandler
	public void ManMoveEffect(PlayerMoveEvent event) throws NullPointerException{
		Player player = event.getPlayer();
		if(!ManUtils.Man(player)){
			return;
		}
		
		if(Boost(player)){
			
				SuitUtils.playEffect(player.getLocation(), Values.ManBoostEffect, 40,0, 5);
			}
		
		else{
			if(ManUtils.HiddenPlayers.contains(player)){
				SuitUtils.playEffect(player.getLocation(), Values.ManInvisibleMoveEffect, 2, 0, 4);
			}else {
			SuitUtils.playEffect(player.getLocation(), Values.ManvisibleMoveEffect, 1, 0, 1);
			}
		}
		
			
	}
	@EventHandler
	public void ManMovePotionEffect(PlayerMoveEvent event){
		Player player = event.getPlayer();
		if(ManUtils.Man(player)){
			if(!Boost(player)){
			if(!ManUtils.HiddenPlayers.contains(player)){
				PlayerEffect.addpotion(PotionEffects.Man_SPEED, player);
			}
			PlayerEffect.addpotion(PotionEffects.Man_FIRE_RESISTANCE, player);
			PlayerEffect.addpotion(PotionEffects.Man_HEALTH_BOOST, player);
			PlayerEffect.addpotion(PotionEffects.Man_INCREASE_DAMAGE, player);
			PlayerEffect.addpotion(PotionEffects.Man_JUMP, player);
			PlayerEffect.addpotion(PotionEffects.Man_WATER_BREATHING, player);
			PlayerEffect.addpotion(PotionEffects.Man_REGENARATION, player);
			}
		}else if(!CustomSuitPlugin.hasAbillity(player)){
			removeManEffect(player);
		}
	}
	private void removeManEffect(Player player) {
		ThorUtils.removePotionEffect(PotionEffects.Man_FIRE_RESISTANCE, player);
		ThorUtils.removePotionEffect(PotionEffects.Man_HEALTH_BOOST, player);
		ThorUtils.removePotionEffect(PotionEffects.Man_INCREASE_DAMAGE, player);
		ThorUtils.removePotionEffect(PotionEffects.Man_Invisiblility, player);
		
	
		
		
		ThorUtils.removePotionEffect(PotionEffects.Man_JUMP, player);
		ThorUtils.removePotionEffect(PotionEffects.Man_SPEED, player);
		ThorUtils.removePotionEffect(PotionEffects.Man_WATER_BREATHING, player);
		ThorUtils.removePotionEffect(PotionEffects.Man_WATER_BREATHING, player);
		ThorUtils.removePotionEffect(PotionEffects.Man_REGENARATION, player);
		
	}
	public static boolean Boost(Player player) {
		if(PlayerEffect.ContainPotionEffect(player, PotionEffects.Man_BOOST_HEALTH_BOOST.getType(), PotionEffects.Man_BOOST_HEALTH_BOOST.getAmplifier())&&
				PlayerEffect.ContainPotionEffect(player, PotionEffects.Man_BOOST_JUMP.getType(), PotionEffects.Man_BOOST_JUMP.getAmplifier())&&
				PlayerEffect.ContainPotionEffect(player, PotionEffects.Man_BOOST_SPEED.getType(), PotionEffects.Man_BOOST_SPEED.getAmplifier())&&
				PlayerEffect.ContainPotionEffect(player, PotionEffects.Man_BOOST_REGENARATION.getType(), PotionEffects.Man_BOOST_REGENARATION.getAmplifier())&&
				PlayerEffect.ContainPotionEffect(player, PotionEffects.Man_BOOST_INCREASE_DAMAGE.getType(), PotionEffects.Man_BOOST_INCREASE_DAMAGE.getAmplifier()))
			
				{
			return true;
				}
		return false;
	}
	@EventHandler
	public void SetInvisible(PlayerInteractEvent event){
		if(event.getAction()!=Action.LEFT_CLICK_AIR&&event.getAction()!=Action.LEFT_CLICK_BLOCK){
			return;
		}
		
		Player player = event.getPlayer();
		if(!ManUtils.Man(player)||!player.isSneaking()){
			return ;
		}
		
			
		ManUtils.changeVisiblility(player , true , true);
			
		return;
		
	
	}
	
}
