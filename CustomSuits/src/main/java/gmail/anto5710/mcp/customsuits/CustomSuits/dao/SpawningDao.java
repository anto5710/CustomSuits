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
 * 이 클래스는 spawing된 엔티티들을 기록하는 역할을 합니다. 구체적으로, 플러그인의 디렉토리에 임의의 파일을 생성해서 사용자가
 * /spn명령어로 엔티티를 생성할때마다 그 내용을 기록합니다.
 * 
 * @author anto5710
 *
 */
public class SpawningDao {

	private final static String SPAWN_FILE_NAME = "spawned-entities.txt";
	private CustomSuitPlugin plugin;
	private Logger logger;
	private File ettFile;

	private Map<String, String> spnMap = new HashMap<>();

	public SpawningDao(CustomSuitPlugin plugin) {
		this.plugin = plugin;
		this.logger = plugin.getLogger();
	}

	public void init() {
		File pluginDir = plugin.getDataFolder();
		logger.info("plugin directory: " + pluginDir.getAbsolutePath());
		pluginDir.mkdir();

		ettFile = new File(pluginDir, SPAWN_FILE_NAME);
		
		if (!ettFile.exists()) {
			try {
				ettFile.createNewFile();
			} catch (IOException e) {
				logger.severe("fail to create spawning files");
			}
		}
		
		Scanner sc;
		try {
			sc = new Scanner(ettFile);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] values = line.split(":"); // "12222", "33223"
				spnMap.put(values[0], values[1]);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void saveEntity(Entity spawnedEntity, Player spawner) {

		spnMap.put("" + spawnedEntity.getEntityId(), "" + spawner.getName());

		String line = String.format("%s:%s", spawnedEntity.getEntityId(),
				spawner.getName());

		PrintStream out = null;
		try {
			FileOutputStream fos = new FileOutputStream(ettFile, true);
			out = new PrintStream(fos);
			out.println(line);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 
	 * @param deadEtt
	 * @param killer
	 */
	public void remove(LivingEntity deadEtt) {
		// 10000, 29383
		String ettID = String.valueOf(deadEtt.getEntityId());

		
		spnMap.remove(ettID);
		System.out.println("removed. current size: " + spnMap.size());
		try {
			writeToFile(spnMap);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// if ( spawnerID != null && spawnerID.equals( killerID ) ) {
		// spnMap.remove(ettID);
		// }

	}

	private void writeToFile(Map<String, String> map)
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

		FileOutputStream fos = new FileOutputStream(ettFile);
		return new PrintStream(fos);
	}

	/**
	 * 주어진 entity가 player에 의해서 생성된 것인지 판단합니다.
	 * 
	 * @param entity
	 * @param player
	 * @return 첫번재 파라미터인 entity를 생성한 플레이어가 두번째 player인 경우에는 true를 반환합니다. 그렇지 않으면
	 *         false를 반환합니다.
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
