package gmail.anto5710.mcp.customsuits.Utils.encompassor.standardized;

import org.bukkit.entity.Item;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.MapEncompassor;

public abstract class DispenseEncompassor <E extends Item,V> extends MapEncompassor<E, V>{

	public DispenseEncompassor(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
	}

	@Override
	public boolean toRemove(E e) {
		return e.isOnGround() || e.isDead();
	}
}
