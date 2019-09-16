package gmail.anto5710.mcp.customsuits.CustomSuits.suit.weapons.repulsor;

import org.bukkit.entity.Snowball;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.CustomEffects;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.standardized.StandardEncompassor;

public class ArcReffecter extends StandardEncompassor<Snowball>{
	public ArcReffecter(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
	}

	@Override
	public void particulate(Snowball e) {
		CustomEffects.play_Suit_ARC(e.getLocation());
	}
}
