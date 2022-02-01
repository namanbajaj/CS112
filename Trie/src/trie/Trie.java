package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie. 
 * 
 * @author Sesh Venugopal
 *
 */
public class Trie {
	
	// prevent instantiation
	private Trie() { }
	
	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
	 * The words in the input array are all lower case.
	 * 
	 * @param allWords Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 */
	public static TrieNode buildTrie(String[] allWords) {
		TrieNode root = new TrieNode(null, null, null);

		if(allWords.length == 0)
			return root;

		root.firstChild = new TrieNode(new Indexes(0, (short) 0, (short) (allWords[0].length() - 1)), null, null); //add first word to tree
		
		for(int i = 1; i < allWords.length; i++){
			TrieNode temp = root.firstChild;
			TrieNode temp2 = root.firstChild;
			int s = -1;
			int e = -1;
			int w = -1;
			int c = -1;

			String word = allWords[i];

			while(temp != null){
				s = temp.substr.startIndex;
				e = temp.substr.endIndex;
				w = temp.substr.wordIndex;

				if(s > word.length()){
					temp2 = temp;
					temp = temp.sibling;
					continue;
				}

				c = samePrefix(allWords[w].substring(s, e + 1), word.substring(s));

				if(c != -1)
					c += s;

				if(c == -1){
					temp2 = temp;
					temp = temp.sibling;
				}

				else{
					if(c == e){
						temp2 = temp;
						temp = temp.firstChild;
					}

					else if(c < e){
						temp2 = temp;
						break;
					}
				}
			}
		

			if(temp == null){
				Indexes add = new Indexes(i, (short) s, (short) (word.length() - 1));
				temp2.sibling = new TrieNode(add, null, null);
			}

			else{
				Indexes current = temp2.substr;
				TrieNode child = temp2.firstChild;
				Indexes newIndexes = new Indexes(current.wordIndex, (short)(c + 1), current.endIndex);
				current.endIndex = (short) c;

				temp2.firstChild = new TrieNode(newIndexes, null, null);
				temp2.firstChild.firstChild = child;
				temp2.firstChild.sibling = new TrieNode(new Indexes((short) i, (short)(c + 1), (short)(word.length() - 1)), null, null);
			}
		}

		return root;
	}

	private static int samePrefix(String word, String compareWord){
		char[] w = word.toCharArray();
		char[] cW = compareWord.toCharArray();

		int prefix = 0;

		int shorterWord = 0;
		if(cW.length > w.length)
			shorterWord = w.length;

		else
			shorterWord = cW.length;

		int i = 0;
		while(prefix < w.length && prefix < cW.length && w[i] == cW[i]){
			if(i < shorterWord){
				prefix++;
				i++;
			}
		}

		return --prefix;
	}
	
	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
	 * trie whose words start with this prefix. 
	 * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
	 * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
	 * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
	 * and for prefix "bell", completion would be the leaf node that holds "bell". 
	 * (The last example shows that an input prefix can be an entire word.) 
	 * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
	 * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root Root of Trie that stores all words to search on for completion lists
	 * @param allWords Array of words that have been inserted into the trie
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with the prefix, 
	 * 			order of leaf nodes does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */
	public static ArrayList<TrieNode> completionList(TrieNode root, String[] allWords, String prefix) {
		if(root == null)
			return null;
		
		ArrayList<TrieNode> words = new ArrayList<TrieNode>();
		TrieNode temp = root;

		while(temp != null){
			if(temp.substr == null)
				temp = temp.firstChild;
			
			String word = allWords[temp.substr.wordIndex];
			String wordPrefix = word.substring(0, temp.substr.endIndex + 1);

			if(word.startsWith(prefix) || prefix.startsWith(wordPrefix)){
				if(temp.firstChild!=null){
					words.addAll(completionList(temp.firstChild,allWords,prefix));
					temp=temp.sibling;
				}
				else{
					words.add(temp);
					temp=temp.sibling;
				}
			}
			else
				temp = temp.sibling;
		}

		if(words.isEmpty())
			return null;

		return words;
	}
	



	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
 }