package com.mgg;

import java.util.Comparator;
import java.util.Iterator;

public class LinkedList<T> implements Iterable<T> {
	// Variables
	private Node<T> startSale;
	private Node<T> endSale;
	private int size = 0;
	private int index;
	private final Comparator<T> comparator;
	
	public LinkedList(Comparator<T> comparator) {
		this.startSale = null;
		this.endSale = null;
		this.comparator = comparator;
	}

	public LinkedList(Node<T> startSale, Node<T> endSale, Comparator<T> comparator) {
		this.startSale = startSale;
		this.endSale = endSale;
		this.comparator = comparator;
		startSale.setNext(endSale);
	}

	public Node<T> getstartSale() {
		return startSale;
	}

	public void setstartSale(Node<T> startSale) {
		this.startSale = startSale;
	}

	public Node<T> getendSale() {
		return endSale;
	}

	public void setendSale(Node<T> endSale) {
		this.endSale = endSale;
	}

	/**
	 * This function returns the size of the list, the number of elements currently
	 * stored in it.
	 * 
	 * @return
	 */

	public int getSize() {
		size = 0;
		Node<T> Sales = startSale;
		if (Sales == null) {
			size = 0;
		} else {
			while (Sales.getNext() != null) {
				size++;
				Sales = Sales.getNext();
			}
		}
		size++;
		// return size;
		index = size - 1;
		return size;
	}

	/**
	 * This function clears out the contents of the list, making it an empty list.
	 */
	public void clear() {
		startSale = null;
		getSize();
	}

	/**
	 * This method adds the given {@link Sales} instance to the beginning of the
	 * list.
	 * 
	 * @param t
	 */
	public void add(T t) {
		Node<T> newSales = new Node<T>(t);
		size++;
		index++;
		if (startSale == null && endSale == null) {
			startSale = newSales;
			endSale = newSales;
		} else {
			Node<T> currentSales = startSale;
			Node<T> afterSales = currentSales.getNext();
			if (this.comparator.compare(currentSales.getSales(), newSales.getSales()) > 0) {
				startSale = newSales;
				startSale.setNext(currentSales);
				currentSales.setNext(afterSales);
			} else {
				while (afterSales != null) {
					if (this.comparator.compare(afterSales.getSales(), newSales.getSales()) < 0) {
						afterSales = afterSales.getNext();
					} else if (this.comparator.compare(afterSales.getSales(), newSales.getSales()) == 0) {
						Node<T> evenAfterSales = afterSales.getNext();
						afterSales.setNext(newSales);
						newSales.setNext(evenAfterSales);
						break;
					} else if (this.comparator.compare(afterSales.getSales(), newSales.getSales()) > 0) {
						Node<T> beforeSales = startSale;
						while (beforeSales.getNext() != afterSales) {
							beforeSales = beforeSales.getNext();
						}
						beforeSales.setNext(newSales);
						newSales.setNext(afterSales);
						break;
					}
				}
				Node<T> beforeSales = startSale;
				while (beforeSales.getNext() != afterSales) {
					beforeSales = beforeSales.getNext();
				}
				if (this.comparator.compare(beforeSales.getSales(), newSales.getSales()) < 0) {
					beforeSales.setNext(newSales);
					newSales.setNext(afterSales);
				}
			}

		}

		Node<T> currentSales = startSale;
		while (currentSales.getNext() != null) {
			currentSales = currentSales.getNext();
		}
		endSale = currentSales;
	}

	/**
	 * This method removes the {@link Sales} from the given <code>position</code>,
	 * indices start at 0. Implicitly, the remaining elements' indices are reduced.
	 * 
	 * @param position
	 */
	public void remove(int position) {
		getSize();
		Node<T> currentNode = startSale;
		if (position < 0 || position > index) {
			System.out.println("IndexOutOfBoundsException");
		} else if (position == 0) {
			startSale = startSale.getNext();
			size--;
			index--;
		} else if (position == index) {
			for (int i = 0; i < position - 1; i++) {
				currentNode = currentNode.getNext();
			}
			currentNode.setNext(null);
			endSale = currentNode;
			size--;
			index--;
		} else {
			for (int i = 0; i < position - 1; i++) {
				currentNode = currentNode.getNext();
			}
			Node<T> toBeRemoved = currentNode.getNext();
			Node<T> nextNode = toBeRemoved.getNext();
			currentNode.setNext(nextNode);
			size--;
			index--;
		}
	}

	/**
	 * Returns the {@link Sales} element stored at the given
	 * <code>position</code>.
	 * 
	 * @param position
	 * @return
	 */
	public T getSales(int position) {
		getSize();
		Node<T> headNode = startSale;
		if (position < index || position > index) {
			return null;
		} else if (position == 0) {
			return headNode.getSales();
		} else if (position == index) {
			return this.endSale.getSales();
		} else {
			for (int i = 0; i < position; i++) {
				if (headNode.getNext() == null) {
					return null;
				} else {
					headNode = headNode.getNext();
				}
			}
			return headNode.getSales();
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new LinkedListIterator<T>(this);
	}

}
