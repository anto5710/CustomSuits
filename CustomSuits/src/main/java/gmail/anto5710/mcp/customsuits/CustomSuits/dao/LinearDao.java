package gmail.anto5710.mcp.customsuits.CustomSuits.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class LinearDao <C extends Collection<E>, E> extends AbstractCollectionDao<C>{
	
	public LinearDao(String filename, JavaPlugin plugin, File dir) {
		super(filename, plugin, dir);
	}
	
	public LinearDao(String filename, JavaPlugin plugin) {
		super(filename, plugin);
	}

	@Override
	public boolean write(BufferedWriter writer) throws IOException {
		for(E e : data) {
			if (e==null) return false;
			
			writer.write(writeNextLine(e)); 
			writer.newLine();
		}
		return true;
	}
	
	public abstract String writeNextLine(E e);
	
	public boolean contains(E e) {
		return data.contains(e); 
	}
	
	public boolean add(E e) {
		return data.add(e);
	}
	
	public boolean remove(E e) {
		return data.remove(e);
	}
}
