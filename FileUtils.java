/**
 * File Utilities for reading and writing
 *
 * @author William Zhang
 * @since 08/25/2022
 */

import java.io.File; import java.io.PrintWriter;
import java.io.FileNotFoundException; import java.util.Scanner;
public class FileUtils{
	/**
	 * opens a file to read using the Scanner class.
	 * @param fileName 		Name of the file to open
	 * @return the Scanner object to the file
	 */
	 
	public static java.util.Scanner openToRead(String fileName){
		java.util.Scanner input = null;
		try{
			input = new java.util.Scanner(new File(fileName));
		} catch(FileNotFoundException e){
			System.err.println("ERROR: Cannot open " + fileName + " for reading.");
			System.exit(-1);
		}
		return input;
	}
	/**
	 * opens a file to read using the PrintWriter class.
	 * @param fileName		name of the file to open
	 * @return the PrintWriter object to the file.
	 */
	public static PrintWriter openToWrite(String fileName){
		PrintWriter output = null;
		try{
			output = new PrintWriter(new File(fileName));
		} catch(FileNotFoundException e){
			System.err.println("ERROR: Cannot open " + fileName + " for writing.");
			System.exit(-1);
		}
		return output;
	}
}
