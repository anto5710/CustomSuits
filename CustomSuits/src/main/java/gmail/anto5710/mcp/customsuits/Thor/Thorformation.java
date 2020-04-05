package gmail.anto5710.mcp.customsuits.Thor;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.MapEncompassor;
import gmail.anto5710.mcp.customsuits.Utils.particles.ParticleUtil;

public class Thorformation extends MapEncompassor<Player, Double>{

	public Thorformation(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
	}

	@Override
	public boolean toRemove(Player player) {
		return player.isDead() || !player.isOnline();
	}

	@Override
	public void particulate(Player player, Double phi) {
		Location location = player.getLocation();
		runEffect(phi, 0, 0, 0, location);

		if (phi > 10 * Math.PI) {
			discard(player);
			Hammer.thormorphosize(player);
		} else {
			put(player, phi += Math.PI / 8);
			SuitUtils.playSound(player, Sound.BLOCK_FIRE_EXTINGUISH, 6F, 6F);
		}	
	}
	
	@Override
	public Double defaultVal(Player e) {
		return 0D;
	}
	
	private void runEffect(double phi, double dx, double dy, double dz, Location loc) {
		for (double t = 0; t <= 2*Math.PI; t = t + Math.PI/16){
			 for (double i = 0; i <= 1; i = i + 1){
	             dx = 0.4*(2*Math.PI-t)*0.5*Math.cos(t + phi + i*Math.PI);
	             dy = 0.5*t;
	             dz = 0.4*(2*Math.PI-t)*0.5*Math.sin(t + phi + i*Math.PI);
	             loc.add(dx, dy, dz);
	             ParticleUtil.playDust(loc, 1, Color.RED, 0.8F);
	             loc.subtract(dx,dy,dz);
			}
		}              		
	}
}
