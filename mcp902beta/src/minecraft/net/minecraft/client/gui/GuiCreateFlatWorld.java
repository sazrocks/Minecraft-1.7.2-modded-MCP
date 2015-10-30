package net.minecraft.client.gui;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiCreateFlatWorld extends GuiScreen
{
    private static RenderItem field_146392_a = new RenderItem();
    private final GuiCreateWorld field_146385_f;
    private FlatGeneratorInfo field_146387_g = FlatGeneratorInfo.getDefaultFlatGenerator();
    private String field_146393_h;
    private String field_146394_i;
    private String field_146391_r;
    private GuiCreateFlatWorld.Details field_146390_s;
    private GuiButton field_146389_t;
    private GuiButton field_146388_u;
    private GuiButton field_146386_v;
    private static final String __OBFID = "CL_00000687";

    public GuiCreateFlatWorld(GuiCreateWorld par1GuiCreateWorld, String par2Str)
    {
        this.field_146385_f = par1GuiCreateWorld;
        this.func_146383_a(par2Str);
    }

    public String func_146384_e()
    {
        return this.field_146387_g.toString();
    }

    public void func_146383_a(String p_146383_1_)
    {
        this.field_146387_g = FlatGeneratorInfo.createFlatGeneratorFromString(p_146383_1_);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.field_146292_n.clear();
        this.field_146393_h = I18n.getStringParams("createWorld.customize.flat.title", new Object[0]);
        this.field_146394_i = I18n.getStringParams("createWorld.customize.flat.tile", new Object[0]);
        this.field_146391_r = I18n.getStringParams("createWorld.customize.flat.height", new Object[0]);
        this.field_146390_s = new GuiCreateFlatWorld.Details();
        this.field_146292_n.add(this.field_146389_t = new GuiButton(2, this.field_146294_l / 2 - 154, this.field_146295_m - 52, 100, 20, I18n.getStringParams("createWorld.customize.flat.addLayer", new Object[0]) + " (NYI)"));
        this.field_146292_n.add(this.field_146388_u = new GuiButton(3, this.field_146294_l / 2 - 50, this.field_146295_m - 52, 100, 20, I18n.getStringParams("createWorld.customize.flat.editLayer", new Object[0]) + " (NYI)"));
        this.field_146292_n.add(this.field_146386_v = new GuiButton(4, this.field_146294_l / 2 - 155, this.field_146295_m - 52, 150, 20, I18n.getStringParams("createWorld.customize.flat.removeLayer", new Object[0])));
        this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 155, this.field_146295_m - 28, 150, 20, I18n.getStringParams("gui.done", new Object[0])));
        this.field_146292_n.add(new GuiButton(5, this.field_146294_l / 2 + 5, this.field_146295_m - 52, 150, 20, I18n.getStringParams("createWorld.customize.presets", new Object[0])));
        this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 + 5, this.field_146295_m - 28, 150, 20, I18n.getStringParams("gui.cancel", new Object[0])));
        this.field_146389_t.field_146125_m = this.field_146388_u.field_146125_m = false;
        this.field_146387_g.func_82645_d();
        this.func_146375_g();
    }

    protected void func_146284_a(GuiButton p_146284_1_)
    {
        int var2 = this.field_146387_g.getFlatLayers().size() - this.field_146390_s.field_148228_k - 1;

        if (p_146284_1_.field_146127_k == 1)
        {
            this.field_146297_k.func_147108_a(this.field_146385_f);
        }
        else if (p_146284_1_.field_146127_k == 0)
        {
            this.field_146385_f.field_146334_a = this.func_146384_e();
            this.field_146297_k.func_147108_a(this.field_146385_f);
        }
        else if (p_146284_1_.field_146127_k == 5)
        {
            this.field_146297_k.func_147108_a(new GuiFlatPresets(this));
        }
        else if (p_146284_1_.field_146127_k == 4 && this.func_146382_i())
        {
            this.field_146387_g.getFlatLayers().remove(var2);
            this.field_146390_s.field_148228_k = Math.min(this.field_146390_s.field_148228_k, this.field_146387_g.getFlatLayers().size() - 1);
        }

        this.field_146387_g.func_82645_d();
        this.func_146375_g();
    }

    public void func_146375_g()
    {
        boolean var1 = this.func_146382_i();
        this.field_146386_v.field_146124_l = var1;
        this.field_146388_u.field_146124_l = var1;
        this.field_146388_u.field_146124_l = false;
        this.field_146389_t.field_146124_l = false;
    }

    private boolean func_146382_i()
    {
        return this.field_146390_s.field_148228_k > -1 && this.field_146390_s.field_148228_k < this.field_146387_g.getFlatLayers().size();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.func_146276_q_();
        this.field_146390_s.func_148128_a(par1, par2, par3);
        this.drawCenteredString(this.field_146289_q, this.field_146393_h, this.field_146294_l / 2, 8, 16777215);
        int var4 = this.field_146294_l / 2 - 92 - 16;
        this.drawString(this.field_146289_q, this.field_146394_i, var4, 32, 16777215);
        this.drawString(this.field_146289_q, this.field_146391_r, var4 + 2 + 213 - this.field_146289_q.getStringWidth(this.field_146391_r), 32, 16777215);
        super.drawScreen(par1, par2, par3);
    }

    class Details extends GuiSlot
    {
        public int field_148228_k = -1;
        private static final String __OBFID = "CL_00000688";

        public Details()
        {
            super(GuiCreateFlatWorld.this.field_146297_k, GuiCreateFlatWorld.this.field_146294_l, GuiCreateFlatWorld.this.field_146295_m, 43, GuiCreateFlatWorld.this.field_146295_m - 60, 24);
        }

        private void func_148225_a(int p_148225_1_, int p_148225_2_, ItemStack p_148225_3_)
        {
            this.func_148226_e(p_148225_1_ + 1, p_148225_2_ + 1);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);

            if (p_148225_3_ != null)
            {
                RenderHelper.enableGUIStandardItemLighting();
                GuiCreateFlatWorld.field_146392_a.renderItemIntoGUI(GuiCreateFlatWorld.this.field_146289_q, GuiCreateFlatWorld.this.field_146297_k.getTextureManager(), p_148225_3_, p_148225_1_ + 2, p_148225_2_ + 2);
                RenderHelper.disableStandardItemLighting();
            }

            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }

        private void func_148226_e(int p_148226_1_, int p_148226_2_)
        {
            this.func_148224_c(p_148226_1_, p_148226_2_, 0, 0);
        }

        private void func_148224_c(int p_148224_1_, int p_148224_2_, int p_148224_3_, int p_148224_4_)
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GuiCreateFlatWorld.this.field_146297_k.getTextureManager().bindTexture(Gui.statIcons);
            float var5 = 0.0078125F;
            float var6 = 0.0078125F;
            boolean var7 = true;
            boolean var8 = true;
            Tessellator var9 = Tessellator.instance;
            var9.startDrawingQuads();
            var9.addVertexWithUV((double)(p_148224_1_ + 0), (double)(p_148224_2_ + 18), (double)GuiCreateFlatWorld.this.zLevel, (double)((float)(p_148224_3_ + 0) * 0.0078125F), (double)((float)(p_148224_4_ + 18) * 0.0078125F));
            var9.addVertexWithUV((double)(p_148224_1_ + 18), (double)(p_148224_2_ + 18), (double)GuiCreateFlatWorld.this.zLevel, (double)((float)(p_148224_3_ + 18) * 0.0078125F), (double)((float)(p_148224_4_ + 18) * 0.0078125F));
            var9.addVertexWithUV((double)(p_148224_1_ + 18), (double)(p_148224_2_ + 0), (double)GuiCreateFlatWorld.this.zLevel, (double)((float)(p_148224_3_ + 18) * 0.0078125F), (double)((float)(p_148224_4_ + 0) * 0.0078125F));
            var9.addVertexWithUV((double)(p_148224_1_ + 0), (double)(p_148224_2_ + 0), (double)GuiCreateFlatWorld.this.zLevel, (double)((float)(p_148224_3_ + 0) * 0.0078125F), (double)((float)(p_148224_4_ + 0) * 0.0078125F));
            var9.draw();
        }

        protected int func_148127_b()
        {
            return GuiCreateFlatWorld.this.field_146387_g.getFlatLayers().size();
        }

        protected void func_148144_a(int p_148144_1_, boolean p_148144_2_, int p_148144_3_, int p_148144_4_)
        {
            this.field_148228_k = p_148144_1_;
            GuiCreateFlatWorld.this.func_146375_g();
        }

        protected boolean func_148131_a(int p_148131_1_)
        {
            return p_148131_1_ == this.field_148228_k;
        }

        protected void func_148123_a() {}

        protected void func_148126_a(int p_148126_1_, int p_148126_2_, int p_148126_3_, int p_148126_4_, Tessellator p_148126_5_, int p_148126_6_, int p_148126_7_)
        {
            FlatLayerInfo var8 = (FlatLayerInfo)GuiCreateFlatWorld.this.field_146387_g.getFlatLayers().get(GuiCreateFlatWorld.this.field_146387_g.getFlatLayers().size() - p_148126_1_ - 1);
            Item var9 = Item.func_150898_a(var8.func_151536_b());
            ItemStack var10 = var8.func_151536_b() == Blocks.field_150350_a ? null : new ItemStack(var9, 1, var8.getFillBlockMeta());
            String var11 = var10 != null && var9 != null ? var9.getItemStackDisplayName(var10) : "Air";
            this.func_148225_a(p_148126_2_, p_148126_3_, var10);
            GuiCreateFlatWorld.this.field_146289_q.drawString(var11, p_148126_2_ + 18 + 5, p_148126_3_ + 3, 16777215);
            String var12;

            if (p_148126_1_ == 0)
            {
                var12 = I18n.getStringParams("createWorld.customize.flat.layer.top", new Object[] {Integer.valueOf(var8.getLayerCount())});
            }
            else if (p_148126_1_ == GuiCreateFlatWorld.this.field_146387_g.getFlatLayers().size() - 1)
            {
                var12 = I18n.getStringParams("createWorld.customize.flat.layer.bottom", new Object[] {Integer.valueOf(var8.getLayerCount())});
            }
            else
            {
                var12 = I18n.getStringParams("createWorld.customize.flat.layer", new Object[] {Integer.valueOf(var8.getLayerCount())});
            }

            GuiCreateFlatWorld.this.field_146289_q.drawString(var12, p_148126_2_ + 2 + 213 - GuiCreateFlatWorld.this.field_146289_q.getStringWidth(var12), p_148126_3_ + 3, 16777215);
        }

        protected int func_148137_d()
        {
            return this.field_148155_a - 70;
        }
    }
}