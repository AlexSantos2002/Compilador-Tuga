package codegen;

import Tuga.*;
import Tuga.TypeChecker.Type;
import Tuga.TypeChecker.Value;
import Tuga.TypeChecker;
import io.ByteCode;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import vm.Instruction;
import vm.OpCode;

import java.util.ArrayList;
import java.util.HashMap;

public class TugaEmitter extends TugaBaseVisitor<Void> {

    private final ArrayList<Instruction> instructions;
    private final ArrayList<Value> constants;
    private final HashMap<Value, Integer> constantIndex;
    private final ParseTreeProperty<String> types;

    public TugaEmitter(ParseTreeProperty<String> types) {
        this.instructions = new ArrayList<>();
        this.constants = new ArrayList<>();
        this.constantIndex = new HashMap<>();
        this.types = types;
    }

    public byte[] getBytecode() {
        ByteCode encoder = new ByteCode(constants.toArray(new Value[0]), instructions.toArray(new Instruction[0]));
        return encoder.toByteArray();
    }

    public void showAssembly() {
        System.out.println("--- Assembly Output ---");
        for (int i = 0; i < instructions.size(); i++) {
            System.out.println(i + ": " + instructions.get(i));
        }
    }

    private void push(OpCode opcode, int... args) {
        instructions.add(new Instruction(opcode, args));
    }

    private int addConstant(Type type, Object value) {
        Value val = new Value(type, value);
        return constantIndex.computeIfAbsent(val, k -> {
            constants.add(k);
            return constants.size() - 1;
        });
    }

    private void autoConvertDouble(TugaParser.ExprContext left, TugaParser.ExprContext right) {
        String leftType = types.get(left);
        String rightType = types.get(right);

        visit(left);
        if (leftType.equals("int") && rightType.equals("double")) {
            push(OpCode.itod);
            visit(right);
        } else if (leftType.equals("double") && rightType.equals("int")) {
            visit(right);
            push(OpCode.itod);
        } else {
            visit(right);
        }
    }

    private void toStringConversion(String type) {
        switch (type) {
            case "int" -> push(OpCode.itos);
            case "double" -> push(OpCode.dtos);
            case "boolean" -> push(OpCode.btos);
        }
    }

    @Override
    public Void visitPrintInst(TugaParser.PrintInstContext ctx) {
        visit(ctx.expr());
        String type = types.get(ctx.expr());
        switch (type) {
            case "int" -> push(OpCode.iprint);
            case "double" -> push(OpCode.dprint);
            case "boolean" -> push(OpCode.bprint);
            case "string" -> push(OpCode.sprint);
            default -> throw new RuntimeException("Cannot print unknown type: " + type);
        }
        return null;
    }

    @Override
    public Void visitAddSubExpr(TugaParser.AddSubExprContext ctx) {
        String left = types.get(ctx.expr(0));
        String right = types.get(ctx.expr(1));

        if ((left.equals("string") || right.equals("string")) && ctx.op.getText().equals("+")) {
            visit(ctx.expr(0));
            toStringConversion(left);
            visit(ctx.expr(1));
            toStringConversion(right);
            push(OpCode.sconcat);
        } else {
            autoConvertDouble(ctx.expr(0), ctx.expr(1));
            push(resolveAddSubOp(ctx.op.getText(), left, right));
        }
        return null;
    }

    @Override
    public Void visitMulDivModExpr(TugaParser.MulDivModExprContext ctx) {
        autoConvertDouble(ctx.expr(0), ctx.expr(1));
        push(resolveMulDivOp(ctx.op.getText(), types.get(ctx.expr(0)), types.get(ctx.expr(1))));
        return null;
    }

    @Override
    public Void visitEqualsOp(TugaParser.EqualsOpContext ctx) {
        autoConvertDouble(ctx.expr(0), ctx.expr(1));
        push(resolveEqualityOp(ctx.op.getText(), types.get(ctx.expr(0)), types.get(ctx.expr(1))));
        return null;
    }

    @Override
    public Void visitRelationalExpr(TugaParser.RelationalExprContext ctx) {
        autoConvertDouble(ctx.expr(0), ctx.expr(1));
        push(resolveRelationalOp(ctx.op.getText(), types.get(ctx.expr(0)), types.get(ctx.expr(1))));
        return null;
    }

