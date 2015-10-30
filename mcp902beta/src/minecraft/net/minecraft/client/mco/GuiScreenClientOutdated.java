package net.minecraft.client.mco;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiScreenClientOutdated extends GuiScreen
{
    private final GuiScreen field_146901_a;
    private static final String __OBFID = "CL_00000772";

    public GuiScreenClientOutdated(GuiScreen par1GuiScreen)
    {
        this.field_146901_a = par1GuiScreen;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.field_146292_n.clear();
        this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 120 + 12, "Back"));
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.func_146276_q_();
        String var4 = I18n.getStringParams("mco.client.outdated.title", new Object[0]);
        String var5 = I18n.getStringParams("mco.client.outdated.msg", new Object[0]);
        this.drawCenteredString(this.field_146289_q, var4, this.field_146294_l / 2, this.field_146295_m / 2 - 50, 16711680);
        this.drawCenteredString(this.field_146289_q, var5, this.field_146294_l / 2, this.field_146295_m / 2 - 30, 16777215);
        super.drawScreen(par1, par2, par3);
    }

    protected void func_146284_a(GuiButton p_146284_1_)
    {
        if (p_146284_1_.field_146127_k == 0)
        {
            this.field_146297_k.func_147108_a(this.field_146901_a);
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        if (par2 == 28 || par2 == 156)
        {
            this.field_146297_k.func_147108_a(this.field_146901_a);
        }
    }
}
