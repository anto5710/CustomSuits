package gmail.anto5710.mcp.customsuits.Thor;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.util.Vector;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Setting.PotionEffects;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.Coolable;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.items.InventoryUtil;
import gmail.anto5710.mcp.customsuits.Utils.items.ItemUtil;
import gmail.anto5710.mcp.customsuits.Utils.particles.CustomEffects;
import gmail.anto5710.mcp.customsuits.Utils.particles.ParticleUtil;


public class Hammer implements Listener{
//	private static CustomSuitPlugin plugin;
	
	static double HammerDeafultDamage = Values.HammerDamage * Math.sqrt(PotionEffects.Thor_INCREASE_DAMAGE.getAmplifier());
//	static Set<Player>Double_Jump_Cooldowns = new HashSet<>();
	private static Coolable double_jump = new Coolable();
	public static Player thor = null;
	public static ThorEffecter thorEffecter; 
	public static HammerThrowEffect hammerEffecter;
	public static Thorformation thorform;
	
	public Hammer(CustomSuitPlugin plugin) {
//		Hammer.plugin = plugin;
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
		if (isThorArmored(player) && !thorEffecter.isRunning() && isWorthy(player)) {
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
			if (isThorArmored(player) && Hammer.isHammerinHand(player) && entity instanceof Damageable) {
				SuitUtils.strikeLightnings(entity.getLocation(), player, 1);
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
		if (distance > 0) {
			Location hammerLoc = entity.getLocation().clone();
			for (double x = 0; x <= distance; x += 0.5) {
				hammerLoc.add(v);
				ParticleUtil.playEffect(Values.HammerReturnEffect, hammerLoc, 30);
				entity.teleport(hammerLoc);
			}
		}
	}
	
	public static boolean isWorthy(Player player){
		return player == Hammer.thor || Hammer.thor == null;
	}
	
	public static boolean isThor(Player p){
		return p == thor;
	}
	

	/**
	 * If Player is holding Thor's Hammer , return true;
	 * 
	 * @param player Player to Check
	 * @return is player holding Hammer
	 */
	public static boolean isHammerinHand(Player player) {
		return InventoryUtil.inMainHand(player, CustomSuitPlugin.hammer);
	}
	
	public static void returnHammer(Player player){
		Location pEyeLoc = player.getEyeLocation();
		Item hammer =Hammer.getDroppedHammer(player);
		if (hammer != null) {
			Hammer.teleportItem(hammer, pEyeLoc);
			SuitUtils.playSound(pEyeLoc, Values.HammerTeleportSound, 6.0F, 6.0F);
		}
	}
	
	/**
	 * Check is that Player Thor 
	 * @param player Player to Check
	 * @return return true if Player is Thor
	 */
	public static boolean isThorArmored(Player player) {
		int count = 0;
		EntityEquipment equipment = player.getEquipment();
		if (ItemUtil.compare(CustomSuitPlugin.Helemt_Thor, equipment.getHelmet())) {
			count++;
		}
		if (ItemUtil.compare(CustomSuitPlugin.Chestplate_Thor, equipment.getChestplate())) {
			count++;
		}
		if (ItemUtil.compare(CustomSuitPlugin.Leggings_Thor, equipment.getLeggings())) {
			count++;
		}
		if (ItemUtil.compare(CustomSuitPlugin.Boots_Thor, equipment.getBoots())) {
			count++;
		}
		return count >= 2;
	}
	
   /**
    * Cancel Picking up Hammer if Player is not Thor
    * @param event PlayerPickupItemEvent
    */
	@EventHandler
	public void cancelPicking_Up_Hammer(EntityPickupItemEvent event){
		Item item = event.getItem();
		if(ItemUtil.compare(CustomSuitPlugin.hammer, item.getItemStack())){
			if(event.getEntityType() == EntityType.PLAYER && event.getEntity() == thor){
				Hammer.hammerEffecter.remove(item);				
			} else{
				ParticleUtil.playEffect(Values.HammerPickUpCancel, item.getLocation(), 1);
				event.setCancelled(true);
			}			
		}
	}
	
	/**
	 * Find Thor's Hammer from World
	 * 
	 * @param world  World to Find
	 * @param player Player
	 * @return
	 */
	public static Item getDroppedHammer(Player player) {
		return hammerEffecter.getKey(player);
	}

	/**
	 * Change that Player to Thor
	 * @param player Player to set
	 */
	public static void thormorphosize(Player player) {
		SuitUtils.runAfter(()->{
			CustomEffects.play_Thor_FormationFin(player);
			InventoryUtil.equip(player, CustomSuitPlugin.Helemt_Thor, CustomSuitPlugin.Chestplate_Thor, 
								   CustomSuitPlugin.Leggings_Thor, CustomSuitPlugin.Boots_Thor);
			SuitUtils.playSound(player, Values.ThorChangeSound, 7.0F, 7.0F);
			thorize(player);
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

		boolean canspell = player == thor && !player.isDead() && Hammer.isHammerinHand(player) && isThorArmored(player);
	
		if (canspell && !double_jump.inCooldown()) {
			Location ploc = player.getLocation();
			Location target = SuitUtils.getTargetLoc(player, 100);
			double distance = ploc.distance(target);
//			Vector vector = MathUtils.calculateVelocity(ploc.toVector(), target.toVector(), 0.08, target.getBlockY() - ploc.getBlockY());
			
			Vector vector = target.toVector().subtract(ploc.toVector()).normalize().multiply(strength*distance);
			
			ParticleUtil.playEffect(Particle.EXPLOSION_HUGE, player.getLocation(), 1);
			player.setVelocity(vector);
			
			SuitUtils.playSound(player, Sound.ENTITY_GHAST_SHOOT, 5F, 1F);
			double_jump.cooldown(40);	
		}
		
		if(player==thor && isThorArmored(player) && player.getGameMode()!= GameMode.CREATIVE){
			player.setFlying(false);
			event.setCancelled(true);
		}
	}
}
