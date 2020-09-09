//package hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class handles reads a user given file then follows commands to
 * manipulate an array of sequences of DNA or RNA
 * 
 * @author Nikolo
 *
 */
public class Bioinfo {
	// static String type;
	// private static int arrayLength;
	// static Sequence [] sequences = new Sequence [8];
	// SLList <String> bases;

	public static void main(String[] args) throws FileNotFoundException {
		File f = new File(args[1]);
		Sequence[] sequences = new Sequence[Integer.parseInt(args[0])];
		int max = Integer.parseInt(args[0]);
		parseFile(f, sequences, max);
	}

	/**
	 * The following method parses the file given by the user. before reading
	 * through the commands of the file this method will fill the sequences array
	 * with empty data.
	 * 
	 * @param f
	 *            is the file given by the user
	 * @param sequences
	 *            is the array of Sequences
	 * @throws FileNotFoundException
	 */
	public static void parseFile(File f, Sequence sequences[], int max) throws FileNotFoundException {
		Scanner text = new Scanner(f);
		int l = 2;
		int pos;
		int pos1;
		int pos2;
		int start;
		int start1;
		int start2;
		int end;
		String type;
		String bases;
		createArray(sequences);// method to fill array with junk data
		while (text.hasNextLine()) {
			Scanner line = new Scanner(text.nextLine());
			while (line.hasNext()) {// while loop to iterate though each line
				String token = line.next().toLowerCase();
				if (token.contains("insert")) {
				//	System.out.println("		INSERT");
					pos = Integer.parseInt(line.next());
					type = line.next();
					if (pos <= max && !(pos < 0)) {
						if (line.hasNext()) {
							bases = line.next();
							insert(pos, type, bases, sequences);// insert method to insert new valid sequences
							}
							else {
								insert2(pos, type, sequences);
							}
					}
					else {
						System.out.println("User Alter! -- The given position is not vaild for insert!  Insert failed at position: " + pos);
					}
				} else if (token.contains("remove")) {
					//System.out.println("		REMOVE");
					token = line.next();
					pos = Integer.parseInt(token);
					remove(pos, sequences);// method to remove sequences
				} else if (token.contains("splice")) {
					//System.out.println("		SPLICE");
					pos = Integer.parseInt(line.next());
					type = line.next();
					bases = line.next();
					start = Integer.parseInt(line.next());
					splice(pos, type, bases, start, sequences);// method to splice DNA and RNA
				} else if (token.contains("clip")) {
					//System.out.println("		CLIP");
					pos = Integer.parseInt(line.next());
					start = Integer.parseInt(line.next());
					if (line.hasNext()) {
						end = Integer.parseInt(line.next());
						clip(pos, start, end, sequences); // method to clip start and end of a sequence
					} else {
						clip2(pos, start, sequences); // method that clips the ending of a sequecnes from a start point
					}
				} else if (token.contains("print")) {
					//System.out.println("		PRINT");
					if (line.hasNext()) {
						pos = Integer.parseInt(line.next());
						printPos(pos, sequences);// method to print a sequences at a given position
					} else {
						print(sequences);// method to print all valid sequences
					}

				} else if (token.contains("copy")) {
					//System.out.println("		COPY");
					pos1 = Integer.parseInt(line.next());
					pos2 = Integer.parseInt(line.next());
					copy(pos1, pos2, sequences);// method to copy one sequence to a new position

				} else if (token.contains("swap")) {
					//System.out.println("		SWAP");
					pos1 = Integer.parseInt(line.next());
					start1 = Integer.parseInt(line.next());
					pos2 = Integer.parseInt(line.next());
					start2 = Integer.parseInt(line.next());
					swap(pos1, start1, pos2, start2, sequences);// method that swaps two given positions of two DNA or
																// RNA sequences
				} else if (token.contains("transcribe")) {
					///System.out.println("		TRANSCRIBE");
					pos = Integer.parseInt(line.next());
					transcribe(pos, sequences);// method that converts a DNA into and RNA
				} else if (token.contains("overlap")) {
					//System.out.println("		OVERLAP");
					pos1 = Integer.parseInt(line.next());
					pos2 = Integer.parseInt(line.next());
					overlap(pos1, pos2, sequences);// method that checks if there is an overlap in the suffix and prfix
													// of two sequences
				} else if (token.contains("translate")) {
					//System.out.println("		TRANSLATE");
					pos = Integer.parseInt(line.next());
					translate(pos, sequences);
				} 
			}
		}
	}

