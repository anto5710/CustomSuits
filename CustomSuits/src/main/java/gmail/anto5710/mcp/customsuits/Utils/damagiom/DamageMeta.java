package gmail.anto5710.mcp.customsuits.Utils.damagiom;

import org.bukkit.entity.Projectile;

import gmail.anto5710.mcp.customsuits.Utils.metadative.Metadative;

public class DamageMeta {
	public static DamageMeta NONE = new DamageMeta(false, false, 1);
	public static DamageMeta X_TEN = new DamageMeta(true, false, 2D);
	public static DamageMeta X_TEN_FIREWORK = new DamageMeta(true, true, 2D);
	
	public static final String 
		FIREWORK = "g.projectiler.FIREWORK",   // boolean
		HEADSHOT = "g.projectiler.HEADSHOT";  // double 배율
		
	public final boolean allowHeadshot;
	public final boolean firework;
	public final double headshot_multiplier;
	
	public DamageMeta(boolean headshot, boolean firework, double headshot_ratio){
		this.allowHeadshot = headshot;
		this.firework = firework;
		this.headshot_multiplier = headshot_ratio;
	}
	
	public static DamageMeta retrieve(Projectile prj){
		boolean allowHeadshot = prj.hasMetadata(HEADSHOT);	
		double headshot_ratio = allowHeadshot ? Metadative.excavate(prj, HEADSHOT) : 1;
		boolean firework = Metadative.excavatruth(prj, FIREWORK);
		return new DamageMeta(allowHeadshot, firework, headshot_ratio);
	}
}
