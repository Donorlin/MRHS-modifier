package mrhs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

/**
 *
 * @author Daniel Jahodka
 */
public class MrhsEquation {

    private int nRows;
    private int nCols;
    private int nRHS;
    private ArrayList<ArrayList<Integer>> leftSide;
    private HashSet<ArrayList<Integer>> rightSide;

    protected void generateRandomLeftSide(Random rand, int nRows, int nCols) {
        leftSide = new ArrayList<>();
        for (int j = 0; j < nRows; j++) {
            ArrayList<Integer> l = new ArrayList<>();
            for (int k = 0; k < nCols; k++) {
                l.add(rand.nextInt(2));
            }
            leftSide.add(l);
        }
    }

    protected void generateRandomRightSides(Random rand, int nRHS, int nCols) {
        rightSide = new HashSet<>();
        for (int j = 0; j < nRHS; j++) {
            ArrayList<Integer> r = new ArrayList<>();
            for (int k = 0; k < nCols; k++) {
                r.add(rand.nextInt(2));
            }
            rightSide.add(r);
        }
    }

    protected void setnRHS(int nRHS) {
        this.nRHS = nRHS;
    }

    protected void setnCols(int nCols) {
        this.nCols = nCols;
    }

    protected void setnRows(int nRows) {
        this.nRows = nRows;
    }

    protected void setRightSide(HashSet<ArrayList<Integer>> leftSide) {
        this.rightSide = leftSide;
    }

    protected void setLeftSide(ArrayList<ArrayList<Integer>> rightSide) {
        this.leftSide = rightSide;
    }

    protected int getnRows() {
        return nRows;
    }

    protected int getnCols() {
        return nCols;
    }

    protected int getnRHS() {
        return nRHS;
    }

    protected ArrayList<ArrayList<Integer>> getLeftSide() {
        return leftSide;
    }

    protected HashSet<ArrayList<Integer>> getRightSide() {
        return rightSide;
    }

    protected boolean checkSizes() {
        if (nRHS != rightSide.size()) {
            nRHS = rightSide.size();
        }
        if (nRows != leftSide.size()) {
            nRows = leftSide.size();
        }
        return checkAllCols();
    }

    private boolean checkAllCols() {
        int sizeFirst = leftSide.get(0).size();

        for (ArrayList<Integer> ls : leftSide) {
            if (sizeFirst != ls.size()) {
                return false;
            }
        }
        for (ArrayList<Integer> rs : rightSide) {
            if (sizeFirst != rs.size()) {
                return false;
            }
        }
        if (nCols != sizeFirst) {
            nCols = sizeFirst;
        }
        return true;
    }

    protected void swapCols(int i, int j) {
        if (Utils.checkColumnsBounds(this, "swapcols", i, j)) {
            for (ArrayList<Integer> l : rightSide) {
                Collections.swap(l, i, j);
            }
            for (ArrayList<Integer> l : leftSide) {
                Collections.swap(l, i, j);
            }
        }
    }

    protected void addCol(int to, int toAdd) {
        if (Utils.checkColumnsBounds(this, "addcols", to, toAdd)) {
            for (ArrayList<Integer> l : leftSide) {
                int valueTo = l.get(to);
                int valueToAdd = l.get(toAdd);
                l.set(to, valueTo ^ valueToAdd);
            }
            for (ArrayList<Integer> r : rightSide) {
                int valueTo = r.get(to);
                int valueToAdd = r.get(toAdd);
                r.set(to, valueTo ^ valueToAdd);
            }
        }
    }

    protected void deleteCol(int i) {
        if (Utils.checkColumnsBounds(this, "deletecol", i)) {
            for (ArrayList<Integer> l : leftSide) {
                l.remove(i);
            }
            for (ArrayList<Integer> r : rightSide) {
                r.remove(i);
            }
            nCols--;
        }
    }
}