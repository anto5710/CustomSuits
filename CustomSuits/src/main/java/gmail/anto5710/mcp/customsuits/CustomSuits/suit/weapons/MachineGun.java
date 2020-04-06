package gmail.anto5710.mcp.customsuits.CustomSuits.suit.weapons;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.PlayerEffect;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.MathUtil;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.damagiom.DamageMode;
import gmail.anto5710.mcp.customsuits.Utils.damagiom.DamageControl;
import gmail.anto5710.mcp.customsuits.Utils.damagiom.DamageUtil;
import gmail.anto5710.mcp.customsuits.Utils.items.InventoryUtil;
import gmail.anto5710.mcp.customsuits.Utils.items.ItemUtil;
import gmail.anto5710.mcp.customsuits.Utils.metadative.Metadative;
import gmail.anto5710.mcp.customsuits.Utils.particles.CustomEffects;
import gmail.anto5710.mcp.customsuits.Utils.particles.ParticleUtil;

public class MachineGun implements Listener{
	public static String gun_regex = Values.gun_regex;
	
	public static Set<Player> charging = new HashSet<>();
	public static Set<Player> sniper_cooldowns = new HashSet<>();

	public static GunEffecter effecter;

	public MachineGun(CustomSuitPlugin plugin) {
		effecter = new GunEffecter(plugin, 1);
	}
	
	
	public static boolean isCharging(Player player) {
		return charging.contains(player);
	}

	public static boolean checkGun(Player player, ItemStack sample) {
		ItemStack itemInHand = InventoryUtil.getMainItem(player);
		return ItemUtil.compareName(itemInHand, gun_regex) && sample.getType() == itemInHand.getType();
	}

	private static double unaimed_spread = 0.3;
	public static void shotMachineGun(Player player) {
		final double spread = PlayerEffect.isZooming(player) ? MathUtil.randomRadius(0.025) : unaimed_spread;
		shoot(player, spread);
		recoil(player, 0.2);
	
		SuitUtils.runAfter(()->MachineGun.shoot(player, spread), 3);
		SuitUtils.runAfter(()->MachineGun.shoot(player, spread), 3);
	}
	
