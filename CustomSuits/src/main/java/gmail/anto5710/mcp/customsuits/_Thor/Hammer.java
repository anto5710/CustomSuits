package gmail.anto5710.mcp.customsuits._Thor;

import java.util.HashSet;


import java.util.Set;

import gmail.anto5710.mcp.customsuits.CustomSuits.FireworkPlay;
import gmail.anto5710.mcp.customsuits.CustomSuits.FireworkProccesor;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.ItemUtil;
import gmail.anto5710.mcp.customsuits.Utils.MathUtils;
import gmail.anto5710.mcp.customsuits.Utils.ParticleUtil;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;


public class Hammer implements Listener {
	static CustomSuitPlugin plugin;
	
	static double HammerDeafultDamage = Values.HammerDamage*2;
	static Set<Player>Double_Jump_Cooldowns = new HashSet<>();
	public static Player thor = null;
	public static Thor_Move move = null; 

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
		if (isPractiallyThor(player) && !Thor_Move.isRunning && move == null) {
			move = new Thor_Move(plugin, player); 
			move.runTaskTimer(plugin, 0,1);
			if(thor == null){
				thor = player;
			}
		}
	}
	
	/**
	 * Strike Lightning To Attacked Entity 
	 * 
	 * @param event EntityDamageByEntityEvent event
	 */
	@EventHandler
	public void damageLightning(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		Entity entity = event.getEntity();
		if (damager instanceof Player) {
			Player player = (Player) damager;
			if (isPractiallyThor(player) && ThorUtils.isHammerinHand(player) && entity instanceof Damageable) {
				ThorUtils.strikeLightnings(entity.getLocation(), player, 1);
			}
		}
	}
	
	/**
	 * Telelport Item to targeted Location
	 * @param entity  Entity to Teleport
	 * @param location TargetLocation
	 */
	public static void teleportItem(Item entity, Location location) {
		Vector vectorStart = entity.getLocation().toVector();
		
		Vector vectorEnd = location.toVector();
		
		Vector difference = vectorEnd.subtract(vectorStart);
		
		
		final double distance = difference.length();
		final Vector v = difference.normalize().multiply(0.5);
		if (distance <= 0) {
			return;
		}
		
		Location currentLoc  = entity.getLocation().clone();
		for(double x = 0; x<=distance ; x+=0.5){
			currentLoc.add(v);
			ParticleUtil.playEffect(Particle.NAUTILUS, currentLoc, 30);
			entity.teleport(currentLoc);
		}
	}
	
	public static boolean canBeThor(Player player){
		return player == Hammer.thor || Hammer.thor==null;
	}
	
	public static boolean isThor(Player p){
		return p == thor;
	}
	
	/**
	 * Check is that Player Thor 
	 * @param player Player to Check
	 * @return return true if Player is Thor
	 */
	public static boolean isPractiallyThor(Player player) {
		int count = 0;
		EntityEquipment equipment = player.getEquipment();
		if (ItemUtil.checkItem(CustomSuitPlugin.Helemt_Thor, equipment.getHelmet())) {
			count++;
		}
		if (ItemUtil.checkItem(CustomSuitPlugin.Chestplate_Thor, equipment.getChestplate())) {
			count++;
		}
		if (ItemUtil.checkItem(CustomSuitPlugin.Leggings_Thor, equipment.getLeggings())) {
			count++;
		}
		if (ItemUtil.checkItem(CustomSuitPlugin.Boots_Thor, equipment.getBoots())) {
			count++;
		}
		return count >= 2;
	}
	
   /**
    * Cancel Picking up Hammer if Player is not Thor
    * @param event PlayerPickupItemEvent
    */
	@SuppressWarnings("deprecation")
	@EventHandler
	public void cancelPicking_Up_Hammer(PlayerPickupItemEvent event){
		Item item = event.getItem();
		Player player =event.getPlayer();
		if(ItemUtil.checkItem(CustomSuitPlugin.hammer, item.getItemStack())){
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
				FireworkEffect effect = FireworkProccesor.getEffect(Color.RED, Type.STAR);
				FireworkPlay.spawn(player.getLocation().add(0, 3, 0), effect, null);
				player.getWorld().strikeLightningEffect(player.getLocation());
				
				player.getEquipment().setHelmet(CustomSuitPlugin.Helemt_Thor);
				player.getEquipment().setChestplate(CustomSuitPlugin.Chestplate_Thor);
				player.getEquipment().setLeggings(CustomSuitPlugin.Leggings_Thor);
				player.getEquipment().setBoots(CustomSuitPlugin.Boots_Thor);
				player.updateInventory();
				
				
				SuitUtils.playSound(player, Values.ThorChangeSound, 7.0F, 7.0F);
				Hammer.thor = player;
			}
		}, 10);
	}
	
	private static int strength = 10;
	/**
	 * Spell the Leap Skill when Thor use space bar twice
	 * @param event PlayerToggleFlightEvent
	 */
	@EventHandler
	public static void Leap(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();

		boolean canspell = player == thor && !player.isDead() && ThorUtils.isHammerinHand(player) && isPractiallyThor(player);
		boolean isCooldown = Double_Jump_Cooldowns != null && Double_Jump_Cooldowns.contains(player);

		if (canspell && !isCooldown) {
			Location ploc = player.getLocation();
			Location target = SuitUtils.getTargetBlock(player, 100).getLocation();
			double distance = ploc.distance(target);
			Vector vector = MathUtils.calculateVelocity(ploc.toVector(), target.toVector(), 0.08, 0);
			
//			Vector vector = target.toVector().subtract(ploc.toVector());
			
			ParticleUtil.playEffect(Particle.EXPLOSION_HUGE, player.getLocation(), 1);
			player.setVelocity(vector);
			
			SuitUtils.playSound(player, Sound.ENTITY_GHAST_SHOOT, 5F, 1F);
			Double_Jump_Cooldowns.add(player);
			final Player player_ = player;

			new BukkitRunnable() {
				@Override
				public void run() {
					Double_Jump_Cooldowns.remove(player_);
				}
			}.runTaskLater(plugin, 40);
			
		}
		if(player==thor && isPractiallyThor(player) && player.getGameMode()!=GameMode.CREATIVE){
			player.setFlying(false);
			event.setCancelled(true);
		}
	}
}
