package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class WorldGenDeadBush extends WorldGenerator
{
    private Block field_150547_a;
    private static final String __OBFID = "CL_00000406";

    public WorldGenDeadBush(Block p_i45451_1_)
    {
        this.field_150547_a = p_i45451_1_;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        Block var6;

        while (((var6 = par1World.func_147439_a(par3, par4, par5)).func_149688_o() == Material.field_151579_a || var6.func_149688_o() == Material.field_151584_j) && par4 > 0)
        {
            --par4;
        }

        for (int var7 = 0; var7 < 4; ++var7)
        {
            int var8 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            int var9 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var10 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

            if (par1World.func_147437_c(var8, var9, var10) && this.field_150547_a.func_149718_j(par1World, var8, var9, var10))
            {
                par1World.func_147465_d(var8, var9, var10, this.field_150547_a, 0, 2);
            }
        }

        return true;
    }
}
