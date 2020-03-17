package gmail.anto5710.mcp.customsuits.Utils;

import javax.annotation.Nonnull;

import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


import net.minecraft.server.v1_15_R1.EnumItemSlot;
import net.minecraft.server.v1_15_R1.Packet;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_15_R1.PlayerConnection;

public class PacketUtil {
	
	public static PlayerConnection connect(@Nonnull Player p){
		return ((CraftPlayer)p).getHandle().playerConnection;
	}
	
	private static void sendPacket(Packet<?>packet, Player p){
		connect(p).sendPacket(packet);
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
