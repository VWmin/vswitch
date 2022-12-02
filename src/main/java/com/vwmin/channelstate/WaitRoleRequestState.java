package com.vwmin.channelstate;

import com.vwmin.OFChannelHandler;
import org.projectfloodlight.openflow.protocol.OFControllerRole;
import org.projectfloodlight.openflow.protocol.OFRoleReply;
import org.projectfloodlight.openflow.protocol.OFRoleRequest;

public class WaitRoleRequestState extends OFState {
    public WaitRoleRequestState(OFChannelHandler handler) {
        super(handler);
    }

    @Override
    void processOFRoleRequest(OFRoleRequest request) {
        OFRoleReply reply = handler.getOfFactory().buildRoleReply()
                .setRole(request.getRole())
                .setGenerationId(request.getGenerationId())
                .setXid(request.getXid())
                .build();
        handler.write(reply);
        if (request.getRole() == OFControllerRole.ROLE_MASTER) {
            handler.setState(new MasterState(handler));
        }
//        handler.setState(new );

    }
}
