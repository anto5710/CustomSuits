package mgear;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.InventoryUtil;
import gmail.anto5710.mcp.customsuits.Utils.ItemUtil;
import gmail.anto5710.mcp.customsuits.Utils.PacketUtil;
import gmail.anto5710.mcp.customsuits.Utils.ParticleUtil;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.SSMapEncompassor;
import net.minecraft.server.v1_15_R1.PacketPlayOutAnimation;

public class MainGear extends SSMapEncompassor<Player, Spindle[]>{
	public static ActiveSpindleEffector spindler;
	public static final String trigger_name = ChatColor.DARK_RED+"Klingen-auslöser"; 
	
	public MainGear(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
		spindler = new ActiveSpindleEffector(plugin, 10);
	}
	
	@Override
	public boolean toRemove(Player p) {
		return !p.isOnline() || p.isDead();
	}
	
	@Override
	public void discard(Player e) {
		for(Spindle spindle : get(e)){
			spindle.retrieve(true, 0);
			spindler.discard(spindle.getUUID());
		}
		super.discard(e);
	}
	
	@Override
	public void particulate(Player p, Spindle[]set) {
		Vector Vf, Vp;
		boolean idling = true;
		for (Spindle spindle : set) {
			if (spindle.hasCatapulted()) {
				idling = false;

				if (spindle.hasAnchored()) { // pulling
					if (spindle.getTension() == null) spindle.updateTension();
					
					Location ploc = p.getLocation();
					Vp = p.getVelocity();
					Vf = Vp.add(spindle.getTension());

					if (p.isSneaking()) {
						ParticleUtil.playEffect(Particle.CLOUD, p.getLocation().add(0, 1, 0), 3);
						Vf.add(ploc.getDirection().multiply(0.1));
						SuitUtils.playSound(ploc, Sound.BLOCK_LAVA_EXTINGUISH, 0.2f, 0.1f);
						SuitUtils.playSound(ploc, Sound.ENTITY_TNT_PRIMED, 0.2f, 0.1f);
					}
					p.setVelocity(Vf);
				} else { // on air
					ParticleUtil.playEffect(Particle.CLOUD, spindle.getCatapult().getLocation(), 1);
				}
			}
		}
		if(idling) sleep(p);
	}

	@Override
	public Spindle[] defaultVal(Player p) {
		Spindle[]set = {new Spindle(p), new Spindle(p)};
		return set;
	}
	
	private void toggle(Spindle s){
		if (s.hasCatapulted()) {
			if (s.hasAnchored()) { // landed
				s.retrieve(true, 6);
			} else { // in air
				s.retrieve(true, 13);
			}
		} else {
			if (s.inCooldown()) {
				SuitUtils.playSound(s.getPlayer(), Sound.ENTITY_CREEPER_HURT, 2, 1);
				SuitUtils.playSound(s.getPlayer(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1, 1);
			} else {
				s.catapult();
				spindler.register(s);
			}
		}
	}
	
	public static void speedometre(Player p, Vector v) {
		if(InventoryUtil.inMainHand(p, CustomSuitPlugin.mg_trigger)){
			ItemStack trigger = InventoryUtil.getMainItem(p);
			ItemUtil.name(trigger, 
				MainGear.trigger_name+ String.format(
				""+
				ChatColor.WHITE+ " (X: "+ChatColor.YELLOW+"%f"+
				ChatColor.WHITE+" | Y: "+ChatColor.YELLOW+"%f"+
				ChatColor.WHITE+" | Z: "+ChatColor.YELLOW+"%f"+
				ChatColor.WHITE+")", v.getX(), v.getY(), v.getZ()));
		}
	}
	
	@EventHandler
	public void right(PlayerSwapHandItemsEvent e){
		Player p = (Player) e.getPlayer();
		if(ItemUtil.checkItem(CustomSuitPlugin.mg_trigger, e.getOffHandItem())){
			autoregister(p);
			
			toggle(get(p)[1]);
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void left(PlayerDropItemEvent e){
		Player p = e.getPlayer();
		if(ItemUtil.checkItem(CustomSuitPlugin.mg_trigger, InventoryUtil.getOffItem(p)) && InventoryUtil.droppedFromMainHand(e)){
			autoregister(p);
		
			toggle(get(p)[0]);
			e.setCancelled(true);
		}
	}
	
	@EventHandler 
	public void slay(EntityDamageByEntityEvent e) {
		if(e.getDamager().getType()==EntityType.PLAYER && isRegistered((Player)e.getDamager())) {
			Player p = (Player) e.getDamager();
			if(InventoryUtil.inAnyHand(p, CustomSuitPlugin.mg_trigger)) {
				double v2 = p.getVelocity().lengthSquared();
				double amplifier = Math.max(1,v2);
				double damage = e.getDamage()*amplifier;
				p.sendMessage("Speed^2: "+v2+"  Damage: "+damage);
				e.setDamage(e.getDamage()*amplifier);
				SuitUtils.runAfter(()->PacketUtil.broadcastPacket(new PacketPlayOutAnimation(PacketUtil.nmsEntiy(p), 3), p.getServer()),6);
			}
		}
	}

}
