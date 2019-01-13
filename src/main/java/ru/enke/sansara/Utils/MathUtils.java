package ru.enke.sansara.Utils;

public final class MathUtils {

    public static float clamp(float value, float min, float max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }
}