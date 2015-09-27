package gmail.anto5710.mcp.customsuits._Thor;

import java.util.List;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.SchedulerHunger;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.WeaponListner;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.ManUtils;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

public class HammerWeapons implements Listener{
	CustomSuitPlugin plugin;
	public HammerWeapons(CustomSuitPlugin plugin){
		this.plugin = plugin;
	}
	@EventHandler
	public void FallingBlockPlace(EntityChangeBlockEvent event){
		Entity entity = event.getEntity();
		if(entity  instanceof FallingBlock){
			if(Thunder_Strike.fallingBlocks.contains((FallingBlock)entity)){
				SuitUtils.createExplosion(entity.getLocation(), 6.0F,true, true);
				Thunder_Strike.fallingBlocks.remove(entity);
			}
		}
	}
	@EventHandler
	public void Lightning(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.LEFT_CLICK_AIR
				|| event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (player.getItemInHand().getType() == Material.AIR
					&& Hammer.Thor(player)&&SchedulerHunger.hunger(player, Values.LightningMissileHunger)) { 
					launch(player);
				}
			}
		
	}
	public static void launch(Player player){
		Location targetblock = SuitUtils.getTargetBlock(player, 100).getLocation();
		WeaponListner.radius = Values.HammerMissileDamage_Radius;
		SuitUtils.LineParticle(targetblock, player.getEyeLocation(), player, Effect.LAVA_POP, 3, 0, 2, Values.LightningMissile,2,  true);
		
		ThorUtils.strikeLightning(targetblock, player, 1, 4.5, Values.LightningMissile);
		SuitUtils.createExplosion(targetblock, Values.HammerMissileExplosion_Power, false, true);
	}
	
	@EventHandler
	public void ThrowHammer(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.RIGHT_CLICK_AIR
				|| event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (SuitUtils.CheckItem(CustomSuitPlugin.hammer,
					player.getItemInHand())) {
				if (Hammer.Thor(player)&&!player.isSneaking()) {
					Item dropped = player.getWorld().dropItem(
							player.getLocation(), player.getItemInHand());
					if (player.getItemInHand().getAmount() == 1) {
						player.getInventory().setItemInHand(
								new ItemStack(Material.AIR, 1));
					} else {

						player.getInventory()
								.getItemInHand()
								.setAmount(
										player.getItemInHand().getAmount() - 1);
					}
					player.updateInventory();
					dropped.setFallDistance(0);

					Location TargetLocation = SuitUtils.getTargetBlock(player,
							500).getLocation();
					Location loc = dropped.getLocation();
					loc.setY(loc.getY() + 2);

					double gravity = 0.0165959600149011612D;
					dropped.teleport(loc);
					org.bukkit.util.Vector v = SuitUtils.calculateVelocity(
							loc.toVector(), TargetLocation.toVector(), gravity,
							6);

					dropped.setVelocity(v);

					
						playEffect(dropped, player);
				
					player.playSound(player.getLocation(), Sound.ANVIL_LAND,
							6.0F, 6.0F);
					player.playSound(player.getLocation(), Sound.IRONGOLEM_HIT,
							4.0F, 2.0F);
				} else{
					Hammer.setThor(player);
				}
			}
		}

	}
	@EventHandler
	public void ThunderStrike(PlayerInteractEvent event){
		if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK){
			Player player = event.getPlayer();
			if(!Hammer.Thor(player)||!SuitUtils.CheckItem(CustomSuitPlugin.hammer, player.getItemInHand())){
				return;
			}
			if(Thunder_Strike.isStriking||player.isSneaking()){
				return;
			}
			if(SchedulerHunger.hunger(player, Values.Thunder_Strike_Hunger)){
				player.playSound(player.getLocation(), Values.Thunder_Strike_Start_Sound,6F, 6F);
				List<Location> list =ManUtils.circle(player.getLocation(), 10, 1, true, false, 0);
				for(Location loc : list){
					SuitUtils.playEffect(player.getLocation(), Values.Thunder_Strike_Start_Effect, 10, 0, 5);
					
				}
				BukkitTask task = new Thunder_Strike_Wait(plugin).runTaskLater(plugin, 60);
			}
		}
		
	}
	

	
	


	public void playEffect(Item dropped, Player player) {
		Repeat.listPlayer.put(dropped, player);
		
		if(!Repeat.isRunning(Repeat.taskID)){
			BukkitTask task = new Repeat(plugin)
			.runTaskTimer(plugin, 0, 10);
		}
			

	}
	
}
