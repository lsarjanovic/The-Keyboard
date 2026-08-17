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
		HashMap<Character, NodeTrie> triesCurr = trieCurr.getTries();

		for (char character : word.toCharArray()) {

			NodeTrie trieNext = triesCurr.get(character)

			if (trieNext == NULL) {

				trieNext = getTrieNode();

				triesCurr.put(character, trieNext);
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

			if (trieCurr == NULL) return;
		}

		collectWords(trieCurr, prefix, suggestionsNext);

		int size = suggestionsNext.size();
		for (int i = 0; i < 3; i++) {
			if (i >= size) break;

			suggestions.add(suggestionsNext.get(i));
		}
	}

	private void addSuggestions(NodeTrie trieCurr, String prefix, List <String> suggestions) {

		if (trieCurr.getIsEndOfWord()) {
			suggestions.add(prefix);
		} else {
			char letter;
			NodeTrie trieNext;

			for(Map.Entry <Character, NodeTrie> entry : trieCurr.getTries().entrySet()) {
				letter = entry.getKey();
				trieNext = entry.getValue();
				addSuggestions(trieNext, prefix + letter, suggestions);
			}
		}
	}



	public static class NodeTrie {
		private Map <Character, NodeTrie> triesNext;
		private int freq;
		private boolean isEndOfWord;

		public TrieNode(int freq,
						boolean isEndOfWord,
						HashMap<Character, NodeTrie> triesNext) {

			this.isEndOfWord = isEndOfWord;
			this.freq = freq;
			this.triesNext = triesNext;
		}

		public void setEndOfWord(boolean nextIsEndOfWord) {
			this.isEndOfWord = nextIsEndOfWord;
		}

		public Map<Character, NodeTrie> getTriesNext() {
			return this.triesNext;
		}

		public boolean getIsEndOfWord() {
			return this.isEndOfWord;
		}

		public int getFrequency() {
			return this.frequency;
		}
	}
}
