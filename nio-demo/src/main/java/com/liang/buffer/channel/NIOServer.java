package com.liang.buffer.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

//服务端
public class NIOServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        //1.打开服务端的通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();


        //2.绑定对应的端口号
        serverSocketChannel.bind(new InetSocketAddress(9999));

        //3.通道默认是阻塞的，需要设置非阻塞
        serverSocketChannel.configureBlocking(false);

        //4.检测是否有客户端连接，游客户端连接会返回对应的channel
        while (true){
            SocketChannel socketChannel= serverSocketChannel.accept();

            if(socketChannel == null){
                //说明没有客户端连接，则可以休息

                System.out.println("没有客户端连接");
                Thread.sleep(1000);
                continue;

            }

            //5.获取客户端传递过来的数据，并把数据放在byteBuffer这个缓冲区

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int read = socketChannel.read(byteBuffer);

            //read总共有三种状态
            //正数，有效字节
            //0本次没有读到数据
            //-1读到末尾

            System.out.println(new String(byteBuffer.array(),0,read, StandardCharsets.UTF_8));

            //6.给客户端写回数据
            socketChannel.write(ByteBuffer.wrap("meiqian".getBytes(StandardCharsets.UTF_8)));

            //7.关闭连接
            socketChannel.close();
        }

    }
}
