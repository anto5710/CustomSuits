package gmail.anto5710.mcp.customsuits.CustomSuits;

import java.awt.Color;
import java.net.Proxy.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.print.DocFlavor.CHAR_ARRAY;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.hamcrest.core.IsSame;

import com.google.common.primitives.Ints;

public class WeaponListner implements Listener {

	static int maxformachine =50;
	static int maxforsniper = 8;
	CustomSuitPlugin plugin ;
	private HashMap<Player, Boolean> charging = new HashMap<>();
	static HashMap<Player , Boolean>cooldowns = new HashMap<>();
	
	public WeaponListner(CustomSuitPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void interectshield(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_AIR
				|| e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (p.getFoodLevel() >= 18) {

				if (Mark(p)){
						if(p.getItemInHand()!=null){
							if(
						 p.getItemInHand().getType() == Material.NETHER_STAR) {
					p.setNoDamageTicks(200);
					p.getWorld()
							.spigot()
							.playEffect(p.getLocation(), Effect.TILE_BREAK,
									Material.AIR.getId(), 0, 0.0F, 1F, 0.0F,
									0.1F, 800, 400);
					p.setFoodLevel(p.getFoodLevel() - 18);
					p.sendMessage(ChatColor.BLUE+ "[Info]: "+ChatColor.DARK_AQUA + "No Damage Time!!");
					p.playSound(p.getLocation(), Sound.FUSE, 2.0F, 2.0F);
				}
			} else
					
					if(p.getItemInHand().getType().equals(Material.NETHER_STAR)) {
				p.sendMessage(ChatColor.RED + "[Warn]: You don't have enough energy!");
				p.playSound(p.getLocation(), Sound.NOTE_STICKS, 6.0F, 6.0F);
			}
		}
			}
		}
	}

	@EventHandler
	public void onPlayerLeftClick(PlayerInteractEvent clickevent) {
		Material is = Material.NETHER_STAR;
		if ((clickevent.getAction() == Action.LEFT_CLICK_AIR)
				|| (clickevent.getAction() == Action.LEFT_CLICK_BLOCK)) {
			Player player = clickevent.getPlayer();
			
			if (player.getFoodLevel() >= 3) {
				Location loc = player.getLocation();
				loc.setY(loc.getY() + 2.0D);
				if (Mark(player)
						&& (player.getEquipment().getItemInHand().getType() == is)) {
					loc = player.getLocation();
					if (!player.isSneaking()) {
						Block targetblock = player.getTargetBlock((HashSet<Byte>) null,
								10000);
						Location targetloc =targetblock.getLocation();
						Location locp = player.getLocation();
						locp.setY(locp.getY() + 1);
						playEffect(targetblock.getLocation(), locp, player, true);
						player.setFoodLevel(player.getFoodLevel() - 2);
						player.sendMessage(ChatColor.BLUE+ "[Info]: "+ChatColor.AQUA
								+ "fired a repulser bim!");
						
						player.playSound(loc, Sound.BLAZE_BREATH, 4.0F, 4.0F);

					}
					if (player.isSneaking()) {
						if (player.getFoodLevel() >= 8) {
							Block targetblock = player.getTargetBlock(
									(HashSet<Byte>) null, 10000);

							Location targetloc =targetblock.getLocation();
							Location locp = player.getLocation();
							locp.setY(locp.getY() + 1);
							playEffect(targetloc,
									locp, player, true);

							player.sendMessage(ChatColor.BLUE+ "[Info]: "+ChatColor.AQUA
									+ "fired a repulser missile!");
							
							
							player.playSound(player.getLocation(),
									Sound.WITHER_DEATH, 4.0F, 4.0F);
							player.setFoodLevel(player.getFoodLevel() - 8);
						} else {
							player.sendMessage(ChatColor.RED
									+ "You don't have enough energy!");
							player.playSound(player.getLocation(),
									Sound.NOTE_STICKS, 6.0F, 6.0F);
						}
					}
				}
			}
			else if (player.getFoodLevel() < 3
					&& player.getItemInHand().getType() == is) {
				player.sendMessage(ChatColor.RED+
						 "[Warn]: You don't have enough energy!");
				player.playSound(player.getLocation(), Sound.NOTE_STICKS, 6.0F,
						6.0F);
			}
		}
	}

