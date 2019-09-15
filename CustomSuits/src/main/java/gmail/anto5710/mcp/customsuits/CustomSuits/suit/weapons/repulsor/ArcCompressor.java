

package gmail.anto5710.mcp.customsuits.CustomSuits.suit.weapons.repulsor;

import java.util.HashSet
;
import java.util.Set;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.HungerScheduler;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.weapons.SuitWeapons;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.EnchantBuilder;
import gmail.anto5710.mcp.customsuits.Utils.InventoryUtil;
import gmail.anto5710.mcp.customsuits.Utils.ItemUtil;
import gmail.anto5710.mcp.customsuits.Utils.PacketUtil;
import gmail.anto5710.mcp.customsuits.Utils.PotionBrewer;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.MapEncompassor;
import net.minecraft.server.v1_13_R2.EnumItemSlot;

public class ArcCompressor extends MapEncompassor<Player, Set<Integer>>{
	public static final ItemStack bow = ItemUtil.createWithName(Material.BOW, 
			ChatColor.DARK_AQUA + "["+ChatColor.AQUA + "Ω"+ChatColor.DARK_AQUA + "]");
	public static final ItemStack star = ItemUtil.createWithName(Material.END_CRYSTAL, 
			ChatColor.DARK_AQUA + "["+ChatColor.AQUA +"Ω"+ChatColor.DARK_AQUA + "]");
		
	public ArcCompressor(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
		bow.addUnsafeEnchantments(new EnchantBuilder().enchant(Enchantment.ARROW_DAMAGE, 50).enchant(Enchantment.ARROW_FIRE, 20).enchant(Enchantment.ARROW_KNOCKBACK, 100).
		enchant(Enchantment.ARROW_INFINITE, 1).serialize());
		ItemUtil.setLore(star, 
				ChatColor.YELLOW + "RIGHT Click" + ChatColor.WHITE +" and"+ChatColor.YELLOW + " HOLD"+ ChatColor.WHITE +" to Compress Repulse Energy",
				"",
				ChatColor.YELLOW + "RELEASE Mouse " + ChatColor.WHITE + "to launch" + ChatColor.AQUA +" Repulsor Bim"
				);
	}
	
