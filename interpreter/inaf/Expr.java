package inaf;

import static inaf.TokenType.*;

import java.util.List;

abstract class Expr {
	interface Visitor<R> {
		R visitNumberNode(NumberNode expr);
		R visitStringNode(StringNode expr);
		R visitLiteralNode(LiteralNode expr);
		R visitAssignNode(AssignNode expr);
		R visitAccessNode(AccessNode expr);
		R visitBinaryOpNode(BinaryOpNode expr);
		R visitUnaryOpNode(UnaryOpNode expr);
		R visitCallNode(CallNode expr);
		R visitGetNode(GetNode expr);
		R visitThisNode(ThisNode expr);
		R visitSuperNode(SuperNode expr);
	}

	abstract <R> R accept(Visitor<R> visitor);
	abstract String toString();
	final Position start;
	final Position end;

	static class NumberNode extends Expr {
		final Token token;
		final Position start;
		final Position end;

		NumberNode(Token token) {
			this.token = token;
			this.start = token.start;
			this.end = token.end;
		}

		@Override
		String toString() {
			return "" + token;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitNumberNode(this);
		}
	}

	static class StringNode extends Expr {
		final Token token;
		final Position start;
		final Position end;

		StringNode(Token token) {
			this.token = token;
			this.start = token.start;
			this.end = token.end;
		}

		@Override
		String toString() {
			return "" + token;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitStringNode(this);
		}
	}

	static class LiteralNode extends Expr {
		final Object value;

		LiteralNode(Object value) {
			this.value = value;
		}

		@Override
		String toString() {
			return "" + token;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitLiteralNode(this);
		}

  	}

	static class AssignNode extends Expr {
		final Token token;
		final Expr value;
		final Position start;
		final Position end;

		AssignNode(Token token, Expr value) {
			this.token = token;
			this.value = value;
			this.start = token.start;
			this.end = token.end;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitAssignNode(this);
		}
	}

	static class AccessNode extends Expr {
		final Token token;
		final Position start;
		final Position end;

		AccessNode(Token token) {
			this.token = token;
			this.start = token.start;
			this.end = token.end;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitAccessNode(this);
		}
	}

	static class BinaryOpNode extends Expr {
		final Token operator;
		final Expr left;
		final Expr right;
		final Position start;
		final Position end;

		BinaryOpNode(Expr left, Token operator, Expr right) {
			this.token = operator;
			this.left = left;
			this.right = right;
			this.start = token.start;
			this.end = token.end;
		}

		@Override
		String toString() {
			return left + " " + operator + " " + right;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitBinaryOpNode(this);
		}
	}

	static class UnaryOpNode extends Expr {
		final Token operator;
		final Expr node;
		final Position start;
		final Position end;

		UnaryOpNode(Token operator, Expr node) {
			this.token = token;
			this.node = node;
			this.start = token.start;
			this.end = token.end;
		}

		@Override
		String toString() {
			return operator + " " + node;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitUnaryOpNode(this);
		}
	}

	static class CallNode extends Expr {
		final Expr callee;
		final Token lparen;
		final List<Expr> arguments;
		final Token rparen;
		final Position start;
		final Position end;

		CallNode(Expr callee, Token lparen, List<Expr> arguments, Token rparen) {
			this.callee = callee;
			this.lparen = lparen;
			this.arguments = arguments;
			this.rparen = rparen;

			this.start = callee.start;

			if(arguments.size() > 0) {
				this.end = arguments.get(arguments.size() - 1).end;
			} else {
				this.end = callee.end;
			}

			if(rparen == RPAREN || lparen == LPAREN) {
				ExpectedCharError invalidsyntax = new ExpectedCharError(this.start, this.end, "Expected '(' or ')!'");
			}
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitCallNode(this);
		}
	}

	static class GetNode extends Expr {
		final Expr object;
		final Token token;
		final Position start;
		final Position end;

		GetNode(Expr object, Token token) {
			this.token = token;
			this.node = node;
			this.start = token.start;
			this.end = token.end;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitGetNode(this);
		}
	}

	static class ThisNode extends Expr {
		final Token keyword;

		ThisNode(Token keyword) {
			this.keyword = keyword;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitThisNode(this);
		}	
	}

	static class SuperNode extends Expr {
		final Token keyword;
		final Token method;

		SuperNode(Token keyword, Token method) {
			this.keyword = keyword;
			this.method = method
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitSuperNode(this);
		}	
	}
}