package io;

import Tuga.TypeChecker.ConstType;
import Tuga.TypeChecker.Type;
import Tuga.TypeChecker.Value;
import vm.Instruction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ByteCode {
    public static class Pair {
        public final Value value;
        public final Instruction instruction;

        public Pair(Value value, Instruction instruction) {
            this.value = value;
            this.instruction = instruction;
        }
    }

    public static void write(byte[] bytes, String filename) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(bytes);
        }
    }

    public static byte[] read(String filename) throws IOException {
        try (FileInputStream fis = new FileInputStream(filename)) {
            byte[] data = new byte[fis.available()];
            int read = fis.read(data);
            if (read != data.length) throw new IOException("Unable to read full file.");
            return data;
        }
    }

    public static void dumpConstantPool(List<Value> constants) {
        int i = 0;
        for (Value value : constants) {
            System.out.print("[" + i + "] ");
            switch (value.type()) {
                case INT -> System.out.println(value.getInt());
                case DOUBLE -> System.out.println(value.getDouble());
                case STRING -> System.out.println(value.getString());
                case BOOL -> System.out.println(value.getBool());
                case ERROR -> System.out.println("ERROR");
                default -> System.out.println("UNKNOWN");
            }
            i++;
        }
    }

    public static void dumpInstructions(List<Instruction> code) {
        int i = 0;
        for (Instruction instr : code) {
            System.out.println("[" + i + "] " + instr);
            i++;
        }
    }

    public static void encode(String filename, List<Value> constants, List<Instruction> instructions) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename))) {
            dos.writeInt(constants.size());
            for (Value val : constants) {
                if (val.type() == Type.INT) {
                    dos.writeByte(ConstType.DOUBLE.ordinal());
                    dos.writeDouble(val.getInt());
                } else if (val.type() == Type.DOUBLE) {
                    dos.writeByte(ConstType.DOUBLE.ordinal());
                    dos.writeDouble(val.getDouble());
                } else if (val.type() == Type.STRING) {
                    dos.writeByte(ConstType.STRING.ordinal());
                    dos.writeUTF(val.getString());
                }
            }

            dos.writeInt(instructions.size());
            for (Instruction instr : instructions) {
                dos.write(instr.encode());
            }
        }
    }

    public static Pair decodeConstant(DataInputStream dis) throws IOException {
        int tag = dis.readByte();
        ConstType type = ConstType.values()[tag];
        if (type == ConstType.DOUBLE) {
            double val = dis.readDouble();
            return new Pair(new Value(Type.DOUBLE, val), null);
        } else if (type == ConstType.STRING) {
            String val = dis.readUTF();
            return new Pair(new Value(Type.STRING, val), null);
        }
        return null;
    }

    public static List<Value> decodeConstants(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<Value> constants = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            constants.add(decodeConstant(dis).value);
        }
        return constants;
    }

    public static List<Instruction> decodeInstructions(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<Instruction> instructions = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            instructions.add(Instruction.decode(dis));
        }
        return instructions;
    }
}