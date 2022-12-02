package com.vwmin.channelstate;

import com.vwmin.OFChannelHandler;
import org.projectfloodlight.openflow.protocol.OFBarrierRequest;
import org.projectfloodlight.openflow.protocol.OFFlowMod;
import org.projectfloodlight.openflow.protocol.OFGroupMod;

public class MasterState extends OFState {
    public MasterState(OFChannelHandler handler) {
        super(handler);
    }

    @Override
    void processOFBarrierRequest(OFBarrierRequest request) {
        super.processOFBarrierRequest(request);
    }

    @Override
    void processOFGroupMod(OFGroupMod msg) {
        super.processOFGroupMod(msg);
    }

    @Override
    void processOFFlowMod(OFFlowMod msg) {
        super.processOFFlowMod(msg);
    }
}
