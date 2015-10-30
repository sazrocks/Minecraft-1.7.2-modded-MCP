package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WorldGenReed extends WorldGenerator
{
    private static final String __OBFID = "CL_00000429";

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        for (int var6 = 0; var6 < 20; ++var6)
        {
            int var7 = par3 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var8 = par4;
            int var9 = par5 + par2Random.nextInt(4) - par2Random.nextInt(4);

            if (par1World.func_147437_c(var7, par4, var9) && (par1World.func_147439_a(var7 - 1, par4 - 1, var9).func_149688_o() == Material.field_151586_h || par1World.func_147439_a(var7 + 1, par4 - 1, var9).func_149688_o() == Material.field_151586_h || par1World.func_147439_a(var7, par4 - 1, var9 - 1).func_149688_o() == Material.field_151586_h || par1World.func_147439_a(var7, par4 - 1, var9 + 1).func_149688_o() == Material.field_151586_h))
            {
                int var10 = 2 + par2Random.nextInt(par2Random.nextInt(3) + 1);

                for (int var11 = 0; var11 < var10; ++var11)
                {
                    if (Blocks.field_150436_aH.func_149718_j(par1World, var7, var8 + var11, var9))
                    {
                        par1World.func_147465_d(var7, var8 + var11, var9, Blocks.field_150436_aH, 0, 2);
                    }
                }
            }
        }

        return true;
    }
}
