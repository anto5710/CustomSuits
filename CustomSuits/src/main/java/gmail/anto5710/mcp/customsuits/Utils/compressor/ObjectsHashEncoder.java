package gmail.anto5710.mcp.customsuits.Utils.compressor;

import java.util.Objects;

public class ObjectsHashEncoder extends CoordEncodingAdaptor{
	String name = "Objects 내장 Hash생성함수";
	@Override
	public int encodeInt(int x, int y) {
		return Objects.hash(x, y);
	}
	
	@Override
	public Object encode(int x, int y) {
		return encodeInt(x, y);
	}
}
