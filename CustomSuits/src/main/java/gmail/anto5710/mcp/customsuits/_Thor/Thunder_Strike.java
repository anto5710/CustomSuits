package gmail.anto5710.mcp.customsuits._Thor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gmail.anto5710.mcp.customsuits.CustomSuits.FireworkPlay;
import gmail.anto5710.mcp.customsuits.CustomSuits.PlayEffect;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.WeaponListner;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.ManUtils;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;
import gmail.anto5710.mcp.customsuits.Utils.WeaponUtils;
import net.minecraft.server.v1_8_R2.EnumParticle;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftWither;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
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
	static Wither wither;
	static Player thor ;
	public Thunder_Strike (CustomSuitPlugin plugin , Wither wither , Player player){
			Thunder_Strike.plugin = plugin;
			Thunder_Strike.wither = wither;
			Thunder_Strike.thor = player;
	}
	@Override
	public void run(){
		BaseLocation = wither.getLocation();
		if(!thor.isOnline()||thor.isDead() || wither.isDead() ){
			isStriking = false;
			BaseLocation.getWorld().setStorm(false);
			BaseLocation.getWorld().setThundering(false);
			wither.damage(100000000000000000D);
			PlayEffect.play_Thunder_Strike_End(wither);
			count = 0;
			ThorUtils.cancel(getTaskId());
		}
		wither.setTarget(null);
		wither.setNoDamageTicks(1);
		
		isStriking = true;
		if(wither.isEmpty()){
			if(thor.isEmpty()){
				thor.teleport(wither.getLocation());
				wither.setPassenger(thor);
			}
		}
		if(count%20==0||count==0){
		run(wither);
		}
		count ++;
		FireworkEffect effect = SuitUtils.getEffect(Color.MAROON, Type.STAR);
		FireworkPlay.spawn(BaseLocation, effect , thor);
	
		if(count>=600){
			isStriking = false;
			BaseLocation.getWorld().setStorm(false);
			BaseLocation.getWorld().setThundering(false);
			wither.damage(100000000000000000D);
			count = 0;
			PlayEffect.play_Thunder_Strike_End(wither);
			ThorUtils.cancel(getTaskId());
		}

	}

	private void run(Wither wither) {
		List<Entity>list =	ThorUtils.findEntity(BaseLocation,  Values.Thunder_Strike_Radius , thor);
		list.remove(wither);
		damage(list);
		
	}
	public static void launchLightningMissile(Entity entity, Location from) {
		Location entitylocation = entity.getLocation();
		from.setDirection(entitylocation.toVector().subtract(from.toVector()));
		SuitUtils.LineParticle(entitylocation, from, thor, EnumParticle.FLAME , 3, 2, Values.LightningMissile,2,false, true, false , 5);
		
		entity.getWorld().strikeLightning(entitylocation);
		
	}
	
	private void damage(List<Entity> list) {
		for(Entity entity : list){
			launchLightningMissile(entity, BaseLocation);
			
		}
	}
}
	

