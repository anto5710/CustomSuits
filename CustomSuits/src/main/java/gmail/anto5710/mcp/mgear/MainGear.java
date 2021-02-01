package gmail.anto5710.mcp.mgear;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.ColorUtil;
import gmail.anto5710.mcp.customsuits.Utils.PacketUtil;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.standardized.SSMapEncompassor;
import gmail.anto5710.mcp.customsuits.Utils.items.InventoryUtil;
import gmail.anto5710.mcp.customsuits.Utils.items.ItemUtil;
import gmail.anto5710.mcp.customsuits.Utils.particles.ParticleUtil;
import net.minecraft.server.v1_16_R3.PacketPlayOutAnimation;

public class MainGear extends SSMapEncompassor<Player, Spindle[]>{
	public static ActiveSpindleEffector spindler;
	public static final String trigger_name = ChatColor.DARK_RED+"3D Maneuver Gear"; 
	
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
			if(spindle.outOfGas()) {
				spindle.retrieve(true, 2);
				
			}else if (spindle.hasCatapulted()) {
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
						spindle.sufficeGas(Values.GearGas_Shift);
					}
					spindle.sufficeGas(Values.GearGas_Pull);
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
			if (s.inCooldown() || s.outOfGas()) {
				SuitUtils.playSound(s.getPlayer(), Sound.ENTITY_CREEPER_HURT, 2, 1);
				SuitUtils.playSound(s.getPlayer(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1, 1);
			} else {
				s.catapult();
				spindler.register(s);
				s.sufficeGas(Values.GearGas_Catapult);
			}
		}
	}
	
	public static void speedometre(Player p, Spindle s) {
		if(InventoryUtil.inMainHand(p, CustomSuitPlugin.mg_trigger)){
			ItemStack trigger = InventoryUtil.getMainItem(p);
			Vector v = s.getTension();
			
			
			String leftatus = ChatColor.DARK_RED+"▮", rightatus = ChatColor.DARK_RED+"▮";
			Spindle[] spindles = CustomSuitPlugin.gearer.get(p);
			if(spindles!=null && spindles.length==2) {
				if(spindles[0]!=null && spindles[0].hasCatapulted()) leftatus = ChatColor.RED+"╹";
				
				if(spindles[1]!=null && spindles[1].hasCatapulted()) rightatus = ChatColor.RED+"╹";
			}
			
			
			ItemUtil.name(trigger,  trigger_name +
					String.format(ColorUtil.colorf("(Gas: %s%s<//>%s "
							+ "|| Δ<b><i>x<//>: <yellow>%.2f<//>"
							+ " | Δ<b><i>y<//>: <yellow>%.2f<//>"
							+ " | Δ<b><i>z<//>: <yellow>%.2f<//>)"),
					
					leftatus, gaage(10, s.getGas(), ChatColor.WHITE+"▤", ChatColor.GRAY+"▤"), rightatus, 
					v.getX(), v.getY(), v.getZ()));
		}	
	}
	
	private static String gaage(int length, float percentage, String fill, String empty) {
		int toFill = Math.round(length*percentage/100);
		String gauge = "";
		for (int i =0; i<length; i++) {
			gauge += i < toFill ? fill : empty;					
		}
		return gauge;
	}
	
	@EventHandler
	public void left(PlayerDropItemEvent e){
		Player p = e.getPlayer();
		if(ItemUtil.compare(CustomSuitPlugin.mg_trigger, InventoryUtil.getOffItem(p)) && InventoryUtil.droppedFromMainHand(e)){
			autoregister(p);
			
			toggle(get(p)[0]);
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void right(PlayerSwapHandItemsEvent e){
		Player p = (Player) e.getPlayer();
		if(ItemUtil.compare(CustomSuitPlugin.mg_trigger, e.getOffHandItem())){
			autoregister(p);
			
			toggle(get(p)[1]);
			e.setCancelled(true);
		}
	}

	private static boolean isDirectAttack(@Nonnull EntityDamageByEntityEvent e) {
		return e.getCause() == DamageCause.ENTITY_ATTACK || e.getCause() == DamageCause.ENTITY_SWEEP_ATTACK;
	}
	
	@EventHandler 
	public void slay(EntityDamageByEntityEvent e) {
		if(isDirectAttack(e) && e.getDamager().getType()==EntityType.PLAYER && isRegistered((Player)e.getDamager())) {
			Player p = (Player) e.getDamager();
			if(InventoryUtil.inAnyHand(p, CustomSuitPlugin.mg_trigger)) {
				double v2 = p.getVelocity().lengthSquared();
				double amplifier = Math.max(1,v2);
				double damage = e.getDamage()*amplifier;
				p.sendMessage(String.format(ColorUtil.colorf("Speed²: <yellow>%.1f<//>  |  Damage: <yellow>%.1f<//>"), v2,damage));
				e.setDamage(e.getDamage()*amplifier);
				SuitUtils.runAfter(()->{
					PacketUtil.broadcastPacket(new PacketPlayOutAnimation(PacketUtil.nmsEntiy(p), 3), p.getServer());	
				}, 6);
			}
		}
	}

}
