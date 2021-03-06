package com.pam.harvestcraft.blocks.blocks;

import java.util.Random;

import com.pam.harvestcraft.HarvestCraft;
import com.pam.harvestcraft.ItemStackUtils;
import com.pam.harvestcraft.gui.GuiHandler;
import com.pam.harvestcraft.tileentities.TileEntityPresser;
import com.pam.harvestcraft.tileentities.TileEntityWaterFilter;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

public class WaterFilterBlock extends BlockContainerRotating {
	
	public static final String registryName = "waterfilter";

	public WaterFilterBlock() {
		super(Material.ROCK);
		setSoundType(SoundType.STONE);
		setCreativeTab(HarvestCraft.modTab);
		this.setHardness(1.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityWaterFilter();
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(this);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

		if(stack.hasDisplayName()) {
			// @TODO line 89
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		// keepInventory = true;
		// if (!keepInventory)
		// {
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if(tileentity instanceof TileEntityWaterFilter) {
			ItemStackUtils.dropInventoryItems(worldIn, pos,
					tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
			worldIn.updateComparatorOutputLevel(pos, this);
		}
		// }

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		}
		TileEntity te = world.getTileEntity(pos);
		if(!(te instanceof TileEntityWaterFilter)) {
			return false;
		}
		player.openGui(HarvestCraft.instance, GuiHandler.GUIID_WATERFILTER, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
}
