package com.vwmin;

import com.vwmin.channelstate.InitState;
import com.vwmin.channelstate.OFState;
import com.vwmin.channelstate.WaitHelloState;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.projectfloodlight.openflow.protocol.*;
import org.projectfloodlight.openflow.protocol.ver15.OFFactoryVer15;

import java.util.Collections;

@Slf4j
public class OFChannelHandler extends SimpleChannelInboundHandler<OFMessage> {

    private volatile OFState state;
    private Channel channel;
    @Getter
    private final OFFactory ofFactory;

    // FIXME 不确定switch端是不是0开始
    private long handshakeTransactionIds = 0;


    OFChannelHandler() {
        this.state = new InitState(this);
        // 15能通过channel handshake 13不能，为什么
        this.ofFactory = new OFFactoryVer15();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channel = ctx.channel();
        log.info("Connected to controller with {}", channel.remoteAddress());
        setState(new WaitHelloState(this));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, OFMessage msg) throws Exception {
        log.info("read >> {}", msg);
        try {
            state.processOFMessage(msg);
        } catch (Exception e) {
            ctx.fireExceptionCaught(e);
        }
    }


    /**
     * Update the channels state. Only called from the state machine.
     */
    public void setState(OFState state){
        this.state = state;
        state.logState();
        state.enterState();
    }


    public String getConnectionInfoString() {
        return "TODO: This is a connection info String.";
    }

    public void write(OFMessage m) {
        // 对面的程序只能处理列表
        channel.writeAndFlush(Collections.singletonList(m));
        log.debug("Send >>> {}", m);
    }

    public long getHandshakeTransactionIds() {
        return handshakeTransactionIds++;
    }


}
