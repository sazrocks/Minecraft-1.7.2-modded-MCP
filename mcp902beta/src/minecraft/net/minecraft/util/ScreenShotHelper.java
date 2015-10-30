package net.minecraft.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.IntBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.event.ClickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class ScreenShotHelper
{
    private static final Logger field_148261_a = LogManager.getLogger();
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    private static IntBuffer field_74293_b;
    private static int[] field_74294_c;
    private static final String __OBFID = "CL_00000656";

    public static IChatComponent func_148260_a(File p_148260_0_, int p_148260_1_, int p_148260_2_, Framebuffer p_148260_3_)
    {
        return func_148259_a(p_148260_0_, (String)null, p_148260_1_, p_148260_2_, p_148260_3_);
    }

    public static IChatComponent func_148259_a(File p_148259_0_, String p_148259_1_, int p_148259_2_, int p_148259_3_, Framebuffer p_148259_4_)
    {
        try
        {
            File var5 = new File(p_148259_0_, "screenshots");
            var5.mkdir();

            if (OpenGlHelper.func_148822_b())
            {
                p_148259_2_ = p_148259_4_.field_147622_a;
                p_148259_3_ = p_148259_4_.field_147620_b;
            }

            int var6 = p_148259_2_ * p_148259_3_;

            if (field_74293_b == null || field_74293_b.capacity() < var6)
            {
                field_74293_b = BufferUtils.createIntBuffer(var6);
                field_74294_c = new int[var6];
            }

            GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
            field_74293_b.clear();

            if (OpenGlHelper.func_148822_b())
            {
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, p_148259_4_.field_147617_g);
                GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, field_74293_b);
            }
            else
            {
                GL11.glReadPixels(0, 0, p_148259_2_, p_148259_3_, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, field_74293_b);
            }

            field_74293_b.get(field_74294_c);
            TextureUtil.func_147953_a(field_74294_c, p_148259_2_, p_148259_3_);
            BufferedImage var7 = null;

            if (OpenGlHelper.func_148822_b())
            {
                var7 = new BufferedImage(p_148259_4_.field_147621_c, p_148259_4_.field_147618_d, 1);
                int var8 = p_148259_4_.field_147620_b - p_148259_4_.field_147618_d;

                for (int var9 = var8; var9 < p_148259_4_.field_147620_b; ++var9)
                {
                    for (int var10 = 0; var10 < p_148259_4_.field_147621_c; ++var10)
                    {
                        var7.setRGB(var10, var9 - var8, field_74294_c[var9 * p_148259_4_.field_147622_a + var10]);
                    }
                }
            }
            else
            {
                var7 = new BufferedImage(p_148259_2_, p_148259_3_, 1);
                var7.setRGB(0, 0, p_148259_2_, p_148259_3_, field_74294_c, 0, p_148259_2_);
            }

            File var12;

            if (p_148259_1_ == null)
            {
                var12 = func_74290_a(var5);
            }
            else
            {
                var12 = new File(var5, p_148259_1_);
            }

            ImageIO.write(var7, "png", var12);
            ChatComponentText var13 = new ChatComponentText(var12.getName());
            var13.func_150256_b().func_150241_a(new ClickEvent(ClickEvent.Action.OPEN_FILE, var12.getAbsolutePath()));
            var13.func_150256_b().func_150228_d(Boolean.valueOf(true));
            return new ChatComponentTranslation("screenshot.success", new Object[] {var13});
        }
        catch (Exception var11)
        {
            field_148261_a.warn("Couldn\'t save screenshot", var11);
            return new ChatComponentTranslation("screenshot.failure", new Object[] {var11.getMessage()});
        }
    }

    private static File func_74290_a(File par0File)
    {
        String var2 = dateFormat.format(new Date()).toString();
        int var3 = 1;

        while (true)
        {
            File var1 = new File(par0File, var2 + (var3 == 1 ? "" : "_" + var3) + ".png");

            if (!var1.exists())
            {
                return var1;
            }

            ++var3;
        }
    }
}
