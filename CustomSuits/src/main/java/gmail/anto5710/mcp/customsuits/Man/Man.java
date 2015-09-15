package gmail.anto5710.mcp.customsuits.Man;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.ManUtils;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class Man implements Listener{
	CustomSuitPlugin plugin;
	public Man(CustomSuitPlugin plugin){
		this.plugin = plugin;
	}
	@EventHandler
	public void DamagedManPlayer(EntityDamageByEntityEvent event){
		Entity Damager = event.getDamager();
		Entity Entity = event.getEntity();
		
		if(Entity instanceof Player){
			Player player = (Player) Entity;
			if(ManUtils.Man(player)){
				ManUtils.setVisible(player);
			}
		}
	}
	@EventHandler
	public void SetInvisible(PlayerInteractEvent event){
		if(event.getAction()!=Action.LEFT_CLICK_AIR&&event.getAction()!=Action.LEFT_CLICK_BLOCK){
			return;
		}
		
		Player player = event.getPlayer();
		if(!ManUtils.Man(player)||!player.isSneaking()){
			return ;
		}
		
			
			ManUtils.setVisible(player);
			
		return;
		
	
	}
}
