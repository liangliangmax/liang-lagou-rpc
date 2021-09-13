package com.liang.buffer.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class NIOSelectorServer {

    public static void main(String[] args) throws IOException {

        //打开一个服务端通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //绑定对应的端口号
        serverSocketChannel.bind(new InetSocketAddress(9999));

        //通道默认是阻塞的，需要设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        //创建选择器
        Selector selector = Selector.open();

        //将服务端通道注册到选择器上，并制定注册监听的事件OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("服务端启动成功");

        while(true){
            //检查选择器是否有事件
            int select = selector.select(1000);

            if(select ==0){
                System.out.println("没有事件发生");

                continue;
            }

            //获取事件合集
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){

                //判断事件是否是客户端连接事件，selectionKey.isAcceptable()
                SelectionKey next = iterator.next();

                if(next.isAcceptable()){

                    //得到客户端通道，并将通道注册到选择器上，并指定监听事件为op_read
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    System.out.println("有客户端连接");

                    //将通道设置非阻塞状态
                    socketChannel.configureBlocking(false);

                    socketChannel.register(selector,SelectionKey.OP_READ);


                }

                //判断是否是客户端读就绪事件selectionKey.isReadable()
                if(next.isReadable()){
                    //得到客户端通道，读取数据到缓冲区

                    SocketChannel channel = (SocketChannel)next.channel();

                    ByteBuffer allocate = ByteBuffer.allocate(1024);

                    int read = channel.read(allocate);

                    if(read >0){

                        System.out.println("客户端消息"+new String(allocate.array(),0,read, StandardCharsets.UTF_8));

                        //给客户端写数据
                        channel.write(ByteBuffer.wrap("你好".getBytes(StandardCharsets.UTF_8)));

                        channel.close();
                    }


                }

                //从集合中删除对应的事件，防止二次处理
                iterator.remove();

            }

        }

    }
}
