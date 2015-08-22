package gmail.anto5710.mcp.customsuits.CustomSuits;

import java.time.temporal.ChronoUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Skull;

public class SuitInventoryGUI implements Listener {
	
	CustomSuitPlugin plugin ;
	
 public SuitInventoryGUI(CustomSuitPlugin plugin){
	 this.plugin= plugin;
 }
 
 @EventHandler
 public void clickAirSetting(InventoryClickEvent e){
	 int slot = e.getSlot();
	 if(e.getInventory().getName().endsWith("[Command]")){
	 if(slot!=2&&slot!=6){
		 e.setCancelled(true);
	 }
 }
 }
 
 
 
 @EventHandler
 public void clickExpOnInventory(InventoryClickEvent e){
 	if(e.getInventory().getName().endsWith("[Settings]")){
 		if(e.getInventory().getItem(8).getType()== Material.EXP_BOTTLE){
 			if(e.getSlot()==8){
 			if(e.getInventory().getItem(8).getAmount()==64){
 				e.getInventory().getItem(8).setAmount(1);
 			}
 			else{
 			e.getInventory().getItem(8).setAmount(e.getInventory().getItem(8).getAmount()+1);
 			}
 		CustomSuitPlugin.equipment.put((Player) e.getWhoClicked(), e.getInventory());
 		e.setCancelled(true);
 		
 		}
 		}
 	}
 }
 
 @EventHandler
 public void ClickCommand(InventoryClickEvent e){
	 if(e.getClickedInventory().getName().endsWith("[Command]")){
		 if(e.getSlot()==1){
			 CustomSuitPlugin.spawnsuitforGUI((Player) e.getWhoClicked());
			 e.setCancelled(true);
		 }
		 else if (e.getSlot()==3){
			 OpenList(e,(Player)e.getWhoClicked());
			 e.setCancelled(true);
		 }
		 else if(e.getSlot()==5){
			 CustomSuitPlugin.spawnall((Player) e.getWhoClicked());
			 e.setCancelled(true);
		 }
		 else if(e.getSlot()==7){
			 CustomSuitPlugin.spawnfireworks((Player) e.getWhoClicked());
			 e.setCancelled(true);
		 }
	 }
 }
 private void OpenList(InventoryClickEvent e, Player whoClicked) {
	 Inventory list = Bukkit.createInventory(null, 36 , "[Online Players]");
	 
	  for(Player p: whoClicked.getServer().getOnlinePlayers()){
		  ItemStack skull = new ItemStack(Material.SKULL_ITEM);
		  MaterialData m = skull.getData();
		  m.setData((byte)3);
		  skull.setData(m);
		  ItemMeta meta = skull.getItemMeta();
		 meta.setDisplayName(p.getName());
		 skull.setItemMeta(meta);
		  list.addItem(skull);
		
	  }
	// TODO Auto-generated method stub
	whoClicked.openInventory(list);
}
@EventHandler
public void clickPlayer(InventoryClickEvent e){
	Player Click =(Player)e.getWhoClicked();
	if(e.getClick() == ClickType.LEFT){
		if(e.getClickedInventory().getName().endsWith("[Online Players]")){
			if(	e.getClickedInventory().getItem(e.getSlot())!=null){
				ItemStack item = e.getClickedInventory().getItem(e.getSlot());
				Click.sendMessage(item+"");
				if(item.getType()==Material.SKULL){
					if(	item.getItemMeta().getDisplayName()!=null){
						String name = item.getItemMeta().getDisplayName();
				Player player= Click.getServer().getPlayer(name);
					
					if(PlayerEffect.Mark(Click)){
						CustomSuitPlugin.targetPlayer(Click, player);
						e.setCancelled(true);
					}
					else {
						Click.sendMessage(ChatColor.BLUE+"[Info]: "+ChatColor.AQUA + "No such entity");
						e.setCancelled(true);
					}
					}
					
		}
			}
		}
	}
}


@EventHandler
 public void clickAir(InventoryClickEvent e){
 	if(e.getClickedInventory().getName().endsWith("[Settings]")){
 	 
 	 int slot= e.getSlot();
 	if(slot==1||slot==2||slot ==3||slot==5||slot==6||slot==7){
 		e.setCancelled(true);
 	}
 }
 }
 @EventHandler
 public void clickAir2(InventoryClickEvent e){
 	if(e.getClickedInventory().getName().endsWith("[Armor]")){
 		if(e.getClick()==ClickType.RIGHT){
 	 Player p =(Player)e.getWhoClicked();
 	 
 	 int slot= e.getSlot();
 	if(slot==1||slot==3||slot ==5||slot==7){
 		e.setCancelled(true);
 	}
 	if(slot==0){
 		if(CustomSuitPlugin.helmetequipment.get(p)==null){
 			
 		CustomSuitPlugin.helmetequipment.put(p, CustomSuitPlugin.helmetinventory);
 	}
 		p.openInventory(CustomSuitPlugin.helmetequipment.get(p));
 	}
 	else if(slot == 2){
 		if(CustomSuitPlugin.chestequipment.get(p)==null){
 			
     		CustomSuitPlugin.chestequipment.put(p, CustomSuitPlugin.chestinventory);
     	}
 		p.openInventory(CustomSuitPlugin.chestequipment.get(p));
 	}
else if(slot == 4){
	if(CustomSuitPlugin.leggingsequipment.get(p)==null){
		
		CustomSuitPlugin.leggingsequipment.put(p, CustomSuitPlugin.leggingsinventory);
	}
	p.openInventory(CustomSuitPlugin.leggingsequipment.get(p));
 	}
else if(slot == 6){
	if(CustomSuitPlugin.bootsequipment.get(p)==null){
		
		CustomSuitPlugin.bootsequipment.put(p, CustomSuitPlugin.bootsinventory);
	}
	p.openInventory(CustomSuitPlugin.bootsequipment.get(p));
}
else if(slot == 8){
	if(CustomSuitPlugin.handequipment.get(p)==null){
		
		CustomSuitPlugin.handequipment.put(p, CustomSuitPlugin.handinventory);
	}
	p.openInventory(CustomSuitPlugin.handequipment.get(p));
}
 	
 }
 	}
 }
 
