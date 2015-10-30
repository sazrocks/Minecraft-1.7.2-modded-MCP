package net.minecraft.client.gui;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.C00PacketKeepAlive;

public class GuiDownloadTerrain extends GuiScreen
{
    private NetHandlerPlayClient field_146594_a;
    private int field_146593_f;
    private static final String __OBFID = "CL_00000708";

    public GuiDownloadTerrain(NetHandlerPlayClient p_i45023_1_)
    {
        this.field_146594_a = p_i45023_1_;
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2) {}

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.field_146292_n.clear();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        ++this.field_146593_f;

        if (this.field_146593_f % 20 == 0)
        {
            this.field_146594_a.func_147297_a(new C00PacketKeepAlive());
        }

        if (this.field_146594_a != null)
        {
            this.field_146594_a.func_147233_a();
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.func_146278_c(0);
        this.drawCenteredString(this.field_146289_q, I18n.getStringParams("multiplayer.downloadingTerrain", new Object[0]), this.field_146294_l / 2, this.field_146295_m / 2 - 50, 16777215);
        super.drawScreen(par1, par2, par3);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
