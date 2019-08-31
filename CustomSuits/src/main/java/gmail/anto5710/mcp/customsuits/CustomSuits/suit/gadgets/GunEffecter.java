package gmail.anto5710.mcp.customsuits.CustomSuits.suit.gadgets;

import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.MathUtil;
import gmail.anto5710.mcp.customsuits.Utils.ParticleUtil;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.LinearEncompassor;


public class GunEffecter extends LinearEncompassor<Snowball>{

	public GunEffecter(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
	}

	@Override
	public boolean toRemove(Snowball e) {
		return e.isDead();
	}

	@Override
	public void particulate(Snowball e) {
		ParticleUtil.playEffect(Particle.CRIT, e.getLocation(), 1);
		
	}

	@EventHandler
	public void damageByGun(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Snowball) {
			Snowball snowball = (Snowball) event.getDamager();
			if (isRegistered(snowball)) {
				event.setDamage(Values.MachineGunDamage);
			}
		}
	}

	@EventHandler
	public void hit(ProjectileHitEvent event) {
		if (event.getEntity() instanceof Snowball) {
			Snowball snowball = (Snowball) event.getEntity();
			if (isRegistered(snowball)) {
				Block hitblock = event.getHitBlock();
				System.out.println(hitblock);
				if(hitblock!=null && !SuitUtils.isUnbreakable(hitblock) && MathUtil.gacha(60)){ //60%의 확률로 부숨
					hitblock.breakNaturally();
				}
				discard(snowball);
			}
		}
	}
}
