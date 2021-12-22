import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/*
Dequeue. A double-ended queue or deque (pronounced “deck”) is a generalization of a stack and a queue that supports
adding and removing items from either the front or the back of the data structure. Create a generic data type Deque
that implements the following API

Corner cases.  Throw the specified exception for the following corner cases:

Throw an IllegalArgumentException if the client calls either addFirst() or addLast() with a null argument.
Throw a java.util.NoSuchElementException if the client calls either removeFirst() or removeLast when the deque is empty.
Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator when there are no more
items to return.
Throw an UnsupportedOperationException if the client calls the remove() method in the iterator.
Unit testing.  Your main() method must call directly every public constructor and method to help verify that they work
as prescribed (e.g., by printing results to standard output).

Performance requirements.  Your deque implementation must support each deque operation (including construction)
in constant worst-case time. A deque containing n items must use at most 48n + 192 bytes of memory. Additionally,
your iterator implementation must support each operation (including construction) in constant worst-case time.
*/

public class Deque<Item> implements Iterable<Item> {

    private int size;

    private Node<Item> first, last;

    private class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> previous;
    }

    // construct an empty deque
    public Deque() {
        size = 0;
        first = last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null || last == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (null == item) throw new IllegalArgumentException();
        Node<Item> oldFirst = first;
        first = new Node<>();
        first.item = item;
        size++;
        first.previous = null;
        if (oldFirst == null)
            last = first;
        else {
            first.next = oldFirst;
            oldFirst.previous = first;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (null == item) throw new IllegalArgumentException();

        Node<Item> oldlast = last;
        last = new Node<>();
        last.item = item;
        size++;
        last.next = null;
        last.previous = null;

        if (isEmpty())
            first = last;
        else {
            oldlast.next = last;
            last.previous = oldlast;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        size--;
        // if first now points to null, then it's the last and only node left
        if (isEmpty()) last = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        last = last.previous;
        size--;
        if (isEmpty()) first = null;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        Node<Item> current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (null == current) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> dequeOfInts = new Deque<>();
        Deque<String> dequeOfStrings = new Deque<>();

        for (int i = 0; i < 10; i++) {
            dequeOfInts.addLast(i);
            dequeOfInts.addFirst(i * i);
        }

        while (dequeOfInts.iterator().hasNext())
            StdOut.println(dequeOfInts.removeLast());

        dequeOfStrings.addFirst("Hello");
        dequeOfStrings.addFirst("World");
        dequeOfStrings.addLast("I'm Deque");

        while (dequeOfStrings.iterator().hasNext()) {
            StdOut.println(dequeOfStrings.removeFirst());
            StdOut.println("size:" + dequeOfStrings.size());
        }
    }
}
