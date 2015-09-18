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
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChannelEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class Man implements Listener{
	CustomSuitPlugin plugin;
	public Man(CustomSuitPlugin plugin){
		this.plugin = plugin;
	}
	@EventHandler
	public void DamagedManPlayer(EntityDamageEvent event){
		
		Entity Entity = event.getEntity();
		
		if(Entity instanceof Player){
			Player player = (Player) Entity;
			if(ManUtils.Man(player)){
				
				ManUtils.changeVisiblility(player , true ,false);
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
		
			
		ManUtils.changeVisiblility(player , true , true);
			
		return;
		
	
	}
	@EventHandler
	public void Boost(PlayerToggleFlightEvent event){
		Player player = event.getPlayer();
		
	}
}
