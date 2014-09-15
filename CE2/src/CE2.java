/**
 * TextBuddy.java
 * 
 * This class allows users to store messages into a file they request with the 
 * provided filename by them, user can add, delete, clear and exit in the program.
 * 
 * This program is written with the below assumptions:
 * 1. Users only uses the commands provided.
 * 2. Although simple out of scope error handling has been coded, 
 * 	  it is still assumed that users uses this program as shown below, not any old, how.
 * 3. Existing Files will be overwritten.
 * 
 * In regards to the module handbook, in terms of how the files are saved, I guess my choice is (a) however
 * Files will only be saved in certain functions such as when user hits, display/delete content.
 * Reason why is because to save after each 'add' command would be too in-efficient.
 * 
 * Available Commands for users are : add, display, delete, clear & exit
 * 
 * Below is the format in which user should follow,
 * or else users would be prompt with the wrong inputs.
 * 
 * 		Welcome To TextBuddy! Your file textbuddy.txt is ready for use.
 * 		Command: added to textbuddy.txt: "i love dao suan"
 * 		Command: added to textbuddy.txt: "i love dao hui"
 * 		Command: added to textbuddy.txt: "i love dao zang"
 * 		Command: clear
 * 		All content(s) deleted from textbuddy.txt
 * 		Command: delete 13
 * 		Deleting index out of scope, data does not exist.
 * 		Command: add i love mee pok
 * 		added to textbuddy.txt: "i love mee pok"
 * 		Command: delete 1
 * 		deleted from textbuddy.txt: "i love mee pok"
 * 		Command: add i love mee kia
 * 		added to textbuddy.txt: "i love mee kia"
 * 		Command: add i love mee sotong
 * 		added to textbuddy.txt: "i love mee sotong"
 * 		Command: add i love mee goreng
 * 		added to textbuddy.txt: "i love mee goreng"
 * 		Command: display
 * 		1: i love mee kia
 * 		2: i love mee sotong
 * 		3: i love mee goreng
 * 		Command:exit 
 *
 * @author Bay Chuan Wei Candiie
 */

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


public class CE2
{
	
	/*Declarations of Constants for input Commands */
	private static final String CMD_ADD = "add";
	private static final String CMD_DISPLAY = "display";
	private static final String CMD_DELETE = "delete";
	private static final String CMD_CLEAR = "clear";
	private static final String CMD_EXIT = "exit";
	private static final String CMD_CHECK = "check";
	private static final String CMD_SORT = "sort";
	
	private static final String MSG_EMPTY_FILE = "%s is empty";	
	private static final String MSG_NO_CMD = "No such command \"%s\" " ;
	private static final String MSG_EMPTY_STRING = "Input string is empty";
	private static final String MSG_FOUND_STRING = "word found";
	private static final String MSG_NOTFOUND_STRING = "not found";
	private static final String MSG_LIST_EMPTY = "Display List is empty";
	private static final String MSG_LIST_SORTED = "Display List is sorted";
	
	/*Declarations of Class Objects */
	private static File file;
	private static String filename;
	private static Scanner userInput;
	private static BufferedWriter fileWriter;
	
	
	/**
	 * Constructor initializes filename and file to null.
	 */
	public CE2()
	{
		filename = null;
		file = null;
	}
	
	/**
	 * Main function of program.
	 * Requests for valid filename.
	 * Creates file and run specified tasks by user. 
	 */
	public static void main(String[] args) throws IOException 	
	{
		do{
			requestFileName();
		}while(!checkValidFileName());
		
		createNewFile();
		printWelcomeMessage();
		getTask();
	}
	
	/**
	 * Request for user's desired filename for the file to be written to.
	 */
	private static void requestFileName()
	{
		userInput = new Scanner(System.in);
		filename = userInput.next();
	}
	
	/**
	 * Checks for valid filename that user has input.
	 * 
	 * @return true = valid | false = invalid filename.
	 * @throws if invalid filename, output a msg to alert user.
	 */
	private static boolean checkValidFileName()
	{
		file = new File(filename);
		
		try 
		{
			file.getCanonicalPath();
			return true;
		} 
		catch (IOException e) 
		{
			System.out.println("Not a valid filename, please input a valid filename");
			return false;
		}
	}

	/**
	 * Creates a new BuffereWriter to write the future data inputs.
	 *
	 * @throws IOException - if file can't be created (perhaps its opened etc.)
	 */
	private static void createNewFile() throws IOException 
	{
		fileWriter = new BufferedWriter(new FileWriter(file));
	}
	
	/**
	 * Prints the WelcomeMessage to remind user of the filename they have input.
	 * 
	 */
	private static void printWelcomeMessage() 
	{
		System.out.println("Welcome To TextBuddy! Your file " 
				+ filename + " is ready for use." );
	}

	/**
	 * Run the desired task with the command given by user.
	 * If command doesn't exist, a message will be displayed to alert user.
	 * 
	 * @throws IOException - on the function that is called.
	 */
	private static void getTask() throws IOException
	{	
		String userCommand = null;
		
		do{
			//Ask for user's input.
			System.out.print("Command: ");
			userCommand = userInput.next();
			
			switch(userCommand)
			{
				case CMD_ADD:
					addContent(userInput.nextLine());
					break;
	
				case CMD_DISPLAY:
					displayContents();
					break;
					
				case CMD_DELETE:
					deleteContent(userInput.nextInt());
					break;
	
				case CMD_CLEAR:
					clearContents(true);
					break;
					
				case CMD_CHECK:
					
					
					if(userInput.hasNext()){	
						System.out.println(checkContains(userInput.next(),getFileData()));	
					}
					else
					{
						checkContains("",getFileData());				
					}
					
					break;
				
				case CMD_SORT:
					//List<String> datas = new LinkedList<String>();
					
					sortContents(getFileData());
					break;	
					
				default:					
					if(!userCommand.equals(CMD_EXIT))
					{
						printErrorMsg(MSG_NO_CMD,userCommand);	
					}
					break;
			}
			
		}while(!userCommand.equals(CMD_EXIT));
		
		exitProgram();
	}

