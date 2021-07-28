package ru.sid.izk.accelerometer.utils;

public final class FromHexUtils {

    public static String hexStringFromFloat(float f) {
        String sF = String.format("0x%8s", Integer.toHexString(Float.floatToIntBits(f))).replace(' ', '0');
        return  sF.replace("0x", "");
    }
    public static String hexStringFromField(String field){
        String s;
        if (field.contains(","))
            s = field.replace(",", ".");
        else
            s = field;
        String sF = hexStringFromFloat(Float.parseFloat(s));
        return sF.replace("0x", "");
    }
}