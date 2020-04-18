package gmail.anto5710.mcp.customsuits.Utils.compressor;

public abstract class CoordEncodingAdaptor{
	
	/**
	 * Returns an unique Double code for the given 2D int. coordinate that can be used to distinguish a coord. as key in a map.             
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @return a unique Double code of the coord. 
	 */
	public double encodeDouble(int x, int y) {return 0;};
	public String encodeString(int x, int y) {return x+","+y;};
	public int encodeInt(int x, int y) {return 0;};
	public abstract Object encode(int x, int y);
}
