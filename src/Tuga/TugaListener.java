package Tuga;
import org.antlr.v4.runtime.tree.ParseTreeListener;

public interface TugaListener extends ParseTreeListener {
	void enterTuga(TugaParser.TugaContext ctx);
	void exitTuga(TugaParser.TugaContext ctx);
	void enterPrintInst(TugaParser.PrintInstContext ctx);
	void exitPrintInst(TugaParser.PrintInstContext ctx);
	void enterEqualsOp(TugaParser.EqualsOpContext ctx);
	void exitEqualsOp(TugaParser.EqualsOpContext ctx);
	void enterOrOp(TugaParser.OrOpContext ctx);
	void exitOrOp(TugaParser.OrOpContext ctx);
	void enterMultDivOp(TugaParser.MultDivOpContext ctx);
	void exitMultDivOp(TugaParser.MultDivOpContext ctx);
	void enterSumSubOp(TugaParser.SumSubOpContext ctx);
	void exitSumSubOp(TugaParser.SumSubOpContext ctx);
	void enterLogicNegateOp(TugaParser.LogicNegateOpContext ctx);
	void exitLogicNegateOp(TugaParser.LogicNegateOpContext ctx);
	void enterLiteralExpr(TugaParser.LiteralExprContext ctx);
	void exitLiteralExpr(TugaParser.LiteralExprContext ctx);
	void enterRelOp(TugaParser.RelOpContext ctx);
	void exitRelOp(TugaParser.RelOpContext ctx);
	void enterParenExpr(TugaParser.ParenExprContext ctx);
	void exitParenExpr(TugaParser.ParenExprContext ctx);
	void enterArithmeticNegateOp(TugaParser.ArithmeticNegateOpContext ctx);
	void exitArithmeticNegateOp(TugaParser.ArithmeticNegateOpContext ctx);
	void enterAndOp(TugaParser.AndOpContext ctx);
	void exitAndOp(TugaParser.AndOpContext ctx);
	void enterInt(TugaParser.IntContext ctx);
	void exitInt(TugaParser.IntContext ctx);
	void enterDouble(TugaParser.DoubleContext ctx);
	void exitDouble(TugaParser.DoubleContext ctx);
	void enterString(TugaParser.StringContext ctx);
	void exitString(TugaParser.StringContext ctx);
	void enterTrue(TugaParser.TrueContext ctx);
	void exitTrue(TugaParser.TrueContext ctx);
	void enterFalse(TugaParser.FalseContext ctx);
	void exitFalse(TugaParser.FalseContext ctx);
}
