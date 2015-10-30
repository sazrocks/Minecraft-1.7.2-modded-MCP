package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class WorldGenDungeons extends WorldGenerator
{
    private static final WeightedRandomChestContent[] field_111189_a = new WeightedRandomChestContent[] {new WeightedRandomChestContent(Items.field_151141_av, 0, 1, 1, 10), new WeightedRandomChestContent(Items.field_151042_j, 0, 1, 4, 10), new WeightedRandomChestContent(Items.field_151025_P, 0, 1, 1, 10), new WeightedRandomChestContent(Items.field_151015_O, 0, 1, 4, 10), new WeightedRandomChestContent(Items.field_151016_H, 0, 1, 4, 10), new WeightedRandomChestContent(Items.field_151007_F, 0, 1, 4, 10), new WeightedRandomChestContent(Items.field_151133_ar, 0, 1, 1, 10), new WeightedRandomChestContent(Items.field_151153_ao, 0, 1, 1, 1), new WeightedRandomChestContent(Items.field_151137_ax, 0, 1, 4, 10), new WeightedRandomChestContent(Items.field_151096_cd, 0, 1, 1, 10), new WeightedRandomChestContent(Items.field_151093_ce, 0, 1, 1, 10), new WeightedRandomChestContent(Items.field_151057_cb, 0, 1, 1, 10), new WeightedRandomChestContent(Items.field_151136_bY, 0, 1, 1, 2), new WeightedRandomChestContent(Items.field_151138_bX, 0, 1, 1, 5), new WeightedRandomChestContent(Items.field_151125_bZ, 0, 1, 1, 1)};
    private static final String __OBFID = "CL_00000425";

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        byte var6 = 3;
        int var7 = par2Random.nextInt(2) + 2;
        int var8 = par2Random.nextInt(2) + 2;
        int var9 = 0;
        int var10;
        int var11;
        int var12;

        for (var10 = par3 - var7 - 1; var10 <= par3 + var7 + 1; ++var10)
        {
            for (var11 = par4 - 1; var11 <= par4 + var6 + 1; ++var11)
            {
                for (var12 = par5 - var8 - 1; var12 <= par5 + var8 + 1; ++var12)
                {
                    Material var13 = par1World.func_147439_a(var10, var11, var12).func_149688_o();

                    if (var11 == par4 - 1 && !var13.isSolid())
                    {
                        return false;
                    }

                    if (var11 == par4 + var6 + 1 && !var13.isSolid())
                    {
                        return false;
                    }

                    if ((var10 == par3 - var7 - 1 || var10 == par3 + var7 + 1 || var12 == par5 - var8 - 1 || var12 == par5 + var8 + 1) && var11 == par4 && par1World.func_147437_c(var10, var11, var12) && par1World.func_147437_c(var10, var11 + 1, var12))
                    {
                        ++var9;
                    }
                }
            }
        }

        if (var9 >= 1 && var9 <= 5)
        {
            for (var10 = par3 - var7 - 1; var10 <= par3 + var7 + 1; ++var10)
            {
                for (var11 = par4 + var6; var11 >= par4 - 1; --var11)
                {
                    for (var12 = par5 - var8 - 1; var12 <= par5 + var8 + 1; ++var12)
                    {
                        if (var10 != par3 - var7 - 1 && var11 != par4 - 1 && var12 != par5 - var8 - 1 && var10 != par3 + var7 + 1 && var11 != par4 + var6 + 1 && var12 != par5 + var8 + 1)
                        {
                            par1World.func_147468_f(var10, var11, var12);
                        }
                        else if (var11 >= 0 && !par1World.func_147439_a(var10, var11 - 1, var12).func_149688_o().isSolid())
                        {
                            par1World.func_147468_f(var10, var11, var12);
                        }
                        else if (par1World.func_147439_a(var10, var11, var12).func_149688_o().isSolid())
                        {
                            if (var11 == par4 - 1 && par2Random.nextInt(4) != 0)
                            {
                                par1World.func_147465_d(var10, var11, var12, Blocks.field_150341_Y, 0, 2);
                            }
                            else
                            {
                                par1World.func_147465_d(var10, var11, var12, Blocks.field_150347_e, 0, 2);
                            }
                        }
                    }
                }
            }

            var10 = 0;

            while (var10 < 2)
            {
                var11 = 0;

                while (true)
                {
                    if (var11 < 3)
                    {
                        label197:
                        {
                            var12 = par3 + par2Random.nextInt(var7 * 2 + 1) - var7;
                            int var14 = par5 + par2Random.nextInt(var8 * 2 + 1) - var8;

                            if (par1World.func_147437_c(var12, par4, var14))
                            {
                                int var15 = 0;

                                if (par1World.func_147439_a(var12 - 1, par4, var14).func_149688_o().isSolid())
                                {
                                    ++var15;
                                }

                                if (par1World.func_147439_a(var12 + 1, par4, var14).func_149688_o().isSolid())
                                {
                                    ++var15;
                                }

                                if (par1World.func_147439_a(var12, par4, var14 - 1).func_149688_o().isSolid())
                                {
                                    ++var15;
                                }

                                if (par1World.func_147439_a(var12, par4, var14 + 1).func_149688_o().isSolid())
                                {
                                    ++var15;
                                }

                                if (var15 == 1)
                                {
                                    par1World.func_147465_d(var12, par4, var14, Blocks.field_150486_ae, 0, 2);
                                    WeightedRandomChestContent[] var16 = WeightedRandomChestContent.func_92080_a(field_111189_a, new WeightedRandomChestContent[] {Items.field_151134_bR.func_92114_b(par2Random)});
                                    TileEntityChest var17 = (TileEntityChest)par1World.func_147438_o(var12, par4, var14);

                                    if (var17 != null)
                                    {
                                        WeightedRandomChestContent.generateChestContents(par2Random, var16, var17, 8);
                                    }

                                    break label197;
                                }
                            }

                            ++var11;
                            continue;
                        }
                    }

                    ++var10;
                    break;
                }
            }

            par1World.func_147465_d(par3, par4, par5, Blocks.field_150474_ac, 0, 2);
            TileEntityMobSpawner var18 = (TileEntityMobSpawner)par1World.func_147438_o(par3, par4, par5);

            if (var18 != null)
            {
                var18.func_145881_a().setMobID(this.pickMobSpawner(par2Random));
            }
            else
            {
                System.err.println("Failed to fetch mob spawner entity at (" + par3 + ", " + par4 + ", " + par5 + ")");
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Randomly decides which spawner to use in a dungeon
     */
    private String pickMobSpawner(Random par1Random)
    {
        int var2 = par1Random.nextInt(4);
        return var2 == 0 ? "Skeleton" : (var2 == 1 ? "Zombie" : (var2 == 2 ? "Zombie" : (var2 == 3 ? "Spider" : "")));
    }
}
