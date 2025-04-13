package Tuga;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

public interface TugaVisitor<T> extends ParseTreeVisitor<T> {
    T visitTuga(TugaParser.TugaContext ctx);
    T visitPrintInst(TugaParser.PrintInstContext ctx);
    T visitEqualsOp(TugaParser.EqualsOpContext ctx);
    T visitOrOp(TugaParser.OrOpContext ctx);
    T visitMultDivOp(TugaParser.MultDivOpContext ctx);
    T visitSumSubOp(TugaParser.SumSubOpContext ctx);
    T visitLogicNegateOp(TugaParser.LogicNegateOpContext ctx);
    T visitLiteralExpr(TugaParser.LiteralExprContext ctx);
    T visitRelOp(TugaParser.RelOpContext ctx);
    T visitParenExpr(TugaParser.ParenExprContext ctx);
    T visitArithmeticNegateOp(TugaParser.ArithmeticNegateOpContext ctx);
    T visitAndOp(TugaParser.AndOpContext ctx);
    T visitInt(TugaParser.IntContext ctx);
    T visitDouble(TugaParser.DoubleContext ctx);
    T visitString(TugaParser.StringContext ctx);
    T visitTrue(TugaParser.TrueContext ctx);
    T visitFalse(TugaParser.FalseContext ctx);
}
