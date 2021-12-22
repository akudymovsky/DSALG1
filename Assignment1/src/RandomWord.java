/*
 * Write a program RandomWord.java that reads a sequence of words from standard input and prints one of those words
 * uniformly at random. Do not store the words in an array or list. Instead, use Knuth’s method: when reading
 * the ith word, select it with probability 1/i to be the champion, replacing the previous champion. After reading
 * all of the words, print the surviving champion.
 *
 * Use the following library functions from algs4.jar
 * StdIn.readString(): reads and returns the next string from standard input.
 * StdIn.isEmpty(): returns true if there are no more strings available on standard input, and false otherwise.
 * StdOut.println(): prints a string and terminating newline to standard output.
 * It’s also fine to use System.out.println() instead.
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {

    public static void main(String[] args) {

        String champion = StdIn.readString();
        int i = 2;

        while (!StdIn.isEmpty()) {
            String nextWord = StdIn.readString();
            if (StdRandom.bernoulli(1 / (double) i))
                champion = nextWord;
            ++i;
        }

        StdOut.println(champion);
    }
}

