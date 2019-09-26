package mainPackage;

import java.sql.Connection;
import java.util.ArrayList;


public class MainFinal {

	public static void main(String[] args) {

		// conf 1: src\knjiga src\Dictionary.db src\SortedListOfWords

		String conStr = args[1];
		DBConnection connect = DBConnection.getConnection(conStr);
		Connection con = connect.connect();

		String fileToRead = args[0];
		Book book = new Book(fileToRead);

		book.allWords = book.readBookFromFile();

		System.out.println("Number of distinct words in the book: " + book.allWords.size() + "\n");

		DBDictionaryEntries d = new DBDictionaryEntries(con);
		ArrayList<String> dictionaryEntries = d.allWordsFromEntries();

		System.out.println("Number of words in the dictionary: " + dictionaryEntries.size() + "\n");

		ArrayList<String> newWords = book.findNewWords(dictionaryEntries);

		System.out.println("Number of new words: " + newWords.size() + "\n");

		book.print20mostlyUsedWords();

		DBBookEntries d2 = new DBBookEntries(con);
		d2.addNewWords(newWords);

		connect.disconnect();

		String fileTowrite = args[2];
		book.writeAllWordsInFile(fileTowrite);
	}
}
