package gmail.anto5710.mcp.customsuits.Utils;

import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_13_R2.EnumItemSlot;
import net.minecraft.server.v1_13_R2.Packet;
import net.minecraft.server.v1_13_R2.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_13_R2.PacketPlayOutEntityEquipment;

public class PacketUtil {
	
	
	private static void sendPacket(Packet<?>packet, Player p){
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
	}
	
	public static void broadcastPacket(Packet<?>packet, Server s){
		if(packet==null) return;
		
		for(Player p: s.getOnlinePlayers()){
			sendPacket(packet, p);
		}
	}
	
	public static PacketPlayOutEntityDestroy newDestroyPacket(Entity e){
		if(e==null) return null;
		
		int id = ((CraftEntity)e).getHandle().getId();
		return new PacketPlayOutEntityDestroy(id);
	}
	
	public static void castDestroyPacket(Entity e){
		broadcastPacket(newDestroyPacket(e), e.getServer());
	}
	
	public static void castEquipmentPacket(Entity e, EnumItemSlot slot, ItemStack item){
		broadcastPacket(newEquipmentPacket(e, slot, item), e.getServer());
	}

	public static PacketPlayOutEntityEquipment newEquipmentPacket(Entity e, EnumItemSlot slot, ItemStack item){
		if(e==null) return null;
			
		int id = ((CraftEntity)e).getHandle().getId();
		
		return new PacketPlayOutEntityEquipment(id, slot, CraftItemStack.asNMSCopy(item));
	}
	
	
}
