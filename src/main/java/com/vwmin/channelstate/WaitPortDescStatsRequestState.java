package com.vwmin.channelstate;

import com.vwmin.OFChannelHandler;
import org.projectfloodlight.openflow.protocol.*;
import org.projectfloodlight.openflow.types.MacAddress;
import org.projectfloodlight.openflow.types.OFPort;
import org.projectfloodlight.openflow.types.U64;

import java.util.*;

public class WaitPortDescStatsRequestState extends OFState {
    public WaitPortDescStatsRequestState(OFChannelHandler handler) {
        super(handler);
    }

    @Override
    public void processOFStatsRequest(OFStatsRequest<?> request) {

        OFPortDescStatsReply reply = handler.getOfFactory().buildPortDescStatsReply()
                .setFlags(getFlags())
                .setEntries(getEntries())
                .setXid(handler.getHandshakeTransactionIds())
                .build();

        handler.write(reply);
        handler.setState(new WaitConfigRequestState(handler));
    }

    private List<OFPortDesc> getEntries() {
        return Collections.singletonList(getEntryS1());
    }

    private OFPortDesc getEntryS1() {
        OFPortDesc.Builder builder = handler.getOfFactory().buildPortDesc();

        Set<OFPortConfig> configs = new HashSet<>();
        configs.add(OFPortConfig.PORT_DOWN);
        // configs.add(OFPortConfig.NO_RECV);
        // configs.add(OFPortConfig.NO_FWD);
        // configs.add(OFPortConfig.NO_PACKET_IN);

        Set<OFPortState> state = new HashSet<>();
        state.add(OFPortState.LINK_DOWN);
        // state.add(OFPortState.BLOCKED);
        // state.add(OFPortState.LIVE);

        Set<OFPortFeatures> current = new HashSet<>();
        Set<OFPortFeatures> advertised = new HashSet<>();
        Set<OFPortFeatures> supported = new HashSet<>();
        Set<OFPortFeatures> peer = new HashSet<>();

        List<OFPortDescProp> properties = new ArrayList<>();
        // try ethernet prop
        properties.add(handler.getOfFactory().buildPortDescPropBsnSpeedCapabilities()
                .setCurrent(U64.of(0L)).build());

        return builder
                .setPortNo(OFPort.LOCAL)
                .setHwAddr(MacAddress.of("ae:14:b6:51:88:49"))
                .setName("s1")
                .setConfig(configs)
                .setState(state)
//                .setCurr(current)
//                .setAdvertised(advertised)
//                .setSupported(supported)
//                .setPeer(peer)
//                .setCurrSpeed(0)
//                .setMaxSpeed(0)
                .setProperties(properties)

                .build();
    }


    private Set<OFStatsReplyFlags> getFlags() {
        Set<OFStatsReplyFlags> flags = new HashSet<>();
        flags.add(OFStatsReplyFlags.REPLY_MORE);
        return flags;
    }
}
