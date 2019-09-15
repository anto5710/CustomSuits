package gmail.anto5710.mcp.customsuits.Utils.encompassor.standardized;

import org.bukkit.entity.Entity;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.LinearEncompassor;

public abstract class StandardEncompassor <E extends Entity> extends LinearEncompassor<E>{
	protected StandardEncompassor(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
	}

	@Override
	public boolean toRemove(Entity e) {
		return e.isDead();
	}
}
