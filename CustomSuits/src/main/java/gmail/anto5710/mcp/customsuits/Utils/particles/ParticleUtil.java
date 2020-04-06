package gmail.anto5710.mcp.customsuits.Utils.particles;

import java.util.function.Consumer;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

public class ParticleUtil {

	public static void playEffect(Particle effect, Location loc, int amount){
		remoteEffect(loc, p->p.spawnParticle(effect, loc, amount, 0, 0, 0, 0));
	}

	public static void playBlockEffect(Particle effect, Location loc, int amount, BlockData data){
		playBlockEffect(effect, loc, amount, 0, data);
	}

	public static void playBlockEffect(Particle effect, Location loc, int amount, double speed, BlockData data) {
		remoteEffect(loc, p-> p.spawnParticle(effect, loc, amount, 0,0,0, speed, data));
	
	}

	public static void playSpell(Particle effect, Location loc, int amount, double R, double G, double B,  double extra){
		remoteEffect(loc, p-> p.spawnParticle(effect, loc, amount, R/255, G/255, B/255, extra));
	}

	public static void playDust(Location loc, int amount, Color color, float size){
		playDust(loc, 0, 0, 0, 0, 0, color, size);
	}
	
	public static void playDust(Location loc, int amount, DustOptions option){
		playDust(loc, 0, 0, 0, amount, 0, option);
	}
		
	public static void playDust(Location loc, double x_spread , double y_spread , double z_spread, int amount , double extra , Color color, float size){
		DustOptions option = new DustOptions(color, size);
		playDust(loc, x_spread, y_spread, z_spread, amount, extra, option);
	}

	public static void playDust(Location loc, double x_spread , double y_spread , double z_spread, int amount , double extra , DustOptions option){
		remoteEffect(loc, p-> p.spawnParticle(Particle.REDSTONE, loc, amount, x_spread, y_spread, z_spread, extra, option));
	}

	/**
	 * @param loc
	 * @param 1~24
	 */
	public static void playNote(Location loc, int notecolor){ 
		remoteEffect(loc, p->p.spawnParticle(Particle.NOTE, loc, 0, notecolor/24D, 0, 0, 1));
	}
	
	public static void playEffect(Particle effect, Location loc, int amount, double speed) {
		remoteEffect(loc,  p-> p.spawnParticle(effect, loc, 0, 0, 0, amount, speed));
	}

	public static void playEffect(Particle effect , Location loc, double x_spread , double y_spread , double z_spread, int amount, double extra, Object data){
		remoteEffect(loc, p-> p.spawnParticle(effect, loc, amount, x_spread, y_spread, z_spread, extra, data));
	}

	private static void remoteEffect(Location loc, Consumer<Player> effect){
		remoteEffect(loc.getWorld(), effect);
	}

	private static void remoteEffect(World w, Consumer<Player> effect){
		for(Player p : w.getPlayers()){
			effect.accept(p);
		}
	}
}
