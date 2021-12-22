import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;
/*
Randomized queue. A randomized queue is similar to a stack or queue, except that the item removed is chosen
uniformly at random among items in the data structure.
Create a generic data type RandomizedQueue that implements the following API


Iterator.  Each iterator must return the items in uniformly random order. The order of two or more iterators to the
same randomized queue must be mutually independent; each iterator must maintain its own random order.

Corner cases.  Throw the specified exception for the following corner cases:

Throw an IllegalArgumentException if the client calls enqueue() with a null argument.
Throw a java.util.NoSuchElementException if the client calls either sample() or dequeue()
when the randomized queue is empty.
Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator
when there are no more items to return.
Throw an UnsupportedOperationException if the client calls the remove() method in the iterator.
Unit testing.  Your main() method must call directly every public constructor and method to verify that they work as
prescribed (e.g., by printing results to standard output).

Performance requirements.  Your randomized queue implementation must support each randomized queue operation
(besides creating an iterator) in constant amortized time. That is, any intermixed sequence of m randomized queue
operations (starting from an empty queue) must take at most cm steps in the worst case, for some constant c.
A randomized queue containing n items must use at most 48n + 192 bytes of memory. Additionally, your iterator
implementation must support operations next() and hasNext() in constant worst-case time; and construction in
linear time; you may (and will need to) use a linear amount of extra memory per iterator.
*/


public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size = 0;
    private Item[] rQueue;

    // construct an empty randomized queue
    public RandomizedQueue() {
        rQueue = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (size == rQueue.length)
            resize(rQueue.length * 2);
        rQueue[size++] = item;
    }

    private void resize(int capacity) {
        assert capacity >= size;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++)
            copy[i] = rQueue[i];
        rQueue = copy;
        copy = null; // time for garbage collector
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int rd = StdRandom.uniform(size);
        Item item = rQueue[rd];
        if (rd != size - 1) // if not the last element
            rQueue[rd] = rQueue[size - 1];
        rQueue[size - 1] = null; // delete from the tail
        size--;
        if (size > 0 && size == rQueue.length / 4)
            resize(rQueue.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int rd = StdRandom.uniform(size);
        Item item = rQueue[rd];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] copy = (Item[]) new Object[rQueue.length];
        private int copySize = size;

        public RandomizedQueueIterator() {
            for (int i = 0; i < rQueue.length; i++)
                copy[i] = rQueue[i];
        }

        @Override
        public boolean hasNext() {
            return copySize > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int rd = StdRandom.uniform(copySize);
            Item item = copy[rd];
            if (rd != copySize - 1)
                copy[rd] = copy[copySize - 1];
            copy[copySize - 1] = null;
            copySize--;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rQueue = new RandomizedQueue<>();
//        for (int i=0; i<100; i++) {
//            rQueue.enqueue(i);
//            StdOut.println(rQueue.sample());
//        }

        for (int i = 0; i < 100; i++) {
            rQueue.enqueue(i);
            rQueue.sample();
        }

        while (rQueue.iterator().hasNext()) rQueue.dequeue();

        for (int i = 0; i < 100; i++) {
            rQueue.enqueue(i);
            StdOut.println("Size: " + rQueue.size());
        }

        while (rQueue.iterator().hasNext()) {
            Integer v = rQueue.iterator().next();
            StdOut.println(v);
            rQueue.dequeue();
        }
    }

}