package com.liang.chat;

import com.liang.netty.MessageCodec;
import com.liang.netty.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * 聊天室客户端
 */
public class NettyChatClient {

    private String ip;
    private int port;

    public NettyChatClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void run() throws InterruptedException {

        //1.创建线程组
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {

            //2.创建客户端启动助手
            Bootstrap bootstrap = new Bootstrap();

            //3.设置线程组
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class) //4.设置客户端通道为nio
                    .handler(new ChannelInitializer<SocketChannel>() { //5.创建一个通道初始化对象
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {

                            //添加编解码器
                            channel.pipeline().addLast(new StringDecoder());
                            channel.pipeline().addLast(new StringEncoder());

                            //6.想pipeline中添加自定义的处理器handler
                            channel.pipeline().addLast(new NettyChatClientHandler());


                        }
                    });


            //7.启动客户端，等待连接服务器，，同时将异步改成同步
            ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
            Channel channel = channelFuture.channel();

            System.out.println(channel.localAddress().toString().substring(1)+"----------");

            Scanner scanner = new Scanner(System.in);

            while (scanner.hasNextLine()){
                String text = scanner.nextLine();

                //向服务端发送消息
                channel.writeAndFlush(text);

            }

            //8.关闭通道，关闭连接
            channelFuture.channel().closeFuture();


        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        new NettyChatClient("127.0.0.1",9998).run();
    }
}
