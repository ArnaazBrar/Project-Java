package com.mgg;

public class Node {

	private Sales item;
	private Node next;

	public Node() {
		this.item = null;
		this.next = null;
	}

	public Node(Sales item) {
		this.item = item;
		this.next = null;
	}

	public Node(Sales item, Node next) {
		this.item = item;
		this.next = next;
	}

	public Sales getSales() {
		return item;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public void setItem(Sales item) {
		this.item = item;
	}
}