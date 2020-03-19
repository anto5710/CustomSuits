package mgear;

import javax.annotation.Nonnull;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.MapEncompassor;
import gmail.anto5710.mcp.customsuits.Utils.metadative.Metadative;

public class ActiveSpindleEffector extends MapEncompassor<String,Spindle>{
	
	protected ActiveSpindleEffector(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
	}
	
	@Override
	public void particulate(String uuid, Spindle spindle) {		
		if(spindle.anchored()){
			spindle.updateTension();
		}
	}
	
	public Spindle getSpindle(@Nonnull Entity a){
		return a.hasMetadata(Spindle.SPINDLE)? 
				get(Metadative.excavatext(a, Spindle.SPINDLE)):null;
	}

	@Override
	public boolean toRemove(String e) {
		Spindle spindle = get(e);
		return !spindle.catapulted() || spindle.p.isDead() || !spindle.p.isOnline();
	}
	
	@EventHandler
	public void toAnchor(ProjectileHitEvent e){
		Projectile proj = e.getEntity();
		if(Spindle.isCatapult(proj)){
			Spindle spindle = getSpindle(proj);	
			SuitUtils.playSound(proj, Sound.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, 2, 9);
			SuitUtils.playSound(proj, Sound.BLOCK_IRON_DOOR_OPEN, 4, 9);
			SuitUtils.playSound(proj, Sound.ENTITY_ARROW_HIT_PLAYER, 5, 9);
			
			
			if(spindle != null && spindle.catapult == proj){
				if(e.getHitEntity()==null){
					spindle.anchor();
				}else{
					spindle.anchor(e.getHitEntity());
				}
			}
		}
	}
	
	@EventHandler
	public void onAnchorDiscord(VehicleExitEvent e){
		Entity passenger =e.getExited(); 
		if(Spindle.isPseudoAnchor(passenger)){
			Spindle spindle = getSpindle(passenger);
			if(spindle!=null && e.getVehicle() == spindle.catapult){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onDamageToSpindle(EntityDamageEvent e){
		Spindle spindle = getSpindle(e.getEntity());
		if(spindle!=null){
			System.out.println(e.getEntity()+" DAMage cancelled");
			e.setCancelled(true);
		}
	}

	@Override
	public Spindle defaultVal(String uuid) {return null;}
}
