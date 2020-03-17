package mgear;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.InventoryUtil;
import gmail.anto5710.mcp.customsuits.Utils.ItemUtil;
import gmail.anto5710.mcp.customsuits.Utils.ParticleUtil;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.MapEncompassor;

public class Protogear extends MapEncompassor<Player,Entity[]>{
	private Map<Player, Vector> vs = new HashMap<>();
	
	public Protogear(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
	}

	@Override
	public boolean toRemove(Player e) {
		return e.isDead() || !e.isOnline();
	}
	@Override
	public void register(Player e) {
		super.register(e);
		vs.put(e, new Vector(0, 0, 0));
	}
	
	private static BlockData bdat = Material.ANVIL.createBlockData();
	@Override
	public void particulate(Player p, Entity[] set) {
		for(int i=0; i<set.length; i++){
			Entity a = set[i]; 
			if(a!=null){
				Location aloc = a.getLocation();
				if(a.getType()==EntityType.ARROW){
					ParticleUtil.playBlockEffect(Particle.BLOCK_DUST, aloc, 3, bdat);
				
				}else{ // BAT
					
					if(!((LivingEntity)a).isLeashed()){
						System.out.println("!!! unleashed");
						a.remove();
						if(a.getVehicle()!=null) a.getVehicle().remove();
						set[i]=null;
						
						continue;
					}
					if(p.isSneaking()){
					Vector diff = aloc.subtract(p.getLocation()).toVector();
					double R = diff.length();
					double v = p.getVelocity().length()+1.5;
					double m = 1.5;
			
					double Cp = m*v*v/(Double.max(R,6));
//					vs.put(p, diff.normalize().multiply(Cp));
						
//					if(p.isSneaking()){
//						Vector diff = aloc.subtract(p.getLocation()).toVector();
//						double R = diff.length();
//						double v = p.getVelocity().length()+2;
//						double m = 2;
//				
//						double Cp = m*v*v/(Double.max(R,2));
						p.setVelocity(p.getVelocity().add(diff.normalize().multiply(Cp)));
//					}
					}
				}
			}
		}
	}

	@Override
	public Entity[] defaultVal(Player e) {
		return new Entity[2];
	}
	
	public void shoot(Player p, int i){
		get(p)[i] = p.launchProjectile(Arrow.class);
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		Player p = e.getPlayer();
		if (ItemUtil.checkItem(trigger, e.getItemDrop().getItemStack())) {
			e.setCancelled(true);

			if(!isRegistered(p)) register(p);
			
			if(get(p)[1]!=null){
				get(p)[1].remove();
				get(p)[1]=null;
				
			}else shoot(p, 1);

		}
	}
	
	@EventHandler
	public void hit(ProjectileHitEvent e){
		if(e.getEntityType()==EntityType.ARROW){
			Arrow a = (Arrow) e.getEntity();
			for (Player p : maptia.keySet()) {
				Entity[] set = get(p);
				
				for (int i = 0; i < set.length; i++) {
					if (set[i] == a) {
						
						Bat b = a.getWorld().spawn(a.getLocation(), Bat.class);
						b.setAI(false);
						b.setSilent(true);
						b.setInvulnerable(true);
						a.addPassenger(b);
						
						b.setLeashHolder(p);
						set[i]=b;
					}
				}
			}
		}
	}
	
	static ItemStack trigger = new ItemStack(Material.IRON_SHOVEL);
	
	@EventHandler
	public void onRight(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(SuitUtils.isRightClick(e) && p.isSneaking() && InventoryUtil.inAnyHand(p, trigger)){
			if(!isRegistered(p)) register(p);
			
			if(get(p)[1]!=null){
				get(p)[1].remove();
				get(p)[1]=null;
				
			}else shoot(p, 1);
		}
	}
}
