package gmail.anto5710.mcp.customsuits.Utils;

public class Coolable {
	private boolean cooldown;
	
	public boolean cooldown(long cooltime) {
		if(inCooldown()) return false;
		
		cooldown=true;
		SuitUtils.runAfter(()->cooldown=false, cooltime);
		return cooldown;
	}
	
	public boolean inCooldown(){return cooldown;}
}
