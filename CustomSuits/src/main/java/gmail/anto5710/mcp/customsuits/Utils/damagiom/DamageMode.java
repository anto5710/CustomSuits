package gmail.anto5710.mcp.customsuits.Utils.damagiom;

import org.bukkit.entity.Projectile;
import gmail.anto5710.mcp.customsuits.Utils.metadative.Metadative;

public class DamageMode {
	public static DamageMode NONE = new DamageMode(false, false, 1);
	public static DamageMode X_TEN = new DamageMode(true, false, 2D);
	public static DamageMode X_TEN_FIREWORK = new DamageMode(true, true, 2D);
	
	public static final String FIREWORK = "g.projectiler.FIREWORK",   // boolean
							   HEADSHOT = "g.projectiler.HEADSHOT";  // double 배율
		
	public final boolean allowHeadshot;
	public final boolean firework;
	public final double headshot_multiplier;
	
	public DamageMode(boolean headshot, boolean firework, double headshot_ratio){
		this.allowHeadshot = headshot;
		this.firework = firework;
		this.headshot_multiplier = headshot_ratio;
	}
	
	public static DamageMode retrieve(Projectile prj){
		boolean allowHeadshot = prj.hasMetadata(HEADSHOT);	
		double headshot_ratio = allowHeadshot ? Metadative.getDouble(prj, HEADSHOT) : 1;
		boolean firework = Metadative.getBoolean(prj, FIREWORK);
		return new DamageMode(allowHeadshot, firework, headshot_ratio);
	}
}
