package com.vwmin;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

public class OFChannelInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) {
        OFChannelHandler handler = new OFChannelHandler();
        ch.pipeline()
                .addLast(PipelineHandler.OF_MESSAGE_DECODER, new OFMessageDecoder())
                .addLast(PipelineHandler.OF_MESSAGE_ENCODER, new OFMessageEncoder())
//                .addLast(PipelineHandler.MAIN_IDLE, new IdleStateHandler(PipelineIdleReadTimeout.MAIN, PipelineIdleWriteTimeout.MAIN, 0))
//                .addLast(PipelineHandler.READ_TIMEOUT, new ReadTimeoutHandler(30))
//                .addLast(PipelineHandler.CHANNEL_HANDSHAKE_TIMEOUT, new HandshakeTimeoutHandler()) switch没能在足够快的时间内完成handshake
                .addLast(PipelineHandler.CHANNEL_HANDLER, handler);
    }

    public static class PipelineHandler {
        public final static String CHANNEL_HANDSHAKE_TIMEOUT = "channelhandshaketimeout";
        public final static String SWITCH_HANDSHAKE_TIMEOUT = "switchhandshaketimeout";
        public final static String CHANNEL_HANDLER = "channelhandler";
        public final static String MAIN_IDLE = "mainidle";
        public final static String AUX_IDLE = "auxidle";
        public final static String OF_MESSAGE_DECODER = "ofmessagedecoder";
        public final static String OF_MESSAGE_ENCODER = "ofmessageencoder";
        public final static String READ_TIMEOUT = "readtimeout";
        public final static String SSL_TLS_ENCODER_DECODER = "ofsecurechannelencoderdecoder";
    }

    /**
     * Timeouts for parts of the handshake, in seconds
     */
    public static class PipelineHandshakeTimeout {
        final static int CHANNEL = 10;
        public final static int SWITCH = 30;
    }

    /**
     * Timeouts for writes on connections, in seconds
     */
    public static class PipelineIdleWriteTimeout {
        final static int MAIN = 2;
        public final static int AUX = 15;
    }

    /**
     * Timeouts for reads on connections, in seconds
     */
    public static class PipelineIdleReadTimeout {
        final static int MAIN = 3 * PipelineIdleWriteTimeout.MAIN;
        public final static int AUX = 3 * PipelineIdleWriteTimeout.AUX;
    }
}
