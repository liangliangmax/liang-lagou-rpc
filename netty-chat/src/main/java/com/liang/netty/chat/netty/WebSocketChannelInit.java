package com.liang.netty.chat.netty;

import com.liang.netty.chat.config.NettyConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class WebSocketChannelInit extends ChannelInitializer {

    @Autowired
    private NettyConfig nettyConfig;

    @Autowired
    private WebSockerHandler webSockerHandler;


    @Override
    protected void initChannel(Channel channel) throws Exception {

        ChannelPipeline pipeline = channel.pipeline();

        //添加http的支持
        pipeline.addLast(new HttpServerCodec());

        //添加对大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());

        //post请求分三部分，request line / request header / message body
        //httpOBjectAggregator将多个信息转化成单一的request或者response对象
        pipeline.addLast(new HttpObjectAggregator(8000));

        pipeline.addLast(new WebSocketServerProtocolHandler(nettyConfig.getPath()));


        //自定义处理handler
        pipeline.addLast(webSockerHandler);


    }

}
