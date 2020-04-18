package gmail.anto5710.mcp.mgear.ibamboo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Sets;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.dao.LinearDao;
import gmail.anto5710.mcp.customsuits.Utils.BinarySearch;
import gmail.anto5710.mcp.customsuits.Utils.MathUtil;
import gmail.anto5710.mcp.customsuits.Utils.compressor.ShiftCoordEncoder;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.LinearEncompassor;
import gmail.anto5710.mcp.customsuits.Utils.items.ItemUtil;
import gmail.anto5710.mcp.customsuits.Utils.particles.ParticleUtil;

public class IronBamboo extends LinearEncompassor<Block>{
	public LinearDao<Set<Block>, Block> bambooDao;
	private Map<Double, List<Integer>> bamboo_columns = new HashMap<>();
	private static final String bambooDao_path = "iron_bamboos.txt"; 
	
	public IronBamboo(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
	}
	
	public void init() {
	
		bambooDao = new LinearDao<Set<Block>, Block>(bambooDao_path, plugin) {
			
			@Override
			public void readNextLine(String line) {
				String[]coord = line.trim().replace("(", "").replace(")","").split(","); 
				
				if(coord !=null && coord.length >= 4) {
					
					World world = null;
					try{
						world = Bukkit.getWorld(UUID.fromString(coord[0]));
						double x = Integer.parseInt(coord[1].trim());
						double y = Integer.parseInt(coord[2].trim());
						double z = Integer.parseInt(coord[3].trim());
						data.add(new Location(world, x, y, z).getBlock());
						
					} catch (NumberFormatException e) {
						logger.severe(String.format("[WARN]: Couldn't determine the location of the Iron Bamboo from coordinate (%s,%s,%s)", coord[1], coord[2], coord[3]));
					} catch (IllegalArgumentException e) {
						logger.severe("[WARN]: Couldn't find the matching world of the Iron Bamboo from UUID: "+coord[0]);
					} 
				}
			}

			@Override
			public String writeNextLine(Block e) {
				return String.format("(%s, %d, %d, %d)", e.getWorld().getUID().toString(), e.getX(), e.getY(), e.getZ());
			}

			@Override
			public Set<Block> defaultData() {
				return new HashSet<Block>();
			}
		};
		
		for(Block bamboo : bambooDao.read()) {
			register(bamboo);
		}
	}
	
	public boolean save() {
		return bambooDao.save(entia);
	}

	private static ShiftCoordEncoder coorder = new ShiftCoordEncoder();
	
	@Override
	public void register(Block bamboo) {		
		super.register(bamboo);
		putHeight(bamboo);
		
	}
	
	private double encode(Block block) {
		return coorder.encodeDouble(block.getX(), block.getZ());
	}
	
	private void putHeight(Block block) {
		double codeXZ = encode(block);
		List<Integer>heights = this.bamboo_columns.get(codeXZ);
		if(heights==null) {heights = new ArrayList<>();}
		heights.add(block.getY());
		this.bamboo_columns.put(codeXZ, heights);
	}
	
	private static final float IRONIZ_PROBABILITY = 100 /* 25 */;
	
	private static Set<BlockFace> square = Sets.newHashSet(BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH, BlockFace.NORTH, BlockFace.DOWN);
	
	@EventHandler
	public void onBambooFertilized(BlockFertilizeEvent e) {
		Block shootApex = e.getBlock();
		Block ground = getGround(shootApex);
		if(isBamboo(ground.getRelative(BlockFace.UP))) {
			List<BlockState>bamboos = e.getBlocks();
			
			Collections.sort(bamboos, (b1, b2)->{return b1.getY()-b2.getY();});
			Block top = bamboos.get(bamboos.size()-1).getBlock(); // the top block(bamboo) gets registered.
			
			for (BlockFace face : square) {
				Block adjacent = ground.getRelative(face);
				if (adjacent.getType() == Material.IRON_ORE) {
					ironize(top, adjacent); break;
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onBambooGrowth(BlockSpreadEvent e) {
		Block shootApex = e.getBlock(); // currently Air
		Block ground = getGround(e.getBlock());
		if (isBamboo(ground.getRelative(BlockFace.UP))) {
			for (BlockFace face : square) {
				Block adjacent = ground.getRelative(face);
				if (adjacent.getType() == Material.IRON_ORE) {
					ironize(shootApex, adjacent);
					break;
				}
			}
		}
	}
	
	private static Block getGround(@Nonnull Block crop) {
		do {
			crop = crop.getRelative(BlockFace.DOWN);
		} while (isBamboo(crop));
		return crop;
	}
	
	private boolean ironize(Block shootApex, Block iron_source) {	
//		iron_source.setType(Material.STONE);
		if(MathUtil.gacha(IRONIZ_PROBABILITY)) {
			register(shootApex); 
			return true;
		}
		return false;
	}

	
	@EventHandler
	public void d(BlockBreakEvent e) {
		Block brokenBlock = e.getBlock();
		brokenBlock.getRelative(BlockFace.EAST).setType(Material.REDSTONE_BLOCK);
		
	}
	
	@EventHandler
	public void onBambooDrop(BlockDropItemEvent e) {		
		Block brokenBlock = e.getBlock();
		brokenBlock.getRelative(BlockFace.EAST).setType(Material.REDSTONE_BLOCK);
		
		if(isBamboo(e.getBlockState().getType())) {
			int count = coutFrom(brokenBlock);
			System.out.println(Arrays.asList(e.getItems().parallelStream().map((i)->i.getItemStack()).toArray()));
			if(count > 0) {
				for(Item drop : e.getItems()) {
					ItemStack item = drop.getItemStack(); 
					if(isBamboo(item.getType()) && !ItemUtil.compare(CustomSuitPlugin.mg_ironbamboo, item)) { // not yet ironized
						ItemStack newDrop = CustomSuitPlugin.mg_ironbamboo.clone();
						newDrop.setAmount(count);
						Bukkit.getServer().broadcastMessage("Dropped "+count);
						drop.setItemStack(newDrop); // replace the ItemStack of dropped Item entity with ironized bamboos.
					}
				}
			}
		}
	}
	
	private int coutFrom(@Nonnull Block stem) {
//		int c = 0;
//		for(Block bamboo : entia) {
//			if(bamboo.getWorld() == stem.getWorld() && bamboo.getX() == stem.getX() &&
//					bamboo.getY() >= stem.getY() && bamboo.getZ() == stem.getZ()) {
//				c++;
//			}
//		}
//		return c;
		List<Integer> heights = bamboo_columns.get(encode(stem));
		if(heights == null || heights.isEmpty()) return 0;
		
		System.out.println(heights);
		int floor = BinarySearch.floorIndex(stem.getY(), heights, true);
		int height = floor ==-1 &&  Math.abs(heights.get(0)-stem.getY())<1? 1:0;
		System.out.println(stem.getY()+"stem -> "+height+" above");
		return height;     
	}
		
	private static boolean isBamboo(@Nonnull Material type) {
		return type == Material.BAMBOO || type == Material.BAMBOO_SAPLING;
	}
	
	private static boolean isBamboo(@Nonnull Block block) {
		return isBamboo(block.getType());
	}

	@Override
	public boolean toRemove(Block block) {
		return !isBamboo(block.getType());
	}
	
	private static final BlockData ironbamboo_effect = Material.IRON_BLOCK.createBlockData();
	@Override
	public void particulate(Block bamboo) {
		ParticleUtil.playBlockEffect(Particle.BLOCK_DUST, bamboo.getLocation().add(0,0.5,0), 4, ironbamboo_effect);
	}
}
