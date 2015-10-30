package net.minecraft.client.gui;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class GuiSleepMP extends GuiChat
{
    private static final String __OBFID = "CL_00000697";

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m - 40, I18n.getStringParams("multiplayer.stopSleeping", new Object[0])));
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        if (par2 == 1)
        {
            this.func_146418_g();
        }
        else if (par2 != 28 && par2 != 156)
        {
            super.keyTyped(par1, par2);
        }
        else
        {
            String var3 = this.field_146415_a.func_146179_b().trim();

            if (!var3.isEmpty())
            {
                this.field_146297_k.thePlayer.sendChatMessage(var3);
            }

            this.field_146415_a.func_146180_a("");
            this.field_146297_k.ingameGUI.func_146158_b().func_146240_d();
        }
    }

    protected void func_146284_a(GuiButton p_146284_1_)
    {
        if (p_146284_1_.field_146127_k == 1)
        {
            this.func_146418_g();
        }
        else
        {
            super.func_146284_a(p_146284_1_);
        }
    }

    private void func_146418_g()
    {
        NetHandlerPlayClient var1 = this.field_146297_k.thePlayer.sendQueue;
        var1.func_147297_a(new C0BPacketEntityAction(this.field_146297_k.thePlayer, 3));
    }
}
