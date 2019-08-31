package gmail.anto5710.mcp.customsuits.Utils.encompassor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Entity;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;

public abstract class MapEncompassor<E extends Entity, V> extends AbstractEncompassor<E>{
	protected Map<E, V> maptia = new HashMap<>();
	
	
	public MapEncompassor(CustomSuitPlugin plugin, long period){
		super(plugin, period);
	}

	@Override
	public boolean isRegistered(E e) {
		return maptia.containsKey(e);
	}
	
	public V get(E e){
		return maptia.get(e);
	}
	
	protected void put(E e, V v){
		maptia.put(e, v);
	}

	public void register(E e, V v) {
		maptia.put(e, v);
		autostart();
	}
	
	@Override
	public void register(E e) {
		register(e, defaultVal(e));	
	}
	
	@Override
	public void remove(E e) {
		maptia.remove(e);
	}

	@Override
	public void run() {
		if(maptia.isEmpty()){
			cancel();
			return;
		}
		for (E e : maptia.keySet()) {
			particulate(e, maptia.get(e));
			if(toBeRemoved(e)) discard(e);
		}
		autoclear();
		tick();
	}
	

	public abstract void particulate(E e, V v);
	public abstract V defaultVal(E e);
	
	@Override
	public void particulate(E e) {
		particulate(e, maptia.get(e));
	}
}