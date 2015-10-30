package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WorldGenSwamp extends WorldGenAbstractTree
{
    private static final String __OBFID = "CL_00000436";

    public WorldGenSwamp()
    {
        super(false);
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int var6;

        for (var6 = par2Random.nextInt(4) + 5; par1World.func_147439_a(par3, par4 - 1, par5).func_149688_o() == Material.field_151586_h; --par4)
        {
            ;
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
                    var9 = 3;
                }

                for (var10 = par3 - var9; var10 <= par3 + var9 && var7; ++var10)
                {
                    for (var11 = par5 - var9; var11 <= par5 + var9 && var7; ++var11)
                    {
                        if (var8 >= 0 && var8 < 256)
                        {
                            Block var12 = par1World.func_147439_a(var10, var8, var11);

                            if (var12.func_149688_o() != Material.field_151579_a && var12.func_149688_o() != Material.field_151584_j)
                            {
                                if (var12 != Blocks.field_150355_j && var12 != Blocks.field_150358_i)
                                {
                                    var7 = false;
                                }
                                else if (var8 > par4)
                                {
                                    var7 = false;
                                }
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
                Block var16 = par1World.func_147439_a(par3, par4 - 1, par5);

                if ((var16 == Blocks.field_150349_c || var16 == Blocks.field_150346_d) && par4 < 256 - var6 - 1)
                {
                    this.func_150515_a(par1World, par3, par4 - 1, par5, Blocks.field_150346_d);
                    int var13;
                    int var17;
                    int var19;

                    for (var17 = par4 - 3 + var6; var17 <= par4 + var6; ++var17)
                    {
                        var10 = var17 - (par4 + var6);
                        var11 = 2 - var10 / 2;

                        for (var19 = par3 - var11; var19 <= par3 + var11; ++var19)
                        {
                            var13 = var19 - par3;

                            for (int var14 = par5 - var11; var14 <= par5 + var11; ++var14)
                            {
                                int var15 = var14 - par5;

                                if ((Math.abs(var13) != var11 || Math.abs(var15) != var11 || par2Random.nextInt(2) != 0 && var10 != 0) && !par1World.func_147439_a(var19, var17, var14).func_149730_j())
                                {
                                    this.func_150515_a(par1World, var19, var17, var14, Blocks.field_150362_t);
                                }
                            }
                        }
                    }

                    for (var17 = 0; var17 < var6; ++var17)
                    {
                        Block var18 = par1World.func_147439_a(par3, par4 + var17, par5);

                        if (var18.func_149688_o() == Material.field_151579_a || var18.func_149688_o() == Material.field_151584_j || var18 == Blocks.field_150358_i || var18 == Blocks.field_150355_j)
                        {
                            this.func_150515_a(par1World, par3, par4 + var17, par5, Blocks.field_150364_r);
                        }
                    }

                    for (var17 = par4 - 3 + var6; var17 <= par4 + var6; ++var17)
                    {
                        var10 = var17 - (par4 + var6);
                        var11 = 2 - var10 / 2;

                        for (var19 = par3 - var11; var19 <= par3 + var11; ++var19)
                        {
                            for (var13 = par5 - var11; var13 <= par5 + var11; ++var13)
                            {
                                if (par1World.func_147439_a(var19, var17, var13).func_149688_o() == Material.field_151584_j)
                                {
                                    if (par2Random.nextInt(4) == 0 && par1World.func_147439_a(var19 - 1, var17, var13).func_149688_o() == Material.field_151579_a)
                                    {
                                        this.generateVines(par1World, var19 - 1, var17, var13, 8);
                                    }

                                    if (par2Random.nextInt(4) == 0 && par1World.func_147439_a(var19 + 1, var17, var13).func_149688_o() == Material.field_151579_a)
                                    {
                                        this.generateVines(par1World, var19 + 1, var17, var13, 2);
                                    }

                                    if (par2Random.nextInt(4) == 0 && par1World.func_147439_a(var19, var17, var13 - 1).func_149688_o() == Material.field_151579_a)
                                    {
                                        this.generateVines(par1World, var19, var17, var13 - 1, 1);
                                    }

                                    if (par2Random.nextInt(4) == 0 && par1World.func_147439_a(var19, var17, var13 + 1).func_149688_o() == Material.field_151579_a)
                                    {
                                        this.generateVines(par1World, var19, var17, var13 + 1, 4);
                                    }
                                }
                            }
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

    /**
     * Generates vines at the given position until it hits a block.
     */
    private void generateVines(World par1World, int par2, int par3, int par4, int par5)
    {
        this.func_150516_a(par1World, par2, par3, par4, Blocks.field_150395_bd, par5);
        int var6 = 4;

        while (true)
        {
            --par3;

            if (par1World.func_147439_a(par2, par3, par4).func_149688_o() != Material.field_151579_a || var6 <= 0)
            {
                return;
            }

            this.func_150516_a(par1World, par2, par3, par4, Blocks.field_150395_bd, par5);
            --var6;
        }
    }
}
