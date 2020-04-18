package gmail.anto5710.mcp.customsuits.Utils.compressor;

public class BijectiveCoordEncoder extends CoordEncodingAdaptor{
	String name = "Bijective 함수방식";
	@Override
	public int encodeInt(int x, int y) {
		int tmp = y + ((x + 1)/2);
		return x + (tmp * tmp);
	}
	@Override
	public Object encode(int x, int y) {
		return encodeInt(x, y);
	}

}
