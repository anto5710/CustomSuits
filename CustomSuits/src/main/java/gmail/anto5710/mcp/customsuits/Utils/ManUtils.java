package gmail.anto5710.mcp.customsuits.Utils;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.PlayerEffect;
import gmail.anto5710.mcp.customsuits.Setting.PotionEffects;
import gmail.anto5710.mcp.customsuits.Setting.Values;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;



public class ManUtils  {
	
	
	private static ArrayList<Player> HiddenPlayers = new ArrayList<>();

	public static boolean Man(Player player){
		
		if(SuitUtils.CheckItem(CustomSuitPlugin.Chestplate_Man, player.getEquipment().getChestplate())){
			return true;
		}
		if(SuitUtils.CheckItem(CustomSuitPlugin.Leggings_Man, player.getEquipment().getLeggings())){
			return true;
		}
		if(SuitUtils.CheckItem(CustomSuitPlugin.Boots_Man, player.getEquipment().getBoots())){
			return true;
		}
		return false;
	}

	public static void setVisible(Player player) {

		if (HiddenPlayers.contains(player)) {
			HiddenPlayers.remove(player);
			SuitUtils.playEffect(player.getLocation(), Values.ManvisibleEffect, 10, 0, 10);
			player.playSound(player.getLocation(), Values.ManvisibleSound, 16.0F, 16.0F);
			for (Player playerOnline : player.getServer().getOnlinePlayers()) {
				if (!playerOnline.canSee(player)) {
					playerOnline.showPlayer(player);

				}

			}

		}
		else	if (!(HiddenPlayers.contains(player))) {
			HiddenPlayers.add(player);
			SuitUtils.playEffect(player.getLocation(), Values.ManInvisibleEffect, 150, 0, 100);
			player.playSound(player.getLocation(), Values.ManInvisibleSound, 16.0F, 16.0F);
			for (Player playerOnline : player.getServer().getOnlinePlayers()) {
				playerOnline.hidePlayer(player);

			}

		}

	}

	
		 
}
