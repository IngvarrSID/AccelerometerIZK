package ru.sid.izk.accelerometer.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class DigitFilter extends DocumentFilter {

    private final int count;
    private static final String DIGITS = "\\d+";

    public DigitFilter(int count) {
        this.count = count;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string.matches(DIGITS)) super.insertString(fb, offset, string, attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text.matches(DIGITS) && offset < count) super.replace(fb, offset, length, text, attrs);
    }
}
