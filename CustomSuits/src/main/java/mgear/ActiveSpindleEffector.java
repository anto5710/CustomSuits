package mgear;

import javax.annotation.Nonnull;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.MapEncompassor;
import gmail.anto5710.mcp.customsuits.Utils.metadative.Metadative;

public class ActiveSpindleEffector extends MapEncompassor<String,Spindle>{
	protected ActiveSpindleEffector(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
	}
	
	@EventHandler
	public void toAnchor(ProjectileHitEvent e){
		Projectile proj = e.getEntity();
		if(Spindle.isCatapult(proj)){
			Spindle spindle = getSpindle(proj);	
			if(spindle != null && spindle.catapult == proj){
				if(e.getHitEntity()==null){
					spindle.anchor();
				}else{
					spindle.anchor(proj);
				}
			}
		}
	}
	
	@EventHandler
	public void onAnchorDiscord(VehicleExitEvent e){
		Entity passenger =e.getExited(); 
		if(Spindle.isPseudoAnchor(passenger)){
			Spindle spindle = getSpindle(passenger);
			if(spindle!=null && e.getVehicle().equals(spindle.catapult)){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onDamageToSpindle(EntityDamageEvent e){
		Spindle spindle = getSpindle(e.getEntity());
		if(spindle!=null) spindle.reset();
	}
	
	public Spindle getSpindle(@Nonnull Entity a){
		return a.hasMetadata(Spindle.SPINDLE)?
				get(Metadative.excavatext(a, Spindle.SPINDLE)):null;
	}
	
	@Override
	public boolean toRemove(String e) {
		return !get(e).catapulted();
	}

	@Override
	public void particulate(String uuid, Spindle spindle) {
		if(spindle.anchored()) spindle.updateTension();
		
	}

	@Override
	public Spindle defaultVal(String uuid) {return null;}
}
