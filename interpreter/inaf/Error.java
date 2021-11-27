package inaf;

class Error {
	private final Position start;
	private final Position end;
	private final String errorName;
	private final String details;
	private static boolean hadError = false;

	Error(Position start, Position end, String errorName, String details) {
		this.start = start;
		this.end = end;
		this.errorName = errorName;
		this.details = details;

		error();
	}

	private void error() {
		System.out.println(errorName + ": " + details + " at file " + start.fn + " line " + (start.ln + 1) +
							 "\n\n" + arrowString());

		hadError = true;
	}

	private String arrowString() {
		String result = "";

		int idxStart = Math.max(0, start.ftxt.substring(0, start.idx).lastIndexOf("\n"));
		int idxEnd = start.ftxt.indexOf("\n", idxStart + 1);

		if(idxEnd < 0) {
			idxEnd = start.ftxt.length();
		}

		int lineCount = end.ln - start.ln + 1;

		for(int i = 0; i < lineCount; i++) {
			String line = start.ftxt.substring(idxStart, idxEnd);

			int colStart, colEnd;
			if(i == 0) {
				colStart = start.col;
			} else {
				colStart = 0;
			}

			if(i == lineCount - 1) {
				colEnd = end.col;
			} else {
				colEnd = line.length() - 1;
			}

			result += line + "\n";

			for(int j = 0; j < colStart; j++) {
				result += " ";
			}

			for(int k = 0; k < colEnd - colStart; k++) {
				result += "^";
			}
		}

		return result.replace("\t", "");
	}

	public static boolean getError() {
		return hadError;
	}
}

class IllegalCharError extends Error {
	IllegalCharError(Position start, Position end, String details) {
		super(start, end, "Illegal Character", details);
	}
}

class ExpectedCharError extends Error {
	ExpectedCharError(Position start, Position end, String details) {
		super(start, end, "Expected Character", details);
	}
}

class InvalidSyntaxError extends Error {
	InvalidSyntaxError(Position start, Position end, String details) {
		super(start, end, "Invalid Syntax", details);
	}
}

class RTError extends RuntimeException {
	final Token token;
	private static boolean hadRTError = false;

	RTError(Token token, String message) {
		super(message);
		this.token = token;
		hadRTError = true;
	}

	public static boolean getRTError() {
		return hadRTError;
	}
}