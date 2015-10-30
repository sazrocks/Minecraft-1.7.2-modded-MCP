package net.minecraft.client.gui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveFormatComparator;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiSelectWorld extends GuiScreen
{
    private static final Logger field_146629_g = LogManager.getLogger();
    private final DateFormat field_146633_h = new SimpleDateFormat();
    protected GuiScreen field_146632_a;
    protected String field_146628_f = "Select world";
    private boolean field_146634_i;
    private int field_146640_r;
    private java.util.List field_146639_s;
    private GuiSelectWorld.List field_146638_t;
    private String field_146637_u;
    private String field_146636_v;
    private String[] field_146635_w = new String[3];
    private boolean field_146643_x;
    private GuiButton field_146642_y;
    private GuiButton field_146641_z;
    private GuiButton field_146630_A;
    private GuiButton field_146631_B;
    private static final String __OBFID = "CL_00000711";

    public GuiSelectWorld(GuiScreen par1GuiScreen)
    {
        this.field_146632_a = par1GuiScreen;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.field_146628_f = I18n.getStringParams("selectWorld.title", new Object[0]);

        try
        {
            this.func_146627_h();
        }
        catch (AnvilConverterException var2)
        {
            field_146629_g.error("Couldn\'t load level list", var2);
            this.field_146297_k.func_147108_a(new GuiErrorScreen("Unable to load worlds", var2.getMessage()));
            return;
        }

        this.field_146637_u = I18n.getStringParams("selectWorld.world", new Object[0]);
        this.field_146636_v = I18n.getStringParams("selectWorld.conversion", new Object[0]);
        this.field_146635_w[WorldSettings.GameType.SURVIVAL.getID()] = I18n.getStringParams("gameMode.survival", new Object[0]);
        this.field_146635_w[WorldSettings.GameType.CREATIVE.getID()] = I18n.getStringParams("gameMode.creative", new Object[0]);
        this.field_146635_w[WorldSettings.GameType.ADVENTURE.getID()] = I18n.getStringParams("gameMode.adventure", new Object[0]);
        this.field_146638_t = new GuiSelectWorld.List();
        this.field_146638_t.func_148134_d(4, 5);
        this.func_146618_g();
    }

    private void func_146627_h() throws AnvilConverterException
    {
        ISaveFormat var1 = this.field_146297_k.getSaveLoader();
        this.field_146639_s = var1.getSaveList();
        Collections.sort(this.field_146639_s);
        this.field_146640_r = -1;
    }

    protected String func_146621_a(int p_146621_1_)
    {
        return ((SaveFormatComparator)this.field_146639_s.get(p_146621_1_)).getFileName();
    }

    protected String func_146614_d(int p_146614_1_)
    {
        String var2 = ((SaveFormatComparator)this.field_146639_s.get(p_146614_1_)).getDisplayName();

        if (var2 == null || MathHelper.stringNullOrLengthZero(var2))
        {
            var2 = I18n.getStringParams("selectWorld.world", new Object[0]) + " " + (p_146614_1_ + 1);
        }

        return var2;
    }

    public void func_146618_g()
    {
        this.field_146292_n.add(this.field_146641_z = new GuiButton(1, this.field_146294_l / 2 - 154, this.field_146295_m - 52, 150, 20, I18n.getStringParams("selectWorld.select", new Object[0])));
        this.field_146292_n.add(new GuiButton(3, this.field_146294_l / 2 + 4, this.field_146295_m - 52, 150, 20, I18n.getStringParams("selectWorld.create", new Object[0])));
        this.field_146292_n.add(this.field_146630_A = new GuiButton(6, this.field_146294_l / 2 - 154, this.field_146295_m - 28, 72, 20, I18n.getStringParams("selectWorld.rename", new Object[0])));
        this.field_146292_n.add(this.field_146642_y = new GuiButton(2, this.field_146294_l / 2 - 76, this.field_146295_m - 28, 72, 20, I18n.getStringParams("selectWorld.delete", new Object[0])));
        this.field_146292_n.add(this.field_146631_B = new GuiButton(7, this.field_146294_l / 2 + 4, this.field_146295_m - 28, 72, 20, I18n.getStringParams("selectWorld.recreate", new Object[0])));
        this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 + 82, this.field_146295_m - 28, 72, 20, I18n.getStringParams("gui.cancel", new Object[0])));
        this.field_146641_z.field_146124_l = false;
        this.field_146642_y.field_146124_l = false;
        this.field_146630_A.field_146124_l = false;
        this.field_146631_B.field_146124_l = false;
    }

    protected void func_146284_a(GuiButton p_146284_1_)
    {
        if (p_146284_1_.field_146124_l)
        {
            if (p_146284_1_.field_146127_k == 2)
            {
                String var2 = this.func_146614_d(this.field_146640_r);

                if (var2 != null)
                {
                    this.field_146643_x = true;
                    GuiYesNo var3 = func_146623_a(this, var2, this.field_146640_r);
                    this.field_146297_k.func_147108_a(var3);
                }
            }
            else if (p_146284_1_.field_146127_k == 1)
            {
                this.func_146615_e(this.field_146640_r);
            }
            else if (p_146284_1_.field_146127_k == 3)
            {
                this.field_146297_k.func_147108_a(new GuiCreateWorld(this));
            }
            else if (p_146284_1_.field_146127_k == 6)
            {
                this.field_146297_k.func_147108_a(new GuiRenameWorld(this, this.func_146621_a(this.field_146640_r)));
            }
            else if (p_146284_1_.field_146127_k == 0)
            {
                this.field_146297_k.func_147108_a(this.field_146632_a);
            }
            else if (p_146284_1_.field_146127_k == 7)
            {
                GuiCreateWorld var5 = new GuiCreateWorld(this);
                ISaveHandler var6 = this.field_146297_k.getSaveLoader().getSaveLoader(this.func_146621_a(this.field_146640_r), false);
                WorldInfo var4 = var6.loadWorldInfo();
                var6.flush();
                var5.func_146318_a(var4);
                this.field_146297_k.func_147108_a(var5);
            }
            else
            {
                this.field_146638_t.func_148147_a(p_146284_1_);
            }
        }
    }

    public void func_146615_e(int p_146615_1_)
    {
        this.field_146297_k.func_147108_a((GuiScreen)null);

        if (!this.field_146634_i)
        {
            this.field_146634_i = true;
            String var2 = this.func_146621_a(p_146615_1_);

            if (var2 == null)
            {
                var2 = "World" + p_146615_1_;
            }

            String var3 = this.func_146614_d(p_146615_1_);

            if (var3 == null)
            {
                var3 = "World" + p_146615_1_;
            }

            if (this.field_146297_k.getSaveLoader().canLoadWorld(var2))
            {
                this.field_146297_k.launchIntegratedServer(var2, var3, (WorldSettings)null);
            }
        }
    }

    public void confirmClicked(boolean par1, int par2)
    {
        if (this.field_146643_x)
        {
            this.field_146643_x = false;

            if (par1)
            {
                ISaveFormat var3 = this.field_146297_k.getSaveLoader();
                var3.flushCache();
                var3.deleteWorldDirectory(this.func_146621_a(par2));

                try
                {
                    this.func_146627_h();
                }
                catch (AnvilConverterException var5)
                {
                    field_146629_g.error("Couldn\'t load level list", var5);
                }
            }

            this.field_146297_k.func_147108_a(this);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.field_146638_t.func_148128_a(par1, par2, par3);
        this.drawCenteredString(this.field_146289_q, this.field_146628_f, this.field_146294_l / 2, 20, 16777215);
        super.drawScreen(par1, par2, par3);
    }

    public static GuiYesNo func_146623_a(GuiScreen p_146623_0_, String p_146623_1_, int p_146623_2_)
    {
        String var3 = I18n.getStringParams("selectWorld.deleteQuestion", new Object[0]);
        String var4 = "\'" + p_146623_1_ + "\' " + I18n.getStringParams("selectWorld.deleteWarning", new Object[0]);
        String var5 = I18n.getStringParams("selectWorld.deleteButton", new Object[0]);
        String var6 = I18n.getStringParams("gui.cancel", new Object[0]);
        GuiYesNo var7 = new GuiYesNo(p_146623_0_, var3, var4, var5, var6, p_146623_2_);
        return var7;
    }

    class List extends GuiSlot
    {
        private static final String __OBFID = "CL_00000712";

        public List()
        {
            super(GuiSelectWorld.this.field_146297_k, GuiSelectWorld.this.field_146294_l, GuiSelectWorld.this.field_146295_m, 32, GuiSelectWorld.this.field_146295_m - 64, 36);
        }

        protected int func_148127_b()
        {
            return GuiSelectWorld.this.field_146639_s.size();
        }

        protected void func_148144_a(int p_148144_1_, boolean p_148144_2_, int p_148144_3_, int p_148144_4_)
        {
            GuiSelectWorld.this.field_146640_r = p_148144_1_;
            boolean var5 = GuiSelectWorld.this.field_146640_r >= 0 && GuiSelectWorld.this.field_146640_r < this.func_148127_b();
            GuiSelectWorld.this.field_146641_z.field_146124_l = var5;
            GuiSelectWorld.this.field_146642_y.field_146124_l = var5;
            GuiSelectWorld.this.field_146630_A.field_146124_l = var5;
            GuiSelectWorld.this.field_146631_B.field_146124_l = var5;

            if (p_148144_2_ && var5)
            {
                GuiSelectWorld.this.func_146615_e(p_148144_1_);
            }
        }

        protected boolean func_148131_a(int p_148131_1_)
        {
            return p_148131_1_ == GuiSelectWorld.this.field_146640_r;
        }

        protected int func_148138_e()
        {
            return GuiSelectWorld.this.field_146639_s.size() * 36;
        }

        protected void func_148123_a()
        {
            GuiSelectWorld.this.func_146276_q_();
        }

        protected void func_148126_a(int p_148126_1_, int p_148126_2_, int p_148126_3_, int p_148126_4_, Tessellator p_148126_5_, int p_148126_6_, int p_148126_7_)
        {
            SaveFormatComparator var8 = (SaveFormatComparator)GuiSelectWorld.this.field_146639_s.get(p_148126_1_);
            String var9 = var8.getDisplayName();

            if (var9 == null || MathHelper.stringNullOrLengthZero(var9))
            {
                var9 = GuiSelectWorld.this.field_146637_u + " " + (p_148126_1_ + 1);
            }

            String var10 = var8.getFileName();
            var10 = var10 + " (" + GuiSelectWorld.this.field_146633_h.format(new Date(var8.getLastTimePlayed()));
            var10 = var10 + ")";
            String var11 = "";

            if (var8.requiresConversion())
            {
                var11 = GuiSelectWorld.this.field_146636_v + " " + var11;
            }
            else
            {
                var11 = GuiSelectWorld.this.field_146635_w[var8.getEnumGameType().getID()];

                if (var8.isHardcoreModeEnabled())
                {
                    var11 = EnumChatFormatting.DARK_RED + I18n.getStringParams("gameMode.hardcore", new Object[0]) + EnumChatFormatting.RESET;
                }

                if (var8.getCheatsEnabled())
                {
                    var11 = var11 + ", " + I18n.getStringParams("selectWorld.cheats", new Object[0]);
                }
            }

            GuiSelectWorld.this.drawString(GuiSelectWorld.this.field_146289_q, var9, p_148126_2_ + 2, p_148126_3_ + 1, 16777215);
            GuiSelectWorld.this.drawString(GuiSelectWorld.this.field_146289_q, var10, p_148126_2_ + 2, p_148126_3_ + 12, 8421504);
            GuiSelectWorld.this.drawString(GuiSelectWorld.this.field_146289_q, var11, p_148126_2_ + 2, p_148126_3_ + 12 + 10, 8421504);
        }
    }
}
