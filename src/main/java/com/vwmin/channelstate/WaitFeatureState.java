package com.vwmin.channelstate;


import com.vwmin.OFChannelHandler;
import org.projectfloodlight.openflow.protocol.OFCapabilities;
import org.projectfloodlight.openflow.protocol.OFFeaturesReply;
import org.projectfloodlight.openflow.protocol.OFFeaturesRequest;
import org.projectfloodlight.openflow.types.DatapathId;
import org.projectfloodlight.openflow.types.OFAuxId;

import java.util.HashSet;
import java.util.Set;

public class WaitFeatureState extends OFState {


    public WaitFeatureState(OFChannelHandler handler) {
        super(handler);
    }

    @Override
    void processOFFeatureRequest(OFFeaturesRequest request) {
        Set<OFCapabilities> capabilities = new HashSet<>();
        capabilities.add(OFCapabilities.FLOW_STATS);
        capabilities.add(OFCapabilities.TABLE_STATS);
        capabilities.add(OFCapabilities.PORT_STATS);
        capabilities.add(OFCapabilities.GROUP_STATS);
//            capabilities.add(OFCapabilities.IP_REASM);
        capabilities.add(OFCapabilities.QUEUE_STATS);
//              capabilities.add(OFCapabilities.PORT_BLOCKED);
        capabilities.add(OFCapabilities.BUNDLES);
//            capabilities.add(OFCapabilities.FLOW_MONITORING);

        OFFeaturesReply reply = handler.getOfFactory().buildFeaturesReply()
                .setDatapathId(DatapathId.of(1L)) //of里的datapath到底是啥
                .setNBuffers(0)
                .setNTables((short) 254)
                .setAuxiliaryId(OFAuxId.of(0))
                .setCapabilities(capabilities)
                .setXid(handler.getHandshakeTransactionIds())
                .build();

        handler.write(reply);
        handler.setState(new WaitPortDescStatsRequestState(handler));
    }
}
