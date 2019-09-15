package gmail.anto5710.mcp.customsuits.Utils.damagiom;

import java.util.Arrays;

import java.util.Collection;
import java.util.function.Predicate;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import gmail.anto5710.mcp.customsuits.Utils.MathUtil;

public class DamageUtil {
		
	public static boolean isHeadshot(Location shotLoc, Entity entity){
		return entity instanceof LivingEntity && MathUtil.distance(shotLoc, ((LivingEntity) entity).getEyeLocation(), 0.35*entity.getWidth()/1.4D);
	}

	public static void areaDamage(Location origin, double damage, Entity shooter, double radius, DamageMeta meta) {
		for (Damageable e : DamageUtil.search(origin, radius, e->MathUtil.distanceBody(origin, e, radius), shooter)) {
			damage(e, damage, origin, shooter, meta);
		}
	}
	
	public static void areaDamageBounded(Location origin, double damage, Entity shooter, double radius, DamageMeta meta) {
		Vector vorigin = origin.toVector();
		for (Damageable e : DamageUtil.search(origin, radius, e->e.getBoundingBox().contains(vorigin), shooter)) {
			damage(e, damage, origin, shooter, meta);
		}
	}
	
	public static void damagevent(EntityDamageEvent event, double damage, Projectile prj, DamageMeta meta) {
		Entity e = event.getEntity();
		if(meta!=null && meta.allowHeadshot){
			damage = applyAttribute(e, damage, prj.getLocation(), getShooter(prj), meta);
		}
		event.setDamage(damage);
	}
	
	public static void damage(Damageable e, double damage, Location origin, Entity damager, DamageMeta meta){
		if (meta != null && meta.allowHeadshot) {
			damage = applyAttribute(e, damage, origin, damager, meta);
		}
		e.damage(damage, damager);
	}
	
	private static double applyAttribute(Entity e, double damage, Location origin, Entity damager, DamageMeta meta){
		if(isHeadshot(origin, e)){
			damage *= meta.headshot_multiplier;
			if(meta.firework) DamageControl.firework(origin, damager);
		}
		return damage;
	}
	
	public static Entity getShooter(@Nonnull Projectile prj){
		ProjectileSource shooter = prj.getShooter();
		return shooter instanceof Entity ? (Entity) shooter : null;
	}
	
	@SuppressWarnings("unchecked")
	public static <E extends Entity> Collection<E> searchBox(Location center, double radius, Class<E> type, Predicate<Entity> condition) {
		return (Collection<E>) center.getWorld().getNearbyEntities(center, radius, radius, radius, condition.and(e->type.isInstance(e)));
	}
	
	public static Collection<Damageable> search(Location center, double radius, Predicate<Entity> refilter, Entity...toExclude){
		Collection<Damageable> entia = searchBox(center, radius, Damageable.class, refilter);
		if (toExclude != null) {
			Arrays.stream(toExclude).forEach(e -> {
				entia.remove(e);
				if (e.isInsideVehicle()) {
					entia.remove(e.getVehicle());
				}
			});
		}
		return entia;
	}
}
