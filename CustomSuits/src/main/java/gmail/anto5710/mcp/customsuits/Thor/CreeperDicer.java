package gmail.anto5710.mcp.customsuits.Thor;

import java.util.HashSet;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.HungerScheduler;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.PotionBrewer;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;

public class CreeperDicer implements Listener{
	private CustomSuitPlugin plugin;
	
	public CreeperDicer(CustomSuitPlugin plugin) {
		this.plugin = plugin;
	}
	
	private static Set<Creeper> dicedOnes = new HashSet<>();
	
	@EventHandler
	public void thunderCreeper(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (!(Hammer.isThor(player) && Hammer.isPractiallyThor(player) 
				&& player.isSneaking() && SuitUtils.isLeftClick(event)
				&& HungerScheduler.sufficeHunger(player, Values.Thunder_Creeper_Hunger))) {
			return;
		}

		final Location location = player.getLocation();
		SuitUtils.playSound(location, Values.Thunder_Creeper_Start_Sound, 10F, 1F);
		location.setDirection(new Vector(1, 0, 1));
		final World world = player.getWorld();
		final int Point = 7;
		final int Count = 20;
		final int maxim = Point * Count;
		
		float angle = 360 / Point;
		
		new BukkitRunnable() {
			int count = 1;
			int point = 0;
			double y = 2.5;

			@Override
			public void run() {
				if (count >= maxim) {
					this.cancel();
					return;
				}
				
				for (point = 0; point < Point; point++) {
					Location loc = location.clone();
					float current_angle = angle * point;
					loc.setYaw(current_angle);
					Vector vector = loc.getDirection().normalize().multiply(1.5).setY(y);

					Creeper creeper = world.spawn(location, Creeper.class);
					creeper.setPowered(true);
					creeper.setVelocity(vector);
					
					PotionBrewer.addPotion(creeper, PotionEffectType.INVISIBILITY, 100000, 1);
					PotionBrewer.addPotion(creeper, PotionEffectType.SLOW, 100000, 20);
					dicedOnes.add(creeper);
					count++;
				}
			}
		}.runTaskTimer(plugin, 0, 1);
		
	}
	
	@EventHandler
	public void whenDicedOneBitesTheDust(EntityDamageEvent e) {
		Entity entity = e.getEntity();

		if (isDicedCreepr(entity) && e.getCause() == DamageCause.FALL) {
			Creeper creeper = (Creeper) entity;
			spawnTNT(creeper);
			dicedOnes.remove(creeper);
			creeper.damage(10000000);
		}
	}
	
	private boolean isDicedCreepr(Entity e){
		return e instanceof Creeper && dicedOnes.contains(e);
	}

	private void spawnTNT(Creeper creeper) {
		TNTPrimed tnt = creeper.getWorld().spawn(creeper.getLocation(), TNTPrimed.class);
		tnt.setFireTicks(35);
		tnt.setIsIncendiary(true);
		tnt.setVelocity(new Vector(0 , 1.75, 0));
	}
}
