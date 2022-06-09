package code;

import java.util.Timer;

/**
 * A new KMP instance is created for every substring search performed. Both the
 * pattern and the text are passed to the constructor and the search method. You
 * could, for example, use the constructor to create the match table and the
 * search method to perform the search itself.
 */
public class KMP {
	private int[] matchTable;

	public KMP(String pattern, String text) {
		// TODO maybe fill this in.
		this.matchTable = buildMatchTable(pattern);
	}

	/**
	 * Perform KMP substring search on the given text with the given pattern.
	 * 
	 * This should return the starting index of the first substring match if it
	 * exists, or -1 if it doesn't.
	 */
	public int search(String pattern, String text) {
		// TODO fill this in.
		Timer timer = new Timer();
		int steps = 0;
		int s = 0;
		int t = 0;
		String[] string = pattern.split("");
		String[] text1 = text.split("");

		while(t+s<text.length()){
			steps++;
			if(string[s].equals(text1[t+s])){//match
				s += 1;
				if(s==string.length){
					System.out.println("search steps: "+steps);
					return t;
				}// found S
			}else if(matchTable[s]==-1){//mismatch no self overlap
				t = t + s + 1;
				s = 0;
			}else{// mismatch with self overlap
				t = t + s - matchTable[s];// match pos jumps forwards
				s = matchTable[s];
			}
		}
		System.out.println("search steps: "+steps);
		return -1;//failed to find S
	}

	public int bruteForceSearch(String pattern, String text){
		int steps = 0;
		String[] pat = pattern.split("");
		String[] txt = text.split("");
		for (int i = 0; i < txt.length-pat.length; i++) {
			boolean match = true;
			for (int j = 0; j < pat.length; j++) {
				steps++;
				if(!pat[j].equals(txt[i+j])){
					match = false;
				}
			}
			if(match){
				System.out.println("brute force search steps: "+steps);
				return i;
			}
		}
		System.out.println("brute force search steps: "+steps);
		return -1;
	}

	private int[] buildMatchTable(String pattern){
		int steps = 0;
		int[] m = new int[pattern.length()];
		m[0] = -1;
		m[1] = 0;
		int j = 0;
		int pos = 2;
		String[] string = pattern.split("");
		while(pos<pattern.length()){
			steps++;
			if(string[pos-1].equals(string[j])){
				m[pos] = j + 1;
				pos++;
				j++;
			}else if(j>0){
				j = m[j];
			}else{
				m[pos] = 0;
				pos++;
			}
		}
		System.out.println("match table steps: "+steps);
		return m;
	}
}
