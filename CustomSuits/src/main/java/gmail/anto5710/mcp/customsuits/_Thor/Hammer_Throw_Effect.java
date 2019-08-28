package gmail.anto5710.mcp.customsuits._Thor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.SuitWeapons;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.MathUtils;
import gmail.anto5710.mcp.customsuits.Utils.ParticleUtil;
import gmail.anto5710.mcp.customsuits.Utils.CustomEffects;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Hammer_Throw_Effect extends BukkitRunnable {


	static boolean isRunning = false;
	public static HashMap<Item, Player> listPlayer = new HashMap<>();
	public static ArrayList<Item>removed = new ArrayList<>();
	
	public Hammer_Throw_Effect() {}
	
	@Override
	public void run() throws IllegalStateException{
		Location loc;
		isRunning = true;
		
			Iterator<Item>iterator = listPlayer.keySet().iterator();
			if(listPlayer.isEmpty()){
				try{
					isRunning = false;
					ThorUtils.cancel(getTaskId());
				}catch(IllegalStateException e){
					
				}
			}
			while (iterator.hasNext()) {
				Item item = iterator.next();
				loc = item.getLocation();
				Player player = listPlayer.get(item);
				
				item.setFireTicks(0);

				item.setPickupDelay(1);
				java.util.List<Entity> list;
				
				ParticleUtil.playEffect(Values.HammerDefaultEffect, loc, 55, 4);
				list = SuitWeapons.findEntity(loc, player, 1);
		
				ThorUtils.damage(list, Hammer.HammerDeafultDamage, player);
				
				if (ThorUtils.isOnGround(item)|| item.isDead()) {
				
					CustomEffects.play_Hammer_Hit_Ground(item);
					item.getWorld().strikeLightning(item.getLocation());
					player.getInventory().addItem(item.getItemStack());
					item.remove();

					SuitUtils.createExplosion(loc, 6F, false, false);
					Explosion_Effect(loc);
					
					removed.add(item);					
					iterator.remove();				
			}
		}
			for(Item item: removed){
				listPlayer.remove(item);
			}
	}
	/**
	 * Plays Explosion Effect in targeted Location
	 * @param location Center
	 */
	public static void Explosion_Effect(Location loc){
			boolean hollow = false;
			boolean sphere = false;
			int r = 3;
		        int cx = loc.getBlockX();
		        int cy = loc.getBlockY();
		        int cz = loc.getBlockZ();
		        for (int x = cx - r; x <= cx +r; x++){
		            for (int z = cz - r; z <= cz +r; z++){
		                for (int y = (sphere ? cy - r : cy); y < (sphere ? cy + r : cy + 2); y++) {
		                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
		                    if (dist < r*r && !(hollow && dist < (r-1)*(r-1))) {
		                    	
		                        Location l = new Location(loc.getWorld(), x, y- 2, z);
		                        float sx = (float) -1 + (float) (Math.random() * ((1 - -1) + 1));
		            	        float sy = (float) (2/l.distance(loc)+MathUtils.randomRadius(0.025));
		            	        float sz = (float) -0.3 + (float)(Math.random() * ((0.3 - -0.3) + 1));
		            	        spawnFallingBlock(l.getBlock() , new Vector(sx*0.4, sy*0.4, sz));
					}
				}
			}
		}

	}
	/**
	 * Spawns Falling block with Velocity
	 * @param block Target Block
	 * @param vector Vector to add
	 */
	private static void spawnFallingBlock(Block block, Vector vector) {
		World world = block.getWorld();
		
		Location loc = block.getLocation();
//		int id = block.getType().getId();
//		byte data = block.getData();
		
		block.breakNaturally();
		
		FallingBlock fallingBlock =world.spawnFallingBlock(loc, block.getBlockData());
		
		fallingBlock.setVelocity(vector);
		
	}

	/**
	 * Return is this Task currently running
	 * @return is this Task currently running
	 */
	public static boolean isRunning(){
		return isRunning;
		
	}
	
}
