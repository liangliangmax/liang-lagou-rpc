package com.liang.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

public class MessageDecoder extends MessageToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {

        System.out.println("正在解码");

        ByteBuf byteBuf = (ByteBuf) msg;

        //传递到下一个handler
        out.add(byteBuf.toString(CharsetUtil.UTF_8));



    }
}
