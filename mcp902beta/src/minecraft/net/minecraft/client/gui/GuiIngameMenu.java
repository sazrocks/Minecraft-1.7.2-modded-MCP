package net.minecraft.client.gui;

import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;

public class GuiIngameMenu extends GuiScreen
{
    private int field_146445_a;
    private int field_146444_f;
    private static final String __OBFID = "CL_00000703";

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.field_146445_a = 0;
        this.field_146292_n.clear();
        byte var1 = -16;
        boolean var2 = true;
        this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 120 + var1, I18n.getStringParams("menu.returnToMenu", new Object[0])));

        if (!this.field_146297_k.isIntegratedServerRunning())
        {
            ((GuiButton)this.field_146292_n.get(0)).field_146126_j = I18n.getStringParams("menu.disconnect", new Object[0]);
        }

        this.field_146292_n.add(new GuiButton(4, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 24 + var1, I18n.getStringParams("menu.returnToGame", new Object[0])));
        this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 96 + var1, 98, 20, I18n.getStringParams("menu.options", new Object[0])));
        GuiButton var3;
        this.field_146292_n.add(var3 = new GuiButton(7, this.field_146294_l / 2 + 2, this.field_146295_m / 4 + 96 + var1, 98, 20, I18n.getStringParams("menu.shareToLan", new Object[0])));
        this.field_146292_n.add(new GuiButton(5, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 48 + var1, 98, 20, I18n.getStringParams("gui.achievements", new Object[0])));
        this.field_146292_n.add(new GuiButton(6, this.field_146294_l / 2 + 2, this.field_146295_m / 4 + 48 + var1, 98, 20, I18n.getStringParams("gui.stats", new Object[0])));
        var3.field_146124_l = this.field_146297_k.isSingleplayer() && !this.field_146297_k.getIntegratedServer().getPublic();
    }

    protected void func_146284_a(GuiButton p_146284_1_)
    {
        switch (p_146284_1_.field_146127_k)
        {
            case 0:
                this.field_146297_k.func_147108_a(new GuiOptions(this, this.field_146297_k.gameSettings));
                break;

            case 1:
                p_146284_1_.field_146124_l = false;
                this.field_146297_k.theWorld.sendQuittingDisconnectingPacket();
                this.field_146297_k.loadWorld((WorldClient)null);
                this.field_146297_k.func_147108_a(new GuiMainMenu());

            case 2:
            case 3:
            default:
                break;

            case 4:
                this.field_146297_k.func_147108_a((GuiScreen)null);
                this.field_146297_k.setIngameFocus();
                break;

            case 5:
                this.field_146297_k.func_147108_a(new GuiAchievements(this, this.field_146297_k.thePlayer.func_146107_m()));
                break;

            case 6:
                this.field_146297_k.func_147108_a(new GuiStats(this, this.field_146297_k.thePlayer.func_146107_m()));
                break;

            case 7:
                this.field_146297_k.func_147108_a(new GuiShareToLan(this));
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        ++this.field_146444_f;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.func_146276_q_();
        this.drawCenteredString(this.field_146289_q, "Game menu", this.field_146294_l / 2, 40, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
