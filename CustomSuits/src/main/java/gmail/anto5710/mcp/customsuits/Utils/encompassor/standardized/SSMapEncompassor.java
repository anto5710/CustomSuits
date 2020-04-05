package gmail.anto5710.mcp.customsuits.Utils.encompassor.standardized;

import java.util.HashSet;
import java.util.Set;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.MapEncompassor;

public abstract class SSMapEncompassor <E, V> extends MapEncompassor<E, V>{
	protected Set<E>sleeping = new HashSet<>();
	
	public SSMapEncompassor(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
	}

	@Override
	public void run() {
		if(toCancel()){
			cancel();
			return;
		}
		for (E e : maptia.keySet()) {
			if(!sleeping.contains(e)) {
				particulate(e, maptia.get(e));
			}
			if(toRemove(e)) discard(e);
		}
		autoclear();
		tick();
	}
	
	@Override
	public boolean toCancel() {
		return sleeping.size() >= maptia.size() || super.toCancel();
	}
	
	public void sleep(E e) {
		sleeping.add(e);
	}
		
	public void interrupt(E e) {
		sleeping.remove(e);
		autostart();
	}
	
	public Set<E> keysInSleep(){
		return sleeping;
	}
		
	public void autoregister(E e) {
		if(isRegistered(e)) {
			interrupt(e);
			
		} else register(e);
	}
	
	@Override
	public void remove(E e) {
		super.remove(e);
		sleeping.remove(e);
	}
}
