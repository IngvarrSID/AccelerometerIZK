package ru.sid.izk.accelerometer.utils;

import javax.swing.*;
import javax.swing.text.PlainDocument;

public final class FilterUtils {
    public static void digitFilter(JTextField textField, int count) {
        PlainDocument doc = (PlainDocument) textField.getDocument();
        doc.setDocumentFilter(new DigitFilter(count));
    }

    public static void floatFilter(JTextField textField, String pattern) {
        PlainDocument doc = (PlainDocument) textField.getDocument();
        doc.setDocumentFilter(new FloatDigitalFilter(pattern));
    }
}
