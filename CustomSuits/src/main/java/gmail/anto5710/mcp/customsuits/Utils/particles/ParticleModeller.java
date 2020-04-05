package gmail.anto5710.mcp.customsuits.Utils.particles;

import java.util.function.Consumer;



import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.MathUtil;

public class ParticleModeller {
	
	private static CustomSuitPlugin plugin;
	public ParticleModeller(CustomSuitPlugin plugin){
		ParticleModeller.plugin = plugin;
	}
	
	public static void ring(double r, Location loc, int count, Consumer<Location> effect) {
		double x,y,z;
		for (int c = 0; c <= count; c++) {
			r += 0.1 * Math.PI;
			for (double ptheta = 0; ptheta <= 2; ptheta += 1/32) { //pseudoTheta
				double theta = ptheta * Math.PI;
				x = r * Math.cos(theta);
				y = 2 * Math.exp(-0.1 * r) * Math.sin(r) + 1.5;
				z = r * Math.sin(theta);
				
				loc.add(x, y, z);
				effect.accept(loc);
				loc.subtract(x, y, z);
			}
		}
	}
		
	public static void flatRing(double r, Location loc, int count, Consumer<Location> effect) {
		double x,z;
		for (int c = 0; c <= count; c++) {
			r += 0.1 * Math.PI;
			for (double ptheta = 0; ptheta <= 2; ptheta += 1/32) { //pseudoTheta
				double theta = ptheta * Math.PI;
				x = r * Math.cos(theta);
				z = r * Math.sin(theta);
				
				loc.add(x, 0, z);
				effect.accept(loc);
				loc.subtract(x, 0, z);
			}
		}
	}
	
	public static void footstep(Entity e, Consumer<Location>effect){
		Location loc = e.getLocation();
		loc.add(0, -0.2, 0);

		Vector v1 = MathUtil.linearRotate(90, loc);
		loc.add(v1);
		effect.accept(loc);
		loc.subtract(v1);
		
		Vector v2 = MathUtil.linearRotate(90+180, loc);
		loc.add(v2);
		effect.accept(loc);		
	}

	public static void sphere(Location loc, double r, Consumer<Location> effect) {
		new BukkitRunnable() {
			double y_theta = 0;
			@Override
			public void run() {
				if (y_theta > 8*Math.PI) {
					this.cancel();
				}
				for(double xz_theta = 0 ;xz_theta <= 2*Math.PI ; xz_theta += Math.PI/40){
					double x = r*Math.cos(xz_theta)*Math.sin(y_theta);
					double y = r*Math.cos(y_theta) + 1.5;
					double z = r*Math.sin(xz_theta)*Math.sin(y_theta);
					loc.add(x, y, z);
					effect.accept(loc);
					loc.subtract(x, y, z);
				}
				y_theta += Math.PI / 10;
			}
		}.runTaskTimer(plugin, 0, 1);
	}
}
