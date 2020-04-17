package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	//Is generator trained or not
	private boolean isTrained;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// To check if it's already trained
		if(isTrained) {
			return;
		}
		
		String[] words = sourceText.split("[\\s]+");
		starter= words[0];
		String prevWord = starter;
		
		for(int i = 1; i < words.length; i++) {
			String w = words[i];
			
			updateWordList(prevWord, w);
			
			prevWord = w;
		}
		
		updateWordList(words[words.length-1], starter);
		isTrained = true;
	}
	
	//Returns the index of Word node in WordList
	private int getNodeIndex(String word) {
		int nodeIndex = -1;
		
		for (int i = 0; i < wordList.size(); i++) {
			if(wordList.get(i).getWord().equals(word)) {
				nodeIndex = i;
			}
		}
		
		return nodeIndex;
	}
	
	//Updates the wordList with words sourceText in train() method
	private void updateWordList(String prevWord, String w) {
		
		int nodeIndex = getNodeIndex(prevWord);
		
		if(nodeIndex == -1) {
			ListNode newNode = new ListNode(prevWord);
			newNode.addNextWord(w);
			wordList.add(newNode);
		}else {
			wordList.get(nodeIndex).addNextWord(w);
		}
		
		prevWord = w;
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		
		String currWord = starter;
		String output = "";
		
		if(isTrained && numWords != 0) {
			output += currWord;
			
			while(--numWords > 0) {
				ListNode currNode = wordList.get(getNodeIndex(currWord));
				String w = currNode.getRandomNextWord(rnGenerator);
				output += " " + w;
				currWord = w;
			}
		}
		
		return output;
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
		wordList.clear();
		starter = "";
		isTrained = false;
		
		String[] words = sourceText.split("[\\p{Punct}\\s]+");
		starter= words[0];
		String prevWord = starter;
		
		for(int i = 1; i < words.length; i++) {
			String w = words[i];
			
			updateWordList(prevWord, w);
			
			prevWord = w;
		}
		
		updateWordList(words[words.length-1], starter);
		isTrained = true;
	}
	
	// TODO: Add any private helper methods you need here.
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		
		
		 MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		 String textString =
		 "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again."
		 ; System.out.println(textString); gen.train(textString);
		 System.out.println(gen); System.out.println(gen.generateText(20)); String
		 textString2 = "You say yes, I say no, "+
		 "You say stop, and I say go, go, go, "+
		 "Oh no. You say goodbye and I say hello, hello, hello, "+
		 "I don't know why you say goodbye, I say hello, hello, hello, "+
		 "I don't know why you say goodbye, I say hello. "+
		 "I say high, you say low, "+ "You say why, and I say I don't know. "+
		 "Oh no. "+ "You say goodbye and I say hello, hello, hello. "+
		 "I don't know why you say goodbye, I say hello, hello, hello, "+
		 "I don't know why you say goodbye, I say hello. "+
		 "Why, why, why, why, why, why, "+ "Do you say goodbye. "+ "Oh no. "+
		 "You say goodbye and I say hello, hello, hello. "+
		 "I don't know why you say goodbye, I say hello, hello, hello, "+
		 "I don't know why you say goodbye, I say hello. "+ "You say yes, I say no, "+
		 "You say stop and I say go, go, go. "+ "Oh, oh no. "+
		 "You say goodbye and I say hello, hello, hello. "+
		 "I don't know why you say goodbye, I say hello, hello, hello, "+
		 "I don't know why you say goodbye, I say hello, hello, hello, "+
		 "I don't know why you say goodbye, I say hello, hello, hello,";
		 System.out.println(textString2); gen.retrain(textString2);
		 System.out.println(gen); System.out.println(gen.generateText(20)); 
		
		 MarkovTextGeneratorLoL gen3 = new MarkovTextGeneratorLoL(new Random());
		 String s = "hi there Hi Leo"; 
		 gen3.train(s);
		 System.out.println(gen3.generateText(4));
		 System.out.println(gen3.toString());
		 
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		int index = generator.nextInt(this.nextWords.size());
	    return nextWords.get(index);
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		
		toReturn += "\n";
		return toReturn;
	}
	
}


