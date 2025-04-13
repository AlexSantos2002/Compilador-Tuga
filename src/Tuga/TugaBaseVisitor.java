package Tuga;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

public class TugaBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements TugaVisitor<T> {
    @Override public T visitTuga(TugaParser.TugaContext ctx) { return visitChildren(ctx); }
    @Override public T visitPrintInst(TugaParser.PrintInstContext ctx) { return visitChildren(ctx); }
    @Override public T visitEqualsOp(TugaParser.EqualsOpContext ctx) { return visitChildren(ctx); }
    @Override public T visitOrOp(TugaParser.OrOpContext ctx) { return visitChildren(ctx); }
    @Override public T visitMultDivOp(TugaParser.MultDivOpContext ctx) { return visitChildren(ctx); }
    @Override public T visitSumSubOp(TugaParser.SumSubOpContext ctx) { return visitChildren(ctx); }
    @Override public T visitLogicNegateOp(TugaParser.LogicNegateOpContext ctx) { return visitChildren(ctx); }
    @Override public T visitLiteralExpr(TugaParser.LiteralExprContext ctx) { return visitChildren(ctx); }
    @Override public T visitRelOp(TugaParser.RelOpContext ctx) { return visitChildren(ctx); }
    @Override public T visitParenExpr(TugaParser.ParenExprContext ctx) { return visitChildren(ctx); }
    @Override public T visitArithmeticNegateOp(TugaParser.ArithmeticNegateOpContext ctx) { return visitChildren(ctx); }
    @Override public T visitAndOp(TugaParser.AndOpContext ctx) { return visitChildren(ctx); }
    @Override public T visitInt(TugaParser.IntContext ctx) { return visitChildren(ctx); }
    @Override public T visitDouble(TugaParser.DoubleContext ctx) { return visitChildren(ctx); }
    @Override public T visitString(TugaParser.StringContext ctx) { return visitChildren(ctx); }
    @Override public T visitTrue(TugaParser.TrueContext ctx) { return visitChildren(ctx); }
    @Override public T visitFalse(TugaParser.FalseContext ctx) { return visitChildren(ctx); }
}