	/**
	 * the following method fills the array with junk data
	 * 
	 * @param sequences
	 *            the array of sequences
	 */
	public static void createArray(Sequence sequences[]) {
		for (int i = 0; i < sequences.length; i++) {
			Sequence object = new Sequence();
			object.removeAll();// fills the object with junk data
			sequences[i] = object;
		}
	}

	/**
	 * this method inserts new valid sequences into the sequences array
	 * 
	 * @param pos
	 *            position of the array that the new sequence will be put
	 * @param type
	 *            a String of DNA or RNA
	 * @param bases
	 *            a String of bases that will be stored in a SLList
	 * @param sequences
	 *            the array of sequences
	 */
	public static void insert(int pos, String type, String bases, Sequence sequences[]) {
		if (checkBases(type, bases)) {// uses a helper method to see if the given DNA's or RNA's only contain valid
										// bases
			Sequence object = new Sequence();
			object.addType(type.toUpperCase());
			for (int i = 0; i < bases.length(); i++) {
				char temp1 = bases.charAt(i);
				String temp2 = Character.toString(temp1).toUpperCase();
				object.insertBase(temp2);
			}
			sequences[pos] = object;// inserts the new sequence into the array
		}
		else {
			System.out.println("User Alert! -- The given sequences bases were not correct for given type at position: " + pos);
		}
	}
	
	/**
	 * this method inserts DNA RNA or AA without bases
	 * @param pos position in the sequence array
	 * @param type the type of the sequences
	 * @param sequences the array of sequences
	 */
	public static void insert2(int pos, String type, Sequence sequences[]) {
		if(type.contains("DNA") || type.contains("RNA") || type.contains("AA")) {
			Sequence object = new Sequence();
			object.addType(type.toUpperCase());
			sequences[pos] = object;
		} 
	}

	/**
	 * this method removes sequences from the array
	 * 
	 * @param pos
	 *            position to be removed
	 * @param sequences
	 *            array of sequences
	 */
	public static void remove(int pos, Sequence sequences[]) {
		if (sequences[pos] == null  || sequences[pos].returnType().contains("-")) { // check is sequences is empty
			System.out.println("User Alert! -- There is no sequence in the given position.  Remove failed at position: " + pos);
		} else {
			sequences[pos].removeAll();// removes all from sequence
		}
	}

	/**
	 * prints all valid sequences
	 * 
	 * @param sequences
	 */
	public static void print(Sequence sequences[]) {
		for (int i = 0; i < sequences.length; i++) {
			if (sequences[i].returnType().contains("DNA") || sequences[i].returnType().contains("RNA")
					|| sequences[i].returnType().contains("AA")) {
				System.out.println(i + " " + sequences[i].returnType() + " " + sequences[i].returnBases());
			}
			else {
				System.out.println(i);
			}
		}
	}

	/**
	 * this method prints the sequence at a given position in the sequence array
	 * 
	 * @param pos
	 * @param sequences
	 */
	public static void printPos(int pos, Sequence sequences[]) {
		if (sequences[pos].returnType() == "-") {
			System.out.println("User Alert! -- There is no sequence in the given position.  Print failed at position: " + pos);
		} else {
			System.out.println(pos + " " + sequences[pos].returnType() + " " + sequences[pos].returnBases());
		}
	}

