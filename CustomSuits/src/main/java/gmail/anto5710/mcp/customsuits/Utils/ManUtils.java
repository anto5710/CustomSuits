package gmail.anto5710.mcp.customsuits.Utils;

import java.awt.List;

import java.util.ArrayList;
import java.util.Collection;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.PlayerEffect;
import gmail.anto5710.mcp.customsuits.Man.RepeatMan;
import gmail.anto5710.mcp.customsuits.Setting.PotionEffects;
import gmail.anto5710.mcp.customsuits.Setting.Values;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import com.google.common.collect.Sets.SetView;



public class ManUtils  {
	static CustomSuitPlugin plugin;
	static RepeatMan repeatman;
	public ManUtils(CustomSuitPlugin plugin){
		this.plugin = plugin;
	}
	
	public static ArrayList<Player> HiddenPlayers = new ArrayList<>();

	public static boolean Man(Player player){
		int checkCount = 0;
		if(SuitUtils.CheckItem(CustomSuitPlugin.Chestplate_Man, player.getEquipment().getChestplate())){
			checkCount++;
		}
		if(SuitUtils.CheckItem(CustomSuitPlugin.Leggings_Man, player.getEquipment().getLeggings())){
			checkCount++;
		}
		if(SuitUtils.CheckItem(CustomSuitPlugin.Boots_Man, player.getEquipment().getBoots())){
			checkCount++;
		}
		if(checkCount>=3){
			return true;
		}
		return false;	
	}
	public static void spawnFallingBlock (Location loc , Block block){
		  FallingBlock fallingblock = loc.getWorld()
	                .spawnFallingBlock(loc, block.getType(), block.getData());
	       
	        block.setType(Material.AIR);
	       
	        float x = (float) -1 + (float) (Math.random() * ((1 - -1) + 1));
	        float y = 1;
	        float z = (float) -0.3 + (float)(Math.random() * ((0.3 - -0.3) + 1));
	       
	        
	        fallingblock.setVelocity(new Vector(x, y, z));
	        
	}
	
	public static void changeVisiblility(Player player, boolean CanSee , boolean Toggle) {
		
		if	(Toggle){
		if (HiddenPlayers.contains(player)) {
			setVisible(player);

		}
		else	if (!(HiddenPlayers.contains(player))) {
			if(player.getFoodLevel()>=Values.ManInvisibleHunger){
			setInvisible(player);
			}
		
		}
		}
		else{
			if(CanSee){
				setVisible(player);
			}else{
				if(player.getFoodLevel()>=Values.ManInvisibleHunger){
				setInvisible(player);
				}
				
			}
		}

	}
	public static double Random(double a){
		double b = a / 2;
		double random = (Math.random() * a) - b;
		return random;
	}
	public static void setInvisible(Player player) throws NullPointerException{
		
			
			SuitUtils.playEffect(player.getLocation(), Values.ManInvisibleEffect, 150, 0, 100);
		
		player.playSound(player.getLocation(), Values.ManInvisibleSound, 16.0F, 16.0F);
		PlayerEffect.addpotion(PotionEffects.Man_Invisiblility , player);
		PlayerEffect.addpotion(PotionEffects.Man_Invisible_SPEED , player);
		for (Player playerOnline : player.getServer().getOnlinePlayers()) {
			playerOnline.hidePlayer(player);
			

		}

		RepeatMan.addPlayer(player);
		
		BukkitTask task = new RepeatMan(plugin).runTaskTimer(plugin, 0, Values.ManHungerDealy);
		
		
	}
	public static void setVisible(Player player){
		SuitUtils.playEffect(player.getLocation(), Values.ManvisibleEffect, 10, 0, 10);
		player.playSound(player.getLocation(), Values.ManvisibleSound, 16.0F, 16.0F);
		ThorUtils.removePotionEffect(PotionEffects.Man_Invisiblility, player);
		ThorUtils.removePotionEffect(PotionEffects.Man_Invisible_SPEED , player);
		for (Player playerOnline : player.getServer().getOnlinePlayers()) {
			if (!playerOnline.canSee(player)) {
				playerOnline.showPlayer(player);

			}

		}
		RepeatMan.removePlayer(player);
	}
	public static boolean CanSee(Player player , boolean CanSee){
		for(Player playerOnline : player.getServer().getOnlinePlayers()){
			if(playerOnline.canSee(player) == CanSee){
				return true;
			}
		}
		return false;
	}
	public static void spawnFallingBlocks(java.util.List<Block> list) {
		int count = 0;
		int divider = Values.Explode_Falling_Block_Count_Divider;
		for(Block block : list){
			if(count %divider==0){
			spawnFallingBlock(block.getLocation(), block);
			}
			count++;
		}
		
	}
	
		 
}
