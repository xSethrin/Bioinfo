//package hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * this class creates the sequence objects
 * @author Nikolo
 *
 */

public class Sequence{
	String type;
	SLList <String> bases = new SLList <String>();
/**
 * this method adds the type to the sequence
 * @param passed the sting passed to the class
 */
	public void addType(String passed) {
		type = passed;
	}
	
	/**
	 * this method adds the bases to a SLList
	 * @param token the single letter to be inserted into the SLList
	 */
	public void insertBase(String token) {
		bases.add(token);
	}
	
	/**
	 * this method gets the bases at a specified index in the SLList
	 * @param index index of the SLList
	 * @return returns the base
	 */
	public String getBase(int index) {
		return bases.getNode(index).getElement();//returns element of given index of node
	}
	
	/**
	 * this method adds a base at the end of the SLList
	 * @param item the base to be added
	 */
	public void addBase(String item) {
		bases.add(item);//add string to SLList
	}
	
	/**
	 * this method grabs a node at the given index
	 * @param index the index of the node
	 * @return returns the node
	 */
	public Node<String> getNode(int index) {
		return bases.getNode(index);
	}
	/**
	 * this method removes all the nodes and sets the type to junk data
	 */
	public void removeAll() {
		type = "-";
		bases.clear();
	}
	
	/**
	 * this method removes all the bases from the SLList
	 */
	public void removeBases() {
		bases.clear();
	}
	
	/**
	 * this method removes a single base at the given index
	 * @param index the index of the base to be removed
	 */
	public void removeSingleBase(int index) {
		bases.remove(index);
	}
	
	/**
	 * this method returns the string of the type of a sequence
	 * @return returns the type of the sequencs
	 */
	public String returnType() {
		return type;
	}
	
	/**
	 * this method returns the bases as strings 
	 * @return returns all the bases in the sequence
	 */
	public String returnBases() {
		return bases.toString();//.replace("==>", "");
	}
	
	/**
	 * this method inserst a base at a given position in the SLList
	 * @param index the index that the bases is inserted
	 * @param token the base
	 */
	public void insertBaseAt(int index, String token) {
		bases.insert(index, token);
	}
	
	/**
	 * this method returns the size of the SLList
	 * @return returns the size of the list
	 */
	public int getSize() {
		return bases.length();
	}
	
	/**
	 * this method sets a new head for the SLList and removes all bases infront of it
	 * @param start the position of the new head
	 */
	public void clipFront(int start) {
		bases.setHead(start);
	}
	
	/**
	 * this method clips the end of a SLList 
	 * @param end the new end of the SLList
	 */
	public void clipEnd(int end) {
		bases.clipEnd(end);
	}
	
	 /**
	  * this method reverses the SLList
	  */
	public void reverse() {
		bases.reverse();
	}
}
