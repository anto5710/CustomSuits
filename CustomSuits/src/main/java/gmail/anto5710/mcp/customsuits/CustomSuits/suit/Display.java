package gmail.anto5710.mcp.customsuits.CustomSuits.suit;





import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Display {
	CustomSuitPlugin plugin;
	static ScoreboardManager manager = Bukkit.getScoreboardManager();
	
	public static Scoreboard create_Scoreboard(){	
		return manager.getNewScoreboard();
	}
	public static void reloadData( Player player){
			ItemStack [] armor = player.getEquipment().getArmorContents();
			
		
			
			double health = Math.round(player.getHealth() / player.getMaxHealth() * 100.0D);
		    double hunger = player.getFoodLevel() *5;
		    double flyspeed = 0;
		    if(player.getFlySpeed()>=0.75){
		    	flyspeed = 100;
		    }else
		    if(player.getFlySpeed()!=0){
		    flyspeed =  Math.round((player.getFlySpeed()/0.75)*100);
		    }
		    Scoreboard scoreboard = create_Scoreboard();
		    Objective objective = scoreboard.registerNewObjective("Mark" + CustomSuitPlugin.getLevel(player), "dummy");
		    
		    Score healthScoreName = objective.getScore(ChatColor.RED +""+ChatColor.BOLD + "Health");
		    healthScoreName.setScore(14);
		    
		    Score healthScore = objective.getScore(health + " %");
		    healthScore.setScore(13);
		    
		    Score blank = objective.getScore(" ");
		    blank.setScore(12);
		    
		    Score hungerScoreName = objective.getScore( ChatColor.AQUA+""+ChatColor.BOLD  + "Hunger");
		    hungerScoreName.setScore(11);
		    
		    Score hungerScore = objective.getScore(hunger + " %  ");
		    hungerScore.setScore(10);
		    
		    Score blank2 = objective.getScore("   ");
		    blank2.setScore(9);
		    
		    Score flyspeedScoreName = objective.getScore(ChatColor.BLUE +""+ChatColor.BOLD +  "FlySpeed");
		    flyspeedScoreName.setScore(8);
		    
		    Score flyspeedScore = objective.getScore(flyspeed + " % ");
		    flyspeedScore.setScore(7);
		    
		    Score blank3 = objective.getScore("    ");
		    blank3.setScore(6);
		    
		    
		    Score durabilityScoreName = objective.getScore(ChatColor.YELLOW +""+ChatColor.BOLD +  "Durability");
		    durabilityScoreName.setScore(5);
		    
		    Score Helmetdurability = objective.getScore("   ⊙ Helmet: "+getDurability(armor[0]));
		    Helmetdurability.setScore(4);
		    Score Chestplatedurability = objective.getScore("   ⊙ Chestplate: "+getDurability(armor[1]));
		    Chestplatedurability.setScore(3);
		    Score Leggingsdurability = objective.getScore("   ⊙ Leggings: "+getDurability(armor[2]));
		    Leggingsdurability.setScore(2);
		    Score Bootsdurability = objective.getScore("   ⊙ Boots: "+getDurability(armor[3]));
		    Bootsdurability.setScore(1);
		    
		    Score blank4 = objective.getScore("          ");
		    blank4.setScore(0);
		    
		    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		    player.setScoreboard(scoreboard);
		
	}
	private static double getDurability(ItemStack itemStack) {
		double durability = 0;
		if(itemStack.getType()!=Material.AIR){
			short max_durability = getMaxDurability(itemStack);
			short item_durability = (short) (max_durability - itemStack.getDurability());
			durability = item_durability/max_durability*100;
			
		}
		return Math.round(durability);
	}
	private static  short getMaxDurability(ItemStack itemStack){
		return itemStack.getType().getMaxDurability();
	}
	public static void clear_Scoreboard(Player player){
		player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		
	}
		
	
}
