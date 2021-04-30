package com.mgg;

import java.util.Iterator;

public class LinkedListIterator<T> implements Iterator<T> {
	Node<T> currentNode = null;
	
	public LinkedListIterator(LinkedList<T> linkedList) {
		this.currentNode = linkedList.getstartSale();
	}
	
	@Override
	public boolean hasNext() {
		return this.currentNode != null;
	}

	@Override
	public T next() {
		T item = this.currentNode.getSales();
		this.currentNode = this.currentNode.getNext();
		return item;
	}

}
