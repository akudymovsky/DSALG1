import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int gridLength;
    private final int top;
    private final int bottom;
    private boolean[] sitesGrid;
    private int openSitesCount;
    private final WeightedQuickUnionUF ufPercolation;
    private final WeightedQuickUnionUF ufIsFull;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException(Integer.toString(n));
        this.gridLength = n;
        openSitesCount = 0;
        sitesGrid = new boolean[n * n + 2]; // N*N grid + 2 spaces for virtual sites at top and bottom

        // initialize grid with zeroes representing all sites being blocked
        for (int i = 0; i < sitesGrid.length; i++)
            sitesGrid[i] = false;

        ufPercolation = new WeightedQuickUnionUF(n * n + 2); // includes virtual top and bottom
        ufIsFull = new WeightedQuickUnionUF(n * n + 1); // includes virtual top

        top = 0; // index of the top virtual site
        bottom = n * n + 1;
    }

    private void validateArguments(int row, int col) {
        if (row < 1 || row > gridLength || col < 1 || col > gridLength)
            throw new IllegalArgumentException();
    }

    // Calculate position in 1D array from 2D notation
    private int indexOf(int row, int col) {
        validateArguments(row, col);
        return (row - 1) * gridLength + (col - 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateArguments(row, col);

        int i = indexOf(row, col);

        // Check if the grid is open at current location then proceed
        if (!sitesGrid[i]) {
            sitesGrid[i] = true;
            ++openSitesCount;

            if (row == 1) {
                ufPercolation.union(i, top); // connect all nodes to the top
                ufIsFull.union(i, top);
            }
            if (row == gridLength)
                ufPercolation.union(i, bottom); // and bottom

            // Check all adjacent open sites
            // Above
            if (row > 1 && isOpen(row - 1, col)) {
                assert (i > gridLength);
                ufPercolation.union(i, i - gridLength);
                ufIsFull.union(i, i - gridLength);
            }
            // Below
            if (row < gridLength && isOpen(row + 1, col)) {
                assert (i + gridLength < gridLength * gridLength);
                ufPercolation.union(i, i + gridLength);
                ufIsFull.union(i, i + gridLength);
            }
            // Left
            if (col > 1 && isOpen(row, col - 1)) {
                ufPercolation.union(i, i - 1);
                ufIsFull.union(i, i - 1);
            }
            // Right
            if (col < gridLength && isOpen(row, col + 1)) {
                ufPercolation.union(i, i + 1);
                ufIsFull.union(i, i + 1);
            }

        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateArguments(row, col);
        return sitesGrid[indexOf(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateArguments(row, col);
        if (!isOpen(row, col)) return false;
        // Is the component connected to the top?
        return ufIsFull.find(indexOf(row, col)) == ufIsFull.find(top);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return ufPercolation.find(top) == ufPercolation.find(bottom);
    }

}