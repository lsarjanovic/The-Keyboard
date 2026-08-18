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
			trieCurr.incrementFrequency();
		}

		trieCurr.setEndOfWord(true);
	}

	public void insertSentence(String sentence) {

		sentence = sentence.toLowerCase().replaceAll("[^a-zA-Z\\s]", "");
		String [ ] words = sentence.split("\\s+");

		for (String word : words) this.insert(word);
	}

	private void addSuggestionsNext(NodeTrie trieCurr, StringBuilder word,
									ArrayList<String> suggestionsNext, int len) {

		if (trieCurr.getIsEndOfWord()) suggestionsNext.add(word.toString());

		for (Map.Entry <Character, NodeTrie> entry : trieCurr.getTriesNext().entrySet()) {
			char letter = entry.getKey();
			NodeTrie trieNext = entry.getValue();

			word.append(letter);
			addSuggestionsNext(trieNext, word, suggestionsNext, len + 1);
			word.deleteCharAt(len);
		}
	}

	private void sortSuggestionsNext(ArrayList<String> suggestionsNext) {

		suggestionsNext.sort((str1, str2) -> {
			if (str1.length() > str2.length()) return 1;
			else if (str1.length() == str2.length()) return 0;
			else return -1;
		});
	}

	private NodeTrie getPrefixNodeTrie(String prefix) {
		NodeTrie trieCurr = this.root;

		prefix = prefix.toLowerCase();

		for (char character : prefix.toCharArray()) {
			HashMap<Character, NodeTrie> triesNext = trieCurr.getTriesNext();

			trieCurr = triesNext.get(character);

			if (trieCurr == null) return null;
		}

		return trieCurr;
	}

	private ArrayList<String> getSuggestionsNext(String prefix) {
		ArrayList <String> suggestionsNext = new ArrayList <String>();

		NodeTrie trieCurr = getPrefixNodeTrie(prefix);
		if (trieCurr == null) return suggestionsNext;

		StringBuilder sbPrefix = new StringBuilder();
		sbPrefix.append(prefix);

		int len = prefix.length();
		addSuggestionsNext(trieCurr, sbPrefix, suggestionsNext, len);

		sortSuggestionsNext(suggestionsNext);

		return suggestionsNext;
	}

	public void setSuggestions(ArrayList<String> suggestions, String sentence) {
		String [ ] sentenceWords = sentence.split("\\s+");
		String prefix = sentenceWords[sentenceWords.length - 1];

		ArrayList<String> suggestionsNext = getSuggestionsNext(prefix);

		int size = suggestionsNext.size();
		for (int i = 0; i < size; i++) {
			if (!(i < 3)) break;

			suggestions.add(suggestionsNext.get(i));
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
