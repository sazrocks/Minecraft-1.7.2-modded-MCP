package net.minecraft.world;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.biome.BiomeGenBase;

public interface IBlockAccess
{
    Block func_147439_a(int var1, int var2, int var3);

    TileEntity func_147438_o(int var1, int var2, int var3);

    /**
     * Any Light rendered on a 1.8 Block goes through here
     */
    int getLightBrightnessForSkyBlocks(int var1, int var2, int var3, int var4);

    /**
     * Returns the block metadata at coords x,y,z
     */
    int getBlockMetadata(int var1, int var2, int var3);

    boolean func_147437_c(int var1, int var2, int var3);

    /**
     * Gets the biome for a given set of x/z coordinates
     */
    BiomeGenBase getBiomeGenForCoords(int var1, int var2);

    /**
     * Returns current world height.
     */
    int getHeight();

    /**
     * set by !chunk.getAreLevelsEmpty
     */
    boolean extendedLevelsInChunkCache();

    /**
     * Return the Vec3Pool object for this world.
     */
    Vec3Pool getWorldVec3Pool();

    /**
     * Is this block powering in the specified direction Args: x, y, z, direction
     */
    int isBlockProvidingPowerTo(int var1, int var2, int var3, int var4);
}