	private void playEffect(Location location1, Location location2,
			Player player, boolean isMissile) {
		double damage = 0;
		double radius = 0;

		int distance = (int) Math.ceil(location1.distance(location2) / 2) - 1;

		if (distance <= 0) {
			return;
		}
		Vector v = location2.toVector().subtract(location1.toVector())
				.normalize().multiply(2);
		Location currentLoc = location1.clone();
		List<Entity> near = currentLoc.getWorld().getEntities();
		if(isMissile){
			if(player.isSneaking()){
				 radius = 2D;
				 damage = 150D;
			}
			else{
				radius = 2D;
				damage = 60D;
			}
		}
		else{
			if(!player.isSneaking()){
			radius = 1.5D;
			damage = 15D-(location1.distance(location2)/10);
			}
			else{
				radius = 1D;
				damage =45D-(location1.distance(location2)/10);
			}
		}
         damage = damage * (CustomSuitPlugin.getPlayerLevel(player)/1+1);
		for (double i = 0; i < distance; i+=0.2) {
			currentLoc.add(v);
			if (isMissile) {
				if (player.isSneaking()) {
					
					
					
					currentLoc.getWorld()
							.spigot()
							.playEffect(currentLoc, Effect.TILE_BREAK,
									Material.DIAMOND_BLOCK.getId(), 0, 0.0F,
									0.0F, 0.0F, 0.0F, 500, 100);

				} else {

					currentLoc.getWorld()
							.spigot()
							.playEffect(currentLoc, Effect.TILE_BREAK,
									Material.DIAMOND_BLOCK.getId(), 0, 0.0F,
									0.0F, 0.0F, 0.0F, 400, 70);
				}
				
				
				for (Entity e : near) {
					if (distance(currentLoc,e,radius)) {
						if (e.getType() == EntityType.PLAYER) {
							Player p = (Player) e;
							if (player != p) {
								LivingEntity len = (LivingEntity) e;
								
									
									damageandeffectmissile(len, currentLoc, damage, p, radius);
								
							}
						} else {
							LivingEntity livingentity = (LivingEntity) e;
							
								
								damageandeffectmissile(livingentity, currentLoc, damage, player, radius);
							
						}
					}

				}
			} else if (isMissile == false) {
				currentLoc.getWorld()
				.spigot()
				.playEffect(currentLoc, Effect.CRIT,
						0, 0, 0.0F,
						0.0F, 0.0F, 0.1F, 100, 1);
						
				for (Entity e : near) {
					if (distance(currentLoc,e,radius)) {
						if (e.getType() == EntityType.PLAYER) {
							Player p = (Player) e;
							if (player != p) {
								if (player.isSneaking() == false) {
									LivingEntity livingentity = (LivingEntity) e;
									damageandeffect(livingentity, currentLoc,damage, player);
								} else if (player.isSneaking()) {
									LivingEntity len = (LivingEntity) e;
									damageandeffect(len, currentLoc,damage, player);
								}

							}
						} else {
							LivingEntity livingentity = (LivingEntity) e;
							damageandeffect(livingentity, currentLoc,damage, player);

						}
					}

				}
			}

		}
		if (isMissile) {
			if (player.isSneaking() == false) {
				
				location1.getWorld().createExplosion(location1.getX(), location1.getY(), location1.getZ(), 10F, false,false);
				location1.getWorld().createExplosion(location1.getX(), location1.getY(), location1.getZ(), 10F, false,false);
				
			} else if (player.isSneaking()) {
				
				player.setNoDamageTicks(20);
				location1.getWorld().createExplosion(location1.getX(), location1.getY(), location1.getZ(), 60F, false,false);
				location1.getWorld().createExplosion(location1.getX(), location1.getY(), location1.getZ(), 60F, false,false);
			}
			for (Entity e : near) {
				if (distance(currentLoc,e,radius)) {
					if (e.getType() == EntityType.PLAYER) {
						Player p = (Player) e;
						if (player != p) {
							LivingEntity len = (LivingEntity) e;
							if(player.isSneaking()){
								
								damageandeffectmissile(len, currentLoc, damage, p, 20);
							}
							else{
								damageandeffectmissile(len, currentLoc, damage, p, 5);
							}
						}
					} else {
						LivingEntity livingentity = (LivingEntity) e;
						if(player.isSneaking()){
							
							damageandeffectmissile(livingentity, currentLoc, damage, player, 20);
						}
						else{
							damageandeffectmissile(livingentity, currentLoc, damage, player, 5);
						}
					}
				}

			}
		}
		if (isMissile == false) {
			Block be = location1.getBlock();
			Material mb = be.getType();
			if (mb != Material.AIR && mb != Material.BEDROCK
					&& mb != Material.OBSIDIAN && mb != Material.WATER
					&& mb != Material.LAVA) {
				be.setType(Material.AIR);
				be.getWorld()
						.spigot()
						.playEffect(be.getLocation(), Effect.TILE_BREAK,
								mb.getId(), 0, 0.0F, 0.0F, 0.0F, 0.0F, 480, 160);
				
			}

		}
	}
	
	
	private void damageandeffectmissile(LivingEntity livingentity, Location currentLoc,
			double damage , Player p,double radius) {
		Location locationofentity = livingentity.getLocation();
		
		double xe = locationofentity.getX();
		double ye = locationofentity.getY();
		double ze = locationofentity.getZ();
		
		double xc = currentLoc.getX();
		double yc = currentLoc.getY();
		double zc = currentLoc.getZ();
	
		if(xc- radius< xe&& xe < xc + radius &&
		  yc-radius < ye && ye < yc +radius&&
		  zc -radius < ze && ze < zc+ radius
				){
			livingentity.damage(damage);
		}
		
		
		}

