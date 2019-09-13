package gmail.anto5710.mcp.customsuits.Utils;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Thor.Hammer;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public class ThorUtils {
	/**
	 * Find Thor's Hammer from World
	 * @param world World to Find
	 * @param player Player
	 * @return
	 */
	public static Item getDroppedHammer(Player player) {	
		return Hammer.hammerEffecter.getKey(player);
	}
	/**
	 * If Player is holding Thor's Hammer , return true;
	 * @param player Player to Check
	 * @return is player holding Hammer
	 */
	public static boolean isHammerinHand(Player player){
		return SuitUtils.holding(player, CustomSuitPlugin.hammer);
	}

	
	/**
	 * Strike Lightning
	 * @param loc Target Location
	 * @param player player
	 * @param amount Amount of Striking
	 */
	public static void strikeLightnings(Location loc, Player player, int amount) {
		for (int c = 0; c < amount; c++) {
			loc.getWorld().strikeLightning(loc);
		}
	}
}
