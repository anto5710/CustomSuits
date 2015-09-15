package gmail.anto5710.mcp.customsuits._Thor;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.persistence.Version;
import javax.swing.plaf.metal.MetalBorders.PaletteBorder;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.Cooldown;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.PlayerEffect;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.SchedulerHunger;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.WeaponListner;
import gmail.anto5710.mcp.customsuits.Setting.PotionEffects;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;
import gmail.anto5710.mcp.customsuits.Utils.WeaponUtils;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;


public class Hammer implements Listener {
	CustomSuitPlugin plugin;
	
	static double HammerDeafultDamage = Values.HammerDamage;
	
	static double RingDamage = Values.HammerExplosionRing;
	
	static float Power = Values.HammerExplosionPower;
	
	static Player thor;

	public Hammer(CustomSuitPlugin plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void ThorMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (Thor(player)) {
			Location location = player.getLocation();

			SuitUtils.playEffect(location, Effect.MAGIC_CRIT, 15, 0, 4);
			PlayerEffect.addpotion(PotionEffects.Thor_FAST_DIGGING, player);
			PlayerEffect.addpotion(PotionEffects.Thor_FIRE_RESISTANCE, player);
			PlayerEffect.addpotion(PotionEffects.Thor_HEALTH_BOOST, player);
			PlayerEffect.addpotion(PotionEffects.Thor_INCREASE_DAMAGE, player);
			PlayerEffect.addpotion(PotionEffects.Thor_JUMP, player);
			PlayerEffect.addpotion(PotionEffects.Thor_REGENERATION, player);
			PlayerEffect.addpotion(PotionEffects.Thor_SPEED, player);
			PlayerEffect.addpotion(PotionEffects.Thor_WATER_BREATHING, player);
		}
		else if(!CustomSuitPlugin.MarkEntity(player)){
			ThorUtils.removePotionEffect(PotionEffects.Thor_FAST_DIGGING, player);
			ThorUtils.removePotionEffect(PotionEffects.Thor_FIRE_RESISTANCE, player);
			ThorUtils.removePotionEffect(PotionEffects.Thor_HEALTH_BOOST, player);
			ThorUtils.removePotionEffect(PotionEffects.Thor_INCREASE_DAMAGE, player);
			ThorUtils.removePotionEffect(PotionEffects.Thor_JUMP, player);
			ThorUtils.removePotionEffect(PotionEffects.Thor_REGENERATION, player);
			ThorUtils.removePotionEffect(PotionEffects.Thor_SPEED, player);
			ThorUtils.removePotionEffect(PotionEffects.Thor_WATER_BREATHING, player);
		}
	}
	
	

	
	
	@EventHandler
	public void DamageLightning(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		Entity entity = event.getEntity();
		if (damager instanceof Player) {
			Player player = (Player) damager;
			if (SuitUtils.CheckItem(CustomSuitPlugin.Hammer,
					player.getItemInHand())) {
				if (entity instanceof Damageable) {
					if (entity instanceof LivingEntity) {

						((LivingEntity) entity)
								.addPotionEffect(new PotionEffect(
										PotionEffectType.BLINDNESS, 100, 100));
						((LivingEntity) entity)
								.addPotionEffect(new PotionEffect(
										PotionEffectType.CONFUSION, 100, 100));
						((LivingEntity) entity)
								.addPotionEffect(new PotionEffect(
										PotionEffectType.SLOW, 100, 100));
						((LivingEntity) entity)
								.addPotionEffect(new PotionEffect(
										PotionEffectType.WEAKNESS, 100, 100));
					}
					ThorUtils.strikeLightning(entity.getLocation(), player, 10, 1.5, HammerDeafultDamage/10);
				}
			}
		}
	}
	
