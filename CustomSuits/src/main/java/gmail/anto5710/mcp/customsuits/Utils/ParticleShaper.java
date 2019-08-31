package gmail.anto5710.mcp.customsuits.Utils;

import java.util.function.Consumer;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ParticleShaper {
	
	
	public static void ring(double t, Location loc, int count, Consumer<Location> effect) {
		double x,y,z;
		for (int c = 0; c <= count; c++) {
			t += 0.1 * Math.PI;
			for (double ptheta = 0; ptheta <= 2; ptheta += 1/32) { //pseudoTheta
				double theta = ptheta * Math.PI;
				x = t * Math.cos(theta);
				y = 2 * Math.exp(-0.1 * t) * Math.sin(t) + 1.5;
				z = t * Math.sin(theta);
				
				loc.add(x, y, z);
				effect.accept(loc);
				loc.subtract(x, y, z);
			}
		}
	}
		
	public static void flatRing(double t, Location loc, int count, Consumer<Location> effect) {
		double x,z;
		for (int c = 0; c <= count; c++) {
			t += 0.1 * Math.PI;
			for (double ptheta = 0; ptheta <= 2; ptheta += 1/32) { //pseudoTheta
				double theta = ptheta * Math.PI;
				x = t * Math.cos(theta);
				z = t * Math.sin(theta);
				
				loc.add(x, 0, z);
				effect.accept(loc);
				loc.subtract(x, 0, z);
			}
		}
	}
	
	public static void playUnderFoot(Entity e, Consumer<Location>effect){
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
	

}
