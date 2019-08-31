package gmail.anto5710.mcp.customsuits.Utils;

import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.gadgets.SuitWeapons;

public class WeaponUtils {
	public static Vector determinePosition(Player player, boolean dualWield, boolean leftClick) {
		int leftOrRight = 90;
		if ((dualWield) && (leftClick)) {
			leftOrRight = -90;
		}
		double playerYaw = (player.getLocation().getYaw() + 90.0F + leftOrRight) * 3.141592653589793D / 180.0D;
		double x = Math.cos(playerYaw);
		double y = Math.sin(playerYaw);
		Vector vector = new Vector(x, 0.0D, y);

		return vector;
	}

	public static void damageNeffect(Location currentLoc, double damage, Entity shooter, boolean isMissile,
			boolean isProjectile, double radius) {

		for (Entity entity : SuitWeapons.findEntity(currentLoc, shooter, radius)) {

			Damageable damageable = (Damageable) entity;

			if (!isMissile && isProjectile && isHeadshot(currentLoc, entity)) {
				damage *=2;
				SuitWeapons.firework(currentLoc, shooter);
			}
			damageable.damage(damage, shooter);
		}
	}
	
	private static boolean isHeadshot(Location shotLoc, Entity entity){
		return entity instanceof LivingEntity && MathUtils.distance(shotLoc, ((LivingEntity) entity).getEyeLocation(), 0.35);
	}
	
	public static boolean damage(Entity toDamage, double damage, Entity afflicter){
		if(toDamage!=null && toDamage instanceof Damageable){
			((Damageable)toDamage).damage(damage, afflicter);
			return true;
		}
		return false;
	}
}
