package chanceCubes.rewards.giantRewards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.biodomeGen.BasicTreesBiome;
import chanceCubes.rewards.biodomeGen.DesertBiome;
import chanceCubes.rewards.biodomeGen.EndBiome;
import chanceCubes.rewards.biodomeGen.IBioDomeBiome;
import chanceCubes.rewards.biodomeGen.NetherBiome;
import chanceCubes.rewards.biodomeGen.OceanBiome;
import chanceCubes.rewards.biodomeGen.SnowGlobeBiome;
import chanceCubes.rewards.defaultRewards.BaseCustomReward;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BioDomeReward extends BaseCustomReward
{
	// @formatter:off
	private IBioDomeBiome[] biomes = new IBioDomeBiome[] { new BasicTreesBiome(), new DesertBiome(), 
			new EndBiome(), new OceanBiome(), new SnowGlobeBiome(), new NetherBiome() };
	// @formatter:on

	public static final int delayShorten = 10;
	
	public BioDomeReward()
	{
		super(CCubesCore.MODID + ":BioDome", 0);
	}

	@Override
	public void trigger(final World world, final BlockPos pos, EntityPlayer player, Map<String, Object> settings)
	{
		// player.addChatMessage(new ChatComponentText("Hey! I can be a Pandora's Box to!"));

		final IBioDomeBiome spawnedBiome = biomes[RewardsUtil.rand.nextInt(biomes.length)];
		this.genDome(pos, world, spawnedBiome);
	}

	public void genDome(final BlockPos pos, final World world, final IBioDomeBiome spawnedBiome)
	{
		this.genDomePart(0, -25, pos, world, spawnedBiome);
	}

	public void genDomePart(final int yinc, final int xinc, final BlockPos pos, final World world, final IBioDomeBiome spawnedBiome)
	{
		List<OffsetBlock> blocks = new ArrayList<OffsetBlock>();
		int delay = 0;
		for(int z = -25; z <= 25; z++)
		{
			BlockPos loc = new BlockPos(xinc, yinc, z);
			float dist = (float) (Math.abs(loc.getDistance(0, 0, 0)) - 25);
			if(dist < 1)
			{
				if(dist >= 0)
				{
					blocks.add(new OffsetBlock(xinc, yinc, z, Blocks.GLASS, false, (delay / delayShorten)));
					delay++;
				}
				else if(yinc == 0)
				{
					blocks.add(new OffsetBlock(xinc, yinc, z, spawnedBiome.getFloorBlock(), false, (delay / delayShorten)));
					delay++;
				}
				spawnedBiome.getRandomGenBlock(dist, RewardsUtil.rand, xinc, yinc, z, blocks, delay);
			}
		}

		final int nextXinc = xinc + 1 > 25 ? (-25) : xinc + 1;
		int Yinctemp = yinc;
		if(nextXinc == -25)
		{
			Yinctemp = Yinctemp + 1 > 25 ? -1 : Yinctemp + 1;
		}

		if(Yinctemp == -1)
		{
			Scheduler.scheduleTask(new Task("Entity_Delays", delay)
			{
				@Override
				public void callback()
				{
					spawnedBiome.spawnEntities(pos, world);
				}
			});
			return;
		}

		final int nextYinc = Yinctemp;

		for(OffsetBlock b : blocks)
			b.spawnInWorld(world, pos.getX(), pos.getY(), pos.getZ());

		Task task = new Task("BioDome Reward", (delay / delayShorten))
		{

			@Override
			public void callback()
			{
				genDomePart(nextYinc, nextXinc, pos, world, spawnedBiome);
			}

		};

		Scheduler.scheduleTask(task);
	}
}