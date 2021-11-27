package inaf;

import static inaf.TokenType.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

class Lexer {
	private static final Map<String, TokenType> keywords;

	static {
		keywords = new HashMap<>();
		keywords.put("package", PACKAGE);
		keywords.put("import", IMPORT);
		keywords.put("class", CLASS);
		keywords.put("method", METHOD);
		keywords.put("init", INIT);
		keywords.put("var", VAR);
		keywords.put("const", CONST);
		keywords.put("string", STRING);
		keywords.put("num", NUM);
		keywords.put("char", CHAR);
		keywords.put("boolean", BOOLEAN);
		keywords.put("true", TRUE);
		keywords.put("false", FALSE);
		keywords.put("if", IF);
		keywords.put("else if", ELIF);
		keywords.put("elseif", ELIF);
		keywords.put("elif", ELIF);
		keywords.put("else", ELSE);
		keywords.put("for", FOR);
		keywords.put("while", WHILE);
		keywords.put("do", DO);
		keywords.put("switch", SWITCH);
		keywords.put("case", CASE);
		keywords.put("return", RETURN);
		keywords.put("continue", CONTINUE);
		keywords.put("break", BREAK);
		keywords.put("super", SUPER);
		keywords.put("this", THIS);
		keywords.put("print", PRINT);
		keywords.put("input", INPUT);
		keywords.put("type", TYPE);
		keywords.put("enum", ENUM);
	}

	private final String fn;
	private final String ftxt;
	private Position pos;
	private char current;
	private final List<Token> tokens = new ArrayList<>();
	private final List<Token> emptyTokens = new ArrayList<>();
	private	Position start;
	private TokenType type;

	Lexer(String fn, String ftxt) {
		this.fn = fn;
		this.ftxt = ftxt;
		this.pos = new Position(-1, 0, -1, fn, ftxt);
		this.current = '\0';
		advanced();
	}

