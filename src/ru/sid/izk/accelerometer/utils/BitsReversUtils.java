package ru.sid.izk.accelerometer.utils;

public final class BitsReversUtils {
    public static String bitsReader(String reversBits){
        StringBuilder bits = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            if(reversBits.length() >i) bits.insert(0,reversBits.charAt(i));
            else  bits.append("0");
        }
        return bits.toString();
    }
}
