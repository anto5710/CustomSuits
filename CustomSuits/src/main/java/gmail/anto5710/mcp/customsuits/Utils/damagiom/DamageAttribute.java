package gmail.anto5710.mcp.customsuits.Utils.damagiom;

import org.bukkit.entity.Projectile;

import gmail.anto5710.mcp.customsuits.Utils.metadative.Metadative;

public class DamageAttribute {
	public static DamageAttribute NONE = new DamageAttribute(false, false, 1);
	public static DamageAttribute X_TEN = new DamageAttribute(true, false, 2D);
	public static DamageAttribute X_TEN_FIREWORK = new DamageAttribute(true, true, 2D);
	
	public static final String 
		FIREWORK = "g.projectiler.FIREWORK",   // boolean
		HEADSHOT = "g.projectiler.HEADSHOT";  // double 배율
		
	public final boolean allowHeadshot;
	public final boolean firework;
	public final double headshot_multiplier;
	
	public DamageAttribute(boolean headshot, boolean firework, double headshot_ratio){
		this.allowHeadshot = headshot;
		this.firework = firework;
		this.headshot_multiplier = headshot_ratio;
	}
	
	public static DamageAttribute retrieve(Projectile prj){
		boolean allowHeadshot = prj.hasMetadata(HEADSHOT);
		double headshot_ratio = allowHeadshot ? Metadative.excavate(prj, HEADSHOT) : 1;
		boolean firework = Metadative.excavatruth(prj, FIREWORK);
		return new DamageAttribute(allowHeadshot, firework, headshot_ratio);
	}
}