	private void damageandeffect(LivingEntity livingentity, Location currentLoc,
			double damage , Player p) {
		if(!livingentity.isDead()){
		Location locationofentity = livingentity.getLocation();
		
		double xe = locationofentity.getX();
		double ye = locationofentity.getY();
		double ze = locationofentity.getZ();
		
		double xc = currentLoc.getX();
		double yc = currentLoc.getY();
		double zc = currentLoc.getZ();
		double radius = 2;
		if(p.isSneaking()){
			radius = 1D;
		}
		while(xc- radius< xe&& xe < xc + radius &&
		  yc-radius < ye && ye < yc +radius&&
		  zc -radius < ze && ze < zc+ radius
				){
			radius = radius -=0.1;
		}
		if(radius <= 0.5){
		firwork(livingentity.getLocation() ,p);
		}
       
		livingentity.damage(damage/radius,p);
		
		livingentity.getWorld()
		.spigot()
		.playEffect(locationofentity, Effect.TILE_BREAK,
				Material.REDSTONE_BLOCK.getId(), 0, 0.0F, 0.0F, 0.0F, 0.3F, (int)(80*damage), 5);
		
		}
		
		/*
		 *1 d 
		 *0.5 
		 *
		 *
		 */
		}
	
		

	private void firwork(Location location , Player p) {
		Location ploc = p.getLocation();
		ploc.setY(ploc.getY()+16);
		Location currentploc = ploc;
	  	double random =(Math.random()*6)-3;
       	
    
    	
    	
		// TODO Auto-generated method stub
		Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
		  Random r = new Random();
      FireworkEffect effect =  FireworkEffect.builder().flicker(r.nextBoolean()).withColor(org.bukkit.Color.RED).withFade(org.bukkit.Color.RED).with(org.bukkit.FireworkEffect.Type.STAR).trail(r.nextBoolean()).build();
      
		FireworkMeta meta = firework.getFireworkMeta();
		meta.addEffect(effect);
		meta.setPower((int) 3);
		 firework.setFireworkMeta(meta);    
	
		 p.playSound(p.getLocation(), Sound.EXPLODE,14.0F, 14.0F);
		 p.playSound(p.getLocation(), Sound.WITHER_DEATH,14.0F, 14.0F);
		 
		
	}

