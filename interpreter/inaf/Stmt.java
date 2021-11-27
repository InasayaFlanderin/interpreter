package inaf;

import java.util.List;

abstract class Stmt {
	interface Visitor<R> {
		R visitBlockStmt(Block stmt);
		R visitExpressionStmt(Expression stmt);
		R visitVarStmt(Var stmt);
		R visitConstStmt(Const stmt);
		R visitStringStmt(String stmt);
		R visitNumStmt(Num stmt);
		R visitCharStmt(Char stmt);
		R visitBooleanStmt(Boolean stmt);
		R visitForStmt(For stmt);
		R visitWhileStmt(While stmt);
		R visitWhileStmt(Do stmt);
		R visitMethodStmt(Method stmt);
		R visitInitStmt(Init stmt);
		R visitClassStmt(Class stmt);
		R visitEnumStmt(Enum stmt);
		R visitReturnStmt(Return stmt);
		R visitContinueStmt(Continue stmt);
		R visitBreakStmt(Break stmt);
		R visitPrintStmt(Print stmt);
		R visitInputStmt(Input stmt);

	}

	abstract <R> R accept(Visitor<R> visitor);
	final Position start;
	final Position end;

	static class Block extends Stmt {
		final List<Stmt> statements;
		final Position start;
		final Position end;

		Block(List<Stmt> statements) {
			this.statements = statements;
			this.start = statements.get(0).start;

			if(statements.size() > 0) {
				this.end = statements.get(statements.size() - 1).end;
			} else {
				this.end = statements.get(0).end;
			}
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitBlockStmt(this);
		}
	}

	static class Expression extends Stmt {
		final Expr expression;
		final Position start;
		final Position end;

		Expression(Expr expression) {
			this.expression = expression;
			this.start = expression.start;
			this.end = expression.end;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitExpressionStmt(this);
		}
	}

	static class Var extends Stmt {
		final Token name;
		final Expr initializer;
		final Position start;
		final Position end;
		Var(Token name, Expr initializer) {
			this.name = name;
			this.initializer = initializer;
			this.start = name.start;
			this.end = initializer.end;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitVarStmt(this);
		}
	}

	static class Const extends Stmt {
		final Token name;
		final Expr initializer;
		final Position start;
		final Position end;
		Const(Token name, Expr initializer) {
			this.name = name;
			this.initializer = initializer;
			this.start = name.start;
			this.end = initializer.end;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitConstStmt(this);
		}
	}

	static class String extends Stmt {
		final Token name;
		final Expr.StringNode initializer;
		final Position start;
		final Position end;
		String(Token name, Expr.StringNode initializer) {
			this.name = name;
			this.initializer = initializer;
			this.start = name.start;
			this.end = initializer.end;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitStringStmt(this);
		}
	}

	static class Num extends Stmt {
		final Token name;
		final Expr.NumberNode initializer;
		final Position start;
		final Position end;
		Num(Token name, Expr.NumberNode initializer) {
			this.name = name;
			this.initializer = initializer;
			this.start = name.start;
			this.end = initializer.end;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitNumStmt(this);
		}
	}

	static class Char extends Stmt {
		final Token name;
		final Expr.StringNode initializer;
		final Position start;
		final Position end;
		Char(Token name, Expr.StringNode initializer) {
			this.name = name;
			this.initializer = initializer;
			this.start = name.start;
			this.end = initializer.end;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitCharStmt(this);
		}
	}

	static class Boolean extends Stmt {
		final Token name;
		final Token initializer;
		final Position start;
		final Position end;
		Boolean(Token name, Token initializer) {
			this.name = name;
			this.initializer = initializer;
			this.start = name.start;
			this.end = initializer.end;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitBooleanStmt(this);
		}
	}

	static class For extends Stmt {
		final List<Expr> condition;
		final List<Stmt> body;
		final Position start;
		final Position end;

		For(List<Expr> condition, List<Stmt> body) {
			this.condition = condition;
			this.body = body;

			if(condition != null) {
				this.start = condition.get(0).start;
			} else {
				this.start = body.get(0).start;
			}

			this.end = body.get(body.size() - 1).end;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitForStmt(this);
		}
	}

	static class While extends Stmt {
		final Expr condition;
		final List<Stmt> body;
		final Position start;
		final Position end;

