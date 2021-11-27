package inaf;

class Position {
	public int idx;
	public int ln;
	public int col;
	public final String fn;
	public final String ftxt;

	Position(int idx, int ln, int col, String fn, String ftxt) {
		this.idx = idx;
		this.ln = ln;
		this.col = col;
		this.fn = fn;
		this.ftxt = ftxt;
	}

	public void advanced(char current) {
		this.idx += 1;
		this.col += 1;

		if(current == '\n') {
			this.ln += 1;
			this.col = 0;
		}
	}

	public void advanced() {
		this.idx += 1;
		this.col += 1;
	}

	public Position copy() {
		return this;
	}
}