package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WorldGenIcePath extends WorldGenerator
{
    private Block field_150555_a;
    private int field_150554_b;
    private static final String __OBFID = "CL_00000416";

    public WorldGenIcePath(int p_i45454_1_)
    {
        this.field_150555_a = Blocks.field_150403_cj;
        this.field_150554_b = p_i45454_1_;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        while (par1World.func_147437_c(par3, par4, par5) && par4 > 2)
        {
            --par4;
        }

        if (par1World.func_147439_a(par3, par4, par5) != Blocks.field_150433_aE)
        {
            return false;
        }
        else
        {
            int var6 = par2Random.nextInt(this.field_150554_b - 2) + 2;
            byte var7 = 1;

            for (int var8 = par3 - var6; var8 <= par3 + var6; ++var8)
            {
                for (int var9 = par5 - var6; var9 <= par5 + var6; ++var9)
                {
                    int var10 = var8 - par3;
                    int var11 = var9 - par5;

                    if (var10 * var10 + var11 * var11 <= var6 * var6)
                    {
                        for (int var12 = par4 - var7; var12 <= par4 + var7; ++var12)
                        {
                            Block var13 = par1World.func_147439_a(var8, var12, var9);

                            if (var13 == Blocks.field_150346_d || var13 == Blocks.field_150433_aE || var13 == Blocks.field_150432_aD)
                            {
                                par1World.func_147465_d(var8, var12, var9, this.field_150555_a, 0, 2);
                            }
                        }
                    }
                }
            }

            return true;
        }
    }
}