 @EventHandler
 public void clickGUI(PlayerInteractEvent e){
	 if(e.getAction()==Action.RIGHT_CLICK_AIR||e.getAction()==Action.RIGHT_CLICK_BLOCK){
	Material m =	CustomSuitPlugin.suitremote.getType();
		 if(e.getItem().getType()==m){
			 if(e.getItem().getItemMeta().getDisplayName()!=null){
		 if(e.getItem().getItemMeta().getDisplayName().endsWith(CustomSuitPlugin.suitremote.getItemMeta().getDisplayName())){
			 e.getPlayer().openInventory(CustomSuitPlugin.inventory);
			 e.setCancelled(true);
		 }
			 }
		 }
	 }
 }
 


 @EventHandler
 public void clickMenu(InventoryClickEvent e){
 	if(e.getClickedInventory().getName().endsWith("[Settings]")){
 		Player player = (Player) e.getWhoClicked();
 		
 		
if(CustomSuitPlugin.helmetequipment.get(player)==null){
 			
	 		CustomSuitPlugin.helmetequipment.put(player, CustomSuitPlugin.helmetinventory);
	 	}
	 		
	 
	
	 		if(CustomSuitPlugin.chestequipment.get(player)==null){
	 			
	     		CustomSuitPlugin.chestequipment.put(player, CustomSuitPlugin.chestinventory);
	     	}
	 		
	 
		if(CustomSuitPlugin.leggingsequipment.get(player)==null){
			
			CustomSuitPlugin.leggingsequipment.put(player, CustomSuitPlugin.leggingsinventory);
		}
	
	
		if(CustomSuitPlugin.bootsequipment.get(player)==null){
			
			CustomSuitPlugin.bootsequipment.put(player, CustomSuitPlugin.bootsinventory);
		}
		
	
		if(CustomSuitPlugin.handequipment.get(player)==null){
			
			CustomSuitPlugin.handequipment.put(player, CustomSuitPlugin.handinventory);
		}
		
 		int slot= e.getSlot();
 	    if(slot==0){
 	    	player.openInventory(CustomSuitPlugin.CommandInventory);
 	    	e.setCancelled(true);
 	    }
 	    else if(slot==4){
 	    	if(CustomSuitPlugin.armorequipment.get(player)==null){
 	    		CustomSuitPlugin.armorequipment.put(player, CustomSuitPlugin.armorinventory);
 	    	}
 	    	e.getWhoClicked().openInventory(CustomSuitPlugin.armorequipment.get(e.getWhoClicked()));
 	    	e.setCancelled(true);
 	    }
 	   
 	}
 }

 

 
}