	/**
	 * 'add' command called by user. Data 'add' is written into the buffer.
	 * 
	 * @throws IOException - if unable to write to fileWriter
	 */
	private static void addContent(String data) throws IOException 
	{	
		//Write user 'add'(ed) data into buffer.
		fileWriter.write(data.trim());
		fileWriter.newLine();
		System.out.println("added to " + filename + ": \"" + data.trim() +"\"");
		
		//return "added to " + filename + ": \"" + data.trim() +"\"";
	}
	
	/**
	 * Display Contents that have been 'add'ed by the user from 1,2... .
	 * 
	 * @throws IOException - if file can't be written to for saving purpose.
	 */
	public static void displayContents() throws IOException 
	{
		// To update & ensure that user 'add'(s) are written to file first.
		writeToFile();
		
		List<String> datas = getFileData();
		
		//Display current data(s) in file.
		if(datas.isEmpty())
		{
			printErrorMsg(MSG_EMPTY_FILE,filename);
		}
		else
		{
			for (String data:datas)
			{
				System.out.println(datas.indexOf(data)+1 + ": " + data);
			}
		}
		
	}

	/**
	 * Deletes the user's selected content.
	 * 
	 * @throws IOException - if file can't be written to for saving purpose.
	 */
	public static void deleteContent(int id) throws IOException 
	{
		// To ensure that user 'add'(s) are written to file first.
		writeToFile(); 
		
		if((id-1) >= getFileData().size() || (id-1) < 0) //Checks if input index out of scope
		{
			System.out.println("Deleting index out of scope, data does not exist.");
		}
		else if(!getFileData().isEmpty())
		{
			//Retrieving & Removing user selected data via specified id.
			List<String> datas = getFileData();	
			String deletedContent = datas.get(id-1);
			datas.remove(id-1);
		
			//Write updated data (with the deleted item gone).
			writeNewDatas(datas);

			System.out.println("deleted from " + filename + ": \"" + deletedContent + "\"");
		}
		else
		{
			printErrorMsg(MSG_EMPTY_FILE,filename);
		}
	}

	/**
	 * Clears all contents in the current file.
	 * 
	 * @param isUserCall - check if 'clear' func is called by user & 
	 * 					content delete function have to be printed
	 * 
	 * @throws IOException - if file can't be close/created.
	 */
	public static void clearContents(boolean isUserCall) throws IOException 
	{
		fileWriter.close();
		createNewFile();
		
		if(isUserCall)
		{
			System.out.println("All content(s) deleted from " + filename);
		}
	}
	
	/**
	 * Get data(s) from file and keep into the List<String>
	 * 
	 * @return the list of data(s)
	 * @throws IOException 
	 */
	private static List<String> getFileData() throws IOException
	{
		//Stores current data(s) in file into a List to assist removal/retrival of object.
		List<String> datas = Files.readAllLines(Paths.get(filename), 
				Charset.forName("UTF-8"));
		
		return datas;
	}
	
	/**
	 * Flush the content(s) in the BufferWriter into the actual file.
	 * 
	 * @throws IOException 
	 */
	private static void writeToFile() throws IOException
	{
		fileWriter.flush(); //flush the current 'add' content into the file
	}
	
	/**
	 * Clear Contents from file before re-writing the data(s) into file.
	 * 
	 * @throws IOException 
	 */
	private static void writeNewDatas(List<String> datas) throws IOException
	{
		//Clear data from file to prevent duplicates.
		clearContents(false);
		
		//Write new data into the buffer.
		for(String data:datas)
		{
			fileWriter.write(data);
			fileWriter.newLine();
		}
		
		writeToFile();
	}
	
	/**
	 * Exits the program, but closes the Scanner and 
	 * BufferedWriter before exiting to prevent leaks.
	 * 
	 * @throws IOException - if unable to close Scanner/BufferedWriter
	 */
	private static void exitProgram() throws IOException 
	{
		userInput.close();
		fileWriter.close();	
		System.exit(0);
	}

	/**
	 * Prints the Error Message to user upon entering wrong command.
	 * Also may used for debugging purposes.
	 * 
	 */	
	private static void printErrorMsg(String format, Object... args )
	{
		String msg = String.format(format, args);
		System.out.println(msg);
	}
	
	/**
	 * @throws IOException 
     *
	 *
	 * 
	 */	
	public static String checkContains(String checkString,List<String> datas) throws IOException{
		
		String retString = MSG_NOTFOUND_STRING;
		
		if(checkString == null || checkString.isEmpty() ||  datas == null || datas.isEmpty() )
		{
			return MSG_EMPTY_STRING;
		}
		
		//If the buffer is not null or empty, push into the storage
		if(fileWriter != null){
			writeToFile();
		}
	
		for(String data:datas){
			if(data.contains(checkString)){
				retString = MSG_FOUND_STRING;
				break;
			}
		}
		
		
		return "\"" + checkString + "\"" + " " + retString;
	}
	
	public static String sortContents(List<String>datas){
	
		String returnMSG = MSG_LIST_EMPTY;
		
		if(datas == null || datas.isEmpty())
		{
			return returnMSG;
		}
		
		return returnMSG;
	}
	/**
    *
	 *
	 * 
	 */	
	
}
