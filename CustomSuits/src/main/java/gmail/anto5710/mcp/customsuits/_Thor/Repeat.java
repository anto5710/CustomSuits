package gmail.anto5710.mcp.customsuits._Thor;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.WeaponListner;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;

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
import org.bukkit.entity.Horse.Variant;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.omg.CosNaming.IstringHelper;

public class Repeat extends BukkitRunnable {

	private final JavaPlugin plugin;

	static int taskID = 0;

	public static HashMap<Item, Player> listPlayer = new HashMap<>();
	public static ArrayList<Item>removed = new ArrayList<>();
	
	public Repeat(JavaPlugin plugin) {
		this.plugin = plugin;

	}
	
	@Override
	
	public void run() throws IllegalStateException{
		Location loc;
		
		
		
		
			Iterator<Item>iterator = listPlayer.keySet().iterator();
			if(listPlayer.isEmpty()){
				try{
					
					ThorUtils.cancel(getTaskId());
				}catch(IllegalStateException e){
					
				}
			}
			while (iterator.hasNext()) {
				Item item = iterator.next();
				loc = item.getLocation();
				this.taskID = getTaskId();
				Player player = listPlayer.get(item);

				item.setFireTicks(0);

				item.setPickupDelay(10);
				java.util.List<Entity> list;
				
//					SuitUtils.playEffect(loc, Values.HammerDefaultEffect, 55, 0, 4);
					list = WeaponListner.findEntity(loc, player, 3);
					ThorUtils.damage(list, Hammer.HammerDeafultDamage*1.5, player);
				
				if (ThorUtils.isOnGround(item)|| item.isDead()) {
				
//						SuitUtils.playEffect(loc, Values.HammerHitGround, 30, 0, 5);
						item.getWorld().strikeLightning(item.getLocation());
						player.getInventory().addItem(item.getItemStack());
						item.remove();

						list = WeaponListner.findEntity(loc, player, 3);

						ThorUtils.damage(list, Hammer.HammerDeafultDamage*2, player);
						SuitUtils.createExplosion(loc, 6F, false, true);
					
						iterator.remove();
						

				}
			
				
			}
				
		
		
	}

	public static boolean isRunning(int taskID){
		BukkitScheduler scheduler = Bukkit.getScheduler();
		
		if(scheduler.isCurrentlyRunning(taskID)){
			return true;
		}
		return false;
		
	}
	
	
	
		
		
	



	

	
}
