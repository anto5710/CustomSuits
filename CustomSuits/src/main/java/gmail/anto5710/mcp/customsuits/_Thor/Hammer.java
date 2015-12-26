package gmail.anto5710.mcp.customsuits._Thor;

import java.util.HashSet;
import java.util.Set;

import gmail.anto5710.mcp.customsuits.CustomSuits.FireworkPlay;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;
import net.minecraft.server.v1_8_R2.EnumParticle;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
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
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;


public class Hammer implements Listener {
	static CustomSuitPlugin plugin;
	
	static double HammerDeafultDamage = Values.HammerDamage*2;
	static Set<Player>Double_Jump_Cooldowns = new HashSet<>();
	public static Player thor = null;

	public Hammer(CustomSuitPlugin plugin) {
		Hammer.plugin = plugin;
	}
	
	/**
	 * If that Sneaking Player is Thor and also RightClicking with Thor's Hammer , Teleport Player to Targeting Location 
	 * 
	 * @param event PlayerInteractEvent
	 */
	/**
	 * Teleport Player to Targeted Location
	 * @param player Player to Teleport
	 * @param To Target Location
	 */
	/**
	 * Reset Thor When Thor Joins
	 * @param event PlayerJoinEvent 
	 */
	@EventHandler
	public void resetThor(PlayerJoinEvent event){
		Player player = event.getPlayer();
		if(thor!=null){
			if(player.getName().endsWith(thor.getName())){
				thor = player;
			}
		}
	}
	/**
	 * Give Thor's PotionEffects and Particle Effects.
	 * @param event PlayerMoveEvent 
	 */
	@EventHandler
	public void ThorMove(PlayerMoveEvent event) {
		
		Player player = event.getPlayer();
		if (Thor(player)) {
			if(!Thor_Move.isRunning){
				new Thor_Move(plugin, player).runTaskTimer(plugin, 0,1);
				if(thor==null){
					thor =  player;
				}
			}
			
		}
	}
	/**
	 * Strike Lightning To Attacked Entity 
	 * 
	 * @param event EntityDamageByEntityEvent event
	 */
	@EventHandler
	public void DamageLightning(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		Entity entity = event.getEntity();
		if (damager instanceof Player) {
			Player player = (Player) damager;
			if(!Thor(player)){
				return;
			}
			if (ThorUtils.isHammerinHand(player)) {
				if (entity instanceof Damageable) {
					ThorUtils.strikeLightnings(entity.getLocation(), player , 1);
				}
			}
		}
	}
	/**
	 * Telelport Item to targeted Location
	 * @param entity  Entity to Teleport
	 * @param location TargetLocation
	 */
	public static void TeleportItem(Item entity, Location location ) {
		Vector vectorStart = entity.getLocation().toVector();
		
		Vector vectorEnd = location.toVector();
		
		Vector difference = vectorEnd.subtract(vectorStart);
		
		
		final double distance = difference.length();
		final Vector v =difference.normalize().multiply(0.5);
		if (distance < 0) {
			return;
		}
		Location currentLoc  = entity.getLocation().clone();
		for(double x = 0; x<=distance ; x+=0.5){
				currentLoc.add(v);
				SuitUtils.playEffect(currentLoc, EnumParticle.HEART, 30 , 1 ,0);
				entity.teleport(currentLoc);
		}
		
			
		
	}
	
	/**
	 * Teleport Entity to Targeted Location With Particle Trails
	 * 
	 * @param entity Entity to Teleport
	 * @param To Target Location
	 * @param effect EnumParticle type
	 */
	
	/**
	 * Check is that Player Thor 
	 * @param player Player to Check
	 * @return return true if Player is Thor
	 */
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
   /**
    * Cancel Picking up Hammer if Player is not Thor
    * @param event PlayerPickupItemEvent
    */
	@EventHandler
	public void CancelPicking_Up_Hammer(PlayerPickupItemEvent event){
		Item item = event.getItem();
		Player player =event.getPlayer();
		if(SuitUtils.CheckItem(CustomSuitPlugin.hammer, item.getItemStack())){
				if(player==thor){
					ThorUtils.remove(item);
					return;
				}
					event.setCancelled(true);
		}
	}
	/**
	 * Change that Player to Thor
	 * @param player Player to set
	 */
	public static void setThor(final Player player) {
	

		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			
			@Override
			public void run() {
				FireworkEffect effect = SuitUtils.getEffect(Color.RED, Type.STAR);
				FireworkPlay.spawn(player.getLocation().add(0, 3, 0), effect, null);
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
	/**
	 * Spell the Leap Skill when Thor use space bar twice
	 * @param event PlayerToggleFlightEvent
	 */
	@EventHandler
	public static void Jump(PlayerInteractEvent event){
		 Player player = event.getPlayer();
		 if(event.getAction()==Action.RIGHT_CLICK_AIR||event.getAction()==Action.RIGHT_CLICK_BLOCK){
		boolean canspell = (player==thor&&player.isSneaking()&&!player.isDead()&&ThorUtils.isHammerinHand(player));
		boolean isCooldown = false;
		if(Double_Jump_Cooldowns!=null){
			if(Double_Jump_Cooldowns.contains(player)){
				isCooldown = true;
			}
		}
		 
		if(canspell){
			if(!isCooldown){
				int strength = 10;
	       
				Vector vector = player.getLocation().getDirection().normalize().multiply(strength);
				
				SuitUtils.playEffect(player.getLocation(), EnumParticle.EXPLOSION_HUGE, 1, 0, 0);
				player.setVelocity(vector);
         
				player.playSound(player.getLocation(), Sound.GHAST_FIREBALL, 5F, 1F);
				Double_Jump_Cooldowns.add(player);
				final Player player_ = player;
				new BukkitRunnable() {
			
					@Override
					public void run() {
						Double_Jump_Cooldowns.remove(player_);
					}
				}.runTaskLater(plugin, 40);
				}
			}
		}
	}

}
