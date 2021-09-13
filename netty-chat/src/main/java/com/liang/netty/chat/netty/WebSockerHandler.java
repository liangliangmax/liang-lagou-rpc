package com.liang.netty.chat.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * websocket数据是以贞的形式处理
 */
@Component
@ChannelHandler.Sharable //设置通道共享
public class WebSockerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public List<Channel> channelList = new ArrayList<>();


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

        String text = textWebSocketFrame.text();
        System.out.println("msg:" + text);

        //当前发送消息的通道
        Channel channel = channelHandlerContext.channel();

        for (Channel channel1 : channelList) {

            if(channel != channel1){
                channel1.writeAndFlush(new TextWebSocketFrame(text));
            }
        }

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

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
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();

        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress().toString().substring(1)+"异常");

        //当有客户端断开连接时候就移除
        channelList.remove(channel);
    }
}
