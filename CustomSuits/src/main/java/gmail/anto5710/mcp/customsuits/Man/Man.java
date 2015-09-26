package gmail.anto5710.mcp.customsuits.Man;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.PlayerEffect;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.SchedulerHunger;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.WeaponListner;
import gmail.anto5710.mcp.customsuits.Setting.PotionEffects;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.ManUtils;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;
import gmail.anto5710.mcp.customsuits.Utils.WeaponUtils;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerChannelEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Bed;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class Man implements Listener{
	CustomSuitPlugin plugin;
	HashMap<Player, Boolean>HittingGround = new HashMap<>();
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
	public void ShotSword(PlayerInteractEvent event){
		Player player = event.getPlayer();
		if(event.getAction()!=Action.RIGHT_CLICK_AIR&&event.getAction()!=Action.RIGHT_CLICK_BLOCK){
			return;
		}
		if(ManUtils.Man(player)){
			
			if(!SuitUtils.CheckItem(CustomSuitPlugin.Sword_Man, player.getItemInHand())){
				return;
			}
			if(!SchedulerHunger.hunger(player, Values.ManSwordShotHunger)){
				return;
			}
			shot(player);
		}
	}
	@EventHandler
	public void removeBomb(PlayerPickupItemEvent event){
		Item item = event.getItem();
		Bomb.remove(item);
	}
	@EventHandler
	public void throwBomb(PlayerInteractEvent event){
		Player player = event.getPlayer();
		if(
				SuitUtils.CheckItem(CustomSuitPlugin.Smoke, player.getItemInHand())||
				SuitUtils.CheckItem(CustomSuitPlugin.Bomb, player.getItemInHand())){
			ItemStack item = player.getItemInHand().clone();
			item.setAmount(1);
			Item dropped = player.getWorld().dropItem(player.getLocation().add(0, 2, 0), item);
			
			dropped.setFallDistance(0);

			Location TargetLocation = SuitUtils.getTargetBlock(player,
					500).getLocation();
			Location loc = dropped.getLocation();
			

			double gravity = 0.0165959600149011612D;
			dropped.teleport(loc);
			org.bukkit.util.Vector v = SuitUtils.calculateVelocity(
					loc.toVector(), TargetLocation.toVector(), gravity,
					6);

			dropped.setVelocity(v);
			
			if(Bomb.Smoke.size() == 0 && Bomb.Bombs.size() == 0){
				BukkitTask task = new Bomb(plugin).runTaskTimer(plugin, 0, 10);
			}
			if(SuitUtils.CheckItem(CustomSuitPlugin.Smoke, player.getItemInHand())){
				Bomb.Smoke.put(dropped, player);
			}else{
				Bomb.Bombs.put(dropped, player);
			}
			
			if(player.getItemInHand().getAmount()==1){
				player.getInventory().remove(player.getItemInHand());
			}else{
			player.getItemInHand().setAmount(player.getItemInHand().getAmount()-1);
			}
			player.updateInventory();
			
		}
	}

	private void shot(Player player) {
		
	
		player.playSound(player.getLocation(), Values.ManSwordShotSound, 6F,6F);
		Location location = player.getEyeLocation();
		Location target = SuitUtils.getTargetBlock(player, 200).getLocation();
		
		Vector vectorStart = location.toVector();
		Vector vectorEnd = target.toVector();
		
		Vector difference = vectorStart.subtract(vectorEnd);
		
		
		double distance = difference.length();
		if (distance < 0) {
			return;
		}

		Location currentLoc = target.clone();
		double dx = (difference.getX() / distance) * 0.5;
		double dy = (difference.getY() / distance) * 0.5;
		double dz = (difference.getZ() / distance) * 0.5;
		
		for (double i = 0; i <= distance; i += 0.2) {
			currentLoc.add(dx, dy, dz);
		SuitUtils.playEffect(currentLoc, Values.ManSwordShotEffect, 10,0, 25);
		ManUtils.damage(ManUtils.findEntity(currentLoc, player, 2), Values.ManSwordShotDamage, player);
		}
		SuitUtils.createExplosion(currentLoc, Values.ManSwordShotExplosionPower, false,true);
	}
	@EventHandler
	public void ManHitGround(BlockDamageEvent event){
		Player player = event.getPlayer();
		
		if(!ManUtils.Man(player)||!player.isSneaking()){
			return;
		}
		if(!SuitUtils.CheckItem(CustomSuitPlugin.Sword_Man, event.getItemInHand())){
			return;
		}
		boolean Hitting = false;
		if(HittingGround.containsKey(player)){
			if(HittingGround.get(player)){
				Hitting = true;
			}
		}
		
			
		if(Hitting){
			return;
		}
		if(!SchedulerHunger.hunger(player, Values.ManHitGroundHunger)){
			return;
		}
		HittingGround.put(player, true);
		List<Location> listGround =ManUtils.circle(event.getBlock().getLocation(), 16, 3, false, false, -2);
		List<Location> list =ManUtils.circle(event.getBlock().getLocation(), 16, 11, false, false, -10);
		player.playSound(player.getLocation(), Values.ManHitGroundSound, 10F, 8F);
		Fall(listGround);
		for(Location loc : list){
			if(loc.getBlock().getType()!=Material.BEDROCK){
			loc.getBlock().setType(Material.AIR);
			}
		}
		
		ManUtils.damage(ManUtils.findEntity(event.getBlock().getLocation(), player, 16), Values.ManHitGroundDamage, player);
		HittingGround.put(player,false);
		
	}
	private void Fall(List<Location> listGround){
		for(Location loc : listGround){
			SuitUtils.playEffect(loc, Effect.MOBSPAWNER_FLAMES, 1,0,3);
			loc.getWorld().spawnFallingBlock(loc, loc.getBlock().getType(), loc.getBlock().getData());
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
				
				event.setDamage((100+addDamage_Random/100)*event.getDamage());
				
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
		if(event.getMaterial() !=Material.AIR){
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