		While(Expr condition, List<Stmt> body) {
			this.condition = condition;
			this.body = body;

			if(condition != null) {
				this.start = condition.start;
			} else {
				this.start = body.get(0).start;
			}

			this.end = body.get(body.size() - 1).end;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitWhileStmt(this);
		}
	}

	static class Do extends Stmt {
		final Expr condition;
		final List<Stmt> body;
		final Position start;
		final Position end;

		Do(Expr condition, List<Stmt> body) {
			this.condition = condition;
			this.body = body;

			if(condition != null) {
				this.start = condition.start;
			} else {
				this.start = body.get(0).start;
			}

			this.end = body.get(body.size() - 1).end;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitDoStmt(this);
		}
	}

	static class Method extends Stmt {
		final Token name;
		final List<Token> parametters;
		final List<Stmt> body;
		final Position start;
		final Position end;

		Method(Token name, List<Token> parametters, List<Stmt> body) {
			this.name = name;
			this.parametters = parametters;
			this.body = body;

			if(name != null) {
				this.start = name.start;
			} else if(parametters.size() > 0) {
				this.start = parametters.get(0).start;
			} else {
				body.get(0).start;
			}

			this.end = body.get(body.size() - 1).end;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			visitor.visitMethodStmt(this);
		}
	}

	static class Init extends Stmt {
		final Token name;
		final List<Token> parametters;
		final List<Stmt> body;
		final Position start;
		final Position end;

		Init(Token name, List<Token> parametters, List<Stmt> body) {
			this.name = name;
			this.parametters = parametters;
			this.body = body;

			if(name != null) {
				this.start = name.start;
			} else if(parametters.size() > 0) {
				this.start = parametters.get(0).start;
			} else {
				body.get(0).start;
			}

			this.end = body.get(body.size() - 1).end;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			visitor.visitInitStmt(this);
		}
	}

	static class Class extends Stmt {
		final Token name;
		final List<Expr.VariableNode> supClass;
		final Stmt.Init constructor;
		final List<Stmt.Method> methods;
		final Position start;
		final Position end;

		Class(Token name, List<Expr.VariableNode> supClass, Stmt.Init constructor, List<Stmt.Method> methods) {
			this.name = name;
			this.supClass = supClass;
			this.constructor = constructor;
			this.methods = methods;

			if(name != null) {
				this.start = name.start;
			} else if(constructor != null) {
				this.start = constructor.start;
			} else {
				this.start = method.get(0).start;
			}

			this.end = body.get(body.size() - 1).end;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			visitor.visitClassStmt(this);
		}
	}

	static class Enum extends Stmt {
		final Token name;
		final List<Expr.LiteralNode> enumValue;
		final Position start;
		final Position end;

		Enum(Token name, List<Expr.LiteralNode> enumValue) {
			this.name = name;
			this.enumValue = enumValue;

			if(name != null) {
				this.start = name.start;
			} else {
				this.start = enum.get(0).start;
			}

			this.end = enum.get(enum.size() - 1).end;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			visitor.visitEnumStmt(this);
		}
	}

	static class Return extends Stmt {
		final Token name;
		final List<Expr> value;
		final Position start;
		final Position end;

		Return(Token name, List<Expr> value) {
			this.name = name;
			this.value = value;

			if(name != null) {
				this.start = name.start;
			} else {
				this.start = value.get(0).start;
			}

			this.end = value.get(value.size() - 1).end;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			visitor.visitReturnStmt(this);
		}
	}

	static class Continue extends Stmt {
		final Token name;
		final Position start;
		final Position end;

		Continue(Token name) {
			this.name = name;
			this.start = name.start
			this.end = name.end;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			visitor.visitContinueStmt(this);
		}
	}

	static class Break extends Stmt {
		final Token name;
		final Position start;
		final Position end;

		Break(Token name) {
			this.name = name;
			this.start = name.start
			this.end = name.end;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			visitor.visitBreakStmt(this);
		}
	}

	static class Print extends Stmt {
		final Expr expression;
		final Position start;
		final Position end;

		Print(Expr expression) {
			this.expression = expression;
			this.start = expression.start
			this.end = expression.end;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			visitor.visitPrintStmt(this);
		}
	}

	static class Input extends Stmt {
		final Expr expression;
		final Position start;
		final Position end;

		Input(Expr expression) {
			this.expression = expression;
			this.start = expression.start
			this.end = expression.end;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			visitor.visitInputStmt(this);
		}
	}
}