package ru.sid.izk.accelerometer.frames;

import ru.sid.izk.accelerometer.connection.MasterModbus;
import ru.sid.izk.accelerometer.connection.Terminal;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class AccelerometerWindow extends JFrame {

    private JPanel accelWindow;

    public AccelerometerWindow(Terminal terminal, MasterModbus masterModbus){

        initAccelWindow();

    }

    private void initAccelWindow(){
        setContentPane(accelWindow);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //setSize(350, 200);
        setTitle("Настройки акселерометра");
        File file = new File("icon.png");
        try {
            setIconImage(ImageIO.read(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
