package mgear;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.InventoryUtil;
import gmail.anto5710.mcp.customsuits.Utils.ItemUtil;
import gmail.anto5710.mcp.customsuits.Utils.ParticleUtil;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.MapEncompassor;

public class MainGear extends MapEncompassor<Player, Spindle[]>{
	public static ActiveSpindleEffector spindler;
	public static String tname = ChatColor.DARK_RED+"Klingen-auslöser"; 
	public static ItemStack trigger = ItemUtil.createWithName(Material.IRON_SWORD, tname);
	
	public MainGear(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
		spindler = new ActiveSpindleEffector(plugin, 10);
	}
	
	@Override
	public boolean toRemove(Player p) {
		return !p.isOnline() || p.isDead();
	}
	
	@Override
	protected void discard(Player e) {
		for(Spindle spindle : get(e)){
			if(spindle.anchored()) spindle.retrieve();
		}
		super.discard(e);
	}
	
	@Override
	public void particulate(Player p, Spindle[]set) {
		Vector Vf,Vp;
		for(Spindle spindle : set){
//			System.out.println("Catapulted: "+spindle.catapult);
//			System.out.println("Anchored: "+spindle.anchor);
			
			if(spindle.catapulted()){
				Location aloc = spindle.catapult.getLocation();
				
				if(spindle.anchored()){ //pulling 
					if(spindle.tension==null) spindle.updateTension();
					
					Location ploc = p.getLocation();
					Vp=p.getVelocity();
					Vf = Vp.add(spindle.tension);
					
					if(p.isSneaking()){
						ParticleUtil.playEffect(Particle.CLOUD, p.getLocation().add(0,1,0),3);
						Vf.add(ploc.getDirection().normalize().multiply(0.1));
					}
					p.setVelocity(Vf);
				}else{ // on air								
					ParticleUtil.playDust(aloc, 4, Color.BLACK, 0.5f);
				}
			}	
		}
	}

	@Override
	public Spindle[] defaultVal(Player p) {
		Spindle[]set = {new Spindle(p), new Spindle(p)};
		return set;
	}
	
	private void toggle(Spindle s){
		if(s.anchored()){
			s.retrieve();
			
		} else if(s.catapulted()){ // on air
			if(s.catapult.isDead()){// cooltimed
				SuitUtils.playSound(s.catapult, Sound.ENTITY_CREEPER_HURT, 1, 1);
			}else { // cooltime	
				s.catapult.remove();
				SuitUtils.runAfter(()-> s.catapult=null, 20);
			}
		} else{
			s.catapult();
			spindler.register(s.uuid, s);
		}
	}
	
	@EventHandler
	public void right(PlayerSwapHandItemsEvent e){
		Player p = (Player) e.getPlayer();
		if(ItemUtil.checkItem(trigger, e.getOffHandItem())){
			if(!isRegistered(p)) register(p);
			
			toggle(get(p)[1]);
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void left(PlayerDropItemEvent e){
		Player p = e.getPlayer();
		if(ItemUtil.checkItem(trigger, InventoryUtil.getOffItem(p))){
			if(!isRegistered(p)) register(p);
			
			toggle(get(p)[0]);
			e.setCancelled(true);
		}
	}
}
