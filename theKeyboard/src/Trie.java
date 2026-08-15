import java.util.*;

public class Trie {
	private TrieNode root;

	public Trie() {
		this.root = new TrieNode(false, 0);
	}

	public void insertSentence(String sentence) {
		//Remove punctuation and set to lowerCase.
		sentence = sentence.toLowerCase().replaceAll("[^a-zA-Zа-яА-Я\\s]", "");
		String [ ] words = sentence.split("\\s+");
		for (String word : words) insert(word);
	}

	private void insert(String word) {
		TrieNode node = this.root;
		for (char c : word.toCharArray()) { //The word is already made lowerCase in insertSentence(String).
			node = node.children.computeIfAbsent(c, k -> new TrieNode(false, 0));
		}
		node.isEndOfWord = true; //Last Character in trie path is marked as last character.
		node.frequency++;
	}

	public void setSuggestions(ArrayList<String> suggestions, String sentence) {
		String [ ] sentenceWords = sentence.split("\\s+");
		String prefix = sentenceWords[sentenceWords.length - 1];

		prefix = prefix.toLowerCase(); //All words are treated as lowercase in search.

		ArrayList <String> suggestionsNext = new ArrayList <String>();
		TrieNode node = this.root;
		
		//Find prefix.
		for (char character : prefix.toCharArray()) {
			node = node.children.get(character);
			if (node == null) return;
		}

		//Save all the words into a list.
		collectWords(node, prefix, suggestionsNext);

		int size = suggestionsNext.size();
		for (int i = 0; i < 3; i++) {
			if (i >= size) break;

			suggestions.add(suggestionsNext.get(i));
		}

		return;
	}

	private void collectWords(TrieNode node, String prefix, List <String> suggestions) {
		if (node.isEndOfWord) {
			suggestions.add(prefix);
		} else {
			char letter;
			TrieNode childNode;
			for(Map.Entry <Character, TrieNode> entry : node.children.entrySet()) {
				letter = entry.getKey();
				childNode = entry.getValue();
				collectWords(childNode, prefix + letter, suggestions);
			}
		}
	}



	public static class TrieNode {
		private Map <Character, TrieNode> children;
		private boolean isEndOfWord;
		private int frequency;

		public TrieNode(boolean isEndOfWord, int frequency) {
			this.children = new HashMap <Character, TrieNode>();
			this.isEndOfWord = isEndOfWord;
			this.frequency = frequency;
		}

		public Map<Character, TrieNode> getChildren() {
			return this.children;
		}

		public boolean getIsEndOfWord() {
			return this.isEndOfWord;
		}

		public int getFrequency() {
			return this.frequency;
		}
	}
}