    @Override
    public Void visitAndExpr(TugaParser.AndExprContext ctx) {
        visit(ctx.expr(0));
        visit(ctx.expr(1));
        push(OpCode.and);
        return null;
    }

    @Override
    public Void visitOrExpr(TugaParser.OrExprContext ctx) {
        visit(ctx.expr(0));
        visit(ctx.expr(1));
        push(OpCode.or);
        return null;
    }

    @Override
    public Void visitLogicNegateOp(TugaParser.LogicNegateOpContext ctx) {
        visit(ctx.expr());
        push(OpCode.not);
        return null;
    }

    @Override
    public Void visitArithmeticNegateOp(TugaParser.ArithmeticNegateOpContext ctx) {
        visit(ctx.expr());
        String type = types.get(ctx.expr());
        if (type.equals("int")) push(OpCode.iuminus);
        else if (type.equals("double")) push(OpCode.duminus);
        return null;
    }

    @Override
    public Void visitParenExpr(TugaParser.ParenExprContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Void visitTuga(TugaParser.TugaContext ctx) {
        visitChildren(ctx);
        push(OpCode.halt);
        return null;
    }

    @Override
    public Void visitInt(TugaParser.IntContext ctx) {
        int value = Integer.parseInt(ctx.INT().getText());
        push(OpCode.iconst, value);
        return null;
    }

    @Override
    public Void visitDouble(TugaParser.DoubleContext ctx) {
        double d = Double.parseDouble(ctx.DOUBLE().getText());
        int idx = addConstant(Type.DOUBLE, d);
        push(OpCode.dconst, idx);
        return null;
    }

    @Override
    public Void visitString(TugaParser.StringContext ctx) {
        String str = ctx.STRING().getText();
        String content = str.substring(1, str.length() - 1);
        int idx = addConstant(Type.STRING, content);
        push(OpCode.sconst, idx);
        return null;
    }

    @Override
    public Void visitTrue(TugaParser.TrueContext ctx) {
        push(OpCode.tconst);
        return null;
    }

    @Override
    public Void visitFalse(TugaParser.FalseContext ctx) {
        push(OpCode.fconst);
        return null;
    }

    private OpCode resolveAddSubOp(String op, String left, String right) {
        return switch (op) {
            case "+" -> (left.equals("double") || right.equals("double")) ? OpCode.dadd : OpCode.iadd;
            case "-" -> (left.equals("double") || right.equals("double")) ? OpCode.dsub : OpCode.isub;
            default -> throw new IllegalStateException("Unknown operator: " + op);
        };
    }

    private OpCode resolveMulDivOp(String op, String left, String right) {
        return switch (op) {
            case "*" -> (left.equals("double") || right.equals("double")) ? OpCode.dmult : OpCode.imult;
            case "/" -> (left.equals("double") || right.equals("double")) ? OpCode.ddiv : OpCode.idiv;
            case "%" -> OpCode.imod;
            default -> throw new IllegalStateException("Unknown operator: " + op);
        };
    }

    private OpCode resolveEqualityOp(String op, String left, String right) {
        return switch (op) {
            case "igual" -> switch (left) {
                case "double", "int" -> right.equals("double") ? OpCode.deq : OpCode.ieq;
                case "string" -> OpCode.seq;
                case "boolean" -> OpCode.beq;
                default -> throw new IllegalStateException();
            };
            case "diferente" -> switch (left) {
                case "double", "int" -> right.equals("double") ? OpCode.dneq : OpCode.ineq;
                case "string" -> OpCode.sneq;
                case "boolean" -> OpCode.bneq;
                default -> throw new IllegalStateException();
            };
            default -> throw new IllegalStateException("Unknown operator: " + op);
        };
    }

    private OpCode resolveRelationalOp(String op, String left, String right) {
        boolean isDouble = left.equals("double") || right.equals("double");
        return switch (op) {
            case "<" -> isDouble ? OpCode.dlt : OpCode.ilt;
            case "<=" -> isDouble ? OpCode.dleq : OpCode.ileq;
            case ">" -> isDouble ? OpCode.dlt : OpCode.ilt;
            case ">=" -> isDouble ? OpCode.dleq : OpCode.ileq;
            default -> throw new IllegalStateException("Unknown relational operator: " + op);
        };
    }
}
