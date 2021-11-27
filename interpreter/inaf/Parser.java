package inaf;

import static inaf.TokenType.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

class ParserResult {

}

class Parser {
	private final List<Token> tokens;
	private int tokenIdx;
	private int amount;
	private Token currentToken; 

	Parser(List<Token> tokens) {
		this.tokens = tokens;
		this.tokenIdx = -1;
		this.currentToken = null;
		advanced();
	}

	private void advanced() {
		this.tokenIdx++;
		updateCurrentToken();
	}

	private void reverse(int amount) {
		this.amount = amount == null ? 1 : amount;
		this.tokenIdx -= amount;
		updateCurrentToken();
	}

	private void updateCurrentToken() {
		if(this.tokenIdx >= 0 && this.tokenIdx <= tokens.size()) {
			this.currentToken = tokens.get(this.tokenIdx);
		} else {
			this.currentToken = null;
		}
	}
}