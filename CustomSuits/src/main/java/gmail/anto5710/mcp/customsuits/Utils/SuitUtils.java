package gmail.anto5710.mcp.customsuits.Utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.google.common.collect.Sets;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Setting.Values;

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
	
	public static void lineParticle(Location target, Location loc, Entity shooter, Consumer<Location> effect, long effectsPerTick) {
		double distance = loc.distance(target);
		if (distance <= 0) return;
			
		Vector v = loc.getDirection().normalize().multiply(0.5);
		new BukkitRunnable() {
			Location curLoc = loc.clone();
			int count = 0;
			double max = 2*distance;
			
			@Override
			public void run() {
				for (int c = 0; c < effectsPerTick; c++) {
					if (count >= max) {
						SuitUtils.breakBlock(target.getBlock());
						this.cancel(); break;
					}
					curLoc.add(v);
					effect.accept(curLoc);
					count ++;
				}
			}
		}.runTaskTimer(plugin, 0, 2);
	}

	public static void createExplosion(Location loc, float power, boolean setFire , boolean breakBlock){
		loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), power, setFire, breakBlock);
	}
	
	public static void sleep(long msec) {
		try {
			Thread.sleep(msec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
	
	public static Set<Material> transparents = new HashSet<>(); 
	
	{
		for (Material m : Material.values()) {
			String name = m.name();
			if (m.isAir() || (m.isBlock() && !m.isSolid() && !m.isOccluding()) && 
					!name.startsWith("LEGACY") &&
					!name.startsWith("POTTED") &&
					!name.endsWith("POT") &&
					!name.startsWith("CHORUS") &&
					!name.endsWith("HEAD") && 
					!name.endsWith("SKULL")
					) {
				transparents.add(m);
			}
		}
		CustomSuitPlugin.logger.info("Added " +transparents.size()+ " transparents materials used for rayTracing");
	}
	
	public static Block getTargetBlock(Player player , int maxDistance){
		return player.getTargetBlock(transparents, maxDistance);
	}
	
	public static Location getTargetLoc(Player player, int maxDistance){
		return getTargetBlock(player, maxDistance).getLocation();
	}
	
	public static void wrongCommand(Player player, Command command) {
		String message = ChatColor.DARK_RED+"[Info]: "+ChatColor.RED+"Wrong Command!"+"\n"+
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
	
	public static void runAfter(@Nonnull Runnable task, long ticks){
		Bukkit.getScheduler().runTaskLater(plugin, task, ticks);
	}
	
	public static void teleportWithPassengers(Location to, Entity vehicle){
		if(anyNull(to, vehicle)) return;
			
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
	
	private static Set<EntityType> armables = Sets.newHashSet(EntityType.ARMOR_STAND, EntityType.ZOMBIE, EntityType.DROWNED, EntityType.SKELETON, EntityType.WITHER_SKELETON, EntityType.HUSK, EntityType.PIG_ZOMBIE);
	public static boolean isArmable(LivingEntity lentity) {
		return armables.contains(lentity.getType());
	}

	public static boolean inWater(Player player){	
		if(player.isSwimming()) return true;
		
		Block eye = player.getEyeLocation().getBlock();
		Block foot = player.getLocation().getBlock();
		Block waist = player.getLocation().add(0, 1, 0).getBlock();
		return SuitUtils.isWater(eye) && SuitUtils.isWater(waist) && SuitUtils.isWater(foot);
	}

	public static boolean isWater(Block block){
		Material m = block.getType();
 		return m==Material.WATER;
	}

	public static boolean isUnbreakable(Block hitblock) {
		return Values.unbreakable.contains(hitblock.getType());
	}

	public static void breakBlock(Block block) {
		if (!isUnbreakable(block)) {
			ParticleUtil.playBlockEffect(Particle.BLOCK_CRACK, block.getLocation(), 15, 5D, block.getBlockData());
			block.breakNaturally();
		}
	}
		
	private static Map<Material, Material> nextCracks = new HashMap<>();
	
	{
		addCracks(Material.ANVIL, Material.CHIPPED_ANVIL, Material.DAMAGED_ANVIL);
		addCracks(Material.STONE, Material.COBBLESTONE);
		addCracks(Material.STONE_BRICKS, Material.CRACKED_STONE_BRICKS);
	}
	
	private static void addCracks(@Nonnull Material...materials) {
		for(int i = 0; i<materials.length-1; i++) {
			nextCracks.put(materials[i], materials[i+1]);
		}
	}
	
	/**
	 * @param block
	 * @param damage out of 1.0
	 */
	public static boolean damageBlock(Block block, double impact) {
		Material m = block.getType();
		float hardness = m.getHardness();
		boolean toCrack =!MathUtil.gacha(100*hardness/(5*impact)); 
		if(toCrack) {
			if(nextCracks.containsKey(m)) {
				ParticleUtil.playBlockEffect(Particle.BLOCK_CRACK, block.getLocation(), 10, 6.8D, block.getBlockData());
				block.setType(nextCracks.get(m));
			}else{
				breakBlock(block);
			}
		}
		return toCrack;
	}
	
	
	
}
