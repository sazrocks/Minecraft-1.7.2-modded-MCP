package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WorldGenForest extends WorldGenAbstractTree
{
    private boolean field_150531_a;
    private static final String __OBFID = "CL_00000401";

    public WorldGenForest(boolean p_i45449_1_, boolean p_i45449_2_)
    {
        super(p_i45449_1_);
        this.field_150531_a = p_i45449_2_;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int var6 = par2Random.nextInt(3) + 5;

        if (this.field_150531_a)
        {
            var6 += par2Random.nextInt(7);
        }

        boolean var7 = true;

        if (par4 >= 1 && par4 + var6 + 1 <= 256)
        {
            int var10;
            int var11;

            for (int var8 = par4; var8 <= par4 + 1 + var6; ++var8)
            {
                byte var9 = 1;

                if (var8 == par4)
                {
                    var9 = 0;
                }

                if (var8 >= par4 + 1 + var6 - 2)
                {
                    var9 = 2;
                }

                for (var10 = par3 - var9; var10 <= par3 + var9 && var7; ++var10)
                {
                    for (var11 = par5 - var9; var11 <= par5 + var9 && var7; ++var11)
                    {
                        if (var8 >= 0 && var8 < 256)
                        {
                            Block var12 = par1World.func_147439_a(var10, var8, var11);

                            if (!this.func_150523_a(var12))
                            {
                                var7 = false;
                            }
                        }
                        else
                        {
                            var7 = false;
                        }
                    }
                }
            }

            if (!var7)
            {
                return false;
            }
            else
            {
                Block var17 = par1World.func_147439_a(par3, par4 - 1, par5);

                if ((var17 == Blocks.field_150349_c || var17 == Blocks.field_150346_d || var17 == Blocks.field_150458_ak) && par4 < 256 - var6 - 1)
                {
                    this.func_150515_a(par1World, par3, par4 - 1, par5, Blocks.field_150346_d);
                    int var18;

                    for (var18 = par4 - 3 + var6; var18 <= par4 + var6; ++var18)
                    {
                        var10 = var18 - (par4 + var6);
                        var11 = 1 - var10 / 2;

                        for (int var20 = par3 - var11; var20 <= par3 + var11; ++var20)
                        {
                            int var13 = var20 - par3;

                            for (int var14 = par5 - var11; var14 <= par5 + var11; ++var14)
                            {
                                int var15 = var14 - par5;

                                if (Math.abs(var13) != var11 || Math.abs(var15) != var11 || par2Random.nextInt(2) != 0 && var10 != 0)
                                {
                                    Block var16 = par1World.func_147439_a(var20, var18, var14);

                                    if (var16.func_149688_o() == Material.field_151579_a || var16.func_149688_o() == Material.field_151584_j)
                                    {
                                        this.func_150516_a(par1World, var20, var18, var14, Blocks.field_150362_t, 2);
                                    }
                                }
                            }
                        }
                    }

                    for (var18 = 0; var18 < var6; ++var18)
                    {
                        Block var19 = par1World.func_147439_a(par3, par4 + var18, par5);

                        if (var19.func_149688_o() == Material.field_151579_a || var19.func_149688_o() == Material.field_151584_j)
                        {
                            this.func_150516_a(par1World, par3, par4 + var18, par5, Blocks.field_150364_r, 2);
                        }
                    }

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }
}
