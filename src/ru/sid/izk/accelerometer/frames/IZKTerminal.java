package ru.sid.izk.accelerometer.frames;

import jssc.SerialPortList;
import ru.sid.izk.accelerometer.connection.MasterModbus;
import ru.sid.izk.accelerometer.connection.Terminal;
import ru.sid.izk.accelerometer.utils.DigitFilter;
import ru.sid.izk.accelerometer.utils.Settings;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class IZKTerminal extends JFrame {

    private JComboBox<String> comboBoxCOM;
    private JComboBox<String> comboBoxBound;
    private JFormattedTextField IZKCOMAddressField;
    private JButton oKButton;
    private JPanel terminalPanel;
    private final String[] portNames = SerialPortList.getPortNames();
    private final String[] bounds = {"4800", "9600", "14400", "19200", "38400", "57600", "115200"};
    private String comName;
    private String bound;


    public IZKTerminal() {
        initTerminalWindow();
        for (String s : portNames) {
            comboBoxCOM.addItem(s);
        }
        for (String s : bounds) {
            comboBoxBound.addItem(s);
        }
        PlainDocument doc = (PlainDocument) IZKCOMAddressField.getDocument();
        doc.setDocumentFilter(new DigitFilter(3));
        try {
            initTerminalSettings();
        } catch (IOException e) {
            e.printStackTrace();

        }
        comboBoxCOM.addActionListener(new ActionListenerCom());
        comboBoxBound.addActionListener(new ActionListenerBound());
        oKButton.addActionListener(new ActionListenerButton());
    }

    private void initTerminalWindow() {
        setContentPane(terminalPanel);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(350, 200);
        setTitle("Терминал");
        File file = new File("icon.png");
        try {
            setIconImage(ImageIO.read(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initTerminalSettings() throws IOException {
        if (Settings.propertiesFileExists()) {
            final Settings settings = new Settings();
            final String currentCOM = settings.getComPort();
            final String currentBound = settings.getBoundRate();
            final String currentAddress = settings.getId();
            for (int i = 0; i < portNames.length; i++) {
                if (portNames[i].equals(currentCOM)) {
                    comName = currentCOM;
                    comboBoxCOM.setSelectedIndex(i);
                    break;
                } else {
                    comName = portNames[0];
                    comboBoxCOM.setSelectedIndex(0);
                }
            }
            for (int i = 0; i < bounds.length; i++) {
                if (bounds[i].equals(currentBound)) {
                    bound = currentBound;
                    comboBoxBound.setSelectedIndex(i);
                } else {
                    bound = bounds[3];
                    comboBoxBound.setSelectedIndex(3);
                }
            }
            IZKCOMAddressField.setText(currentAddress);
        } else {
            comName = portNames[0];
            comboBoxCOM.setSelectedIndex(0);
            bound = bounds[3];
            comboBoxBound.setSelectedIndex(3);
            IZKCOMAddressField.setText("80");
        }
    }

    class ActionListenerCom implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = ((JComboBox<?>) e.getSource()).getSelectedIndex();
            comName = portNames[i];
        }
    }

    class ActionListenerBound implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = ((JComboBox<?>) e.getSource()).getSelectedIndex();
            bound = bounds[i];
        }
    }

    class ActionListenerButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            final Settings settings;
            try {
                if (!Settings.propertiesFileExists()) {
                    settings = new Settings(comName, bound, IZKCOMAddressField.getText(), String.format("%s/Documents/Technosensor/ConfigSU5DV/Archive", System.getProperty("user.home")));
                } else {
                    settings = new Settings();
                    settings.setComPort(comName);
                    settings.setBoundRate(bound);
                    settings.setId(IZKCOMAddressField.getText());
                }
                settings.storeProperties("terminal settings");
            } catch (Exception q) {
                q.printStackTrace();
            }
            Terminal terminal = new Terminal(comName, bound);
            MasterModbus masterModbus = new MasterModbus(terminal, Integer.parseInt(IZKCOMAddressField.getText()));
            dispose();
            new AccelerometerWindow(terminal, masterModbus);
        }
    }
}