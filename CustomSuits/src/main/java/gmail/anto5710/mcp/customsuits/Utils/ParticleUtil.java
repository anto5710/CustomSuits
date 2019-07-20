package gmail.anto5710.mcp.customsuits.Utils;

import java.util.function.Consumer;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

public class ParticleUtil {

	public static void playEffect(Particle effect, Location location, int amount){
		ParticleUtil.remoteEffect(location, p->p.spawnParticle(effect, location, amount, 0, 0, 0, 0));
	}

	public static void playBlockEffect(Particle effect, Location location, int amount, BlockData data){
		ParticleUtil.playBlockEffect(effect, location, amount, 0, data);
	}

	public static void playBlockEffect(Particle effect, Location location, int amount, double speed, BlockData data) {
		ParticleUtil.remoteEffect(location, p-> p.spawnParticle(effect, location, amount, 0,0,0, speed, data));
	}

	public static void playSpell(Particle effect, Location location, int amount, double R, double G, double B,  double extra){
		ParticleUtil.remoteEffect(location, p-> p.spawnParticle(effect, location, amount, R/255, G/255, B/255, extra));
	}

	public static void playDust(Particle effect, Location location, double x_spread , double y_spread , double z_spread, int amount , double extra , Color color, float size){
		DustOptions option = new DustOptions(color, size);
		playDust(effect, location, x_spread, y_spread, z_spread, amount, extra, option, size);
	}

	public static void playDust(Particle effect, Location location, double x_spread , double y_spread , double z_spread, int amount , double extra , DustOptions option, float size){
		ParticleUtil.remoteEffect(location, p-> p.spawnParticle(effect, location, amount, x_spread, y_spread, z_spread, extra, option));
	}
	
	public static void playEffect(Particle effect, Location loc, int amount, double speed) {
		ParticleUtil.remoteEffect(loc,  p-> p.spawnParticle(effect, loc, 0, 0, 0, amount, speed));
	}

	public static void playEffect(Particle effect , Location location, double x_spread , double y_spread , double z_spread, int amount, double extra, Object data){
		ParticleUtil.remoteEffect(location, p-> p.spawnParticle(effect, location, amount, x_spread, y_spread, z_spread, extra, data));
	}

	private static void remoteEffect(Location loc, Consumer<Player> effect){
		ParticleUtil.remoteEffect(loc.getWorld(), effect);
	}

	private static void remoteEffect(World w, Consumer<Player> effect){
		for(Player p : w.getPlayers()){
			effect.accept(p);
		}
	}
}
