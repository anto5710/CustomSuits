package gmail.anto5710.mcp.customsuits.Utils;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class MathUtils {

	public static double wholeRandom(double m){
		return Math.random()*m;
	}
	
	public static double randomRadius(double r) {
		return random(-r, r);
	}

	public static double random(double m, double M){
		return m + Math.random() * (M-m);
	}
	
	public static double bound(double min, double num, double max){
		double boundMin = Math.max(min, num);
		double boundMax = Math.min(max, boundMin);
		return boundMax;
	}
	
	public static Vector calculateVelocity(Vector from, Vector to, double gravity, int heightGain){
	
	    
	    // Block locations
	    int endGain = to.getBlockY() - from.getBlockY();
	    double horizDist = Math.sqrt(MathUtils.distanceSquared(from, to));
	    // Height gain
	    int gain = heightGain;
	    double maxGain = gain > (endGain + gain) ? gain : (endGain + gain);
	    // Solve quadratic equation for velocity
	    double a = -horizDist * horizDist / (4 * maxGain);
	    double b = horizDist;
	    double c = -endGain;
	    double slope = -b / (2 * a) - Math.sqrt(b * b - 4 * a * c) / (2 * a);
	    // Vertical velocity
	    double vy = Math.sqrt(maxGain * gravity);
	    // Horizontal velocity
	    double vh = vy / slope;
	    // Calculate horizontal direction
	    int dx = to.getBlockX() - from.getBlockX();
	    int dz = to.getBlockZ() - from.getBlockZ();
	    double mag = Math.sqrt(dx * dx + dz * dz);
	    double dirx = dx / mag;
	    double dirz = dz / mag;
	    // Horizontal velocity components
	    double vx = vh * dirx;
	    double vz = vh * dirz;
	    return new Vector(vx, vy, vz);
	}

	static double distanceSquared(Vector from, Vector to){
	    double dx = to.getBlockX() - from.getBlockX();
	    double dz = to.getBlockZ() - from.getBlockZ();
	    return dx * dx + dz * dz;
	}

	public static boolean distanceSqrd(Location currentLoc, Entity entity, double radiusSqrd) {
		Location location = entity.getLocation();
		if(MathUtils.withinDistanceSqrd(location, currentLoc, radiusSqrd)){
			return true;
		}
		// 몸 높이를 따라 올라가며 체크
		double height = ((CraftEntity)entity).getHandle().getHeadHeight();
		double offset = 0.25;
		for(double y= -offset; y<=height; y+=offset){
			location.add(0, y, 0);
			if(MathUtils.withinDistanceSqrd(location, currentLoc, radiusSqrd)){
				return true;
			}
			location.subtract(0, y, 0);
		}
		return false;
	}
	
	private static boolean withinDistanceSqrd(Location loc1, Location loc2, double radiusSqrd) {
		return loc1.distanceSquared(loc2) <= radiusSqrd;
	}

	public static boolean distance(Location currentLoc, Entity entity , double radius) {
		return distanceSqrd(currentLoc, entity, radius * radius);
	}

//	public static boolean withinDistance(Location loc1, Location loc2, double range){
//		return loc1.distanceSquared(loc2) <= range*range;
//	}
//	
	
	public static Vector randomVector(double range){
		double dx = randomRadius(range), dy = randomRadius(range), dz= randomRadius(range);
		return new Vector(dx, dy, dz);
	}

}
