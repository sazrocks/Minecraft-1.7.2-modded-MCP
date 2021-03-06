package net.minecraft.client.gui;

import io.netty.buffer.Unpooled;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiCommandBlock extends GuiScreen
{
    private static final Logger field_146488_a = LogManager.getLogger();
    private GuiTextField field_146485_f;
    private GuiTextField field_146486_g;
    private final CommandBlockLogic field_146489_h;
    private GuiButton field_146490_i;
    private GuiButton field_146487_r;
    private static final String __OBFID = "CL_00000748";

    public GuiCommandBlock(CommandBlockLogic p_i45032_1_)
    {
        this.field_146489_h = p_i45032_1_;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.field_146485_f.func_146178_a();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.field_146292_n.clear();
        this.field_146292_n.add(this.field_146490_i = new GuiButton(0, this.field_146294_l / 2 - 4 - 150, this.field_146295_m / 4 + 120 + 12, 150, 20, I18n.getStringParams("gui.done", new Object[0])));
        this.field_146292_n.add(this.field_146487_r = new GuiButton(1, this.field_146294_l / 2 + 4, this.field_146295_m / 4 + 120 + 12, 150, 20, I18n.getStringParams("gui.cancel", new Object[0])));
        this.field_146485_f = new GuiTextField(this.field_146289_q, this.field_146294_l / 2 - 150, 50, 300, 20);
        this.field_146485_f.func_146203_f(32767);
        this.field_146485_f.func_146195_b(true);
        this.field_146485_f.func_146180_a(this.field_146489_h.func_145753_i());
        this.field_146486_g = new GuiTextField(this.field_146289_q, this.field_146294_l / 2 - 150, 135, 300, 20);
        this.field_146486_g.func_146203_f(32767);
        this.field_146486_g.func_146184_c(false);
        this.field_146486_g.func_146180_a(this.field_146489_h.func_145753_i());

        if (this.field_146489_h.func_145749_h() != null)
        {
            this.field_146486_g.func_146180_a(this.field_146489_h.func_145749_h().func_150260_c());
        }

        this.field_146490_i.field_146124_l = this.field_146485_f.func_146179_b().trim().length() > 0;
    }

    public void func_146281_b()
    {
        Keyboard.enableRepeatEvents(false);
    }

    protected void func_146284_a(GuiButton p_146284_1_)
    {
        if (p_146284_1_.field_146124_l)
        {
            if (p_146284_1_.field_146127_k == 1)
            {
                this.field_146297_k.func_147108_a((GuiScreen)null);
            }
            else if (p_146284_1_.field_146127_k == 0)
            {
                PacketBuffer var2 = new PacketBuffer(Unpooled.buffer());

                try
                {
                    var2.writeByte(this.field_146489_h.func_145751_f());
                    this.field_146489_h.func_145757_a(var2);
                    var2.func_150785_a(this.field_146485_f.func_146179_b());
                    this.field_146297_k.func_147114_u().func_147297_a(new C17PacketCustomPayload("MC|AdvCdm", var2));
                }
                catch (Exception var7)
                {
                    field_146488_a.error("Couldn\'t send command block info", var7);
                }
                finally
                {
                    var2.release();
                }

                this.field_146297_k.func_147108_a((GuiScreen)null);
            }
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        this.field_146485_f.func_146201_a(par1, par2);
        this.field_146486_g.func_146201_a(par1, par2);
        this.field_146490_i.field_146124_l = this.field_146485_f.func_146179_b().trim().length() > 0;

        if (par2 != 28 && par2 != 156)
        {
            if (par2 == 1)
            {
                this.func_146284_a(this.field_146487_r);
            }
        }
        else
        {
            this.func_146284_a(this.field_146490_i);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        this.field_146485_f.func_146192_a(par1, par2, par3);
        this.field_146486_g.func_146192_a(par1, par2, par3);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.func_146276_q_();
        this.drawCenteredString(this.field_146289_q, I18n.getStringParams("advMode.setCommand", new Object[0]), this.field_146294_l / 2, 20, 16777215);
        this.drawString(this.field_146289_q, I18n.getStringParams("advMode.command", new Object[0]), this.field_146294_l / 2 - 150, 37, 10526880);
        this.field_146485_f.func_146194_f();
        byte var4 = 75;
        byte var5 = 0;
        FontRenderer var10001 = this.field_146289_q;
        String var10002 = I18n.getStringParams("advMode.nearestPlayer", new Object[0]);
        int var10003 = this.field_146294_l / 2 - 150;
        int var8 = var5 + 1;
        this.drawString(var10001, var10002, var10003, var4 + var5 * this.field_146289_q.FONT_HEIGHT, 10526880);
        this.drawString(this.field_146289_q, I18n.getStringParams("advMode.randomPlayer", new Object[0]), this.field_146294_l / 2 - 150, var4 + var8++ * this.field_146289_q.FONT_HEIGHT, 10526880);
        this.drawString(this.field_146289_q, I18n.getStringParams("advMode.allPlayers", new Object[0]), this.field_146294_l / 2 - 150, var4 + var8++ * this.field_146289_q.FONT_HEIGHT, 10526880);

        if (this.field_146486_g.func_146179_b().length() > 0)
        {
            int var7 = var4 + var8 * this.field_146289_q.FONT_HEIGHT + 20;
            this.drawString(this.field_146289_q, I18n.getStringParams("advMode.previousOutput", new Object[0]), this.field_146294_l / 2 - 150, var7, 10526880);
            this.field_146486_g.func_146194_f();
        }

        super.drawScreen(par1, par2, par3);
    }
}
