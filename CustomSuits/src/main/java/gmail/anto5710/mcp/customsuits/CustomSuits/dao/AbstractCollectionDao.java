package gmail.anto5710.mcp.customsuits.CustomSuits.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import javax.annotation.Nonnull;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractCollectionDao<C> implements IColletorDao<C>{
	protected C data;
	protected final String name;
	protected JavaPlugin plugin;
	protected Logger logger;
	protected File target;
	
	public AbstractCollectionDao(String filename, @Nonnull JavaPlugin plugin) {
		this(filename, plugin, plugin.getDataFolder());
	}
	
	public AbstractCollectionDao(String filename, @Nonnull JavaPlugin plugin, @Nonnull File dir) {
		this.name = filename;
		this.plugin = plugin;
		this.logger = plugin.getLogger();
		this.data = defaultData();
		init(dir);
	}
	
	protected void init(File dir) {
		target = new File(dir, name);
		if(!target.exists()) {
			try {
				target.createNewFile();
				logger.info(String.format("Successfully created %s at %s", name, getPath()));
			} catch (IOException e) {
				logger.severe(String.format("[Warn]: Failed to create %s at %s", name, getPath()));
			}
		}
	}
	
	@Override
	public C read() {
		BufferedReader breader = null;
		FileReader reader = null;
		try {
			reader = new FileReader(target);
			breader = new BufferedReader(reader);
			read(breader);
		} catch (Exception e) {
			logger.severe("[Warn]: Failed to read data from "+name);
			e.printStackTrace();
		} finally {
			try {
				if (breader != null) {
					breader.close();
				}else if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				logger.severe("[Warn]: Failed while closing the FileReader for "+name);
				e.printStackTrace();
			}
		}
		return data;
	}
	
	@Override
	public void read(BufferedReader writer) throws IOException {
		String line = null;
		while((line = writer.readLine()) != null && !line.isEmpty()) {
			readNextLine(line);
		}
	}
	
	@Override
	public boolean save() {
		BufferedWriter bwriter = null;
		FileWriter writer = null;
		boolean result = false;
		
		try {
			writer = new FileWriter(target);
			bwriter = new BufferedWriter(writer);
			result = write(bwriter);
		} catch (Exception e) {
			logger.severe("[Warn]: Failed to save data at "+name);
			e.printStackTrace();
		} finally {
			try {
				if (bwriter != null) {
					bwriter.close();
				}else if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				logger.severe("[Warn]: Failed while closing the FileWriter for "+name);
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@Override
	public boolean save(@Nonnull C data) {
		this.data = data;
		return save();
	}
	
	@Override
	public File getFile() {
		return target;
	}
	
	@Override
	public String getPath() {
		return target.getAbsolutePath();
	}
	
	@Override
	public C getLoaded() {
		return data;
	}
}
