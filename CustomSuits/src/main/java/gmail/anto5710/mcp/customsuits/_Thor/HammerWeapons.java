package gmail.anto5710.mcp.customsuits._Thor;

import gmail.anto5710.mcp.customsuits.CustomSuits.PlayEffect;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.HungerScheduler;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;


import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class HammerWeapons implements Listener{
	private CustomSuitPlugin plugin;
	
	public HammerWeapons(CustomSuitPlugin plugin){
		this.plugin = plugin;
	}
	/**
	 * Launch LightningMissile if player is Thor and have enough Hunger
	 * @param event PlayerInteractEvent 
	 */
//	@EventHandler
//	public void Lightning(PlayerInteractEvent event) {
//		Player player = event.getPlayer();
//		if (SuitUtils.isLeftClick(event) && SuitUtils.holdingNothing(player)
//				&& Hammer.isPractiallyThor(player) && HungerScheduler.addHunger(player, Values.LightningMissileHunger)) {
//			launchLightningMissile(player);
//		}
//	}
//	
//	public static void launchLightningMissile(Player player){
//		Location targetblock = SuitUtils.getTargetBlock(player, 100).getLocation();
//		player.playSound(player.getLocation(), Values.LightningMissileSound, 16.0F,0F);
//		SuitUtils.LineParticle(targetblock, player.getEyeLocation(), player, Particle.LAVA, 3, 0, Values.LightningMissile, 2, false , true, false,5);
//	}
	/**
	 * Throw Hammer if Player is Thor
	 * @param event PlayerInteractEvent
	 */
	@EventHandler
	public void ThrowHammer(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(player.isSneaking()){
			return;
		}
		if (SuitUtils.isRightClick(event)) {
			if (ThorUtils.isHammerinHand(player)) {
				if(Hammer.isPractiallyThor(player)){
					event.setCancelled(true);
					throwHammer(player);
				} else if(Hammer.canBeThor(player)){
					awakenThor(player);
				}
				
			}else if(Hammer.isThor(player)){
				returnHammer(player);
			}
		}
	}
	
	public void awakenThor(Player player){
		PlayEffect.play_Thor_Change_Effect(player , 0);
		Hammer.thor = player;
	}
	
	public void returnHammer(Player player){
		Location playerEyeLocation = player.getEyeLocation();
		Item hammer =ThorUtils.getDroppedHammer(player.getWorld() , player);
		if(hammer==null){
			return;
		}
		Hammer.teleportItem(hammer , playerEyeLocation);
		player.playSound(playerEyeLocation, Values.HammerTeleportSound, 6.0F,6.0F);
	}
	
	public void throwHammer(Player player) {
		Location location = player.getEyeLocation();
		
		ItemStack ItemInHand =player.getInventory().getItemInMainHand();
		Item dropped = player.getWorld().dropItem(location, ItemInHand);
		dropped.setFallDistance(0);
		
		ThorUtils.removeItemInHand(player);

//		double gravity = 0.0165959600149011612D;
		Vector v = player.getLocation().getDirection().normalize().multiply(2);
		dropped.teleport(location);
		dropped.setVelocity(v);
		
		addEffectToHammer(dropped, player);
	
		player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND,6.0F, 6.0F);
		player.playSound(player.getLocation(), Sound.ENTITY_IRON_GOLEM_HURT,4.0F, 2.0F);
	}
	
	/**
	 * Add Particle Trail to thrown hammer
	 * @param dropped Hammer Item
	 * @param player Player
	 */
	public void addEffectToHammer(Item dropped, Player player) {
		Hammer_Throw_Effect.listPlayer.put(dropped, player);
		
		if(!Hammer_Throw_Effect.isRunning()){
			new Hammer_Throw_Effect().runTaskTimer(plugin, 0, 1);
		}
	}
}
