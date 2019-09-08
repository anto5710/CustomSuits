package gmail.anto5710.mcp.customsuits.Utils;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.weapons.SuitWeapons;
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
	
	public static void playSound(Location loc, Sound sound, float volume, float pitch){
		loc.getWorld().playSound(loc, sound, volume, pitch);
	}
	
	public static void playSound(Entity entity, Sound sound, float volume, float pitch){
		entity.getWorld().playSound(entity.getLocation(), sound, volume, pitch);
	}
	
	public static void playerSound(Player player, Sound sound, float volume, float pitch){
		player.playSound(player.getLocation(), sound, volume, pitch);
	}
	
	public static void lineParticle(final Location target,final Location location,
			final Entity shooter, final Particle effect, final int amount, final double speed,
			 final double damage, final double radius,final boolean isSuitProjectile, 
			 final boolean isMissile ,final boolean isSneaking, final int Effect_Count_Second) {
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
							SuitWeapons.breakblock(target.getBlock());
						}
						this.cancel();
						break;
					}
					currentLoc.add(v);

					CustomEffects.play_Suit_Missile_Effect(currentLoc, effect, amount, speed, isSneaking, (isMissile && isSuitProjectile));
					WeaponUtils.damageAdjacent(currentLoc, damage, shooter, radius, isSuitProjectile);
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
	
	public static boolean holding(Player player, ItemStack sample) {
		return !anyNull(player, sample) && ItemUtil.checkItem(sample, getHoldingItem(player));
	}

	public static boolean holdingNone(Player player){
		ItemStack item = getHoldingItem(player);
		return ItemUtil.isAir(item);
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
		tick(player);
	}
	
	public static void warn(Player player,String warn){
		player.sendMessage(ChatColor.DARK_RED
				+ "[Warn]: "+ChatColor.RED+warn+" !");		
		tick(player);
	}
	
	public static void tick(Player player){
		playerSound(player, Sound.BLOCK_DISPENSER_FAIL, 6.0F, 6.0F);
	}
	
	@SuppressWarnings("deprecation")
	private static Set<Material> transparents = 
	Sets.newHashSet(Material.WATER, Material.LAVA, Material.AIR, Material.COBWEB, 
					Material.LEGACY_SAPLING, Material.DEAD_BUSH, Material.GRASS, 
					Material.FERN, Material.WHEAT);
	public static Block getTargetBlock(Player player , int maxDistance){
		return player.getTargetBlock(transparents, maxDistance);
	}
	
	public static Location getTargetLoc(Player player, int maxDistance){
		return getTargetBlock(player, maxDistance).getLocation();
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
	private static Set<EntityType> armables = Sets.newHashSet(EntityType.ARMOR_STAND, EntityType.ZOMBIE, EntityType.SKELETON, EntityType.WITHER_SKELETON, EntityType.HUSK, EntityType.PIG_ZOMBIE);
	public static boolean isArmable(LivingEntity lentity) {
		return armables.contains(lentity.getType());
	}

	public static boolean inWater(Player player){	
		Block eye = player.getEyeLocation().getBlock();
		Block foot = player.getLocation().getBlock();
		Block waist = player.getLocation().add(0, 1, 0).getBlock();
		
		return SuitUtils.isWater(eye) && SuitUtils.isWater(waist) && SuitUtils.isWater(foot);
	}

	@SuppressWarnings("deprecation")
	public static boolean isWater(Block block){
		Material m = block.getType();
 		return m==Material.WATER || m==Material.LEGACY_STATIONARY_WATER;
	}

	public static boolean isUnbreakable(Block hitblock) {
		return Values.unbreakable.contains(hitblock);
	}
}
