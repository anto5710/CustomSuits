package gmail.anto5710.mcp.customsuits._Thor;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.SuitUtils;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.WeaponListner;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.omg.CosNaming.IstringHelper;

public class Repeat extends BukkitRunnable {

	private final JavaPlugin plugin;

	static int taskID = 0;

	static HashMap<Item, Player> listPlayer = new HashMap<>();
	static HashMap<Item, Boolean> listTeleport = new HashMap<>();

	public Repeat(JavaPlugin plugin) {
		this.plugin = plugin;

	}
	
	@Override
	
	public void run() {
		Location loc;
		
		
		
		if (listPlayer.size() == 1) {
			ArrayList<Item> list = new ArrayList<>();
			list.addAll(listPlayer.keySet());
			Item item = list.get(0);

			loc = item.getLocation();
			this.taskID = getTaskId();
			Player player = listPlayer.get(item);
			boolean isTP = listTeleport.get(item);
			Run(item, loc, taskID, player, isTP);
			
		} else if(listPlayer.size()>1){
			for (Item item : listPlayer.keySet()) {
				loc = item.getLocation();
				this.taskID = getTaskId();
				Player player = listPlayer.get(item);
				boolean isTP = listTeleport.get(item);

				Run(item, loc, taskID, player, isTP);
			}
		}
		
	}
	/**
	 * 
	 * @param taskID - TaskId Of That Task
	 * @return - Return If That Task is Running
	 */
	public static boolean isRunning(int taskID){
		BukkitScheduler scheduler = Bukkit.getScheduler();
		
		if(scheduler.isCurrentlyRunning(taskID)){
			return true;
		}
		return false;
		
	}
	/**
	 * 
	 * @param item
	 * @param loc
	 * @param TaskID
	 * @param player
	 * @param isTeleport
	 */
	private void Run(Item item, Location loc, int TaskID, Player player,
			boolean isTeleport) {
		if (isFire(item)) {
			player.getInventory().addItem(item.getItemStack());
			item.remove();
			remove(item);

		}

		item.setPickupDelay(10);
		java.util.List<Entity> list;
		if (!isTeleport) {
			SuitUtils.playEffect(loc, Effect.LAVA_POP, 55, 0, 4);
			list = WeaponListner.findEntity(loc, player, 4);
			damage(list, 40, player);
		} else {
			SuitUtils.playEffect(loc, Effect.PORTAL, 55, 0, 4);
		}
		if (isOnGround(item)) {
			if (!isTeleport) {
				SuitUtils.playEffect(loc, Effect.ENDER_SIGNAL, 30, 0, 5);
				item.getWorld().strikeLightning(item.getLocation());
				player.getInventory().addItem(item.getItemStack());
				item.remove();

				list = WeaponListner.findEntity(loc, player, 4);

				damage(list, 80, player);
				SuitUtils.createExplosion(loc, 6F, false, true);
				remove(item);
			} else {
				player.teleport(loc);
				item.getWorld().strikeLightning(item.getLocation());
				player.getInventory().addItem(item.getItemStack());
				item.remove();
				Hammer.strikeLightning(loc, player, 20,1.5,Hammer
						.HammerDeafultDamage);
				remove(item);
			}

		}
	}

	private boolean isFire(Item item) {
	if	((item.getFireTicks() == -1 || item.getFireTicks() == 0) == false){
		return false;
	}
		return false;
	}

	private boolean isOnGround(Item item) {
		if(item.isOnGround()){
			return true;
		}
		Location location = item.getLocation();
		double Y = location.getY()-1;
		
			Block block = new Location(location.getWorld(), location.getX(), Y, location.getZ()).getBlock();
			Material matareial = block.getType();
			if(matareial!=Material.AIR&&matareial !=Material.WATER){
				return true;
			}
		
		
		return false;
	}

	public static void remove(Item item) {
		
		if (listPlayer.containsKey(item)) {
			listPlayer.remove(item);
		}
		if (listTeleport.containsKey(item)) {
			listTeleport.remove(item);
		}

	}

	public static void cancel(int taskId) {

		BukkitScheduler scheduler = Bukkit.getScheduler();

		scheduler.cancelTask(taskId);

	}

	public static void damage(java.util.List<Entity> list, double damage,
			Player player) {
		if (list.contains(player)) {
			list.remove(player);
		}
		for (Entity e : list) {
			if (e instanceof Damageable) {
				((Damageable) e).damage(damage);

			}
		}

	}

	// What you want to schedule goes here

}
