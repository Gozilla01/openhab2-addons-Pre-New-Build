/**
 * Copyright (c) 2010-2019 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.openwebnet.handler;

import static org.openhab.binding.openwebnet.OpenWebNetBindingConstants.*;

import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.openwebnet.OpenWebNetBindingConstants;
import org.openwebnet.message.BaseOpenMessage;
import org.openwebnet.message.OpenMessageFactory;
import org.openwebnet.message.Who;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link OpenWebNetScenesHandler} is responsible for handling commands/messages for a Scenes OpenWebNet device.
 * It extends the abstract {@link OpenWebNetThingHandler}.
 *
 * @author Massimo Valla - Initial contribution
 */

public class OpenWebNetScenesHandler extends OpenWebNetThingHandler {

    private final Logger logger = LoggerFactory.getLogger(OpenWebNetScenesHandler.class);

    public final static Set<ThingTypeUID> SUPPORTED_THING_TYPES = OpenWebNetBindingConstants.SCENES_SUPPORTED_THING_TYPES;
    private final static int WHAT_START_ON = 1;
    private final static int WHAT_START_OFF = 2;
    private final static int WHAT_ACTIVATE_ON = 3;
    private final static int WHAT_ACTIVATE_OFF = 4;

    public OpenWebNetScenesHandler(@NonNull Thing thing) {
        super(thing);
        logger.debug("==OWN:ScenesHandler== constructor");
    }

    @Override
    public void initialize() {
        super.initialize();
        logger.debug("==OWN:ScenesHandler== initialize() thing={}", thing.getUID());
    }

    @Override
    protected void requestChannelState(ChannelUID channel) {
        logger.debug("==OWN:ScenesHandler== requestChannelState() thingUID={} channel={}", thing.getUID(),
                channel.getId());
        String commandOWN = String.format("*#17*%d##", deviceWhere);
        bridgeHandler.gateway.send(OpenMessageFactory.parse(commandOWN));
    }

    @Override
    protected void handleChannelCommand(ChannelUID channel, Command command) {
        switch (channel.getId()) {
            case CHANNEL_SCENES_START:
                handleScenesStartCommand(channel, command);
                break;
            case CHANNEL_SCENES_ACTIVATE:
                handleScenesActivateCommand(channel, command);
                break;
            default: {
                logger.warn("==OWN:ScenesHandler== Unsupported channel UID {}", channel);
            }
        }
        // TODO
        // Note: if communication with thing fails for some reason,
        // indicate that by setting the status with detail information
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
        // "Could not control device at IP address x.x.x.x");
    }

    /**
     * Handles Scenes Start switch command
     *
     * @param channel
     * @param command
     */
    private void handleScenesStartCommand(ChannelUID channel, Command command) {
        logger.debug("==OWN:ScenesHandler== handleAuxCommand() (command={} - channel={})", command, channel);
        if (command instanceof OnOffType) {
            if (OnOffType.ON.equals(command)) {
                sendScenas(WHAT_START_ON);
            } else if (OnOffType.OFF.equals(command)) {
                sendScenas(WHAT_START_OFF);
            }
        } else {
            logger.warn("==OWN:ScenesHandler== Unsupported command: {}", command);
        }
    }

    /**
     * Handles Scenes Activate switch command
     *
     * @param channel
     * @param command
     */
    private void handleScenesActivateCommand(ChannelUID channel, Command command) {
        logger.debug("==OWN:ScenesHandler== handleAuxCommand() (command={} - channel={})", command, channel);
        if (command instanceof OnOffType) {
            if (OnOffType.ON.equals(command)) {
                sendScenas(WHAT_ACTIVATE_ON);
            } else if (OnOffType.OFF.equals(command)) {
                sendScenas(WHAT_ACTIVATE_OFF);
            }
        } else {
            logger.warn("==OWN:ScenesHandler== Unsupported command: {}", command);
        }
    }

    /**
     * Send Command
     */
    private void sendScenas(int what) {
        logger.debug("==OWN:ScenesHandler== sendScenas() deviceWhere:{}", deviceWhere);
        String commandOWN = String.format("*%d*%d*%d##", Who.SCENARIO_PROGRAMMING, what, deviceWhere);
        bridgeHandler.gateway.send(OpenMessageFactory.parse(commandOWN));
    }

    @Override
    protected String ownIdPrefix() {
        return org.openwebnet.message.Who.SCENARIO_PROGRAMMING.value().toString();
    }

    @Override
    protected void handleMessage(BaseOpenMessage msg) {
        super.handleMessage(msg);
        String channelID;
        logger.debug("==OWN:ScenesHandler== handleMessage() for thing: {}", thing.getUID());
        switch (Integer.parseInt(msg.getWhat().toString())) {
            case WHAT_START_ON:
                channelID = CHANNEL_SCENES_START;
                updateState(channelID, OnOffType.ON);
                break;
            case WHAT_START_OFF:
                channelID = CHANNEL_SCENES_START;
                updateState(channelID, OnOffType.OFF);
                break;
            case WHAT_ACTIVATE_ON:
                channelID = CHANNEL_SCENES_ACTIVATE;
                updateState(channelID, OnOffType.ON);
                break;
            case WHAT_ACTIVATE_OFF:
                channelID = CHANNEL_SCENES_ACTIVATE;
                updateState(channelID, OnOffType.OFF);
                break;
            default: {
                logger.warn("==OWN:ScenesHandler== Unsupported msg {}", msg);
            }
        }
    }

} // class