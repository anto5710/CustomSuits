package gmail.anto5710.mcp.customsuits.Thor;

import org.bukkit.util.Vector;

public class VorticalMeta {
	public final double radius;
	private double yOffset = 0D;
	private double theta = 0;
	private boolean isUp = false;
	public VorticalMeta(double radius) {
		this.radius = radius;
	}
	
	public void addTheta(double dt){
		theta += dt;
		if(theta > Math.PI) theta %= Math.PI;
	}
	
	public Vector dLinear(){
		return new Vector(Math.sin(theta * radius), 0, 
				Math.cos(theta * radius));
	}
	
	public void addY_Offset(double dy){
		yOffset += isUp? dy : -dy;
		updateUP();
	}
	
	private void updateUP(){
		if (yOffset >= 2) {
			isUp = false;
		}
		if (yOffset <= 0) {
			isUp = true;
		}
	}

	public double getY_Offset() {
		return yOffset;
	}

	public boolean isUp(){
		return isUp;
	}
	
	public double getTheta(){
		return theta;
	}
}
