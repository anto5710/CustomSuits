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
	public Thunder_Strike (CustomSuitPlugin plugin , Wither wither){
			this.plugin = plugin;
			this.wither = wither;
	}
	@Override
	public void run(){
		if(Hammer.thor == null || wither.isDead() ){
			isStriking = false;
			BaseLocation.getWorld().setStorm(false);
			BaseLocation.getWorld().setThundering(false);
			wither.damage(100000000D);
			PlayEffect.play_Thunder_Strike_End(wither);
			count = 0;
			ThorUtils.cancel(getTaskId());
		}
		wither.setTarget(null);
		wither.setNoDamageTicks(1);
		BaseLocation = wither.getLocation();
		isStriking = true;
		if(wither.isEmpty()){
			if(Hammer.thor.isEmpty()){
				Hammer.thor.teleport(wither.getLocation());
				wither.setPassenger(Hammer.thor);
			}
		}
		if(count%20==0||count==0){
		List<Entity>list =	ThorUtils.findEntity(BaseLocation,  Values.Thunder_Strike_Radius , Hammer.thor);
		list.remove(wither);
		damage(list);
		}
		
		count ++;
		FireworkEffect effect = FireworkEffect.builder().with(Type.BURST).withColor(Color.RED , Color.MAROON ).withFade(Color.GRAY , Color.BLACK  ,Color.WHITE , Color.SILVER).withFlicker().withTrail().trail(true).build();
		
		FireworkPlay.spawn(BaseLocation, effect , Hammer.thor);
	
		if(count>=400){
			isStriking = false;
			BaseLocation.getWorld().setStorm(false);
			BaseLocation.getWorld().setThundering(false);
			count = 0;
			wither.damage(100000000D);
			PlayEffect.play_Thunder_Strike_End(wither);
//			FireworkEffect effect = SuitUtils.getRandomEffect();
			
//			FireworkPlay.spawn(BaseLocation, effect , Hammer.thor);
			ThorUtils.cancel(getTaskId());
		}
		
	
	}

	public static void Lightning(Entity entity, Location from, EnumParticle effect) {
		Location entitylocation = entity.getLocation();
SuitUtils.LineParticle(entitylocation, BaseLocation, Hammer.thor, EnumParticle.VILLAGER_ANGRY, 3, 0, 2, Values.LightningMissile,2,false, false);
		
		SuitUtils.createExplosion(entitylocation, Values.HammerMissileExplosion_Power, false, true);
//			SuitUtils.playEffect(entitylocation, effect, 2, 0, 5);
//			
		
	
	entity.getWorld().strikeLightning(entitylocation);
		
		
	}
//	private static void spawnFallingblocks(Location spawnLocation ) {
//		List<Location>list = ManUtils.circle(spawnLocation, 2, 2, false, true, 0);
//		for(Location location : list){
//			FallingBlock block = location.getWorld().spawnFallingBlock(location, Material.SOUL_SAND, (byte) 0);
//			if(fallingBlocks.isEmpty()){
//				new Meteo(plugin).runTaskTimer(plugin, 0, 1);
//			}
//			fallingBlocks.add(block);
//		}
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
	
	private void damage(List<Entity> list) {
		for(Entity entity : list){
			Lightning(entity, BaseLocation, EnumParticle.VILLAGER_ANGRY);
			
		}
		}
//		
	}
	
	

