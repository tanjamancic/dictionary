package mainPackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Book {

	protected String fileName;
	protected ArrayList<String> allWords;
	protected HashMap<String, Integer> countWords;

	public Book(String fileName) {
		super();
		this.fileName = fileName;
		allWords = new ArrayList<String>();
		countWords = new HashMap<String, Integer>();
	}

	public ArrayList<String> readBookFromFile() {

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));

			String line = br.readLine();

			while (line != null) {

				if (line.isEmpty()) {
					line = br.readLine();
					continue;
				}

				if (line.matches(".*((- )|(-))$")) {
					line = line.replaceAll("(- )$|(-)$", "");
					line += br.readLine();
					continue;
				}

				String[] wordByWord = processingOneLine(line);

				for (int i = 0; i < wordByWord.length; i++) {
					if (wordByWord[i].matches("^-.*")) {
						wordByWord[i] = wordByWord[i].replaceAll("^-", "");
					}
					if (wordByWord[i].isEmpty()) {
						continue;
					}
					if (!allWords.contains(wordByWord[i])) {
						allWords.add(wordByWord[i]);
					}
					addWord(wordByWord[i]);
				}

				line = br.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return allWords;
	}

	private String[] processingOneLine(String line) {

		line = line.toLowerCase();
		line = line.replaceAll("[^a-zA-Z -]", "");
		String[] splitString = line.split(" ");

		return splitString;

	}

	private void addWord(String word) {

		if (countWords.containsKey(word)) {
			countWords.replace(word, countWords.get(word) + 1);
		} else {
			countWords.put(word, 1);
		}
	}

	private Map<String, Integer> sortCountByValue(boolean order) {

		List<Entry<String, Integer>> list = new LinkedList<>(countWords.entrySet());

		list.sort((o1, o2) -> order
				? o1.getValue().compareTo(o2.getValue()) == 0 ? o1.getKey().compareTo(o2.getKey())
						: o1.getValue().compareTo(o2.getValue())
				: o2.getValue().compareTo(o1.getValue()) == 0 ? o2.getKey().compareTo(o1.getKey())
						: o2.getValue().compareTo(o1.getValue()));

		return list.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));

	}

	public void print20mostlyUsedWords() {

		Map<String, Integer> sortedCount = sortCountByValue(false);
		
		int repeat = 0;
		System.out.println("Mostly used words: ");
		for (String i : sortedCount.keySet()) {
			if (repeat == 20) {
				System.out.println();
				break;
			}
			System.out.println(i + " - " + sortedCount.get(i));
			repeat++;
		}
	}

	public ArrayList<String> findNewWords(ArrayList<String> dictionary) {

		ArrayList<String> newWords = new ArrayList<String>();

		for (String s : allWords) {
			if (!dictionary.contains(s)) {
				newWords.add(s);
			}
		}

		sortWords(newWords);
		
		return newWords;

	}

	private ArrayList<String> sortWords(ArrayList<String> words) {

		Collections.sort(words);

		return words;

	}

	public void writeAllWordsInFile(String fileName) {

		sortWords(allWords);

		FileWriter fw = null;
		try {
			fw = new FileWriter(fileName);

			for (int i = 0; i < allWords.size(); i++) {
				fw.write(allWords.get(i) + "\n");
				fw.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (fw != null) {
			try {
				System.out.println("New file with all the distinct words from the book, in the alphabetical order, has been created.");
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
