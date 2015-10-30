package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class GuiVideoSettings extends GuiScreen
{
    private GuiScreen field_146498_f;
    protected String field_146500_a = "Video Settings";
    private GameSettings field_146499_g;
    private GuiListExtended field_146501_h;
    private static final GameSettings.Options[] field_146502_i = new GameSettings.Options[] {GameSettings.Options.GRAPHICS, GameSettings.Options.RENDER_DISTANCE, GameSettings.Options.AMBIENT_OCCLUSION, GameSettings.Options.FRAMERATE_LIMIT, GameSettings.Options.ANAGLYPH, GameSettings.Options.VIEW_BOBBING, GameSettings.Options.GUI_SCALE, GameSettings.Options.ADVANCED_OPENGL, GameSettings.Options.GAMMA, GameSettings.Options.RENDER_CLOUDS, GameSettings.Options.PARTICLES, GameSettings.Options.USE_SERVER_TEXTURES, GameSettings.Options.USE_FULLSCREEN, GameSettings.Options.ENABLE_VSYNC, GameSettings.Options.MIPMAP_LEVELS, GameSettings.Options.ANISOTROPIC_FILTERING};
    private static final String __OBFID = "CL_00000718";

    public GuiVideoSettings(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
    {
        this.field_146498_f = par1GuiScreen;
        this.field_146499_g = par2GameSettings;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.field_146500_a = I18n.getStringParams("options.videoTitle", new Object[0]);
        this.field_146292_n.clear();
        this.field_146292_n.add(new GuiButton(200, this.field_146294_l / 2 - 100, this.field_146295_m - 27, I18n.getStringParams("gui.done", new Object[0])));
        this.field_146501_h = new GuiOptionsRowList(this.field_146297_k, this.field_146294_l, this.field_146295_m, 32, this.field_146295_m - 32, 25, field_146502_i);
    }

    protected void func_146284_a(GuiButton p_146284_1_)
    {
        if (p_146284_1_.field_146124_l)
        {
            if (p_146284_1_.field_146127_k == 200)
            {
                this.field_146297_k.gameSettings.saveOptions();
                this.field_146297_k.func_147108_a(this.field_146498_f);
            }
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        int var4 = this.field_146499_g.guiScale;
        super.mouseClicked(par1, par2, par3);
        this.field_146501_h.func_148179_a(par1, par2, par3);

        if (this.field_146499_g.guiScale != var4)
        {
            ScaledResolution var5 = new ScaledResolution(this.field_146297_k.gameSettings, this.field_146297_k.displayWidth, this.field_146297_k.displayHeight);
            int var6 = var5.getScaledWidth();
            int var7 = var5.getScaledHeight();
            this.func_146280_a(this.field_146297_k, var6, var7);
        }
    }

    protected void func_146286_b(int p_146286_1_, int p_146286_2_, int p_146286_3_)
    {
        int var4 = this.field_146499_g.guiScale;
        super.func_146286_b(p_146286_1_, p_146286_2_, p_146286_3_);
        this.field_146501_h.func_148181_b(p_146286_1_, p_146286_2_, p_146286_3_);

        if (this.field_146499_g.guiScale != var4)
        {
            ScaledResolution var5 = new ScaledResolution(this.field_146297_k.gameSettings, this.field_146297_k.displayWidth, this.field_146297_k.displayHeight);
            int var6 = var5.getScaledWidth();
            int var7 = var5.getScaledHeight();
            this.func_146280_a(this.field_146297_k, var6, var7);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.func_146276_q_();
        this.field_146501_h.func_148128_a(par1, par2, par3);
        this.drawCenteredString(this.field_146289_q, this.field_146500_a, this.field_146294_l / 2, 5, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
