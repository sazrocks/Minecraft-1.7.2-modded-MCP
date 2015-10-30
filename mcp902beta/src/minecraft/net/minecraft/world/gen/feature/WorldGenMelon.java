package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WorldGenMelon extends WorldGenerator
{
    private static final String __OBFID = "CL_00000424";

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        for (int var6 = 0; var6 < 64; ++var6)
        {
            int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            int var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

            if (Blocks.field_150440_ba.func_149742_c(par1World, var7, var8, var9) && par1World.func_147439_a(var7, var8 - 1, var9) == Blocks.field_150349_c)
            {
                par1World.func_147465_d(var7, var8, var9, Blocks.field_150440_ba, 0, 2);
            }
        }

        return true;
    }
}
