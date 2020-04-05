package gmail.anto5710.mcp.customsuits.CustomSuits.suit.weapons;

import org.bukkit.Particle;

import org.bukkit.entity.Snowball;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.standardized.StandardEncompassor;
import gmail.anto5710.mcp.customsuits.Utils.particles.ParticleUtil;


public class GunEffecter extends StandardEncompassor<Snowball>{

	public GunEffecter(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
	}

	@Override
	public void particulate(Snowball e) {
		ParticleUtil.playEffect(Particle.CRIT, e.getLocation(), 1);
	}
}
