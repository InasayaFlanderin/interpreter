package inaf;

enum TokenType {
	//char
	//1 char
	LPAREN, RPAREN, LBRACE, RBRACE, LBRACKET, RBRACKET, COMMA, DOT, IRRBANG, COLON, SEMICOLON,
	/*( , ) , { , }, [, ], , , . , ? , : , ; , */

	//1-2 char(s)
	NOT, NOTEQUAL, //! , !=
	EQUAL, DEQUAL, //= , ==
	GREATER, GTE, // >, >=
	LESS, LTE, // <, <=
	AMPERSAND, AND, // &, &&
	EOR, OR, // |, ||
	PLUS, PLUSEQUAL, // +, +=
	MINUS, MINUSEQUAL, LAMBDA, // -, -=, ->
	MUL, MULEQUAL,// *, *=
	DIV, DIVEQUAL, // /, /=
	MOD, MODEQUAL, // %, %=

	//misc

	NEWLINE,

	//literal
	IDENTIFIER, STRINGL, NUMBER,

	//keywords
	PACKAGE, IMPORT, CLASS, METHOD, INIT,
	VAR, CONST, STRING, NUM, CHAR, BOOLEAN,
	TRUE, FALSE,
	IF, ELIF, ELSE, FOR, WHILE, DO, SWITCH, CASE,
	RETURN, CONTINUE, BREAK,
	SUPER, THIS,
	PRINT, INPUT, TYPE,
	ENUM,

	//EOF
	EOF
}