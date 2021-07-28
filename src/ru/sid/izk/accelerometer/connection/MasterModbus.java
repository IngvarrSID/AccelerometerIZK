package ru.sid.izk.accelerometer.connection;

import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;


public class MasterModbus {

    private ModbusMaster modbusMaster;
    private final int id;
    private final Terminal terminal;

    public MasterModbus(Terminal terminal, int id) {
        this.id = id;
        this.terminal = terminal;
        try {
            modbusMaster = ModbusMasterFactory.createModbusMasterASCII(terminal.getSp());
            modbusMaster.connect();
        } catch (Exception e) {
            e.printStackTrace();
            terminal.setError(true);
        }
    }


    public ModbusMaster getModbusMaster() {
        return modbusMaster;
    }

    public int getId() {
        return id;
    }

    public void disconnect() {
        try {
            modbusMaster.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Terminal getTerminal() {
        return terminal;
    }
}
