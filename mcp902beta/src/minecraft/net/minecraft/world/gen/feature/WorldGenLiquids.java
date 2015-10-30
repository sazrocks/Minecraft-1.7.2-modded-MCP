package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WorldGenLiquids extends WorldGenerator
{
    private Block field_150521_a;
    private static final String __OBFID = "CL_00000434";

    public WorldGenLiquids(Block p_i45465_1_)
    {
        this.field_150521_a = p_i45465_1_;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        if (par1World.func_147439_a(par3, par4 + 1, par5) != Blocks.field_150348_b)
        {
            return false;
        }
        else if (par1World.func_147439_a(par3, par4 - 1, par5) != Blocks.field_150348_b)
        {
            return false;
        }
        else if (par1World.func_147439_a(par3, par4, par5).func_149688_o() != Material.field_151579_a && par1World.func_147439_a(par3, par4, par5) != Blocks.field_150348_b)
        {
            return false;
        }
        else
        {
            int var6 = 0;

            if (par1World.func_147439_a(par3 - 1, par4, par5) == Blocks.field_150348_b)
            {
                ++var6;
            }

            if (par1World.func_147439_a(par3 + 1, par4, par5) == Blocks.field_150348_b)
            {
                ++var6;
            }

            if (par1World.func_147439_a(par3, par4, par5 - 1) == Blocks.field_150348_b)
            {
                ++var6;
            }

            if (par1World.func_147439_a(par3, par4, par5 + 1) == Blocks.field_150348_b)
            {
                ++var6;
            }

            int var7 = 0;

            if (par1World.func_147437_c(par3 - 1, par4, par5))
            {
                ++var7;
            }

            if (par1World.func_147437_c(par3 + 1, par4, par5))
            {
                ++var7;
            }

            if (par1World.func_147437_c(par3, par4, par5 - 1))
            {
                ++var7;
            }

            if (par1World.func_147437_c(par3, par4, par5 + 1))
            {
                ++var7;
            }

            if (var6 == 3 && var7 == 1)
            {
                par1World.func_147465_d(par3, par4, par5, this.field_150521_a, 0, 2);
                par1World.scheduledUpdatesAreImmediate = true;
                this.field_150521_a.func_149674_a(par1World, par3, par4, par5, par2Random);
                par1World.scheduledUpdatesAreImmediate = false;
            }

            return true;
        }
    }
}
