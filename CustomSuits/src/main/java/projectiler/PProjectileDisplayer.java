package projectiler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.bukkit.Location;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;

public class PProjectileDisplayer extends BukkitRunnable{
	Map<Projectile, Consumer<Location>> prjs = new HashMap<>();
	private Set<Projectile> toRemove = new HashSet<>();
	private boolean running;
	private long period;
	private CustomSuitPlugin plugin;
	
	public PProjectileDisplayer(CustomSuitPlugin plugin, long period) {
		this.plugin = plugin;
		this.period = period;
		this.running = false;
	}
	
	@Override
	public void run() {
		this.running = true;
		if (prjs.isEmpty()) {
			sleep(); return;
		}
		for (Projectile prj : prjs.keySet()) {
			if (!prj.isDead()) {
				play(prj);
			} else {
				toRemove.add(prj);
			}
		}
		toRemove.forEach(prj->prjs.remove(prj));
		toRemove.clear();
	}
	
	private void play(Projectile prj){
		prjs.get(prj).accept(prj.getLocation());
	}
	
	private void sleep(){
		running = false;
		this.cancel();
	}
	
	private void start(){
		runTaskTimer(plugin, 0, period);
	}
	
	public void registerPProjectile(Projectile prj, Consumer<Location>effect) {
		if (!SuitUtils.anyNull(prj, effect)) {
			boolean toRun = prjs.isEmpty();
			prjs.put(prj, effect);
			if(toRun && !running) start();
		}
	}	
}
