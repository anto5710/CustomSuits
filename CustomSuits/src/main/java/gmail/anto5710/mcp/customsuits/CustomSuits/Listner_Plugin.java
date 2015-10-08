package gmail.anto5710.mcp.customsuits.CustomSuits;

import java.util.Iterator;
import java.util.List;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.ManUtils;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.scheduler.BukkitTask;

public class Listner_Plugin implements Listener{
	CustomSuitPlugin plugin;
	
	public Listner_Plugin(CustomSuitPlugin plugin){
		this.plugin = plugin;
	}
	@EventHandler
	public void BeaconBreak(BlockDamageEvent event){
		Block block = event.getBlock();
		if(block.getType()==Material.BEACON){
		Bukkit.getServer().broadcastMessage(ChatColor.DARK_PURPLE+  "===========================\n"+
													    	"    Beacon is Breaking!!   \n"
														 +  "===========================");
		}
	}
	@EventHandler
	public void GameEnd(BlockBreakEvent event){
		Block block = event.getBlock();
		if(block.getType()==Material.BEACON){
		Bukkit.getServer().broadcastMessage(ChatColor.AQUA+  "===========================\n"+
													    	 "         Game End!!        \n"
														 +   "===========================");
			for(Player player: Bukkit.getServer().getOnlinePlayers()){
				player.setGameMode(GameMode.CREATIVE);
				
				Location baseloc = player.getLocation();
				for(int i = 0; i<= 100 ; i++){
					double LocX_Random = ManUtils.Random(5);
					double LocY_Random = ManUtils.Random(5);
					double LocZ_Random = ManUtils.Random(5);
					int power = (int) (ManUtils.Random(5)+ 2.5);
					Location loc = baseloc.clone().add(LocX_Random, LocY_Random, LocZ_Random);
					
					int R_random = ((int)(ManUtils.Random(255)+127.5));
					int G_random = ((int)(ManUtils.Random(255)+127.5));
					int B_random = ((int)(ManUtils.Random(255)+127.5));
					
					int fade_R_random = ((int)(ManUtils.Random(255)+127.5));
					int  fade_G_random =((int)(ManUtils.Random(255)+127.5));
					int fade_B_random = ((int)(ManUtils.Random(255)+127.5));
					int Type_random = (int) (ManUtils.Random(4)+2);
					
					
				SuitUtils.spawnFirework(Color.fromBGR(B_random, G_random, R_random), Type.values()[Type_random], power, true, true ,Color.fromBGR(fade_B_random, fade_G_random, fade_R_random),loc);
				}
				player.playSound(player.getLocation(), Sound.WITHER_DEATH,10F, 10F);
				player.playSound(player.getLocation(), Sound.WITHER_SPAWN,10F, 10F);
			}
		}
	}
	@EventHandler
	public void ExplodeEffect(EntityExplodeEvent event){
		List<Block> list	 = event.blockList();
		Iterator<Block> iterator= list.iterator();
		while(iterator.hasNext() ){
			Block block = iterator.next();
			if(block.getType() == Material.BEACON){
				event.setCancelled(true);
				return;
			}
		}
		ManUtils.spawnFallingBlocks(list);
		return;
		}
}
