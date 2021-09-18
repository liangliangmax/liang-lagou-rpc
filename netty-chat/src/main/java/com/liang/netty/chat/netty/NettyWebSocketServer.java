package com.liang.netty.chat.netty;

import com.liang.netty.chat.config.NettyConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class NettyWebSocketServer implements Runnable {

    @Autowired
    private NettyConfig nettyConfig;

    @Autowired
    private WebSocketChannelInit webSocketChannelInit;

    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);

    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    /**
     * 容器销毁时候关闭
     */
    @PreDestroy
    public void close(){
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();

    }

    @Override
    public void run() {

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup);

            serverBootstrap.channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(webSocketChannelInit);


            ChannelFuture channelFuture = serverBootstrap.bind(nettyConfig.getPort()).sync();

            System.out.println("netty服务端启动成功");

            channelFuture.channel().closeFuture().sync();

        }catch (Exception e){

            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
