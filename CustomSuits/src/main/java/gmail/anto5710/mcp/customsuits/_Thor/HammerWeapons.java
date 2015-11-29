package gmail.anto5710.mcp.customsuits._Thor;

import gmail.anto5710.mcp.customsuits.CustomSuits.PlayEffect;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.SchedulerHunger;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;
import net.minecraft.server.v1_8_R2.EnumParticle;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class HammerWeapons implements Listener{
	CustomSuitPlugin plugin;
	public HammerWeapons(CustomSuitPlugin plugin){
		this.plugin = plugin;
	}
	/**
	 * Launch LightningMissile if player is Thor and have enough Hunger
	 * @param event PlayerInteractEvent 
	 */
	@EventHandler
	public void Lightning(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.LEFT_CLICK_AIR
				|| event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (player.getItemInHand().getType() == Material.AIR && Hammer.Thor(player)&&
			    SchedulerHunger.hunger(player, Values.LightningMissileHunger)) { 
					launchLightningMissile(player);
			}
		}
	}
	public static void launchLightningMissile(Player player){
		Location targetblock = SuitUtils.getTargetBlock(player, 100).getLocation();
		player.playSound(player.getLocation(), Values.LightningMissileSound, 16.0F,0F);
		SuitUtils.LineParticle(targetblock, player.getEyeLocation(), player, EnumParticle.FLAME, 3,  2, Values.LightningMissile,2,false , true, false,5);
	}
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
		if (event.getAction() == Action.RIGHT_CLICK_AIR
				|| event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (ThorUtils.isHammerinHand(player)) {
				if(Hammer.Thor(player)){
					ThrowHammer(player);
				}
			else{
				if(player == Hammer.thor||Hammer.thor==null){
					PlayEffect.play_Thor_Change_Effect(player , 0);
					Hammer.thor =player;
					}
				}
			}else{
				Location playerEyeLocation = player.getEyeLocation();
				Item hammer =ThorUtils.getItem(player.getWorld() , player);
				if(hammer ==null){
					return;
				}
				Hammer.TeleportItem(hammer , playerEyeLocation );
				player.playSound(playerEyeLocation, Values.HammerTeleportSound, 6.0F,6.0F);
			}
		}

	}
	public void ThrowHammer(Player player) {
		
		ItemStack ItemInHand =player.getItemInHand();
		Item dropped = player.getWorld().dropItem(player.getLocation(),ItemInHand);
		Location loc = dropped.getLocation().add(0, 2, 0);
		dropped.setFallDistance(0);
		
		if (ItemInHand.getAmount() == 1) {
			player.getInventory().setItemInHand(new ItemStack(Material.AIR, 1));
		} else {
			ItemInHand.setAmount(ItemInHand.getAmount()-1);
			player.setItemInHand(ItemInHand);
		}
		player.updateInventory();
		Location TargetLocation = SuitUtils.getTargetBlock(player,500).getLocation();

		double gravity = 0.0165959600149011612D;
		dropped.teleport(loc);
		Vector v = SuitUtils.calculateVelocity(loc.toVector(), TargetLocation.toVector(), gravity,6);
		dropped.setVelocity(v);

		addEffectToHammer(dropped, player);
	
		player.playSound(player.getLocation(), Sound.ANVIL_LAND,6.0F, 6.0F);
		player.playSound(player.getLocation(), Sound.IRONGOLEM_HIT,4.0F, 2.0F);
				
	}
	/**
	 * Stop Wither launching Wither skull
	 * @param event ProjectileLaunchEvent
	 */
	@EventHandler
	public void Stop_Wither_Launch(ProjectileLaunchEvent event){
		Entity shooter = (Entity) event.getEntity().getShooter();
		if(shooter == Thunder_Strike.wither){
			event.getEntity().remove();
			return;
		}
	}
	/**
	 * If player who is Thor left click with enough Hunger , Start ThunderStrike 
	 * @param event PlayerInteractEvent
	 */
	@EventHandler
	public void StartThunderStrike(PlayerInteractEvent event){
		if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK){
			Player player = event.getPlayer();
			
			if(!Hammer.Thor(player)||!ThorUtils.isHammerinHand(player)){
				return;
			}
			if(Thunder_Strike.isStriking||!player.isSneaking()){
				return;
			}
			if(SchedulerHunger.hunger(player, Values.Thunder_Strike_Hunger)){
					PlayEffect.play_Thunder_Strike_Start_Effect(player.getLocation() , player);
			}
		}
		
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
