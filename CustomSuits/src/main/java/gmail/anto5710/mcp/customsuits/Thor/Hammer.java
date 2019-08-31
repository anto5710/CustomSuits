package gmail.anto5710.mcp.customsuits.Thor;

import java.util.HashSet;


import java.util.Set;

import gmail.anto5710.mcp.customsuits.CustomSuits.FireworkPlay;
import gmail.anto5710.mcp.customsuits.CustomSuits.FireworkProccesor;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.ItemUtil;
import gmail.anto5710.mcp.customsuits.Utils.MathUtil;
import gmail.anto5710.mcp.customsuits.Utils.ParticleUtil;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.craftbukkit.libs.jline.internal.ShutdownHooks.Task;
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
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;


public class Hammer implements Listener {
	static CustomSuitPlugin plugin;
	
	static double HammerDeafultDamage = Values.HammerDamage*2;
	static Set<Player>Double_Jump_Cooldowns = new HashSet<>();
	public static Player thor = null;
	public static ThorEffecter thorEffecter; 
	public static HammerThrowEffect hammerEffecter;
	public static Thorformation thorform;
	
	public Hammer(CustomSuitPlugin plugin) {
		Hammer.plugin = plugin;
		hammerEffecter = new HammerThrowEffect(plugin, 1);
		thorform = new Thorformation(plugin, 3);
		thorEffecter = new ThorEffecter(plugin, 1);
	}
	
	private static void thorize(Player player){
		if(player!=null){
			thor = player;
			thorEffecter.register(player);
		}
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
	public void refreshThor(PlayerJoinEvent event){
		Player player = event.getPlayer();
		if(thor!=null){
			if(player.getName().endsWith(thor.getName())){
				thorize(player);
			}
		}
	}
	/**
	 * Give Thor's PotionEffects and Particle Effects.
	 * @param event PlayerMoveEvent 
	 */
	@EventHandler
	public void onThorMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (isPractiallyThor(player) && !thorEffecter.isRunning() && canBeThor(player)) {
			thorize(player);
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
	public static void setThor(Player player) {
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
				thorize(player);
			}
		}, 10);
	}
	
	private static double strength = 0.8D;
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
			Location target = SuitUtils.getTargetLoc(player, 100);
			double distance = ploc.distance(target);
//			Vector vector = MathUtils.calculateVelocity(ploc.toVector(), target.toVector(), 0.08, target.getBlockY() - ploc.getBlockY());
			
			Vector vector = target.toVector().subtract(ploc.toVector()).normalize().multiply(strength*distance);
			
			ParticleUtil.playEffect(Particle.EXPLOSION_HUGE, player.getLocation(), 1);
			player.setVelocity(vector);
			
			SuitUtils.playSound(player, Sound.ENTITY_GHAST_SHOOT, 5F, 1F);
			Double_Jump_Cooldowns.add(player);

			Bukkit.getScheduler().runTaskLater(plugin, () -> {
				Double_Jump_Cooldowns.remove(player);
			}, 40);
			
		}
		if(player==thor && isPractiallyThor(player) && player.getGameMode()!=GameMode.CREATIVE){
			player.setFlying(false);
			event.setCancelled(true);
		}
	}
}
