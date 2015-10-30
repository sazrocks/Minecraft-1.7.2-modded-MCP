package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WorldGenTaiga1 extends WorldGenAbstractTree
{
    private static final String __OBFID = "CL_00000427";

    public WorldGenTaiga1()
    {
        super(false);
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int var6 = par2Random.nextInt(5) + 7;
        int var7 = var6 - par2Random.nextInt(2) - 3;
        int var8 = var6 - var7;
        int var9 = 1 + par2Random.nextInt(var8 + 1);
        boolean var10 = true;

        if (par4 >= 1 && par4 + var6 + 1 <= 256)
        {
            int var13;
            int var14;
            int var19;

            for (int var11 = par4; var11 <= par4 + 1 + var6 && var10; ++var11)
            {
                boolean var12 = true;

                if (var11 - par4 < var7)
                {
                    var19 = 0;
                }
                else
                {
                    var19 = var9;
                }

                for (var13 = par3 - var19; var13 <= par3 + var19 && var10; ++var13)
                {
                    for (var14 = par5 - var19; var14 <= par5 + var19 && var10; ++var14)
                    {
                        if (var11 >= 0 && var11 < 256)
                        {
                            Block var15 = par1World.func_147439_a(var13, var11, var14);

                            if (!this.func_150523_a(var15))
                            {
                                var10 = false;
                            }
                        }
                        else
                        {
                            var10 = false;
                        }
                    }
                }
            }

            if (!var10)
            {
                return false;
            }
            else
            {
                Block var18 = par1World.func_147439_a(par3, par4 - 1, par5);

                if ((var18 == Blocks.field_150349_c || var18 == Blocks.field_150346_d) && par4 < 256 - var6 - 1)
                {
                    this.func_150515_a(par1World, par3, par4 - 1, par5, Blocks.field_150346_d);
                    var19 = 0;

                    for (var13 = par4 + var6; var13 >= par4 + var7; --var13)
                    {
                        for (var14 = par3 - var19; var14 <= par3 + var19; ++var14)
                        {
                            int var21 = var14 - par3;

                            for (int var16 = par5 - var19; var16 <= par5 + var19; ++var16)
                            {
                                int var17 = var16 - par5;

                                if ((Math.abs(var21) != var19 || Math.abs(var17) != var19 || var19 <= 0) && !par1World.func_147439_a(var14, var13, var16).func_149730_j())
                                {
                                    this.func_150516_a(par1World, var14, var13, var16, Blocks.field_150362_t, 1);
                                }
                            }
                        }

                        if (var19 >= 1 && var13 == par4 + var7 + 1)
                        {
                            --var19;
                        }
                        else if (var19 < var9)
                        {
                            ++var19;
                        }
                    }

                    for (var13 = 0; var13 < var6 - 1; ++var13)
                    {
                        Block var20 = par1World.func_147439_a(par3, par4 + var13, par5);

                        if (var20.func_149688_o() == Material.field_151579_a || var20.func_149688_o() == Material.field_151584_j)
                        {
                            this.func_150516_a(par1World, par3, par4 + var13, par5, Blocks.field_150364_r, 1);
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
