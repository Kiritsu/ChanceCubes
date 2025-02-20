package chanceCubes.rewards.defaultRewards;

import java.util.Map;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.util.RewardsUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DoubleRainbow extends BaseCustomReward
{
	byte[] colorsMeta = { 14, 1, 4, 13, 11, 10 };

	public DoubleRainbow()
	{
		super(CCubesCore.MODID + ":Double_Rainbow", 15);
	}

	@Override
	public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
	{
		RewardsUtil.sendMessageToNearPlayers(world, pos, 32, "Double Rainbow!");
		OffsetBlock b;
		for(int x = -7; x < 8; x++)
		{
			for(int y = 0; y < 8; y++)
			{
				float dist = (float) (Math.abs(pos.getDistance(pos.getX() + x, pos.getY() + y, pos.getZ())));
				if(dist > 1 && dist <= 8)
				{
					int distIndex = (int) (dist - 2);
					b = new OffsetBlock(x, y, 0, Blocks.WOOL, false);
					b.setBlockState(RewardsUtil.getBlockStateFromBlockMeta(Blocks.WOOL, colorsMeta[distIndex]));
					b.setDelay((x + 7) * 10);
					b.spawnInWorld(world, pos.getX(), pos.getY(), pos.getZ());
				}
			}
		}

		for(int x = -17; x < 18; x++)
		{
			for(int y = 0; y < 18; y++)
			{
				float dist = (float) (Math.abs(pos.getDistance(pos.getX() + x, pos.getY() + y, pos.getZ())));
				if(dist >= 12 && dist <= 18)
				{
					int distIndex = (int) (dist - 12);
					b = new OffsetBlock(x, y, 0, Blocks.WOOL, false);
					b.setBlockState(RewardsUtil.getBlockStateFromBlockMeta(Blocks.WOOL, colorsMeta[distIndex]));
					b.setDelay((x + 12) * 5);
					b.spawnInWorld(world, pos.getX(), pos.getY(), pos.getZ());
				}
			}
		}
	}
}