	/**
	 * this method splices the sequence at a given start position
	 * 
	 * @param pos
	 *            positions in the array
	 * @param type
	 *            string type of the sequences
	 * @param bases
	 *            new bases to be spliced into the SLList
	 * @param start
	 *            start posistion
	 * @param sequences
	 *            the array of sequences
	 */
	public static void splice(int pos, String type, String bases, int start, Sequence sequences[]) {
		if (sequences[pos].returnType() == "-") {
			System.out.println("User Alert! -- The selected position is empty.  Splice failed at position: " + pos);
		}
		else if (!checkBases(type, bases)) {
			System.out.println("User Alert! -- The given bases contain invalid charcaters.  Splice failed at position: " + pos);
		}
		else if (!sequences[pos].returnType().equals(type) || !sequences[pos].returnType().equals(type)) {
			System.out.println(
					"User Alert! -- The given types do not match, and there for, cannot be spliced.  Splice failed at position: " + pos);
		}
		else if (start >= sequences[pos].getSize()) {
			System.out.println("User Alert! -- The given start point is out of bounds.  Splice failed at position: " + pos);
		}
		else if (type.contains(sequences[pos].returnType())) {
			if (start > sequences[pos].getSize()) {
				System.out.println("User Alert! -- The give start point is too large.  Splice failed at position: " + pos);
			} else {
				for (int i = 0; i < bases.length(); i++) {
					char temp1 = bases.charAt(i);
					String temp2 = Character.toString(temp1);
					sequences[pos].insertBaseAt(start + i, temp2);
				}
			}
		} 
	}

	/**
	 * clips the bases of a sequences from the start to end point given
	 * 
	 * @param pos
	 *            position in the array
	 * @param start
	 *            start point of the clip
	 * @param end
	 *            end point of the clip
	 * @param sequences
	 *            array of sequences
	 */
	public static void clip(int pos, int start, int end, Sequence sequences[]) {
		if (start < 0) {
			System.out.println("User Alert! -- The start postion is less than zero.  Clip failed at position: " + pos);
		} else if (end > sequences[pos].getSize()) {
			System.out.println("User Alert! -- The given end point is greater than list length.  Clip failed!"); 
		}else if (start > sequences[pos].getSize() || end > sequences[pos].getSize()) {
			System.out.println("User Alert! -- The start and/or end point(s) are out of bound.  Clip failed at position: " + pos);
		} else if (end < start) {
			sequences[pos].removeBases();
		} else if (end == sequences[pos].getSize()){
			sequences[pos].clipFront(start);
			sequences[pos].clipEnd(0);
		} else {
			sequences[pos].clipFront(start);
			sequences[pos].clipEnd(end - start);
		}
	}

	/**
	 * This method clips the front of a given sequences if valid
	 * 
	 * @param pos
	 *            position of the sequences in the array
	 * @param start
	 *            the start point of the clip
	 * @param sequences
	 *            the array of sequences
	 */
	public static void clip2(int pos, int start, Sequence sequences[]) {
		if (sequences[pos].getSize() == 0) {
			System.out.println("User Alert! -- the given position does not contains a sequences.  Clip failed at position: " + pos);
		} else if (start < 0 || start > sequences[pos].getSize()) {
			System.out.println("User Alert! -- The given given start point is out of bounds.  Clip failed at position: " + pos);
		} else {
			sequences[pos].clipFront(start);
		}
	}

	/**
	 * copies one sequences to a new position in the sequence array
	 * 
	 * @param pos1
	 *            position of the sequences to be copied
	 * @param pos2
	 *            position where the new sequence will be put
	 * @param sequences
	 *            array of sequences
	 */
	public static <E> void copy(int pos1, int pos2, Sequence sequences[]) {
		if (sequences[pos1].returnType() == "-") {
			System.out.println("User Alert! -- The given position is empty.  Copy failed!" );
		} else {
			sequences[pos2].removeAll();// removes type and bases
			sequences[pos2].addType(sequences[pos1].returnType());// sets new type
			for (int i = 0; i < sequences[pos1].getSize(); i++) {
				sequences[pos2].addBase(sequences[pos1].getBase(i));// adds 1 base from pos2 at point i to pos1 at point
																	// i
			}
		}
	}

