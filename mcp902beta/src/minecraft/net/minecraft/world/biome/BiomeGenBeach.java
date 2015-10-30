package net.minecraft.world.biome;

import net.minecraft.init.Blocks;

public class BiomeGenBeach extends BiomeGenBase
{
    private static final String __OBFID = "CL_00000157";

    public BiomeGenBeach(int par1)
    {
        super(par1);
        this.spawnableCreatureList.clear();
        this.topBlock = Blocks.field_150354_m;
        this.fillerBlock = Blocks.field_150354_m;
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 0;
        this.theBiomeDecorator.reedsPerChunk = 0;
        this.theBiomeDecorator.cactiPerChunk = 0;
    }
}
