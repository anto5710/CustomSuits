package gmail.anto5710.mcp.customsuits._Thor;

import java.util.ArrayList;
import java.util.List;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.WeaponListner;
import gmail.anto5710.mcp.customsuits.Setting.Values;
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
			ThorUtils.cancel(getTaskId());
		}
		
	
	}

	public static void Lightning(Entity entity, Location from, Effect effect) {
		Location entitylocation = entity.getLocation();
		Location spawnLocation = entitylocation.clone();
		spawnLocation.setY(spawnLocation.getY()+50);
			FallingBlock block = entity.getWorld().spawnFallingBlock(spawnLocation, Material.SOUL_SAND, (byte) 0);
			
			fallingBlocks.add(block);
			SuitUtils.playEffect(entitylocation, effect, 2, 0, 5);
			ThorUtils.spreadItems(entitylocation, new ItemStack(Material.BONE) );
		
	
		ThorUtils.strikeLightning(entitylocation, Hammer.thor, 1, 2, Values.Thunder_Strike_Damage);
		
		if(entity instanceof LivingEntity){
			ThorUtils.tesla(entity);
		}
		
	}
	private void damage(List<Entity> list) {
		for(Entity entity : list){
		
			Location loc = BaseLocation.clone();
			WeaponUtils.setRandomLoc(loc, Values.Thunder_Strike_Radius);
			Lightning(entity, loc, Values.Thunder_Strike_Effect);
		}
		
	}
	public static void Start(Player player) {
		BaseLocation = player.getLocation();
		int Y = BaseLocation.getBlockY()+200;
		int count = 0;
			for(int y = BaseLocation.getBlockY() ;Y>=y ; y+=1 ){
				count ++;
				BaseLocation.setY(y);
						            SuitUtils.playEffect(BaseLocation, Values.Thunder_Strike_Spawn_Effect, 40, 0, 5);
						            
						          if(count%40==0){
						        	  Hammer.thor.playSound(player.getLocation(), Values.Thunder_Strike_Spawn_Sound,10.0f, 5.0F);
						          }
						        }
			
						
				           
				       
				           
				        
		
		if(!isStriking){
			isStriking = true;
			BaseLocation.getWorld().setStorm(true);
			BaseLocation.getWorld().setThundering(true);
			BukkitTask task = new Thunder_Strike(plugin).runTaskTimer(plugin, 0,20);
			
		}
		
	}
}
