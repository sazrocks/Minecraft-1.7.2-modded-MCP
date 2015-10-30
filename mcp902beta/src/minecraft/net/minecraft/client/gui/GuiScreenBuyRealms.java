package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiScreenBuyRealms extends GuiScreen
{
    private static final Logger field_146819_a = LogManager.getLogger();
    private GuiScreen field_146817_f;
    private static int field_146818_g = 111;
    private volatile String field_146820_h = "";
    private static final String __OBFID = "CL_00000770";

    public GuiScreenBuyRealms(GuiScreen p_i45035_1_)
    {
        this.field_146817_f = p_i45035_1_;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen() {}

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.field_146292_n.clear();
        short var1 = 212;
        this.field_146292_n.add(new GuiButton(field_146818_g, this.field_146294_l / 2 - var1 / 2, 180, var1, 20, I18n.getStringParams("gui.back", new Object[0])));
        this.func_146816_h();
    }

    private void func_146816_h()
    {
        Session var1 = this.field_146297_k.getSession();
        final McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
        (new Thread()
        {
            private static final String __OBFID = "CL_00000771";
            public void run()
            {
                try
                {
                    GuiScreenBuyRealms.this.field_146820_h = var2.func_148690_i();
                }
                catch (ExceptionMcoService var2x)
                {
                    GuiScreenBuyRealms.field_146819_a.error("Could not get stat");
                }
            }
        }).start();
    }

    public void func_146281_b()
    {
        Keyboard.enableRepeatEvents(false);
    }

    protected void func_146284_a(GuiButton p_146284_1_)
    {
        if (p_146284_1_.field_146124_l)
        {
            if (p_146284_1_.field_146127_k == field_146818_g)
            {
                this.field_146297_k.func_147108_a(this.field_146817_f);
            }
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2) {}

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.func_146276_q_();
        this.drawCenteredString(this.field_146289_q, I18n.getStringParams("mco.buy.realms.title", new Object[0]), this.field_146294_l / 2, 11, 16777215);
        String[] var4 = this.field_146820_h.split("\n");
        int var5 = 52;
        String[] var6 = var4;
        int var7 = var4.length;

        for (int var8 = 0; var8 < var7; ++var8)
        {
            String var9 = var6[var8];
            this.drawCenteredString(this.field_146289_q, var9, this.field_146294_l / 2, var5, 10526880);
            var5 += 18;
        }

        super.drawScreen(par1, par2, par3);
    }
}
