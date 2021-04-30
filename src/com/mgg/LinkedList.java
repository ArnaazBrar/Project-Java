package com.mgg;

import java.util.Comparator;

public class LinkedList {
	// Variables
	private Node startSale;
	private Node endSale;
	private int size = 0;
	private int index;
	private final Comparator<Sales> comparator;
	
	public LinkedList(Comparator<Sales> comparator) {
		this.startSale = null;
		this.endSale = null;
		this.comparator = comparator;
	}

	public LinkedList(Node startSale, Node endSale, Comparator<Sales> comparator) {
		this.startSale = startSale;
		this.endSale = endSale;
		this.comparator = comparator;
		startSale.setNext(endSale);
	}

	public Node getstartSale() {
		return startSale;
	}

	public void setstartSale(Node startSale) {
		this.startSale = startSale;
	}

	public Node getendSale() {
		return endSale;
	}

	public void setendSale(Node endSale) {
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
		Node Sales = startSale;
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
	public void add(Sales t) {
		Node newSales = new Node(t);
		size++;
		index++;
		if (startSale == null && endSale == null) {
			startSale = newSales;
			endSale = newSales;
		} else {
			Node currentSales = startSale;
			Node afterSales = currentSales.getNext();
			if (this.comparator.compare(currentSales.getSales(), newSales.getSales()) > 0) {
				startSale = newSales;
				startSale.setNext(currentSales);
				currentSales.setNext(afterSales);
			} else {
				while (afterSales != null) {
					if (this.comparator.compare(afterSales.getSales(), newSales.getSales()) < 0) {
						afterSales = afterSales.getNext();
					} else if (this.comparator.compare(afterSales.getSales(), newSales.getSales()) == 0) {
						Node evenAfterSales = afterSales.getNext();
						afterSales.setNext(newSales);
						newSales.setNext(evenAfterSales);
						break;
					} else if (this.comparator.compare(afterSales.getSales(), newSales.getSales()) > 0) {
						Node beforeSales = startSale;
						while (beforeSales.getNext() != afterSales) {
							beforeSales = beforeSales.getNext();
						}
						beforeSales.setNext(newSales);
						newSales.setNext(afterSales);
						break;
					}
				}
				Node beforeSales = startSale;
				while (beforeSales.getNext() != afterSales) {
					beforeSales = beforeSales.getNext();
				}
				if (this.comparator.compare(beforeSales.getSales(), newSales.getSales()) < 0) {
					beforeSales.setNext(newSales);
					if(newSales.getNext() != null) {
						Node evenAfterSales = newSales.getNext();
						afterSales.setNext(evenAfterSales);
					}
					newSales.setNext(afterSales);
				}
			}

		}

		Node currentSales = startSale;
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
		Node currentNode = startSale;
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
			Node toBeRemoved = currentNode.getNext();
			Node nextNode = toBeRemoved.getNext();
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
	public Sales getSales(int position) {
		getSize();
		Node headNode = startSale;
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

}
