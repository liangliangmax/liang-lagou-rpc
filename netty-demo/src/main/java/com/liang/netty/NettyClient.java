package com.liang.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {



    public static void main(String[] args) throws InterruptedException {

        //1.创建线程组
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        //2.创建客户端启动助手
        Bootstrap bootstrap = new Bootstrap();

        //3.设置线程组
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class) //4.设置客户端通道为nio
                .handler(new ChannelInitializer<SocketChannel>() { //5.创建一个通道初始化对象
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

//                        ch.pipeline().addLast(new MessageCodec());
//                        ch.pipeline().addLast(new MessageEncoder());

                        //添加编解码器
                        ch.pipeline().addLast(new MessageCodec());

                        //6.想pipeline中添加自定义的处理器handler
                        ch.pipeline().addLast(new NettyClientHandler());

                    }
                });


        //7.启动客户端，等待连接服务器，，同时将异步改成同步
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9999).sync();

        //8.关闭通道，关闭连接
        channelFuture.channel().closeFuture();

        eventLoopGroup.shutdownGracefully();


    }

}
