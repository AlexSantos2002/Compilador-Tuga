package Tuga;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

@SuppressWarnings("CheckReturnValue")
public class TugaBaseListener implements TugaListener {
    @Override public void enterTuga(TugaParser.TugaContext ctx) { }
    @Override public void exitTuga(TugaParser.TugaContext ctx) { }
    @Override public void enterPrintInst(TugaParser.PrintInstContext ctx) { }
    @Override public void exitPrintInst(TugaParser.PrintInstContext ctx) { }
    @Override public void enterEqualsOp(TugaParser.EqualsOpContext ctx) { }
    @Override public void exitEqualsOp(TugaParser.EqualsOpContext ctx) { }
    @Override public void enterOrOp(TugaParser.OrOpContext ctx) { }
    @Override public void exitOrOp(TugaParser.OrOpContext ctx) { }
    @Override public void enterMultDivOp(TugaParser.MultDivOpContext ctx) { }
    @Override public void exitMultDivOp(TugaParser.MultDivOpContext ctx) { }
    @Override public void enterSumSubOp(TugaParser.SumSubOpContext ctx) { }
    @Override public void exitSumSubOp(TugaParser.SumSubOpContext ctx) { }
    @Override public void enterLogicNegateOp(TugaParser.LogicNegateOpContext ctx) { }
    @Override public void exitLogicNegateOp(TugaParser.LogicNegateOpContext ctx) { }
    @Override public void enterLiteralExpr(TugaParser.LiteralExprContext ctx) { }
    @Override public void exitLiteralExpr(TugaParser.LiteralExprContext ctx) { }
    @Override public void enterRelOp(TugaParser.RelOpContext ctx) { }
    @Override public void exitRelOp(TugaParser.RelOpContext ctx) { }
    @Override public void enterParenExpr(TugaParser.ParenExprContext ctx) { }
    @Override public void exitParenExpr(TugaParser.ParenExprContext ctx) { }
    @Override public void enterArithmeticNegateOp(TugaParser.ArithmeticNegateOpContext ctx) { }
    @Override public void exitArithmeticNegateOp(TugaParser.ArithmeticNegateOpContext ctx) { }
    @Override public void enterAndOp(TugaParser.AndOpContext ctx) { }
    @Override public void exitAndOp(TugaParser.AndOpContext ctx) { }
    @Override public void enterInt(TugaParser.IntContext ctx) { }
    @Override public void exitInt(TugaParser.IntContext ctx) { }
    @Override public void enterDouble(TugaParser.DoubleContext ctx) { }
    @Override public void exitDouble(TugaParser.DoubleContext ctx) { }
    @Override public void enterString(TugaParser.StringContext ctx) { }
    @Override public void exitString(TugaParser.StringContext ctx) { }
    @Override public void enterTrue(TugaParser.TrueContext ctx) { }
    @Override public void exitTrue(TugaParser.TrueContext ctx) { }
    @Override public void enterFalse(TugaParser.FalseContext ctx) { }
    @Override public void exitFalse(TugaParser.FalseContext ctx) { }
    @Override public void enterEveryRule(ParserRuleContext ctx) { }
    @Override public void exitEveryRule(ParserRuleContext ctx) { }
    @Override public void visitTerminal(TerminalNode node) { }
    @Override public void visitErrorNode(ErrorNode node) { }
}