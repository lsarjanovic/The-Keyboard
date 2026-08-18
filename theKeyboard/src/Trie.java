import java.util.*;

public class Trie {
	private NodeTrie root;

	public Trie() {
		this.root = getTrieNode();
	}

	private NodeTrie getTrieNode() {
		HashMap<Character, NodeTrie> triesNext = new HashMap<Character, NodeTrie>();
		boolean isEndOfWord = false;
		int freq = 0;

		NodeTrie trie = new NodeTrie(freq, isEndOfWord, triesNext);

		return trie;
	}

	private void insert(String word) {
		NodeTrie trieCurr = this.root;

		for (char character : word.toCharArray()) {

			NodeTrie trieNext = trieCurr.getTriesNext().get(character);

			if (trieNext == null) {

				trieNext = getTrieNode();

				trieCurr.getTriesNext().put(character, trieNext);
			}

			trieCurr = trieNext;
		}

		trieCurr.setEndOfWord(true);
		trieCurr.incrementFrequency();
	}

	public void insertSentence(String sentence) {

		sentence = sentence.toLowerCase().replaceAll("[^a-zA-Z\\s]", "");
		String [ ] words = sentence.split("\\s+");

		for (String word : words) this.insert(word);
	}

	public void setSuggestions(ArrayList<String> suggestions, String sentence) {
		String [ ] sentenceWords = sentence.split("\\s+");
		String prefix = sentenceWords[sentenceWords.length - 1];

		prefix = prefix.toLowerCase();

		ArrayList <String> suggestionsNext = new ArrayList <String>();
		NodeTrie trieCurr = this.root;
		
		for (char character : prefix.toCharArray()) {
			HashMap<Character, NodeTrie> triesNext = trieCurr.getTriesNext();

			trieCurr = triesNext.get(character);

			if (trieCurr == null) return;
		}

		addSuggestions(trieCurr, prefix, suggestionsNext);

		suggestionsNext.sort((str1, str2) -> {
			if (str1.length() > str2.length()) return 1;
			else if (str1.length() == str2.length()) return 0;
			else return -1;
		});

		int size = suggestionsNext.size();
		for (int i = 0; i < size; i++) {
			if (!(i < 3)) break;

			suggestions.add(suggestionsNext.get(i));
		}
	}

	private void addSuggestions(NodeTrie trieCurr, String prefix, List <String> suggestions) {

		if (trieCurr.getIsEndOfWord()) suggestions.add(prefix);
		else {

			for(Map.Entry <Character, NodeTrie> entry : trieCurr.getTriesNext().entrySet()) {
				char letter = entry.getKey();
				NodeTrie trieNext = entry.getValue();


				addSuggestions(trieNext, prefix + letter, suggestions);
			}
		}
	}


	public static class NodeTrie {
		private HashMap <Character, NodeTrie> triesNext;
		private int freq;
		private boolean isEndOfWord;

		public NodeTrie(int freq,
						boolean isEndOfWord,
						HashMap<Character, NodeTrie> triesNext) {

			this.isEndOfWord = isEndOfWord;
			this.freq = freq;
			this.triesNext = triesNext;
		}

		public HashMap<Character, NodeTrie> getTriesNext() {
			return this.triesNext;
		}

		public boolean getIsEndOfWord() {
			return this.isEndOfWord;
		}

		public int getFrequency() {
			return this.freq;
		}

		public void setEndOfWord(boolean nextIsEndOfWord) {
			this.isEndOfWord = nextIsEndOfWord;
		}

		public void incrementFrequency() {
			this.freq++;
		}
	}
}
