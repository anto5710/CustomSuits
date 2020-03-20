package mgear;

import javax.annotation.Nonnull;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

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
			if((!spindle.catapult.isOnGround()||t%6==0) && Spindle.isPseudoAnchor(spindle.anchor)){
				spindle.integrate();
			}
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
			if(spindle != null && spindle.catapult == proj){
				SuitUtils.playSound(proj, Sound.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, 2, 9);
				SuitUtils.playSound(proj, Sound.BLOCK_IRON_DOOR_OPEN, 4, 9);
				SuitUtils.playSound(proj, Sound.ENTITY_ARROW_HIT_PLAYER, 5, 9);
				SuitUtils.playSound(proj, Sound.BLOCK_PISTON_EXTEND, 1, 11);
				
				if(e.getHitEntity()==null){
					spindle.anchor();
				}else{
					spindle.anchor(e.getHitEntity());
				}
			}
		}
	}
	
	@EventHandler
	public void death(EntityDeathEvent e){
		Spindle spindle = getSpindle(e.getEntity());
		if(spindle!=null){
			spindle.rejoice();
		}
	}
	
	public void register(@Nonnull Spindle spindle) {
		register(spindle.uuid, spindle);
	}

	@EventHandler
	public void onDamageToSpindle(EntityDamageEvent e){
		Entity entity = e.getEntity();
		Spindle spindle = getSpindle(entity);
		
		if(spindle!=null && (Spindle.isPseudoAnchor(entity) || Spindle.isCatapult(entity)) && e.getDamage() > 20){
			spindle.rejoice();
		}
	}

	@Override
	public Spindle defaultVal(String uuid) {return null;}
}
