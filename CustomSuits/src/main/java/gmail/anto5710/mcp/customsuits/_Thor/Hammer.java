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

	public void removePotionEffect(PotionEffectType regeneration, Player player) {
		if(player.hasPotionEffect(regeneration)){
			player.removePotionEffect(regeneration);
		}
		
	}

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
					if(player.getItemInHand().getAmount()==1){
						player.getInventory().setItemInHand(new ItemStack(Material.AIR, 1));
					}else{
					
						player.getInventory().getItemInHand().setAmount(player.getItemInHand().getAmount()-1);
					}
						player.updateInventory();
					dropped.setFallDistance(0);

					Location target = player.getTargetBlock((HashSet<Byte>)null, 1000)
							.getLocation();
					Location loc = dropped.getLocation();
					loc.setY(loc.getY() + 2);

					double gravity = 0.0165959600149011612D;
					dropped.teleport(loc);
					org.bukkit.util.Vector v = SuitUtils.calculateVelocity(
							loc.toVector(), target.toVector(), gravity, 6);

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
	@EventHandler
	public void ItemRemovedCancel(ItemDespawnEvent event){
		Item item = event.getEntity();
		if(SuitUtils.CheckItem(CustomSuitPlugin.Hammer, item.getItemStack())){
			event.setCancelled(true);
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
					strikeLightning(entity.getLocation(), player, 10, 1.5, HammerDeafultDamage/10);
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
					&& Thor(player)&&SchedulerHunger.hunger(player, -1)) {
				Location block = player.getTargetBlock((HashSet<Byte>)null, 300).getLocation();
				SuitUtils.LineParticle(block, player.getEyeLocation(), player, Effect.LAVA_POP, 20, 0, 2, HammerDeafultDamage, 2, true);
				
				strikeLightning(block, player, 1, 2.5, HammerDeafultDamage);
				SuitUtils.createExplosion(block, Power, false, true);
				}
			}
		
	}

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

	@EventHandler
	public void ExplosionRing(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.LEFT_CLICK_AIR
				|| event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (Thor(player)
					&& SuitUtils.CheckItem(CustomSuitPlugin.Hammer,
							player.getItemInHand()) && player.isSneaking()&&SchedulerHunger.hunger(player, -4)) {
				for (double i = 2; i < 50; i+=0.5) {
					player.setNoDamageTicks(20);
					getRing(i, player);
				}
			}
		}
	}
	

	private void playEffect(Item dropped, Player player, boolean isTP) {
		Repeat.listPlayer.put(dropped, player);
		Repeat.listTp.put(dropped, isTP);
		if(!Repeat.isRunning(Repeat.id)){
			BukkitTask task = new Repeat(plugin)
			.runTaskTimer(plugin, 0, 10);
		}
			

	}

	public static boolean Thor(Player p) {
		int count = 0;
		if (SuitUtils.CheckItem(CustomSuitPlugin.Helemt_Thor, p.getEquipment()
				.getHelmet())) {
			count++;
		}
		if (SuitUtils.CheckItem(CustomSuitPlugin.Chestplate_Thor, p
				.getEquipment().getChestplate())) {
			count++;
		}
		if (SuitUtils.CheckItem(CustomSuitPlugin.Leggings_Thor, p
				.getEquipment().getLeggings())) {
			count++;
		}
		if (SuitUtils.CheckItem(CustomSuitPlugin.Boots_Thor, p.getEquipment()
				.getBoots())) {
			count++;
		}
		if (count >= 2) {
			return true;
		}
		return false;

	}

	public void getRing(double radiuse, Player player) {
		int points = 12; // amount of points to be generated
		for (int i = 0; i < 360; i += 360 / points) {
			double angle = (i * Math.PI / 180);
			double x = radiuse * Math.cos(angle);
			double z = radiuse * Math.sin(angle);
			Location loc = player.getLocation().add(x, 1, z);
			SuitUtils.createExplosion(loc, 6.5F, false, false);
			Repeat.damage(WeaponListner.findEntity(loc, player, 5.5),
					HammerDeafultDamage * 2, player);
		}
	}
	@EventHandler
	public void pickupHammer(PlayerPickupItemEvent event){
		Item item = event.getItem();
		Player player =event.getPlayer();
		if(SuitUtils.CheckItem(CustomSuitPlugin.Hammer, item.getItemStack())){
				if(Thor(player)){
					Repeat.listPlayer.remove(item);
					Repeat.listTp.remove(item);
		
				}else{
					SuitUtils.playEffect(player.getEyeLocation(), Effect.STEP_SOUND, 5, Material.IRON_BLOCK.getId(), 2);
					player.playSound(player.getLocation(), Sound.IRONGOLEM_DEATH, 10F, 8F);
					player.damage(HammerDeafultDamage, item);
					event.setCancelled(true);
					
				}
		}
	}
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
	}

	public static void strikeLightning(Location loc, Player player, int amount,
			double damageRadius, double damage) {
		if(loc.getBlock().getType()==Material.AIR){
			return;
		}
		for (int c = 0; c < amount; c++) {
			loc.getWorld().strikeLightning(loc);
			Repeat.damage(WeaponListner.findEntity(loc, player, damageRadius),
					damage, player);
		}
	}
}
