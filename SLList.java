//package hw1;

import java.util.*;
import java.lang.*;
/**
* implements a single-linked list.  These methods are used to manipulate nodes
* @author Nikolo
*/

public class SLList<E> implements List<E>{
	private Node<E> head;
	private int size = 0; //the number of elements in list
	
	/** constructors*/
	public SLList(E item){
		head = new Node<E>(item);
		size++;
	}
	
	public SLList (){
		head = null;
		size = 0;
	}

	 /** Remove all contents from the list, so it is once again
      empty. */
	public void clear(){
		head = null;
		size = 0;
	}
  
	
	
	/** Insert an element at the given location. 
		* allows you to insert after the tail
		* @param item The element to be inserted. 
		*/
    public void insert(int index, E item){
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException(Integer.toString(index));
		}
		if (index == 0){//new head
			addFirst(item);//helper method
		}
		else{
			Node<E> node = getNode(index-1);//using helper
			addAfter(node, item);
		}
	}
	/**
	* creates a new head
	*@param item the item to go into the new head
	*/
	private void addFirst(E item){
		head = new Node <E>(item, head);
		size++;
	}
	
	/**
	* 
	*/
	private void addAfter(Node<E> node, E item){
		node.setNext(new Node<E>(item, node.getNext()));
		size++;
	}
	
	/**
	* helper, return node at given index
	*@param index the index of the node to get
	
	*/
	public Node<E> getNode(int index){
		if (index < 0 || index >=size){
			throw new IndexOutOfBoundsException(Integer.toString(index));
		}
		Node<E> node = head;
		for(int i = 0; i < index; i++){
			node = node.getNext();
		}
		return node;
	}

    
	/** Append an element at the end of the list.
	*  @param item The element to be appended.
	*/
	public void add(E item){
		if (size == 0) {
			addFirst(item);
		}
		else {
			Node<E> node = head;
				for(int i = 0; i < size-1; i++) {
					node = node.getNext();
				}
				node.setNext(new Node<E>(item));
				size++;
		}	
	}
  
  
	/** 
	* Remove the  element at the given location.
	 * @return 
	*/
	public void remove(int index){
		Node<E> curr = head;
		Node<E> prev = null;
		Node<E> next = null;
		for(int i = 0; i <= index; i++) {
			prev = curr;
			curr = curr.getNext();
		}
		next = curr.getNext();
		prev.setNext(next);
		size--;
	}
	
	/**
	 * this method clips the end of a sllist by setting the next node to null
	 * @param index index pasased to be last in list
	 */
	public void clipEnd(int index) {
		Node<E> node = getNode(index);
		node.setNext(null);
		size = index;
	}
	
	/**
	 * this method sets a new head and gets rid of anything before it in the sllist
	 * @param index index to be the new head
	 */
	public void setHead(int index) {
		head = getNode(index);
		size = size - index;
	}
  
  
  
	/** 
	* Get the element in the position to one step left. 
	* @return element in the node to the left of the node at the index, 
	* null if at the head. 
	*/  
	public E prev(int index){
		Node<E> node = head;
		node = getNode(index-1);
		return node.getElement();
	}
  
  
	/** Get the element in the position one step right. 
	* @return the element in the node to the right of 
	* the node at the index, null if at the end. 
	*/
	public E next(int index){
		Node<E> node = head;
		node = getNode(index+1);
		return node.getElement();
	}
  
  
	/** @return The number of elements in the list. */
	public int length(){
		return size;
	}
    
    
	/** Turn the contents of the Nodes to a string in order from head to end.
	* @return The String representation of the 
	* elements in the list from head to end. 
	* A ==> B ==> C
	*/
	public String toString(){
		Node<E> node = head;
		String result = "";
		while(node != null){
			result = result + node.getElement().toString();
			if (node.getNext() !=null){
				result = result + "==>";
			}
			node = node.getNext();
		}
		return result;
	}
  
	/** Reverse the content of the list.
		* if list is A => B => C it becomes C => B => A
		*/
	public void reverse(){
		Node<E> node = head;
		if(node == null || node.getNext() == null){
			return;
		}
		Node<E> prev = node.getNext();
		Node<E> curr = prev.getNext();
		prev.setNext(node);
		node.setNext(null);
		
		while(curr != null){
			Node<E> next = curr.getNext();
			curr.setNext(prev);
			prev = curr;
			curr = next;
		}
		head = prev;
	}
  
   
   /** @return The  element at given position. */
   public E getValue(int index){
	   if(index < 0 || index >= size){
		   throw new IndexOutOfBoundsException(Integer.toString(index));
	   }
	   Node<E> node = getNode(index);
	   return node.getElement();
   }
	/**inserts the given list after the given index
	*@param list the list to be insterted
	* @param index the location for insertion after the index
	*/
	public void insertList (SLList list, int index){
		if (list.length() == 0) {
			return;
		}
		if (index == size -1) {
			Node<E> nodeB1 = list.getHead();
			Node<E> nodeAL = getLast();
			nodeAL.setNext(nodeB1);
			size = size + list.size;
			return;
		}
		Node<E> nodeB1 = list.getHead();
		Node<E> nodeBL = list.getLast();
		Node<E> nodeA1 = getNode(index);
		Node<E> nodeA2 = getNode(index + 1);
		nodeBL.setNext(nodeA2);
		nodeA1.setNext(nodeB1);
		size = size+list.length();	
	}

	/**
	* @retunrs the head of the list
	*/
	public Node<E> getHead(){
		return head;
	}
		
	/**
	*@returns the tail of the list
	*/
	public Node<E> getLast(){
		return getNode(size-1);
	}

	
 
}
