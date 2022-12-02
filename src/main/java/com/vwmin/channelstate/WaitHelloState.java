package com.vwmin.channelstate;

import com.vwmin.OFChannelHandler;
import lombok.extern.slf4j.Slf4j;
import org.projectfloodlight.openflow.protocol.OFHello;

/**
 * 这个阶段的作用时版本协商，controller会使用双方的最低版本
 */
@Slf4j
public class WaitHelloState extends OFState {


    public WaitHelloState(OFChannelHandler handler) {
        super(handler);
    }

    @Override
    void processOFHello(OFHello hello){
        log.info("TODO: process hello >>> {}", hello);
        OFHello send = handler.getOfFactory().buildHello()
                .setXid(handler.getHandshakeTransactionIds())
                .build();

        handler.write(send);
        handler.setState(new WaitFeatureState(handler));
    }

}
