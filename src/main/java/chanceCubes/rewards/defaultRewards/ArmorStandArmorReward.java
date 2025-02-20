package chanceCubes.rewards.defaultRewards;

import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ArmorStandArmorReward extends BaseCustomReward
{

	public ArmorStandArmorReward()
	{
		super(CCubesCore.MODID + ":Armor_Stand_Armor", 40);
	}

	// @formatter:off
	private String[] names = {"dmodoomsirius", "MJRLegends", "Twp156", "JSL7", "Ratblade", "DerRedstoneProfi", "Turkey2349"};
	
	private ItemStack[] headItems = {new ItemStack(Items.CHAINMAIL_HELMET), new ItemStack(Items.DIAMOND_HELMET),
			new ItemStack(Items.GOLDEN_HELMET), new ItemStack(Items.IRON_HELMET), new ItemStack(Items.LEATHER_HELMET),
			new ItemStack(Items.SKULL, 1, 0), new ItemStack(Items.SKULL, 1, 1), new ItemStack(Items.SKULL, 1, 2),
			new ItemStack(Items.SKULL, 1, 3), new ItemStack(Items.SKULL, 1, 4), new ItemStack(Items.SKULL, 1, 5),
			new ItemStack(Blocks.CHEST)};
	
	private ItemStack[] chestItems = {new ItemStack(Items.CHAINMAIL_CHESTPLATE), new ItemStack(Items.DIAMOND_CHESTPLATE),
			new ItemStack(Items.GOLDEN_CHESTPLATE), new ItemStack(Items.IRON_CHESTPLATE), new ItemStack(Items.LEATHER_CHESTPLATE),
			new ItemStack(Items.ELYTRA), new ItemStack(Items.BANNER)};
	
	private ItemStack[] legsItems = {new ItemStack(Items.CHAINMAIL_LEGGINGS), new ItemStack(Items.DIAMOND_LEGGINGS),
			new ItemStack(Items.GOLDEN_LEGGINGS), new ItemStack(Items.IRON_LEGGINGS), new ItemStack(Items.LEATHER_LEGGINGS)};
	
	private ItemStack[] bootsItems = {new ItemStack(Items.CHAINMAIL_BOOTS), new ItemStack(Items.DIAMOND_BOOTS),
			new ItemStack(Items.GOLDEN_BOOTS), new ItemStack(Items.IRON_BOOTS), new ItemStack(Items.LEATHER_BOOTS)};
	
	private ItemStack[] handItems = {new ItemStack(Items.CAKE), new ItemStack(Blocks.TORCH),
			new ItemStack(Items.SHIELD), new ItemStack(Items.IRON_SWORD), new ItemStack(Items.DIAMOND_HOE),
			new ItemStack(Items.BANNER), new ItemStack(Items.COOKIE), new ItemStack(Items.STICK),
			new ItemStack(Items.GOLDEN_CARROT)};
	// @formatter:on

	@Override
	public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
	{
		EntityArmorStand armorStand = new EntityArmorStand(world);
		String[] allNames = ArrayUtils.addAll(names, super.getSettingAsStringList(settings, "names", new String[0]));
		String name = allNames[RewardsUtil.rand.nextInt(allNames.length)];
		armorStand.setCustomNameTag(name);
		armorStand.setAlwaysRenderNameTag(true);
		armorStand.setPositionAndRotation(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0);

		ItemStack[] allHeadItems = ArrayUtils.addAll(headItems, super.getSettingAsItemStackList(settings, "head_item_stacks", new ItemStack[0]));
		ItemStack headStack = allHeadItems[RewardsUtil.rand.nextInt(allHeadItems.length)].copy();
		if(headStack.getItem() instanceof ItemSkull && headStack.getItemDamage() == 3)
		{
			NBTTagCompound nbt = headStack.getTagCompound();
			if(nbt == null)
			{
				nbt = new NBTTagCompound();
				headStack.setTagCompound(nbt);
			}
			nbt.setString("SkullOwner", name);
		}

		armorStand.setItemStackToSlot(EntityEquipmentSlot.HEAD, headStack);
		ItemStack[] allChestItems = ArrayUtils.addAll(chestItems, super.getSettingAsItemStackList(settings, "chest_item_stacks", new ItemStack[0]));
		armorStand.setItemStackToSlot(EntityEquipmentSlot.CHEST, allChestItems[RewardsUtil.rand.nextInt(allChestItems.length)].copy());
		ItemStack[] allLegsItems = ArrayUtils.addAll(legsItems, super.getSettingAsItemStackList(settings, "leg_item_stacks", new ItemStack[0]));
		armorStand.setItemStackToSlot(EntityEquipmentSlot.LEGS, allLegsItems[RewardsUtil.rand.nextInt(allLegsItems.length)].copy());
		ItemStack[] allBootItems = ArrayUtils.addAll(bootsItems, super.getSettingAsItemStackList(settings, "boot_item_stacks", new ItemStack[0]));
		armorStand.setItemStackToSlot(EntityEquipmentSlot.FEET, allBootItems[RewardsUtil.rand.nextInt(allBootItems.length)].copy());
		ItemStack[] allHandItems = ArrayUtils.addAll(handItems, super.getSettingAsItemStackList(settings, "hand_item_stacks", new ItemStack[0]));
		armorStand.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, allHandItems[RewardsUtil.rand.nextInt(allHandItems.length)].copy());
		armorStand.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, allHandItems[RewardsUtil.rand.nextInt(allHandItems.length)].copy());
		world.spawnEntity(armorStand);
	}
}