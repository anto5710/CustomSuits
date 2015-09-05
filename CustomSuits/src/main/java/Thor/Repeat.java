package Thor;

import java.awt.List;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.SuitUtils;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.WeaponListner;

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
import org.omg.CosNaming.IstringHelper;

public class Repeat extends BukkitRunnable {

	private final JavaPlugin plugin;
	private final Player player;

	private Location loc;
	private final Item item;

	boolean isTP;

	public Repeat(JavaPlugin plugin, Player player, Item item, boolean isTP) {
		this.plugin = plugin;
		this.player = player;
		this.loc = item.getLocation();

		this.item = item;
		this.isTP = isTP;
	}

	@Override
	public void run() {
			
		if((item.getFireTicks()==-1||item.getFireTicks()==0)==false){
			player.getInventory().addItem(item.getItemStack());
			item.remove();
			cancel();
		}
		
		this.loc = item.getLocation();

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
				cancel();
			} else {
				player.teleport(loc);
				item.getWorld().strikeLightning(item.getLocation());
				player.getInventory().addItem(item.getItemStack());
				item.remove();
				for (int i = 0; i < 20; i++) {
					player.getWorld().strikeLightning(loc);
				}
				cancel();
			}
			
			
		}
		
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