package net.minecraft.client.gui;

import java.util.Iterator;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

public class GuiGameOver extends GuiScreen
{
    private int field_146347_a;
    private boolean field_146346_f = false;
    private static final String __OBFID = "CL_00000690";

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.field_146292_n.clear();

        if (this.field_146297_k.theWorld.getWorldInfo().isHardcoreModeEnabled())
        {
            if (this.field_146297_k.isIntegratedServerRunning())
            {
                this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 96, I18n.getStringParams("deathScreen.deleteWorld", new Object[0])));
            }
            else
            {
                this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 96, I18n.getStringParams("deathScreen.leaveServer", new Object[0])));
            }
        }
        else
        {
            this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 72, I18n.getStringParams("deathScreen.respawn", new Object[0])));
            this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 96, I18n.getStringParams("deathScreen.titleScreen", new Object[0])));

            if (this.field_146297_k.getSession() == null)
            {
                ((GuiButton)this.field_146292_n.get(1)).field_146124_l = false;
            }
        }

        GuiButton var2;

        for (Iterator var1 = this.field_146292_n.iterator(); var1.hasNext(); var2.field_146124_l = false)
        {
            var2 = (GuiButton)var1.next();
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2) {}

    protected void func_146284_a(GuiButton p_146284_1_)
    {
        switch (p_146284_1_.field_146127_k)
        {
            case 0:
                this.field_146297_k.thePlayer.respawnPlayer();
                this.field_146297_k.func_147108_a((GuiScreen)null);
                break;

            case 1:
                GuiYesNo var2 = new GuiYesNo(this, I18n.getStringParams("deathScreen.quit.confirm", new Object[0]), "", I18n.getStringParams("deathScreen.titleScreen", new Object[0]), I18n.getStringParams("deathScreen.respawn", new Object[0]), 0);
                this.field_146297_k.func_147108_a(var2);
                var2.func_146350_a(20);
        }
    }

    public void confirmClicked(boolean par1, int par2)
    {
        if (par1)
        {
            this.field_146297_k.theWorld.sendQuittingDisconnectingPacket();
            this.field_146297_k.loadWorld((WorldClient)null);
            this.field_146297_k.func_147108_a(new GuiMainMenu());
        }
        else
        {
            this.field_146297_k.thePlayer.respawnPlayer();
            this.field_146297_k.func_147108_a((GuiScreen)null);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawGradientRect(0, 0, this.field_146294_l, this.field_146295_m, 1615855616, -1602211792);
        GL11.glPushMatrix();
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        boolean var4 = this.field_146297_k.theWorld.getWorldInfo().isHardcoreModeEnabled();
        String var5 = var4 ? I18n.getStringParams("deathScreen.title.hardcore", new Object[0]) : I18n.getStringParams("deathScreen.title", new Object[0]);
        this.drawCenteredString(this.field_146289_q, var5, this.field_146294_l / 2 / 2, 30, 16777215);
        GL11.glPopMatrix();

        if (var4)
        {
            this.drawCenteredString(this.field_146289_q, I18n.getStringParams("deathScreen.hardcoreInfo", new Object[0]), this.field_146294_l / 2, 144, 16777215);
        }

        this.drawCenteredString(this.field_146289_q, I18n.getStringParams("deathScreen.score", new Object[0]) + ": " + EnumChatFormatting.YELLOW + this.field_146297_k.thePlayer.getScore(), this.field_146294_l / 2, 100, 16777215);
        super.drawScreen(par1, par2, par3);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        ++this.field_146347_a;
        GuiButton var2;

        if (this.field_146347_a == 20)
        {
            for (Iterator var1 = this.field_146292_n.iterator(); var1.hasNext(); var2.field_146124_l = true)
            {
                var2 = (GuiButton)var1.next();
            }
        }
    }
}