	/**
	 * method that swaps two portions of two sequences
	 * 
	 * @param pos1
	 *            the position of the first sequence
	 * @param start1
	 *            the start point of the swapped
	 * @param pos2
	 *            the position of the second sequence
	 * @param start2
	 *            the start point of the second sequence to be swapped
	 * @param sequences
	 *            the array of sequences
	 */
	public static void swap(int pos1, int start1, int pos2, int start2, Sequence sequences[]) {
		if (!sequences[pos1].returnType().contains(sequences[pos2].returnType())) {
			System.out.println(
					"User Alert! -- The given sequences are of different types and therefore, cannot be swaped.  Swap failed!");
		} else if (sequences[pos1].getSize() == 0 || sequences[pos2].getSize() == 0) {
			System.out.println(
					"User Alert! -- One or more of the given sequences is empty and therefore, cannot be swaped.  Swap failed!");
		} else if (start1 <= 0 || start2 <= 0) {
			System.out.println(
					"User Alert! -- One or more of the given start possitions is less than or equal to zero.  Swap failed!");
		} else if (start1 > sequences[pos1].getSize() || start2 > sequences[pos2].getSize()) {
			System.out.println(
					"User Aler! -- One or more of the given start positions is greater than the sequnces length.  Swap failed!");
		} else if (sequences[pos1].getSize() == start1) {
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			for(int i = start2; i < sequences[pos2].getSize(); i++) {
				sequences[pos1].addBase(sequences[pos2].getBase(i));
			}
			sequences[pos2].clipEnd(start2 - 1);
		}
		else if (sequences[pos2].getSize() == start2) {
			for(int i = start1; i < sequences[pos1].getSize(); i++) {
				sequences[pos2].addBase(sequences[pos1].getBase(i));
			}
			sequences[pos1].clipEnd(start1 -1);
		}
		
		else {
			Sequence object1 = new Sequence();
			Sequence object2 = new Sequence();
			object1.addBase(sequences[pos1].getBase(start1));
			for (int i = start1; i < sequences[pos1].getSize(); i++) {
				object1.addBase(sequences[pos1].getBase(i));
			}
			sequences[pos1].clipEnd(start1 - 1);
			object2.addBase(sequences[pos2].getBase(start2));
			for (int i = start2; i < sequences[pos2].getSize(); i++) {
				object2.addBase(sequences[pos2].getBase(i));
			}
			sequences[pos2].clipEnd(start2 - 1);
			for (int i = 0; i < object2.getSize(); i++) {
				sequences[pos1].addBase(object2.getBase(i));
			}
			for (int i = 0; i < object1.getSize(); i++) {
				sequences[pos2].addBase(object1.getBase(i));
			}
		}
	}

	/**
	 * this method transcribes the given sequences
	 * 
	 * @param pos
	 *            position of the sequence to be transcribed
	 * @param sequences
	 *            the array of sequences
	 */
	public static void transcribe(int pos, Sequence sequences[]) {
		if (sequences[pos].returnType().contains("RNA")) {
			System.out.println("User Alert! -- The given sequences is already RNA.  Translate failed at position: " + pos);
		} else if (sequences[pos].returnType().contains("-")) {
			System.out.println("User Alert! -- The given sequences is empty.  Translate failed at position: " + pos);
		} else if (sequences[pos].returnType().contains("DNA")) {
			sequences[pos].addType("RNA");
			for (int i = 0; i < sequences[pos].getSize(); i++) {
				if (sequences[pos].getBase(i).contains("T")) {
					sequences[pos].insertBaseAt(i, "U");
					sequences[pos].removeSingleBase(i);
				}
			}
			for (int i = 0; i < sequences[pos].getSize(); i++) {
				if (sequences[pos].getBase(i).contains("A")) {
					sequences[pos].insertBaseAt(i, "U");
					sequences[pos].removeSingleBase(i);

				} else if (sequences[pos].getBase(i).contains("U")) {
					sequences[pos].insertBaseAt(i, "A");
					sequences[pos].removeSingleBase(i);
				} else if (sequences[pos].getBase(i).contains("C")) {
					sequences[pos].insertBaseAt(i, "G");
					sequences[pos].removeSingleBase(i);

				} else if (sequences[pos].getBase(i).contains("G")) {
					sequences[pos].insertBaseAt(i, "C");
					sequences[pos].removeSingleBase(i);
				}
			}
			sequences[pos].reverse();
		}
	}

