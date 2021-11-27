package inaf;

class Token {
	final TokenType type;
	final Object literal;
	final Position start;
	final Position end;

	Token(TokenType type, Object literal, Position start, Position end) {
		this.type = type;
		this.literal = literal;

		Position tempEnd;

		if(start != null) {
			this.start = start.copy();
			tempEnd = start.copy();
			tempEnd.advanced();
		} else {
			this.start = null;
		}

		if(end != null) {
			tempEnd = end.copy();
		} else {
			tempEnd = null;
		}

		this.end = tempEnd;
	}

	public boolean match(TokenType type, Object literal) {
		return this.type == type && this.literal == literal;
	}

	public String toString() {
		if(this.literal != null) {
			return type + " : " + literal;
		}

		return type + "";
	}
}