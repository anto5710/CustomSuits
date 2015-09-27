package gmail.anto5710.mcp.customsuits.Man;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.PlayerEffect;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.WeaponListner;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.ManUtils;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;
import gmail.anto5710.mcp.customsuits.Utils.WeaponUtils;
import gmail.anto5710.mcp.customsuits._Thor.Repeat;

import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Bomb extends BukkitRunnable{
	CustomSuitPlugin plugin;
	static HashMap<Item, Player>Bombs = new HashMap<>();
	static HashMap<Item, Player>Smoke = new HashMap<>();
	
	public Bomb(CustomSuitPlugin plugin){
		this.plugin = plugin;
	}
	@Override
	public void run() throws IllegalStateException{
		if(Bombs == null && Smoke == null ){
			try {
				ThorUtils.cancel(getTaskId());
				
			} catch (IllegalStateException e) {
				
			}
		}
		run_Bombs();
		run_Smoke();
		
	}
	public static void remove(Item item){
		if(Bombs.containsKey(item)){
			Bombs.remove(item);
			
		}
		
		if(Smoke.containsKey(item)){
			Smoke.remove(item);
		}
	}
	
	private void run_Smoke() {
		if(Smoke.size()==1){
			
			Iterator<Item>iterator = Smoke.keySet().iterator();
			Item item =iterator.next();
			item.setPickupDelay(20);
			item.setFireTicks(0);
			if(item.isDead()){
				remove(item);
			}
			
			Location location  = item.getLocation();
			SuitUtils.playEffect(location, Values.ManSmokeEffect, 20, 0, 3);
			item.setPickupDelay(20);
			if(ThorUtils.isOnGround(item)){
				if(Smoke_Repeat.SmokeCount.size() == 0){
					new Smoke_Repeat(plugin).runTaskTimer(plugin, 0, 5);
				}
				Smoke_Repeat.SmokeCount.put(item, (long) 0);
				remove(item);
			}
		}else{
			for(Item item : Smoke.keySet()){
				item.setFireTicks(0);
				if(item.isDead()){
					remove(item);
				}
				Location location  = item.getLocation();
				SuitUtils.playEffect(location, Values.ManSmokeEffect, 20, 0, 3);
				if(ThorUtils.isOnGround(item)){
					if(Smoke_Repeat.SmokeCount.size() == 0){
						new Smoke_Repeat(plugin).runTaskTimer(plugin, 0, 5);
					}
					Smoke_Repeat.SmokeCount.put(item, (long) 0);
					remove(item);
				}
			}
		}
		
	}
	
	private void run_Bombs() {
	if(Bombs.size()==1){
			Iterator<Item> iterator = Bombs.keySet().iterator();
			Item item =iterator.next();
			if(item.isDead()){
				remove(item);
			}
			Location location = item.getLocation();
			SuitUtils.playEffect(location, Values.ManBombEffect, 10, 0, 5);
			item.getWorld().playSound(location, Values.ManBombSound, 5F,6F);
			item.setFireTicks(0);
			if(ThorUtils.isOnGround(item)){
				remove(item);
				item.remove();
				SuitUtils.createExplosion(location, 6.0F, true, true);
			}
		}else{
			for(Item item : Bombs.keySet()){
				if(item.isDead()){
					remove(item);
				}
				Location location = item.getLocation();
				SuitUtils.playEffect(location, Values.ManBombEffect, 10, 0, 5);
				item.getWorld().playSound(location, Values.ManBombSound, 5F,6F);
				item.setFireTicks(0);
				if(ThorUtils.isOnGround(item)){
					remove(item);
					item.remove();
					SuitUtils.createExplosion(location, 6.0F, true, true);
				}
			}
		}
		
	}
}
