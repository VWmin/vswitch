package com.vwmin.channelstate;

import com.vwmin.OFChannelHandler;
import org.projectfloodlight.openflow.protocol.OFConfigFlags;
import org.projectfloodlight.openflow.protocol.OFGetConfigReply;
import org.projectfloodlight.openflow.protocol.OFGetConfigRequest;

import java.util.HashSet;
import java.util.Set;

public class WaitConfigRequestState extends OFState {
    protected WaitConfigRequestState(OFChannelHandler handler) {
        super(handler);
    }


    @Override
    public void processOFGetConfigRequest(OFGetConfigRequest request) {
        Set<OFConfigFlags> flags = new HashSet<>();
        flags.add(OFConfigFlags.FRAG_NORMAL);

        OFGetConfigReply reply = handler.getOfFactory().buildGetConfigReply()
                .setXid(handler.getHandshakeTransactionIds())
                .setFlags(flags)
                .setMissSendLen(0xffff)
                .build();
        handler.write(reply);
        handler.setState(new WaitDescriptionStatsRequest(handler));
    }
}
