package gmail.anto5710.mcp.customsuits.Utils;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class MathUtil {
	
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
	
	public static boolean gacha(double percent){
		return wholeRandom(100) < percent;
	}
	
	public static Vector calculateVelocity(Vector from, Vector to, double gravity, int heightGain){
	    // Block locations
	    int endGain = to.getBlockY() - from.getBlockY();
	    double horizDist = Math.sqrt(MathUtil.distanceSqrd(from, to));
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

	static double distanceSqrd(Vector from, Vector to){
	    double dx = to.getBlockX() - from.getBlockX();
	    double dz = to.getBlockZ() - from.getBlockZ();
	    return dx * dx + dz * dz;
	}

	public static boolean distanceSqrdBody(Location currentLoc, Entity entity, double radiusSqrd) {
		Location location = entity.getLocation();
		if(MathUtil.distanceSqrd(location, currentLoc, radiusSqrd)){
			return true;
		}
		// 몸 높이를 따라 올라가며 체크
		double height = ((CraftEntity)entity).getHandle().getHeadHeight();
		double offset = 0.25;
		for(double y= -offset; y<=height; y+=offset){
			location.add(0, y, 0);
			if(MathUtil.distanceSqrd(location, currentLoc, radiusSqrd)){
				return true;
			}
			location.subtract(0, y, 0);
		}
		return false;
	}
	
	private static boolean distanceSqrd(Location loc1, Location loc2, double radiusSqrd) {
		return loc1.distanceSquared(loc2) <= radiusSqrd;
	}

	public static boolean distanceBody(Location loc, Entity entity , double radius) {
		return distanceSqrdBody(loc, entity, radius * radius);
	}
	
	public static boolean distance(Location loc1, Location loc2 , double radius) {
		return distanceSqrd(loc1, loc2, radius * radius);
	}
	
	public static Location randomLoc(Location location, double a) {
		return location.clone().add(randomRadius(a), randomRadius(a), randomRadius(a));
	}

	public static Vector randomVector(double range){
		double dx = randomRadius(range), dy = randomRadius(range), dz= randomRadius(range);
		return new Vector(dx, dy, dz);
	}
	
	public static Vector rotate(Vector v , Location loc){
		double yawR = loc.getY()/180.0*Math.PI;
		double pitchR = loc.getPitch()/180.0*Math.PI;
		
		v = rotateX(v, pitchR);
		v = rotateY(v, -yawR);
		return v;
	}

	public static Vector rotateX(Vector v, double a) {
		double y = Math.cos(a) * v.getX() - Math.sin(a) * v.getZ();
		double z = Math.sin(a) * v.getY() + Math.cos(a) * v.getZ();

		return v.setY(y).setZ(z);
	}
	
	public static Vector rotateY(Vector v, double a) {
		double x = Math.cos(a) * v.getX() + Math.sin(a) * v.getZ();
		double z = -Math.sin(a) * v.getX() + Math.cos(a) * v.getZ();

		return v.setX(x).setZ(z);
	}
	
	public static Vector rotateZ(Vector v, double a) {
		double x = Math.cos(a) * v.getX() - Math.sin(a) * v.getY();
		double y = Math.sin(a) * v.getX() + Math.cos(a) * v.getY();

		return v.setX(x).setY(y);
	}

	private static float distance_between = 10F;
	public static Vector linearRotate(float deltaYaw, Location loc){
		Location newloc = loc.clone();
		float yaw = (loc.getYaw() + deltaYaw) % 360;
		
		newloc.setYaw(yaw);
		newloc.setPitch(90 - distance_between);
		Vector v = newloc.getDirection(); v.setY(0);
		return v;
	}


}
