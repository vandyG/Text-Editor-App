package spelling;

import static org.junit.Assert.assertEquals;

import java.util.List;

public class AutoCompleteDictionaryTrieClient {
	public static void main(String[] args) {
		AutoCompleteDictionaryTrie smallDict = new AutoCompleteDictionaryTrie();
		
		smallDict.addWord("Hello");
		smallDict.addWord("HElLo");
		smallDict.addWord("help");
		smallDict.addWord("he");
		smallDict.addWord("hem");
		smallDict.addWord("hot");
		smallDict.addWord("hey");
		smallDict.addWord("a");
		smallDict.addWord("subsequent");
		
		System.out.println(smallDict.size());
		
		List<String> completions;
		
		System.out.println(smallDict.predictCompletions("", 0));
		System.out.println(smallDict.predictCompletions("",  4));
		
		
	}
}
