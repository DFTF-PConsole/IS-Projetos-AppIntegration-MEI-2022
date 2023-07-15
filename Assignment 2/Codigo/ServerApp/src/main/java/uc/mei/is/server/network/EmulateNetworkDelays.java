package uc.mei.is.server.network;

import java.time.Duration;
import java.util.Random;

public class EmulateNetworkDelays {
    private static boolean active = false;
    private static long seed = 0;
    private static long minDelayMillis = 0;
    private static long maxDelayMillis = 50;
    private static Random random = null;


    public static boolean isActive() {
        return EmulateNetworkDelays.active;
    }

    public static void setActive(boolean isActive) {
        EmulateNetworkDelays.active = isActive;
    }

    public static long getSeed() {
        return EmulateNetworkDelays.seed;
    }

    public static void setSeed(long seed) {
        EmulateNetworkDelays.seed = seed;
    }

    public static long getMinDelayMillis() {
        return EmulateNetworkDelays.minDelayMillis;
    }

    public static void setMinDelayMillis(long minDelayMillis) {
        EmulateNetworkDelays.minDelayMillis = minDelayMillis;
    }

    public static long getMaxDelayMillis() {
        return EmulateNetworkDelays.maxDelayMillis;
    }

    public static void setMaxDelayMillis(long maxDelayMillis) {
        EmulateNetworkDelays.maxDelayMillis = maxDelayMillis;
    }
    
    public static Duration emulate() {
        if (EmulateNetworkDelays.random == null) {
            EmulateNetworkDelays.random = new Random(getSeed());
        }
        return isActive() ? Duration.ofMillis((EmulateNetworkDelays.random.nextLong(getMaxDelayMillis()+1-getMinDelayMillis()) + getMinDelayMillis())) : Duration.ofMillis(0);
    }
}
