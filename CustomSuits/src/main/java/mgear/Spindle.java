package mgear;

import java.util.UUID;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import gmail.anto5710.mcp.customsuits.Utils.MathUtil;
import gmail.anto5710.mcp.customsuits.Utils.PotionBrewer;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.metadative.Metadative;

public class Spindle {
	protected final Player p;
	protected Arrow catapult;
	protected LivingEntity anchor;
	protected Vector tension;
	protected final String uuid;
	
	public Spindle(Player p){
		this.p = p;
		this.uuid = UUID.randomUUID().toString();
	}
	
	public static String ANCHOR = "ANCHORE";
	public static String PSEUDO_ANCHOR = "AFAKE ANCHOR";
	public static String CATAPULT = "CATAPULTE";
	public static String SPINDLE = "SPINDLE WHO";
	
	public void catapult(){
		Location loc = p.getEyeLocation();
		Location target = SuitUtils.getTargetLoc(p, 100);
		float d = (float) target.distance(loc);
		
		catapult = p.getWorld().spawnArrow(loc, target.subtract(loc).toVector(), 0.2f*d, 5f);
		
		Metadative.imprint(catapult, CATAPULT, true);
		Metadative.imprint(catapult, SPINDLE, uuid);
	}
	
	public boolean anchored(){
		return anchor!=null;
	}
	
	public boolean catapulted(){
		return catapult != null;
	}
	
	public static boolean isPseudoAnchor(@Nonnull Entity anchor){
		return anchor.getType()==EntityType.BAT && Metadative.excavatruth(anchor, PSEUDO_ANCHOR);
	}
		
	public void anchor(Entity toAnchorAt){
		if(catapulted()){
			if(anchored() && isPseudoAnchor(anchor)) anchor.remove();
			
			if(toAnchorAt instanceof LivingEntity && ((LivingEntity)toAnchorAt).setLeashHolder(p)){
				anchor = (LivingEntity) toAnchorAt;
			}else{
				anchor = spawnPseudoAnchor(catapult.getLocation());
				anchor.setLeashHolder(p);
			} 
			Metadative.imprint(anchor, ANCHOR, true);
			Metadative.imprint(anchor, SPINDLE, uuid);
		}
	}
	
	public void anchor(){
		anchor(catapult);
	}

	private static LivingEntity spawnPseudoAnchor(Location loc){
		LivingEntity anchor = loc.getWorld().spawn(loc, Bat.class);
		anchor.setAI(false);
		anchor.setSilent(true);
		anchor.setInvulnerable(true);

		PotionBrewer.permaPotion(anchor, PotionEffectType.INVISIBILITY, 10);
		Metadative.imprint(anchor, PSEUDO_ANCHOR, true);
		return anchor;
	}
	
	public void reset(){
		if(isPseudoAnchor(anchor)) anchor.remove();
		
		anchor = null;
		catapult.remove(); catapult = null;
	}
	
	private static double pmass = 1.5;
	public Vector updateTension(){
		Vector diff = MathUtil.disposition(anchor, p);
		double R2 = diff.lengthSquared();
		double speed2 = Math.max(1, p.getVelocity().lengthSquared());
		
		double centrip = pmass*speed2/(R2>10*10? R2 : 125);
		tension = diff.multiply(centrip); 
		System.out.println("X: "+tension.getX() + " Y:  " + tension.getY() + " Z: " + tension.getZ());
		return tension;
	}
		
	public static boolean isAnchor(@Nonnull Entity entity) {
		return entity instanceof LivingEntity && Metadative.excavatruth(entity, ANCHOR);
	}
	
	public static boolean isCatapult(@Nonnull Entity entity) {
		return entity.getType()==EntityType.ARROW && Metadative.excavatruth(entity, CATAPULT);
	}
}
