package gmail.anto5710.mcp.customsuits._Thor;

import java.util.HashMap;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.PlayerEffect;
import gmail.anto5710.mcp.customsuits.Setting.PotionEffects;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;
import net.minecraft.server.v1_8_R2.EnumParticle;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import com.avaje.ebean.BackgroundExecutor;


public class Hammer implements Listener {
	static CustomSuitPlugin plugin;
	
	static double HammerDeafultDamage = Values.HammerDamage*2;
	static HashMap<Player, Boolean>Teleleporting =new HashMap<>() ;
	

	
	public static Player thor = null;

	public Hammer(CustomSuitPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void Teleportation(PlayerInteractEvent event){
		if(event.getAction() ==Action.RIGHT_CLICK_AIR||event.getAction()==Action.RIGHT_CLICK_BLOCK){
			Player player = event.getPlayer();
			if(!player.isSneaking()){
				return;
			}
			if(!Thor(player)||!SuitUtils.CheckItem(CustomSuitPlugin.hammer, player.getItemInHand())){
				return;
			}
			
			if(!Teleleporting.containsKey(player)||!Teleleporting.get(player)){
				Teleleporting.put(player, true);
				Teleport(player);
			}
		}
	}
	private void Teleport(Player player) {
		Location To = SuitUtils.getTargetBlock(player,300).getLocation();
	org.bukkit.util.Vector direction =	player.getLocation().getDirection();
	
		TeleportEntityInLine(player, To, 3, 0, 3);
		player.playSound(player.getLocation(), Values.HammerTeleportSound, 6.0f, 6.0f);
		Location After = player.getLocation();
		After.setDirection(direction);
		
	}
	@EventHandler
	public void resetThor(PlayerJoinEvent event){
		Player player = event.getPlayer();
		if(thor!=null){
			if(player.getName().endsWith(thor.getName())){
				thor = player;
			}
		}
	}
	
	@EventHandler
	public void ThorMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (Thor(player)) {
			if(thor ==null){
				thor = player;
			}
			if(Thor_Move.list.isEmpty()){
				BukkitTask task = new Thor_Move(plugin).runTaskTimer(plugin, 0,1);
				Thor_Move.list.add(player);
				}
			PlayerEffect.addpotion(PotionEffects.Thor_FAST_DIGGING, player);
			PlayerEffect.addpotion(PotionEffects.Thor_FIRE_RESISTANCE, player);
			PlayerEffect.addpotion(PotionEffects.Thor_HEALTH_BOOST, player);
			PlayerEffect.addpotion(PotionEffects.Thor_INCREASE_DAMAGE, player);
			PlayerEffect.addpotion(PotionEffects.Thor_JUMP, player);
			PlayerEffect.addpotion(PotionEffects.Thor_REGENERATION, player);
			PlayerEffect.addpotion(PotionEffects.Thor_SPEED, player);
			PlayerEffect.addpotion(PotionEffects.Thor_WATER_BREATHING, player);
		}
		else if(!CustomSuitPlugin.hasAbillity(player)){
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
			if(!Thor(player)){
				return;
			}
			if (SuitUtils.CheckItem(CustomSuitPlugin.hammer,
					player.getItemInHand())) {
				if (entity instanceof Damageable) {
					ThorUtils.strikeLightning(entity.getLocation(), player, 0, 0 ,0);
				}
			}
		}
	}
	