	/**
	 * this method translates the given sequence to an amino acid
	 * 
	 * @param pos
	 *            position of the sequences to be translated
	 * @param sequences
	 *            the array of sequences
	 */
	public static void translate(int pos, Sequence sequences[]) {
		int start = 0;
		if (sequences[pos].returnType().contains("RNA")) { // if valid
			if (checkStart(sequences, pos)) {
				Sequence temp1 = new Sequence();
				Sequence temp2 = new Sequence();
				for (int i = 0; i < sequences[pos].getSize() - 2; i++) {
					if (sequences[pos].getBase(i).contains("A") && sequences[pos].getBase(i + 1).contains("U")
							&& sequences[pos].getBase(i + 2).contains("G")) {
						start = i + 3;
						temp1.addBase("M");
						i = sequences[pos].getSize() - 2;
					}
				}
				for (int i = start; i < sequences[pos].getSize() - 2; i += 3) {
					temp2.addBase(sequences[pos].getBase(i));
					temp2.addBase(sequences[pos].getBase(i + 1));
					temp2.addBase(sequences[pos].getBase(i + 2));
					if (temp2.returnBases().contains("UUU") || temp2.returnBases().contains("UUC")) {
						temp1.addBase("F");
						temp2.removeAll();
					} else if (temp2.returnBases().contains("UUA") || temp2.returnBases().contains("UUG")
							|| temp2.returnBases().contains("CUU") || temp2.returnBases().contains("CUC")
							|| temp2.returnBases().contains("CUA") || temp2.returnBases().contains("CUG")) {
						temp1.addBase("L");
						temp2.removeAll();
					} else if (temp2.returnBases().contains("AUU") || temp2.returnBases().contains("AUC")
							|| temp2.returnBases().contains("AUA")) {
						temp1.addBase("I");
						temp2.removeAll();
					} else if (temp2.returnBases().contains("GUU") || temp2.returnBases().contains("GUC")
							|| temp2.returnBases().contains("GUA") || temp2.returnBases().contains("GUG")) {
						temp1.addBase("V");
						temp2.removeAll();
					} else if (temp2.returnBases().contains("UCU") || temp2.returnBases().contains("UCC")
							|| temp2.returnBases().contains("UCA") || temp2.returnBases().contains("UCG")) {
						temp1.addBase("S");
						temp2.removeAll();
					} else if (temp2.returnBases().contains("CUU") || temp2.returnBases().contains("CCC")
							|| temp2.returnBases().contains("CCA") || temp2.returnBases().contains("CCG")) {
						temp1.addBase("P");
						temp2.removeAll();
					} else if (temp2.returnBases().contains("ACU") || temp2.returnBases().contains("ACC")
							|| temp2.returnBases().contains("ACA") || temp2.returnBases().contains("ACG")) {
						temp1.addBase("T");
						temp2.removeAll();
					} else if (temp2.returnBases().contains("GCU") || temp2.returnBases().contains("GCC")
							|| temp2.returnBases().contains("GCA") || temp2.returnBases().contains("GCG")) {
						temp1.addBase("A");
						temp2.removeAll();
					} else if (temp2.returnBases().contains("UAU") || temp2.returnBases().contains("UAC")) {
						temp1.addBase("Y");
						temp2.removeAll();
					} else if (temp2.returnBases().contains("CAU") || temp2.returnBases().contains("CAC")) {
						temp1.addBase("H");
						temp2.removeAll();
					} else if (temp2.returnBases().contains("CAA") || temp2.returnBases().contains("CAG")) {
						temp1.addBase("Q");
						temp2.removeAll();
					} else if (temp2.returnBases().contains("AAU") || temp2.returnBases().contains("AAC")) {
						temp1.addBase("N");
						temp2.removeAll();
					} else if (temp2.returnBases().contains("AAA") || temp2.returnBases().contains("AAG")) {
						temp1.addBase("K");
						temp2.removeAll();
					} else if (temp2.returnBases().contains("GAU") || temp2.returnBases().contains("GAC")) {
						temp1.addBase("D");
						temp2.removeAll();
					} else if (temp2.returnBases().contains("GAA") || temp2.returnBases().contains("GAG")) {
						temp1.addBase("E");
						temp2.removeAll();
					} else if (temp2.returnBases().contains("UGU") || temp2.returnBases().contains("UGC")) {
						temp1.addBase("C");
						temp2.removeAll();
					} else if (temp2.returnBases().contains("UGG")) {
						temp1.addBase("W");
						temp2.removeAll();
					} else if (temp2.returnBases().contains("CGU") || temp2.returnBases().contains("CGC")
							|| temp2.returnBases().contains("CGA") || temp2.returnBases().contains("CGG")
							|| temp2.returnBases().contains("AGA") || temp2.returnBases().contains("AGG")) {
						temp1.addBase("R");
						temp2.removeAll();
					} else if (temp2.returnBases().contains("AGU") || temp2.returnBases().contains("AGC")) {
						temp1.addBase("S");
						temp2.removeAll();
					} else if (temp2.returnBases().contains("GGU") || temp2.returnBases().contains("GGC")
							|| temp2.returnBases().contains("GGA") || temp2.returnBases().contains("GGG")) {
						temp1.addBase("G");
						temp2.removeAll();
					} else if (temp2.returnBases().contains("UAA") || temp2.returnBases().contains("UAG")
							|| temp2.returnBases().contains("UGA")) {
						i = sequences[pos].getSize() - 2;
						temp2.removeAll();
					}
				}
				sequences[pos].removeAll();// removes type and bases
				sequences[pos].addType("AA");// sets new type to AA
				for (int i = 0; i < temp1.getSize(); i++) {
					sequences[pos].addBase(temp1.getBase(i));// adds the bases from temp1 to the sequences
				}
			}
		}
	}

