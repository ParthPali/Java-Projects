package jrJava.barbarianAttack;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<E> {

	private Link<E> first;
	
	public LinkedList(){
		first = null; // redundant
	}
	
	
	public Iterator<E> Iterator(){
		return new MyIterator();
	}
	
	public boolean isEmpty(){ 
		return first==null;
	}
	
	public int size(){
		int count = 0;
		Link<E> current = first;
		while(current!=null){
			count++;
			current = current.next;
		}
		return count;
	}
	
	
	public E remove(){
		Link<E> temp = first;
		if(first!=null) first = first.next;
		return temp.data;
	}
	
	
	/*
	public void insert(E data){
		Link<E> link = new Link(data);
		
		Link<E> current = first;
		Link<E> previous = null;
		
		while(current!=null && current.data.compareTo(data)<0){
			previous = current;
			current = current.next;
		}
		
		if(previous==null){
			if(first!=null) link.next = first;
			first = link;
		}
		else { 
			previous.next = link;
			link.next = current;
		}
	}
	*/
	
	
	public void add(E data){
		Link<E> link = new Link<E>(data);
		link.next = first;
		first = link;
	}
	
	
	public void printAll(){
		Link<E> current = first;
		while(current!=null){
			System.out.print(current + " ");
			current = current.next;
		}
		System.out.println();
	}
	
	
	public class MyIterator implements Iterator<E> {

		Link<E> current;
		Link<E> previous;
		
		public void reset(){
			current = null;
			previous = null;
		}
		
		public boolean hasNext(){
			if(current==null && previous==null) return first!=null;
			return current.next!=null;
		}
		
		public E next(){
			if(current==null && previous==null){
				current = first;
			}
			else {
				previous = current;
				current = current.next;
			}
			return current.data;
		}
		
		
		public void remove() {   
			if(current==null) throw new NoSuchElementException(); 
			
			if(previous==null){
				first = first.next;
				reset();
			}
			else {
				previous.next = current.next;
				current = previous;
			}
		}
	}

	
	
	private static class Link<T> {

		public Link<T> next;
		
		public T data;
		
		public Link(T data){
			this.data = data;
		}
		
		public String toString(){
			return "[" + data.toString() + "]";
		}
		
	}
}










