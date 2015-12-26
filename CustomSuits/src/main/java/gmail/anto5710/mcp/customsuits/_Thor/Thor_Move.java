package gmail.anto5710.mcp.customsuits._Thor;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.PlayerEffect;
import gmail.anto5710.mcp.customsuits.Setting.PotionEffects;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;
import net.minecraft.server.v1_8_R2.EnumParticle;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Thor_Move extends BukkitRunnable{
	
	   
		CustomSuitPlugin plugin;
		static Location location;
		static double Add_Y_Offset = 0D;
		static boolean isUp = false;
		static Player player;
		static double add_Horizonal = 0;
		static double radius = 1;
		public static boolean isRunning = false;
		public Thor_Move(CustomSuitPlugin plugin ,Player player){
			Thor_Move.player = player;
			this.plugin = plugin;
		
			 Add_Y_Offset = 0D;
			 isUp = false;
			 add_Horizonal = 0;
			 radius = 1;
			 isRunning = false;
			 AddThorPotion_Effects();
		}
		@Override
		public void run() throws IllegalStateException{
			location = player.getLocation();
			isRunning = true;
			if(!Hammer.Thor(player)){
				try {
					RemoveThorPotion_Effects();
					isRunning = false;
					this.cancel();
					
				} catch (IllegalStateException e) {
				}
			}
			if(!player.isOnline()){
				RemoveThorPotion_Effects();
				isRunning = false;
				this.cancel();
				
			}
					Location loc = location.clone();
					
						add_Horizonal+=0.2;
						
						double y = loc.getY()+Add_Y_Offset;
						double x = Math.sin(add_Horizonal*radius);
						double z = Math.cos(add_Horizonal*radius);
						loc.add(x, 0, z);
						loc.setY(y);
							
						SuitUtils.playEffect(loc, EnumParticle.FLAME,5, 0, 0);
						Location locii = location.clone();
						locii.add(-1*x, 0, -1*z);
						locii.setY(y);
						SuitUtils.playEffect(locii, EnumParticle.FLAME,5, 0, 0);
						if(isUp){
							Add_Y_Offset+=0.1;
						}else{
							Add_Y_Offset-=0.1;
						}
						if(Add_Y_Offset>=2){
							isUp = false;
						}
						if(Add_Y_Offset<=0){
							isUp = true;
						}
						loc = location	;
	}
		private void AddThorPotion_Effects() {
			PlayerEffect.addpotion(PotionEffects.Thor_FAST_DIGGING, player);
			PlayerEffect.addpotion(PotionEffects.Thor_FIRE_RESISTANCE, player);
			PlayerEffect.addpotion(PotionEffects.Thor_HEALTH_BOOST, player);
			PlayerEffect.addpotion(PotionEffects.Thor_INCREASE_DAMAGE, player);
			PlayerEffect.addpotion(PotionEffects.Thor_JUMP, player);
			PlayerEffect.addpotion(PotionEffects.Thor_REGENERATION, player);
			PlayerEffect.addpotion(PotionEffects.Thor_SPEED, player);
			PlayerEffect.addpotion(PotionEffects.Thor_WATER_BREATHING, player);		
			
		}
			
		
		private void RemoveThorPotion_Effects() {
			ThorUtils.removePotionEffect(PotionEffects.Thor_FAST_DIGGING, player);
			ThorUtils.removePotionEffect(PotionEffects.Thor_FIRE_RESISTANCE, player);
			ThorUtils.removePotionEffect(PotionEffects.Thor_HEALTH_BOOST, player);
			ThorUtils.removePotionEffect(PotionEffects.Thor_INCREASE_DAMAGE, player);
			ThorUtils.removePotionEffect(PotionEffects.Thor_JUMP, player);
			ThorUtils.removePotionEffect(PotionEffects.Thor_REGENERATION, player);
			ThorUtils.removePotionEffect(PotionEffects.Thor_SPEED, player);
			ThorUtils.removePotionEffect(PotionEffects.Thor_WATER_BREATHING, player);
		}
			
		
	

}
