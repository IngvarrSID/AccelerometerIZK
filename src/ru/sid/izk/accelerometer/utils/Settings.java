package ru.sid.izk.accelerometer.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class Settings {

    private String comPort;
    private String boundRate;
    private String id;
    private String path;
    private static final String settingsPath = String.format("%s/Documents/Technosensor/Accelerometer", System.getProperty("user.home"));

    public Settings() {
        try (FileInputStream in = new FileInputStream(settingsPath + "/settings.properties")) {
            final Properties properties = new Properties();
            properties.load(in);
            this.comPort = properties.getProperty("ComPort");
            this.boundRate = properties.getProperty("BoundRate");
            this.id = properties.getProperty("Id");
            this.path = properties.getProperty("Path");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public Settings(final String comPort, final String boundRate, final String id, final String path) {
        this.comPort = comPort;
        this.boundRate = boundRate;
        this.id = id;
        this.path = path;
    }

    public Settings(final String path) {
        this.path=path;
    }

    public static boolean propertiesFileExists() {
        File f = new File(settingsPath + "/settings.properties");
        return f.exists();
    }

    public String getComPort() {
        return comPort;
    }

    public String getBoundRate() {
        return boundRate;
    }

    public String getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public void setComPort(String comPort) {
        this.comPort = comPort;
    }

    public void setBoundRate(String boundRate) {
        this.boundRate = boundRate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void storeProperties(final String comments) {

        File dir = new File(settingsPath);
        if (dir.mkdirs()) System.out.println("Путь создан");
        else System.out.println("Путь не создан");

        try (FileOutputStream out = new FileOutputStream(settingsPath + "/settings.properties")) {
            final Properties properties = new Properties();
            Optional.ofNullable(comPort).ifPresent(v -> properties.setProperty("ComPort", comPort));
            Optional.ofNullable(boundRate).ifPresent(v -> properties.setProperty("BoundRate", boundRate));
            Optional.ofNullable(id).ifPresent(v -> properties.setProperty("Id", id));
            Optional.ofNullable(path).ifPresent(v -> properties.setProperty("Path", path));
            properties.store(out, comments);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}