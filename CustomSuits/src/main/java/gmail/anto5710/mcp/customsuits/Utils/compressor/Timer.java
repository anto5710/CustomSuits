package gmail.anto5710.mcp.customsuits.Utils.compressor;

public class Timer {	
	private long accu = 0;
	private long origin = -1, lastart;
	
	public void start() {
		lastart = System.currentTimeMillis(); 
		if(origin==-1) origin = lastart;		
	}
	
	public long lapt() {
		long lap = System.currentTimeMillis() - lastart;
		accu += lap;
		return lap;
	}
	
	public long total() {
		return accu;
	}
	
	public long absTotal() {
		return System.currentTimeMillis() - origin;
	}
	
	public void reset() {
		origin = -1; accu = 0;
	}

	@SuppressWarnings("unused")
	private static double toMM(double px) {
		return 10*px*2.54/127.68;
	}
}
