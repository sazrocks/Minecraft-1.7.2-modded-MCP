package net.minecraft.client.gui;

import java.net.URI;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

public class GuiScreenDemo extends GuiScreen
{
    private static final Logger field_146349_a = LogManager.getLogger();
    private static final ResourceLocation field_146348_f = new ResourceLocation("textures/gui/demo_background.png");
    private static final String __OBFID = "CL_00000691";

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.field_146292_n.clear();
        byte var1 = -16;
        this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 - 116, this.field_146295_m / 2 + 62 + var1, 114, 20, I18n.getStringParams("demo.help.buy", new Object[0])));
        this.field_146292_n.add(new GuiButton(2, this.field_146294_l / 2 + 2, this.field_146295_m / 2 + 62 + var1, 114, 20, I18n.getStringParams("demo.help.later", new Object[0])));
    }

    protected void func_146284_a(GuiButton p_146284_1_)
    {
        switch (p_146284_1_.field_146127_k)
        {
            case 1:
                p_146284_1_.field_146124_l = false;

                try
                {
                    Class var2 = Class.forName("java.awt.Desktop");
                    Object var3 = var2.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                    var2.getMethod("browse", new Class[] {URI.class}).invoke(var3, new Object[] {new URI("http://www.minecraft.net/store?source=demo")});
                }
                catch (Throwable var4)
                {
                    field_146349_a.error("Couldn\'t open link", var4);
                }

                break;

            case 2:
                this.field_146297_k.func_147108_a((GuiScreen)null);
                this.field_146297_k.setIngameFocus();
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
    }

    public void func_146276_q_()
    {
        super.func_146276_q_();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.field_146297_k.getTextureManager().bindTexture(field_146348_f);
        int var1 = (this.field_146294_l - 248) / 2;
        int var2 = (this.field_146295_m - 166) / 2;
        this.drawTexturedModalRect(var1, var2, 0, 0, 248, 166);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.func_146276_q_();
        int var4 = (this.field_146294_l - 248) / 2 + 10;
        int var5 = (this.field_146295_m - 166) / 2 + 8;
        this.field_146289_q.drawString(I18n.getStringParams("demo.help.title", new Object[0]), var4, var5, 2039583);
        var5 += 12;
        GameSettings var6 = this.field_146297_k.gameSettings;
        this.field_146289_q.drawString(I18n.getStringParams("demo.help.movementShort", new Object[] {GameSettings.getKeyDisplayString(var6.keyBindForward.func_151463_i()), GameSettings.getKeyDisplayString(var6.keyBindLeft.func_151463_i()), GameSettings.getKeyDisplayString(var6.keyBindBack.func_151463_i()), GameSettings.getKeyDisplayString(var6.keyBindRight.func_151463_i())}), var4, var5, 5197647);
        this.field_146289_q.drawString(I18n.getStringParams("demo.help.movementMouse", new Object[0]), var4, var5 + 12, 5197647);
        this.field_146289_q.drawString(I18n.getStringParams("demo.help.jump", new Object[] {GameSettings.getKeyDisplayString(var6.keyBindJump.func_151463_i())}), var4, var5 + 24, 5197647);
        this.field_146289_q.drawString(I18n.getStringParams("demo.help.inventory", new Object[] {GameSettings.getKeyDisplayString(var6.field_151445_Q.func_151463_i())}), var4, var5 + 36, 5197647);
        this.field_146289_q.drawSplitString(I18n.getStringParams("demo.help.fullWrapped", new Object[0]), var4, var5 + 68, 218, 2039583);
        super.drawScreen(par1, par2, par3);
    }
}
