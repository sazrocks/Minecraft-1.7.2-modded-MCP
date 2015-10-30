package net.minecraft.command;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public interface ICommandSender
{
    /**
     * Gets the name of this command sender (usually username, but possibly "Rcon")
     */
    String getCommandSenderName();

    IChatComponent func_145748_c_();

    void func_145747_a(IChatComponent var1);

    /**
     * Returns true if the command sender is allowed to use the given command.
     */
    boolean canCommandSenderUseCommand(int var1, String var2);

    /**
     * Return the position for this command sender.
     */
    ChunkCoordinates getPlayerCoordinates();

    World getEntityWorld();
}
