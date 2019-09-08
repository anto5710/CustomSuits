package gmail.anto5710.mcp.customsuits.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import gmail.anto5710.mcp.customsuits.CustomSuits.FireworkProccesor;

public class WeaponUtils {
	public static Vector determinePosition(Player player, boolean dualWield, boolean leftClick) {
		int leftOrRight = 90;
		if ((dualWield) && (leftClick)) {
			leftOrRight = -90;
		}
		double pYaw = (player.getLocation().getYaw() + 90F + leftOrRight) * Math.PI / 180D;
		double x = Math.cos(pYaw);
		double y = Math.sin(pYaw);
		Vector dHand = new Vector(x, 0, y);

		return dHand;
	}

	public static void damageAdjacent(Location origin, double damage, Entity shooter, double radius, boolean firework) {
		for (Entity entity : WeaponUtils.findEntities(origin, shooter, radius)) {
			shot(entity, damage, shooter, origin, firework);
		}
	}

	public static boolean shot(Entity toDamage, double damage, Entity afflicter, Location hitLoc, boolean firework){
		if(toDamage!=null && toDamage instanceof Damageable){
			if(isHeadshot(hitLoc, toDamage)){
				damage *=2;
				if(firework) WeaponUtils.firework(hitLoc, afflicter);
			}
			((Damageable)toDamage).damage(damage, afflicter);
			return true;
		}
		return false;
	}
	
	public static Entity getShooter(Projectile prj){
		ProjectileSource shooter = prj.getShooter();
		return shooter instanceof Entity ? (Entity) shooter : null;
	}
	
	private static boolean isHeadshot(Location shotLoc, Entity entity){
		return entity instanceof LivingEntity && MathUtil.distance(shotLoc, ((LivingEntity) entity).getEyeLocation(), 0.35);
	}
		
	public static <E extends Entity> List<Entity> findEntities(Location center, Entity shooter, double radius) {
		Collection<Entity> near = center.getWorld().getNearbyEntities(center, radius, radius, radius,
				e -> e instanceof Damageable);
		near.remove(shooter);
		if (shooter.isInsideVehicle()) {
			near.remove(shooter.getVehicle());
		}
		// currentLoc.getWorld().getNearbyEntities(arg0, arg1, arg2, arg3);
		ArrayList<Entity> list = new ArrayList<>();
		for (Entity entity : near) {
			if (MathUtil.distanceBody(center, entity, radius) && !list.contains(entity)) {
				list.add(entity);
			}
		}
		return list;
	}

	public static void firework(Location loc, Entity shooter) {
		FireworkEffect effect = FireworkProccesor.getRandomEffect();
		Firework firework = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		FireworkMeta meta = firework.getFireworkMeta();
		meta.addEffect(effect);
		meta.setPower((int) (MathUtil.randomRadius(3) + 1.5));
		firework.setFireworkMeta(meta);
		if (shooter != null) {
			SuitUtils.playSound(shooter, Sound.ENTITY_GENERIC_EXPLODE, 14.0F, 14.0F);
			SuitUtils.playSound(shooter, Sound.ENTITY_WITHER_DEATH, 14.0F, 14.0F);
		}
	}
}
