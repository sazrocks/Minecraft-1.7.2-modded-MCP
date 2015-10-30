package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class GuiControls extends GuiScreen
{
    private static final GameSettings.Options[] field_146492_g = new GameSettings.Options[] {GameSettings.Options.INVERT_MOUSE, GameSettings.Options.SENSITIVITY, GameSettings.Options.TOUCHSCREEN};
    private GuiScreen field_146496_h;
    protected String field_146495_a = "Controls";
    private GameSettings field_146497_i;
    public KeyBinding field_146491_f = null;
    private GuiKeyBindingList field_146494_r;
    private GuiButton field_146493_s;
    private static final String __OBFID = "CL_00000736";

    public GuiControls(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
    {
        this.field_146496_h = par1GuiScreen;
        this.field_146497_i = par2GameSettings;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.field_146494_r = new GuiKeyBindingList(this, this.field_146297_k);
        this.field_146292_n.add(new GuiButton(200, this.field_146294_l / 2 - 155, this.field_146295_m - 29, 150, 20, I18n.getStringParams("gui.done", new Object[0])));
        this.field_146292_n.add(this.field_146493_s = new GuiButton(201, this.field_146294_l / 2 - 155 + 160, this.field_146295_m - 29, 150, 20, I18n.getStringParams("controls.resetAll", new Object[0])));
        this.field_146495_a = I18n.getStringParams("controls.title", new Object[0]);
        int var1 = 0;
        GameSettings.Options[] var2 = field_146492_g;
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            GameSettings.Options var5 = var2[var4];

            if (var5.getEnumFloat())
            {
                this.field_146292_n.add(new GuiOptionSlider(var5.returnEnumOrdinal(), this.field_146294_l / 2 - 155 + var1 % 2 * 160, 18 + 24 * (var1 >> 1), var5));
            }
            else
            {
                this.field_146292_n.add(new GuiOptionButton(var5.returnEnumOrdinal(), this.field_146294_l / 2 - 155 + var1 % 2 * 160, 18 + 24 * (var1 >> 1), var5, this.field_146497_i.getKeyBinding(var5)));
            }

            ++var1;
        }
    }

    protected void func_146284_a(GuiButton p_146284_1_)
    {
        if (p_146284_1_.field_146127_k == 200)
        {
            this.field_146297_k.func_147108_a(this.field_146496_h);
        }
        else if (p_146284_1_.field_146127_k == 201)
        {
            KeyBinding[] var2 = this.field_146297_k.gameSettings.keyBindings;
            int var3 = var2.length;

            for (int var4 = 0; var4 < var3; ++var4)
            {
                KeyBinding var5 = var2[var4];
                var5.func_151462_b(var5.func_151469_h());
            }

            KeyBinding.resetKeyBindingArrayAndHash();
        }
        else if (p_146284_1_.field_146127_k < 100 && p_146284_1_ instanceof GuiOptionButton)
        {
            this.field_146497_i.setOptionValue(((GuiOptionButton)p_146284_1_).func_146136_c(), 1);
            p_146284_1_.field_146126_j = this.field_146497_i.getKeyBinding(GameSettings.Options.getEnumOptions(p_146284_1_.field_146127_k));
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        if (this.field_146491_f != null)
        {
            this.field_146497_i.func_151440_a(this.field_146491_f, -100 + par3);
            this.field_146491_f = null;
            KeyBinding.resetKeyBindingArrayAndHash();
        }
        else if (par3 != 0 || !this.field_146494_r.func_148179_a(par1, par2, par3))
        {
            super.mouseClicked(par1, par2, par3);
        }
    }

    protected void func_146286_b(int p_146286_1_, int p_146286_2_, int p_146286_3_)
    {
        if (p_146286_3_ != 0 || !this.field_146494_r.func_148181_b(p_146286_1_, p_146286_2_, p_146286_3_))
        {
            super.func_146286_b(p_146286_1_, p_146286_2_, p_146286_3_);
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        if (this.field_146491_f != null)
        {
            if (par2 == 1)
            {
                this.field_146497_i.func_151440_a(this.field_146491_f, 0);
            }
            else
            {
                this.field_146497_i.func_151440_a(this.field_146491_f, par2);
            }

            this.field_146491_f = null;
            KeyBinding.resetKeyBindingArrayAndHash();
        }
        else
        {
            super.keyTyped(par1, par2);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.func_146276_q_();
        this.field_146494_r.func_148128_a(par1, par2, par3);
        this.drawCenteredString(this.field_146289_q, this.field_146495_a, this.field_146294_l / 2, 8, 16777215);
        boolean var4 = true;
        KeyBinding[] var5 = this.field_146497_i.keyBindings;
        int var6 = var5.length;

        for (int var7 = 0; var7 < var6; ++var7)
        {
            KeyBinding var8 = var5[var7];

            if (var8.func_151463_i() != var8.func_151469_h())
            {
                var4 = false;
                break;
            }
        }

        this.field_146493_s.field_146124_l = !var4;
        super.drawScreen(par1, par2, par3);
    }
}
