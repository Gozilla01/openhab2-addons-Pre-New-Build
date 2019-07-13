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
import java.util.concurrent.TimeUnit;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.UnDefType;
import org.openhab.binding.openwebnet.OpenWebNetBindingConstants;
import org.openwebnet.message.BaseOpenMessage;
import org.openwebnet.message.Lighting;
import org.openwebnet.message.OpenMessage;
import org.openwebnet.message.OpenMessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link OpenWebNetMotionDetectorHandler} is responsible for handling commands/messages for a Motion Detector
 * OpenWebNet device.
 * It extends the abstract {@link OpenWebNetThingHandler}.
 *
 * @author Massimo Valla - Initial contribution
 */

public class OpenWebNetMotionDetectorHandler extends OpenWebNetThingHandler {

    private final Logger logger = LoggerFactory.getLogger(OpenWebNetMotionDetectorHandler.class);

    public final static Set<ThingTypeUID> SUPPORTED_THING_TYPES = OpenWebNetBindingConstants.MOTION_DETECTOR_SUPPORTED_THING_TYPES;
    protected Lighting.Type lightingType = Lighting.Type.ZIGBEE;
    private final static int SCHEDULE_DELAY = 2000; // ms
    private final static String REQUEST_CHANNEL = "6";

    // protected Command.Type commandType = Command.Type.ZIGBEE;
    public OpenWebNetMotionDetectorHandler(@NonNull Thing thing) {
        super(thing);
        logger.debug("==OWN:MotionDetectorHandler== constructor");
    }

    @Override
    public void initialize() {
        super.initialize();
        logger.debug("==OWN:MotionDetectorHandler== initialize() thing={}", thing.getUID());
    }

    @Override
    protected void requestChannelState(ChannelUID channel) {
        logger.debug("==OWN:MotionDetectorHandler== requestChannelState() thingUID={} channel={}", thing.getUID(),
                channel.getId());
        // is not possible to request channel state for Command buttons
        updateStatus(ThingStatus.ONLINE);
        updateState(channel, UnDefType.UNDEF);
        if (channel.getId().equals(CHANNEL_MOTION_DETECTOR_VALUE)) {
            requestLux();
        }
    }

    @Override
    protected void handleChannelCommand(ChannelUID channel, Command command) {
        logger.debug("==OWN:MotionDetectorHandler== handleChannelCommand() command={} channel={}", command,
                channel.getId());
    }

    @Override
    protected String ownIdPrefix() {
        return org.openwebnet.message.Who.LIGHTING.value().toString();
    }

    @Override
    protected void handleMessage(BaseOpenMessage msg) {
        super.handleMessage(msg);
        String channelID;
        Integer value;
        /*
         * to extract value lux
         * example OWN with 23,7 Lux
         * #1*03#4#01*6*237##
         * #1*WHERE*REQUEST_CHANNEL*LUX##
         */
        if (msg.toString().indexOf("*6*") != -1) {
            channelID = CHANNEL_MOTION_DETECTOR_VALUE;
            value = Integer.parseInt(msg.toString().substring(
                    msg.toString().indexOf("*" + REQUEST_CHANNEL + "*") + REQUEST_CHANNEL.length() + 2,
                    msg.toString().indexOf(OpenMessage.FRAME_END)));
            updateState(channelID, new DecimalType(value));
        } else {
            channelID = CHANNEL_MOTION_DETECTOR_SWITCH;
            updateState(channelID, OnOffType.ON);
            ScheduleToOff(channelID);
        }
    }

    /**
     * Schedule to OFF
     *
     * @param channel
     **/
    private void ScheduleToOff(String channel) {
        scheduler.schedule(() -> {
            logger.debug(
                    "==OWN:MotionDetectorHandler== ScheduleReleased() # " + deviceWhere + " sending virtual UnDef...");
            updateState(channel, OnOffType.OFF);
        }, SCHEDULE_DELAY, TimeUnit.MILLISECONDS);
    }

    /**
     * Request value lux
     *
     * example OWN
     * *#1*03#4#01*6##
     * *#1*WHERE*REQUEST_CHANNEL##
     */
    private void requestLux() {
        String commandOWN = OpenMessage.FRAME_START + "#1*" + deviceWhere + "*" + REQUEST_CHANNEL
                + OpenMessage.FRAME_END;
        logger.debug("==OWN:MotionDetectorHandler== requestLux() deviceWhere:{}", deviceWhere);
        bridgeHandler.gateway.send(OpenMessageFactory.parse(commandOWN));
    }

    /**
     * Returns a WHERE address (string) based on bridge type and unit (optional)
     *
     * @param unit device unit
     **/
    protected String toWhere(String unit) {
        logger.debug("==OWN:MotionDetectorHandler== toWhere(unit) ownId={}", ownId);
        if (bridgeHandler.isBusGateway()) {
            return deviceWhere;
        } else {
            return deviceWhere + unit;
        }
    }

    /**
     * Returns a WHERE address based on channel
     *
     * @param channel channel
     **/
    protected String toWhere(ChannelUID channel) {
        logger.debug("==OWN:CommandHandler== toWhere(ChannelUID) ownId={}", ownId);
        if (bridgeHandler.isBusGateway()) {
            return deviceWhere;
        } else if (channel.getId().equals(CHANNEL_SWITCH_02)) {
            return deviceWhere + BaseOpenMessage.UNIT_02;
        } else { // CHANNEL_SWITCH_01 or other channels
            return deviceWhere + BaseOpenMessage.UNIT_01;
        }
    }

} // class
