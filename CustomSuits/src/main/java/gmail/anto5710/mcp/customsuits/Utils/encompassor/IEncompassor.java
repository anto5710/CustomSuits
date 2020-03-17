package gmail.anto5710.mcp.customsuits.Utils.encompassor;

import org.bukkit.event.Listener;


public interface IEncompassor <E> extends Listener, Runnable{
	public boolean isRegistered(E e);
	public void register(E e);
	public void remove(E e);
	public boolean toRemove(E e);
	public void particulate(E e);
	public boolean isRunning();
	public long getPeriod();
	public void start();
	public void cancel();
	
	
}
