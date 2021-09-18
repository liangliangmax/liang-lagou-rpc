package com.liang.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.util.List;

public class MessageEncoder extends MessageToMessageEncoder {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {

        System.out.println("正在编码");

        String str = (String) msg;

        out.add(Unpooled.copiedBuffer(str, CharsetUtil.UTF_8));

    }
}
