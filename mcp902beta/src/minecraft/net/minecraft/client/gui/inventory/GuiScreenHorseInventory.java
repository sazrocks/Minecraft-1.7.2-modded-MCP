package net.minecraft.client.gui.inventory;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiScreenHorseInventory extends GuiContainer
{
    private static final ResourceLocation field_147031_u = new ResourceLocation("textures/gui/container/horse.png");
    private IInventory field_147030_v;
    private IInventory field_147029_w;
    private EntityHorse field_147034_x;
    private float field_147033_y;
    private float field_147032_z;
    private static final String __OBFID = "CL_00000760";

    public GuiScreenHorseInventory(IInventory par1IInventory, IInventory par2IInventory, EntityHorse par3EntityHorse)
    {
        super(new ContainerHorseInventory(par1IInventory, par2IInventory, par3EntityHorse));
        this.field_147030_v = par1IInventory;
        this.field_147029_w = par2IInventory;
        this.field_147034_x = par3EntityHorse;
        this.field_146291_p = false;
    }

    protected void func_146979_b(int p_146979_1_, int p_146979_2_)
    {
        this.field_146289_q.drawString(this.field_147029_w.func_145818_k_() ? this.field_147029_w.func_145825_b() : I18n.getStringParams(this.field_147029_w.func_145825_b(), new Object[0]), 8, 6, 4210752);
        this.field_146289_q.drawString(this.field_147030_v.func_145818_k_() ? this.field_147030_v.func_145825_b() : I18n.getStringParams(this.field_147030_v.func_145825_b(), new Object[0]), 8, this.field_147000_g - 96 + 2, 4210752);
    }

    protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.field_146297_k.getTextureManager().bindTexture(field_147031_u);
        int var4 = (this.field_146294_l - this.field_146999_f) / 2;
        int var5 = (this.field_146295_m - this.field_147000_g) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);

        if (this.field_147034_x.isChested())
        {
            this.drawTexturedModalRect(var4 + 79, var5 + 17, 0, this.field_147000_g, 90, 54);
        }

        if (this.field_147034_x.func_110259_cr())
        {
            this.drawTexturedModalRect(var4 + 7, var5 + 35, 0, this.field_147000_g + 54, 18, 18);
        }

        GuiInventory.func_147046_a(var4 + 51, var5 + 60, 17, (float)(var4 + 51) - this.field_147033_y, (float)(var5 + 75 - 50) - this.field_147032_z, this.field_147034_x);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.field_147033_y = (float)par1;
        this.field_147032_z = (float)par2;
        super.drawScreen(par1, par2, par3);
    }
}
