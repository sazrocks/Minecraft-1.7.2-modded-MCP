package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class ScreenChatOptions extends GuiScreen
{
    private static final GameSettings.Options[] field_146399_a = new GameSettings.Options[] {GameSettings.Options.CHAT_VISIBILITY, GameSettings.Options.CHAT_COLOR, GameSettings.Options.CHAT_LINKS, GameSettings.Options.CHAT_OPACITY, GameSettings.Options.CHAT_LINKS_PROMPT, GameSettings.Options.CHAT_SCALE, GameSettings.Options.CHAT_HEIGHT_FOCUSED, GameSettings.Options.CHAT_HEIGHT_UNFOCUSED, GameSettings.Options.CHAT_WIDTH};
    private static final GameSettings.Options[] field_146395_f = new GameSettings.Options[] {GameSettings.Options.SHOW_CAPE};
    private final GuiScreen field_146396_g;
    private final GameSettings field_146400_h;
    private String field_146401_i;
    private String field_146398_r;
    private int field_146397_s;
    private static final String __OBFID = "CL_00000681";

    public ScreenChatOptions(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
    {
        this.field_146396_g = par1GuiScreen;
        this.field_146400_h = par2GameSettings;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        int var1 = 0;
        this.field_146401_i = I18n.getStringParams("options.chat.title", new Object[0]);
        this.field_146398_r = I18n.getStringParams("options.multiplayer.title", new Object[0]);
        GameSettings.Options[] var2 = field_146399_a;
        int var3 = var2.length;
        int var4;
        GameSettings.Options var5;

        for (var4 = 0; var4 < var3; ++var4)
        {
            var5 = var2[var4];

            if (var5.getEnumFloat())
            {
                this.field_146292_n.add(new GuiOptionSlider(var5.returnEnumOrdinal(), this.field_146294_l / 2 - 155 + var1 % 2 * 160, this.field_146295_m / 6 + 24 * (var1 >> 1), var5));
            }
            else
            {
                this.field_146292_n.add(new GuiOptionButton(var5.returnEnumOrdinal(), this.field_146294_l / 2 - 155 + var1 % 2 * 160, this.field_146295_m / 6 + 24 * (var1 >> 1), var5, this.field_146400_h.getKeyBinding(var5)));
            }

            ++var1;
        }

        if (var1 % 2 == 1)
        {
            ++var1;
        }

        this.field_146397_s = this.field_146295_m / 6 + 24 * (var1 >> 1);
        var1 += 2;
        var2 = field_146395_f;
        var3 = var2.length;

        for (var4 = 0; var4 < var3; ++var4)
        {
            var5 = var2[var4];

            if (var5.getEnumFloat())
            {
                this.field_146292_n.add(new GuiOptionSlider(var5.returnEnumOrdinal(), this.field_146294_l / 2 - 155 + var1 % 2 * 160, this.field_146295_m / 6 + 24 * (var1 >> 1), var5));
            }
            else
            {
                this.field_146292_n.add(new GuiOptionButton(var5.returnEnumOrdinal(), this.field_146294_l / 2 - 155 + var1 % 2 * 160, this.field_146295_m / 6 + 24 * (var1 >> 1), var5, this.field_146400_h.getKeyBinding(var5)));
            }

            ++var1;
        }

        this.field_146292_n.add(new GuiButton(200, this.field_146294_l / 2 - 100, this.field_146295_m / 6 + 168, I18n.getStringParams("gui.done", new Object[0])));
    }

    protected void func_146284_a(GuiButton p_146284_1_)
    {
        if (p_146284_1_.field_146124_l)
        {
            if (p_146284_1_.field_146127_k < 100 && p_146284_1_ instanceof GuiOptionButton)
            {
                this.field_146400_h.setOptionValue(((GuiOptionButton)p_146284_1_).func_146136_c(), 1);
                p_146284_1_.field_146126_j = this.field_146400_h.getKeyBinding(GameSettings.Options.getEnumOptions(p_146284_1_.field_146127_k));
            }

            if (p_146284_1_.field_146127_k == 200)
            {
                this.field_146297_k.gameSettings.saveOptions();
                this.field_146297_k.func_147108_a(this.field_146396_g);
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.func_146276_q_();
        this.drawCenteredString(this.field_146289_q, this.field_146401_i, this.field_146294_l / 2, 20, 16777215);
        this.drawCenteredString(this.field_146289_q, this.field_146398_r, this.field_146294_l / 2, this.field_146397_s + 7, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
