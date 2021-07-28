package ru.sid.izk.accelerometer.connection;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;

public class ModbusReader {

    private final ModbusMaster modbusMaster;
    private final int slaveID;


    public ModbusReader(ModbusMaster m, int slaveID) {
        this.modbusMaster = m;
        this.slaveID = slaveID;
    }

    public int[] readRegisters(int offset, int quantity, int count) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {
        int[] allRegisters = new int[quantity * count];
        int indent = offset;
        for (int i = 0; i < count; i++) {
            int[] registers = modbusMaster.readInputRegisters(slaveID,offset,quantity);
            System.arraycopy(registers, 0, allRegisters, offset - indent, offset + registers.length - offset);
            offset = offset+quantity;
        }
        return allRegisters;
    }

    public void writeModeRegister(int offset, int register) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {

        modbusMaster.writeSingleRegister(slaveID, offset, register);

    }

    public String changeChannel(int channel) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {

           if (channel>0 && channel <5) {
               modbusMaster.writeSingleRegister(slaveID, 0, channel + 4);
               modbusMaster.writeSingleRegister(slaveID, 1, channel - 1);
               return String.format("Выбран %d измерительный канал", channel);
           }
           else return "Что то пошло не так";
        }

    public void writeRegister(int offset, int[] registers) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {

        modbusMaster.writeMultipleRegisters(slaveID, offset, registers);

    }

    public int[] readHoldingsRegisters(int offset, int quantity, int count) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {
        int[] allRegisters = new int[quantity * count];
        int indent = offset;
        for (int i = 0; i < count; i++) {
            int[] registers = modbusMaster.readHoldingRegisters(slaveID,offset,quantity);
            System.arraycopy(registers, 0, allRegisters, offset - indent, offset + registers.length - offset);
            offset = offset+quantity;
        }
        return allRegisters;
    }

    public boolean[] coilsReader(int offset,int quantity) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {
        return modbusMaster.readCoils(slaveID,offset,quantity);
    }
    public boolean[] discreteReader(int offset,int quantity) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {
        return modbusMaster.readDiscreteInputs(slaveID,offset,quantity);
    }

    public void  writeCoil(int offset,boolean bool) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {
        modbusMaster.writeSingleCoil(slaveID,offset,bool);
    }

    public void writeMultipleRegisters(int offset,int[] registers) throws ModbusProtocolException, ModbusNumberException, ModbusIOException {
        modbusMaster.writeMultipleRegisters(slaveID,offset,registers);
    }
}