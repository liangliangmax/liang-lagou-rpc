package com.liang.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

public class NettyChatServerHandler extends SimpleChannelInboundHandler<String> {

    public static List<Channel> channelList = new ArrayList<>();

    /**
     * 通道就绪事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        //当有新客户端连接进来时候，将通道存入集合
        channelList.add(channel);
        System.out.println("[server]:" +
                channel.remoteAddress().toString().substring(1)+"在线");

    }

    //channel下线
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();

        //当有客户端断开连接时候就移除
        channelList.remove(channel);

        System.out.println(channel.remoteAddress().toString().substring(1) +"下线");

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        //当前发送消息的通道
        Channel channel = ctx.channel();

        for (Channel channel1 : channelList) {

            if(channel != channel1){
                channel1.writeAndFlush("["+
                        channel.remoteAddress().toString().substring(1)+
                        "]说："+msg);
            }
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();

        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress().toString().substring(1)+"异常");

        //当有客户端断开连接时候就移除
        channelList.remove(channel);
    }
}