	private boolean distance(Location currentLoc, Entity e, double radius) {
		Location loc = e.getLocation();
		// TODO Auto-generated method stub
		if(currentLoc.distance(loc)<=radius){
			return true;
		}
		else{
	    loc.setY(loc.getY()-1D);
	    if(currentLoc.distance(loc)<=radius){
			return true;
		}
	    loc.setY(loc.getY()+1D);
	    if(currentLoc.distance(loc)<=radius){
			return true;
		}
		}
		return false;
	}

	@EventHandler
	public void gun(PlayerInteractEvent clickevent) {
		Player player = clickevent.getPlayer();
       ItemStack item = CustomSuitPlugin.getGun();
  
     
       
       if(clickevent.getAction()==Action.RIGHT_CLICK_AIR||clickevent.getAction()==Action.RIGHT_CLICK_BLOCK){
    	   if(Mark(player)){
    		   if(player.getItemInHand().getType() == Material.IRON_BARDING){
    		   if(player.getItemInHand().getItemMeta().getDisplayName()!=null&&
    				   player.getItemInHand().getItemMeta().getDisplayName().contains(":-:")
    				   ){
    		   String name = item.getItemMeta().getDisplayName();
			   String gunname = player.getItemInHand().getItemMeta().getDisplayName();
			   String[]values = name.split(":-:");
			   int ammoamount = 0;
			   
			   String[]names = gunname.split(":-:");
    		   if(values[0].endsWith(names[0])){
    			   int cnt = Integer.parseInt(names[1]);
    			   int snipe = Integer.parseInt(names[2]);
    			   if(cnt==0){
    					

    					Material ammomat = Material.FLINT;
    					ItemStack ammo = new ItemStack(ammomat, 1);
    					if (player.getInventory().contains(ammomat, 1)) {
    						charging.put(player , true);
    						for(int i = 0; i <maxformachine ; i++){
    							if (player.getInventory().contains(ammomat, 1)) {
    								
    								player.getInventory().removeItem(ammo);
    								
    								ammoamount++;
    							}
    						}
    					
    					   player.updateInventory();
    					   
    					   player.playSound(player.getLocation(), Sound.LEVEL_UP, 4.0F, 1.0F);
    					   cooldown(2.0, player);
    					   sleep(2000);
    					   ItemMeta meta = player.getItemInHand().getItemMeta();
    					   
    					   meta.setDisplayName(names[0]+":-:"+ammoamount+":-:"+snipe);
    					   player.getItemInHand().setItemMeta(meta);
    					   player.playSound(player.getLocation(), Sound.ANVIL_LAND, 4.0F, 4.0F);
    					   player.playSound(player.getLocation(), Sound.EXPLODE, 4.0F, 4.0F);
    					   player.playSound(player.getLocation(), Sound.VILLAGER_HIT, 4.0F, 4.0F);
    					   player.playSound(player.getLocation(), Sound.CLICK, 4.0F, 4.0F);
    					   player.playSound(player.getLocation(), Sound.CREEPER_HISS, 4.0F, 4.0F);
    					  
    					 
    					   cooldown(0.5, player);
    					   sleep(500);
   			        	player.playSound(player.getLocation(), Sound.IRONGOLEM_HIT, 4.0F, 4.0F);	
   			        	
   			        	
   			        	
   			        	player.playSound(player.getLocation(), Sound.DIG_WOOD, 15.0F,3.5F);
   			        	player.playSound(player.getLocation(), Sound.DOOR_CLOSE, 4.0F, 2.5F);
   			        	player.playSound(player.getLocation(), Sound.DOOR_OPEN, 4.0F, 4.0F);	
   			        	charging .put(player ,false);
    					   
    				   }
    					else{
    						player.sendMessage(ChatColor.RED+
    								 "[Warn]: You don't have enough ammo!");
    						player.playSound(player.getLocation(), Sound.NOTE_STICKS, 6.0F,
    								6.0F);
    					}
    							
    			   }
    			   if(!ischarging(player)){
    			   if(snipe ==0&&player.isSneaking()){

   					Material ammomat = Material.GHAST_TEAR;
   					ItemStack ammo = new ItemStack(ammomat, 1);
   					if (player.getInventory().contains(ammomat, 1)) {
   						for(int i = 0; i <maxforsniper ; i++){
   							if (player.getInventory().contains(ammomat, 1)) {
   								
   								player.getInventory().removeItem(ammo);
   								
   								ammoamount++;
   							}
   						}
   					
   					   player.updateInventory();
   					   
   					   player.playSound(player.getLocation(), Sound.LEVEL_UP, 4.0F, 1.0F);
   					   cooldown(2.0, player);
   					   sleep(2000);
   					   ItemMeta meta = player.getItemInHand().getItemMeta();
   					   
   					   meta.setDisplayName(names[0]+":-:"+cnt+":-:"+ammoamount);
   					   player.getItemInHand().setItemMeta(meta);
   					   player.playSound(player.getLocation(), Sound.ANVIL_LAND, 4.0F, 4.0F);
   					   player.playSound(player.getLocation(), Sound.EXPLODE, 4.0F, 4.0F);
   					   player.playSound(player.getLocation(), Sound.VILLAGER_HIT, 4.0F, 4.0F);
   					   player.playSound(player.getLocation(), Sound.CLICK, 4.0F, 4.0F);
   					   player.playSound(player.getLocation(), Sound.CREEPER_HISS, 4.0F, 4.0F);
   					  
   					 
   					   cooldown(0.5, player);
   					   sleep(500);
  			        	player.playSound(player.getLocation(), Sound.IRONGOLEM_HIT, 4.0F, 4.0F);	
  			        	
  			        	
  			        	
  			        	player.playSound(player.getLocation(), Sound.DIG_WOOD, 15.0F,3.5F);
  			        	player.playSound(player.getLocation(), Sound.DOOR_CLOSE, 4.0F, 2.5F);
  			        	player.playSound(player.getLocation(), Sound.DOOR_OPEN, 4.0F, 4.0F);	
   					   
   					   
   				   }
   					else{
   						player.sendMessage(ChatColor.RED+
   								 "[Warn]: You don't have enough ammo!");
   						player.playSound(player.getLocation(), Sound.NOTE_STICKS, 6.0F,
   								6.0F);
   					}
   							
    			   }
    			       
    			   Location loc = player.getTargetBlock((HashSet<Byte>)null, 10000).getLocation();
    			   Location ploc = player.getLocation();
    			   Location locationplayer= player.getLocation();
    			   ploc.setY(ploc.getY()+1);
    		if(!player.isSneaking()){
    			        	if(cnt!=0){
    			        if(player.hasPotionEffect(PotionEffectType.SLOW)){
    			        	double random =(Math.random()*2)-1;
    			           	
    			        	ploc.setX(ploc.getX()+random);
    			        	random =(Math.random()*2)-1;
    			        	
    			        	ploc.setZ(ploc.getZ()+random);
    			        	
    			        	loc.setX(loc.getX()+random);
    			        	random =(Math.random()*2)-1;
    			        	loc.setY(loc.getY()+random);
    			        	random =(Math.random()*2)-1;
    			        	loc.setZ(loc.getZ()+random);
    			        	
    			        }
    			        else {
    			          	double random =(Math.random()*3)-1.5;
    			           	
    			        	ploc.setX(ploc.getX()+random);
    			        	random =(Math.random()*3)-1.5;
    			        	
    			        	ploc.setZ(ploc.getZ()+random);
    			        	
    			        	loc.setX(loc.getX()+random);
    			        	random =(Math.random()*3)-1.5;
    			        	loc.setY(loc.getY()+random);
    			        	random =(Math.random()*3)-1.5;
    			        	loc.setZ(loc.getZ()+random);
    			        }
    			        
    			 
								
    			        player.playSound(player.getLocation(), Sound.ZOMBIE_WOOD, 3.0F, 3.0F);
    		        	player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
    		        	player.playSound(player.getLocation(), Sound.EXPLODE, 1.0F, 1.0F);
								player.playSound(player.getLocation(), Sound.ZOMBIE_WOOD, 3.0F, 3.0F);
	    			        	player.playSound(player.getLocation(), Sound.ANVIL_LAND, 0.3F, 0.3F);
	    			        	player.playSound(player.getLocation(), Sound.EXPLODE, 23.0F, 21.0F);
	    			        	player.playSound(player.getLocation(), Sound.IRONGOLEM_HIT, 4.0F, 4.0F);	
	    			        	
								
							
	    			        
	    			        	
	    			        	
	    			        	
	    			        	
	    			        	
	    			        	
								
								
	    			        	
	    			        	
	    			        	
							
					
					locationplayer.setY(locationplayer.getY()+1);
														playEffect(loc, ploc , player, false);
														locationplayer.getWorld()
														.spigot()
														.playEffect(locationplayer, Effect.TILE_BREAK,
																Material.STONE.getId(), 0, 0.0F,
																0.0F, 0.0F, 0.2F, 50, 50);

							
							
														ItemMeta meta = item.getItemMeta();
							    			        	meta.setDisplayName(values[0]+":-:"+(cnt-1)+":-:"+snipe);
							    			        	player.getInventory().getItemInHand().setItemMeta(meta);
							    			        	
    			        	}
    			        			
    		   			}
    		else{
    			if(snipe!=0){
    			if(!isCooldown(player)){
    			 if(player.hasPotionEffect(PotionEffectType.SLOW)==false){
		        	double random =(Math.random()*6)-3;
		        	
		           	
		        	ploc.setX(ploc.getX()+random);
		        	random =(Math.random()*6)-3;
		        	
		        	ploc.setZ(ploc.getZ()+random);
		        	
		        	loc.setX(loc.getX()+random);
		        	random =(Math.random()*6)-3;
		        	loc.setY(loc.getY()+random);
		        	random =(Math.random()*6)-3;
		        	loc.setZ(loc.getZ()+random);
    			 }
		        	
    			 
    				player.playSound(player.getLocation(), Sound.ZOMBIE_WOOD, 3.0F, 3.0F);
		        	player.playSound(player.getLocation(), Sound.ANVIL_LAND, 0.3F, 0.3F);
		        	player.playSound(player.getLocation(), Sound.EXPLODE, 23.0F, 21.0F);
		        	player.playSound(player.getLocation(), Sound.IRONGOLEM_HIT, 4.0F, 4.0F);	
		        	
		        	playEffect(loc, ploc , player, false);
		        	
					locationplayer.getWorld()
					.spigot()
					.playEffect(locationplayer, Effect.TILE_BREAK,
							Material.STONE.getId(), 0, 0.0F,
							0.0F, 0.0F, 0.2F, 50, 50);

		        	
		        	
		        	
		        	
		        cooldown(2, player);
					
		        	
		        	ItemMeta meta = item.getItemMeta();
		        	meta.setDisplayName(values[0]+":-:"+(cnt)+":-:"+(snipe-1));
		        	player.getInventory().getItemInHand().setItemMeta(meta);
		        
		    
    			
    		}
    		}
    		}
    		}  	
    		   		}
    		   }
    	   }
       }
       }
	}
	private boolean ischarging(Player player) {
		if(!charging.containsKey(player)){
			return false;
		}
		else{
			if(charging.get(player)==false){
				return false;
			}
		}
		// TODO Auto-generated method stub
		return true;
	}

	private boolean isCooldown(Player player) {
		if(cooldowns.containsKey(player)){
			if(cooldowns.get(player)==false){
				return false;
			}
			else{
				return true;
			}
		}
		else{
			
			return false;
		}
			}

	private void sleep(long msec) {
		try {
			Thread.sleep(msec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	private void cooldown(double msec , Player player) {
		
		// 1 tick = 0.05 sec
		// 1 sec = 20 tick
		long tick = (long)msec*20;
	
	   
	    	cooldowns.put(player, true);
	  
		BukkitTask task = new Cooldown(plugin, player).runTaskLater(plugin, tick);
	}
	private boolean Mark(Player p) {
		if(PlayerEffect.Mark(p)){
			return true;
		}
		return false;
	}
	 
	 
	  
	
	  
	 
	

}
