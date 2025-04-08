// Generated from C:/Users/alexa/IdeaProjects/Compilador Tuga/src/expr.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link exprParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface exprVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link exprParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(exprParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(exprParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AndExpr}
	 * labeled alternative in {@link exprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpr(exprParser.AndExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryNotExpr}
	 * labeled alternative in {@link exprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryNotExpr(exprParser.UnaryNotExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParentExpr}
	 * labeled alternative in {@link exprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParentExpr(exprParser.ParentExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RelationalExpr}
	 * labeled alternative in {@link exprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpr(exprParser.RelationalExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OrExpr}
	 * labeled alternative in {@link exprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpr(exprParser.OrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FalseLiteral}
	 * labeled alternative in {@link exprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalseLiteral(exprParser.FalseLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConcatExpr}
	 * labeled alternative in {@link exprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConcatExpr(exprParser.ConcatExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqualityExpr}
	 * labeled alternative in {@link exprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpr(exprParser.EqualityExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link exprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(exprParser.StringLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TrueLiteral}
	 * labeled alternative in {@link exprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrueLiteral(exprParser.TrueLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MulDivModExpr}
	 * labeled alternative in {@link exprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDivModExpr(exprParser.MulDivModExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntLiteral}
	 * labeled alternative in {@link exprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntLiteral(exprParser.IntLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DoubleLiteral}
	 * labeled alternative in {@link exprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoubleLiteral(exprParser.DoubleLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddSubExpr}
	 * labeled alternative in {@link exprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSubExpr(exprParser.AddSubExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryMinusExpr}
	 * labeled alternative in {@link exprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryMinusExpr(exprParser.UnaryMinusExprContext ctx);
}