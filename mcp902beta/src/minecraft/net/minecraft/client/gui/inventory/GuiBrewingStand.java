package net.minecraft.client.gui.inventory;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiBrewingStand extends GuiContainer
{
    private static final ResourceLocation field_147014_u = new ResourceLocation("textures/gui/container/brewing_stand.png");
    private TileEntityBrewingStand field_147013_v;
    private static final String __OBFID = "CL_00000746";

    public GuiBrewingStand(InventoryPlayer par1InventoryPlayer, TileEntityBrewingStand par2TileEntityBrewingStand)
    {
        super(new ContainerBrewingStand(par1InventoryPlayer, par2TileEntityBrewingStand));
        this.field_147013_v = par2TileEntityBrewingStand;
    }

    protected void func_146979_b(int p_146979_1_, int p_146979_2_)
    {
        String var3 = this.field_147013_v.func_145818_k_() ? this.field_147013_v.func_145825_b() : I18n.getStringParams(this.field_147013_v.func_145825_b(), new Object[0]);
        this.field_146289_q.drawString(var3, this.field_146999_f / 2 - this.field_146289_q.getStringWidth(var3) / 2, 6, 4210752);
        this.field_146289_q.drawString(I18n.getStringParams("container.inventory", new Object[0]), 8, this.field_147000_g - 96 + 2, 4210752);
    }

    protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.field_146297_k.getTextureManager().bindTexture(field_147014_u);
        int var4 = (this.field_146294_l - this.field_146999_f) / 2;
        int var5 = (this.field_146295_m - this.field_147000_g) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
        int var6 = this.field_147013_v.func_145935_i();

        if (var6 > 0)
        {
            int var7 = (int)(28.0F * (1.0F - (float)var6 / 400.0F));

            if (var7 > 0)
            {
                this.drawTexturedModalRect(var4 + 97, var5 + 16, 176, 0, 9, var7);
            }

            int var8 = var6 / 2 % 7;

            switch (var8)
            {
                case 0:
                    var7 = 29;
                    break;

                case 1:
                    var7 = 24;
                    break;

                case 2:
                    var7 = 20;
                    break;

                case 3:
                    var7 = 16;
                    break;

                case 4:
                    var7 = 11;
                    break;

                case 5:
                    var7 = 6;
                    break;

                case 6:
                    var7 = 0;
            }

            if (var7 > 0)
            {
                this.drawTexturedModalRect(var4 + 65, var5 + 14 + 29 - var7, 185, 29 - var7, 12, var7);
            }
        }
    }
}
