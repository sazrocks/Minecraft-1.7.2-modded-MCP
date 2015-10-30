package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.IIcon;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTallGrass extends BlockBush implements IGrowable
{
    private static final String[] field_149871_a = new String[] {"deadbush", "tallgrass", "fern"};
    private IIcon[] field_149870_b;
    private static final String __OBFID = "CL_00000321";

    protected BlockTallGrass()
    {
        super(Material.field_151582_l);
        float var1 = 0.4F;
        this.func_149676_a(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, 0.8F, 0.5F + var1);
    }

    public IIcon func_149691_a(int p_149691_1_, int p_149691_2_)
    {
        if (p_149691_2_ >= this.field_149870_b.length)
        {
            p_149691_2_ = 0;
        }

        return this.field_149870_b[p_149691_2_];
    }

    public int func_149635_D()
    {
        double var1 = 0.5D;
        double var3 = 1.0D;
        return ColorizerGrass.getGrassColor(var1, var3);
    }

    public boolean func_149718_j(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_)
    {
        return this.func_149854_a(p_149718_1_.func_147439_a(p_149718_2_, p_149718_3_ - 1, p_149718_4_));
    }

    public int func_149741_i(int p_149741_1_)
    {
        return p_149741_1_ == 0 ? 16777215 : ColorizerGrass.getGrassColor(0.5D, 1.0D);
    }

    public int func_149720_d(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
    {
        int var5 = p_149720_1_.getBlockMetadata(p_149720_2_, p_149720_3_, p_149720_4_);
        return var5 == 0 ? 16777215 : p_149720_1_.getBiomeGenForCoords(p_149720_2_, p_149720_4_).func_150558_b(p_149720_2_, p_149720_3_, p_149720_4_);
    }

    public Item func_149650_a(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return p_149650_2_.nextInt(8) == 0 ? Items.field_151014_N : null;
    }

    public int func_149679_a(int p_149679_1_, Random p_149679_2_)
    {
        return 1 + p_149679_2_.nextInt(p_149679_1_ * 2 + 1);
    }

    public void func_149636_a(World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_)
    {
        if (!p_149636_1_.isRemote && p_149636_2_.getCurrentEquippedItem() != null && p_149636_2_.getCurrentEquippedItem().getItem() == Items.field_151097_aZ)
        {
            p_149636_2_.addStat(StatList.mineBlockStatArray[Block.func_149682_b(this)], 1);
            this.func_149642_a(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, new ItemStack(Blocks.field_150329_H, 1, p_149636_6_));
        }
        else
        {
            super.func_149636_a(p_149636_1_, p_149636_2_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_);
        }
    }

    public int func_149643_k(World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_)
    {
        return p_149643_1_.getBlockMetadata(p_149643_2_, p_149643_3_, p_149643_4_);
    }

    public void func_149666_a(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
        for (int var4 = 1; var4 < 3; ++var4)
        {
            p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
        }
    }

    public void func_149651_a(IIconRegister p_149651_1_)
    {
        this.field_149870_b = new IIcon[field_149871_a.length];

        for (int var2 = 0; var2 < this.field_149870_b.length; ++var2)
        {
            this.field_149870_b[var2] = p_149651_1_.registerIcon(field_149871_a[var2]);
        }
    }

    public boolean func_149851_a(World p_149851_1_, int p_149851_2_, int p_149851_3_, int p_149851_4_, boolean p_149851_5_)
    {
        int var6 = p_149851_1_.getBlockMetadata(p_149851_2_, p_149851_3_, p_149851_4_);
        return var6 != 0;
    }

    public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_)
    {
        return true;
    }

    public void func_149853_b(World p_149853_1_, Random p_149853_2_, int p_149853_3_, int p_149853_4_, int p_149853_5_)
    {
        int var6 = p_149853_1_.getBlockMetadata(p_149853_3_, p_149853_4_, p_149853_5_);
        byte var7 = 2;

        if (var6 == 2)
        {
            var7 = 3;
        }

        if (Blocks.field_150398_cm.func_149742_c(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_))
        {
            Blocks.field_150398_cm.func_149889_c(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_, var7, 2);
        }
    }
}