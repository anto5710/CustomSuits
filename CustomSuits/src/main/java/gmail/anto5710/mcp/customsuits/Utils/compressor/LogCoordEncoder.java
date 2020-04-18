package gmail.anto5710.mcp.customsuits.Utils.compressor;

public class LogCoordEncoder extends CoordEncodingAdaptor{
	String name = "상용로그소수점 이동방식";
	@Override
	public double encodeDouble(int x, int y) {		
		if(y == 0) {return x;}
		int state = y<0? 1:2;
		double absV = Math.abs(y);
		return x<0? x-(absV*10+state)*Math.pow(10, -(int)Math.log10(absV)-2) : x+(absV*10+state)*Math.pow(10, -(int)Math.log10(absV)-2);	
	}

	@Override
	public Object encode(int x, int y) {
		return encodeDouble(x, y);
	}
}