	private static int chargeToExtent(Player player, Material ammo, int full_amount) {
		int charged_ammo = 0;
		if (player.getInventory().contains(ammo)) {
			charging.add(player);
			for (int i = 0; i < full_amount; i++) {
				if (InventoryUtil.sufficeMaterial(player, ammo)) {
					charged_ammo++;
				}
			}
			player.updateInventory();
			SuitUtils.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 4.0F, 1.0F);

			SuitUtils.runAfter(() -> {
				Location loc = player.getLocation();
				SuitUtils.playSound(loc, Sound.BLOCK_ANVIL_LAND, 4.0F, 4.0F);
				SuitUtils.playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 4.0F, 4.0F);
				SuitUtils.playSound(loc, Sound.ENTITY_VILLAGER_HURT, 4.0F, 4.0F);
				SuitUtils.playSound(loc, Sound.UI_BUTTON_CLICK, 4.0F, 4.0F);
				SuitUtils.playSound(loc, Sound.ENTITY_CREEPER_PRIMED, 4.0F, 4.0F);
			}, 40);
			SuitUtils.runAfter(() -> {
				Location loc = player.getLocation();
				SuitUtils.playSound(loc, Sound.ENTITY_IRON_GOLEM_HURT, 4.0F, 4.0F);
				SuitUtils.playSound(loc, Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 15.0F, 3.5F);
				SuitUtils.playSound(loc, Sound.BLOCK_IRON_DOOR_CLOSE, 4.0F, 2.5F);
				SuitUtils.playSound(loc, Sound.BLOCK_IRON_DOOR_OPEN, 4.0F, 4.0F);
				charging.remove(player);
			}, 65);
		} else {
			SuitUtils.lack(player, "Ammo");
			player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 6.0F, 6.0F);
		}
		return charged_ammo;
	}

	public static void cooldown(double msec, Player player) {
		// 1 tick = 0.05 sec
		// 1 sec = 20 tick
		long tick = (long)(msec*20);

		sniper_cooldowns.add(player);
		SuitUtils.runAfter(()->{
			Location loc = player.getLocation();
			SuitUtils.playSound(loc, Sound.ENTITY_IRON_GOLEM_HURT, 4.0F, 4.0F);
			SuitUtils.playSound(loc, Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 15.0F, 3.5F);
			SuitUtils.playSound(loc, Sound.BLOCK_WOODEN_DOOR_CLOSE, 4.0F, 2.5F);
			SuitUtils.playSound(loc, Sound.BLOCK_WOODEN_DOOR_OPEN, 4.0F, 4.0F);
			sniper_cooldowns.remove(player);
		}, tick);
	}

	public static boolean inSniperCooldown(Player player) {
		return sniper_cooldowns.contains(player);
	}

	private static void shoot(Player player, double spread) {
		playGunSound(player);

		Snowball snowball = player.launchProjectile(Snowball.class);
		// enshroud(snowball);;
		Vector v = player.getLocation().getDirection().multiply(3.0).add(MathUtil.randomVector(spread));

		snowball.setVelocity(v);
		Metadative.imprint(snowball, Values.MachineGunDamage);
		Metadative.imprint(snowball, DamageControl.BLOCKSHOT, 60D); //60% 확률로 blockshot
		
		effecter.register(snowball);

		CustomEffects.play_GunShotEffect(player);
	}

	@EventHandler
	public void gun(PlayerInteractEvent clickevent) throws InterruptedException {
		Player player = clickevent.getPlayer();
		ItemStack Gun = CustomSuitPlugin.gunitem;

		if (SuitUtils.isRightClick(clickevent) && CustomSuitPlugin.isMarkEntity(player)
				&& MachineGun.checkGun(player, Gun)) {
			ItemStack gun = InventoryUtil.getMainItem(player);
			String name = ItemUtil.getName(gun);
			String[] values = name.split(gun_regex);
			values[0] = values[0].replace(ChatColor.YELLOW + Values.MachineGunName + "«", "");
			String gunfirstName = ChatColor.YELLOW + Values.MachineGunName + " «";
			ItemStack copy = gun;
			boolean isSniper = player.isSneaking();
			
			if (!isSniper) {
				int machineAmmo  = Integer.parseInt(values[0].replace(gunfirstName, ""));
				if (machineAmmo <= 0) { // 머신건 총알 부족
					if (!player.getInventory().contains(Values.MachineGunAmmo)) {
						SuitUtils.lack(player, "Machine Gun Ammo"); 
						return;
					} else if (!isCharging(player)) {
						int amount = chargeToExtent(player, Material.FLINT, Values.MachineGunAmmoAmount);
						ItemUtil.name(copy, gunfirstName + amount + gun_regex + values[1].replace("»", "") + "»");
						InventoryUtil.setMainItem(player, copy);
					}
				}

				if (!isCharging(player)) {					
					CustomEffects.play_GunShotEffect(player);
					shotMachineGun(player);
					ItemUtil.name(copy, gunfirstName + (Integer.parseInt(values[0].replace(gunfirstName, "")) - 1) + 
									gun_regex + values[1].replace("»", "") + "»");
					InventoryUtil.setMainItem(player, copy);
				}
			} else {
				int sniperAmmo = Integer.parseInt(values[1].replace("»", ""));
				if (sniperAmmo <= 0) {
					if (!player.getInventory().contains(Values.SniperAmmo)) {
						SuitUtils.lack(player, "Sniper Ammo");
						return;
					} else if (!isCharging(player)) {
						int amount = MachineGun.chargeToExtent(player, Values.SniperAmmo, Values.SnipeAmmoAmount);
						ItemUtil.name(copy, values[0] + gun_regex + amount + "»");
						InventoryUtil.setMainItem(player, copy);
					}
				}
				if (!isCharging(player)) {
					Location target = SuitUtils.getTargetLoc(player, Values.Suit_Gun_Shot_Radius);
					if (!inSniperCooldown(player)) {
						Location location = player.getEyeLocation();
						Vector direction = location.getDirection();
						location.setDirection(MathUtil.randomLoc(target.clone().subtract(location), 5).toVector().normalize());
						if (PlayerEffect.isZooming(player)) {
							location.setDirection(direction);
						}

						SuitUtils.playSound(player, Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 3.0F, 3.0F);
						SuitUtils.playSound(player, Sound.BLOCK_ANVIL_LAND, 0.3F, 0.3F);
						SuitUtils.playSound(player, Sound.ENTITY_GENERIC_EXPLODE, 23.0F, 21.0F);
						SuitUtils.playSound(player, Sound.ENTITY_IRON_GOLEM_HURT, 4.0F, 4.0F);

						CustomEffects.play_GunShotEffect(player);

						CustomEffects.lineParticle(target, location, player, loc -> {
							ParticleUtil.playEffect(Values.SniperEffect, loc, Values.SniperEffectAmount);
							DamageUtil.areaDamage(loc, Values.SniperDamage, player, 0.5, DamageMode.X_TEN_FIREWORK);
						}, 20);
						cooldown(2, player);
						ItemUtil.name(copy,
								values[0] + gun_regex + (Integer.parseInt(values[1].replace("»", "")) - 1) + "»");
						InventoryUtil.setMainItem(player, copy);
					}
				}
			}
			player.updateInventory();
		}
	}
	
	private static void recoil(Player player, double amplitude) {
		Vector direction = player.getLocation().getDirection().multiply(-amplitude);
		direction.setY(0.1);
		player.setVelocity(direction);
	}
	
	private static void playGunSound(Player player) {
		Location loc = player.getLocation();
		SuitUtils.playSound(loc, Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 3.0F, 3.0F);
		SuitUtils.playSound(loc, Sound.BLOCK_ANVIL_LAND, 1.0F, 1.0F);
		SuitUtils.playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);
		SuitUtils.playSound(loc, Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 3.0F, 3.0F);
		SuitUtils.playSound(loc, Sound.BLOCK_ANVIL_LAND, 0.3F, 0.3F);
		SuitUtils.playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 23.0F, 21.0F);
		SuitUtils.playSound(loc, Sound.ENTITY_IRON_GOLEM_HURT, 4.0F, 4.0F);
	}
}
