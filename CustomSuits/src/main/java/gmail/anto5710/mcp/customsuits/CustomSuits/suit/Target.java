package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import java.util.Collection;

import gmail.anto5710.mcp.customsuits.CustomSuits.dao.SpawningDao;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class Target implements Runnable{
	CustomSuitPlugin plugin;
	private BukkitTask task;
	
	public Target(CustomSuitPlugin plugin){
		this.plugin = plugin;
	}
	
	public void awaken(){
		task = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 1);
	}
	
	public void run(){
		if (SpawningDao.spawnMap.isEmpty()) {
			stop();		
		} else {
			for(Player player : CustomSuitPlugin.dao.getPlayers()){
				SuitSettings hdle = CustomSuitPlugin.handle(player);
				hdle.removeDeadTargets();
				target(player, hdle.getCurrentTarget(), false);
			}
		}
	}
	
	private void stop(){
		task.cancel();
		task = null;
	}
	
	public boolean isRunning(){
		return task != null;
	}
	
	public static void target(Player player, LivingEntity target, boolean sendMessage) {
		boolean isPlayed = false;
		Collection<Mob> list = player.getWorld().getEntitiesByClass(Mob.class);

		boolean engage = target != null;
		String targetName = engage ? getTargetName(target) : "";

		for (Mob e : list) {
			if (CustomSuitPlugin.dao.isCreatedBy(e, player)) {
				target(e, target, engage);
				isPlayed = true;
			}
		}

		if (sendMessage) {
			if (!isPlayed) {
				player.sendMessage(ChatColor.BLUE + "[Info]: " + ChatColor.AQUA + "No such entity");
				player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 6.0F, 6.0F);
			} else {
				player.sendMessage(ChatColor.BLUE + "[Info]: " + ChatColor.AQUA + "Target : " + ChatColor.DARK_AQUA + targetName);
			}
		}
	}
	
	private static String getTargetName(LivingEntity target){
		return target instanceof Player ?  ((Player) target).getName() : target.getType().getEntityClass().getSimpleName();
	}
	
	private static void target(Mob mob, LivingEntity target, boolean engage){
		mob.setTarget(target);
		if (mob instanceof PigZombie) {
			PigZombie pg = (PigZombie) mob;
			if (engage) {
				pg.setAngry(true);
				pg.setAnger(100000);
			} else {
				pg.setAngry(false);
				pg.setAnger(0);
			}
		}
	}
}
