package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class WorldGeneratorBonusChest extends WorldGenerator
{
    /**
     * Instance of WeightedRandomChestContent what will randomly generate items into the Bonus Chest.
     */
    private final WeightedRandomChestContent[] theBonusChestGenerator;

    /**
     * Value of this int will determine how much items gonna generate in Bonus Chest.
     */
    private final int itemsToGenerateInBonusChest;
    private static final String __OBFID = "CL_00000403";

    public WorldGeneratorBonusChest(WeightedRandomChestContent[] par1ArrayOfWeightedRandomChestContent, int par2)
    {
        this.theBonusChestGenerator = par1ArrayOfWeightedRandomChestContent;
        this.itemsToGenerateInBonusChest = par2;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        Block var6;

        while (((var6 = par1World.func_147439_a(par3, par4, par5)).func_149688_o() == Material.field_151579_a || var6.func_149688_o() == Material.field_151584_j) && par4 > 1)
        {
            --par4;
        }

        if (par4 < 1)
        {
            return false;
        }
        else
        {
            ++par4;

            for (int var7 = 0; var7 < 4; ++var7)
            {
                int var8 = par3 + par2Random.nextInt(4) - par2Random.nextInt(4);
                int var9 = par4 + par2Random.nextInt(3) - par2Random.nextInt(3);
                int var10 = par5 + par2Random.nextInt(4) - par2Random.nextInt(4);

                if (par1World.func_147437_c(var8, var9, var10) && World.func_147466_a(par1World, var8, var9 - 1, var10))
                {
                    par1World.func_147465_d(var8, var9, var10, Blocks.field_150486_ae, 0, 2);
                    TileEntityChest var11 = (TileEntityChest)par1World.func_147438_o(var8, var9, var10);

                    if (var11 != null && var11 != null)
                    {
                        WeightedRandomChestContent.generateChestContents(par2Random, this.theBonusChestGenerator, var11, this.itemsToGenerateInBonusChest);
                    }

                    if (par1World.func_147437_c(var8 - 1, var9, var10) && World.func_147466_a(par1World, var8 - 1, var9 - 1, var10))
                    {
                        par1World.func_147465_d(var8 - 1, var9, var10, Blocks.field_150478_aa, 0, 2);
                    }

                    if (par1World.func_147437_c(var8 + 1, var9, var10) && World.func_147466_a(par1World, var8 - 1, var9 - 1, var10))
                    {
                        par1World.func_147465_d(var8 + 1, var9, var10, Blocks.field_150478_aa, 0, 2);
                    }

                    if (par1World.func_147437_c(var8, var9, var10 - 1) && World.func_147466_a(par1World, var8 - 1, var9 - 1, var10))
                    {
                        par1World.func_147465_d(var8, var9, var10 - 1, Blocks.field_150478_aa, 0, 2);
                    }

                    if (par1World.func_147437_c(var8, var9, var10 + 1) && World.func_147466_a(par1World, var8 - 1, var9 - 1, var10))
                    {
                        par1World.func_147465_d(var8, var9, var10 + 1, Blocks.field_150478_aa, 0, 2);
                    }

                    return true;
                }
            }

            return false;
        }
    }
}