	@EventHandler
	public void BackToThor(PlayerInteractEvent event){
		Player player = event.getPlayer();
	
		if(event.getAction()==Action.RIGHT_CLICK_AIR||event.getAction() ==Action.RIGHT_CLICK_BLOCK){
			
			if(!player.isSneaking()&&player == thor){
				
					Location playerlocation = player.getEyeLocation();
					Item hammer =ThorUtils.getItem(player.getWorld() , player);
					if(hammer ==null){
						return;
					}
					TeleportEntityInLine(hammer  , playerlocation, 5 , 0 ,0 );
					player.playSound(playerlocation, Sound.ENDERMAN_TELEPORT, 6.0F,6.0F);
			}
		}
	}
	
	
	
	
	private void TeleportEntityInLine(Entity entity , Location To, int amount,int data, int radius) {
		
		boolean isPlayer = (entity instanceof Player);
		
		Location location = entity.getLocation().clone();
		org.bukkit.util.Vector vectorStart = location.toVector();
		
		org.bukkit.util.Vector vectorEnd = To.toVector();
		
		org.bukkit.util.Vector difference = vectorEnd.subtract(vectorStart);
		
		
		double distance = difference.length();
		if (distance < 0) {
			return;
		}

		Location currentLoc = location.clone();
		double dx = (difference.getX() / distance) * 0.5;
		double dy = (difference.getY() / distance) * 0.5;
		double dz = (difference.getZ() / distance) * 0.5;
		
		for (double i = 0; i <= distance; i += 0.2) {
			currentLoc.add(dx, dy, dz);


			if(isPlayer){
				SuitUtils.playEffect(currentLoc,  EnumParticle.PORTAL, 30 , 1 ,0);
			}else{
				SuitUtils.playEffect(currentLoc,  EnumParticle.HEART, 1 , 0 ,0);
			}
			

		

		
	}
		if(isPlayer){
			final Entity entityClone = entity;
			final Location loc = To.clone();
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				
				@Override
				public void run() {
					entityClone.teleport(loc);
					Teleleporting.put((Player) entityClone,false);
				}
			},  40);
			
		}else{
			
			entity.teleport(To);
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
		if(SuitUtils.CheckItem(CustomSuitPlugin.hammer, item.getItemStack())){
				if(player==thor||thor==null){
					ThorUtils.remove(item);
		
				}else{
					SuitUtils.playEffect(item.getLocation().add(0 , 0.5 ,0), EnumParticle.BARRIER, 1,0, 0);
					event.setCancelled(true);
					
					
				}
		}
	}


	





	public static void setThor(final Player player) {
	

		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			
			@Override
			public void run() {
				
				
				
				
				
				FireworkEffect effect = FireworkEffect.builder().with(Type.STAR).withColor(Color.RED , Color.MAROON ).withFade(Color.GRAY , Color.BLACK  ,Color.WHITE , Color.SILVER).withFlicker().withTrail().trail(true).build();
				
				Firework firework = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
			   FireworkMeta  meta = firework.getFireworkMeta();
				meta.addEffect(effect);
				meta.setPower(0);
				
				firework.setFireworkMeta(meta);
				
				firework.playEffect(EntityEffect.FIREWORK_EXPLODE);
				
				player.getWorld().strikeLightningEffect(player.getLocation());
				
				player.getEquipment().setHelmet(CustomSuitPlugin.Helemt_Thor);
				player.getEquipment().setChestplate(CustomSuitPlugin.Chestplate_Thor);
				player.getEquipment().setLeggings(CustomSuitPlugin.Leggings_Thor);
				player.getEquipment().setBoots(CustomSuitPlugin.Boots_Thor);
				player.updateInventory();
				
				
				player.playSound(player.getLocation(), Values.ThorChangeSound, 7.0F, 7.0F);
				Hammer.thor = player;
				
			}
		}, 10);
		
		
		
	}

	public static void Start_Thunder_strike(Player player) {
			Thunder_Strike.BaseLocation = player.getLocation();
			double Y = Thunder_Strike.BaseLocation.getBlockY()+50;
			Thunder_Strike.BaseLocation.setY(Y);
			
							
					           
					       
					           
					        
			
			
				if(!Thunder_Strike.isStriking){
					
					Thunder_Strike.BaseLocation.getWorld().setStorm(true);
					Thunder_Strike.BaseLocation.getWorld().setThundering(true);
					Thunder_Strike.BaseLocation.getWorld().setWeatherDuration(20*20);
					Wither wither= (Wither) Thunder_Strike.BaseLocation.getWorld().spawnEntity(Thunder_Strike.BaseLocation.clone().add(0, -50, 0), EntityType.WITHER);
					wither.setCustomName(ChatColor.GOLD+"Thunder Strike");
					wither.setRemoveWhenFarAway(false);
					wither.setCustomNameVisible(true);
					wither.setVelocity(new Vector(0, 50, 0));
				BukkitTask task = new Thunder_Strike(plugin , wither).runTaskTimer(plugin, 0,1);
				player.playSound(player.getLocation(), Values.Thunder_Strike_Start_Sound,6F, 6F);
				}
			
		
	}

	
}
