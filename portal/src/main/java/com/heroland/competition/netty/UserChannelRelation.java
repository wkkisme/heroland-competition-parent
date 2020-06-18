package com.heroland.competition.netty;


import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * heroland-competition
 *
 * @author wangkai
 * @date 2020/6/16
 */

public class UserChannelRelation {
    private static Logger logger = LoggerFactory.getLogger(UserChannelRelation.class);

    private static HashMap<String, Channel> manager = new HashMap<>();

    public static void put(String userId, Channel channel) {
        manager.put(userId, channel);
    }

    public static Channel get(String userId) {
        return manager.get(userId);
    }

    public static void remove(String userId) {
        manager.remove(userId);
    }

    public static void output() {
        for (HashMap.Entry<String, Channel> entry : manager.entrySet()) {
            logger.info("UserId:{},ChannelId{}", entry.getKey(), entry.getValue().id().asLongText());
        }
    }

    /**
     * 移除Channel
     *
     * @param channel
     */
    public static void remove(Channel channel) {
        for (Map.Entry<String, Channel> entry : manager.entrySet()) {
            if (entry.getValue().equals(channel)) {
                manager.remove(entry.getKey());
            }
        }
    }
}
