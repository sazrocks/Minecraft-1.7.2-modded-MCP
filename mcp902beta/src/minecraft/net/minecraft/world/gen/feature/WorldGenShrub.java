package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WorldGenShrub extends WorldGenTrees
{
    private int field_150528_a;
    private int field_150527_b;
    private static final String __OBFID = "CL_00000411";

    public WorldGenShrub(int par1, int par2)
    {
        super(false);
        this.field_150527_b = par1;
        this.field_150528_a = par2;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        Block var6;

        while (((var6 = par1World.func_147439_a(par3, par4, par5)).func_149688_o() == Material.field_151579_a || var6.func_149688_o() == Material.field_151584_j) && par4 > 0)
        {
            --par4;
        }

        Block var7 = par1World.func_147439_a(par3, par4, par5);

        if (var7 == Blocks.field_150346_d || var7 == Blocks.field_150349_c)
        {
            ++par4;
            this.func_150516_a(par1World, par3, par4, par5, Blocks.field_150364_r, this.field_150527_b);

            for (int var8 = par4; var8 <= par4 + 2; ++var8)
            {
                int var9 = var8 - par4;
                int var10 = 2 - var9;

                for (int var11 = par3 - var10; var11 <= par3 + var10; ++var11)
                {
                    int var12 = var11 - par3;

                    for (int var13 = par5 - var10; var13 <= par5 + var10; ++var13)
                    {
                        int var14 = var13 - par5;

                        if ((Math.abs(var12) != var10 || Math.abs(var14) != var10 || par2Random.nextInt(2) != 0) && !par1World.func_147439_a(var11, var8, var13).func_149730_j())
                        {
                            this.func_150516_a(par1World, var11, var8, var13, Blocks.field_150362_t, this.field_150528_a);
                        }
                    }
                }
            }
        }

        return true;
    }
}
