package com.liang.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * 编解码器
 *
 */
public class MessageCodec extends MessageToMessageCodec {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {

        System.out.println("正在编码");

        String str = (String) msg;

        out.add(Unpooled.copiedBuffer(str, CharsetUtil.UTF_8));
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {

        System.out.println("正在解码");

        ByteBuf byteBuf = (ByteBuf) msg;

        //传递到下一个handler
        out.add(byteBuf.toString(CharsetUtil.UTF_8));

    }
}