	List<Token> scanTokens() {
		while(current != '\0') {
			switch(current) {
				case '\t':
					advanced();
					break;
				case ' ':
					advanced();
					break;
				case '\r':
					advanced();
					break;
				case '#':
					advanced();

					while(current != '\n') {
						advanced();
					}

					advanced();
					break;
				case '\n':
					addToken(NEWLINE, this.pos);
					advanced();
					break;
				case '\"':
					start = this.pos.copy();

					advanced();

					while(current != '\0' && current != '\"') {
						advanced();
					}

					advanced();

					addToken(STRINGL, this.pos.ftxt.substring(start.idx, this.pos.idx), start, this.pos);
					break;
				case '+':
					type = PLUS;
					start = this.pos.copy();

					advanced();

					if(current =='=') {
						advanced();

						type = PLUSEQUAL;
					}

					addToken(type, start, this.pos);
					break;
				case '-':
					type = MINUS;
					start = this.pos.copy();

					advanced();

					if(current =='=') {
						advanced();

						type = MINUSEQUAL;
					}

					if(current =='>') {
						advanced();

						type = LAMBDA;
					}

					addToken(type, start, this.pos);
					break;
				case '*':
					type = MUL;
					start = this.pos.copy();

					advanced();

					if(current =='=') {
						advanced();

						type = MULEQUAL;
					}

					addToken(type, start, this.pos);
					break;
				case '/':
					type = DIV;
					start = this.pos.copy();

					advanced();

					if(current == '=') {
						advanced();

						type = DIVEQUAL;
					}

					if(current == '*') {
						advanced();

						type = null;

						while(current != '*' && this.pos.ftxt.charAt(this.pos.idx++) == '/') {
							advanced();
						}
						advanced();
					}

					if(type != null) {
						addToken(type, start, this.pos);
					}
					break;
				case '%':
					type = MOD;
					start = this.pos.copy();

					advanced();

					if(current =='=') {
						advanced();

						type = MODEQUAL;
					}

					addToken(type, start, this.pos);
					break;
				case '(':
					addToken(LPAREN, this.pos);
					break;
				case ')':
					addToken(RPAREN, this.pos);
					break;
				case '{':
					addToken(LBRACE, this.pos);
					break;
				case '}':
					addToken(RBRACE, this.pos);
					break;
				case '[':
					addToken(LBRACKET, this.pos);
					break;
				case ']':
					addToken(RBRACKET, this.pos);
					break;
				case '!':
					type = NOT;
					start = this.pos.copy();

					advanced();

					if(current =='=') {
						advanced();

						type = NOTEQUAL;
					}

					addToken(type, start, this.pos);
					break;
				case '=':
					type = EQUAL;
					start = this.pos.copy();

					advanced();

					if(current =='=') {
						advanced();

						type = DEQUAL;
					}

					addToken(type, start, this.pos);
					break;
				case '>':
					type = GREATER;
					start = this.pos.copy();

					advanced();

					if(current =='=') {
						advanced();

						type = GTE;
					}

					addToken(type, start, this.pos);
					break;
				case '<':
					type = LESS;
					start = this.pos.copy();

					advanced();

					if(current =='=') {
						advanced();

						type = LTE;
					}

					addToken(type, start, this.pos);
					break;
				case '|':
					type = EOR;
					start = this.pos.copy();

					advanced();

					if(current =='|') {
						advanced();

						type = OR;
					}

					addToken(type, start, this.pos);
					break;
				case '&':
					type = AMPERSAND;
					start = this.pos.copy();

					advanced();

					if(current =='=') {
						advanced();

						type = AND;
					}

					addToken(type, start, this.pos);
					break;
				case ',':
					addToken(COMMA, this.pos);
					advanced();
					break;
				case '.':
					addToken(DOT, this.pos);
					advanced();
					break;
				case '?':
					addToken(IRRBANG, this.pos);
					advanced();
					break;
				case ':':
					addToken(COLON, this.pos);
					advanced();
					break;
				case ';':
					addToken(SEMICOLON, this.pos);
					advanced();
					break;
				default:
					if(current >= '0' && current <= '9') {
						int dotCount = 0;
						start = this.pos.copy();

						while(current != '\0' && (current >= '0' && current <= '9')) {
							if(current =='.') {
								if(dotCount == 1) {
									break;
								}

								dotCount++;
							}
							advanced();
						}

						addToken(NUMBER, Double.parseDouble(ftxt.substring(start.idx, this.pos.idx)), start, this.pos);
					} else if((current >= 'a' && current <= 'z') || (current >= 'A' && current <= 'Z') || current =='_') {
						start = this.pos.copy();

						while((current >= '0' && current <= '9') || ((current >= 'a' && current <= 'z') || (current >= 'A' && current <= 'Z') || current =='_')) {
							advanced();
						}

						String text = ftxt.substring(start.idx, this.pos.idx);
						type = keywords.get(text);

						if(type == null) {
							type = 	IDENTIFIER;
						}

						addToken(type, text, start, this.pos);
					} else {
						start = this.pos.copy();
						char c = current;
						advanced();
						
						IllegalCharError charError = new IllegalCharError(start, this.pos, "\"" + c + "\"");
						return emptyTokens;
					}
					break;
			}
		}

		tokens.add(new Token(EOF, "", this.pos, null));
		return tokens;
	}

	private void advanced() {
		this.pos.advanced(this.current);

		if(this.pos.idx < ftxt.length()) {
			current = this.ftxt.charAt(this.pos.idx);
		} else {
			current = '\0';
		}
	}

	private void addToken(TokenType type) {
		addToken(type, null);
	}

	private void addToken(TokenType type, Object literal) {
		addToken(type, literal, null);
	}

	private void addToken(TokenType type, Position start) {
		addToken(type, null, start);
	}

	private void addToken(TokenType type, Object literal, Position start) {
		addToken(type, literal, start, null);
	}

	private void addToken(TokenType type, Position start, Position end) {
		addToken(type, null, start, end);
	}

	private void addToken(TokenType type, Object literal, Position start, Position end) {
		tokens.add(new Token(type, literal, start, end));
	}
}