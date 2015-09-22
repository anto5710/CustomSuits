package gmail.anto5710.mcp.customsuits._Thor;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.SchedulerHunger;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
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
	public void Lightning(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.LEFT_CLICK_AIR
				|| event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (player.getItemInHand().getType() == Material.AIR
					&& Hammer.Thor(player)&&SchedulerHunger.hunger(player, Values.LightningMissileHunger)) { 
				Location targetblock = SuitUtils.getTargetBlock(player, 300).getLocation();
				SuitUtils.LineParticle(targetblock, player.getEyeLocation(), player, Effect.LAVA_POP, 3, 0, 2, Values.LightningMissile, 2, true);
				
				ThorUtils.strikeLightning(targetblock, player, 1, 4.5, Values.LightningMissile);
				SuitUtils.createExplosion(targetblock, Hammer.Power, false, true);
				}
			}
		
	}
	
	@EventHandler
	public void ThrowHammer(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.RIGHT_CLICK_AIR
				|| event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (SuitUtils.CheckItem(CustomSuitPlugin.hammer,
					player.getItemInHand())) {
				if (Hammer.Thor(player)) {
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

					if (player.isSneaking()) {
						playEffect(dropped, player, true);
					} else {

						playEffect(dropped, player, false);
					}
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
	public void ExplosionRing(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.LEFT_CLICK_AIR
				|| event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (Hammer.Thor(player)
					&& SuitUtils.CheckItem(CustomSuitPlugin.hammer,
							player.getItemInHand()) && player.isSneaking()&&SchedulerHunger.hunger(player, Values.HammerExplosionRingHunger)) {
				Location playerLocation = player.getLocation();
				for(int radius = 2 ; radius< Values.HammerExplosionRingRadius ; radius+=3){
					player.setNoDamageTicks(20);
					ThorUtils.getRing(radius, player, Values.HammerExplosionPower, Values.HammerExplosionRing, false, true);
				}
				player.teleport(playerLocation);
			}
		}
	}
	
	
	public void playEffect(Item dropped, Player player, boolean isTeleport) {
		Repeat.listPlayer.put(dropped, player);
		Repeat.listTeleport.put(dropped, isTeleport);
		if(!Repeat.isRunning(Repeat.taskID)){
			BukkitTask task = new Repeat(plugin)
			.runTaskTimer(plugin, 0, 10);
		}
			

	}
	
}
