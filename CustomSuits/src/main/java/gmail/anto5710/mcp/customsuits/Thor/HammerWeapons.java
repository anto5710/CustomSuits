package gmail.anto5710.mcp.customsuits.Thor;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.CustomEffects;
import gmail.anto5710.mcp.customsuits.Utils.InventoryUtil;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;


import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class HammerWeapons implements Listener{
	@SuppressWarnings("unused")
	private CustomSuitPlugin plugin;
	
	public HammerWeapons(CustomSuitPlugin plugin){
		this.plugin = plugin;
	}
	/**
	 * Throw Hammer if Player is Thor
	 * @param event PlayerInteractEvent
	 */
	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (!player.isSneaking() && SuitUtils.isRightClick(event)) {
			if (ThorUtils.isHammerinHand(player)) {
				if(Hammer.isPractiallyThor(player)){
					event.setCancelled(true);
					throwHammer(player);
				} else if(Hammer.canBeThor(player)){
					awakenThor(player);
				}
				
			}else if(Hammer.isThor(player)){
				Hammer.returnHammer(player);
			}
		}
	}
	
	public void awakenThor(Player player){
		CustomEffects.play_Thor_Change(player);
		Hammer.thor = player;
	}
	
	public void throwHammer(Player player) {
		Location location = player.getEyeLocation();
		
		ItemStack ItemInHand =player.getInventory().getItemInMainHand();
		Item dropped = player.getWorld().dropItem(location, ItemInHand);
		dropped.setFallDistance(0);
		
		InventoryUtil.removeMainItem(player);

//		double gravity = 0.0165959600149011612D;
		Vector v = player.getLocation().getDirection().normalize().multiply(2);
		dropped.teleport(location);
		dropped.setVelocity(v);
		
		Hammer.hammerEffecter.register(dropped, player);
	
		SuitUtils.playSound(player, Sound.BLOCK_ANVIL_LAND,6.0F, 6.0F);
		SuitUtils.playSound(player, Sound.ENTITY_IRON_GOLEM_HURT,4.0F, 2.0F);
	}
}
