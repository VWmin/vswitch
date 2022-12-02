package com.vwmin.channelstate;

import com.vwmin.OFChannelHandler;
import org.projectfloodlight.openflow.protocol.OFDescStatsReply;
import org.projectfloodlight.openflow.protocol.OFStatsRequest;

import java.util.Collections;

public class WaitDescriptionStatsRequest extends OFState {
    protected WaitDescriptionStatsRequest(OFChannelHandler handler) {
        super(handler);
    }

    @Override
    void processOFStatsRequest(OFStatsRequest<?> request) {
        OFDescStatsReply reply = handler.getOfFactory().buildDescStatsReply()
                .setFlags(Collections.emptySet())
                .setDpDesc("s1")
                .setHwDesc("Open vSwitch")
                .setMfrDesc("Nicira, Inc.")
                .setSerialNum("None")
                .setSwDesc("2.13.1")
                .setXid(handler.getHandshakeTransactionIds())
                .build();
        handler.write(reply);
//        handler.setState(new WaitTableFeatureRequest(handler));
        handler.setState(new WaitRoleRequestState(handler));
    }
}
