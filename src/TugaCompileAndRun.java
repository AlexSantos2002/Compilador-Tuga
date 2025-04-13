package Tuga;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.FileInputStream;
import java.io.InputStream;

import codegen.TugaEmitter;
import io.ByteCode;
import vm.VirtualMachine;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

public class TugaCompileAndRun {

    private static final String BYTECODE_FILENAME = "bytecodes.bc";
    private static final String FLAG_TRACE = "-trace";
    private static final String FLAG_ASM = "-asm";
    private static final String FLAG_DIRECT = "-direct";

    private static boolean traceEnabled = false;
    private static boolean asmOutput = false;
    private static boolean runDirectly = false;
    private static boolean dumpOutput = true;
    //private static boolean showLexerErrors = false; podera ser necessrio mais tarde
    //private static boolean showParserErrors = false; podera ser necessario mais tarde
    private static boolean showTypeErrors = false;

    public static void main(String[] args) throws Exception {
        String sourceFile = null;
        if (args.length > 0) {
            sourceFile = args[0];
            for (int i = 1; i < args.length; i++) {
                switch (args[i]) {
                    case FLAG_TRACE -> traceEnabled = true;
                    case FLAG_ASM -> asmOutput = true;
                    case FLAG_DIRECT -> runDirectly = true;
                }
            }
        }

        try (InputStream input = (sourceFile != null) ? new FileInputStream(sourceFile) : System.in) {
            CharStream codeStream = CharStreams.fromStream(input);

            TugaLexer lexer = new TugaLexer(codeStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            TugaParser parser = new TugaParser(tokens);
            ParseTree tree = parser.program();

            TypeChecker checker = new TypeChecker(showTypeErrors);
            boolean valid = checker.visit(tree);
            if (!valid) {
                System.out.println("Input has type checking errors");
                return;
            }

            ParseTreeProperty<String> typeInfo = new ParseTreeProperty<>();
            TugaEmitter emitter = new TugaEmitter(typeInfo);
            emitter.visit(tree);

            if (asmOutput) {
                emitter.showAssembly();
            }

            byte[] bytecode = emitter.getBytecode();
            if (!runDirectly) {
                ByteCode.write(bytecode, BYTECODE_FILENAME);
                bytecode = ByteCode.read(BYTECODE_FILENAME);
            }

            VirtualMachine vm = new VirtualMachine(bytecode, traceEnabled);
            if (dumpOutput) {
                printVMState(vm);
            }
            vm.run();

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private static void printVMState(VirtualMachine vm) {
        System.out.println("*** Constant pool ***");
        vm.dumpConstantPool();
        System.out.println("*** Instructions ***");
        vm.dumpInstructions();
        System.out.println("*** VM output ***");
    }
}