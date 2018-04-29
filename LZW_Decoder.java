/*******************************
 * Name: Bronson Rikiriki      *
 * ID: 1257348                 *
 * Assignment 2 - LZW Decoder  *
 *******************************/

import java.io.*;
import java.util.*;
import java.lang.*;

public class LZW_Decoder {  
	
	/**
	 * Create a dictionary with the size of 4096 possible phrases
	 * and also initialising the first 256 phrases as all the 8bit
	 * binary patterns.
	 *
	 * @return dict - multidimensional array of phrases 
	 */
	public static byte[][] initialise_dictionary() {
		byte[][] dict = new byte[40960][];
		// For the 256 8bit patterns to add to the dictionary
		for(int i = 0; i < 256; i++){
			byte[] temp = new byte[1];
			temp[0] = (byte) i;
			dict[i] = temp;
		}
		byte[] reset = new byte[1];
		reset[0] = (byte) 0;
		dict[256] = reset; // This will be the reset symbol in the dictionary
		return dict;
	}
	
	/**
	 * Find a phrase in the dictionary.
	 *
	 * @param pNum - phrase number
	 * @return phrase - array of possible byte phrases
	 */
	public static byte[] find_phrase(int pNum) {
		byte[] phrase = dictionary[pNum];
		return phrase;
	}
	
	/**
	 * Add a new phrase to the dictionary.
	 *
	 * @param p - array of bytes
	 */
	public static void add_phrase(byte[] p) {
		int i = 0;
		while(dictionary[i] != null)
			i++;
		dictionary[i] = p;
	}

	/**
	 * Add a byte to the end of an array of bytes
	 *
	 * @param p - full byte array
	 * @param b - byte to add
	 * @return phrase - new byte array
	 */
	public static byte[] add_byte(byte[] p, byte b) {
		byte[] phrase = Arrays.copyOf(p, (p.length + 1));
		phrase[phrase.length - 1] = b;
		return phrase;
	}
			
	// Initialise the start of the dictionary
	public static byte[][] dictionary = initialise_dictionary();
	
	public static void main(String args[]) {  
		try{
			// Open the input file to start reading the phrase numbers
			File file = new File(args[0]);
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			String s; // For reading the phrase numbers on each line
			// Output stream to send the decoded bytes through
			String filename = args[0].substring(0, args[0].lastIndexOf('.')) + ".out";
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename));
			
			// Get the first phrase number, save it, then write it to the output stream
			s = br.readLine();
			int pNum = Integer.parseInt(s);
			byte[] phrase = find_phrase(pNum);
			dos.write(phrase, 0, phrase.length);
			// Holds the new phrase after decoding
			byte[] currentPhrase; 
			
			// Read a phrase number 
			while((s = br.readLine()) != null){
				pNum = Integer.parseInt(s);
				// If the reset symbol phrase number is read
				if(pNum == 256) {
					byte[][] newD = initialise_dictionary();
					dictionary = Arrays.copyOf(newD, 40960);
					phrase = new byte[0];
				}
				else{
					// If the phrase exists in the dictionary
					if(find_phrase(pNum) != null){
						currentPhrase = find_phrase(pNum); // Get the phrase
						dos.write(currentPhrase, 0, currentPhrase.length); // Output the phrase
						phrase = add_byte(phrase, currentPhrase[0]); // Create new phrase
						add_phrase(phrase); // Add the new phrase to the dictionary
						phrase = currentPhrase; // Set the start of the next new phrase
					}
					else{
						currentPhrase = add_byte(phrase, phrase[0]); // Create the next phrase 
						dos.write(currentPhrase, 0, currentPhrase.length); // Output the phrase
						add_phrase(currentPhrase); // Add the new phrase to the dictionary
						phrase = currentPhrase; // Set the start of the next new phrase
					}
				}
			}
			dos.flush(); // Flush all the bytes to generate the output file*/
		}
		catch(Exception e){
			System.err.println("Error: " + e);
		}
	}  
}  