package gmail.anto5710.mcp.customsuits.CustomSuits.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 * A Data Access Object that reads and writes a specific file in text base.
 * @author anto5710
 *
 * @param <C> the type of collection — Map, List, Set... any type is fine.
 */
public interface IColletorDao<C> {
	public C defaultData();
	public void readNextLine(String line);
	public void read(BufferedReader writer) throws IOException;
	public C read();

	public boolean write(BufferedWriter writer) throws IOException;
	public boolean save(C data);
	public boolean save();
	
	public C getLoaded();
	public File getFile();
	
	/**
	 * @return The absolute path of the target file.
	 */
	public String getPath();
}
