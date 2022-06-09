package code;

import java.util.*;

/**
 * A new instance of HuffmanCoding is created for every run. The constructor is
 * passed the full text to be encoded or decoded, so this is a good place to
 * construct the tree. You should store this tree in a field and then use it in
 * the encode and decode methods.
 */
public class HuffmanCoding {
	private BinaryNode root;

	/**
	 * This would be a good place to compute and store the tree.
	 */
	public HuffmanCoding(String text) {
		// TODO fill this in.
		Queue<Node> queue = new PriorityQueue<>((a, b)->(a.probability() - b.probability()));
		Map<String, Integer> occurrences = occurrences(text);
		Set<String> keySet = occurrences.keySet();
		for (String s : keySet) {
			queue.add(new LeafNode(s, occurrences.get(s)));
		}
		while(queue.size()>1){
			Node n1 = queue.poll();
			Node n2 = queue.poll();
			String newName = n1.name()+n2.name();
			int newProbability = n1.probability()+n2.probability();
			BinaryNode node = new BinaryNode(newName, newProbability, n1, n2);
			queue.add(node);
		}
		root = (BinaryNode) queue.poll();
		root.setCode("");
	}

	/**
	 * Take an input string, text, and encode it with the stored tree. Should
	 * return the encoded text as a binary string, that is, a string containing
	 * only 1 and 0.
	 */
	public String encode(String text) {
		// TODO fill this in.
		StringBuilder encodedText = new StringBuilder();

		Map<String, String> codes = root.getNamesToCodes();
		System.out.println(codes.toString());
		System.out.println(codes.keySet().size());
		String[] characters = text.split("");
		for (String character : characters) {
			encodedText.append(codes.get(character));//.append(" ");
		}

		return encodedText.toString();
	}

	/**
	 * Take encoded input as a binary string, decode it using the stored tree,
	 * and return the decoded text as a text string.
	 */
	public String decode(String encoded) {
		// TODO fill this in.
		StringBuilder text = new StringBuilder();

		Map<String, String> names = root.getCodesToNames();
		String[] nums = encoded.split("");
		Node node = root;
		for (int i=0; i<nums.length; i++){
			if(node instanceof BinaryNode){
				if(nums[i].equals("0")){
					node = ((BinaryNode) node).getLeft();
				}else{
					node = ((BinaryNode) node).getRight();
				}
				if(node instanceof LeafNode){
					text.append(node.name());
					node = root;
				}
			}
		}

		return text.toString();
	}

	private Map<String, Integer> occurrences(String text){
		Map<String, Integer> occurrences = new HashMap<>();
		String[] characters = text.split("");
		for(int i=0; i<characters.length; i++){
			if(!occurrences.containsKey(characters[i])){
				String character = characters[i];
				int num = 0;
				for(int j=i; j<characters.length; j++){
					if(characters[j].equals(character)){
						num++;
					}
				}
				occurrences.put(character, num);
			}
		}
		return occurrences;
	}

	/**
	 * The getInformation method is here for your convenience, you don't need to
	 * fill it in if you don't wan to. It is called on every run and its return
	 * value is displayed on-screen. You could use this, for example, to print
	 * out the encoding tree.
	 */
	public String getInformation() {
		return "";
	}
}
