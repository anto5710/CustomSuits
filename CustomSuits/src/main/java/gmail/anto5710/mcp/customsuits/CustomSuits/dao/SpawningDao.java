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
import java.util.logging.Logger;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

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

	private Map<String, String> spnMap = new HashMap<>();

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
				spnMap.put(values[0], values[1]);
			}
			sc.close();
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		}

	}

	public void saveEntity(Entity spawnedEntity, Player spawner) {

		spnMap.put("" + spawnedEntity.getEntityId(), "" + spawner.getName());

		String line = String.format("%s:%s", spawnedEntity.getEntityId(),
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
	public void remove(LivingEntity RemovedEntity) {
		
		String entityID = String.valueOf(RemovedEntity.getEntityId());

		
		spnMap.remove(entityID);
		System.out.println("removed. current size: " + spnMap.size());
		try {
			writeToFile(spnMap);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	

	}

	public void writeToFile(Map<String, String> map)
			throws FileNotFoundException {

		PrintStream out = openFileStream();
		Iterator<String> itr = map.keySet().iterator();
		while (itr.hasNext()) {
			String ettID = itr.next();
			String spawnername = map.get(ettID);
			out.println(String.format("%s:%s", ettID, spawnername));
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

		String entityID = entity.getEntityId() + "";
		String playername = player.getName();

		if (spnMap.containsKey(entityID)) {

			if (playername.equals(spnMap.get(entityID))) {
				return true;
			}

		}
		return false;
	}

}
