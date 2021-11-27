package inaf;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.util.List;

public class Inaf {
	public static void main(String[] args) throws IOException {
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(input);
		System.out.println("Enter file name or file (absolute) path or command to run");

		for(;;) {
			System.out.print("Inaf > ");
			String command = reader.readLine();

			if(command == null) {
				continue;
			} else if (command.equals("exit") || command.equals("quit")) {
				System.exit(0);
			} else {
				if(command.endsWith(".inaf")) {
					run(command);
				} else {
					String filename = command + ".inaf";
					run(filename);
				}
			}
		}
	}

	private static void run(String path) throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get(path));
		Lexer lexer = new Lexer(path, new String(bytes, Charset.defaultCharset()));

		List<Token> tokens = lexer.scanTokens();

		Parser parser = new Parser(tokens);

		boolean hadError = Error.getError();
		boolean hadRTError = RTError.getRTError();

		if(hadError) {
			System.exit(65);
		}

		if(hadRTError) {
			System.exit(70);
		}
	}
}