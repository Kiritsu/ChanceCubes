package chanceCubes.rewards.giantRewards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.defaultRewards.BaseCustomReward;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.util.RewardsUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FluidSphereReward extends BaseCustomReward
{
	public FluidSphereReward()
	{
		super(CCubesCore.MODID + ":Fluid_Sphere", 0);
	}
	
	@Override
	public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
	{
		List<OffsetBlock> blocks = new ArrayList<OffsetBlock>();

		Block block = RewardsUtil.getRandomFluid().getBlock();

		int delay = 0;
		for(int i = 0; i <= 5; i++)
		{
			for(int yy = -5; yy < 6; yy++)
			{
				for(int zz = -5; zz < 6; zz++)
				{
					for(int xx = -5; xx < 6; xx++)
					{
						BlockPos loc = new BlockPos(xx, yy, zz);
						double dist = Math.abs(loc.getDistance(0, 0, 0));
						if(dist <= 5 - i && dist > 5 - (i + 1))
						{
							if(i == 0)
							{
								OffsetBlock osb = new OffsetBlock(xx, yy, zz, Blocks.GLASS, false, delay);
								osb.setBlockState(Blocks.GLASS.getDefaultState());
								blocks.add(osb);
								delay++;
							}
							else
							{
								OffsetBlock osb = new OffsetBlock(xx, yy, zz, block, false, delay);
								osb.setBlockState(block.getDefaultState());
								blocks.add(osb);
								delay++;
							}

						}
					}
				}
			}
			delay += 10;
		}

		for(OffsetBlock b : blocks)
			b.spawnInWorld(world, pos.getX(), pos.getY(), pos.getZ());
	}
}