package code;


/**
 * A new instance of LempelZiv is created for every run.
 */
public class LempelZiv {
	/**
	 * Take uncompressed input as a text string, compress it, and return it as a
	 * text string.
	 */
	public String compress(String input) {
		// TODO fill this in.
		StringBuilder output = new StringBuilder();

		int cursor = 0;
		int windowSize = 10000;
		while (cursor < input.length()) {
			int lookahead = 0;
			int prevMatch = 0;

			String textWindow = input.substring((cursor < windowSize)?0:(cursor - windowSize), cursor);
			String match = input.substring(cursor, cursor+lookahead);

			while(textWindow.contains(match)) {
				lookahead++;
				prevMatch = textWindow.indexOf(match);
				match = input.substring(cursor, cursor+lookahead);

			}
			output.append("[").append(textWindow.length() - prevMatch).append(",").append(lookahead - 1).append(",").append(match.charAt(match.length() - 1)).append("]");
			cursor += lookahead;
		}
		System.out.println("compressed");
		return output.toString();
	}

	/**
	 * Take compressed input as a text string, decompress it, and return it as a
	 * text string.
	 */
	public String decompress(String compressed) {
		// TODO fill this in.
		StringBuilder output = new StringBuilder();

		java.util.Scanner s = new java.util.Scanner(compressed);
		s.useDelimiter("");
		while (s.hasNext()) {
			s.next();
			s.useDelimiter(",");
			int distBack = s.nextInt();
			s.useDelimiter("");
			s.next();
			s.useDelimiter(",");
			int length = s.nextInt();
			s.useDelimiter("");
			s.next();
			String c = s.next();
			s.next();
			int i1 = output.length() - distBack;
			int i2 = output.length() - distBack + length;
			String extra = output.substring(i1, i2);
			output.append(extra).append(c);
		}
		return output.toString();
	}

	/**
	 * The getInformation method is here for your convenience, you don't need to
	 * fill it in if you don't want to. It is called on every run and its return
	 * value is displayed on-screen. You can use this to print out any relevant
	 * information from your compression.
	 */
	public String getInformation() {
		return "";
	}
}
