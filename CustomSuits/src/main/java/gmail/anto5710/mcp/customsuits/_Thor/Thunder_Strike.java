package gmail.anto5710.mcp.customsuits._Thor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.WeaponListner;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.ManUtils;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;
import gmail.anto5710.mcp.customsuits.Utils.WeaponUtils;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class Thunder_Strike extends BukkitRunnable{
	static CustomSuitPlugin plugin;
	static boolean isStriking = false;
	static Location BaseLocation ;
	static ArrayList<FallingBlock> fallingBlocks  = new ArrayList<>();
	static int count = 0;
	public Thunder_Strike (CustomSuitPlugin plugin){
			this.plugin = plugin;
	}
	public void run() throws IllegalStateException{
		if(Hammer.thor == null){
			isStriking = false;
			BaseLocation.getWorld().setStorm(false);
			BaseLocation.getWorld().setThundering(false);
			count = 0;
			ThorUtils.cancel(getTaskId());
		}
		isStriking = true;
		
		List<Entity>list =	ThorUtils.findEntity(BaseLocation, Values.Thunder_Strike_Radius);
		if(list.contains(Hammer.thor)){
			list.remove(Hammer.thor);
		}
		damage(list);
		
		count ++;
	
		if(count>=Values.Thunder_Strike_Time){
			isStriking = false;
			BaseLocation.getWorld().setStorm(false);
			BaseLocation.getWorld().setThundering(false);
			count = 0;
			ThorUtils.cancel(getTaskId());
		}
		
	
	}

	public static void Lightning(Entity entity, Location from, Effect effect) {
		Location entitylocation = entity.getLocation();
		Location spawnLocation = entitylocation.clone();
		spawnLocation.setY(spawnLocation.getY()+50);
		
		spawnFallingblocks(spawnLocation);
//			SuitUtils.playEffect(entitylocation, effect, 2, 0, 5);
//			
		
	
		ThorUtils.strikeLightning(entitylocation, Hammer.thor, 1, 2, Values.Thunder_Strike_Damage);
		
		if(entity instanceof LivingEntity){
			ThorUtils.tesla(entity);
		}
		
	}
	private static void spawnFallingblocks(Location spawnLocation ) {
		List<Location>list = ManUtils.circle(spawnLocation, 2, 2, false, true, 0);
		for(Location location : list){
			FallingBlock block = location.getWorld().spawnFallingBlock(location, Material.SOUL_SAND, (byte) 0);
			fallingBlocks.add(block);
		}
//		FallingBlock block = spawnLocation.getWorld().spawnFallingBlock(spawnLocation, Material.SOUL_SAND, (byte) 0);
//		spawnLocation.add(0, 1, 0);
//		FallingBlock block2 = spawnLocation.getWorld().spawnFallingBlock(spawnLocation, Material.SOUL_SAND, (byte) 0);
//		spawnLocation.add(0, -2, 0);
//		FallingBlock block3 = spawnLocation.getWorld().spawnFallingBlock(spawnLocation, Material.SOUL_SAND, (byte) 0);
//		spawnLocation.add(-1 , 1 ,0 );
//		FallingBlock block4 = spawnLocation.getWorld().spawnFallingBlock(spawnLocation, Material.SOUL_SAND, (byte) 0);
//		spawnLocation.add(2, 0, 0);
//		FallingBlock block5 = spawnLocation.getWorld().spawnFallingBlock(spawnLocation, Material.SOUL_SAND, (byte) 0);
//		spawnLocation.add(-1, 0, -1);
//		FallingBlock block6 = spawnLocation.getWorld().spawnFallingBlock(spawnLocation, Material.SOUL_SAND, (byte) 0);
//		spawnLocation.add(0, 0, 2);
//		FallingBlock block7 = spawnLocation.getWorld().spawnFallingBlock(spawnLocation, Material.SOUL_SAND, (byte) 0);
//		
//		fallingBlocks.addAll(Arrays.asList(block , block2 , block3 , block4 , block5 , block6 , block7));
//		
	}
	private void damage(List<Entity> list) {
		for(Entity entity : list){
		
			Location loc = BaseLocation.clone();
			WeaponUtils.setRandomLoc(loc, Values.Thunder_Strike_Radius);
//			Lightning(entity, loc, Values.Thunder_Strike_Effect);
		}
		
	}
	
	public static  boolean playEffect_Spawn(Location baseLocation , Player player , int maxY) {
		int Count = 0;
		for(int y = BaseLocation.getBlockY() ;maxY>=y ; y+=1 ){
			Count++;
			BaseLocation.setY(y);
//					            SuitUtils.playEffect(BaseLocation, Values.Thunder_Strike_Spawn_Effect, 40, 0, 5);
					            
					          if(Count%40==0){
					        	  Hammer.thor.playSound(player.getLocation(), Values.Thunder_Strike_Spawn_Sound,10.0f, 5.0F);
					          }
	
		}
		return true;
	}
	
	
}
