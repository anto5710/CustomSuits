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
import gmail.anto5710.mcp.customsuits.Utils.MathUtil;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.MapEncompassor;
import gmail.anto5710.mcp.customsuits.Utils.metadative.Metadative;

public class ActiveSpindleEffector extends MapEncompassor<String,Spindle>{

	protected ActiveSpindleEffector(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
	}
	
	@Override
	public void particulate(String uuid, Spindle spindle) {		
		if(spindle.hasAnchored()){
			spindle.updateTension();
			MainGear.speedometre(spindle.getPlayer(), spindle);
			if(toIntegrate(spindle)) spindle.integrate();
		}
	}
	
	@Override
	public boolean toRemove(String e) {
		return !get(e).hasCatapulted();
	}
	
	public Spindle getSpindle(@Nonnull Entity a){
		return a.hasMetadata(Spindle.SPINDLE)? get(Metadative.getString(a, Spindle.SPINDLE)) : null;
	}

	
	private static final int INTEGRATERVAL = 6;
	private boolean toIntegrate(Spindle spindle){
		Entity target = spindle.getAnchorTarget();
		return spindle.pseudoAnchored() && target != null && 
				(t%INTEGRATERVAL == 0 || MathUtil.isMoving(target)); 
	}
	
	@EventHandler
	public void toAnchor(ProjectileHitEvent e){
		Projectile proj = e.getEntity();
		if(Spindle.isCatapult(proj)){
			Spindle spindle = getSpindle(proj);	
			if(spindle != null && spindle.getCatapult() == proj){
				SuitUtils.playSound(proj, Sound.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, 2, 9);
				SuitUtils.playSound(proj, Sound.BLOCK_IRON_DOOR_OPEN, 4, 9);
				SuitUtils.playSound(proj, Sound.ENTITY_ARROW_HIT_PLAYER, 5, 9);
				SuitUtils.playSound(proj, Sound.BLOCK_PISTON_EXTEND, 1, 11);
				
				if(e.getHitEntity()==null){
					spindle.anchor();
					if(SuitUtils.damageBlock(e.getHitBlock(), (0.6/25)*proj.getVelocity().length())) {
						spindle.retrieve(false, 0);
					}
				}else{
					spindle.anchor(e.getHitEntity());
				}
			}
		}
	}
	
	@EventHandler
	public void onAnchorDestroy(EntityDeathEvent e){
		Spindle spindle = getSpindle(e.getEntity());
		if(spindle!=null){
			spindle.retrieve(false, 0);
		}
	}
	
	@EventHandler
	public void onDamageToSpindle(EntityDamageEvent e){
		Entity entity = e.getEntity();
		Spindle spindle = getSpindle(entity);
		
		if(spindle!=null && (Spindle.isPseudoAnchor(entity) || Spindle.isCatapult(entity)) && e.getDamage() > 20){
			spindle.retrieve(false, 0);
		}
	}
	
	public void register(@Nonnull Spindle spindle) {
		register(spindle.getUUID(), spindle);
	}

	@Override
	public Spindle defaultVal(String uuid) {return null;}
}
