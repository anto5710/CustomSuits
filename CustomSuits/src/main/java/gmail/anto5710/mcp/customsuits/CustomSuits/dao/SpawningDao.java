package gmail.anto5710.mcp.customsuits.CustomSuits.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.Control;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;

/**
 * This class save the Suit Entities that spawned by player
 * 
 * @author anto5710
 *
 */
public class SpawningDao {

	private final static String SPAWN_FILE_NAME = "spawned-entities.txt";
	private CustomSuitPlugin plugin;
	private Logger logger;
	private File entityFile;

	public static Map<String, String> spawnMap = new HashMap<>();

	public SpawningDao(CustomSuitPlugin plugin) {
		this.plugin = plugin;
		this.logger = plugin.getLogger();
	}

	public void init() {
		File pluginDir = plugin.getDataFolder();
		logger.info("[Plugin Directory]: " + pluginDir.getAbsolutePath());
		pluginDir.mkdir();

		entityFile = new File(pluginDir, SPAWN_FILE_NAME);
		
		if (!entityFile.exists()) {
			try {
				entityFile.createNewFile();
			} catch (IOException e) {
				logger.severe("[Warn]: Fail to create Spawning Files");
			}
		}
		
		Scanner sc;
		try {
			sc = new Scanner(entityFile);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] values = line.split(":");
				spawnMap.put(values[0], values[1]);
			}
			sc.close();
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		}

	}

	public void saveEntity(Entity spawnedEntity, Player spawner) {
		boolean run = false;
		if(spawnMap.isEmpty()){
			run = true;
		}
		spawnMap.put( spawnedEntity.getUniqueId().toString(),   spawner.getName());
		if(run){
			new Control(plugin, this).runTaskTimer(plugin, 0, 1);
		}
		String line = String.format("%s:%s", spawnedEntity.getUniqueId(),
				spawner.getName());

		PrintStream out = null;
		try {
			FileOutputStream fos = new FileOutputStream(entityFile, true);
			out = new PrintStream(fos);
			out.println(line);
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		} finally {
			out.close();
		}
		
	}

	/**
	 * 
	 * @param RemovedEntity - Removed or Dead Suit Entity
	 */
	public void remove(Entity RemovedEntity ) {
		
		String entityID = String.valueOf(RemovedEntity.getUniqueId());
		
		
		spawnMap.remove(entityID);
//		System.out.println("removed. current size: " + spawnMap.size());
		try {
			writeToFile(spawnMap);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	
		
	}
public void remove(String entityID ) {
		
		
		
		spawnMap.remove(entityID);
//		System.out.println("removed. current size: " + spawnMap.size());
		try {
			writeToFile(spawnMap);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	
		
	}
	
	public void writeToFile(Map<String, String> map)
			throws FileNotFoundException {

		PrintStream out = openFileStream();
		Iterator<String> itr = map.keySet().iterator();
		while (itr.hasNext()) {
			String entityID = itr.next();
			String spawnername = map.get(entityID);
			out.println(String.format("%s:%s", entityID, spawnername));
		}
		out.close();

	}

	private PrintStream openFileStream() throws FileNotFoundException {
		PrintStream out = openFileStream();

		FileOutputStream fos = new FileOutputStream(entityFile);
		return new PrintStream(fos);
	}

	/**
	 * @return  Check Entity's Owner
	 * 
	 * @param entity - Check Entity
	 * @param player - Player for check owner
	 * @return Return true if that entity's owner is player Else Return false
	 */
	public boolean isCreatedBy(Entity entity, Player player) {

		String entityID = entity.getUniqueId() + "";
		String playername = player.getName();

		if (spawnMap.containsKey(entityID)) {

			if (playername.equals(spawnMap.get(entityID))) {
				return true;
			}

		}
		return false;
	}

}
