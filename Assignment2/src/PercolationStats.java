import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double mean;
    private final double sd;
    private final double highConfidence;
    private final double lowConfidence;
    private static final double confLvl = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        double[] results = new double[trials];

        int testR;
        int testC;

        for (int i = 0; i < trials; i++) {
            Percolation per = new Percolation(n);
            while (!per.percolates()) {
                testR = (int) ((StdRandom.uniform() * n) + 1);
                testC = (int) ((StdRandom.uniform() * n) + 1);
                if (!per.isOpen(testR, testC)) {
                    per.open(testR, testC);
                }
            }
            results[i] = (double) (per.numberOfOpenSites()) / (n * n);
        }

        mean = StdStats.mean(results);
        sd = StdStats.stddev(results);

        final double v = (confLvl * sd) / Math.sqrt(trials);
        lowConfidence = mean - v;
        highConfidence = mean + v;

    }

    // sample mean of percolation threshold
    public double mean() {

        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {

        return sd;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {

        return lowConfidence;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {

        return highConfidence;
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length < 2) throw new IllegalArgumentException("Insufficient arguments");
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, trials);

        StdOut.println("mean\t\t\t\t\t" + "= " + ps.mean());
        StdOut.println("stddev\t\t\t\t\t" + "= " + ps.stddev());
        StdOut.println("95% confidence interval " + "= [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }

}