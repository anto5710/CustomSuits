package gmail.anto5710.mcp.customsuits._Thor;

import java.util.HashSet;
import java.util.Vector;

import javax.persistence.Version;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.Cooldown;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.SuitUtils;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.WeaponListner;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

public class Hammer implements Listener {
	CustomSuitPlugin plugin;
	double HammerDeafultDamage = 20;

	public Hammer(CustomSuitPlugin plugin) {
		this.plugin = plugin;
	}
	@EventHandler
	public void ThorMove(PlayerMoveEvent event){
		
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
					player.getInventory().remove(player.getItemInHand());
					dropped.setFallDistance(0);

					Location target = player.getTargetBlock(
							(HashSet<Byte>) null, 100000).getLocation();
					Location loc = dropped.getLocation();
					loc.setY(loc.getY() + 2.5);

					double gravity = 0.0164959600149011612D;
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
				} else {
					setThor(player);

				}
			}
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
					for (int i = 0; i < 10; i++) {
						entity.getWorld().strikeLightning(entity.getLocation());
					}
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
					&& Thor(player)) {
				Block block = player
						.getTargetBlock(((HashSet<Byte>) null), 100);
				Location loc = block.getLocation();
				if (loc.getBlock() != null) {
					loc.getWorld().strikeLightning(loc);
					loc.setY(loc.getY() + 1);
					SuitUtils.playEffect(loc, Effect.MAGIC_CRIT, 250, 0, 3);
					Repeat.damage(WeaponListner.findEntity(loc, player, 1.5),
							HammerDeafultDamage, player);
				}
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
							player.getItemInHand()) && player.isSneaking()) {
				for (int i = 2; i < 30; i++) {
					player.setNoDamageTicks(20);
					getRing(i, player);
				}
			}
		}
	}

	private void playEffect(Item dropped, Player player, boolean isTP) {
		BukkitTask task = new Repeat(plugin, player, dropped, isTP)
				.runTaskTimer(plugin, 0, 10);

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

	public void getRing(int size, Player player) {
		int points = 12; // amount of points to be generated
		for (int i = 0; i < 360; i += 360 / points) {
			double angle = (i * Math.PI / 180);
			double x = size * Math.cos(angle);
			double z = size * Math.sin(angle);
			Location loc = player.getLocation().add(x, 1, z);
			SuitUtils.createExplosion(loc, 6.5F, false, true);
			Repeat.damage(WeaponListner.findEntity(loc, player, 5),
					HammerDeafultDamage * 2, player);
		}
	}

	public void setThor(Player player) {

		for (int c = 0; c < 20; c++) {
			player.getWorld().strikeLightningEffect(player.getLocation());
		}
		SuitUtils.sleep(500);
		player.playSound(player.getLocation(), Sound.ENDERMAN_STARE, 7.0F, 7.0F);
		for (PotionEffect p : player.getActivePotionEffects()) {
			player.removePotionEffect(p.getType());
		}
		CustomSuitPlugin.addPotionEffects(player, new PotionEffect(
				PotionEffectType.SPEED, 999999999, 3), new PotionEffect(
				PotionEffectType.FIRE_RESISTANCE, 9999999, 40),
				new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999999,
						30), new PotionEffect(PotionEffectType.HEALTH_BOOST,
						999999999, 40), new PotionEffect(
						PotionEffectType.REGENERATION, 999999999, 10),
				new PotionEffect(PotionEffectType.JUMP, 999999999, 5));
		player.getEquipment().setHelmet(CustomSuitPlugin.Helemt_Thor);
		player.getEquipment().setChestplate(CustomSuitPlugin.Chestplate_Thor);
		player.getEquipment().setLeggings(CustomSuitPlugin.Leggings_Thor);
		player.getEquipment().setBoots(CustomSuitPlugin.Boots_Thor);
		player.updateInventory();

	}

}
