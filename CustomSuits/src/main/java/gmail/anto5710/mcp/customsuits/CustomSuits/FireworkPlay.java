package gmail.anto5710.mcp.customsuits.CustomSuits;

import net.minecraft.server.v1_13_R2.EntityFireworks;

import net.minecraft.server.v1_13_R2.World;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;

import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkPlay extends EntityFireworks {
	Player[] players = null;

	public FireworkPlay(World world, Player... p) {
		super(world);
		players = p;
		this.a(0.25F, 0.25F);
	}

	boolean gone = false;

	@Override
	public boolean t(net.minecraft.server.v1_13_R2.Entity entity) {
		if (gone) {
			return false;
		}
		gone = true;
		world.broadcastEntityEffect(this, (byte) 17);
		world.removeEntity(this);
		return true;
	}

	public static void spawn(Location location, FireworkEffect effect, Player... players) {
		try {
			FireworkPlay firework = new FireworkPlay(((CraftWorld) location.getWorld()).getHandle(), players);
			FireworkMeta meta = ((Firework) firework.getBukkitEntity()).getFireworkMeta();
			meta.addEffect(effect);
			((Firework) firework.getBukkitEntity()).setFireworkMeta(meta);
			firework.setPosition(location.getX(), location.getY(), location.getZ());

			if ((((CraftWorld) location.getWorld()).getHandle()).addEntity(firework)) {
				firework.setInvisible(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}