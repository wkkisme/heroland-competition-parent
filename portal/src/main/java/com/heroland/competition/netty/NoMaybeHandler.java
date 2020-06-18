package com.heroland.competition.netty;


import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * heroland-competition
 *
 * @author wangkai
 * @date 2020/6/16
 */

public class NoMaybeHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private final Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    /**
     * 管理所有客户端的channel通道
     */
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        //获取客户端传输过来的消息
        String content = textWebSocketFrame.text();
        Channel currentChannel = channelHandlerContext.channel();
        try {
            //将消息转换成pojo
            WsDataContent wsDataContent =new WsDataContent();
            wsDataContent.setData(textWebSocketFrame);
            wsDataContent.setMsgId("1");
            wsDataContent.setAction(1);
            UserWebsocketSalt userWebsocketSalt1 = new UserWebsocketSalt();
            userWebsocketSalt1.setUserId("2323");
            wsDataContent.setSalt(userWebsocketSalt1);
            Integer action = wsDataContent.getAction();
            String msgId = wsDataContent.getMsgId();
            //判断消息类型，根据不同的类型来处理不同的业务
            if (action.equals(MsgActionEnum.CONNECT.type)) {
                //当Websocket第一次建立的时候，初始化Channel，把Channel和userId关联起来
                UserWebsocketSalt userWebsocketSalt = wsDataContent.getSalt();
                if (userWebsocketSalt == null || userWebsocketSalt.getUserId() == null) {
                    //主动断开连接
                    writeAndFlushResponse(MsgActionEnum.BREAK_OFF.type, msgId, "断开", currentChannel);
                    //currentChannel.close();
                    return;
                }
                String userId = userWebsocketSalt.getUserId();
                //我们用loginLabel 标签结合长连接消息来做单点登录，踢设备下线，可以忽略中间的业务代码，这里主要是处理将userId于Channel绑定，存在Map中  -》UserChannelRelation.put(userId, currentChannel)
                String loginLabel = userWebsocketSalt.getLoginLabel();
                Channel existChannel = UserChannelRelation.get(userId);
                if (existChannel != null) {
                    writeAndFlushResponse(MsgActionEnum.MESSAGE_SIGN.type, msgId, null, currentChannel);
                    existChannel.close();
                    //存在当前用户的连接，验证登录标签
//                    LinkUserService linkUserService = (LinkUserService) SpringUtil.getBean("linkUserServiceImpl");
//                    if (linkUserService.checkUserLoginLabel(userId, loginLabel)) {
//                        //是同一次登录标签,加入新连接，关闭旧的连接
//                        UserChannelRelation.put(userId, currentChannel);
//                        writeAndFlushResponse(MsgActionEnum.BREAK_OFF.type, null, "断开", existChannel);
//                        writeAndFlushResponse(MsgActionEnum.MESSAGE_SIGN.type, msgId, null, currentChannel);
//                        //existChannel.close();
//                    } else {
//                        //不是同一次登录标签，拒绝连接
//                        writeAndFlushResponse(MsgActionEnum.BREAK_OFF.type, null, "断开", currentChannel);
//                        //currentChannel.close();
//                    }
                } else {
                    UserChannelRelation.put(userId, currentChannel);
                    writeAndFlushResponse(MsgActionEnum.MESSAGE_SIGN.type, msgId, null, currentChannel);
                }
            } else if (action.equals(MsgActionEnum.KEEPALIVE.type)) {
                //心跳类型的消息
                log.info("收到来自Channel为{}的心跳包......", currentChannel);
                writeAndFlushResponse(MsgActionEnum.MESSAGE_SIGN.type, msgId, null, currentChannel);
            } else {
                throw new RuntimeException("连接请求参数错误！");
            }
        } catch (Exception e) {
            log.error("当前连接出错！关闭当前Channel！");
            closeAndRemoveChannel(currentChannel);
        }
    }

    /**
     * 响应客户端
     */
    public static void writeAndFlushResponse(Integer action, String msgId, Object data, Channel channel) {
        WsDataContent wsDataContent = new WsDataContent();
        wsDataContent.setAction(action);
        wsDataContent.setMsgId(msgId);
        wsDataContent.setData(data);
        channel.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(wsDataContent)));
    }

    /**
     * 构建强制下线消息体
     *
     * @return
     */
//    public static PushMessageData createKickMsgBody() {
//        PushMessageData pushMessageData = new PushMessageData();
//        pushMessageData.setMsgType(MessageEnums.MsgTp.ClientMsgTp.getId());
//        pushMessageData.setMsgVariety(MessageEnums.ClientMsgTp.FORCED_OFFLINE.getCode());
//        pushMessageData.setTime(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
//        pushMessageData.setMsgBody(null);
//        return pushMessageData;
//    }

    /**
     * 构建派单消息体
     *
     * @return
     */
//    public static PushMessageData createDistributeOrderMsgBody(String orderId) {
//        PushMessageData pushMessageData = new PushMessageData();
//        pushMessageData.setMsgType(MessageEnums.MsgTp.OrderMsgTp.getId());
//        pushMessageData.setMsgVariety(MessageEnums.OrderMsgTp.PUSH_CODE_ORDER_ROB.getCode());
//        pushMessageData.setTime(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
//        MsgBodyVO msgBodyVO = new MsgBodyVO(orderId);
//        pushMessageData.setMsgBody(msgBodyVO);
//        return pushMessageData;
//    }

    /**
     * 当客户端连接服务端之后（打开连接）
     * 获取客户端的channel，并且放到ChannelGroup中去进行管理
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端建立连接，Channel Id为:{}", ctx.channel().id().asShortText());
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel
        Channel channel = ctx.channel();
        clients.remove(channel);
        UserChannelRelation.remove(channel);
        log.info("客户端断开连接，Channel Id为:{}", channel.id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //发生异常之后关闭连接（关闭channel），随后从ChannelGroup中移除
        Channel channel = ctx.channel();
        cause.printStackTrace();
        channel.close();
        clients.remove(channel);
        UserChannelRelation.remove(channel);
        log.info("连接发生异常，Channel Id为:{}", channel.id().asShortText());
    }

    /**
     * 关闭Channel
     *
     * @param channel
     */
    public static void closeAndRemoveChannel(Channel channel) {
        channel.close();
        clients.remove(channel);
    }
}
