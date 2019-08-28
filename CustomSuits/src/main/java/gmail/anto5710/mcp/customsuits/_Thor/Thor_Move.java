package gmail.anto5710.mcp.customsuits._Thor;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Setting.PotionEffects;
import gmail.anto5710.mcp.customsuits.Utils.ParticleUtil;
import gmail.anto5710.mcp.customsuits.Utils.PotionBrewer;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Thor_Move extends BukkitRunnable {
	static Location location;
	static double Add_Y_Offset = 0D;
	static boolean isUp = false;
	static Player player;
	static double add_Horizonal = 0;
	static double radius = 1;
	static boolean isRunning = false;

	public Thor_Move(CustomSuitPlugin plugin, Player player) {
		Thor_Move.player = player;
		Add_Y_Offset = 0D;
		isUp = false;
		add_Horizonal = 0;
		radius = 1;
		isRunning = false;
		addThorPotion_Effects();
		player.setAllowFlight(true);
	}
	
	@Override
	public void run() throws IllegalStateException {
		location = player.getLocation();
		if (!Hammer.isPractiallyThor(player) || !player.isOnline()) {
			try {
				removeMovingEffect();
				return;
			} catch (IllegalStateException e) {}
		}
		isRunning = true;
		
		Location loc = location.clone();

		add_Horizonal += 0.2;

		double y = loc.getY() + Add_Y_Offset;
		double x = Math.sin(add_Horizonal * radius);
		double z = Math.cos(add_Horizonal * radius);
		
		loc.add(x, 0, z);
		loc.setY(y);
		ParticleUtil.playEffect(Particle.FLAME, loc, 5);

		Location locii = location.clone();
		locii.add(-1 * x, 0, -1 * z);
		locii.setY(y);
		ParticleUtil.playEffect(Particle.FLAME, locii, 5);
		
		Add_Y_Offset += isUp? 0.1 : -0.1;
		
		if (Add_Y_Offset >= 2) {
			isUp = false;
		}
		if (Add_Y_Offset <= 0) {
			isUp = true;
		}
		
		loc = location;
	}
	
	public void removeMovingEffect(){
		removeThorPotion_Effects();
		player.setAllowFlight(false);
		isRunning = false;
		Hammer.move = null;
		cancel();
	}

	private void addThorPotion_Effects() {
		PotionBrewer.addPotions(player, PotionEffects.Thor_FAST_DIGGING, PotionEffects.Thor_FIRE_RESISTANCE,
				PotionEffects.Thor_HEALTH_BOOST, PotionEffects.Thor_INCREASE_DAMAGE, PotionEffects.Thor_JUMP,
				PotionEffects.Thor_REGENERATION, PotionEffects.Thor_SPEED, PotionEffects.Thor_WATER_BREATHING);
	}

	private void removeThorPotion_Effects() {
		PotionBrewer.removePotionEffects(player, PotionEffects.Thor_FAST_DIGGING, PotionEffects.Thor_FIRE_RESISTANCE,
				PotionEffects.Thor_HEALTH_BOOST, PotionEffects.Thor_INCREASE_DAMAGE, PotionEffects.Thor_JUMP,
				PotionEffects.Thor_REGENERATION, PotionEffects.Thor_SPEED, PotionEffects.Thor_WATER_BREATHING);
	}
}
