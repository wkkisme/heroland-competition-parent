package com.heroland.competition.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * heroland-competition
 *
 * @author wangkai
 * @date 2020/6/16
 */
@Component
public class WebSocketServer {

    private final Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    /**
     * 主线程组 负责接收请求
     */
    private EventLoopGroup mainGroup;
    /**
     * 从线程组  负责处理请求   这里的主从线程组就是典型的多路复用思想
     */
    private EventLoopGroup subGroup;
    /**
     * 启动器
     */
    private ServerBootstrap server;
    /**
     * 某个操作完成时（无论是否成功）future将得到通知。
     */
    private ChannelFuture future;

    /**
     * 单例WbSocketServer
     */
    private static class SingletonWsServer {
        static final WebSocketServer instance = new WebSocketServer();
    }

    public static WebSocketServer getInstance() {
        return SingletonWsServer.instance;
    }


    public WebSocketServer() {
        mainGroup = new NioEventLoopGroup();
        subGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WwbSocketServerInitialize());//自定义的初始化类，注册管道内的处理器
    }

    public void start() {
        this.future = server.bind(8088);
        log.info("| Netty WebSocket Server 启动完毕，监听端口：8088 | ------------------------------------------------------ |");
    }
}
