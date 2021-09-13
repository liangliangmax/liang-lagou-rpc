package com.liang.buffer.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class NIOClient {

    public static void main(String[] args) throws IOException {

        //1.打开通道
        SocketChannel socketChannel = SocketChannel.open();

        //2.这是链接ip和端口号
        socketChannel.connect(new InetSocketAddress("127.0.0.1",9999));

        //3.写出数据

        ByteBuffer byteBuffer = ByteBuffer.wrap("还钱".getBytes(StandardCharsets.UTF_8));
        socketChannel.write(byteBuffer);

        //4.读取服务器的数据

        ByteBuffer allocate = ByteBuffer.allocate(1024);

        int read = socketChannel.read(allocate);

        System.out.println(new String(allocate.array(),0,read,StandardCharsets.UTF_8));


        //5.释放资源
        socketChannel.close();


    }
}
