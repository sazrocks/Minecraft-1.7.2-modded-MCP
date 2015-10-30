package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHopper extends BlockContainer
{
    private final Random field_149922_a = new Random();
    private IIcon field_149921_b;
    private IIcon field_149923_M;
    private IIcon field_149924_N;
    private static final String __OBFID = "CL_00000257";

    public BlockHopper()
    {
        super(Material.field_151573_f);
        this.func_149647_a(CreativeTabs.tabRedstone);
        this.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public void func_149719_a(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
    {
        this.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public void func_149743_a(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_)
    {
        this.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
        super.func_149743_a(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        float var8 = 0.125F;
        this.func_149676_a(0.0F, 0.0F, 0.0F, var8, 1.0F, 1.0F);
        super.func_149743_a(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var8);
        super.func_149743_a(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.func_149676_a(1.0F - var8, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.func_149743_a(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.func_149676_a(0.0F, 0.0F, 1.0F - var8, 1.0F, 1.0F, 1.0F);
        super.func_149743_a(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public int func_149660_a(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_)
    {
        int var10 = Facing.oppositeSide[p_149660_5_];

        if (var10 == 1)
        {
            var10 = 0;
        }

        return var10;
    }

    public TileEntity func_149915_a(World p_149915_1_, int p_149915_2_)
    {
        return new TileEntityHopper();
    }

    public void func_149689_a(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
    {
        super.func_149689_a(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, p_149689_5_, p_149689_6_);

        if (p_149689_6_.hasDisplayName())
        {
            TileEntityHopper var7 = func_149920_e(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_);
            var7.func_145886_a(p_149689_6_.getDisplayName());
        }
    }

    public void func_149726_b(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
    {
        super.func_149726_b(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
        this.func_149919_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
    }

    public boolean func_149727_a(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (p_149727_1_.isRemote)
        {
            return true;
        }
        else
        {
            TileEntityHopper var10 = func_149920_e(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);

            if (var10 != null)
            {
                p_149727_5_.func_146093_a(var10);
            }

            return true;
        }
    }

    public void func_149695_a(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
    {
        this.func_149919_e(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
    }

    private void func_149919_e(World p_149919_1_, int p_149919_2_, int p_149919_3_, int p_149919_4_)
    {
        int var5 = p_149919_1_.getBlockMetadata(p_149919_2_, p_149919_3_, p_149919_4_);
        int var6 = func_149918_b(var5);
        boolean var7 = !p_149919_1_.isBlockIndirectlyGettingPowered(p_149919_2_, p_149919_3_, p_149919_4_);
        boolean var8 = func_149917_c(var5);

        if (var7 != var8)
        {
            p_149919_1_.setBlockMetadataWithNotify(p_149919_2_, p_149919_3_, p_149919_4_, var6 | (var7 ? 0 : 8), 4);
        }
    }

    public void func_149749_a(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
        TileEntityHopper var7 = (TileEntityHopper)p_149749_1_.func_147438_o(p_149749_2_, p_149749_3_, p_149749_4_);

        if (var7 != null)
        {
            for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8)
            {
                ItemStack var9 = var7.getStackInSlot(var8);

                if (var9 != null)
                {
                    float var10 = this.field_149922_a.nextFloat() * 0.8F + 0.1F;
                    float var11 = this.field_149922_a.nextFloat() * 0.8F + 0.1F;
                    float var12 = this.field_149922_a.nextFloat() * 0.8F + 0.1F;

                    while (var9.stackSize > 0)
                    {
                        int var13 = this.field_149922_a.nextInt(21) + 10;

                        if (var13 > var9.stackSize)
                        {
                            var13 = var9.stackSize;
                        }

                        var9.stackSize -= var13;
                        EntityItem var14 = new EntityItem(p_149749_1_, (double)((float)p_149749_2_ + var10), (double)((float)p_149749_3_ + var11), (double)((float)p_149749_4_ + var12), new ItemStack(var9.getItem(), var13, var9.getItemDamage()));

                        if (var9.hasTagCompound())
                        {
                            var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
                        }

                        float var15 = 0.05F;
                        var14.motionX = (double)((float)this.field_149922_a.nextGaussian() * var15);
                        var14.motionY = (double)((float)this.field_149922_a.nextGaussian() * var15 + 0.2F);
                        var14.motionZ = (double)((float)this.field_149922_a.nextGaussian() * var15);
                        p_149749_1_.spawnEntityInWorld(var14);
                    }
                }
            }

            p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
        }

        super.func_149749_a(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }

    public int func_149645_b()
    {
        return 38;
    }

    public boolean func_149686_d()
    {
        return false;
    }

    public boolean func_149662_c()
    {
        return false;
    }

    public boolean func_149646_a(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
    {
        return true;
    }

    public IIcon func_149691_a(int p_149691_1_, int p_149691_2_)
    {
        return p_149691_1_ == 1 ? this.field_149923_M : this.field_149921_b;
    }

    public static int func_149918_b(int p_149918_0_)
    {
        return p_149918_0_ & 7;
    }

    public static boolean func_149917_c(int p_149917_0_)
    {
        return (p_149917_0_ & 8) != 8;
    }

    public boolean func_149740_M()
    {
        return true;
    }

    public int func_149736_g(World p_149736_1_, int p_149736_2_, int p_149736_3_, int p_149736_4_, int p_149736_5_)
    {
        return Container.calcRedstoneFromInventory(func_149920_e(p_149736_1_, p_149736_2_, p_149736_3_, p_149736_4_));
    }

    public void func_149651_a(IIconRegister p_149651_1_)
    {
        this.field_149921_b = p_149651_1_.registerIcon("hopper_outside");
        this.field_149923_M = p_149651_1_.registerIcon("hopper_top");
        this.field_149924_N = p_149651_1_.registerIcon("hopper_inside");
    }

    public static IIcon func_149916_e(String p_149916_0_)
    {
        return p_149916_0_.equals("hopper_outside") ? Blocks.field_150438_bZ.field_149921_b : (p_149916_0_.equals("hopper_inside") ? Blocks.field_150438_bZ.field_149924_N : null);
    }

    public String func_149702_O()
    {
        return "hopper";
    }

    public static TileEntityHopper func_149920_e(IBlockAccess p_149920_0_, int p_149920_1_, int p_149920_2_, int p_149920_3_)
    {
        return (TileEntityHopper)p_149920_0_.func_147438_o(p_149920_1_, p_149920_2_, p_149920_3_);
    }
}
