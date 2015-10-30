package net.minecraft.network;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import net.minecraft.client.network.NetHandlerHandshakeMemory;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.NetHandlerHandshakeTCP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MessageDeserializer;
import net.minecraft.util.MessageDeserializer2;
import net.minecraft.util.MessageSerializer;
import net.minecraft.util.MessageSerializer2;
import net.minecraft.util.ReportedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetworkSystem
{
    private static final Logger field_151275_b = LogManager.getLogger();
    private static final NioEventLoopGroup field_151276_c = new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty IO #%d").setDaemon(true).build());
    private final MinecraftServer field_151273_d;
    public volatile boolean field_151277_a;
    private final List field_151274_e = Collections.synchronizedList(new ArrayList());
    private final List field_151272_f = Collections.synchronizedList(new ArrayList());
    private static final String __OBFID = "CL_00001447";

    public NetworkSystem(MinecraftServer p_i45292_1_)
    {
        this.field_151273_d = p_i45292_1_;
        this.field_151277_a = true;
    }

    public void func_151265_a(InetAddress p_151265_1_, int p_151265_2_) throws IOException
    {
        List var3 = this.field_151274_e;

        synchronized (this.field_151274_e)
        {
            this.field_151274_e.add(((ServerBootstrap)((ServerBootstrap)(new ServerBootstrap()).channel(NioServerSocketChannel.class)).childHandler(new ChannelInitializer()
            {
                private static final String __OBFID = "CL_00001448";
                protected void initChannel(Channel p_initChannel_1_)
                {
                    try
                    {
                        p_initChannel_1_.config().setOption(ChannelOption.IP_TOS, Integer.valueOf(24));
                    }
                    catch (ChannelException var4)
                    {
                        ;
                    }

                    try
                    {
                        p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(false));
                    }
                    catch (ChannelException var3)
                    {
                        ;
                    }

                    p_initChannel_1_.pipeline().addLast("timeout", new ReadTimeoutHandler(30)).addLast("legacy_query", new PingResponseHandler(NetworkSystem.this)).addLast("splitter", new MessageDeserializer2()).addLast("decoder", new MessageDeserializer()).addLast("prepender", new MessageSerializer2()).addLast("encoder", new MessageSerializer());
                    NetworkManager var2 = new NetworkManager(false);
                    NetworkSystem.this.field_151272_f.add(var2);
                    p_initChannel_1_.pipeline().addLast("packet_handler", var2);
                    var2.func_150719_a(new NetHandlerHandshakeTCP(NetworkSystem.this.field_151273_d, var2));
                }
            }).group(field_151276_c).localAddress(p_151265_1_, p_151265_2_)).bind().syncUninterruptibly());
        }
    }

    public SocketAddress func_151270_a()
    {
        List var2 = this.field_151274_e;
        ChannelFuture var1;

        synchronized (this.field_151274_e)
        {
            var1 = ((ServerBootstrap)((ServerBootstrap)(new ServerBootstrap()).channel(LocalServerChannel.class)).childHandler(new ChannelInitializer()
            {
                private static final String __OBFID = "CL_00001449";
                protected void initChannel(Channel p_initChannel_1_)
                {
                    NetworkManager var2 = new NetworkManager(false);
                    var2.func_150719_a(new NetHandlerHandshakeMemory(NetworkSystem.this.field_151273_d, var2));
                    NetworkSystem.this.field_151272_f.add(var2);
                    p_initChannel_1_.pipeline().addLast("packet_handler", var2);
                }
            }).group(field_151276_c).localAddress(LocalAddress.ANY)).bind().syncUninterruptibly();
            this.field_151274_e.add(var1);
        }

        return var1.channel().localAddress();
    }

    public void func_151268_b()
    {
        this.field_151277_a = false;
        Iterator var1 = this.field_151274_e.iterator();

        while (var1.hasNext())
        {
            ChannelFuture var2 = (ChannelFuture)var1.next();
            var2.channel().close().syncUninterruptibly();
        }
    }

    public void func_151269_c()
    {
        List var1 = this.field_151272_f;

        synchronized (this.field_151272_f)
        {
            Iterator var2 = this.field_151272_f.iterator();

            while (var2.hasNext())
            {
                final NetworkManager var3 = (NetworkManager)var2.next();

                if (!var3.func_150724_d())
                {
                    var2.remove();

                    if (var3.func_150730_f() != null)
                    {
                        var3.func_150729_e().func_147231_a(var3.func_150730_f());
                    }
                    else if (var3.func_150729_e() != null)
                    {
                        var3.func_150729_e().func_147231_a(new ChatComponentText("Disconnected"));
                    }
                }
                else
                {
                    try
                    {
                        var3.processReadPackets();
                    }
                    catch (Exception var8)
                    {
                        if (var3.func_150731_c())
                        {
                            CrashReport var10 = CrashReport.makeCrashReport(var8, "Ticking memory connection");
                            CrashReportCategory var6 = var10.makeCategory("Ticking connection");
                            var6.addCrashSectionCallable("Connection", new Callable()
                            {
                                private static final String __OBFID = "CL_00001450";
                                public String call()
                                {
                                    return var3.toString();
                                }
                            });
                            throw new ReportedException(var10);
                        }

                        field_151275_b.warn("Failed to handle packet for " + var3.getSocketAddress(), var8);
                        final ChatComponentText var5 = new ChatComponentText("Internal server error");
                        var3.func_150725_a(new S40PacketDisconnect(var5), new GenericFutureListener[] {new GenericFutureListener()
                            {
                                private static final String __OBFID = "CL_00001451";
                                public void operationComplete(Future p_operationComplete_1_)
                                {
                                    var3.func_150718_a(var5);
                                }
                            }
                        });
                        var3.func_150721_g();
                    }
                }
            }
        }
    }

    public MinecraftServer func_151267_d()
    {
        return this.field_151273_d;
    }
}