	@EventHandler
	public void onRelease(EntityShootBowEvent e){
		if(ItemUtil.checkItem(bow, e.getBow()) && e.getEntityType() == EntityType.PLAYER){
			e.setCancelled(true);
			Player p = (Player)e.getEntity();
			float force = e.getForce();
			
			if(HungerScheduler.sufficeHunger(p, (int)(force*2*Values.BimHunger))){
				SuitWeapons.repulseBim(p, e.getForce());
				
				PotionBrewer.removePotionEffectByType(p, PotionEffectType.SLOW);
				p.sendMessage(Values.BimMessage);
			}else{
				SuitUtils.lack(p, "Energy");
			}	
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
	public void press(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (CustomSuitPlugin.isMarkEntity(p) && SuitUtils.isRightClick(e)) {
			boolean change = true;

			if (InventoryUtil.holdingInAny(p, star)) {
				if(InventoryUtil.holdingMain(p, star)){					
					InventoryUtil.setMainItem(p, bow);
				}
				if(InventoryUtil.holdingOff(p, star)){
					InventoryUtil.setOffItem(p, bow);
				}				
			} else if (InventoryUtil.holdingInAny(p, bow)) {
				PotionBrewer.addPotion(p, PotionEffectType.SLOW, 999999999, 1);
				SuitUtils.playSound(p, Sound.BLOCK_BEACON_ACTIVATE, 10F, 12F);
				SuitUtils.playSound(p, Sound.BLOCK_BEACON_POWER_SELECT, 7F, 32F);
				SuitUtils.playSound(p, Sound.BLOCK_PISTON_EXTEND, 7F, 12F);
				SuitUtils.playSound(p, Sound.BLOCK_TRIPWIRE_CLICK_OFF, 2F, 12F);
			}else change = false;
			
			if(change){
				update(p, false, 2);
			}
		}
	}
	
	@EventHandler
	public void onChangeItemInHand(PlayerItemHeldEvent e){
		PlayerInventory inven = e.getPlayer().getInventory();
		if(ItemUtil.checkItem(bow, inven.getItem(e.getNewSlot()))){
			Player p = e.getPlayer();
			update(p, false, 3);
		}
	}
	
	private void update(Player p, boolean disclose, long delay){
		if(disclose){
			disclose(p);
		}
		
		if(InventoryUtil.holdingMain(p, bow) && !isItemInHandDisguised(p)){
			System.out.println("ItemInHand NOT Disguised");
			SuitUtils.runAfter(()->{
				disguiseMainHand(p);
			}, delay);	
		}
		if(InventoryUtil.holdingOff(p, bow) && !isItemInOffHandDisguised(p)){
			System.out.println("ItemInOFfHand NOT Disguised");
			SuitUtils.runAfter(()->{
				disguiseOffHand(p);
			}, delay);	
		}
	}
	
	private void disguiseMainHand(Player p){
		if(InventoryUtil.holdingMain(p, bow)){
			PacketUtil.castEquipmentPacket(p, EnumItemSlot.MAINHAND, star);
			int mainHand = InventoryUtil.mainSlot(p);
			disguisedSlots(p).add(mainHand);
			System.out.printf("slot %d item is disguised\n",mainHand);
		}		
	}
	
	private void disguiseOffHand(Player p){
		if(InventoryUtil.holdingOff(p, bow)){
			PacketUtil.castEquipmentPacket(p, EnumItemSlot.OFFHAND, star);
			disguisedSlots(p).add(InventoryUtil.offSlot());
			System.out.printf("slot %d item is disguised\n",InventoryUtil.offSlot());
		}
	}
	
	@EventHandler
	public void onSwap(PlayerSwapHandItemsEvent e){
		Player p = e.getPlayer();
		if(p!=null){
			update(p, true, 2);
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e){
		Player p = getPlayer(e);
		if(p!=null && ItemUtil.checkItem(bow, e.getCurrentItem())){
			disclose(p);
			e.setCurrentItem(star);
		}
	}
	
	@Override
	public boolean isRegistered(Player e) {
		boolean is = super.isRegistered(e);
		if(!is) register(e);
		return is && !get(e).isEmpty();
	}
	
	public boolean isCharging(Player p){
		return InventoryUtil.holdingInAny(p, bow) && p.isHandRaised();
	}
	
	@EventHandler
	public void onDrag(InventoryDragEvent e) {
		Player p = getPlayer(e);
		if (p != null && isRegistered(p)) {
			if (e.getInventorySlots().stream().anyMatch(slot -> isDisguised(p, slot))) {
				disclose(p);
			}
		}
	}
	
	private Player getPlayer(InventoryInteractEvent e){
		return e.getWhoClicked() instanceof Player ? (Player)e.getWhoClicked() : null;
	}
	
	@EventHandler
	public void drop(PlayerDropItemEvent e){
		Item item = e.getItemDrop();
		ItemStack stack = item.getItemStack();
		if(ItemUtil.checkItem(bow, stack)){
			item.setItemStack(star);
			disclose(e.getPlayer());
		}	
	}
	
	private boolean isItemInHandDisguised(Player p){
		return isDisguised(p, InventoryUtil.mainSlot(p));
	}
	
	private boolean isItemInOffHandDisguised(Player p){
		return isDisguised(p, InventoryUtil.offSlot());
	}
	
	private boolean isDisguised(@Nonnull Player p, int slot){
		return isRegistered(p) && get(p).contains(slot);
	}
	
	public void disclose(Player p){
		if(isRegistered(p)) disguisedSlots(p).clear();
	}
	
	private Set<Integer> disguisedSlots(Player p){
		return get(p);
	}
	
	@Override
	public boolean toRemove(Player p) {
		return p.isDead() || !p.isOnline();
	}
	
	@Override
	public void particulate(Player p, Set<Integer> v) {
		if(t%6==0 && isCharging(p))SuitUtils.playSound(p, Sound.BLOCK_DISPENSER_FAIL, 7F, 9);
		
		tick();
	}

	@Override
	public Set<Integer> defaultVal(Player p) {
		return new HashSet<>();	
	}
}
