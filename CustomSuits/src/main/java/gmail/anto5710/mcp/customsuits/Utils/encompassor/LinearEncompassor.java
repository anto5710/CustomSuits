package gmail.anto5710.mcp.customsuits.Utils.encompassor;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Entity;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;

public abstract class LinearEncompassor<E extends Entity> extends AbstractEncompassor<E>{
	protected Set<E> entia = new HashSet<>();
	
	protected LinearEncompassor(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
		
	}

	@Override
	public boolean isRegistered(E e) {
		return entia.contains(e);
	}

	@Override
	public void register(E e) {
		entia.add(e);
		autostart();
	}

	@Override
	public void remove(E e) {
		entia.remove(e);
	}

	
	@Override
	public void run() {
		if(entia.isEmpty()){
			cancel();
			return;
		}
		for (E e : entia) {
			particulate(e);
			if(toRemove(e)) discard(e);
		}
		autoclear();
		tick();
	}
}
