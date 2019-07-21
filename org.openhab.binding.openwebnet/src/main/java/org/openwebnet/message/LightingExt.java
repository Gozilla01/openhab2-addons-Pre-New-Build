package org.openwebnet.message;

import java.util.Arrays;

import org.openwebnet.OpenDeviceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LightingExt extends BaseOpenMessage {
    private static final Logger g;
    private static final int h;

    protected LightingExt(final String s) {
        super(s);
    }

    @Override
    protected final What a(final int n) {
        return WHAT.fromValue(n);
    }

    public static LightingExt requestTurnOn(final String s, final Type type) {
        // Correzione provvisoria per la problematica nel group con doppio ##
        // BaseOpenMessage.a(s, type);
        return new LightingExt(
                String.format("*%d*%d*%s##", LightingExt.h, 1, BaseOpenMessage.b(s, type).replaceFirst("\\##", "\\#")));
    }

    public static LightingExt requestTurnOff(final String s, final Type type) {
        // Correzione provvisoria per la problematica nel group con doppio ##
        // BaseOpenMessage.a(s, type);
        return new LightingExt(
                String.format("*%d*%d*%s##", LightingExt.h, 0, BaseOpenMessage.b(s, type).replaceFirst("\\##", "\\#")));
    }

    public static LightingExt requestTurnOnWhat(final String s, final int what, final Type type) {
        // Correzione provvisoria per la problematica nel group con doppio ##
        // BaseOpenMessage.a(s, type);
        return new LightingExt(String.format("*%d*%d*%s##", LightingExt.h, what,
                BaseOpenMessage.b(s, type).replaceFirst("\\##", "\\#")));
    }

    @Override
    public OpenDeviceType detectDeviceType() {
        if (this.isCommand()) {
            final What what;
            OpenDeviceType openDeviceType;
            if ((what = this.getWhat()) == WHAT.OFF || what == WHAT.ON || what == WHAT.MOVEMENT_DETECTED
                    || what == WHAT.END_MOVEMENT_DETECTED) {
                openDeviceType = OpenDeviceType.SCS_ON_OFF_SWITCH;
            } else {
                openDeviceType = OpenDeviceType.SCS_DIMMER_SWITCH;
            }
            return openDeviceType;
        }
        return null;
    }

    static {
        g = LoggerFactory.getLogger(LightingExt.class);
        h = Who.LIGHTING.value();
    }

    public enum WHAT implements What {
        OFF(Integer.valueOf(0)),
        ON(Integer.valueOf(1)),
        DIMMER_20(Integer.valueOf(2)),
        DIMMER_30(Integer.valueOf(3)),
        DIMMER_40(Integer.valueOf(4)),
        DIMMER_50(Integer.valueOf(5)),
        DIMMER_60(Integer.valueOf(6)),
        DIMMER_70(Integer.valueOf(7)),
        DIMMER_80(Integer.valueOf(8)),
        DIMMER_90(Integer.valueOf(9)),
        DIMMER_100(Integer.valueOf(10)),
        TIMER_1_MIN(Integer.valueOf(2)),
        TIMER_2_MIN(Integer.valueOf(12)),
        TIMER_3_MIN(Integer.valueOf(13)),
        TIMER_4_MIN(Integer.valueOf(14)),
        TIMER_5_MIN(Integer.valueOf(15)),
        TIMER_15_MIN(Integer.valueOf(16)),
        TIMER_30_SEC(Integer.valueOf(7)),
        TIMER_05_SEC(Integer.valueOf(18)),
        BLINKING_05_SEC(Integer.valueOf(20)),
        BLINKING_1_SEC(Integer.valueOf(21)),
        BLINKING_1_5_SEC(Integer.valueOf(22)),
        BLINKING_2_SEC(Integer.valueOf(23)),
        BLINKING_2_5_SEC(Integer.valueOf(24)),
        BLINKING_3_SEC(Integer.valueOf(25)),
        BLINKING_3_5_SEC(Integer.valueOf(26)),
        BLINKING_4_SEC(Integer.valueOf(27)),
        BLINKING_4_5_SEC(Integer.valueOf(28)),
        BLINKING_5_SEC(Integer.valueOf(29)),
        DIMMER_UP(Integer.valueOf(30)),
        DIMMER_DOWN(Integer.valueOf(31)),
        DIMMER_TOGGLE(Integer.valueOf(32)),
        MOVEMENT_DETECTED(Integer.valueOf(34)),
        END_MOVEMENT_DETECTED(Integer.valueOf(39)),
        COMMAND_TRANSLATION(Integer.valueOf(1000));

        private final Integer a;

        private WHAT(final Integer a) {
            this.a = a;
        }

        public static WHAT fromValue(final Integer n) {
            return Arrays.stream(values()).filter(what -> n == what.a).findFirst().orElseGet(null);
        }

        @Override
        public final Integer value() {
            return this.a;
        }
    }
}