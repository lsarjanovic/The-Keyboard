import java.util.*;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;

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

	private void addWord(String word) {
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

	public void addSentence(String sentence) {

		sentence = sentence.toLowerCase().replaceAll("[^a-zA-Z\\s]", "");
		String [ ] words = sentence.split("\\s+");

		for (String word : words) addWord(word);
	}

	private void addSuggestionsNext(NodeTrie trieCurr,
									StringBuilder word,
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

	public ArrayList<String> getWords() {
		NodeTrie trieCurr = this.root;
		StringBuilder word = new StringBuilder();

		ArrayList<String> words = new ArrayList<String>();

		addSuggestionsNext(trieCurr, word, words, 0);

		return words;
	}

	private void setTriesRemove(NodeTrie [ ] triesRemove, NodeTrie trieNext) {
		if (triesRemove[1] == null) triesRemove[1] = trieNext;
		else {
			triesRemove[0] = triesRemove[1];
			triesRemove[1] = trieNext;
		}
	}

	private void setIndexRemove(int [ ] indexRemove, int index) {
		if (indexRemove[1] == -1) indexRemove[1] = index;
		else {
			indexRemove[0] = indexRemove[1];
			indexRemove[1] = index;
		}
	}


	private void removeTries(String word) {
		NodeTrie trieCurr = this.root;

		char [ ] letters = word.toCharArray();

		int indexRemove = 0;
		NodeTrie trieRemove = trieCurr;

		int size1 = word.length();
		for (int i = 0; i < size1; i++) {
			if (trieCurr.getIsEndOfWord()) {
				indexRemove = i;
				trieRemove = trieCurr;
				break;
			}

			char character = letters[i];
			NodeTrie trieNext = trieCurr.getTriesNext().get(character);

			if (trieNext == null) System.out.println("Error deleting word: word not found.");

			trieCurr = trieNext;
		}

		char character = letters[indexRemove];

		HashMap<Character, NodeTrie> triesNextRemove = trieRemove.getTriesNext();
		triesNextRemove.remove(letters[indexRemove]);
	}

	private boolean lastTrie(NodeTrie trieCurr) {
		int count = 0;

		for (Map.Entry <Character, NodeTrie> entry : trieCurr.getTriesNext().entrySet()) count++;

		if (count == 0) return true;
		else return false;
	}

	public void removeWord(String word) {
		NodeTrie trieCurr = this.root;

		char [ ] letters = word.toCharArray();

		int size1 = word.length();
		for (int i = 0; i < size1; i++) {

			char character = letters[i];
			NodeTrie trieNext = trieCurr.getTriesNext().get(character);

			if (trieNext == null) System.out.println("Error deleting word: word not found.");

			trieCurr = trieNext;
		}

		if (trieCurr.getIsEndOfWord()) trieCurr.setEndOfWord(false);
		if (lastTrie(trieCurr)) removeTries(word);
	}


	private int getIndex(String s1) {
		int size1 = s1.length();

		int index = 0;
		for (char c : s1.toCharArray()) {
			if (c != '\t') break;

			index++;
		}

		return index;
	}

	private boolean getIsEndOfWord(int index, String line) {

		if (line.length() - 1 >= index + 1
		 && line.charAt(index + 1) == '_') return true;
		else return false;
	}

	public void loadWords() throws IOException {

		try {
			FileReader input = new FileReader("resources/tries.txt");

			BufferedReader bufferedReader = new BufferedReader(input);

			String line = bufferedReader.readLine();

			StringBuilder word = new StringBuilder();

			while(line != null) {
				int index = getIndex(line);
				word.delete(index, word.length());

				char letter = line.charAt(index);
				boolean isEndOfWord = getIsEndOfWord(index, line);

				word.append(letter);
				if (isEndOfWord) addWord(word.toString());

				line = bufferedReader.readLine();
			}

			bufferedReader.close();
		} catch (IOException e) {
			System.out.print(e.getMessage());
		}
	}

	private String getTab(int depth) {
		StringBuilder tab = new StringBuilder();

		for (int i = 0; i < depth; i++) tab.append('\t');

		return tab.toString();
	}

	private void setWordLong(NodeTrie trieCurr,
							 StringBuilder wordCurr,
							 int depth) {

		HashMap<Character, NodeTrie> triesNext = trieCurr.getTriesNext();

		String tab = getTab(depth);

		for (Map.Entry<Character, NodeTrie> entry : triesNext.entrySet()) {
			char character = entry.getKey();
			boolean endOfWord = entry.getValue().getIsEndOfWord();

			wordCurr.append(tab);
			wordCurr.append(character);
			if (endOfWord) wordCurr.append('_');

			wordCurr.append('\n');

			NodeTrie trieNext = entry.getValue();

			setWordLong(trieNext, wordCurr, depth + 1);
		}
	}


	public void saveWords() {
		StringBuilder wordLong = new StringBuilder();

		NodeTrie root = this.root;

		setWordLong(root, wordLong, 0);

		String text = wordLong.toString();

		try {
			FileWriter fileWriter = new FileWriter("resources/tries.txt");
			BufferedWriter f_writer = new BufferedWriter(fileWriter);

			f_writer.write(text);

			f_writer.close();
		} catch (IOException e) {
			System.out.print(e.getMessage());
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

		private HashMap<Character, NodeTrie> getTriesNext() {
			return this.triesNext;
		}

		private boolean getIsEndOfWord() {
			return this.isEndOfWord;
		}

		private int getFrequency() {
			return this.freq;
		}

		private void removeWordTrie() {
			this.triesNext = null;
		}

		private void setEndOfWord(boolean nextIsEndOfWord) {
			this.isEndOfWord = nextIsEndOfWord;
		}

		private void incrementFrequency() {
			this.freq++;
		}
	}
}
