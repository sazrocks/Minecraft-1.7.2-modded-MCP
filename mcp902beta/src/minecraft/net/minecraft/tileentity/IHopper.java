package net.minecraft.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

public interface IHopper extends IInventory
{
    World func_145831_w();

    /**
     * Gets the world X position for this hopper entity.
     */
    double getXPos();

    /**
     * Gets the world Y position for this hopper entity.
     */
    double getYPos();

    /**
     * Gets the world Z position for this hopper entity.
     */
    double getZPos();
}
