package com.heroland.competition.netty;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * heroland-competition
 *
 * @author wangkai
 * @date 2020/6/16
 */

public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    private final Logger log = LoggerFactory.getLogger(HeartBeatHandler.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 判断evt是否是IdleStateEvent（用于触发用户事件，包含 读空闲/写空闲/读写空闲 ）
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.info("进入读空闲...");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                log.info("进入写空闲...");
            } else if (event.state() == IdleState.ALL_IDLE) {
                log.info("关闭无用的Channel，以防资源浪费。Channel Id：{}", ctx.channel().id());
                Channel channel = ctx.channel();
                channel.close();
                UserChannelRelation.remove(channel);
                log.info("Channel关闭后，client的数量为:{}", NoMaybeHandler.clients.size());
            }
        }
    }
}
