package gmail.anto5710.mcp.customsuits._Thor;

import java.awt.List;
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

	private Location loc ;
	private final Player player;
	private final Item item;
	private final boolean isTP;
	 static int id;
	 

    
	

	public Repeat(JavaPlugin plugin, Player player , Item item, boolean isTP) {
		this.plugin = plugin;
		this.item = item;
		this.player = player;
		
		this.isTP = isTP;
		
		
	}

	@Override
	public void run() {
		this.loc = item.getLocation();
		this.id= getTaskId();
	
		if((item.getFireTicks()==-1||item.getFireTicks()==0)==false){
			player.getInventory().addItem(item.getItemStack());
			item.remove();
			cancel(getTaskId());
		}
		

		item.setPickupDelay(10);
		java.util.List<Entity> list;
		if (!isTP) {
			SuitUtils.playEffect(loc, Effect.LAVA_POP, 55, 0, 4);
			list = WeaponListner.findEntity(loc, player, 4);
			damage(list, 40,player);
		} else{
			SuitUtils.playEffect(loc, Effect.PORTAL, 55, 0, 4);
		}
		if (item.isOnGround()) {
			if (!isTP) {
				SuitUtils.playEffect(loc, Effect.ENDER_SIGNAL, 30, 0, 5);
				item.getWorld().strikeLightning(item.getLocation());
				player.getInventory().addItem(item.getItemStack());
				item.remove();

				list = WeaponListner.findEntity(loc, player, 4);

				damage(list, 80,player);
				SuitUtils.createExplosion(loc, 6F, false, true);
				cancel(getTaskId());
			} else {
				player.teleport(loc);
				item.getWorld().strikeLightning(item.getLocation());
				player.getInventory().addItem(item.getItemStack());
				item.remove();
				for (int i = 0; i < 20; i++) {
					player.getWorld().strikeLightning(loc);
				}
				cancel(getTaskId());
			}
			
			
		}
		
	}

	public static void cancel(int taskId) {
		
		BukkitScheduler scheduler = Bukkit.getScheduler();
		
		
			scheduler.cancelTask(taskId);
		
		
		
	}
	

	public static void damage(java.util.List<Entity> list, double damage, Player player) {
		if(list.contains(player)){
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
