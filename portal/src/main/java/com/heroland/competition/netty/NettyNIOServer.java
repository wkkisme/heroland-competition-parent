package com.heroland.competition.netty;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * heroland-competition
 *
 * @author wangkai
 * @date 2020/6/16
 */

//@Component
public class NettyNIOServer implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        try {
            WebSocketServer.getInstance().start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
