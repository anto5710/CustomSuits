package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.CustomSuits.FireworkProccesor;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.HungerScheduler;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.gadgets.TNTLauncher;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.ItemUtil;
import gmail.anto5710.mcp.customsuits.Utils.MathUtil;
import gmail.anto5710.mcp.customsuits.Utils.ParticleUtil;
import gmail.anto5710.mcp.customsuits.Utils.CustomEffects;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;

import java.util.ArrayList;
import java.util.Collection;
import org.bukkit.ChatColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SuitWeapons implements Listener {

	static ArrayList<Fireball> listFireball = new ArrayList<>();

	public static CustomSuitPlugin plugin;

//	public static Set<Player> TNT_cooldowns = new HashSet<>();
	public static TNTLauncher tnter; 
	
	private static Material suitlauncher = Values.SuitLauncher;
	
	
	public SuitWeapons(CustomSuitPlugin plugin) {
		this.plugin = plugin;
		tnter = new TNTLauncher(plugin, 1);
	}

	@EventHandler
	public void interectShield(PlayerInteractEvent event) {
		final Player player = event.getPlayer();
		if (SuitUtils.isRightClick(event) && player.isSneaking() && CustomSuitPlugin.isMarkEntity(player)
				&& SuitUtils.getHoldingItem(player).getType() == suitlauncher) {
			if (HungerScheduler.sufficeHunger(player, Values.SuitShieldHunger)) {
				final int sec = (CustomSuitPlugin.getSuitLevel(player)) / 20 + 3;
				player.setNoDamageTicks(sec * 20);
				player.sendMessage(ChatColor.BLUE + "[Info]: " + ChatColor.AQUA + "Created Shield: "
						+ ChatColor.DARK_AQUA + sec + " Seconds! ");
				new BukkitRunnable() {
					int tick;

					@Override
					public void run() {
						if (tick >= sec * 20) {
							this.cancel();
						}
						CustomEffects.play_Suit_NoDamageTime(player, null);
						tick += 5;
					}
				}.runTaskTimer(plugin, 0, 5);
				SuitUtils.playSound(player, Values.SuitShieldSound, 2.0F, 2.0F);
			} else {
				SuitUtils.lack(player, "Energy");
			}
		}
	}

	@EventHandler
	public void LaunchFireball(PlayerInteractEvent clickevent) {
		Player player = clickevent.getPlayer();
		if (SuitUtils.isRightClick(clickevent)
				&& ItemUtil.checkItem(CustomSuitPlugin.missileLauncher, SuitUtils.getHoldingItem(player))) {
			Material ammoMaterial = Values.LauncherAmmo;
			ItemStack ammo = new ItemStack(ammoMaterial, 1);
			
			if (player.getInventory().contains(ammoMaterial, 1)) {
				player.getInventory().removeItem(ammo);
				player.updateInventory();

				SuitUtils.playSound(player, Values.LauncherSound, 5.0F, 5.0F);
				fireball(player);
			} else {
				SuitUtils.lack(player, "Missile");
			}
		}
	}

	private void fireball(Player player) {
		Location targetloc = SuitUtils.getTargetLoc(player, 1000);
		Location locationplayer = player.getLocation();
		Vector vector = targetloc.toVector().subtract(locationplayer.toVector()).normalize().multiply(2);

		Fireball fireball = player.launchProjectile(Fireball.class, vector);

		fireball.setIsIncendiary(true);
		listFireball.add(fireball);
	}

	@EventHandler
	public void explodeFireball(ProjectileHitEvent event) {
		Projectile e = event.getEntity();
		if (e instanceof Fireball) {
			ProjectileSource s = e.getShooter();
			if (s != null && s instanceof Player && listFireball.contains(e)) {
				e.getWorld().createExplosion(e.getLocation(), Values.LauncherPower);
			}
		}
	}

	private void removeItem(Player player, ItemStack itemStack, int amount) {
		Inventory inventory = player.getInventory();
		if (inventory.containsAtLeast(itemStack, amount)) {
			inventory.removeItem(itemStack);
		}
		player.updateInventory();
	}

	private static Material ammo = Material.TNT;
	
	@EventHandler
	public void onPlayerLeftClick(PlayerInteractEvent e) {
		if (SuitUtils.isLeftClick(e)) {
			Player player = e.getPlayer();
			String message = Values.BimMessage;

			int energy = Values.BimHunger;

			if (CustomSuitPlugin.isMarkEntity(player) 
					&& SuitUtils.getHoldingItem(player).getType() == suitlauncher) {

				if(!player.isSneaking()){
					if (HungerScheduler.sufficeHunger(player, energy)) {
						launch(player, message);
					} else {
						SuitUtils.lack(player, "Energy");
					}
				}else{
					if (!tnter.inTNTcooldown(player) && player.getInventory().contains(ammo)) {
						removeItem(player, new ItemStack(ammo), 1);
						tnter.throwTNT(player, 5);
					} else {
						SuitUtils.lack(player, ammo.name());
					}
				}
			}
		}
	}

	private void launch(Player player, String message) {
		Location targetloc = SuitUtils.getTargetLoc(player, 500);
		Location loc = player.getLocation();
		
		playEffect(targetloc, loc, player, true);
		player.sendMessage(message);
	}

	private void playEffect(Location to, Location from, final Player player, boolean isMissile) {
		double radius, damage;
		int amount, data, effect_count;
		Particle effect;
		
		if(isMissile){
			radius = Values.BimRadius;
			damage = Values.Bim * ((CustomSuitPlugin.getSuitLevel(player) / 32D) + 1);
			amount = Values.BimEffectAmount;
			
			data = Values.SuitBim_MissileEffectData;
			effect = Values.SuitProjectileEffect;
			effect_count = 3;
		}else{ // sniper
			radius = Values.SniperRadius;
			damage = Values.SniperDamage;
			amount = Values.SniperEffectAmount; 
			
			data = Material.ANVIL.getId();
			effect = Values.SniperEffect;
			effect_count = 100;
		}
		
		SuitUtils.playSound(player, Sound.ENTITY_WITHER_SHOOT, 1F, 5F);
		SuitUtils.playSound(player, Sound.ENTITY_GENERIC_EXPLODE, 1F, 0F);
		SuitUtils.playSound(player, Sound.ENTITY_BLAZE_AMBIENT, 1F, -1F);
		SuitUtils.lineParticle(to, player.getEyeLocation(), player, effect, amount, data, damage, radius, true, 
				isMissile, player.isSneaking(), effect_count);
	}

	public static void breakblock(Block block) {
		Material material = block.getType();

		if (!Values.IgnoreMaterials_Gun.contains(material)) {
			ParticleUtil.playBlockEffect(Particle.BLOCK_CRACK, block.getLocation(), 10, 5D, block.getBlockData());
			block.breakNaturally();
		}
	}

	public static ArrayList<Entity> findEntity(Location currentLoc, Entity shooter, double radius) {
		Collection<Entity> near = currentLoc.getWorld().getNearbyEntities(currentLoc, radius, radius, radius,
				e -> e instanceof Damageable);
		near.remove(shooter);
		if (shooter.isInsideVehicle()) {
			near.remove(shooter.getVehicle());
		}
		// currentLoc.getWorld().getNearbyEntities(arg0, arg1, arg2, arg3);
		ArrayList<Entity> list = new ArrayList<>();
		for (Entity entity : near) {
			if (MathUtil.distanceBody(currentLoc, entity, radius) && !list.contains(entity)) {
				list.add(entity);
			}
		}
		return list;
	}

	public static void firework(Location location, Entity shooter) {
		FireworkEffect effect = FireworkProccesor.getRandomEffect();
		Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
		FireworkMeta meta = firework.getFireworkMeta();
		meta.addEffect(effect);
		meta.setPower((int) (MathUtil.randomRadius(3) + 1.5));
		firework.setFireworkMeta(meta);
		if (shooter != null) {
			SuitUtils.playSound(shooter, Sound.ENTITY_GENERIC_EXPLODE, 14.0F, 14.0F);
			SuitUtils.playSound(shooter, Sound.ENTITY_WITHER_DEATH, 14.0F, 14.0F);
		}
	}
}
