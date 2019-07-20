package gmail.anto5710.mcp.customsuits.Utils;

import gmail.anto5710.mcp.customsuits.CustomSuits.PlayEffect;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.WeaponListner;
import gmail.anto5710.mcp.customsuits.Setting.Values;

import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.google.common.collect.Sets;

public class SuitUtils {
	static CustomSuitPlugin plugin;
	public SuitUtils(CustomSuitPlugin plugin){
		SuitUtils.plugin = plugin;
	}
	
	public static boolean anyNull(Object ... objs){
		if(objs==null) return true;
		
		for(int i =0; i< objs.length; i++){
			if(objs[i]==null) return true;
		}
		return false;
	}
	
	public static void LineParticle(final Location target,final Location location,
			final Entity shooter, final Particle effect, final int amount, final double speed,
			 final double damage, final double radius,final boolean isSuitProjectile, final boolean isMissile ,final boolean isSneaking, final int Effect_Count_Second) {
		Vector vectorStart = location.toVector();

		Vector vectorEnd = target.toVector();

		Vector difference = vectorEnd.subtract(vectorStart);

		final double distance = difference.length();
		final Vector v = location.getDirection().normalize().multiply(0.5);
		if (distance < 0) {
			return;
		}

		new BukkitRunnable() {
			Location currentLoc = location.clone();
			double count = 0;

			@Override
			public void run() {

				for (int c = 0; c < Effect_Count_Second; c++) {
					if (count >= distance) {
						if (isMissile) {
							float power = Values.BimExplosionPower;
							SuitUtils.createExplosion(currentLoc, power, false, true);

						} else {
							WeaponListner.breakblock(target.getBlock());
						}
						this.cancel();
						break;
					}
					currentLoc.add(v);

					PlayEffect.play_Suit_Missile_Effect(currentLoc, effect, amount, speed, isSneaking, (isMissile && isSuitProjectile));
					WeaponUtils.damageNeffect(currentLoc, damage, shooter, isMissile, isSuitProjectile, radius);
					count += 1;
				}

			}
		}.runTaskTimer(plugin, 0, 2);
	}

	public static void createExplosion(Location location, float power , boolean setFire , boolean BlockBreak ){
		
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();
		
		location.getWorld().createExplosion(x, y, z, power, setFire, BlockBreak);
	}
	
	public static void sleep(long msec) {
		try {
			Thread.sleep(msec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean holdingNothing(Player player){
		ItemStack item = getHoldingItem(player);
		return isNull(item);
	}
	
	public static boolean isNull(ItemStack item){
		return item==null || item.getType() == Material.AIR;
	}
	
	public static ItemStack getHoldingItem(Player player){
		return player.getInventory().getItemInMainHand();
	}
	
	public static void setHoldingItem(Player player, ItemStack item){
		player.getInventory().setItemInMainHand(item);
	}
	
	public static Entity getFirstPassenger(Entity vehicle){
		List<Entity> passengers = vehicle.getPassengers();
		return (passengers == null||passengers.isEmpty()) ? null : passengers.get(0);
	}
	public static void lack(Player player,String warn){
		player.sendMessage(ChatColor.DARK_RED
				+ "[Warn]: "+ChatColor.RED+"You don't have enough "+ ChatColor.GOLD+warn+ChatColor.RED+" !");
		
		player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 6.0F, 6.0F);
	}
	
	
	public static void warn(Player player,String warn){
		player.sendMessage(ChatColor.DARK_RED
				+ "[Warn]: "+ChatColor.RED+warn+" !");
		
		player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 6.0F, 6.0F);
	}
	
	public static boolean checkItem(ItemStack sample, ItemStack check){
		if(check==null){
			return false;
		}
		if(sample.getType()==check.getType () && checkName(sample , check)){
			return checkLore(sample, check);
		}
		return false;
	}

	public static boolean checkLore(ItemStack sample, ItemStack check) {
		if (!sample.getItemMeta().hasLore() && !check.getItemMeta().hasLore()) return true;
		
		if (sample.getItemMeta().hasLore() && check.getItemMeta().hasLore()) {
			
			List<String> sampleList = sample.getItemMeta().getLore();
			List<String> checkList = check.getItemMeta().getLore();

			if (sampleList.size() != checkList.size()) {
				return false;
			}
			for (int index = 0; index <= checkList.size() - 1; index++) {
				if (!checkList.get(index).endsWith(sampleList.get(index))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public static boolean checkName(ItemStack item, String token){
		if (item==null || token ==null || token.isEmpty()) return false;
		
		String name = item.getItemMeta().getDisplayName();
		return name != null && name.contains(token);
	}
	
	public static boolean checkName(ItemStack sample, ItemStack check) {
		if(sample == null || check == null) return sample == check;
		
		String sampleName = sample.getItemMeta().getDisplayName();
		String checkName = check.getItemMeta().getDisplayName();
		
		if(sampleName == null && checkName == null) return true;
		
		return sampleName != null && checkName != null && sampleName.endsWith(checkName);
	}
	
	public static Block getTargetBlock(Player player , int maxDistance){
		Set<Material> transparents = 
		Sets.newHashSet(Material.WATER, Material.LAVA, Material.AIR, Material.COBWEB, 
						Material.LEGACY_SAPLING, Material.DEAD_BUSH, Material.GRASS, 
						Material.FERN, Material.WHEAT);
			
		Block targetblock = player.getTargetBlock(transparents, maxDistance);
		return targetblock;
	}
	
	public static void wrongCommand(Player player, Command command) {
		String message =ChatColor.DARK_RED+ "[Info]: "+ChatColor.RED+"Wrong Command!"+"\n"+
						ChatColor.DARK_RED+"[Usage]: "+ChatColor.RED+command.getUsage();
		player.sendMessage(message);
	}
	
	public static boolean isLeftClick(PlayerInteractEvent e){
		if(e==null) return false;
		
		Action action = e.getAction();
		return action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK;
	}
	
	public static boolean isRightClick(PlayerInteractEvent e){
		if(e==null) return false;
		
		Action action = e.getAction();
		return action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;
	}
	
	public static void teleportWithPassengers(Location to, Entity vehicle){
		if(vehicle==null || to == null) return;
			
		List<Entity> passengers = vehicle.getPassengers();
		vehicle.eject();
		vehicle.teleport(to);
		
		if(passengers!=null){
			passengers.forEach(p->{
				p.teleport(to);
				vehicle.addPassenger(p);
			});
		}
	}
	private static Set<EntityType> armorables = Sets.newHashSet(EntityType.ARMOR_STAND, EntityType.ZOMBIE, EntityType.SKELETON, EntityType.WITHER_SKELETON, EntityType.HUSK, EntityType.PIG_ZOMBIE);
	public static boolean canWearArmor(LivingEntity lentity) {
		return armorables.contains(lentity.getType());
	}
}
