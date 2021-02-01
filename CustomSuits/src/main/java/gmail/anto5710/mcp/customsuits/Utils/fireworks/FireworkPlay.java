package gmail.anto5710.mcp.customsuits.Utils.fireworks;

import java.util.List;

import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import gmail.anto5710.mcp.customsuits.Utils.PacketUtil;
import net.minecraft.server.v1_16_R3.EntityFireworks;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_16_R3.World;

public class FireworkPlay extends EntityFireworks {
	public FireworkPlay(World world) {
		super(EntityTypes.FIREWORK_ROCKET, world);
		this.a(0.25F, 0.25F);
	}
	
	boolean gone = false;

	public static void spawn(Location loc, FireworkEffect effect) {
		try {
			
			World world = ((CraftWorld) loc.getWorld()).getHandle();
			FireworkPlay firework = new FireworkPlay(world);
			Firework bfire = (Firework) firework.getBukkitEntity();
			
			FireworkMeta meta = bfire.getFireworkMeta();
			meta.addEffect(effect);
			bfire.setFireworkMeta(meta);
			firework.setPosition(loc.getX(), loc.getY(), loc.getZ());

			if (world.addEntity(firework)) firework.setInvisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void tick() {
		if (gone) {
			return;
		}

		if (!world.isClientSide) {
			gone = true;

			CraftServer server = world.getServer();
			List<CraftPlayer>players = server.getOnlinePlayers();

			if(players!=null && players.size()>0){
				PacketUtil.broadcastPacket(new PacketPlayOutEntityStatus(this, (byte) 17), server);	
			}else world.broadcastEntityEffect(this, ((byte) 17));
			
			this.die();
		}
	}
}