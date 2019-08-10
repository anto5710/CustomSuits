package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import java.util.ArrayList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import gmail.anto5710.mcp.customsuits.CustomSuits.dao.SpawningDao;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Target extends BukkitRunnable{
	
	
	CustomSuitPlugin plugin;
	static boolean isRunning = false;
	public Thread thread;
	public Target(CustomSuitPlugin plugin){
		this.plugin = plugin;
		this.thread = new Thread(this,"T-SUIT_TARGET");
	}
	public void start(){
		this.thread.start();
	}
	
	public void run() throws IllegalStateException {
		int taskID = 0;
		try {
			taskID = this.getTaskId();
		} catch (IllegalStateException e) {
			System.out.println("EMPTY TARGET");
//			e.printStackTrace();
		}

		if (SpawningDao.spawnMap.isEmpty()) {
			isRunning = false;
			ThorUtils.cancel(taskID);
		} else {
			isRunning = true;
			Iterator<Player> iterator = getPlayers(SpawningDao.spawnMap);
			while (iterator.hasNext()) {
				Player player = iterator.next();
				SuitSettings hdle = CustomSuitPlugin.hdle(player);
				hdle.removeDeadTarget();
				target(player, hdle.getCurrentTarget(), false);
			}
		}
	}
	
	public static void target(Player player, LivingEntity target, boolean sendMessage) {
		boolean isPlayed = false;
		Collection<Mob> list = player.getWorld().getEntitiesByClass(Mob.class);

		String name = "";
		boolean engage = target != null;
		if (engage) {
			name = target instanceof Player ?  ((Player) target).getName() : target.getType().getEntityClass().getSimpleName();			
		}
		for (Entity e : list) {
			if (CustomSuitPlugin.dao.isCreatedBy(e, player)) {
				((Mob) e).setTarget(target);
				if (e instanceof PigZombie) {
					PigZombie pg = (PigZombie) e;
					if (engage) {
						pg.setAngry(true);
						pg.setAnger(100000);
					} else {
						pg.setAngry(false);
						pg.setAnger(0);
					}
				}
				isPlayed = true;
			}
		}
		if (sendMessage) {
			if (!isPlayed) {
				player.sendMessage(ChatColor.BLUE + "[Info]: " + ChatColor.AQUA + "No such entity");
				player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 6.0F, 6.0F);
			} else {
				player.sendMessage(ChatColor.BLUE + "[Info]: " + ChatColor.AQUA + "Target : " + ChatColor.DARK_AQUA + name);
			}
		}
	}
	
	private Iterator<Player> getPlayers(Map<String, String> Map) {
		List<Player> list = new ArrayList<Player>();
		Iterator<String> iterator = Map.values().iterator();
		while (iterator.hasNext()) {
			String name = iterator.next();
			Player player = Bukkit.getServer().getPlayer(name);
			if (list.isEmpty() && player != null) {
				list.add(player);
			} else if (player != null && !list.contains(player)) {
				list.add(player);
			}
		}

		return list.iterator();
	}
}