	/**
	 * this method checks if there is an overlap between two given sequences
	 * 
	 * @param pos1
	 *            position of the first sequences
	 * @param pos2
	 *            position of the second sequence
	 * @param sequences
	 */
	public static void overlap(int pos1, int pos2, Sequence sequences[]) {
		//change if statment
		int min = 0;
		if (sequences[pos1].returnType().contains("-") || sequences[pos2].returnType().contains("-") || sequences[pos1].getSize() == 0 || sequences[pos2].getSize() == 0) {
			System.out.println("User Alert! -- There is no sequnces at one or both given possitions.  Overlap failed!");
		} else if (!sequences[pos1].returnType().contains(sequences[pos2].returnType())) {
			System.out.println("User Alert! -- The given sequence types do not match.  Overlap failed.");
		} else {
			min = Math.min(sequences[pos1].getSize(), sequences[pos2].getSize());
			Sequence object1 = new Sequence();
			Sequence object2 = new Sequence();
			while (min != 0) {
				for (int i = 0; i < min; i++) {
					object2.addBase(sequences[pos2].getBase(i));
				}
				if (sequences[pos1].getSize() > min) {
					for (int i = sequences[pos1].getSize() - min; i < sequences[pos1].getSize(); i++) {
						object1.addBase(sequences[pos1].getBase(i));
					}
				} else if (sequences[pos1].getSize() == min) {
					for (int i = 0; i < sequences[pos1].getSize(); i++) {
						object1.addBase(sequences[pos1].getBase(i));
					}
				}
				if (object1.returnBases().contains(object2.returnBases())) {
					if (sequences[pos1].getSize() == min) {
						System.out.println("Overlap starts at index 0; bases that overlap " + object1.returnBases());
						min = 0;
					} else {
						System.out.println("Overlap starts at index " + (sequences[pos1].getSize() - min)
								+ "; bases that overlap " + object1.returnBases());
						min = 0;
					}
				} else {
					min--;
					object1.removeBases();
					object2.removeBases();
					if (min == 0) {
						System.out.println("The two given sequences do not contain an overlap.");
					}
				}
			}
		}
	}

