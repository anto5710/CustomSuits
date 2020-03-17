package gmail.anto5710.mcp.customsuits.Utils.encompassor;


import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;

public abstract class AbstractEncompassor<E> implements IEncompassor<E>{
	protected CustomSuitPlugin plugin;
	private final long period;
	private BukkitTask task;
	private long max_tick = 1000;
	
	protected long t=0;
	private Set<E> toRemove = new HashSet<>();	
	
	protected AbstractEncompassor(CustomSuitPlugin plugin, long period) {
		this.plugin = plugin;
		this.period = period; 
	}
	
	@Override
	public final long getPeriod() {
		return period;
	}
	
	@Override
	public final boolean isRunning() {
		return task != null;
	}
	
	protected void tick(){
		t++; //tick
		if(t>max_tick) t%=max_tick;
	}
	
	protected void setMaxTick(long max){
		max_tick = Math.max(max, 0);
	}
	
	protected void autostart() {
		if(!isRunning()) start();
	}
	
	protected void discard(E e){
		toRemove.add(e);
	}
	
	protected void autoclear() {
		for(E e : toRemove){
			remove(e);
		}
		toRemove.clear();
	}
	
	@Override
	public final void start() {
		task = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, period);
	}
	
	@Override
	public void cancel() {
		task.cancel();
		task = null;
	}
	
}
