package gmail.anto5710.mcp.customsuits.CustomSuits.suit.gadgets;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.CustomEffects;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.WeaponUtils;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.LinearEncompassor;

public class ArcReffecter extends LinearEncompassor<Snowball>{

	public ArcReffecter(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
	}

	@Override
	public boolean toRemove(Snowball e) {
		return e.isDead();
	}

	@Override
	public void particulate(Snowball e) {
		CustomEffects.playArcReactor(e.getLocation());
	}
	
	@EventHandler
	public void impact(ProjectileHitEvent e){
		if (e.getEntityType() == EntityType.SNOWBALL) {
			Snowball bim = (Snowball) e.getEntity();
			
			if (isRegistered(bim)) {
				Entity hit = e.getHitEntity();
				if (hit != null) {
					transfix(bim, hit);
				}
				SuitUtils.createExplosion(bim.getLocation(), Values.BimExplosionPower, false, true);
			}
		}
	}

	private void transfix(Projectile bim, Entity hit){		
		Entity shooter = WeaponUtils.getShooter(bim);
		double damage = bim.hasMetadata("repulseDamage") ? bim.getMetadata("repulseDamage").get(0).asDouble() : Values.Bim;
		System.out.println(damage +" DAm");
		WeaponUtils.shot(hit, damage, shooter, bim.getLocation(), false);
	}
}
