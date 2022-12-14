/**
 * Copyright 2011, Big Switch Networks, Inc.
 * Originally created by David Erickson, Stanford University
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 **/

package com.vwmin;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.projectfloodlight.openflow.protocol.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Decode an openflow message from a channel, for use in a netty pipeline.
 *
 * @author Andreas Wundsam <andreas.wundsam@bigswitch.com>
 */
public class OFMessageDecoder extends ByteToMessageDecoder {

    private OFMessageReader<OFMessage> reader;

    public OFMessageDecoder() {
        setReader();
    }

    public OFMessageDecoder(OFVersion version) {
        setVersion(version);
        setReader();
    }

    private void setReader() {
        reader = OFFactories.getGenericReader();
    }

    public void setVersion(OFVersion version) {
        OFFactory factory = OFFactories.getFactory(version);
        this.reader = factory.getReader();
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (!ctx.channel().isActive()) {
            // In testing, I see decode being called AFTER decode last.
            // This check avoids that from reading corrupted frames
            return;
        }

        // Note(andiw): netty4 adds support for more efficient handling of lists messages in the
        // pipeline itself.
        // Instead of constructing a list of messages here, we could also just add the individual
        // messages to the "out" list provided by netty. This would require changing all the handlers
        // in the pipeline to accept "OFMessage" instead of "Iterable<OFMessage>". Probably
        // a good idea, but left for a future cleanup.

        OFMessage message = reader.readFrom(in);
        if (message != null) {
            out.add(message);
        }

    }
}