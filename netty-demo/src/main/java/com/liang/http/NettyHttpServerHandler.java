package com.liang.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * http服务器处理类
 */
public class NettyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 读取就绪事件
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        //判断是否是http请求
        if(msg instanceof HttpRequest){
            DefaultHttpRequest request = (DefaultHttpRequest) msg;

            System.out.println("浏览器请求路径是："+request.uri());

            ByteBuf byteBuf = Unpooled.copiedBuffer("你好", CharsetUtil.UTF_8);

            //给浏览器进行相应
            DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK, byteBuf);

            defaultFullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/html;charset=utf-8");
            defaultFullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());

            ctx.writeAndFlush(defaultFullHttpResponse);


        }

    }
}
