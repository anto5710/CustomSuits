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
	public void ExplodeEffect(EntityExplodeEvent event){
		List<Block> list	 = event.blockList();
		ManUtils.spawnFallingBlocks(list);
		return;
		}
}