	/**
	 * this is a helper method that checks of a given DNR or RNA only contain valid
	 * bases.
	 * 
	 * @param type
	 *            string of the type, either RNA or DNA if valid
	 * @param bases
	 *            string of bases
	 * @return true if all the bases are valid for their given type.
	 */
	public static boolean checkBases(String type, String bases) {

		int count = 0;
		if (type.toUpperCase().equals("RNA")) {
			if (bases == null || bases == "") {
				return true;
			} else { // make sure bases only have AUGC
				for (int i = 0; i < bases.length(); i++) {
					if (bases.toUpperCase().charAt(i) == 'A' || bases.toUpperCase().charAt(i) == 'U'
							|| bases.toUpperCase().charAt(i) == 'G' || bases.toUpperCase().charAt(i) == 'C') {
						count++;
					}
				}
				if (count == bases.length()) {
					return true;
				}

				else {
					return false;
				}
			}
		} else if (type.toUpperCase().equals("DNA")) {
			// make sure bases only have ATGC
			if (bases == null || bases == "") {
				return true;
			} else {
				for (int i = 0; i < bases.length(); i++) {
					if (bases.toUpperCase().charAt(i) == 'A' || bases.toUpperCase().charAt(i) == 'T'
							|| bases.toUpperCase().charAt(i) == 'G' || bases.toUpperCase().charAt(i) == 'C') {
						count++;
					}
				}
				if (count == bases.length()) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * this method checks to see if the given sequences contains a start codon
	 * 
	 * @param sequences
	 *            the array of sequences
	 * @param pos
	 *            the position of the sequences to be checked
	 * @return return true or false
	 */
	public static boolean checkStart(Sequence sequences[], int pos) {
		// Sequence temp = new Sequence();
		for (int i = 0; i < sequences[pos].getSize() - 2; i++) {
			if (sequences[pos].getBase(i).contains("A") && sequences[pos].getBase(i + 1).contains("U")
					&& sequences[pos].getBase(i + 2).contains("G")) {
				int start = i + 3;
				if (checkStop(sequences, pos, start)) {
					return true;
				} else {
					System.out.println("User Alert! -- The given sequences does not contain a valid stop codon.  Translate Failed!");
					return false;
				}
			}
		}
		System.out.println("User Alert! -- The given sequences does not contain a valid start codon.  Translate Failed!");
		return false;
	}

	/**
	 * this method checks to see if the given sequence contains a stop codon
	 * 
	 * @param sequences
	 *            the array of sequences
	 * @param pos
	 *            the position of the sequence to be checked
	 * @param start
	 *            the start point of checking for a valid codon
	 * @return true or false
	 */
	public static boolean checkStop(Sequence sequences[], int pos, int start) {
		Sequence temp = new Sequence();
		for (int i = start; i < sequences[pos].getSize() - 2; i += 3) {
			temp.addBase(sequences[pos].getBase(i));
			temp.addBase(sequences[pos].getBase(i + 1));
			temp.addBase(sequences[pos].getBase(i + 2));
			if (temp.returnBases().contains("UAA") || temp.returnBases().contains("UAG")
					|| temp.returnBases().contains("UGA")) {
				return true;
			}
			temp.removeBases();
		}
		return false;
	}
}