package Tuga;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

public class TypeChecker extends TugaBaseVisitor<String> {

	private boolean hasError = false;
	private final boolean showErrors;

	public TypeChecker(boolean showErrors) {
		this.showErrors = showErrors;
	}

	public boolean visitRoot(TugaParser.ProgramContext ctx) {
		visitChildren(ctx);
		return !hasError;
	}

	@Override
	public String visitStatement(TugaParser.StatementContext ctx) {
		visit(ctx.expr());
		return null;
	}

	@Override
	public String visitIntLiteral(TugaParser.IntLiteralContext ctx) {
		return "int";
	}

	@Override
	public String visitDoubleLiteral(TugaParser.DoubleLiteralContext ctx) {
		return "double";
	}

	@Override
	public String visitStringLiteral(TugaParser.StringLiteralContext ctx) {
		return "string";
	}

	@Override
	public String visitTrueLiteral(TugaParser.TrueLiteralContext ctx) {
		return "boolean";
	}

	@Override
	public String visitFalseLiteral(TugaParser.FalseLiteralContext ctx) {
		return "boolean";
	}

	@Override
	public String visitUnaryMinusExpr(TugaParser.UnaryMinusExprContext ctx) {
		String exprType = visit(ctx.expr());
		if (exprType.equals("int") || exprType.equals("double")) return exprType;
		reportError(ctx.getStart().getLine(), "Operador '-' não pode ser aplicado a '" + exprType + "'");
		return "error";
	}

	@Override
	public String visitUnaryNotExpr(TugaParser.UnaryNotExprContext ctx) {
		String exprType = visit(ctx.expr());
		if (exprType.equals("boolean")) return "boolean";
		reportError(ctx.getStart().getLine(), "Operador 'nao' não pode ser aplicado a '" + exprType + "'");
		return "error";
	}

	@Override
	public String visitParentExpr(TugaParser.ParentExprContext ctx) {
		return visit(ctx.expr());
	}

	@Override
	public String visitAddSubExpr(TugaParser.AddSubExprContext ctx) {
		return checkArithmeticBinary(ctx, ctx.expr(0), ctx.expr(1), ctx.op.getText());
	}

	@Override
	public String visitMulDivModExpr(TugaParser.MulDivModExprContext ctx) {
		return checkArithmeticBinary(ctx, ctx.expr(0), ctx.expr(1), ctx.op.getText());
	}

	private String checkArithmeticBinary(ParserRuleContext ctx, TugaParser.ExprContext left, TugaParser.ExprContext right, String op) {
		String l = visit(left);
		String r = visit(right);
		if (l.equals("int") && r.equals("int")) return "int";
		if ((l.equals("int") && r.equals("double")) || (l.equals("double") && r.equals("int")) || (l.equals("double") && r.equals("double"))) return "double";
		reportError(ctx.getStart().getLine(), "Operador '" + op + "' inválido entre '" + l + "' e '" + r + "'");
		return "error";
	}

	@Override
	public String visitRelationalExpr(TugaParser.RelationalExprContext ctx) {
		String l = visit(ctx.expr(0));
		String r = visit(ctx.expr(1));
		if ((l.equals("int") || l.equals("double")) && (r.equals("int") || r.equals("double"))) return "boolean";
		reportError(ctx.getStart().getLine(), "Comparação relacional inválida entre '" + l + "' e '" + r + "'");
		return "error";
	}

	@Override
	public String visitEqualityExpr(TugaParser.EqualityExprContext ctx) {
		String l = visit(ctx.expr(0));
		String r = visit(ctx.expr(1));
		if (l.equals(r) || (isNumeric(l) && isNumeric(r))) return "boolean";
		reportError(ctx.getStart().getLine(), "Comparação de igualdade inválida entre '" + l + "' e '" + r + "'");
		return "error";
	}

	@Override
	public String visitAndExpr(TugaParser.AndExprContext ctx) {
		String l = visit(ctx.expr(0));
		String r = visit(ctx.expr(1));
		if (l.equals("boolean") && r.equals("boolean")) return "boolean";
		reportError(ctx.getStart().getLine(), "Operador 'e' exige booleanos, mas recebeu '" + l + "' e '" + r + "'");
		return "error";
	}

	@Override
	public String visitOrExpr(TugaParser.OrExprContext ctx) {
		String l = visit(ctx.expr(0));
		String r = visit(ctx.expr(1));
		if (l.equals("boolean") && r.equals("boolean")) return "boolean";
		reportError(ctx.getStart().getLine(), "Operador 'ou' exige booleanos, mas recebeu '" + l + "' e '" + r + "'");
		return "error";
	}

	@Override
	public String visitConcatExpr(TugaParser.ConcatExprContext ctx) {
		String l = visit(ctx.expr(0));
		String r = visit(ctx.expr(1));
		if (l.equals("string") || r.equals("string") || isPrimitive(l) || isPrimitive(r)) return "string";
		reportError(ctx.getStart().getLine(), "Concatenação inválida entre '" + l + "' e '" + r + "'");
		return "error";
	}

	private boolean isNumeric(String type) {
		return type.equals("int") || type.equals("double");
	}

	private boolean isPrimitive(String type) {
		return type.equals("int") || type.equals("double") || type.equals("boolean") || type.equals("string");
	}

	private void reportError(int line, String msg) {
		hasError = true;
		if (showErrors) {
			System.err.println("Type error (line " + line + "): " + msg);
		}
	}

	public enum Type {
		INT, DOUBLE, STRING, BOOL, ERROR;
		public boolean isNumerical() {
			return this == INT || this == DOUBLE;
		}
	}

	public enum ConstType {
		DOUBLE, STRING
	}

	public static class Value {
		private final Type type;
		private final Object value;

		public Value(Type type, Object value) {
			this.type = type;
			this.value = value;
		}

		public Type type() {
			return this.type;
		}

		public int getInt() {
			if (this.type != Type.INT) throw new IllegalStateException("Not an INT: " + this.type);
			return (Integer) value;
		}

		public double getDouble() {
			if (this.type != Type.DOUBLE) throw new IllegalStateException("Not a DOUBLE: " + this.type);
			return (Double) value;
		}

		public String getString() {
			if (this.type != Type.STRING) throw new IllegalStateException("Not a STRING: " + this.type);
			return (String) value;
		}

		public boolean getBool() {
			if (this.type != Type.BOOL) throw new IllegalStateException("Not a BOOL: " + this.type);
			return (Boolean) value;
		}

		public String getErrorValue() {
			if (this.type != Type.ERROR) throw new IllegalStateException("Not an ERROR: " + this.type);
			return (String) value;
		}

		@Override
		public String toString() {
			return switch (this.type) {
				case INT -> String.valueOf(getInt());
				case DOUBLE -> String.valueOf(getDouble());
				case STRING -> "\"" + getString() + "\"";
				case BOOL -> String.valueOf(getBool());
				case ERROR -> "ERROR";
				default -> "UNKNOWN";
			};
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Value that = (Value) o;
			if (this.type != that.type) return false;
			return switch (this.type) {
				case INT -> getInt() == that.getInt();
				case DOUBLE -> getDouble() == that.getDouble();
				case STRING -> getString().equals(that.getString());
				case BOOL -> getBool() == that.getBool();
				default -> false;
			};
		}

		@Override
		public int hashCode() {
			return switch (this.type) {
				case INT -> 137 * Integer.hashCode(getInt());
				case DOUBLE -> 241 * Double.hashCode(getDouble());
				case STRING -> 487 * getString().hashCode();
				case BOOL -> 1031 * Boolean.hashCode(getBool());
				default -> 0;
			};
		}
	}
}