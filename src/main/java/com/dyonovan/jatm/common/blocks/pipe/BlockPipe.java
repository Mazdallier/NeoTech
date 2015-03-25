package com.dyonovan.jatm.common.blocks.pipe;

import com.dyonovan.jatm.JATM;
import com.dyonovan.jatm.collections.DummyState;
import com.dyonovan.jatm.common.blocks.BlockBakeable;
import com.dyonovan.jatm.lib.Constants;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public abstract class BlockPipe extends BlockBakeable {
    public BlockPipe(Material materialIn, String name, Class<? extends TileEntity> tileClass) {
        super(materialIn, name, tileClass);
        setCreativeTab(JATM.tabJATM);
        setUnlocalizedName(Constants.MODID + ":" + name);
    }

    public abstract boolean isCableConnected(IBlockAccess blockaccess, BlockPos pos, EnumFacing face);

    /**
     * Get width (from center 0.0F - 8.0F)
     * @return Radius of pipe
     */
    public abstract float getWidth();

    public abstract String getBackgroundTexture();

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
        float x1 = 0.25F;
        float x2 = 1.0F - x1;
        float y1 = 0.25F;
        float y2 = 1.0F - y1;
        float z1 = 0.25F;
        float z2 = 1.0F - z1;
        if(isCableConnected(worldIn, pos.west(), EnumFacing.WEST)) {
            x1 = 0.0F;
        }

        if(isCableConnected(worldIn, pos.east(), EnumFacing.EAST)) {
            x2 = 1.0F;
        }

        if(isCableConnected(worldIn, pos.north(), EnumFacing.NORTH)) {
            z1 = 0.0F;
        }

        if(isCableConnected(worldIn, pos.south(), EnumFacing.SOUTH)) {
            z2 = 1.0F;
        }

        if(isCableConnected(worldIn, pos.down(), EnumFacing.DOWN)) {
            y1 = 0.0F;
        }

        if(isCableConnected(worldIn, pos.up(), EnumFacing.UP)) {
            y2 = 1.0F;
        }

        this.setBlockBounds(x1, y1, z1, x2, y2, z2);
    }

    @Override
    public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity) {
        this.setBlockBoundsBasedOnState(worldIn, pos);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        return new DummyState(world, pos, (BlockBakeable) world.getBlockState(pos).getBlock());
    }

    @Override
    public boolean isBlockNormalCube() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isFullBlock() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean isTranslucent() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
}
