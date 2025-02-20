package chanceCubes.profiles.triggers;

import chanceCubes.profiles.IProfile;
import chanceCubes.profiles.ProfileManager;
import net.minecraft.world.EnumDifficulty;

public class DifficultyTrigger implements ITrigger<EnumDifficulty>
{
	private IProfile prof;
	private EnumDifficulty diff;

	public DifficultyTrigger(IProfile prof, EnumDifficulty diff)
	{
		this.prof = prof;
		this.diff = diff;
	}

	@Override
	public void onTrigger(EnumDifficulty[] args)
	{
		if(args.length == 2)
		{
			if(args[0].equals(diff))
				ProfileManager.enableProfile(prof);
			else if(args[1].equals(diff))
				ProfileManager.disableProfile(prof);
		}
	}

	@Override
	public String getTriggerDesc()
	{
		return "Trigger on game difficulty change";
	}
}
