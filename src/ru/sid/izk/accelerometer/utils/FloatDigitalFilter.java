package ru.sid.izk.accelerometer.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class FloatDigitalFilter extends DocumentFilter {
    private final String pattern;

    public FloatDigitalFilter(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        String numS = fb.getDocument().getText(0,fb.getDocument().getLength());
        numS += string;
        if (numS.matches(pattern)) super.insertString(fb, offset, string, attr);

    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        String numS = fb.getDocument().getText(0, fb.getDocument().getLength());
        numS += text;
        if (numS.matches(pattern))
            super.replace(fb, offset, length, text, attrs);
    }




}
