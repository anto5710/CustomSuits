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
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.SuitUtils;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.WeaponListner;
import gmail.anto5710.mcp.customsuits.Setting.Values;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;


public class Hammer implements Listener {
	CustomSuitPlugin plugin;
	
	static double HammerDeafultDamage = Values.HammerDamage;
	
	static double RingDamage = Values.HammerExplosionRing;
	
	static float Power = Values.HammerExplosionPower;
	
	ArrayList<Player>thor  = new ArrayList<>();

	public Hammer(CustomSuitPlugin plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * Play the Effect of Thor and Remove Thor's Effect when Thor Dead Or Removed
	 * @param event - PlayerMoveEvent
	 * 
	 */
	@EventHandler
	public void ThorMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (Thor(player)) {
			Location location = player.getLocation();

			SuitUtils.playEffect(location, Effect.MAGIC_CRIT, 15, 0, 4);
			PlayerEffect.addpotion(new PotionEffect(PotionEffectType.HEALTH_BOOST, 99999999, 20),player);
			PlayerEffect.addpotion(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999999, 15),player);
			PlayerEffect.addpotion(new PotionEffect(PotionEffectType.SPEED, 99999999, 5),player);
			PlayerEffect.addpotion(new PotionEffect(PotionEffectType.JUMP, 99999999, 3),player);
			PlayerEffect.addpotion(new PotionEffect(PotionEffectType.WATER_BREATHING, 99999999, 2),player);
			PlayerEffect.addpotion(new PotionEffect(PotionEffectType.FAST_DIGGING, 99999999, 10),player);
			PlayerEffect.addpotion(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 99999999, 10), player);
			PlayerEffect.addpotion(new PotionEffect(PotionEffectType.REGENERATION, 99999999, 20),player);
		}
		else if(!CustomSuitPlugin.MarkEntity(player)){
			removePotionEffect(PotionEffectType.HEALTH_BOOST,player);
			removePotionEffect(PotionEffectType.INCREASE_DAMAGE,player);
			removePotionEffect(PotionEffectType.SPEED,player);
			removePotionEffect(PotionEffectType.FAST_DIGGING,player);
		    removePotionEffect(PotionEffectType.JUMP,player);
			removePotionEffect(PotionEffectType.WATER_BREATHING,player);
			removePotionEffect(PotionEffectType.REGENERATION,player);
			removePotionEffect(PotionEffectType.FIRE_RESISTANCE, player);
		}
	}
	
	/**
	 * 
	 * @param PotionEffectType - Removing PotionEffectType
	 * @param player - Player to remove PotionEffect
	 */
	public void removePotionEffect(PotionEffectType PotionEffectType, Player player) {
		if(player.hasPotionEffect(PotionEffectType)){
			player.removePotionEffect(PotionEffectType);
		}
		
	}
	/**
	 * If Thor didn't Armed , Set Thor Armed
	 * Else Throw the Hammer
	 * @param event - This event called When Thor Right Click the Hammer
	 * 
	 * 
	 */
	@EventHandler
	public void ThrowHammer(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.RIGHT_CLICK_AIR
				|| event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (SuitUtils.CheckItem(CustomSuitPlugin.Hammer,
					player.getItemInHand())) {
				if (Thor(player)) {
					Item dropped = player.getWorld().dropItem(
							player.getLocation(), player.getItemInHand());
					if (player.getItemInHand().getAmount() == 1) {
						player.getInventory().setItemInHand(
								new ItemStack(Material.AIR, 1));
					} else {

						player.getInventory()
								.getItemInHand()
								.setAmount(
										player.getItemInHand().getAmount() - 1);
					}
					player.updateInventory();
					dropped.setFallDistance(0);

					Location TargetLocation = SuitUtils.getTargetBlock(player,
							500).getLocation();
					Location loc = dropped.getLocation();
					loc.setY(loc.getY() + 2);

					double gravity = 0.0165959600149011612D;
					dropped.teleport(loc);
					org.bukkit.util.Vector v = SuitUtils.calculateVelocity(
							loc.toVector(), TargetLocation.toVector(), gravity,
							6);

					dropped.setVelocity(v);

					if (player.isSneaking()) {
						playEffect(dropped, player, true);
					} else {

						playEffect(dropped, player, false);
					}
					player.playSound(player.getLocation(), Sound.ANVIL_LAND,
							6.0F, 6.0F);
					player.playSound(player.getLocation(), Sound.IRONGOLEM_HIT,
							4.0F, 2.0F);
				} else {
					setThor(player);

				}
			}
		}

	}
	/**
	 * Cancel The Event If that itemstack is Thor's Hammer
	 * @param event - Called When Hammer Removed
	 * 
	 */
	@EventHandler
	public void ItemRemovedCancel(ItemDespawnEvent event){
		Item item = event.getEntity();
		if(SuitUtils.CheckItem(CustomSuitPlugin.Hammer, item.getItemStack())){
			event.setCancelled(true);
		}
	}
	/**
	 * Add Effects to Damaged Entity
	 * @param event - Called When Entity Damaged By Thor's Hammers
	 *  
	 */
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
					strikeLightning(entity.getLocation(), player, 10, 1.5, HammerDeafultDamage/10);
				}
			}
		}
	}
	/**
	 * Launch the Lightning Missile
	 * @param event - When Player try to launch the Lightning Missile
	 *
	 */
	@EventHandler
	public void Lightning(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.LEFT_CLICK_AIR
				|| event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (SuitUtils.CheckItem(CustomSuitPlugin.Hammer,
					player.getItemInHand())
					&& Thor(player)&&SchedulerHunger.hunger(player, Values.LightningMissileHunger)) {
				Location targetblock = SuitUtils.getTargetBlock(player, 300).getLocation();
				SuitUtils.LineParticle(targetblock, player.getEyeLocation(), player, Effect.LAVA_POP, 20, 0, 2, HammerDeafultDamage, 2, true);
				
				strikeLightning(targetblock, player, 1, 2.5, HammerDeafultDamage);
				SuitUtils.createExplosion(targetblock, Power, false, true);
				}
			}
		
	}
	/**
	 * Cancel the Lightning Damage Event for Thor 
	 * @param event- When Thor damaged by Lightning
	 * 
	 * 
	 */
	
	@EventHandler
	public void LightningDamagedThor(EntityDamageEvent event){
		Entity entity = event.getEntity();
		if(entity instanceof Player){
			Player player = (Player) entity;
			if(Thor(player)&&event.getCause()==DamageCause.LIGHTNING){
				event.setCancelled(true);
				
			}
		}
	}
	/**
	 * Spell Explosion Ring
	 * @param event - When Thor try to use Explosion Ring Skill
	 * 
	 *
	 */
	@EventHandler
	public void ExplosionRing(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.LEFT_CLICK_AIR
				|| event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (Thor(player)
					&& SuitUtils.CheckItem(CustomSuitPlugin.Hammer,
							player.getItemInHand()) && player.isSneaking()&&SchedulerHunger.hunger(player, Values.HammerExplosionRingHunger)) {
				for (double count = 2; count < 50; count+=0.5) {
					player.setNoDamageTicks(20);
					getRing(count, player);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param dropped - Hammer
	 * @param player - Thor
	 * @param isTeleport - Telepoting Player to Hammer Landed Location
	 */
	public void playEffect(Item dropped, Player player, boolean isTeleport) {
		Repeat.listPlayer.put(dropped, player);
		Repeat.listTeleport.put(dropped, isTeleport);
		if(!Repeat.isRunning(Repeat.taskID)){
			BukkitTask task = new Repeat(plugin)
			.runTaskTimer(plugin, 0, 10);
		}
			

	}
	/**
	 * 
	 * @param player - Player to Check
	 * @return Return true If Player has Thor's Armor more than 2
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
     * Make a Explosion Ring
     * @param radius - The Radius Of Circle
     * @param player - Player 
     * 
     * 
     */
	public void getRing(double radius, Player player) {
		int points = 12; // amount of points to be generated
		for (int i = 0; i < 360; i += 360 / points) {
			double angle = (i * Math.PI / 180);
			double x = radius * Math.cos(angle);
			double z = radius * Math.sin(angle);
			Location loc = player.getLocation().add(x, 1, z);
			SuitUtils.createExplosion(loc, 6.5F, false, false);
			Repeat.damage(WeaponListner.findEntity(loc, player, 5.5),
					HammerDeafultDamage * 2, player);
		}
	}
	/**
	 *  Cancel If The Player is not Thor
	 * @param event - When Player Pick up the Hammer
	 * 
	 * 
	 */
	@EventHandler
	public void pickupHammer(PlayerPickupItemEvent event){
		Item item = event.getItem();
		Player player =event.getPlayer();
		if(SuitUtils.CheckItem(CustomSuitPlugin.Hammer, item.getItemStack())){
				if(Thor(player)){
					Repeat.remove(item);
		
				}else{
					SuitUtils.playEffect(player.getEyeLocation(), Effect.STEP_SOUND, 5, Material.IRON_BLOCK.getId(), 2);
					player.playSound(player.getLocation(), Sound.IRONGOLEM_DEATH, 10F, 8F);
					
					event.setCancelled(true);
					
					
				}
		}
	}
	/**
	 *  Set that Player to Thor
	 * @param player - Player to set Thor
	 *
	 */
	public void setThor(Player player) {
		if(thor.size()==0){
			thor.add(player);
		}
		if(!thor.contains(player)){
			return;
		}
			thor.add(player);
		strikeLightning(player.getLocation(), player, 20, 0, HammerDeafultDamage);

		SuitUtils.sleep(500);
		
		
		player.getEquipment().setHelmet(CustomSuitPlugin.Helemt_Thor);
		player.getEquipment().setChestplate(CustomSuitPlugin.Chestplate_Thor);
		player.getEquipment().setLeggings(CustomSuitPlugin.Leggings_Thor);
		player.getEquipment().setBoots(CustomSuitPlugin.Boots_Thor);
		player.updateInventory();

		player.playSound(player.getLocation(), Sound.ENDERMAN_STARE, 7.0F, 7.0F);
		player.getWorld().setStorm(true);
		player.getWorld().setThundering(true);
		
	}
	/**
	 * Strike Lightning
	 * @param location - Location to Stike
	 * @param player - Player
	 * @param amount - The amount of Lightning
	 * @param damageRadius - Radius of Damage 
	 * @param damage - Lightning's Damage
	 */
	public static void strikeLightning(Location location, Player player, int amount,
			double damageRadius, double damage) {
	
		for (int c = 0; c < amount; c++) {
			location.getWorld().strikeLightning(location);
			Repeat.damage(WeaponListner.findEntity(location, player, damageRadius),
					damage, player);
		}
	}
}
