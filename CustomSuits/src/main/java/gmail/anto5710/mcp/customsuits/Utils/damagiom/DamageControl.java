package gmail.anto5710.mcp.customsuits.Utils.damagiom;

import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import gmail.anto5710.mcp.customsuits.CustomSuits.FireworkProccesor;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.weapons.SuitWeapons;
import gmail.anto5710.mcp.customsuits.Utils.MathUtil;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.metadative.Metadative;

public class DamageControl implements Listener{
	public static final String 
						DAMAGE = "g.projectiler.HIT_DAMAGE",  // double damage
						EXPLOSIVE = "g.projectiler.EXPLOSION",
						FIRE = "g.projectiler.EXPLOSION.fire",
						NODESTROY = "g.projectiler.EXPLOSION.nodestroy",
						BLOCKSHOT = "g.projectiler.BLOCKSHOT"; 
	
	@SuppressWarnings("unused")
	private CustomSuitPlugin plugin;
	public DamageControl(CustomSuitPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void genericDamage(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Projectile) {
			Projectile prj = (Projectile) e.getDamager();

			if (prj.hasMetadata(DAMAGE)) {
				double damage = Metadative.excavate(prj, DAMAGE);
				DamageUtil.damagevent(e, damage, prj, DamageMeta.retrieve(prj));
			}
		}
	}
	
	@EventHandler
	public void genericExplosion(ProjectileHitEvent e){
		Projectile prj = (Projectile) e.getEntity();
		if(prj.hasMetadata(EXPLOSIVE)){
			float yield = Metadative.excafate(prj, EXPLOSIVE);
			boolean fire = Metadative.excavatruth(prj, FIRE);
			
			if(prj instanceof Explosive){
				Metadative.setExplosive((Explosive)prj, yield, fire);
			}else{
				boolean destroy = !Metadative.excavatruth(prj, NODESTROY);
				SuitUtils.createExplosion(prj.getLocation(), yield, fire, destroy);
			}
		}
	}
	
	@EventHandler
	public void genericBlockShot(ProjectileHitEvent e){
		Block hit = e.getHitBlock();
		Projectile prj = e.getEntity();
		if(hit != null && prj.hasMetadata(BLOCKSHOT) && MathUtil.gacha(Metadative.excavate(prj, BLOCKSHOT))){
			SuitWeapons.breakblock(hit);
		}
	}

	
	public static void firework(Location loc, Entity shooter) {
		FireworkEffect effect = FireworkProccesor.getRandomEffect();
		Firework firework = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		FireworkMeta meta = firework.getFireworkMeta();
		meta.addEffect(effect);
		meta.setPower((int) (MathUtil.wholeRandom(3) + 1.5));
		firework.setFireworkMeta(meta);
		if (shooter != null) {
			SuitUtils.playSound(shooter, Sound.ENTITY_GENERIC_EXPLODE, 14.0F, 14.0F);
			SuitUtils.playSound(shooter, Sound.ENTITY_WITHER_DEATH, 14.0F, 14.0F);
		}
	}	
}
