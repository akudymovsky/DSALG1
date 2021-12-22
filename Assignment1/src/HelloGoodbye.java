/*
 * Write a program HelloGoodbye.java that takes two names as command-line
 * arguments and prints hello and goodbye messages as shown below
 * (with the names for the hello message in the same order as the
 * command-line arguments and with the names for the goodbye message in reverse order).
 */

public class HelloGoodbye {

    public static void main(String[] args) {
        String firstName, secondName;

        if (args.length > 1) {
            firstName = args[0];
            secondName = args[1];

            System.out.println("Hello " + firstName + " and " + secondName + '.');
            System.out.println("Goodbye " + secondName + " and " + firstName + '.');
        }

    }
}
