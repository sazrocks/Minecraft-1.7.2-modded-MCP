package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WorldGenPumpkin extends WorldGenerator
{
    private static final String __OBFID = "CL_00000428";

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        for (int var6 = 0; var6 < 64; ++var6)
        {
            int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            int var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

            if (par1World.func_147437_c(var7, var8, var9) && par1World.func_147439_a(var7, var8 - 1, var9) == Blocks.field_150349_c && Blocks.field_150423_aK.func_149742_c(par1World, var7, var8, var9))
            {
                par1World.func_147465_d(var7, var8, var9, Blocks.field_150423_aK, par2Random.nextInt(4), 2);
            }
        }

        return true;
    }
}