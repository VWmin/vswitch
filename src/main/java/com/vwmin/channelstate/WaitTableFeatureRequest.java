package com.vwmin.channelstate;

import com.vwmin.OFChannelHandler;
import org.projectfloodlight.openflow.protocol.OFStatsRequest;

/**
 * 当版本小于13或等于15时，这个状态被跳过，因此这个处理函数也不会被执行
 * 下一个是plugin state，由于没有plugin也被跳过
 */
public class WaitTableFeatureRequest extends OFState {
    public WaitTableFeatureRequest(OFChannelHandler handler) {
        super(handler);
    }

    @Override
    void processOFStatsRequest(OFStatsRequest<?> request) {
        super.processOFStatsRequest(request);

    }
}
