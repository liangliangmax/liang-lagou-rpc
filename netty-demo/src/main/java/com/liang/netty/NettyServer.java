package com.liang.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    public static void main(String[] args) throws InterruptedException {

        //1.创建bossGroup线程组，：处理网络事件： 连接事件
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);

        //2.创建workerGroup线程组：处理网络事件，读写事件
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        //3.创建服务端启动助手
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //4.设置bossGroup线程组和workGroup线程组
        serverBootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)  //5.设置服务端通道实现为nio
                .option(ChannelOption.SO_BACKLOG,128)  //6.参数设置
                .childOption(ChannelOption.SO_KEEPALIVE,true)  //6.参数设置
                .childHandler(new ChannelInitializer<SocketChannel>() {  //7.创建一个通道初始化对象

                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {


//                        channel.pipeline().addLast(new MessageCodec());
//                        channel.pipeline().addLast(new MessageEncoder());

                        //添加编解码器
                        channel.pipeline().addLast(new MessageCodec());

                        //8.向pipeline中添加自定义的业务处理handler
                        channel.pipeline().addLast(new NettyServerHandler());

                    }
                });


        //9启动服务端并绑定端口，同时将异步改成同步
        ChannelFuture channelFuture = serverBootstrap.bind(9999).sync();

        System.out.println("服务端启动成功");
        //10.关闭通道，和关闭连接池
        channelFuture.channel().closeFuture().sync();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();

    }
}
