package com.liang.http;

import com.liang.chat.NettyChatServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyHttpServer {

    private int port;

    public NettyHttpServer(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {

        //1.创建bossGroup线程组，：处理网络事件： 连接事件
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);

        //2.创建workerGroup线程组：处理网络事件，读写事件
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

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

                            //添加编解码器
                            channel.pipeline().addLast(new HttpServerCodec());

                            //8.向pipeline中添加自定义的业务处理handler
                            channel.pipeline().addLast(new NettyHttpServerHandler());


                        }
                    });


            //9启动服务端并绑定端口，同时将异步改成同步
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(future.isSuccess()){
                        System.out.println("端口绑定成功");
                    }else {
                        System.out.println("端口绑定失败");
                    }
                }
            });

            System.out.println("http服务端启动成功");
            //10.关闭通道，和关闭连接池
            channelFuture.channel().closeFuture().sync();


        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }


    public static void main(String[] args) throws InterruptedException {
        new NettyHttpServer(8080).run();
    }
}
