package gmail.anto5710.mcp.customsuits.CustomSuits;
import net.minecraft.server.v1_8_R2.EntityFireworks;
import net.minecraft.server.v1_8_R2.EntityPlayer;
import net.minecraft.server.v1_8_R2.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_8_R2.PlayerConnection;
import net.minecraft.server.v1_8_R2.World;
import net.minecraft.server.v1_8_R2.WorldServer;

import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkPlay
  extends EntityFireworks
{
  Player[] players = null;
  
  public FireworkPlay(World world, Player... p)
  {
    super(world);
    this.players = p;
    a(0.25F, 0.25F);
    
  }
  
  boolean gone = false;
//  private static void setWorldStatic(World world, boolean static_boolean) throws Exception {
//      java.lang.reflect.Field static_field = World.class.getDeclaredField("isStatic");
//    
//      static_field.setAccessible(true);
//      static_field.set(world, static_boolean);
//  }
  @Override
	public void t_() {
		if (gone) {
			return;
		}

	
					world.broadcastEntityEffect(this, (byte) 17);
		
		this.die();
	}
      
    
  
  
  public static void spawn(Location location, FireworkEffect effect, Player... players)
  {
    try
    {
      FireworkPlay firework = new FireworkPlay(((CraftWorld)location.getWorld()).getHandle(), players);
      FireworkMeta meta = ((Firework)firework.getBukkitEntity()).getFireworkMeta();
      meta.addEffect(effect);
      meta.setPower(0);
      ((Firework)firework.getBukkitEntity()).setFireworkMeta(meta);
      
      firework.setPosition(location.getX(), location.getY(), location.getZ());
      if (((CraftWorld)location.getWorld()).getHandle().addEntity(firework)) {
    	  
    	  firework.setInvisible(true);
    	  
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
