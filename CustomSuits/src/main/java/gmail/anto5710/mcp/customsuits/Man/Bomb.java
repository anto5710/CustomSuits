package gmail.anto5710.mcp.customsuits.Man;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import gmail.anto5710.mcp.customsuits.CustomSuits.PlayEffect;
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
	public static HashMap<Item, Player>Bombs = new HashMap<>();
	public static HashMap<Item, Player>Smoke = new HashMap<>();
	
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

	private void run_Smoke() {
		if(Smoke.size()==1){
			
			Iterator<Item>iterator = Smoke.keySet().iterator();
			Item item =iterator.next();
			item.setPickupDelay(20);
			item.setFireTicks(0);
			if(item.isDead()){
				ManUtils.remove(item);
			}
			
			Location location  = item.getLocation();
			PlayEffect.play_Man_Smoke_Effect(location);
			item.setPickupDelay(20);
			if(ThorUtils.isOnGround(item)){
				if(Smoke_Repeat.SmokeCount.size() == 0){
					new Smoke_Repeat(plugin).runTaskTimer(plugin, 0, 5);
				}
				Smoke_Repeat.SmokeCount.put(item, (long) 0);
				ManUtils.remove(item);
			}
		}else{
			Iterator<Item> iterator = Smoke.keySet().iterator();
			ArrayList<Item>removed = new ArrayList<>();
			while(iterator.hasNext()){
				Item item = iterator.next();
				item.setFireTicks(0);
				if(item.isDead()){
					iterator.remove();
					removed.add(item);
				}
				Location location  = item.getLocation();
				PlayEffect.play_Man_Smoke_Effect(location);
				if(ThorUtils.isOnGround(item)){
					if(Smoke_Repeat.SmokeCount.size() == 0){
						new Smoke_Repeat(plugin).runTaskTimer(plugin, 0, 5);
					}
					Smoke_Repeat.SmokeCount.put(item, (long) 0);
					iterator.remove();
					removed.add(item);
				}
			}
			
				Smoke.remove(removed);
				removed.clear();
			
		}
		
	}
	
	private void run_Bombs()  {
	if(Bombs.size()==1){
			Iterator<Item> iterator = Bombs.keySet().iterator();
			Item item =iterator.next();
			if(item.isDead()){
				ManUtils.	remove(item);
			}
			Location location = item.getLocation();
			PlayEffect.play_Man_Bomb_Effect(location);
			item.getWorld().playSound(location, Values.ManBombSound, 5F,6F);
			item.setFireTicks(0);
			if(ThorUtils.isOnGround(item)){
				ManUtils.remove(item);
				item.remove();
				SuitUtils.createExplosion(location, 6.0F, true, true);
			}
		}else{
			Iterator<Item> itrerator = Bombs.keySet().iterator();
			ArrayList<Item>removed = new ArrayList<>();
			while(itrerator.hasNext()){
				Item item = itrerator.next();
				if(item.isDead()){
					itrerator.remove();
					item.remove();
					removed.add(item);					
					SuitUtils.createExplosion(item.getLocation(), 6.0F, true, true);
				}
				Location location = item.getLocation();
				PlayEffect.play_Man_Bomb_Effect(location);
				item.getWorld().playSound(location, Values.ManBombSound, 5F,6F);
				item.setFireTicks(0);
				if(ThorUtils.isOnGround(item)){
					itrerator.remove();
					item.remove();
					removed.add(item);	
					SuitUtils.createExplosion(location, 6.0F, true, true);
				}
				}
			Bombs.remove(removed);
			removed.clear();;
		}
		
	}
}