	@EventHandler
	public void Lightning(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.LEFT_CLICK_AIR
				|| event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (SuitUtils.CheckItem(CustomSuitPlugin.Hammer,
					player.getItemInHand())
					&& Thor(player)&&SchedulerHunger.hunger(player, Values.LightningMissileHunger)) {
				Location targetblock = SuitUtils.getTargetBlock(player, 300).getLocation();
				SuitUtils.LineParticle(targetblock, player.getEyeLocation(), player, Effect.LAVA_POP, 5, 0, 2, HammerDeafultDamage, 2, true);
				
				ThorUtils.strikeLightning(targetblock, player, 1, 2.5, HammerDeafultDamage);
				SuitUtils.createExplosion(targetblock, Power, false, true);
				}
			}
		
	}
	@EventHandler
	public void BackToThor(PlayerInteractEvent event){
		Player player = event.getPlayer();
		if(event.getAction()==Action.LEFT_CLICK_AIR||event.getAction() ==Action.LEFT_CLICK_BLOCK){
			if(!player.isSneaking()&&player == thor){
				
					Location playerlocation = player.getLocation();
					Item hammer =ThorUtils.getItem(player.getWorld() , player);
					if(hammer ==null){
						return;
					}
					Location entitylocation = hammer.getLocation();
					org.bukkit.util.Vector vectorStart = entitylocation.toVector();
					
					org.bukkit.util.Vector vectorEnd = playerlocation.toVector();
					
					org.bukkit.util.Vector difference = vectorStart.subtract(vectorEnd);
					
					
					double distance = difference.length();
					if (distance < 0) {
						return;
					}

					Location currentLoc = playerlocation.clone();
					double dx = (difference.getX() / distance) * 0.5;
					double dy = (difference.getY() / distance) * 0.5;
					double dz = (difference.getZ() / distance) * 0.5;
					for (int i = 0; i <=distance; i++) {
						currentLoc.add(dx , dy , dz);
						
						hammer.teleport(currentLoc);

						SuitUtils.playEffect(currentLoc, Effect.HEART, 55, 0, 4);
						

					
			
					
				}
					hammer.teleport(playerlocation);
					player.playSound(playerlocation, Sound.ENDERMAN_TELEPORT, 6.0F,6.0F);
			}
		}
	}
	
	
	
	
	public static boolean Thor(Player player) {
		int count = 0;
		if (SuitUtils.CheckItem(CustomSuitPlugin.Helemt_Thor, player.getEquipment()
				.getHelmet())) {
			count++;
		}
		if (SuitUtils.CheckItem(CustomSuitPlugin.Chestplate_Thor, player
				.getEquipment().getChestplate())) {
			count++;
		}
		if (SuitUtils.CheckItem(CustomSuitPlugin.Leggings_Thor, player
				.getEquipment().getLeggings())) {
			count++;
		}
		if (SuitUtils.CheckItem(CustomSuitPlugin.Boots_Thor, player.getEquipment()
				.getBoots())) {
			count++;
		}
		if (count >= 2) {
			return true;
		}
		return false;

	}
   

	@EventHandler
	public void pickupHammer(PlayerPickupItemEvent event){
		Item item = event.getItem();
		Player player =event.getPlayer();
		if(SuitUtils.CheckItem(CustomSuitPlugin.Hammer, item.getItemStack())){
				if(player==thor){
					ThorUtils.remove(item);
		
				}else{
					SuitUtils.playEffect(player.getEyeLocation(), Effect.STEP_SOUND, 5, Material.IRON_BLOCK.getId(), 2);
					player.playSound(player.getLocation(), Sound.IRONGOLEM_DEATH, 10F, 8F);
					
					event.setCancelled(true);
					
					
				}
		}
	}


	
	@EventHandler
	public void MoveWorldThor(PlayerInteractEvent event){
		Player player = event.getPlayer();
		if(!Thor(player)||!player.isSneaking()||player.getItemInHand().getType() !=Material.AIR){
			return;
		}
		
			if(event.getAction() ==Action.LEFT_CLICK_AIR||event.getAction() ==Action.LEFT_CLICK_BLOCK){
				Location location = player.getLocation();	
				Environment world_type  = player.getWorld().getEnvironment();
				
				if(event.getAction() ==Action.LEFT_CLICK_AIR){
					if(player.getWorld().getEnvironment() ==Environment.THE_END){
					world_type = Environment.NORMAL;
					}
					else{
						world_type =Environment.THE_END;
					}
				}
				else if(event.getAction()==Action.LEFT_CLICK_BLOCK)	{
					if(player.getWorld().getEnvironment() ==Environment.NETHER){
						world_type = Environment.NORMAL;
					}else{
					world_type =Environment.NETHER;
					}
				}
				
				
			
				World world=	getWorldByType(world_type , player);
				location.setWorld(world);
				location.setY(location.getY()+1);
				ThorUtils.strikeLightning(player.getLocation(), player, 30, 0, 0);
				SuitUtils.playEffect(player.getLocation(), Values.HammerTeleportEffect, 200, 0, 10);
				SuitUtils.playEffect(player.getLocation(), Values.HammerHitGround, 100, 0, 10);
				location = world.getSpawnLocation();
				player.teleport(location);
				
				SuitUtils.playEffect(player.getLocation(), Values.HammerTeleportEffect, 200, 0, 10);
				SuitUtils.playEffect(player.getLocation(), Values.HammerHitGround, 100, 0, 10);
				ThorUtils.strikeLightning(location, player, 30, 0, 0);
				player.playSound(player.getLocation(), Values.HammerTeleportSound, 16.0F, 16.0F);
			}
			
		
	}


	private World getWorldByType(Environment world_type, Player player) {
			for(World world : player.getServer().getWorlds()){
				if(world.getEnvironment()==world_type){
					return world;
				}
			}
		return player.getWorld();
	}


	public static void setThor(Player player) {
		if(thor==null){
			thor=player;
		}
		if(player!=thor){
			return;
		}
			
			ThorUtils.strikeLightning(player.getLocation(), player, 20, 0, HammerDeafultDamage);

		SuitUtils.sleep(500);
		
		
		player.getEquipment().setHelmet(CustomSuitPlugin.Helemt_Thor);
		player.getEquipment().setChestplate(CustomSuitPlugin.Chestplate_Thor);
		player.getEquipment().setLeggings(CustomSuitPlugin.Leggings_Thor);
		player.getEquipment().setBoots(CustomSuitPlugin.Boots_Thor);
		player.updateInventory();

		player.playSound(player.getLocation(), Values.ThorChangeSound, 7.0F, 7.0F);
		
		
	}

	
}
