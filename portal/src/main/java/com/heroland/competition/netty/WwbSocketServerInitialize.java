package com.heroland.competition.netty;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * heroland-competition
 *
 * @author wangkai
 * @date 2020/6/16
 */

public class WwbSocketServerInitialize extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //定义管道------------------------------------------------
        ChannelPipeline pipeline = socketChannel.pipeline();
        //定义管道中的众多处理器
        //HTTP的编解码处理器  HttpRequestDecoder, HttpResponseEncoder
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        // 对httpMessage进行聚合，聚合成FullHttpRequest或FullHttpResponse
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));
        // 增加心跳支持
        // 针对客户端，如果在1分钟时没有向服务端发送读写心跳(ALL)，则主动断开
        pipeline.addLast(new IdleStateHandler(60, 60, 60));
        pipeline.addLast(new HeartBeatHandler());//自定义的心跳处理器

        // ====================== 以下是支持httpWebsocket ======================
        /**
         * websocket 服务器处理的协议，用于指定给客户端连接访问的路由 : /ws
         * 对于websocket来讲，都是以frames进行传输的，不同的数据类型对应的frames也不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        // 自定义的业务处理handler
        pipeline.addLast(new NoMaybeHandler());
    }
}
