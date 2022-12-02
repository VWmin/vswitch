package com.vwmin.channelstate;

import com.vwmin.ControllerStateException;
import com.vwmin.OFChannelHandler;
import lombok.extern.slf4j.Slf4j;
import org.projectfloodlight.openflow.protocol.*;

import java.io.IOException;

@Slf4j
public abstract class OFState {
    protected final OFChannelHandler handler;

    protected OFState(OFChannelHandler handler) {
        this.handler = handler;
    }

    public void enterState() {
        // Do Nothing
    }

    public void logState() {
        log.debug("{} OFConnection Handshake - enter state {}",
                handler.getConnectionInfoString(), this.getClass().getSimpleName());
    }

    public void processOFMessage(OFMessage msg) {
        switch (msg.getType()) {
            case ECHO_REQUEST:
                processOFEchoRequest((OFEchoRequest) msg);
                break;
            case HELLO:
                processOFHello((OFHello) msg);
                break;
            case FEATURES_REQUEST:
                processOFFeatureRequest((OFFeaturesRequest) msg);
                break;
            case STATS_REQUEST:
                processOFStatsRequest((OFStatsRequest<?>) msg);
                break;
            case GET_CONFIG_REQUEST:
                processOFGetConfigRequest((OFGetConfigRequest) msg);
                break;
            case ROLE_REQUEST:
                processOFRoleRequest((OFRoleRequest) msg);
            case FLOW_MOD:
                processOFFlowMod((OFFlowMod) msg);
            case GROUP_MOD:
                processOFGroupMod((OFGroupMod) msg);
            case BARRIER_REQUEST:
                processOFBarrierRequest((OFBarrierRequest) msg);

        }
    }

    void processOFBarrierRequest(OFBarrierRequest request) {
        log.warn("barrier request, 没做 >> {}", request);
    }

    void processOFGroupMod(OFGroupMod msg) {
        log.warn("group mod，也没做！ >>> {}", msg);
    }

    void processOFFlowMod(OFFlowMod msg) {
        log.warn("flow mod, 还没做！ >>> {}", msg);
    }

    void processOFRoleRequest(OFRoleRequest request) {
        illegalMessageReceived(request);
    }

    void processOFGetConfigRequest(OFGetConfigRequest request) {
        illegalMessageReceived(request);
    }

    void processOFFeatureRequest(OFFeaturesRequest request) {
        illegalMessageReceived(request);
    }

    void processOFHello(OFHello hello) {
        illegalMessageReceived(hello);
    }

    void processOFStatsRequest(OFStatsRequest<?> request) {
        illegalMessageReceived(request);
    }

    void processOFEchoRequest(OFEchoRequest echoRequest) {
        OFEchoReply reply = handler.getOfFactory().buildEchoReply()
                .setXid(echoRequest.getXid())
                .setData(echoRequest.getData())
                .build();
        handler.write(reply);
    }

    void processOFEchoReply(OFEchoReply echoReply) {

    }

    void processOFError(OFErrorMsg m) {
//            logErrorDisconnect(m);
    }

    void processOFExperimenter(OFExperimenter m) {
//            unhandledMessageReceived(m);
    }

    protected String getControllerStateMessage(OFMessage m, String details) {
//            return String.format("Switch: [%s], State: [%s], received: [%s], details: %s",
//                    getConnectionInfoString(),
//                    this.toString(),
//                    m.getType().toString(),
//                    details);
        return "TODO: This is a controller state message.";
    }

    protected void illegalMessageReceived(OFMessage m) {
        String msg = getControllerStateMessage(m,
                "Controller should never send this message in the current state");
        throw new ControllerStateException(msg);

    }


}




