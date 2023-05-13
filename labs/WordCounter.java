import java.util.Scanner;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class WordCounter{

	public static int processText(StringBuffer text, String stopword) throws InvalidStopwordException, TooSmallText{
		//System.out.println(text);
		boolean foundStopword = false;
		if (stopword == null)
			foundStopword = true;

		Pattern regex = Pattern.compile("[a-zA-Z0-9']+");
		Matcher regexMatcher = regex.matcher(text);
		int count = 0;
		while (regexMatcher.find()) {
			count++;
    		//System.out.println("I just found the word: " + regexMatcher.group());
    		if (count >= 5 && stopword != null && regexMatcher.group().equals(stopword)){
    			foundStopword = true;
    			break;
    		}
		} 

		if (count < 5)
			throw new TooSmallText("Only found " + count + " words.");
		
		if (!foundStopword)
			throw new InvalidStopwordException("Couldn't find stopword: " + stopword);

		return count;
	}

	public static StringBuffer processFile(String path) throws EmptyFileException{
		//System.out.println("path" + path);
		StringBuffer text = new StringBuffer();
		boolean foundFile = false;
		while (!foundFile) {
	  		try {
				String strCurrentLine = null;
				BufferedReader objReader = new BufferedReader(new FileReader(path));
				foundFile = true;
	   			while ((strCurrentLine = objReader.readLine()) != null) {
	   				text.append(strCurrentLine);
	   				//System.out.println(strCurrentLine);
	   			}
	   			objReader.close();
	   		} catch (IOException err1) {
	   			System.out.println("Sorry, we couldn't find that file. Could you please re-enter the path: ");
	   			try {
		   			Scanner keyboard = new Scanner(System.in);
		   			path = keyboard.nextLine();
		   		} catch (Exception err2) {
		   			path = null;
		   		}
		   	}
		}

		if (text.length() == 0)
			throw new EmptyFileException(path + " was empty");

		return text;
	}

	public static void main (String[] args){
		
		String option = null;
		while (option == null)
			try {
				Scanner keyboard = new Scanner(System.in);
				System.out.println("Enter (1) to process a file and (2) to process text on the command line: ");
				int optionInt = keyboard.nextInt();
				if (optionInt == 1 || optionInt == 2)
					option = "" + optionInt;
				else 
					System.out.println("You didn't enter a valid number, please try again.");
			} catch (Exception e){
				System.out.println("You didn't enter a valid number, please try again.");
			}

		StringBuffer text = null;
		if (option.equals("1"))
			try {
				text = processFile(args[0]);
			} catch (EmptyFileException e) {
				System.out.println(e);
				text = new StringBuffer("");
			}
		else 
			text = new StringBuffer(args[0]);

		String stopword = null;
		if (args.length > 1)
			stopword = args[1];

		try {
			System.out.println("Found " + processText(text, stopword) + " words.");
		} catch (InvalidStopwordException err1) {
			try {
				Scanner keyboard = new Scanner(System.in);
				System.out.println(err1 + " Could you enter a new one: ");
				stopword = keyboard.nextLine();
				System.out.println("Found " + processText(text, stopword) + " words.");
			} catch (Exception err2) {
				System.out.println("Sorry, things failed again.");
			} 
		} catch (TooSmallText e){
			System.out.println(e);
		}
	}
}